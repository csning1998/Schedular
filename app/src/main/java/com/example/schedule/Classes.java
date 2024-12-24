// src/main/java/com/example/schedule/Classes.java
package com.example.schedule;

public class Classes {
    private long id;
    private String name;
    private String teacher;
    private int dayIndex; // 1 ~ 7 (星期一 ~ 星期日)
    private int timeIndex; // 1 ~ 5 (節次)

    // Getters and Setters

    public long getId() {
        return id;
    }

    public void setId(long id) { this.id = id; }

    public String getName() {
        return name;
    }

    public void setName(String name) { this.name = name; }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) { this.teacher = teacher; }

    public int getDayIndex() {
        return dayIndex;
    }

    public void setDayIndex(int dayIndex) { this.dayIndex = dayIndex; }

    public int getTimeIndex() {
        return timeIndex;
    }

    public void setTimeIndex(int timeIndex) { this.timeIndex = timeIndex; }
}
