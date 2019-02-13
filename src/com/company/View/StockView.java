package com.company.View;

import com.company.AsyncFunctions.AddToListAsync;
import com.company.Database.DatabaseService;
import com.company.Interfaces.AddItemsToTableListener;
import murilo.libs.model.exception.ModelException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StockView extends JPanel implements AddItemsToTableListener {
    private javax.swing.JPanel stockListPanel;
    private javax.swing.JTable stockTable;
    private java.awt.TextField searchText;
    public DefaultTableModel tableModel = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    public DatabaseService database;
    private AddItemsToTableListener stockView = this;


    public StockView(DatabaseService database) {
        this.database = database;
        initComponents();
    }

    private void initComponents() {
        JButton search = new JButton();
        searchText = new java.awt.TextField();
        stockListPanel = new javax.swing.JPanel();
        JScrollPane stockListScroll = new JScrollPane();
        JButton addToStock = new JButton();
        JButton editItem = new JButton();
        JButton deleteItem = new JButton();
        stockTable = new javax.swing.JTable() {
            public Class getColumnClass(int column) {
                return (column == 0) ? Icon.class : Object.class;
            }
        };
        stockTable.setRowHeight(60);

        addToStock.setText("Adicionar...");
        addToStock.addActionListener(actionEvent -> {
            StockItemView frame = new StockItemView(database, stockView);
            frame.setVisible(true);
        });


        editItem.setText("Editar...");
        editItem.addActionListener(actionEvent -> {
            if (stockTable.getSelectedRow() != -1) {
                String barcode = (String) tableModel.getValueAt(stockTable.getSelectedRow(), 12);
                String start = (String) tableModel.getValueAt(stockTable.getSelectedRow(), 3);
                StockItemView frame = new StockItemView(database, stockView, barcode, start);
                frame.setVisible(true);
            }
        });

        deleteItem.setText("Excluir");
        deleteItem.addActionListener(actionEvent -> {
            if (stockTable.getSelectedRow() != -1) {
                Integer ok = JOptionPane.showConfirmDialog(null, "Você realmente gostaria de excluir o item?", "Excluir item", JOptionPane.OK_CANCEL_OPTION);
                if(ok == JOptionPane.OK_OPTION) {
                    String barcode = (String) tableModel.getValueAt(stockTable.getSelectedRow(), 12);
                    String start = (String) tableModel.getValueAt(stockTable.getSelectedRow(), 3);
                    try {
                        database.deleteItem(database.getItemId(barcode, start));
                        addRowsToTable();
                    } catch (ModelException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        searchText.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 20));
        searchText.addActionListener(actionEvent -> addRowsToTable());
        search.addActionListener(actionEvent -> addRowsToTable());

        tableModel.setColumnIdentifiers(new String[]{
                "Foto", "Descrição", "Cor", "Partida", "Categoria", "Marca", "Peso", "Metros", "Localização", "Preço",
                "Estoque", "Observações", "Código de barras"
        });

        stockTable.setModel(tableModel);
        stockListScroll.setViewportView(stockTable);

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
        stockTable.getColumnModel().getColumn(11).setPreferredWidth(168);
        stockTable.getColumnModel().getColumn(12).setPreferredWidth(100);
        stockTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        stockTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2 && stockTable.getSelectedRow() != -1) {
                    String barcode = (String) tableModel.getValueAt(stockTable.getSelectedRow(), 12);
                    String start = (String) tableModel.getValueAt(stockTable.getSelectedRow(), 3);
                    StockItemView frame = new StockItemView(database, stockView, barcode, start);
                    frame.setVisible(true);
                }
            }
        });

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

        addRowsToTable();
    }

    public JPanel getStockListPanel() {
        return stockListPanel;
    }

    public void addRowsToTable() {
        new AddToListAsync(this, searchText.getText()).execute();
    }

    @Override
    public void addItemsToTable() {
        addRowsToTable();
    }
}
