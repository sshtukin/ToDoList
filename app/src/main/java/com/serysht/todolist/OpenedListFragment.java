package com.serysht.todolist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class OpenedListFragment extends Fragment {
    private static final String TASK_ID = "task_id";
    private static final String TASK_POSITION = "position";

    private EditText mTitle;
    private EditText mAdditional;
    private Button mButton;
    private CheckBox mCheckBox;
    private TaskManager mTaskManager;
    private RecyclerView mRecyclerView;
    private Task mTask;
    private TaskAdapter mTaskAdapter;
    private int mPosition;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UUID taskId = (UUID) getArguments().getSerializable(TASK_ID);
        mPosition = (int) getArguments().getSerializable(TASK_POSITION);

        mTaskManager = TaskManager.get(getActivity());
        mTask = mTaskManager.getTaskById(taskId);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_opened_list, container, false);


        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false);
        mLinearLayoutManager.scrollToPositionWithOffset(mPosition, 0);

        mRecyclerView = view.findViewById(R.id.recycler_view_opened);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(mRecyclerView);

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
        if (requestCode == 1) {
            Date date = (Date) data
                    .getSerializableExtra("lol");
            mTask.setDate(date);
//            mDateButton.setText(mCrime.getDate().toString());
        }
    }

    public void updateRecyclerView() {
        List<Task> mTaskList = mTaskManager.getTaskList();

        if (mTaskAdapter == null) {
            mTaskAdapter = new TaskAdapter();
            mTaskAdapter.setTasks(mTaskList);
            mRecyclerView.setAdapter(mTaskAdapter);
        } else {
            mTaskAdapter.setTasks(mTaskList);
            mTaskAdapter.notifyDataSetChanged();
        }
    }


    private class TaskHolder extends RecyclerView.ViewHolder{
        private EditText mTitle;
        private EditText mAdditional;
        private CheckBox mCheckBox;
        private Button mSaveButton;
        private Button mDateButton;

        public TaskHolder(LayoutInflater layoutInflater, ViewGroup parent) {
            super(layoutInflater.inflate(R.layout.opened_task_item, parent, false));
            mTitle = (EditText) itemView.findViewById(R.id.task_title);
            mAdditional = (EditText) itemView.findViewById(R.id.task_additional);
            mCheckBox = (CheckBox) itemView.findViewById(R.id.checkBox);
            mButton = (Button) itemView.findViewById(R.id.save_button);

            mDateButton = (Button) itemView.findViewById(R.id.date_button);
            mDateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager manager = getFragmentManager();
                    DatePickerFragment dialog = DatePickerFragment.newInstance(new Date());
                    dialog.setTargetFragment(OpenedListFragment.this, 1);
                    dialog.show(manager, "LOL");
                    updateRecyclerView();
                }
            });

        }

        public void bind(Task task){
            mTask = task;
            mTitle.setText(task.getTitle());
            mAdditional.setText(task.getAdditional());
            mCheckBox.setChecked(mTask.isDone());
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

    public static OpenedListFragment newInstance(UUID taskId, int position) {
        Bundle args = new Bundle();
        args.putSerializable(TASK_ID, taskId);
        args.putInt(TASK_POSITION, position);
        OpenedListFragment fragment = new OpenedListFragment();
        fragment.setArguments(args);
        return fragment;
    }
}