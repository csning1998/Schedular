package com.example.schedule;

public class Utils {

    /**
     * 將「星期一/星期二...」轉成 1~7
     */
    public static int dayStringToIndex(String dayStr) {
        switch (dayStr) {
            case "星期一": return 1;
            case "星期二": return 2;
            case "星期三": return 3;
            case "星期四": return 4;
            case "星期五": return 5;
            case "星期六": return 6;
            case "星期日": return 7;
        }
        return 0;
    }

    /**
     * 將 1~7 轉成「星期一/星期二...」
     */
    public static String dayIndexToString(int dayIndex) {
        switch (dayIndex) {
            case 1: return "星期一";
            case 2: return "星期二";
            case 3: return "星期三";
            case 4: return "星期四";
            case 5: return "星期五";
            case 6: return "星期六";
            case 7: return "星期日";
        }
        return "未知";
    }

    /**
     * 將 timeIndex -> "第 X 節"
     */
    public static String timeIndexToString(int timeIndex) {
        return "第 " + timeIndex + " 節";
    }
}
