package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends javax.swing.JFrame {

    private Main() {
        try {
            Class.forName("org.h2.Driver");
            st = DriverManager.getConnection("jdbc:h2:./stockDBFiles/stockDB", "test", "test").createStatement();
            st.execute("CREATE TABLE stock(barcode varchar(25) NOT NULL PRIMARY KEY, color varchar(25), start varchar(50), description varchar(255) NOT NULL, " +
                    "category varchar(25), weight integer, trademark varchar(25), meters integer, location varchar(25), price float NOT NULL, " +
                    "quantity float NOT NULL, observation varchar(255), image text)");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException ignored) {
        }
        try {
            st.execute("CREATE TABLE trademark(trademark varchar(25) NOT NULL PRIMARY KEY)");
        } catch (SQLException ignored) {
        }
        try {
            st.execute("CREATE TABLE category(category varchar(25) NOT NULL PRIMARY KEY)");
        } catch (SQLException ignored) {
        }
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        tabs = new javax.swing.JTabbedPane();
        stockPanel = new javax.swing.JPanel();
        barCodeText = new java.awt.TextField();
        startText = new java.awt.TextField();
        colorText = new java.awt.TextField();
        descriptionText = new java.awt.TextField();
        barCode = new java.awt.Label();
        start = new java.awt.Label();
        color = new java.awt.Label();
        description = new java.awt.Label();
        category = new java.awt.Label();
        trademark = new java.awt.Label();
        addToDb = new javax.swing.JButton();
        categoryCombo = new javax.swing.JComboBox();
        trademarkCombo = new javax.swing.JComboBox();
        localization = new java.awt.Label();
        price = new java.awt.Label();
        weigth = new java.awt.Label();
        meters = new java.awt.Label();
        observations = new java.awt.Label();
        weigthText = new java.awt.TextField();
        metersText = new java.awt.TextField();
        localizationText = new java.awt.TextField();
        priceText = new java.awt.TextField();
        observationText = new java.awt.TextField();
        quantity = new java.awt.Label();
        quantityText = new java.awt.TextField();
        salePanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        itemList = new javax.swing.JList();
        confirm = new javax.swing.JButton();
        cancel = new javax.swing.JButton();
        barCodeToAdd = new java.awt.TextField();
        addToList = new javax.swing.JButton();
        total = new java.awt.Label();
        totalPrice = new java.awt.Label();
        photo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        barCodeToAdd.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 20)); // NOI18N

        tabs.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 14)); // NOI18N

        barCodeText.addActionListener(evt -> barCodeText.transferFocus());

        startText.addActionListener(evt -> startText.transferFocus());

        colorText.addActionListener(evt -> colorText.transferFocus());

        descriptionText.addActionListener(evt -> descriptionText.transferFocus());

        barCode.setText("Código de barras");

        color.setText("Cor");

        start.setText("Partida");

        description.setText("Descrição");

        category.setText("Categoria");

        trademark.setText("Marca");

        addToDb.setFont(new java.awt.Font("Arial", Font.PLAIN, 18)); // NOI18N
        addToDb.setText("Salvar");
        addToDb.addActionListener(this::addToDbActionPerformed);

        photo.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        photo.setText(" Adicionar uma foto...");
        photo.setToolTipText("");
        photo.setFocusable(true);
        photo.setBorder(BorderFactory.createLineBorder(Color.black));
        photo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JFileChooser jf = new JFileChooser();
                int result = jf.showSaveDialog(null);
                if(result == JFileChooser.APPROVE_OPTION) {
                    try {
                        FileInputStream fileInputStreamReader = new FileInputStream(jf.getSelectedFile());
                        byte[] bytes = new byte[(int)jf.getSelectedFile().length()];
                        fileInputStreamReader.read(bytes);
                        photo.setText(Base64.getEncoder().encodeToString(bytes));
                        photo.setIcon(new ImageIcon(new ImageIcon(jf.getSelectedFile().getAbsolutePath()).getImage()
                                .getScaledInstance(photo.getWidth(), photo.getHeight(), Image.SCALE_AREA_AVERAGING)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        try {
            setTrademarkCombo();
            trademarkCombo.addActionListener(actionEvent -> {
                if(trademarkCombo.getSelectedIndex() == 0) {
                    String marca = JOptionPane.showInputDialog(null, "Marca: ", "Inserir marca", JOptionPane.QUESTION_MESSAGE);
                    try {
                        if(marca != null) {
                            PreparedStatement novoSt = st.getConnection().prepareStatement("INSERT INTO trademark (trademark) VALUES (?)");
                            novoSt.setString(1, marca);
                            novoSt.executeUpdate();
                            setTrademarkCombo();
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Marca já cadastrada");
                    }
                } else if(trademarkCombo.getSelectedIndex() == trademarkCombo.getItemCount() - 1) {
                    String marca = JOptionPane.showInputDialog(null, "Marca: ", "Excluir marca", JOptionPane.QUESTION_MESSAGE);
                    try {
                        PreparedStatement novoSt = st.getConnection().prepareStatement("DELETE FROM trademark WHERE trademark = ?");
                        novoSt.setString(1, marca);
                        novoSt.executeUpdate();
                        setTrademarkCombo();
                    } catch (SQLException e) {
                    }
                } else {
                    trademarkCombo.transferFocus();
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            setCategoryCombo();
            categoryCombo.addActionListener(actionEvent -> {
                if(categoryCombo.getSelectedIndex() == 0) {
                    String marca = JOptionPane.showInputDialog(null, "Categoria: ", "Inserir categoria", JOptionPane.QUESTION_MESSAGE);
                    try {
                        if(marca != null) {
                            PreparedStatement novoSt = st.getConnection().prepareStatement("INSERT INTO category (category) VALUES (?)");
                            novoSt.setString(1, marca);
                            novoSt.executeUpdate();
                            setCategoryCombo();
                        }
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "Categoria já cadastrada");
                    }
                } else if(categoryCombo.getSelectedIndex() == categoryCombo.getItemCount() - 1) {
                    String marca = JOptionPane.showInputDialog(null, "Categoria: ", "Excluir categoria", JOptionPane.QUESTION_MESSAGE);
                    try {
                        PreparedStatement novoSt = st.getConnection().prepareStatement("DELETE FROM category WHERE category = ?");
                        novoSt.setString(1, marca);
                        novoSt.executeUpdate();
                        setCategoryCombo();
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(null, "Categoria não existente");
                    }
                } else {
                    categoryCombo.transferFocus();
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }

        localization.setText("Localização");

        price.setText("Preço de venda");

        weigth.setText("Peso");

        meters.setText("Metros");

        observations.setText("Observações");

        weigthText.addActionListener(evt -> weigthText.transferFocus());

        metersText.addActionListener(evt -> metersText.transferFocus());

        localizationText.addActionListener(evt -> localizationText.transferFocus());

        priceText.addActionListener(evt -> priceText.transferFocus());

        observationText.addActionListener(evt -> observationText.transferFocus());

        quantity.setText("Estoque atual");

        quantityText.addActionListener(evt -> quantityText.transferFocus());

        javax.swing.GroupLayout stockPanelLayout = new javax.swing.GroupLayout(stockPanel);
        stockPanel.setLayout(stockPanelLayout);
        stockPanelLayout.setHorizontalGroup(
                stockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(stockPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(stockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, stockPanelLayout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addGroup(stockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(addToDb, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(startText, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(stockPanelLayout.createSequentialGroup()
                                                .addComponent(description, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(stockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(descriptionText, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, stockPanelLayout.createSequentialGroup()
                                                                .addGap(33, 33, 33)
                                                                .addGroup(stockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(categoryCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(trademarkCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(0, 0, Short.MAX_VALUE))))
                                        .addGroup(stockPanelLayout.createSequentialGroup()
                                                .addGroup(stockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(stockPanelLayout.createSequentialGroup()
                                                                .addComponent(barCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(barCodeText, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(category, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(trademark, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(quantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addGroup(stockPanelLayout.createSequentialGroup()
                                                .addGroup(stockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(localization, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(price, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(observations, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(15, 15, 15)
                                                .addGroup(stockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(observationText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addGroup(stockPanelLayout.createSequentialGroup()
                                                                .addGroup(stockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                        .addComponent(quantityText, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addGroup(stockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                                .addComponent(localizationText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                .addComponent(priceText, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)))
                                                                .addGap(91, 91, 91)
                                                                .addGroup(stockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, stockPanelLayout.createSequentialGroup()
                                                                                .addGap(0, 1, Short.MAX_VALUE)
                                                                                .addGroup(stockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                        .addComponent(start, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                        .addGroup(stockPanelLayout.createSequentialGroup()
                                                                                                .addComponent(color, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(colorText, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, stockPanelLayout.createSequentialGroup()
                                                                                .addGroup(stockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                        .addComponent(weigth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                        .addComponent(meters, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                .addGroup(stockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                        .addComponent(metersText, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                        .addComponent(weigthText, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                                        .addComponent(photo, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );
        stockPanelLayout.setVerticalGroup(
                stockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(stockPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(stockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(barCodeText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(barCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(colorText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(color, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(stockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(startText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(start, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(stockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(stockPanelLayout.createSequentialGroup()
                                                .addGroup(stockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(stockPanelLayout.createSequentialGroup()
                                                                .addComponent(description, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(category, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(stockPanelLayout.createSequentialGroup()
                                                                .addComponent(descriptionText, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(stockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addGroup(stockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                .addComponent(categoryCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(weigthText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                        .addComponent(weigth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(stockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(stockPanelLayout.createSequentialGroup()
                                                                .addGroup(stockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(trademarkCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(trademark, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(meters, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(stockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(localization, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(localizationText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                        .addComponent(metersText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(stockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(priceText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(price, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(quantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(quantityText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(stockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(stockPanelLayout.createSequentialGroup()
                                                .addComponent(observations, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 139, Short.MAX_VALUE)
                                                .addComponent(addToDb, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(stockPanelLayout.createSequentialGroup()
                                                .addComponent(observationText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(photo, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
        );

        tabs.addTab("Estoque", stockPanel);

        itemList.setFont(new java.awt.Font("Arial", Font.PLAIN, 24)); // NOI18N
        jScrollPane1.setViewportView(itemList);

        confirm.setText("Confirmar");
        confirm.addActionListener(actionEvent -> confirmPurchase());

        cancel.setText("Cancelar");
        cancel.addActionListener(actionEvent -> clearList());

        barCodeToAdd.addActionListener(actionEvent -> insertItemIntoList());

        addToList.setText("Adicionar");
        addToList.addActionListener(actionEvent -> insertItemIntoList());

        total.setFont(new java.awt.Font("Arial", Font.PLAIN, 48)); // NOI18N
        total.setText("Total: ");

        totalPrice.setFont(new java.awt.Font("Arial", Font.PLAIN, 48)); // NOI18N
        totalPrice.setText("0,00");

        javax.swing.GroupLayout salePanelLayout = new javax.swing.GroupLayout(salePanel);
        salePanel.setLayout(salePanelLayout);
        salePanelLayout.setHorizontalGroup(
                salePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1)
                        .addGroup(salePanelLayout.createSequentialGroup()
                                .addGroup(salePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, salePanelLayout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(cancel)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(confirm))
                                        .addGroup(salePanelLayout.createSequentialGroup()
                                                .addGap(111, 297, Short.MAX_VALUE)
                                                .addComponent(total, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(totalPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(salePanelLayout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(barCodeToAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(addToList)))
                                .addContainerGap())
        );
        salePanelLayout.setVerticalGroup(
                salePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(salePanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(salePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(addToList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(barCodeToAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                                .addGroup(salePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(total, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(totalPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(salePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(confirm)
                                        .addComponent(cancel))
                                .addContainerGap())
        );

        total.getAccessibleContext().setAccessibleDescription("");

        tabs.addTab("Venda", salePanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(tabs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(tabs)
        );

        pack();

        barCodeText.requestFocus();
        itemList.setCellRenderer(new ItemRenderer());
        itemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        itemList.setModel(model);
    }

    private void addToDbActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            PreparedStatement newSt = st.getConnection().prepareStatement("INSERT INTO stock (barcode, color, start, " +
                    "description, category, weight, trademark, meters, location," +
                    "price, quantity, observation, image) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)");
            newSt.setString(1, barCodeText.getText());
            newSt.setString(2, colorText.getText());
            newSt.setString(3, startText.getText());
            newSt.setString(4, descriptionText.getText());
            newSt.setString(5, String.valueOf(categoryCombo.getSelectedItem()));
            newSt.setInt(6, Integer.valueOf(weigthText.getText()));
            newSt.setString(7, String.valueOf(trademarkCombo.getSelectedItem()));
            newSt.setInt(8, Integer.valueOf(metersText.getText()));
            newSt.setString(9, localizationText.getText());
            if(priceText.getText().contains(",")) newSt.setFloat(10, Float.valueOf(priceText.getText().replace(",", ".")));
            else newSt.setFloat(10, Float.valueOf(priceText.getText()));
            newSt.setInt(11, Integer.valueOf(quantityText.getText()));
            newSt.setString(12, observationText.getText());
            newSt.setString(13, photo.getText());
            newSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void confirmPurchase() {
        try {
            if(model.size() > 0) {
                PreparedStatement newSt = st.getConnection().prepareStatement("UPDATE stock SET quantity = ((SELECT quantity FROM stock WHERE barcode = ? LIMIT 1) - ?) WHERE barcode = ?");
                for (int i = 0; i < model.size(); i++) {
                    newSt.setString(1, ((ItemObject) model.getElementAt(i)).getBarcode());
                    newSt.setInt(2, ((ItemObject) model.getElementAt(i)).getQuantity());
                    newSt.setString(3, ((ItemObject) model.getElementAt(i)).getBarcode());
                    newSt.executeUpdate();
                }
                model.removeAllElements();
                totalPriceText = (float) 0;
                totalPrice.setText(String.format("%.02f", totalPriceText).replace(".", ","));
                salePanel.validate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void clearList() {
        if (!model.isEmpty()) {
            model.removeAllElements();
        }
        totalPriceText = (float) 0;
        totalPrice.setText(String.format("%.02f", totalPriceText).replace(".", ","));
        salePanel.validate();
        barCodeToAdd.requestFocus();
        barCodeToAdd.setText("");
    }

    private void insertItemIntoList() {
        if(!barCodeToAdd.getText().equals("") && barCodeToAdd.getText() != null) {
            if (barCodeToAdd.getText().charAt(0) == 'x') {
                if(model.size() > 0) {
                    int selected = itemList.getSelectedIndex();
                    if (selected >= 0) {
                        if (Integer.valueOf(barCodeToAdd.getText().replace("x", "")) == 0) {
                            model.remove(selected);
                            setTotalPriceTextLabel();
                        } else {
                            ItemObject itemObject = (ItemObject) model.getElementAt(selected);
                            model.set(selected, new ItemObject(itemObject.getName(), itemObject.getPrice(), itemObject.getImage(),
                                    Integer.valueOf(barCodeToAdd.getText().replace("x", "")), itemObject.getBarcode()));
                            setTotalPriceTextLabel();
                        }
                    } else {
                        if (Integer.valueOf(barCodeToAdd.getText().replace("x", "")) == 0) {
                            model.remove(model.indexOf(model.lastElement()));
                            setTotalPriceTextLabel();
                        } else {
                            ItemObject itemObject = (ItemObject) model.lastElement();
                            model.set(model.indexOf(itemObject), new ItemObject(itemObject.getName(), itemObject.getPrice(), itemObject.getImage(),
                                    Integer.valueOf(barCodeToAdd.getText().replace("x", "")), itemObject.getBarcode()));
                            setTotalPriceTextLabel();
                        }
                    }
                }
            } else {
                boolean hasInList = false;
                for (int i = 0; i < model.size(); i++) {
                    ItemObject itemObject = (ItemObject) model.getElementAt(i);
                    if (itemObject.getBarcode().equals(barCodeToAdd.getText())) {
                        model.set(i, new ItemObject(itemObject.getName(), itemObject.getPrice(), itemObject.getImage(), itemObject.getQuantity() + 1, itemObject.getBarcode()));
                        setTotalPriceTextLabel();
                        hasInList = true;
                    }
                }
                if (!hasInList) {
                    PreparedStatement newSt = null;
                    try {
                        newSt = st.getConnection().prepareStatement("SELECT * FROM stock WHERE barcode = ?");
                        newSt.setString(1, barCodeToAdd.getText());
                        ResultSet rs = newSt.executeQuery();
                        if (rs.next()) {
                            String description = rs.getNString(4);
                            Float price = rs.getFloat(10);
                            model.addElement(new ItemObject(description, String.valueOf(price), null, 1, barCodeToAdd.getText()));
                            setTotalPriceTextLabel();
                        } else {
                            JOptionPane.showMessageDialog(null, "Esse item não existe no estoque");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        barCodeToAdd.requestFocus();
        barCodeToAdd.setText("");
    }

    private void setTotalPriceTextLabel() {
        float price = 0;
        for(int i = 0; i < model.size(); i++) {
            price += (Float.valueOf(((ItemObject)model.getElementAt(i)).getPrice()) * ((ItemObject)model.getElementAt(i)).getQuantity());
        }
        totalPriceText = price;
        totalPrice.setText(String.format("%.02f", totalPriceText).replace(".", ","));
        salePanel.validate();
    }

    private void setCategoryCombo() throws SQLException {
        PreparedStatement newSt = st.getConnection().prepareStatement("SELECT * FROM category");
        ResultSet rs = newSt.executeQuery();
        ArrayList<String> categories = new ArrayList<>();
        categories.add("Nova categoria...");
        while (rs.next()) {
            categories.add(rs.getString("category"));
        }
        categories.add("Excluir categoria...");
        categoryCombo.setModel(new DefaultComboBoxModel(categories.toArray(new String[categories.size()])));
    }

    private void setTrademarkCombo() throws SQLException {
        PreparedStatement newSt = st.getConnection().prepareStatement("SELECT * FROM trademark");
        ResultSet rs = newSt.executeQuery();
        ArrayList<String> trademarks = new ArrayList<>();
        trademarks.add("Nova marca...");
        while (rs.next()) {
            trademarks.add(rs.getString("trademark"));
        }
        trademarks.add("Excluir marca...");
        trademarkCombo.setModel(new DefaultComboBoxModel(trademarks.toArray(new String[trademarks.size()])));
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(() -> new Main().setVisible(true));
    }

    private javax.swing.JButton addToDb;
    private javax.swing.JButton addToList;
    private java.awt.Label barCode;
    private java.awt.TextField barCodeText;
    private java.awt.TextField barCodeToAdd;
    private javax.swing.JButton cancel;
    private java.awt.Label category;
    private javax.swing.JComboBox categoryCombo;
    private java.awt.Label start;
    private java.awt.TextField startText;
    private javax.swing.JButton confirm;
    private java.awt.Label description;
    private java.awt.TextField descriptionText;
    private javax.swing.JList itemList;
    private javax.swing.JScrollPane jScrollPane1;
    private java.awt.Label localization;
    private java.awt.TextField localizationText;
    private java.awt.Label meters;
    private java.awt.TextField metersText;
    private java.awt.TextField observationText;
    private java.awt.Label observations;
    private java.awt.Label price;
    private java.awt.TextField priceText;
    private java.awt.Label quantity;
    private java.awt.TextField quantityText;
    private javax.swing.JPanel salePanel;
    private java.awt.Label color;
    private java.awt.TextField colorText;
    private javax.swing.JPanel stockPanel;
    private javax.swing.JTabbedPane tabs;
    private java.awt.Label total;
    private java.awt.Label totalPrice;
    private java.awt.Label trademark;
    private javax.swing.JComboBox trademarkCombo;
    private java.awt.Label weigth;
    private java.awt.TextField weigthText;
    private javax.swing.JLabel photo;
    private DefaultListModel model = new DefaultListModel();
    private Statement st;
    private Float totalPriceText = (float) 0;

    /*
    https://www.youtube.com/watch?v=VHd29F_Tk04
    https://stackoverflow.com/questions/3775373/java-how-to-add-image-to-jlabel?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
    https://stackoverflow.com/questions/36492084/how-to-convert-an-image-to-base64-string-in-java?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
    */
}