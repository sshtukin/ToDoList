package com.serysht.todolist;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.GregorianCalendar;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import java.util.Date;

public class DatePickerFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_date, null);
//
//        Date date = (Date) getArguments().getSerializable(ARG_DATE);
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH);
//        int day = calendar.get(Calendar.DAY_OF_MONTH);

//
//         DatePicker mDatePicker = (DatePicker) view.findViewById(R.id.dialog_date_picker);
//        mDatePicker.init(year, month, day, null);
        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle("lol")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        int year = mDatePicker.getYear();
//                        int month = mDatePicker.getMonth();
//                        int day = mDatePicker.getDayOfMonth();
//                        Date date = new GregorianCalendar(year, month, day).
//                                getTime();
//                        sendResult(Activity.RESULT_OK, date);
                    }
                })
                .create();
    }

    public static DatePickerFragment newInstance(Date date){
        Bundle args = new Bundle();
        args.putSerializable("lol", date);

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void sendResult(int resultCode, Date date){
        if(getTargetFragment() == null){
            return;
        }
        Intent intent = new Intent();
        intent.putExtra("lol", date);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
