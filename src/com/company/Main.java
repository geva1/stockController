package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;

public class Main extends javax.swing.JFrame {

    private Main() {
        try {
            Class.forName("org.h2.Driver");
            st = DriverManager.getConnection("jdbc:h2:./stockDBFiles/stockDB", "test", "test").createStatement();
            st.execute("CREATE TABLE IF NOT EXISTS trademark (\n" +
                    "  trademark VARCHAR(45) NOT NULL,\n" +
                    "  PRIMARY KEY (trademark));\n" +
                    "\n" +
                    "CREATE TABLE IF NOT EXISTS category (\n" +
                    "  category VARCHAR(45) NOT NULL,\n" +
                    "  PRIMARY KEY (category));\n" +
                    "\n" +
                    "CREATE TABLE IF NOT EXISTS stock (\n" +
                    "  barcode VARCHAR(25) NOT NULL,\n" +
                    "  color VARCHAR(45) NOT NULL,\n" +
                    "  start VARCHAR(45) NOT NULL,\n" +
                    "  description VARCHAR(255) NOT NULL,\n" +
                    "  weight FLOAT NOT NULL,\n" +
                    "  meters INT NOT NULL,\n" +
                    "  location VARCHAR(45) NULL,\n" +
                    "  price FLOAT NOT NULL,\n" +
                    "  quantity FLOAT NOT NULL,\n" +
                    "  observation VARCHAR(255) NOT NULL,\n" +
                    "  image TEXT NULL,\n" +
                    "  trademark VARCHAR(45) NOT NULL,\n" +
                    "  category VARCHAR(45) NOT NULL,\n" +
                    "  PRIMARY KEY (barcode),\n" +
                    "    FOREIGN KEY (trademark)\n" +
                    "    REFERENCES trademark (trademark)\n" +
                    "    ON DELETE NO ACTION\n" +
                    "    ON UPDATE NO ACTION,\n" +
                    "    FOREIGN KEY (category)\n" +
                    "    REFERENCES category (category)\n" +
                    "    ON DELETE NO ACTION\n" +
                    "    ON UPDATE NO ACTION);\n" +
                    "\n" +
                    "CREATE TABLE IF NOT EXISTS client (\n" +
                    "  cpf VARCHAR(15) NOT NULL,\n" +
                    "  name VARCHAR(127) NULL,\n" +
                    "  adress VARCHAR(100) NULL,\n" +
                    "  phone VARCHAR(45) NULL,\n" +
                    "  cellphone VARCHAR(45) NULL,\n" +
                    "  email VARCHAR(45) NULL,\n" +
                    "  PRIMARY KEY (cpf));\n" +
                    "\n" +
                    "CREATE TABLE IF NOT EXISTS sale (\n" +
                    "  id INT NOT NULL AUTO_INCREMENT,\n" +
                    "  cpf VARCHAR(15) NOT NULL,\n" +
                    "  PRIMARY KEY (id),\n" +
                    "    FOREIGN KEY (cpf)\n" +
                    "    REFERENCES client (cpf)\n" +
                    "    ON DELETE NO ACTION\n" +
                    "    ON UPDATE NO ACTION);\n" +
                    "\n" +
                    "CREATE TABLE IF NOT EXISTS sale_has_item (\n" +
                    "  sale INT NOT NULL,\n" +
                    "  barcode VARCHAR(25) NOT NULL,\n" +
                    "    FOREIGN KEY (sale)\n" +
                    "    REFERENCES sale (id)\n" +
                    "    ON DELETE NO ACTION\n" +
                    "    ON UPDATE NO ACTION,\n" +
                    "    FOREIGN KEY (barcode)\n" +
                    "    REFERENCES stock (barcode)\n" +
                    "    ON DELETE NO ACTION\n" +
                    "    ON UPDATE NO ACTION);\n");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
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
        Label barCode = new Label();
        Label start = new Label();
        Label color = new Label();
        Label description = new Label();
        Label category = new Label();
        Label trademark = new Label();
        addToDb = new javax.swing.JButton();
        categoryCombo = new javax.swing.JComboBox();
        trademarkCombo = new javax.swing.JComboBox();
        Label localization = new Label();
        Label price = new Label();
        Label weight = new Label();
        Label meters = new Label();
        Label observations = new Label();
        weightText = new java.awt.TextField();
        metersText = new java.awt.TextField();
        localizationText = new java.awt.TextField();
        priceText = new java.awt.TextField();
        observationText = new java.awt.TextField();
        Label quantity = new Label();
        quantityText = new java.awt.TextField();
        salePanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        itemList = new javax.swing.JList();
        confirm = new javax.swing.JButton();
        cancel = new javax.swing.JButton();
        barCodeToAdd = new java.awt.TextField();
        searchText = new java.awt.TextField();
        addToList = new javax.swing.JButton();
        total = new java.awt.Label();
        total2Times = new java.awt.Label();
        total3Times = new java.awt.Label();
        totalPrice = new java.awt.Label();
        photo = new javax.swing.JLabel();
        delete = new javax.swing.JButton();
        search = new javax.swing.JButton();
        stockListPanel = new javax.swing.JPanel();
        stockListScroll = new javax.swing.JScrollPane();
        addToStock = new javax.swing.JButton();
        editItem = new javax.swing.JButton();
        deleteItem = new javax.swing.JButton();
        cancelUpdate = new javax.swing.JButton();
        stockTable = new javax.swing.JTable() {
            public Class getColumnClass(int column) {
                return (column == 0) ? Icon.class : Object.class;
            }
        };
        stockTable.setRowHeight(60);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        barCodeToAdd.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 20));

