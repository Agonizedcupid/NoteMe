package com.aariyan.noteme.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aariyan.noteme.Adapter.TaskAdapter;
import com.aariyan.noteme.Constant.Constant;
import com.aariyan.noteme.Database.DatabaseAdapter;
import com.aariyan.noteme.Model.TaskModel;
import com.aariyan.noteme.R;

import java.util.ArrayList;
import java.util.List;

public class TestFragment extends Fragment {
    //Instance variable of recyclerview for showing the Test task Task
    private RecyclerView testTaskRecyclerView;
    //Instance variable of database
    DatabaseAdapter databaseAdapter;
    //for taking the data list
    private List<TaskModel> listOfTestTask = new ArrayList<>();
    //warning text when no data found
    private TextView warningText;
    //progressbar will show when need time to show the data
    private ProgressBar progressBar;

    public TestFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Constant.rootView =  inflater.inflate(R.layout.fragment_test, container, false);
        databaseAdapter = new DatabaseAdapter(requireContext());
        //Instantiate UI
        initUI();

        //Loading data from database:
        loadData("Test");
        return Constant.rootView;
    }

    private void loadData(String status) {
        listOfTestTask.clear();
        //listOfTestTask = databaseAdapter.getTestTask(status);
        listOfTestTask = databaseAdapter.getOpenTask(status);
        //if have the data on list
        if (listOfTestTask.size() > 0) {
            testTaskRecyclerView.setVisibility(View.VISIBLE);
            warningText.setVisibility(View.GONE);
            TaskAdapter adapter = new TaskAdapter(requireContext(), listOfTestTask);
            testTaskRecyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else {
            //if no data found
            testTaskRecyclerView.setVisibility(View.GONE);
            warningText.setVisibility(View.VISIBLE);
        }
        progressBar.setVisibility(View.GONE);
    }

    private void initUI() {

        warningText = Constant.rootView.findViewById(R.id.warningText);
        progressBar = Constant.rootView.findViewById(R.id.progressbar);

        //instantiate the recyclerview
        testTaskRecyclerView = Constant.rootView.findViewById(R.id.testTaskRecyclerView);
        //setting the layout manager for showing item accordingly
        testTaskRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

    }
}