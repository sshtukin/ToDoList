package com.serysht.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TitlesListFragment extends Fragment{
    private RecyclerView mRecyclerView;
    private TaskAdapter mTaskAdapter;
    private TaskManager mTaskManager;
    private List<Task> mTaskList;
    private FloatingActionButton mFloatingActionButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_titles_list,
                container, false);

        mTaskManager = TaskManager.get(getActivity());

        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setHasFixedSize(true);

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
            }
        }).attachToRecyclerView(mRecyclerView);

        mFloatingActionButton = view.findViewById(R.id.floatingActionButton);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                CreateDialogFragment dialog = new CreateDialogFragment();
                dialog.show(manager, "LOL");
                //updateRecyclerView();
//
//                DatePickerFragment dialog = DatePickerFragment.newInstance(new Date());
//                dialog.setTargetFragment(OpenedListFragment.this, 1);
//                dialog.show(manager, "LOL");
//                updateRecyclerView();
            }
        });


        updateRecyclerView();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateRecyclerView();
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
        Task mTask;

        public TaskHolder(LayoutInflater layoutInflater, ViewGroup parent) {
            super(layoutInflater.inflate(R.layout.title_item, parent, false));
            mTaskTitle = itemView.findViewById(R.id.task_title);
            itemView.setOnClickListener(this);
        }

        public void bind(Task task){
            mTask = task;
            mTaskTitle.setText(task.getTitle());
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
