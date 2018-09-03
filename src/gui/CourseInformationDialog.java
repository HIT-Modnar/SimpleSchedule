package gui;

import java.util.*;
import course.Course;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class CourseInformationDialog extends JDialog implements ActionListener {
    JButton Done; // "OK"按钮
    JPanel southPane; // 南部面板

    CourseInformationDialog(JFrame F, String s, Course course) {
        super(F, s, true);
        Container con = this.getContentPane();
        con.setLayout(new BorderLayout(5, 5));

        StringBuilder strBud = new StringBuilder("<html>课程:");
        strBud.append(course.courseName);
        strBud.append("<br>教师:");
        strBud.append(course.teacherName);
        strBud.append("<br>地点:");
        strBud.append(course.location);
        strBud.append("<br>性质:");
        strBud.append(course.property);
        strBud.append("</html>");

        southPane = new JPanel();
        southPane.setLayout(new GridLayout(1, 7));
        Done = new JButton("OK");
        Done.addActionListener(this);

        for(int i = 0; i < 3; i++) southPane.add(new JLabel());
        southPane.add(Done);
        for(int i = 0; i < 3; i++) southPane.add(new JLabel());

        JLabel label =  new JLabel(strBud.toString(), JLabel.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, 25));
        con.add(label, BorderLayout.CENTER);
        con.add(southPane, BorderLayout.SOUTH);
        con.setVisible(true); this.pack();
    }

    public void actionPerformed(ActionEvent e) {
        setVisible(true);
        dispose();
    }
}

