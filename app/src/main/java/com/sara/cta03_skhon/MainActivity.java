package com.sara.cta03_skhon;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class MainActivity extends AppCompatActivity {

     //DAO and Adapter: used for managing tasks in the database
    // and displaying task items in a RecyclerView
    private TaskDAO taskDAO;
    private TaskAdapter todoAdapter;


    /* Sets the layout: setContentView(R.layout.activity_main);
       taskDAO = new TaskDAO(this): Initialize TaskDAO and open the database connection
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskDAO = new TaskDAO(this);
        taskDAO.open();

        // Sets up the RecyclerView for displaying task items
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the TaskAdapter with data from the DAO and set it to the RecyclerView
        // Initialize the EditText for inputting new tasks and the Button for adding tasks
        todoAdapter = new TaskAdapter(taskDAO.getAllTodoItems(), taskDAO);
        recyclerView.setAdapter(todoAdapter);

        EditText editTextTask = findViewById(R.id.editTextTask);
        Button buttonAdd = findViewById(R.id.buttonAdd);

        // Set the OnClickListener for the add button to handle adding new tasks
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String task = editTextTask.getText().toString();
                if (!task.isEmpty()) {
                    taskDAO.addTodoItem(task);
                    todoAdapter.updateItems(taskDAO.getAllTodoItems());
                    editTextTask.setText("");
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        taskDAO.close();
        super.onDestroy();
    }
}
