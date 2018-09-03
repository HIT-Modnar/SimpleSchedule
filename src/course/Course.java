package course;

import java.util.*;
import java.util.regex.*;

public class Course {
    public static final int TOTAL_WEEK = 20;

    public final int courseID;
    public final String courseName, teacherName, location, property;
    private final String information;
    public static int total = 0;
    
    public final int[][] weekList = new int[TOTAL_WEEK][];
    
    public Course(String courseName, String teacherName, String location, String property, String information) {
        this.courseID = total++;
        this.courseName = courseName;
        this.teacherName = teacherName;
        this.location = location;
        this.property = property;
        this.information = information;
    }

    public void exchange() {
        String patternStr1 = "(\\d+)\\-(\\d+)";

        String[] tempInformation = information.split("/");

        String weekStr = tempInformation[0]; // 记录第几周的字符串
        Integer start = 0, end = 0; // 记录起始与结束周数
        Pattern pattern = Pattern.compile(patternStr1); 
        Matcher parse = pattern.matcher(weekStr);

        if(parse.find()) {
            System.out.println("Found it"); // Test
            start = Integer.parseInt(parse.group(1));
            end = Integer.parseInt(parse.group(2));
            System.out.println(start + "  " + end); // Test
        }

        String dayStr = tempInformation[1];
        System.out.println(dayStr); // Test
        String[] tempDayList = dayStr.split("\\.");
        int[] intDayList = new int[tempDayList.length]; // 记录星期几
        for(int i = 0; i < tempDayList.length; i++) {
            System.out.println(tempDayList[i]); // Test
            intDayList[i] = Integer.parseInt(tempDayList[i]);
            System.out.println(intDayList[i]); // Test
        }

        String timeStr = tempInformation[2];
        String[] tempTimeList = timeStr.split("\\.");
        int[] intTimeList = new int[tempTimeList.length]; // 记录第几节课
        for(int i = 0; i < tempTimeList.length; i++) {
            intTimeList[i] = Integer.parseInt(tempTimeList[i]);
        }

        for(int i = start - 1; i < end; i++) {
            weekList[i] = new int[intDayList.length];
            for(int j = 0; j < intDayList.length; j++) {
                System.out.println(intTimeList[j] + "  " + intDayList[j]);
                weekList[i][j] = (intTimeList[j] - 1) * 7 + intDayList[j] - 1;
            }
        }
    }

//    public static void main(String[] args) {
//        Course course = new Course("Computer", "Teacher", "ZhengXin", "1-1/1.3/3.2");
//        course.exchange();
//        for(int i = 0; i < TOTAL_WEEK; i++) {
//            if(course.weekList[i] == null) continue;
//            for(int j = 0; j < course.weekList[i].length; j++) {
//                System.out.println("i:" + i +",j:" + j + " " + course.weekList[i][j]);
//            }
//        }
//    }
}
