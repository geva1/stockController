package com.company;

import javax.swing.*;

public class Main extends JFrame {

    private static void createAndShowGUI() {
        String[] labels = {"Name: ", "Fax: ", "Email: ", "Address: "};
        int numPairs = labels.length;

        //Create and populate the panel.
        JPanel p = new JPanel(new SpringLayout());
        for (String label : labels) {
            JLabel l = new JLabel(label, JLabel.TRAILING);
            p.add(l);
            JTextField textField = new JTextField(10);
            l.setLabelFor(textField);
            p.add(textField);
        }

        //Lay out the panel.
        SpringUtilities.makeCompactGrid(p,
                numPairs, 2, //rows, cols
                6, 6,        //initX, initY
                6, 6);       //xPad, yPad

        //Create and set up the window.
        JFrame frame = new JFrame("SpringForm");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Set up the content pane.
        p.setOpaque(true);  //content panes must be opaque
        frame.setContentPane(p);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(Main::createAndShowGUI);
    }

    /*private final JButton stock = new JButton();
    private final JButton shop = new JButton();

    private JFrame jFrame = new JFrame();

    public static void main(String[] args) {
        try {
            SwingUtilities.invokeAndWait(Main::new);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Main() {
        super();
        this.setTitle("Estoque");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        JTabbedPane tabbedPane = new JTabbedPane();

        SpringLayout gridLayout = new SpringLayout();

        JPanel card1 = new JPanel(gridLayout);
        card1.add(makeButton());

        JPanel card2 = new JPanel();
        card2.add(makeNewButton());

        tabbedPane.addTab("          ESTOQUE          ", card1);
        tabbedPane.addTab("           VENDA           ", card2);

        this.getContentPane().add(tabbedPane);
    }

    private JButton makeButton() {
        stock.setText("Estoque");
        stock.setBounds(10, 10, 100, 30);
        stock.addActionListener(e -> {
            jFrame.removeAll();
            jFrame.revalidate();
            jFrame.repaint();
        });
        return stock;
    }

    private JButton makeNewButton() {
        shop.setText("Venda");
        shop.setBounds(120, 10, 100, 30);
        shop.addActionListener(e -> {

        });
        return shop;
    }*/
}