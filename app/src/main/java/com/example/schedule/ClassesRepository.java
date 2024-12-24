// src/main/java/com/example/schedule/ClassesRepository.java
package com.example.schedule;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ClassesRepository {

    private DBHelper dbHelper;

    public ClassesRepository(Context context) {
        dbHelper = new DBHelper(context);
    }

    // 插入課程
    public long insertCourse(Classes course) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_NAME, course.getName());
        values.put(DBHelper.COLUMN_TEACHER, course.getTeacher());
        values.put(DBHelper.COLUMN_DAY_INDEX, course.getDayIndex());
        values.put(DBHelper.COLUMN_TIME_INDEX, course.getTimeIndex());

        long id = db.insert(DBHelper.TABLE_CLASSES, null, values);
        db.close();
        return id;
    }

    // 更新課程
    public int updateCourse(Classes course) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_NAME, course.getName());
        values.put(DBHelper.COLUMN_TEACHER, course.getTeacher());
        values.put(DBHelper.COLUMN_DAY_INDEX, course.getDayIndex());
        values.put(DBHelper.COLUMN_TIME_INDEX, course.getTimeIndex());

        int rows = db.update(DBHelper.TABLE_CLASSES, values,
                DBHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(course.getId())});
        db.close();
        return rows;
    }

    // 刪除課程
    public void deleteCourse(long courseId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DBHelper.TABLE_CLASSES,
                DBHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(courseId)});
        db.close();
    }

    // 獲取單一課程
    public Classes getCourseById(long courseId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DBHelper.TABLE_CLASSES,
                null,
                DBHelper.COLUMN_ID + " = ?",
                new String[]{String.valueOf(courseId)},
                null, null, null);

        Classes course = null;
        if (cursor != null && cursor.moveToFirst()) {
            course = new Classes();
            course.setId(cursor.getLong(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ID)));
            course.setName(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_NAME)));
            course.setTeacher(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_TEACHER)));
            course.setDayIndex(cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_DAY_INDEX)));
            course.setTimeIndex(cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_TIME_INDEX)));
            cursor.close();
        }
        db.close();
        return course;
    }

    // 獲取所有課程
    public List<Classes> getAllCourses() {
        List<Classes> courseList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DBHelper.TABLE_CLASSES,
                null,
                null,
                null,
                null, null, DBHelper.COLUMN_DAY_INDEX + " ASC, " + DBHelper.COLUMN_TIME_INDEX + " ASC");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Classes course = new Classes();
                course.setId(cursor.getLong(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ID)));
                course.setName(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_NAME)));
                course.setTeacher(cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_TEACHER)));
                course.setDayIndex(cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_DAY_INDEX)));
                course.setTimeIndex(cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_TIME_INDEX)));
                courseList.add(course);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return courseList;
    }
}
