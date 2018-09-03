package gui;

import java.util.*;

import java.io.*;
import java.awt.*;
import java.awt.Image;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.ImageIO;

import gui.SelectWeekDialog;
import gui.CourseInformationDialog;
import course.Course;

public class Schedule extends JFrame implements ActionListener {
    private int currentWeekNum; // 当前周数
    private int totalWeekNum; // 学期内总周数
    private CardLayout cardLayout = new CardLayout(); // 卡片布局
    private JPanel listCourse = new JPanel(); // 卡片布局载体
    private JButton preWeek, nextWeek; // 增加了监听器的按钮
    private JButton currentWeek; // 显示当前周数的按钮

    private java.util.List<Course> courseList;
    private JButton wholeCoursesButton[][];

    public Schedule(String windowName, java.util.List<Course> courseList) { // TODO
        super(windowName);
        this.courseList = courseList;

        currentWeekNum = 1;
        totalWeekNum = 17;
        wholeCoursesButton = new JButton[totalWeekNum][];

        Container con = this.getContentPane();
        con.setLayout(new BorderLayout(5, 5));

        currentWeek = new JButton("No." + currentWeekNum + " week");
        currentWeek.setFont(new Font("翩翩体", Font.BOLD, 20));
        currentWeek.addActionListener(this);

        preWeek = new JButton("<< Last Week");
        nextWeek = new JButton("Next Week >>");
        preWeek.addActionListener(this); // 为按钮添加监听器
        nextWeek.addActionListener(this); // 为按钮添加监听器

        JPanel titlePane = new JPanel(); titlePane.setLayout(new GridLayout(1, 3));
        titlePane.add(preWeek); titlePane.add(currentWeek); titlePane.add(nextWeek);

        JPanel leftPane = new JPanel(); leftPane.setLayout(new GridLayout(7, 1));
        JPanel rightPane = new JPanel(); rightPane.setLayout(new GridLayout(7, 1));

        leftPane.add(new JButton("Course Number"));
        for(int i = 1; i <= 6; i++) {
            leftPane.add(new JButton("No." + (2 * i -1) + "-" + (2 * i)));
        }
        rightPane.add(new JButton("Course Time"));
        rightPane.add(new JButton("8:00 - 9:45"));
        rightPane.add(new JButton("10:00 - 11:45"));
        rightPane.add(new JButton("13:45 - 15:30"));
        rightPane.add(new JButton("15:45 - 17:30"));
        rightPane.add(new JButton("18:30 - 20:15"));
        rightPane.add(new JButton("20:30 - 22;15"));

        listCourse.setVisible(true);
        listCourse.setLayout(cardLayout); // 翻页布局

        JPanel mainPane; // 中央主面板，承载星期标签、课程按钮
        for(int i = 1; i <= totalWeekNum; i++) {
            mainPane = new JPanel(); 
            mainPane.setVisible(true);
            mainPane.setLayout(new GridLayout(7, 7));
            mainPane.add(new JLabel("Mon", JLabel.CENTER));
            mainPane.add(new JLabel("Tue", JLabel.CENTER));
            mainPane.add(new JLabel("Wed", JLabel.CENTER));
            mainPane.add(new JLabel("Thu", JLabel.CENTER));
            mainPane.add(new JLabel("Fri", JLabel.CENTER));
            mainPane.add(new JLabel("Sat", JLabel.CENTER));
            mainPane.add(new JLabel("Sum", JLabel.CENTER));

//            for(int j = 0; j < 42; j++) {
//                String str1 = "Week" + i;
//                String str2 = "Course." + (j + 1);
//                String str = "<html>" + str1 + "<br>" + str2 + "</html>";
//                mainPane.add(new JButton(str));
//            }

            JButton[] buttonList = setCourse(courseList, i - 1);
            wholeCoursesButton[i-1] = buttonList;
            for(int j = 0; j < buttonList.length; j++) {
                mainPane.add(buttonList[j]);
            }

            System.out.println(i);
            listCourse.add(mainPane);
        }

        con.add(titlePane, BorderLayout.NORTH);
        con.add(leftPane, BorderLayout.WEST);
        con.add(rightPane, BorderLayout.EAST);
        con.add(listCourse, BorderLayout.CENTER);

        this.setVisible(true); this.pack();
    }

