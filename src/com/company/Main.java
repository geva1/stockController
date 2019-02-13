package com.company;

import com.company.Database.DatabaseService;
import com.company.View.ClientView;
import com.company.View.SaleView;
import com.company.View.StockView;

import javax.swing.*;
import java.awt.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends JFrame {
    private Main() {
        try {
            setTitle("Arte e Tricô");
            database = new DatabaseService();
        } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        JTabbedPane jTabbedPane1 = new JTabbedPane();

        jTabbedPane1.setFont(new Font("Tahoma", Font.PLAIN, 14));

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        StockView stockView = new StockView(database);
        jTabbedPane1.addTab("Estoque", stockView.getStockListPanel());

        jTabbedPane1.addTab("Vendas", new SaleView(database, stockView).getSaleListPanel());

        jTabbedPane1.addTab("Clientes", new ClientView(database));

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jTabbedPane1)
        );

        pack();
    }

    public static void main(String args[]) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        EventQueue.invokeLater(() -> new Main().setVisible(true));
    }

    private DatabaseService database;
}