        tabs.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 14));

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

        addToDb.setFont(new java.awt.Font("Arial", Font.PLAIN, 18));
        addToDb.setText("Salvar");
        addToDb.addActionListener(actionEvent -> addToDbActionPerformed());

        cancelUpdate.setFont(new java.awt.Font("Arial", Font.PLAIN, 18));
        cancelUpdate.setText("Cancelar");
        cancelUpdate.addActionListener(actionEvent -> {
            clearAllFields();
            addRowsToTable();
            jFrame.remove(stockPanel);
            jFrame.add(stockListPanel);
            tabs.invalidate();
            tabs.revalidate();
        });

        photo.setFont(new java.awt.Font("Arial", Font.PLAIN, 12));
        photo.setText(" Adicionar uma foto...");
        photo.setToolTipText("");
        photo.setFocusable(true);
        photo.setBorder(BorderFactory.createLineBorder(Color.black));
        photo.setHorizontalTextPosition(JLabel.CENTER);
        photo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JFileChooser jf = new JFileChooser();
                int result = jf.showSaveDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    try {
                        FileInputStream fileInputStreamReader = new FileInputStream(jf.getSelectedFile());
                        byte[] bytes = new byte[(int) jf.getSelectedFile().length()];
                        //noinspection ResultOfMethodCallIgnored
                        fileInputStreamReader.read(bytes);
                        int width = new ImageIcon(jf.getSelectedFile().getAbsolutePath()).getImage().getWidth(null);
                        int height = new ImageIcon(jf.getSelectedFile().getAbsolutePath()).getImage().getHeight(null);
                        int greater;
                        if (width > height) greater = width;
                        else greater = height;
                        photo.setText("                                                ");
                        photo.setName(Base64.getEncoder().encodeToString(bytes));
                        photo.setIcon(new ImageIcon(new ImageIcon(jf.getSelectedFile().getAbsolutePath()).getImage()
                                .getScaledInstance((int) (photo.getWidth() * ((float) width / (float) greater)), (int) (photo.getHeight() * ((float) height / (float) greater)), Image.SCALE_SMOOTH)));
                        photo.setHorizontalTextPosition(JLabel.CENTER);
                        photo.setVerticalTextPosition(JLabel.CENTER);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        try {
            setTrademarkCombo();
            trademarkCombo.addActionListener(trademarkListener);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            setCategoryCombo();
            categoryCombo.addActionListener(categoryListener);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        localization.setText("Localização");

        price.setText("Preço de venda");

        weight.setText("Peso");

        meters.setText("Metros");

        observations.setText("Observações");

        weightText.addActionListener(evt -> weightText.transferFocus());

        metersText.addActionListener(evt -> metersText.transferFocus());

        localizationText.addActionListener(evt -> localizationText.transferFocus());

        priceText.addActionListener(evt -> priceText.transferFocus());

        observationText.addActionListener(evt -> observationText.transferFocus());

        quantity.setText("Estoque atual");

        quantityText.addActionListener(evt -> quantityText.transferFocus());

        addToStock.setText("Adicionar...");
        addToStock.addActionListener(actionEvent -> {
            if (isEdit) {
                isEdit = false;
            }
            clearAllFields();
            jFrame.remove(stockListPanel);
            jFrame.add(stockPanel);
            tabs.invalidate();
            tabs.revalidate();
            try {
                setCategoryCombo();
                setTrademarkCombo();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        editItem.setText("Editar...");
        editItem.addActionListener(actionEvent -> editItemAction());

        deleteItem.setText("Excluir");
        deleteItem.addActionListener(actionEvent -> {
            if (stockTable.getSelectedRow() != -1) {
                try {
                    PreparedStatement del = st.getConnection().prepareStatement("DELETE FROM stock WHERE barcode = ?");
                    del.setString(1, (String) tableModel.getValueAt(stockTable.getSelectedRow(), 12));
                    del.executeUpdate();
                    addRowsToTable();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        searchText.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 20));
        searchText.addActionListener(actionEvent -> searchItems());
        search.addActionListener(actionEvent -> searchItems());

        tableModel.setColumnIdentifiers(new String[]{
                "Foto", "Descrição", "Cor", "Partida", "Categoria", "Marca", "Peso", "Metros", "Localização", "Preço", "Estoque", "Observações", "Código de barras"
        });
        addRowsToTable();
        stockTable.setModel(tableModel);
        stockListScroll.setViewportView(stockTable);

        stockTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        stockTable.getColumnModel().getColumn(0).setPreferredWidth(60);
        stockTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        stockTable.getColumnModel().getColumn(2).setPreferredWidth(50);
        stockTable.getColumnModel().getColumn(3).setPreferredWidth(50);
        stockTable.getColumnModel().getColumn(4).setPreferredWidth(80);
        stockTable.getColumnModel().getColumn(6).setPreferredWidth(80);
        stockTable.getColumnModel().getColumn(7).setPreferredWidth(80);
        stockTable.getColumnModel().getColumn(8).setPreferredWidth(80);
        stockTable.getColumnModel().getColumn(9).setPreferredWidth(50);
        stockTable.getColumnModel().getColumn(10).setPreferredWidth(100);
        stockTable.getColumnModel().getColumn(11).setPreferredWidth(193);
        stockTable.getColumnModel().getColumn(12).setPreferredWidth(100);
        stockTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        stockTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2 && stockTable.getSelectedRow() != -1) {
                    editItemAction();
                }
            }
        });

        jFrame = new JFrame();
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(stockListPanel);
        stockListPanel.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(addToStock, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(editItem, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(deleteItem, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(searchText)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                        .addComponent(stockListScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 1100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(addToStock, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(editItem, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(deleteItem, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(searchText, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                                .addComponent(stockListScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 455, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        ImageIcon ii = new ImageIcon("iconImages/magnifying_glass.png");
        search.setIcon(new ImageIcon(ii.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH)));

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
                                                        .addGroup(stockPanelLayout.createSequentialGroup()
                                                                .addComponent(cancelUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(15, 15, 15)
                                                                .addComponent(addToDb, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 662, Short.MAX_VALUE)
                                                                .addGroup(stockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, stockPanelLayout.createSequentialGroup()
                                                                                .addGroup(stockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                        .addComponent(start, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                        .addGroup(stockPanelLayout.createSequentialGroup()
                                                                                                .addComponent(color, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, 18)
                                                                                                .addComponent(colorText, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, stockPanelLayout.createSequentialGroup()
                                                                                .addGroup(stockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                        .addComponent(weight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                        .addComponent(meters, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                .addGroup(stockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                        .addComponent(metersText, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                        .addComponent(weightText, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                                        .addComponent(photo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                                                                                .addComponent(weightText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                        .addComponent(weight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
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
                                                .addGroup(stockPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(addToDb, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(cancelUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(stockPanelLayout.createSequentialGroup()
                                                .addComponent(observationText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(photo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
        );

        jFrame.add(stockListPanel);

        tabs.addTab("Estoque", jFrame.getContentPane());

        itemList.setFont(new java.awt.Font("Arial", Font.PLAIN, 24));
        itemList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2) {
                    String quantidade = JOptionPane.showInputDialog(null, "Quantidade: ", "Atualizar quantidade", JOptionPane.QUESTION_MESSAGE);
                    if (quantidade != null) {
                        ItemObject itemObject = model.getElementAt(itemList.getSelectedIndex());
                        model.set(itemList.getSelectedIndex(), new ItemObject(itemObject.getName(), itemObject.getPrice(), itemObject.getImage(),
                                Float.valueOf(quantidade.replace(",", ".")), itemObject.getBarcode()));
                        setTotalPriceTextLabel();
                    }
                }
            }
        });
        jScrollPane1.setViewportView(itemList);

        confirm.setText("Confirmar");
        confirm.addActionListener(actionEvent -> confirmPurchase());

        delete.setText("Exluir item");
        delete.addActionListener(actionEvent -> deleteItemFromList());

        cancel.setText("Cancelar");
        cancel.addActionListener(actionEvent -> clearList());

        barCodeToAdd.addActionListener(actionEvent -> insertItemIntoList());

        addToList.setText("Adicionar");
        addToList.addActionListener(actionEvent -> insertItemIntoList());

        total.setFont(new java.awt.Font("Arial", Font.PLAIN, 48));
        total.setText("À Vista: R$ ");

        totalPrice.setFont(new java.awt.Font("Arial", Font.PLAIN, 48));
        totalPrice.setText("0,00");

        total2Times.setFont(new java.awt.Font("Arial", Font.PLAIN, 18));
        total2Times.setText("2x R$ 0,00");
        total3Times.setFont(new java.awt.Font("Arial", Font.PLAIN, 18));
        total3Times.setText("3x R$ 0,00");

        javax.swing.GroupLayout salePanelLayout = new javax.swing.GroupLayout(salePanel);
        salePanel.setLayout(salePanelLayout);
        salePanelLayout.setHorizontalGroup(
                salePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1)
                        .addGroup(salePanelLayout.createSequentialGroup()
                                .addGroup(salePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, salePanelLayout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(delete)
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(cancel)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(confirm))
                                        .addGroup(salePanelLayout.createSequentialGroup()
                                                .addGap(111, 297, Short.MAX_VALUE)
                                                .addGroup(salePanelLayout.createParallelGroup()
                                                        .addComponent(total2Times, GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(total3Times, GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                                        .addComponent(totalPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(salePanelLayout.createSequentialGroup()
                                                .addComponent(total2Times, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(total3Times, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(salePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(confirm)
                                        .addComponent(cancel)
                                        .addComponent(delete))
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

        tabs.addChangeListener(changeEvent -> {
            if (tabs.getSelectedIndex() == 0) {
                addRowsToTable();
            }
        });

        pack();

        addToStock.requestFocus();
        itemList.setCellRenderer(new ItemRenderer());
        itemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        itemList.setModel(model);
    }

    private void addToDbActionPerformed() {
        if (isEdit) {
            try {
                PreparedStatement del = st.getConnection().prepareStatement("DELETE FROM stock WHERE barcode = ?");
                del.setString(1, barCodeText.getText());
                del.executeUpdate();
                isEdit = false;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        ArrayList<String> isOk = verifyAllFields();
        if (isOk.size() == 0) {
            try {
                PreparedStatement newSt = st.getConnection().prepareStatement("INSERT INTO stock (barcode, color, start, " +
                        "description, category, weight, trademark, meters, location," +
                        "price, quantity, observation, image) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)");
                newSt.setString(1, barCodeText.getText());
                newSt.setString(2, colorText.getText());
                newSt.setString(3, startText.getText());
                newSt.setString(4, descriptionText.getText());
                newSt.setString(5, String.valueOf(categoryCombo.getSelectedItem()));
                newSt.setFloat(6, Float.valueOf(weightText.getText().replace(",", ".")));
                newSt.setString(7, String.valueOf(trademarkCombo.getSelectedItem()));
                newSt.setInt(8, Integer.valueOf(metersText.getText()));
                newSt.setString(9, localizationText.getText());
                newSt.setFloat(10, Float.valueOf(priceText.getText().replace(",", ".")));
                newSt.setFloat(11, Float.valueOf(quantityText.getText().replace(",", ".")));
                newSt.setString(12, observationText.getText());
                newSt.setString(13, photo.getName());
                newSt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            clearAllFields();
            addRowsToTable();
            jFrame.remove(stockPanel);
            jFrame.add(stockListPanel);
            tabs.invalidate();
            tabs.revalidate();
        } else {
            StringBuilder stringToShow = new StringBuilder("Preencha corretamente os campos: \n");
            for(String string : isOk) {
                stringToShow.append(string).append("\n");
            }
            JOptionPane.showMessageDialog(null, stringToShow.toString());
        }
    }

    private ArrayList<String> verifyAllFields() {
        ArrayList<String> fields = new ArrayList<>();
        if(barCodeText.getText().equals("")) {
            fields.add("Código de barras");
        }
        if(colorText.getText().equals("")) {
            fields.add("Cor");
        }
        if(startText.getText().equals("")) {
            fields.add("Partida");
        }
        if(descriptionText.getText().equals("")) {
            fields.add("Descrição");
        }
        if(String.valueOf(categoryCombo.getSelectedItem()).equals("Nova categoria...") || String.valueOf(categoryCombo.getSelectedItem()).equals("Excluir categoria...")) {
            fields.add("Categoria");
        }
        if(String.valueOf(trademarkCombo.getSelectedItem()).equals("Nova marca...") || String.valueOf(trademarkCombo.getSelectedItem()).equals("Excluir marca...")) {
            fields.add("Marca");
        }
        if(!weightText.getText().matches("^\\d+([./,]\\d{0,3}?)?$")) {
            fields.add("Peso");
        }
        if(!metersText.getText().matches("^\\d+?$")) {
            fields.add("Metros");
        }
        if(!priceText.getText().matches("^\\d+[./,]\\d{2}?$")) {
            fields.add("Preço de venda");
        }
        if(!quantityText.getText().matches("^\\d+([./,]\\d{0,3}?)?$")) {
            fields.add("Estoque");
        }
        if(observationText.getText().equals("")) {
            fields.add("Observações");
        }
        if(photo.getName().equals("")) {
            fields.add("Foto");
        }
        return fields;
    }

    private void confirmPurchase() {
        try {
            if (model.size() > 0) {
                PreparedStatement newSt = st.getConnection().prepareStatement("UPDATE stock SET quantity = ((SELECT quantity FROM stock WHERE barcode = ? LIMIT 1) - ?) WHERE barcode = ?");
                for (int i = 0; i < model.size(); i++) {
                    newSt.setString(1, (model.getElementAt(i)).getBarcode());
                    newSt.setFloat(2, (model.getElementAt(i)).getQuantity());
                    newSt.setString(3, (model.getElementAt(i)).getBarcode());
                    newSt.executeUpdate();
                }
                model.removeAllElements();
                setTotalPriceTextLabel();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void clearList() {
        if (!model.isEmpty()) {
            model.removeAllElements();
        }
        setTotalPriceTextLabel();
        barCodeToAdd.requestFocus();
        barCodeToAdd.setText("");
    }

    private void insertItemIntoList() {
        if (!barCodeToAdd.getText().equals("") && barCodeToAdd.getText() != null) {
            if (barCodeToAdd.getText().charAt(0) == 'x') {
                if (model.size() > 0) {
                    int selected = itemList.getSelectedIndex();
                    if (selected >= 0) {
                        if (Integer.valueOf(barCodeToAdd.getText().replace("x", "")) == 0) {
                            model.remove(selected);
                            setTotalPriceTextLabel();
                        } else {
                            ItemObject itemObject = model.getElementAt(selected);
                            model.set(selected, new ItemObject(itemObject.getName(), itemObject.getPrice(), itemObject.getImage(),
                                    Float.valueOf(barCodeToAdd.getText().replace("x", "")), itemObject.getBarcode()));
                            setTotalPriceTextLabel();
                        }
                    } else {
                        if (Float.valueOf(barCodeToAdd.getText().replace("x", "")) == 0) {
                            model.remove(model.indexOf(model.lastElement()));
                            setTotalPriceTextLabel();
                        } else {
                            ItemObject itemObject = model.lastElement();
                            model.set(model.indexOf(itemObject), new ItemObject(itemObject.getName(), itemObject.getPrice(), itemObject.getImage(),
                                    Float.valueOf(barCodeToAdd.getText().replace("x", "")), itemObject.getBarcode()));
                            setTotalPriceTextLabel();
                        }
                    }
                }
            } else {
                boolean hasInList = false;
                for (int i = 0; i < model.size(); i++) {
                    ItemObject itemObject = model.getElementAt(i);
                    if (itemObject.getBarcode().equals(barCodeToAdd.getText())) {
                        model.set(i, new ItemObject(itemObject.getName(), itemObject.getPrice(), itemObject.getImage(), itemObject.getQuantity() + 1, itemObject.getBarcode()));
                        setTotalPriceTextLabel();
                        hasInList = true;
                    }
                }
                if (!hasInList) {
                    try {
                        PreparedStatement newSt = st.getConnection().prepareStatement("SELECT * FROM stock WHERE barcode = ?");
                        newSt.setString(1, barCodeToAdd.getText());
                        ResultSet rs = newSt.executeQuery();
                        if (rs.next()) {
                            String description = rs.getNString(4);
                            Float price = rs.getFloat(10);
                            String imageBase64 = rs.getString(13);
                            model.addElement(new ItemObject(description, String.valueOf(price), imageBase64, (float) 1, barCodeToAdd.getText()));
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
        for (int i = 0; i < model.size(); i++) {
            price += (Float.valueOf((model.getElementAt(i)).getPrice()) * (model.getElementAt(i)).getQuantity());
        }
        totalPrice.setText(String.format("%.02f", (Math.ceil((price - (price * 0.05)) * 100)) / 100).replace(".", ","));
        total2Times.setText(String.format("2x R$ %.02f", (Math.ceil(((price - (price * 0.03)) / 2) * 100)) / 100).replace(".", ","));
        total3Times.setText(String.format("3x R$ %.02f", (Math.ceil((price / 3) * 100)) / 100).replace(".", ","));
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
        categoryCombo.setModel(new DefaultComboBoxModel<>(categories.toArray(new String[0])));
        categoryCombo.setLightWeightPopupEnabled(false);
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
        trademarkCombo.setModel(new DefaultComboBoxModel<>(trademarks.toArray(new String[0])));
        trademarkCombo.setLightWeightPopupEnabled(false);
    }

    private void deleteItemFromList() {
        if (itemList.getSelectedIndex() >= 0) {
            model.remove(itemList.getSelectedIndex());
            setTotalPriceTextLabel();
        }
    }

    private void addRowsToTable() {
        try {
            if (tableModel.getRowCount() > 0) {
                tableModel.setRowCount(0);
            }
            PreparedStatement newSt = st.getConnection().prepareStatement("SELECT * FROM  stock");
            ResultSet rs = newSt.executeQuery();
            Object[] stockItems = new Object[13];
            while (rs.next()) {
                if (rs.getString("image") != null) {
                    if (!rs.getString("image").equals("")) {
                        int width = ImageIO.read(new ByteArrayInputStream(new sun.misc.BASE64Decoder().decodeBuffer(rs.getString("image")))).getWidth(null);
                        int height = ImageIO.read(new ByteArrayInputStream(new sun.misc.BASE64Decoder().decodeBuffer(rs.getString("image")))).getHeight(null);
                        int greater;
                        if (width > height) greater = width;
                        else greater = height;
                        stockItems[0] = new ImageIcon(ImageIO.read(new ByteArrayInputStream(new sun.misc.BASE64Decoder().decodeBuffer(rs.getString("image"))))
                                .getScaledInstance((int) (60 * ((float) width / (float) greater)), (int) (60 * ((float) height / (float) greater)), Image.SCALE_SMOOTH));
                    }
                }
                stockItems[1] = rs.getString("description");
                stockItems[2] = rs.getString("color");
                stockItems[3] = rs.getString("start");
                stockItems[4] = rs.getString("category");
                stockItems[5] = rs.getString("trademark");
                stockItems[6] = rs.getFloat("weight");
                stockItems[7] = rs.getInt("meters");
                stockItems[8] = rs.getString("location");
                stockItems[9] = String.format("%.02f", rs.getFloat("price")).replace(".", ",");
                stockItems[10] = String.format("%.03f", rs.getFloat("quantity")).replace(".", ",");
                stockItems[11] = rs.getString("observation");
                stockItems[12] = rs.getString("barcode");
                tableModel.addRow(stockItems);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private void editItemAction() {
        if (stockTable.getSelectedRow() != -1) {
            jFrame.remove(stockListPanel);
            jFrame.add(stockPanel);
            tabs.invalidate();
            tabs.revalidate();
            try {
                isEdit = true;
                PreparedStatement newSt = st.getConnection().prepareStatement("SELECT * FROM stock WHERE barcode = ?");
                newSt.setString(1, (String) tableModel.getValueAt(stockTable.getSelectedRow(), 12));
                ResultSet rs = newSt.executeQuery();
                categoryCombo.removeActionListener(categoryListener);
                trademarkCombo.removeActionListener(trademarkListener);
                while (rs.next()) {
                    barCodeText.setText(rs.getString("barcode"));
                    colorText.setText(rs.getString("color"));
                    startText.setText(rs.getString("start"));
                    descriptionText.setText(rs.getString("description"));
                    categoryCombo.setSelectedItem(rs.getString("category"));
                    weightText.setText(String.valueOf(rs.getFloat("weight")).replace(".", ","));
                    trademarkCombo.setSelectedItem(rs.getString("trademark"));
                    metersText.setText(String.valueOf(rs.getInt("meters")));
                    localizationText.setText(rs.getString("location"));
                    priceText.setText(String.format("%.02f", rs.getFloat("price")).replace(".", ","));
                    quantityText.setText(String.valueOf(rs.getFloat("quantity")).replace(".", ","));
                    observationText.setText(rs.getString("observation"));
                    if (rs.getString("image") != null) {
                        if (!rs.getString("image").equals("")) {
                            int width = ImageIO.read(new ByteArrayInputStream(new sun.misc.BASE64Decoder().decodeBuffer(rs.getString("image")))).getWidth(null);
                            int height = ImageIO.read(new ByteArrayInputStream(new sun.misc.BASE64Decoder().decodeBuffer(rs.getString("image")))).getHeight(null);
                            int greater;
                            if (width > height) greater = width;
                            else greater = height;
                            photo.setText("                                                ");
                            photo.setName(rs.getString("image"));
                            photo.setIcon(new ImageIcon(ImageIO.read(new ByteArrayInputStream(new sun.misc.BASE64Decoder().decodeBuffer(rs.getString("image"))))
                                    .getScaledInstance((int) (150 * ((float) width / (float) greater)), (int) (150 * ((float) height / (float) greater)), Image.SCALE_SMOOTH)));
                            photo.setHorizontalTextPosition(JLabel.CENTER);
                            photo.setVerticalTextPosition(JLabel.CENTER);
                        }
                    }
                }
                categoryCombo.addActionListener(categoryListener);
                trademarkCombo.addActionListener(trademarkListener);
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void trademarkComboListener() {
        if (trademarkCombo.getSelectedIndex() == 0) {
            String marca = JOptionPane.showInputDialog(null, "Marca: ", "Inserir marca", JOptionPane.QUESTION_MESSAGE);
            try {
                if (marca != null) {
                    PreparedStatement novoSt = st.getConnection().prepareStatement("INSERT INTO trademark (trademark) VALUES (?)");
                    novoSt.setString(1, marca);
                    novoSt.executeUpdate();
                    setTrademarkCombo();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Marca já cadastrada");
            }
        } else if (trademarkCombo.getSelectedIndex() == trademarkCombo.getItemCount() - 1) {
            String marca = JOptionPane.showInputDialog(null, "Marca: ", "Excluir marca", JOptionPane.QUESTION_MESSAGE);
            try {
                PreparedStatement novoSt = st.getConnection().prepareStatement("DELETE FROM trademark WHERE trademark = ?");
                novoSt.setString(1, marca);
                novoSt.executeUpdate();
                setTrademarkCombo();
            } catch (SQLException ignored) {
            }
        } else {
            trademarkCombo.transferFocus();
        }
    }

    private void categoryComboListener() {
        if (categoryCombo.getSelectedIndex() == 0) {
            String marca = JOptionPane.showInputDialog(null, "Categoria: ", "Inserir categoria", JOptionPane.QUESTION_MESSAGE);
            try {
                if (marca != null) {
                    PreparedStatement novoSt = st.getConnection().prepareStatement("INSERT INTO category (category) VALUES (?)");
                    novoSt.setString(1, marca);
                    novoSt.executeUpdate();
                    setCategoryCombo();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Categoria já cadastrada");
            }
        } else if (categoryCombo.getSelectedIndex() == categoryCombo.getItemCount() - 1) {
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
    }

    private void clearAllFields() {
        categoryCombo.removeActionListener(categoryListener);
        trademarkCombo.removeActionListener(trademarkListener);
        isEdit = false;
        barCodeText.setText("");
        colorText.setText("");
        startText.setText("");
        descriptionText.setText("");
        categoryCombo.setSelectedIndex(0);
        weightText.setText("");
        trademarkCombo.setSelectedIndex(0);
        metersText.setText("");
        localizationText.setText("");
        priceText.setText("");
        quantityText.setText("");
        observationText.setText("");
        photo.setText(" Adicionar uma foto...");
        photo.setName("");
        photo.setIcon(null);
        photo.revalidate();
        categoryCombo.addActionListener(categoryListener);
        trademarkCombo.addActionListener(trademarkListener);
        barCodeText.requestFocus();
    }

    private void searchItems() {
        if (!searchText.getText().equals("")) {
            addRowsToTable();
            for (int i = tableModel.getRowCount() - 1; i > -1; i--) {
                boolean contains = false;
                for (int j = 1; j < tableModel.getColumnCount(); j++) {
                    if (String.valueOf(tableModel.getValueAt(i, j)).toUpperCase().contains(searchText.getText().toUpperCase())) {
                        contains = true;
                    }
                }
                if (!contains) {
                    tableModel.removeRow(i);
                }
            }
        } else {
            addRowsToTable();
        }
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
    private java.awt.TextField barCodeText;
    private java.awt.TextField barCodeToAdd;
    private javax.swing.JButton cancel;
    private javax.swing.JComboBox<String> categoryCombo;
    private java.awt.TextField startText;
    private javax.swing.JButton confirm;
    private javax.swing.JButton delete;
    private javax.swing.JButton search;
    private java.awt.TextField descriptionText;
    private javax.swing.JList itemList;
    private javax.swing.JScrollPane jScrollPane1;
    private java.awt.TextField localizationText;
    private java.awt.TextField metersText;
    private java.awt.TextField observationText;
    private java.awt.TextField priceText;
    private java.awt.TextField quantityText;
    private java.awt.TextField searchText;
    private javax.swing.JPanel salePanel;
    private java.awt.TextField colorText;
    private javax.swing.JPanel stockPanel;
    private javax.swing.JTabbedPane tabs;
    private java.awt.Label total;
    private java.awt.Label total2Times;
    private java.awt.Label total3Times;
    private java.awt.Label totalPrice;
    private javax.swing.JComboBox<String> trademarkCombo;
    private java.awt.TextField weightText;
    private javax.swing.JLabel photo;
    private javax.swing.JPanel stockListPanel;
    private javax.swing.JScrollPane stockListScroll;
    private javax.swing.JTable stockTable;
    private javax.swing.JButton addToStock;
    private javax.swing.JButton deleteItem;
    private javax.swing.JButton editItem;
    private javax.swing.JButton cancelUpdate;
    private DefaultListModel<ItemObject> model = new DefaultListModel<>();
    private DefaultTableModel tableModel = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private ActionListener categoryListener = actionEvent -> categoryComboListener();
    private ActionListener trademarkListener = actionEvent -> trademarkComboListener();
    private Statement st;
    private boolean isEdit = false;
    private JFrame jFrame;
}