package com.serysht.todolist;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
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
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class CreateDialogFragment extends DialogFragment {
    public static final String TAG = "CreateDialogFragment";
    public static final String EXTRA_DATE = "date";
    private static final int REQUEST_CODE = 1;

    private EditText mTitle;
    private EditText mAdditional;
    private Button mSaveButton;
    private Button mDateButton;
    private Task mTask;
    private TaskManager mTaskManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_create,
                container, false);

        mTitle = view.findViewById(R.id.dialog_create_task_title);
        mAdditional = view.findViewById(R.id.dialog_create_task_additional);
        mSaveButton = view.findViewById(R.id.dialog_create_save_button);
        mDateButton = view.findViewById(R.id.dialog_create_date_opened_button);

        mTaskManager = TaskManager.get(getContext());
        mTask = new Task();

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mTitle.getText().toString().equals("")){

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
                else {
                    Toast.makeText(getContext(),
                            R.string.missing_title_warning,
                            Toast.LENGTH_SHORT).show();
                }

                if (mTask.isDateEnabled()) {
                    AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

                    Calendar calendar = Calendar.getInstance();
                    Date date = new Date();
                    date.setTime(mTask.getDate().getTime()+36000000);//10 am
                    calendar.setTime(date);

                    Intent intent = new Intent("com.serysht.DISPLAY_NOTIFICATION");

                    PendingIntent broadcast = PendingIntent.getBroadcast(getContext(), 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), broadcast);
                }
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
            mTask.setDateEnabled(true);

            mDateButton.setText(DateFormat.getDateInstance().format(mTask.getDate()));
        }
    }
}
