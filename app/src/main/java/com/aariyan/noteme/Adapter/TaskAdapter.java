package com.aariyan.noteme.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.aariyan.noteme.Activity.AddTaskActivity;
import com.aariyan.noteme.Activity.TaskDetailsActivity;
import com.aariyan.noteme.Database.DatabaseAdapter;
import com.aariyan.noteme.Model.TaskModel;
import com.aariyan.noteme.R;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    //to get the context of host activity or fragment
    private Context context;
    //to get the whole list of data
    private List<TaskModel> listOfTask;
    // instance variable for performing Update and Delete operation
    DatabaseAdapter databaseAdapter;
    int result;

    public TaskAdapter(Context context, List<TaskModel> listOfTask) {
        this.context = context;
        this.listOfTask = listOfTask;
        //instantiate the database
        databaseAdapter = new DatabaseAdapter(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.task_single_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //mapping the list data with model class
        TaskModel model = listOfTask.get(position);
        //set the task name
        holder.taskName.setText(model.getTaskName());
        //set the created date
        holder.taskCreatedDate.setText(model.getTaskCreatedDate());
        //set the deadline of task
        holder.taskDeadline.setText(model.getTaskDeadline());

        //for showing the details of particular task
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TaskDetailsActivity.class);
                intent.putExtra("createdDate", model.getTaskCreatedDate());
                intent.putExtra("status", model.getTaskStatus());
                intent.putExtra("title", model.getTaskName());
                intent.putExtra("description", model.getTaskDescription());
                intent.putExtra("deadline", model.getTaskDeadline());
                intent.putExtra("email", model.getEmail());
                intent.putExtra("phone", model.getPhone());
                intent.putExtra("url", model.getUrl());
                intent.putExtra("id", model.getPrimaryKey());
                context.startActivity(intent);
            }
        });

        //for editing single task
        holder.editTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddTaskActivity.class);
                intent.putExtra("hasExtra", "fromList");
                intent.putExtra("createdDate", model.getTaskCreatedDate());
                intent.putExtra("status", model.getTaskStatus());
                intent.putExtra("title", model.getTaskName());
                intent.putExtra("description", model.getTaskDescription());
                intent.putExtra("deadline", model.getTaskDeadline());
                intent.putExtra("email", model.getEmail());
                intent.putExtra("phone", model.getPhone());
                intent.putExtra("url", model.getUrl());
                intent.putExtra("id", model.getPrimaryKey());
                context.startActivity(intent);
            }
        });

        //delete a single task
        holder.deleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result = databaseAdapter.deleteTask("" + model.getPrimaryKey());

                if (result > 0) {
                    Toast.makeText(context, "Task deleted successfully!", Toast.LENGTH_SHORT).show();
                    listOfTask.remove(position);
                    notifyItemRemoved(position);
                } else {
                    Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return listOfTask.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView taskName, taskCreatedDate, taskDeadline;
        private ImageView editTask, deleteTask;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            taskName = itemView.findViewById(R.id.taskNameOnTaskSingleList);
            taskCreatedDate = itemView.findViewById(R.id.taskCreatedDateOnTaskSingleList);
            taskDeadline = itemView.findViewById(R.id.taskDeadlineOnTaskSingleList);
            editTask = itemView.findViewById(R.id.editTaskFromList);
            deleteTask = itemView.findViewById(R.id.deleteTaskFromList);
        }
    }
}
