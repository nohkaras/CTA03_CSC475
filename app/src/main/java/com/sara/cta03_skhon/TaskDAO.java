package com.sara.cta03_skhon;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO {

    // SQLiteDatabase instance for database operations
    private SQLiteDatabase db;
    // Helper class for managing database creation and version management
    private TaskDatabaseHelper dbHelper;

    // Constructor to initialize the database helper
    public TaskDAO(Context context) {
        dbHelper = new TaskDatabaseHelper(context);
    }

    // Open the database for writing
    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    // Close the database
    public void close() {
        dbHelper.close();
    }

    // Add a new task item to the database
    public void addTodoItem(String task) {
        // ContentValues to hold the task details
        ContentValues values = new ContentValues();
        values.put(TaskDatabaseHelper.COLUMN_TASK, task);
        values.put(TaskDatabaseHelper.COLUMN_COMPLETED, 0);
        db.insert(TaskDatabaseHelper.TABLE_NAME, null, values);
    }

    // Delete a task item from the database by ID
    public void deleteTodoItem(long id) {
        db.delete(TaskDatabaseHelper.TABLE_NAME, TaskDatabaseHelper.COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }

    // Update the completion status of a task item by ID
    public void updateTodoItem(long id, boolean completed) {
        // ContentValues to hold the updated task details
        ContentValues values = new ContentValues();
        values.put(TaskDatabaseHelper.COLUMN_COMPLETED, completed ? 1 : 0); // Set completion status
        // Update the task in the table where the ID matches
        db.update(TaskDatabaseHelper.TABLE_NAME, values, TaskDatabaseHelper.COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }

    // Gets all the task items from the database
    public List<TaskItem> getAllTodoItems() {
        List<TaskItem> items = new ArrayList<>();
        // Query the database for all task items
        Cursor cursor = db.query(TaskDatabaseHelper.TABLE_NAME, null, null, null, null, null, null);

        // Iterate through the result set and create TaskItem objects
        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(TaskDatabaseHelper.COLUMN_ID)); // Get the task ID
            String task = cursor.getString(cursor.getColumnIndexOrThrow(TaskDatabaseHelper.COLUMN_TASK)); // Get the task description
            int completed = cursor.getInt(cursor.getColumnIndexOrThrow(TaskDatabaseHelper.COLUMN_COMPLETED)); // Get the completion status

            items.add(new TaskItem(id, task, completed == 1)); // Add the task item to the list
        }
        cursor.close(); // Close the cursor
        return items; // Return the list of task items
    }
}
