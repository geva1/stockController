package com.company.View;

import com.company.Database.DatabaseService;
import com.company.ItemRenderer;
import com.company.Models.Client;
import com.company.Objects.ItemObject;

import javax.swing.*;
import java.awt.*;

class SaleFinishView extends JFrame {
    private DatabaseService database;
    private Client client;
    private DefaultListModel<ItemObject> model;
    private Boolean confirmed = false;

    SaleFinishView(DatabaseService database, Client client, DefaultListModel<ItemObject> model) {
        this.database = database;
        this.client = client;
        this.model = model;
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        jScrollPane1 = new javax.swing.JScrollPane();
        installmentList = new javax.swing.JList<>();
        addEditClient = new javax.swing.JButton();
        clientName = new java.awt.Label();
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        itemsList = new javax.swing.JList<>();
        paymentMode = new javax.swing.JList<>();
        removeClient = new javax.swing.JButton();
        confirmButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        installmentList3 = new javax.swing.JList<>();
        jScrollPane5 = new javax.swing.JScrollPane();
        installmentList2 = new javax.swing.JList<>();
        label2 = new java.awt.Label();
        label3 = new java.awt.Label();
        label4 = new java.awt.Label();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        jScrollPane2.setViewportView(itemsList);
        jScrollPane1.setViewportView(installmentList);
        jScrollPane3.setViewportView(paymentMode);
        jScrollPane4.setViewportView(installmentList3);
        jScrollPane5.setViewportView(installmentList2);

        label2.setAlignment(java.awt.Label.CENTER);
        label3.setAlignment(java.awt.Label.CENTER);
        label4.setAlignment(java.awt.Label.CENTER);
        label3.setText("");
        label2.setText("");
        label4.setText("");

        if (client != null) {
            addEditClient.setText("Alterar Cliente");
            clientName.setText(client.getName());
        } else {
            addEditClient.setText("Adicionar Cliente");
        }
        addEditClient.addActionListener(actionEvent -> {
            //todo: call add client screen
        });

        removeClient.setText("Remover Cliente");
        removeClient.addActionListener(actionEvent -> {
            client = null;
            addEditClient.setText("Adicionar Cliente");
            clientName.setText("");
        });

        confirmButton.setText("Confirmar");
        confirmButton.addActionListener(actionEvent -> {
            confirmed = true;
            //todo: update database
            dispose();
        });

        cancelButton.setText("Cancelar");
        cancelButton.addActionListener(actionEvent -> dispose());

        installmentList.setVisible(false);
        installmentList2.setVisible(false);
        installmentList3.setVisible(false);

        DefaultListModel<String> payment = new DefaultListModel<>();
        payment.addElement("À Vista");
        payment.addElement("2 Vezes");
        payment.addElement("3 Vezes");

        paymentMode.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        paymentMode.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 18));
        paymentMode.setModel(payment);
        paymentMode.addListSelectionListener(listSelectionEvent -> {
            switch (paymentMode.getSelectedIndex()) {
                case 0:
                    installmentList.setVisible(true);
                    installmentList2.setVisible(false);
                    installmentList3.setVisible(false);
                    label3.setText("1ª");
                    label2.setText("");
                    label4.setText("");
                    break;
                case 1:
                    installmentList.setVisible(true);
                    installmentList2.setVisible(true);
                    installmentList3.setVisible(false);
                    label3.setText("1ª");
                    label2.setText("2ª");
                    label4.setText("");
                    break;
                case 2:
                    installmentList.setVisible(true);
                    installmentList2.setVisible(true);
                    installmentList3.setVisible(true);
                    label3.setText("1ª");
                    label2.setText("2ª");
                    label4.setText("3ª");
                    break;
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 453, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(addEditClient, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(removeClient, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(confirmButton, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(clientName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                        .addComponent(label3, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                                                .addGap(39, 39, 39)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(label2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                                        .addComponent(label4, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE))))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(label4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(clientName, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(addEditClient, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(removeClient, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(confirmButton, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                                                        .addComponent(cancelButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                        .addComponent(jScrollPane2))
                                .addContainerGap())
        );

        itemsList.setCellRenderer(new ItemRenderer());
        itemsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        itemsList.setModel(model);

        pack();
    }

    Boolean getConfirmation() {
        return confirmed;
    }

    private javax.swing.JButton addEditClient;
    private javax.swing.JButton cancelButton;
    private java.awt.Label clientName;
    private javax.swing.JButton confirmButton;
    private javax.swing.JList<String> installmentList;
    private javax.swing.JList<String> paymentMode;
    private javax.swing.JList<ItemObject> itemsList;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton removeClient;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private java.awt.Label label2;
    private java.awt.Label label3;
    private java.awt.Label label4;
    private javax.swing.JList<String> installmentList2;
    private javax.swing.JList<String> installmentList3;
}
