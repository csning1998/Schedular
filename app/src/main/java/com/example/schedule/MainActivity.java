package com.example.schedule;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new ScheduleFragment())
                    .commit();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add_course) {
            // 顯示新增課程 Dialog
            ScheduleFragment fragment = (ScheduleFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            if (fragment != null) {
                fragment.showEditDialog(-1, -1, -1); // -1 表示新增
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
