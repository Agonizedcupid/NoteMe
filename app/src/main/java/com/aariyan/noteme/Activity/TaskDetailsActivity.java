package com.aariyan.noteme.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aariyan.noteme.Database.DatabaseAdapter;
import com.aariyan.noteme.HomeScreen;
import com.aariyan.noteme.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TaskDetailsActivity extends AppCompatActivity {

    private FloatingActionButton editTaskBtn;
    private String createdDate, status, title, description, deadline, email, phone, url;
    private int id;

    //Instance of database adapter to update the current task
    DatabaseAdapter databaseAdapter;
    int result;

    private ImageView deleteTask;

    private TextView taskCreatedDate, taskStatus, taskName, taskDescription, taskDeadline;
    private LinearLayout taskEmail, taskPhone, taskUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        databaseAdapter = new DatabaseAdapter(this);

        //getting the intent value for showing the details
        if (getIntent() != null) {
            createdDate = getIntent().getStringExtra("createdDate");
            status = getIntent().getStringExtra("status");
            title = getIntent().getStringExtra("title");
            description = getIntent().getStringExtra("description");
            deadline = getIntent().getStringExtra("deadline");
            email = getIntent().getStringExtra("email");
            phone = getIntent().getStringExtra("phone");
            url = getIntent().getStringExtra("url");
            id = getIntent().getIntExtra("id", -1);
        }

        //instantiate UI variable
        initUI();

        //setting the value:
        setUpValueOnUi();
    }

    //setting up the UI value for showing the details of single task
    private void setUpValueOnUi() {
        taskCreatedDate.setText(createdDate);
        taskStatus.setText(status);
        taskName.setText(title);
        taskDescription.setText(description);
        taskDeadline.setText(deadline);

        //action event for email,phone, and url
        taskEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!email.equals("")) {
                    try {
                        //it will redirect you to the email or gmail app
                        startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + email)));
                    } catch (android.content.ActivityNotFoundException e) {
                        //if no application found in the device then it will throw an exception
                        Toast.makeText(TaskDetailsActivity.this, "No application found!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //if no email provided
                    Toast.makeText(TaskDetailsActivity.this, "No email found!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        taskPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //it will redirect you to the dial pad with phone number
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phone));
                startActivity(intent);
            }
        });
        taskUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //it will open in browser
                if (!url.equals("")) {
                    if (!url.startsWith("http://")) {
                        url = "http://" + url;
                    }
                    Uri uri = Uri.parse(url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                } else {
                    Toast.makeText(TaskDetailsActivity.this, "No url found!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void initUI() {

        taskCreatedDate = findViewById(R.id.taskCreatedDateOnTaskDetailList);
        taskStatus = findViewById(R.id.statusOfTask);
        taskName = findViewById(R.id.taskNameOnDetailsScreen);
        taskDescription = findViewById(R.id.taskDescription);
        taskDeadline = findViewById(R.id.deadlineOnTaskDetailsScreen);
        taskEmail = findViewById(R.id.emailLayout);
        taskPhone = findViewById(R.id.phoneLayout);
        taskUrl = findViewById(R.id.urlLayout);

        //For deleting the task from details
        deleteTask = findViewById(R.id.deleteTaskFromDetailsPage);
        deleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result = databaseAdapter.deleteTask("" + id);
                if (result > 0) {
                    Toast.makeText(TaskDetailsActivity.this, "Task deleted successfully!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(TaskDetailsActivity.this, HomeScreen.class)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                } else {
                    Toast.makeText(TaskDetailsActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //if want to update task from details
        editTaskBtn = findViewById(R.id.editTaskFloatingBtn);
        editTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TaskDetailsActivity.this, AddTaskActivity.class);
                intent.putExtra("hasExtra", "fromList");
                intent.putExtra("createdDate", createdDate);
                intent.putExtra("status", status);
                intent.putExtra("title", title);
                intent.putExtra("description", description);
                intent.putExtra("deadline", deadline);
                intent.putExtra("email", email);
                intent.putExtra("phone", phone);
                intent.putExtra("url", url);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
    }
}