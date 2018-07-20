package com.serysht.todolist;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Date;

public class CreateDialogFragment extends DialogFragment {
    public static final String TAG = "CreateDialogFragment";
    private static final int REQUEST_CODE = 1;
    public static final String EXTRA_DATE = "date";

    private EditText mTitle;
    private EditText mAdditional;
    private Button mSaveButton;
    private TaskManager mTaskManager;
    private ImageButton mDateButton;
    private Task mTask;
    private TextView mDate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_create,
                container, false);
        mTitle = view.findViewById(R.id.task_title);
        mAdditional = view.findViewById(R.id.task_additional);
        mSaveButton = view.findViewById(R.id.save_button);
        mDateButton = view.findViewById(R.id.date_opened_button);
        mDate = view.findViewById(R.id.date_text_view);
        mTaskManager = TaskManager.get(getContext());
        mTask = new Task();

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mTask.setTitle(mTitle.getText().toString());
                mTask.setAdditional(mAdditional.getText().toString());
                mTaskManager.addTask(mTask);

                Intent intent = new Intent();
                if(getTargetFragment() == null){
                    dismiss();
                }
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                dismiss();
            }
        });

        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(new Date());
                dialog.setTargetFragment(CreateDialogFragment.this, REQUEST_CODE);
                dialog.show(manager, DatePickerFragment.TAG);
            }
        });

        return view;
    }

    public static CreateDialogFragment newInstance(){
        CreateDialogFragment fragment = new CreateDialogFragment();
        return fragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE) {
            Date date = (Date) data
                    .getSerializableExtra(EXTRA_DATE);

            mTask.setDate(date);
            mTaskManager.updateTask(mTask);
            mDate.setText(date.toString());
        }
    }
}
