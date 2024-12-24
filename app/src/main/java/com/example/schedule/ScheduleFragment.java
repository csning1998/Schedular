// src/main/java/com/example/schedule/ScheduleFragment.java
package com.example.schedule;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ScheduleFragment extends Fragment {

    private ClassesRepository repository;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // 載入 fragment_schedule.xml
        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        repository = new ClassesRepository(requireContext());

        // 載入課程資料並顯示
        loadCourses(view);

        // 設置 FloatingActionButton 點擊事件
        FloatingActionButton fab = view.findViewById(R.id.fab_add_course);
        fab.setOnClickListener(v -> showEditDialog(-1, -1, -1)); // -1 表示新增
    }

    private void loadCourses(View root) {
        List<Classes> allCourses = repository.getAllCourses();

        for (Classes course : allCourses) {
            int dayIndex = course.getDayIndex(); // 1 ~ 7
            int timeIndex = course.getTimeIndex(); // 1 ~ 5

            // 根據 dayIndex 和 timeIndex 找到對應的 TextView
            String dayContainerId = "day" + dayIndex + "_container";
            String timeTextViewId = "day" + dayIndex + "_time" + timeIndex;

            int dayContainerResId = getResources().getIdentifier(dayContainerId, "id", requireContext().getPackageName());
            int timeTextViewResId = getResources().getIdentifier(timeTextViewId, "id", requireContext().getPackageName());

            if (dayContainerResId == 0 || timeTextViewResId == 0) {
                continue; // 無此 ID，跳過
            }

            View dayContainer = root.findViewById(dayContainerResId);
            if (dayContainer == null) continue;

            TextView timeTextView = dayContainer.findViewById(timeTextViewResId);
            if (timeTextView == null) continue;

            // 設置課程名稱和老師
            timeTextView.setText(course.getName() + "\n" + course.getTeacher());

            // 設置背景顏色區分不同課程
            timeTextView.setBackgroundColor(getColorByCourseId(course.getId()));
            timeTextView.setTextColor(Color.WHITE);

            // 設置點擊事件，編輯或查看課程詳情
            timeTextView.setOnClickListener(v -> {
                // 顯示課程詳情
                DetailActivity.launch(requireContext(), course.getId());
            });
        }
    }

    // 根據課程 ID 返回不同的顏色，僅作示範
    private int getColorByCourseId(long courseId) {
        switch ((int) (courseId % 5)) {
            case 0: return Color.parseColor("#FF5722"); // 橙色
            case 1: return Color.parseColor("#4CAF50"); // 綠色
            case 2: return Color.parseColor("#2196F3"); // 藍色
            case 3: return Color.parseColor("#9C27B0"); // 紫色
            default: return Color.parseColor("#FF9800"); // 淺橙
        }
    }

    void showEditDialog(int dayIndex, int timeIndex, long courseId) {
        EditCourseDialog dialog = EditCourseDialog.newInstance(dayIndex, timeIndex, courseId);
        dialog.setTargetFragment(this, 0); // 設置目標 Fragment
        dialog.show(getParentFragmentManager(), "EditCourseDialog");
    }

    @Override
    public void onResume() {
        super.onResume();
        // 每次回到此 Fragment，刷新課程顯示
        View root = getView();
        if (root != null) {
            // 清除現有課程顯示
            clearAllCourses(root);
            // 重新載入課程
            loadCourses(root);
        }
    }

    private void clearAllCourses(View root) {
        for (int dayIndex = 1; dayIndex <= 7; dayIndex++) {
            String dayContainerId = "day" + dayIndex + "_container";
            int dayContainerResId = getResources().getIdentifier(dayContainerId, "id", requireContext().getPackageName());

            if (dayContainerResId == 0) continue;

            View dayContainer = root.findViewById(dayContainerResId);
            if (dayContainer == null) continue;

            for (int timeIndex = 1; timeIndex <= 5; timeIndex++) {
                String timeTextViewId = "day" + dayIndex + "_time" + timeIndex;
                int timeTextViewResId = getResources().getIdentifier(timeTextViewId, "id", requireContext().getPackageName());

                if (timeTextViewResId == 0) continue;

                TextView timeTextView = dayContainer.findViewById(timeTextViewResId);
                if (timeTextView == null) continue;

                timeTextView.setText("(空)");
                timeTextView.setBackgroundColor(Color.parseColor("#E0F0F0"));
                timeTextView.setTextColor(Color.BLACK);

                // 設置新增課程的點擊事件
                final int finalDayIndex = dayIndex;
                final int finalTimeIndex = timeIndex;
                timeTextView.setOnClickListener(v -> showEditDialog(finalDayIndex, finalTimeIndex, -1));
            }
        }
    }
}
