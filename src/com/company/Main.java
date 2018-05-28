package com.company;

import javax.swing.*;

public class Main extends JFrame {
    private final JButton b = new JButton();
    private final JButton c = new JButton();
    private boolean isTest = false;

    private Main() {
        super();
        JFrame jFrame = new JFrame();
        jFrame.setTitle("stockController");
        jFrame.getContentPane().setLayout(null);
        jFrame.setBounds(100, 100, 180, 140);
        jFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        jFrame.add(makeButton());
        jFrame.add(makeNewButton());
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(jFrame);
    }

    private JButton makeButton() {
        b.setText("Click me!");
        b.setBounds(10, 10, 100, 30);
        b.addActionListener(e -> c.setVisible(false));
        return b;
    }

    private JButton makeNewButton() {
        c.setText("Click me!");
        c.setBounds(120, 10, 100, 30);
        c.addActionListener(e -> {
            if (!isTest) {
                b.setText("Test coco");
                isTest = true;
            } else {
                b.setText("Click me!");
                isTest = false;
            }
        });
        return c;
    }

    public static void main(String[] args) {
        // Swing calls must be run by the event dispatching thread.
        try {
            SwingUtilities.invokeAndWait(Main::new);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}