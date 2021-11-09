package com.aariyan.noteme;

import static com.aariyan.noteme.Constant.Constant.listOfTask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.aariyan.noteme.Activity.AddTaskActivity;
import com.aariyan.noteme.Constant.Constant;
import com.aariyan.noteme.Fragment.DoneFragment;
import com.aariyan.noteme.Fragment.InProgressFragment;
import com.aariyan.noteme.Fragment.OpenFragment;
import com.aariyan.noteme.Fragment.TestFragment;
import com.aariyan.noteme.Model.TaskModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private TextView topHeadingTitle;
    private FloatingActionButton addTaskBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        //Instantiate the UI:
        initUI();

        if (savedInstanceState == null) {
            //setting the default fragment when app start first time
            setFragmentById(new OpenFragment());
            //setting the specified title for each different fragment
            topHeadingTitle.setText(getResources().getString(R.string.open_task_list));
        }

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("headerTitle", topHeadingTitle.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        topHeadingTitle.setText(savedInstanceState.getString("headerTitle"));
    }

    private void initUI() {

        addTaskBtn = findViewById(R.id.addTaskFloatingBtn);
        addTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddTaskActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        //Toolbar text
        topHeadingTitle = findViewById(R.id.topHeadingTitle);

        //BottomNavigation View
        bottomNavigationView = findViewById(R.id.navigationBar);
        //setting the icon tint list to null for showing the exact color of ICON
        bottomNavigationView.setItemIconTintList(null);
        //Click even for all navigation menu

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_open:
                        //setting the specified fragment for each menu
                        setFragmentById(new OpenFragment());
                        //setting the specified title for each different fragment
                        topHeadingTitle.setText(getResources().getString(R.string.open_task_list));
                        break;

                    case R.id.nav_inProgress:
                        //setting the specified fragment for each menu
                        setFragmentById(new InProgressFragment());
                        //setting the specified title for each different fragment
                        topHeadingTitle.setText(getResources().getString(R.string.in_progress_task_list));
                        break;

                    case R.id.nav_test:
                        //setting the specified fragment for each menu
                        setFragmentById(new TestFragment());
                        //setting the specified title for each different fragment
                        topHeadingTitle.setText(getResources().getString(R.string.test_task_list));
                        break;

                    case R.id.nav_done:
                        //setting the specified fragment for each menu
                        setFragmentById(new DoneFragment());
                        //setting the specified title for each different fragment
                        topHeadingTitle.setText(getResources().getString(R.string.done_task_list));
                        break;
                }
                return true;
            }
        });


    }

    private void setFragmentById(Fragment particularFragment) {
        //setting the fragment in the fragment container (FrameLayout)
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, particularFragment).commit();
    }
}