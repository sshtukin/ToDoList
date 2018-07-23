package com.serysht.todolist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

 import java.text.DateFormat;
 import java.util.ArrayList;
import java.util.List;

public class TitlesListFragment extends Fragment{
    public static final int REQUEST_CODE = 0;

    private RecyclerView mRecyclerView;
    private TaskAdapter mTaskAdapter;
    private TaskManager mTaskManager;
    private List<Task> mTaskList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_titles_list,
                container, false);

        mTaskManager = TaskManager.get(getActivity());

        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);


        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                Task task = mTaskList.get(viewHolder.getAdapterPosition());
                mTaskManager.deleteTask(task.getUUID());
                updateRecyclerView();
                Snackbar
                        .make(view, "Deleted", Snackbar.LENGTH_SHORT)
                        .show();
            }
        }).attachToRecyclerView(mRecyclerView);


        updateRecyclerView();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateRecyclerView();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE) {
            updateRecyclerView();
        }
    }

    public void updateRecyclerView() {
        mTaskList = mTaskManager.getTaskList();
        if (mTaskAdapter == null) {
            mTaskAdapter = new TaskAdapter();
            mTaskAdapter.setTasks(mTaskList);
            mRecyclerView.setAdapter(mTaskAdapter);
        } else {
            mTaskAdapter.setTasks(mTaskList);
            mTaskAdapter.notifyDataSetChanged();
        }
    }


    private class TaskHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView mTaskTitle;
        TextView mTaskDate;
        TextView mTaskAdditional;
        Task mTask;

        public TaskHolder(LayoutInflater layoutInflater, ViewGroup parent) {
            super(layoutInflater.inflate(R.layout.item_title, parent, false));
            mTaskTitle = itemView.findViewById(R.id.task_title);
            mTaskDate = itemView.findViewById(R.id.task_date);
            mTaskAdditional = itemView.findViewById(R.id.task_addtional_item);
            itemView.setOnClickListener(this);
        }

        public void bind(Task task){
            mTask = task;
            mTaskTitle.setText(task.getTitle());
            if (!mTask.isDateEnabled()){
                mTaskDate.setVisibility(View.INVISIBLE);
            }
            mTaskDate.setText(DateFormat.getDateInstance().format(mTask.getDate()));

            if (task.getAdditional().equals("")){
                mTaskAdditional.setVisibility(View.GONE);
            }

            mTaskAdditional.setText(task.getAdditional());
        }

        @Override
        public void onClick(View v) {
            Intent intent = OpenedListActivity.newIntent(getActivity(),
                    mTask.getUUID(), getAdapterPosition());
            startActivity(intent);
        }
    }

    private class TaskAdapter extends RecyclerView.Adapter<TaskHolder>{
        List<Task> mTaskList = new ArrayList<>();

        public void setTasks(List<Task> taskList) {
            mTaskList = taskList;
        }

        @Override
        public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new TaskHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(TaskHolder holder, int position) {
            Task mTask =  mTaskList.get(position);
            holder.bind(mTask);
        }

        @Override
        public int getItemCount() {
            return mTaskList.size();
        }
    }
}
