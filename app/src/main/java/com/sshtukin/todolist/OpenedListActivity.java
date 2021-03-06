package com.sshtukin.todolist;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sshtukin.todolist.fragments.OpenedListFragment;

import java.util.UUID;

public class OpenedListActivity extends AppCompatActivity {
    public static final String EXTRA_TASK_ID = "task_id";
    public static final String EXTRA_POSITION = "position";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opened_list);

        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_TASK_ID);
        int position = (int) getIntent().getSerializableExtra(EXTRA_POSITION);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.frame_layout);

        if (fragment == null) {
            fragment = OpenedListFragment.newInstance(crimeId, position);
            fragmentManager.beginTransaction()
                    .add(R.id.frame_layout, fragment)
                    .commit();
        }

    }

    public static Intent newIntent(Context context, UUID taskId, int position) {
        Intent intent = new Intent(context, OpenedListActivity.class);
        intent.putExtra(EXTRA_TASK_ID, taskId);
        intent.putExtra(EXTRA_POSITION, position);
        return intent;
    }


}
