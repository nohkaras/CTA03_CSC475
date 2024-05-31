package com.sara.cta03_skhon;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {


    private List<TaskItem> taskItems;  // List to hold task items
    private TaskDAO taskDAO;  // DAO for managing tasks in the database


    // Constructor to initialize task items and DAO
    public TaskAdapter(List<TaskItem> taskItems, TaskDAO taskDAO) {
        this.taskItems = taskItems;
        this.taskDAO = taskDAO;
    }


    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new ViewHolder(view); // Return a new holder instance
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TaskItem item = taskItems.get(position);
        holder.textViewTask.setText(item.getTask());
        holder.checkBoxCompleted.setChecked(item.isCompleted());


        // Set a click listener for the checkbox to update the task's completion status in the database
        holder.checkBoxCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskDAO.updateTodoItem(item.getId(), holder.checkBoxCompleted.isChecked());
            }
        });

        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskDAO.deleteTodoItem(item.getId());
                updateItems(taskDAO.getAllTodoItems());
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskItems.size();
    }

    public void updateItems(List<TaskItem> items) {
        this.taskItems = items;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTask;
        CheckBox checkBoxCompleted;
        Button buttonDelete;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTask = itemView.findViewById(R.id.textViewTask);
            checkBoxCompleted = itemView.findViewById(R.id.checkBoxCompleted);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }
}
