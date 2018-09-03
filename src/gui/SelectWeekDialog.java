package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SelectWeekDialog extends JDialog implements ActionListener {
    private JButton Done, Cancel;
    private JPanel southPane, centerPane;
    private JButton buttonList[];
    private static int buttonAmount, selectWeek;

    SelectWeekDialog(JFrame F, String s, int buttonAmount) {
        super(F, s, true);
        this.buttonAmount = buttonAmount;

        if(buttonAmount <= 0) {
            System.out.println("Error! Code location:src/gui/SelectWeekDialog.java 18");
            System.exit(0);
        }

        Container con = this.getContentPane();
        con.setLayout(new BorderLayout(5, 5));
        southPane = new JPanel();
        southPane.setLayout(new GridLayout(1, 8));

        centerPane = new JPanel();
        centerPane.setLayout(new GridLayout(buttonAmount, 1));
        buttonList = new JButton[buttonAmount];
        for(int i = 0; i < buttonAmount; i++) {
            buttonList[i] = new JButton("Week " + (i + 1));
            buttonList[i].addActionListener(this);
            centerPane.add(buttonList[i]);
        }

        Done = new JButton("OK"); Done.addActionListener(this);
        Cancel = new JButton("CANCEL"); Cancel.addActionListener(this);

        for(int i = 0; i < 2; i++) southPane.add(new JLabel());
        southPane.add(Done); southPane.add(Cancel);
        for(int i = 0; i < 2; i++) southPane.add(new JLabel());

        con.add(southPane, BorderLayout.SOUTH);
        con.add(centerPane, BorderLayout.CENTER);
        con.setVisible(true); this.pack();
    }

    public void actionPerformed(ActionEvent e) {
        for(int i = 0; i < buttonAmount; i++) {
            if(e.getSource() == buttonList[i]) {
                System.out.println("You selected " + (i + 1) + " week.");
                this.selectWeek = i + 1;
                break;
            }
        }
        setVisible(true);
        dispose();
    }

    public static int getSelectWeek() {
        return selectWeek;
    }
}
