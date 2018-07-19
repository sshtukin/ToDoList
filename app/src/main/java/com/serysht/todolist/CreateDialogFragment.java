package com.serysht.todolist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class CreateDialogFragment extends DialogFragment {
    private EditText mTitle;
    private EditText mAdditional;
    private Button mSaveButton;
    private TaskManager mTaskManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_create,
                container, false);
        mTitle = (EditText) view.findViewById(R.id.task_title);
        mAdditional = (EditText) view.findViewById(R.id.task_additional);
        mSaveButton = (Button) view.findViewById(R.id.save_button);
        mTaskManager = TaskManager.get(getContext());

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Task task = new Task();
                task.setTitle(mTitle.getText().toString());
                task.setAdditional(mAdditional.getText().toString());
                mTaskManager.addTask(task);
               dismiss();
            }
        });


        return view;
    }
    //    @NonNull
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        View view = LayoutInflater.from(getActivity())
//                .inflate(R.layout.dialog_create, null);
//
//        return new AlertDialog.Builder(getActivity())
//                .setView(view)
//                .setTitle("lol")
//                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
////                        int year = mDatePicker.getYear();
////                        int month = mDatePicker.getMonth();
////                        int day = mDatePicker.getDayOfMonth();
////                        Date date = new GregorianCalendar(year, month, day).
////                                getTime();
////                        sendResult(Activity.RESULT_OK, date);
//                    }
//                })
//                .create();
//
//    }
}
