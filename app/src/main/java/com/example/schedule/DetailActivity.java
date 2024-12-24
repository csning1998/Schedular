// src/main/java/com/example/schedule/DetailActivity.java
package com.example.schedule;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {

    private static final String EXTRA_COURSE_ID = "extra_course_id";

    private TextView tvTitle, tvTime, tvClsNum, tvSub, tvTeacher;
    private long courseId;
    private ClassesRepository repository;

    public static void launch(Context context, long courseId) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(EXTRA_COURSE_ID, courseId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        // 初始化 Repository
        repository = new ClassesRepository(this);

        // 綁定視圖
        tvTitle = findViewById(R.id.tv_title);
        tvTime = findViewById(R.id.time);
        tvClsNum = findViewById(R.id.clsNum);
        tvSub = findViewById(R.id.sub);
        tvTeacher = findViewById(R.id.teacher);

        // 獲取課程 ID
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_COURSE_ID)) {
            courseId = intent.getLongExtra(EXTRA_COURSE_ID, -1);
            if (courseId != -1) {
                loadCourseDetails();
            } else {
                Toast.makeText(this, "課程不存在", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            Toast.makeText(this, "無法獲取課程資訊", Toast.LENGTH_SHORT).show();
            finish();
        }

        // 設置按鈕事件
        findViewById(R.id.btn_edit).setOnClickListener(v -> {
            EditCourseDialog dialog = EditCourseDialog.newInstance(-1, -1, courseId);
            dialog.show(getSupportFragmentManager(), "EditCourseDialog");
        });

        findViewById(R.id.btn_delete).setOnClickListener(v -> showDeleteConfirmation());

        findViewById(R.id.backButton).setOnClickListener(v -> finish());
    }

    private void loadCourseDetails() {
        Classes course = repository.getCourseById(courseId);
        if (course != null) {
            tvTitle.setText("課程詳情");
            tvTime.setText("周" + getWeekday(course.getDayIndex()) + " " + course.getTimeIndex());
            tvClsNum.setText(String.valueOf(course.getTimeIndex()));
            tvSub.setText(course.getName());
            tvTeacher.setText(course.getTeacher());
        } else {
            Toast.makeText(this, "課程不存在", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private String getWeekday(int dayIndex) {
        switch (dayIndex) {
            case 1: return "一";
            case 2: return "二";
            case 3: return "三";
            case 4: return "四";
            case 5: return "五";
            case 6: return "六";
            case 7: return "日";
            default: return "";
        }
    }

    private void showDeleteConfirmation() {
        new AlertDialog.Builder(this)
                .setTitle("刪除課程")
                .setMessage("確定要刪除這門課程嗎？")
                .setPositiveButton("確定", (dialog, which) -> {
                    repository.deleteCourse(courseId);
                    Toast.makeText(this, "課程已刪除", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .setNegativeButton("取消", null)
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 重新載入課程詳情，以防編輯後變更
        loadCourseDetails();
    }
}
