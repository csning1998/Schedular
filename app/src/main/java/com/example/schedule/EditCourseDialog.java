package com.example.schedule;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/**
 * DialogFragment 用來編輯/新增課程
 */
public class EditCourseDialog extends DialogFragment {

    private static final String ARG_DAY_INDEX = "arg_day_index";
    private static final String ARG_TIME_INDEX = "arg_time_index";
    private static final String ARG_COURSE_ID = "arg_course_id";

    private EditText etName, etTeacher;
    private Spinner spinnerDay, spinnerTime;
    private Button btnSave, btnCancel;
    private ClassesRepository repository;

    private int dayIndex, timeIndex;
    private long courseId;  // -1 表示新增

    public static EditCourseDialog newInstance(int dayIndex, int timeIndex, long courseId) {
        EditCourseDialog dialog = new EditCourseDialog();
        Bundle args = new Bundle();
        args.putInt(ARG_DAY_INDEX, dayIndex);
        args.putInt(ARG_TIME_INDEX, timeIndex);
        args.putLong(ARG_COURSE_ID, courseId);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // 無標題
        getDialog().requestWindowFeature(STYLE_NO_TITLE);

        return inflater.inflate(R.layout.dialog_edit_course, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        repository = new ClassesRepository(requireContext());

        // 綁定視圖
        etName = view.findViewById(R.id.et_name);
        etTeacher = view.findViewById(R.id.et_teacher);
        spinnerDay = view.findViewById(R.id.et_day);
        spinnerTime = view.findViewById(R.id.et_time);
        btnSave = view.findViewById(R.id.btn_save);
        btnCancel = view.findViewById(R.id.btn_cancel);

        // 獲取參數
        if (getArguments() != null) {
            dayIndex = getArguments().getInt(ARG_DAY_INDEX, -1);
            timeIndex = getArguments().getInt(ARG_TIME_INDEX, -1);
            courseId = getArguments().getLong(ARG_COURSE_ID, -1);
        }

        // 如果是編輯模式
        if (courseId != -1) {
            Classes c = repository.getCourseById(courseId);
            if (c != null) {
                etName.setText(c.getName());
                etTeacher.setText(c.getTeacher());
                spinnerDay.setSelection(c.getDayIndex());
                spinnerTime.setSelection(c.getTimeIndex());
            }
        } else {
            // 新增模式，若有預設 dayIndex/timeIndex
            if (dayIndex != -1) spinnerDay.setSelection(dayIndex);
            if (timeIndex != -1) spinnerTime.setSelection(timeIndex);
        }

        // 設置按鈕事件
        btnSave.setOnClickListener(v -> onSaveClicked());
        btnCancel.setOnClickListener(v -> dismiss());
    }

    private void onSaveClicked() {
        String name = etName.getText().toString().trim();
        String teacher = etTeacher.getText().toString().trim();

        int selectedDay = spinnerDay.getSelectedItemPosition();
        int selectedTime = spinnerTime.getSelectedItemPosition();

        if (TextUtils.isEmpty(name)
                || TextUtils.isEmpty(teacher)
                || selectedDay == 0
                || selectedTime == 0) {
            Toast.makeText(requireContext(), "請填寫完整資訊", Toast.LENGTH_SHORT).show();
            return;
        }

        Classes c = new Classes();
        c.setName(name);
        c.setTeacher(teacher);
        c.setDayIndex(selectedDay);
        c.setTimeIndex(selectedTime);

        if (courseId != -1) {
            // 編輯
            c.setId(courseId);
            repository.updateCourse(c);
            Toast.makeText(requireContext(), "課程已更新", Toast.LENGTH_SHORT).show();
        } else {
            // 新增
            repository.insertCourse(c);
            Toast.makeText(requireContext(), "課程已新增", Toast.LENGTH_SHORT).show();
        }
        dismiss();
    }
}
