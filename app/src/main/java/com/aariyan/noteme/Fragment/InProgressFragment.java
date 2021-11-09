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


public class InProgressFragment extends Fragment {
    //Instance variable of recyclerview for showing the In-Progress Task
    private RecyclerView inProgressTaskRecyclerView;
    //Instance variable of database
    DatabaseAdapter databaseAdapter;
    //for taking the data list
    private List<TaskModel> listOfInProgressTask = new ArrayList<>();
    //warning text when no data found
    private TextView warningText;
    //progressbar will show when need time to show the data
    private ProgressBar progressBar;


    public InProgressFragment() {
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
        Constant.rootView = inflater.inflate(R.layout.fragment_in_progress, container, false);
        databaseAdapter = new DatabaseAdapter(requireContext());
        //Instantiate UI
        initUI();

        //Loading data from database:
        loadData("In-Progress");
        return Constant.rootView;
    }

    private void loadData(String status) {
        listOfInProgressTask.clear();
        //listOfInProgressTask = databaseAdapter.getInProgressTask(status);
        listOfInProgressTask = databaseAdapter.getOpenTask(status);
        //if have the data on list
        if (listOfInProgressTask.size() > 0) {
            inProgressTaskRecyclerView.setVisibility(View.VISIBLE);
            warningText.setVisibility(View.GONE);
            TaskAdapter adapter = new TaskAdapter(requireContext(), listOfInProgressTask);
            inProgressTaskRecyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else {
            //if no data found
            inProgressTaskRecyclerView.setVisibility(View.GONE);
            warningText.setVisibility(View.VISIBLE);
        }
        progressBar.setVisibility(View.GONE);
    }

    private void initUI() {

        warningText = Constant.rootView.findViewById(R.id.warningText);
        progressBar = Constant.rootView.findViewById(R.id.progressbar);

        //instantiate the recyclerview
        inProgressTaskRecyclerView = Constant.rootView.findViewById(R.id.inProgressTaskRecyclerView);
        //setting the layout manager for showing item accordingly
        inProgressTaskRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

    }
}