    private JButton[] setCourse(java.util.List<Course> courseList, int tempCurrentWeekNum) { // TODO
        JButton[] retButton = new JButton[42];
        for(Course course : courseList) {
            if(course.weekList[tempCurrentWeekNum] != null) {
                System.out.println("Here! " + tempCurrentWeekNum); // Test
                for(int i = 0; i < course.weekList[tempCurrentWeekNum].length; i++) {
                    StringBuilder strbud = new StringBuilder("<html>");
                    strbud.append(course.courseName);
                    strbud.append("<br>");
                    strbud.append(course.teacherName);
                    strbud.append("</html>");
                    retButton[course.weekList[tempCurrentWeekNum][i]] = new JButton(strbud.toString());
                    retButton[course.weekList[tempCurrentWeekNum][i]].addActionListener(this);
                }
            }
        }
        for(int i = 0; i < 42; i++) {
            if(retButton[i] == null) {
                retButton[i] = new JButton();
            }
        }
        return retButton;
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == preWeek) {
            System.out.println("Click Left!");
            currentWeekNum--;
            if(currentWeekNum <= 0) currentWeekNum += totalWeekNum;
            currentWeek.setText("No." + currentWeekNum + " week");
            cardLayout.previous(listCourse);
        } else if(e.getSource() == nextWeek) {
            System.out.println("Click Right!");
            currentWeekNum++;
            if(currentWeekNum > totalWeekNum) currentWeekNum -= totalWeekNum;
            currentWeek.setText("No." + currentWeekNum + " week");
            cardLayout.next(listCourse);
        } else if(e.getSource() == currentWeek) {
            System.out.println("Click CurrentWeek Button!");
            SelectWeekDialog dialog = new SelectWeekDialog(this, "Select Week", totalWeekNum);
            dialog.setLocation(450, 150);
            dialog.setSize(500, 500);
            dialog.setVisible(true);

            int targetWeekNum = dialog.getSelectWeek();
            System.out.println("Recieved " + targetWeekNum); // Test
            if(targetWeekNum > currentWeekNum) {
                for(int i = 0; i < targetWeekNum - currentWeekNum; i++) {
                    cardLayout.next(listCourse);
                }
            } else {
                for(int i = 0; i < currentWeekNum - targetWeekNum; i++) {
                    cardLayout.previous(listCourse);
                }
            }
            currentWeekNum = targetWeekNum;
            currentWeek.setText("No." + currentWeekNum + " week");
        } else {
            System.out.println("Click someone course!");
            for(JButton button : wholeCoursesButton[currentWeekNum-1]) {
                if(e.getSource() == button) {
                    System.out.println("Catch it!");
                    for(Course course : courseList) {
                        if(button.getText().contains(course.courseName)) {
                            CourseInformationDialog dialog = new CourseInformationDialog(this, "Course Information", course);
                            dialog.setLocation(450, 150);
                            dialog.setSize(400, 400);
                            dialog.setVisible(true);
                            break;
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        java.util.List<Course> courseList = new ArrayList<>(); // TODO
        courseList.add(new Course("机器学习", "刘扬", "正心32", "专业限选", "1-9/2.4/4.4"));
        courseList.add(new Course("西方美术导论", "卞秉利", "正心32", "素质选修", "2-8/2.2/5.6"));
        courseList.add(new Course("自然语言处理", "孙承杰,杨沐昀", "致知11", "专业限选", "1-17/1.3/1.1/"));
        courseList.add(new Course("习近平新时代中国特色社会主义思想专题辅导", "巩茹敏,解保军,由田,闫金红", "正心42", "必修", "1-17/2/1"));
        courseList.add(new Course("计算机网络", "刘亚维", "正心310", "专业限选", "1-11/2.4/2.1"));
        courseList.add(new Course("计算机网络(实验)", "刘亚维", "格物213", "专业限选(实验)", "8-11/3/3"));
        courseList.add(new Course("计算机数学基础", "任世军", "正心224", "外专业课程", "3-15/2.4/5.5"));
        
        for(Course temp : courseList) 
            temp.exchange();

        Schedule mainWindow = new Schedule("StevenShen's Schedule", courseList);
        mainWindow.setSize(1000, 1000);
        mainWindow.setLocation(200, 10);
    }
}
