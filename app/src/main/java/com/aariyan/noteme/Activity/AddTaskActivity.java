package com.aariyan.noteme.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aariyan.noteme.Constant.Constant;
import com.aariyan.noteme.Database.DatabaseAdapter;
import com.aariyan.noteme.HomeScreen;
import com.aariyan.noteme.R;

import java.net.URL;
import java.util.Calendar;

public class AddTaskActivity extends AppCompatActivity {

    DatabaseAdapter databaseAdapter;
    long id;

    private TextView topHeadingTitle;
    private EditText taskName, taskDescription;
    private TextView deadline;
    private Spinner statusSpinner;
    private LinearLayout emailLayout, phoneLayout, urlLayout;
    private Button submitBtn;

    //calender button to show the calender to choose date
    private ImageView calenderBtn;

    //For date picker dialog
    private DatePickerDialog datePickerDialog;
    private Calendar calendar;
    int day, month, year;

    private String selectedDate = "";
    private String taskStatus = "";
    private String phone = "";
    private String email = "";
    private String url = "";

    private ProgressBar progressBar;

    //for storing the intent value
    private String createdDate, status, title, description, taskDeadline, taskEmail, taskPhone, taskUrl;
    private int primaryKey;

    private boolean checkTransition = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        databaseAdapter = new DatabaseAdapter(this);

        calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);

        initUI();

        //taking the intent value
        //if user want to update the task
        if (getIntent().hasExtra("hasExtra")) {
            checkTransition = true;
            topHeadingTitle.setText(getResources().getString(R.string.update_task));
            createdDate = getIntent().getStringExtra("createdDate");
            status = getIntent().getStringExtra("status");
            title = getIntent().getStringExtra("title");
            description = getIntent().getStringExtra("description");
            taskDeadline = getIntent().getStringExtra("deadline");
            taskEmail = getIntent().getStringExtra("email");
            taskPhone = getIntent().getStringExtra("phone");
            taskUrl = getIntent().getStringExtra("url");
            primaryKey = getIntent().getIntExtra("id", -1);
            fillingOldValue();

        } else {
            //if user want to create new task
            checkTransition = false;
            topHeadingTitle.setText(getResources().getString(R.string.add_task));
        }


    }

    //filling UI with present data
    private void fillingOldValue() {
        taskName.setText(title, TextView.BufferType.EDITABLE);
        taskDescription.setText(description, TextView.BufferType.EDITABLE);
        deadline.setText(taskDeadline);

        email = taskEmail;
        phone = taskPhone;
        url = taskUrl;
        selectedDate = taskDeadline;

        emailLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputDialogWithValue("email", taskEmail);
            }
        });

        phoneLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputDialogWithValue("phone", taskPhone);
            }
        });
        urlLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputDialogWithValue("url", taskUrl);
            }
        });
    }

    //show the dialog with value to update
    private void showInputDialogWithValue(String type, String value) {
        Dialog dialog = new Dialog(AddTaskActivity.this);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.url_phone_email_dialog);
        //dialog.setCancelable(false);

        ImageView topImage = dialog.findViewById(R.id.topImage);
        EditText writeEmailPhoneURL = dialog.findViewById(R.id.writeEmailPhoneURL);
        Button save = dialog.findViewById(R.id.saveInfo);

        if (type.equals("phone")) {
            topImage.setImageResource(R.drawable.ic_phone);
            writeEmailPhoneURL.setHint(getResources().getString(R.string.enter_phone));
            writeEmailPhoneURL.setText(value, TextView.BufferType.EDITABLE);
            save.setText(getResources().getString(R.string.save_phone));
        }
        if (type.equals("email")) {
            topImage.setImageResource(R.drawable.ic_email);
            writeEmailPhoneURL.setHint(getResources().getString(R.string.enter_email));
            writeEmailPhoneURL.setText(value, TextView.BufferType.EDITABLE);
            save.setText(getResources().getString(R.string.save_email));
        }

        if (type.equals("url")) {
            topImage.setImageResource(R.drawable.ic_url);
            writeEmailPhoneURL.setHint(getResources().getString(R.string.enter_url));
            writeEmailPhoneURL.setText(value, TextView.BufferType.EDITABLE);
            save.setText(getResources().getString(R.string.save_url));
        }


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (type.equals("phone")) {
//                    phone = writeEmailPhoneURL.getText().toString();
//                    Toast.makeText(AddTaskActivity.this, "Phone number added!", Toast.LENGTH_SHORT).show();
//                }
//                if (type.equals("email")) {
//                    email = writeEmailPhoneURL.getText().toString();
//                    Toast.makeText(AddTaskActivity.this, "Email added!", Toast.LENGTH_SHORT).show();
//
//                }
//                if (type.equals("url")) {
//                    url = writeEmailPhoneURL.getText().toString();
//                    Toast.makeText(AddTaskActivity.this, "URL added!", Toast.LENGTH_SHORT).show();
//
//                }
                //Phone number
                if (type.equals("phone")) {
                    if (writeEmailPhoneURL.getText().toString().length() >= 11) {
                        phone = writeEmailPhoneURL.getText().toString();
                        Toast.makeText(AddTaskActivity.this, "Phone number added!", Toast.LENGTH_SHORT).show();
                    } else if (writeEmailPhoneURL.getText().toString().length() > 0 && writeEmailPhoneURL.getText().toString().length() < 11) {
                        Toast.makeText(AddTaskActivity.this, "Phone number should be 11 digit!", Toast.LENGTH_SHORT).show();
                    }
                }

                //email
                if (type.equals("email")) {
                    if (writeEmailPhoneURL.getText().toString().length() > 0) {
                        String emailValidationPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                        if (writeEmailPhoneURL.getText().toString().matches(emailValidationPattern)) {
                            email = writeEmailPhoneURL.getText().toString();
                            Toast.makeText(AddTaskActivity.this, "Email added!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AddTaskActivity.this, "Invalid email!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                //URL
                if (type.equals("url")) {
                    if (writeEmailPhoneURL.getText().toString().length() > 0) {
                        if (isValidUrl(writeEmailPhoneURL.getText().toString())) {
                            url = writeEmailPhoneURL.getText().toString();
                            Toast.makeText(AddTaskActivity.this, "URL added!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AddTaskActivity.this, "Invalid URL!", Toast.LENGTH_SHORT).show();

                        }
                    }
                }
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    //instantiate UI and onClick event
    private void initUI() {
        topHeadingTitle = findViewById(R.id.topHeadingTitle);
        taskName = findViewById(R.id.taskNameEditText);
        taskDescription = findViewById(R.id.taskDescriptionEditText);
        deadline = findViewById(R.id.deadlineTextView);
        statusSpinner = findViewById(R.id.statusSpinner);
        emailLayout = findViewById(R.id.emailLinearLayout);
        phoneLayout = findViewById(R.id.phoneLinearLayout);
        urlLayout = findViewById(R.id.urlLinearLayout);
        calenderBtn = findViewById(R.id.calenderBtn);
        submitBtn = findViewById(R.id.submitTask);
        progressBar = findViewById(R.id.progressbar);

        emailLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputDialog("email");
            }
        });
        phoneLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputDialog("phone");
            }
        });
        urlLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputDialog("url");
            }
        });

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.task_status_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        statusSpinner.setAdapter(adapter);

        statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                taskStatus = parent.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        calenderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                selectDate();
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkTransition) {
                    //if want to add a new task
                    addTask();
                } else {
                    //if use want to update the task
                    updateTask();
                }

            }
        });
    }

    //for updating the task
    private void updateTask() {
        //by id checking the update is completed successfully or not;
        id = databaseAdapter.updateTask("" + primaryKey, taskName.getText().toString(), selectedDate, taskStatus,
                taskDescription.getText().toString(), "" + email, "" + phone, "" + url);

        //if update happens successfully
        if (id > 0) {
            showSuccessDialog();
        } else {
            //if found any issues
            Toast.makeText(AddTaskActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }
    }

    //for selecting the date from date picker
    private void selectDate() {
        datePickerDialog = new DatePickerDialog(AddTaskActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                int j = i1 + 1;

                selectedDate = i2 + "." + j + "." + i;
                deadline.setText(selectedDate);

            }
        }, day, month, year);

        //for not showing the previous date before today
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        datePickerDialog.show();
        progressBar.setVisibility(View.GONE);
    }

    //For adding new task
    private void addTask() {
        if (TextUtils.isEmpty(taskName.getText().toString())) {
            taskName.setError("Task name can't be empty!");
            taskName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(taskDescription.getText().toString())) {
            taskDescription.setError("Task description can't be empty!");
            taskDescription.requestFocus();
            return;
        }

        if (selectedDate.equals("")) {
            Toast.makeText(AddTaskActivity.this, "Please choose deadline!", Toast.LENGTH_SHORT).show();
            return;
        }
//        if (email.equals("")) {
//            Toast.makeText(AddTaskActivity.this, "Please add an email!", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (phone.equals("")) {
//            Toast.makeText(AddTaskActivity.this, "Please add phone number!", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if (url.equals("")) {
//            Toast.makeText(AddTaskActivity.this, "Please add url!", Toast.LENGTH_SHORT).show();
//            return;
//        }

        //If everything is ok (validation is done)
        id = databaseAdapter.insertTask(taskName.getText().toString(), Constant.todayDate(), selectedDate, taskStatus,
                taskDescription.getText().toString(), "" + email, "" + phone, "" + url);

        if (id > 0) {
            showSuccessDialog();
        } else {
            Toast.makeText(AddTaskActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
        }
    }

    //if success for adding or updating a task
    //It will redirect you to the MainActivity
    private void showSuccessDialog() {
        Dialog dialog = new Dialog(AddTaskActivity.this);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.success_dialog);
        dialog.setCancelable(false);

        Button ok = dialog.findViewById(R.id.okBtn);


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddTaskActivity.this, HomeScreen.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
                dialog.dismiss();

            }
        });

        dialog.show();
    }

    //for input value of option phone, email and url
    private void showInputDialog(String type) {
        Dialog dialog = new Dialog(AddTaskActivity.this);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.url_phone_email_dialog);
        //dialog.setCancelable(false);

        ImageView topImage = dialog.findViewById(R.id.topImage);
        EditText writeEmailPhoneURL = dialog.findViewById(R.id.writeEmailPhoneURL);
        Button save = dialog.findViewById(R.id.saveInfo);

        if (type.equals("phone")) {
            topImage.setImageResource(R.drawable.ic_phone);
            writeEmailPhoneURL.setHint(getResources().getString(R.string.enter_phone));
            writeEmailPhoneURL.setInputType(InputType.TYPE_CLASS_PHONE);
            save.setText(getResources().getString(R.string.save_phone));
        }
        if (type.equals("email")) {
            topImage.setImageResource(R.drawable.ic_email);
            writeEmailPhoneURL.setHint(getResources().getString(R.string.enter_email));
            save.setText(getResources().getString(R.string.save_email));
        }

        if (type.equals("url")) {
            topImage.setImageResource(R.drawable.ic_url);
            writeEmailPhoneURL.setHint(getResources().getString(R.string.enter_url));
            save.setText(getResources().getString(R.string.save_url));
        }


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Phone number
                if (type.equals("phone")) {
                    //checking if the phone number is valid of 11 digit
                    if (writeEmailPhoneURL.getText().toString().length() >= 11) {
                        phone = writeEmailPhoneURL.getText().toString();
                        Toast.makeText(AddTaskActivity.this, "Phone number added!", Toast.LENGTH_SHORT).show();
                    }
                    //if phone number is not valid
                    else if (writeEmailPhoneURL.getText().toString().length() > 0 && writeEmailPhoneURL.getText().toString().length() < 11) {
                        Toast.makeText(AddTaskActivity.this, "Phone number should be 11 digit!", Toast.LENGTH_SHORT).show();
                    }
                }

                //email
                if (type.equals("email")) {
                    if (writeEmailPhoneURL.getText().toString().length() > 0) {
                        //validating the email with regex/pattern
                        String emailValidationPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                        if (writeEmailPhoneURL.getText().toString().matches(emailValidationPattern)) {
                            email = writeEmailPhoneURL.getText().toString();
                            Toast.makeText(AddTaskActivity.this, "Email added!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AddTaskActivity.this, "Invalid email!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                //URL
                if (type.equals("url")) {
                    if (writeEmailPhoneURL.getText().toString().length() > 0) {
                        if (isValidUrl(writeEmailPhoneURL.getText().toString())) {
                            url = writeEmailPhoneURL.getText().toString();
                            Toast.makeText(AddTaskActivity.this, "URL added!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AddTaskActivity.this, "Invalid URL!", Toast.LENGTH_SHORT).show();

                        }
                    }
                }
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    //Returns true if url is valid
    public boolean isValidUrl(String url) {
        //Try creating a valid URL
        try {
            new URL(url).toURI();
            return true;
        }

        // If there was an Exception
        // while creating URL object
        catch (Exception e) {
            return false;
        }
    }
}