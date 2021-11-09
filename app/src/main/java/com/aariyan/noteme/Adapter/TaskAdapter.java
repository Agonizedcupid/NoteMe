package com.aariyan.noteme.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.aariyan.noteme.Database.DatabaseAdapter;
import com.aariyan.noteme.Model.TaskModel;
import com.aariyan.noteme.R;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    //to get the context of host activity or fragment
    private Context context;
    private List<TaskModel> listOfTask;
    DatabaseAdapter databaseAdapter;
    int result;

    public TaskAdapter(Context context, List<TaskModel> listOfTask) {
        this.context = context;
        this.listOfTask = listOfTask;
        databaseAdapter = new DatabaseAdapter(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.task_single_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        TaskModel model = listOfTask.get(position);
        holder.taskName.setText(model.getTaskName());
        holder.taskCreatedDate.setText(model.getTaskCreatedDate());
        holder.taskDeadline.setText(model.getTaskDeadline());

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
