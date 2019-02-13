package com.company.View;

import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.swing.AutoCompleteSupport;
import com.company.AsyncFunctions.AddToListAsync;
import com.company.AsyncFunctions.AddToSaleListAsync;
import com.company.Database.DatabaseService;
import com.company.Interfaces.AddSaleToListListener;
import com.company.Models.Client;
import murilo.libs.model.exception.ModelException;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class SaleView extends JPanel implements AddSaleToListListener {
    private JPanel saleListPanel;
    private JTable saleTable;
    private TextField searchText;
    public JComboBox<String> searchDate;
    public DefaultTableModel tableModel = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    public DatabaseService database;
    private AddSaleToListListener listener = this;
    private StockView stockView;

    public SaleView(DatabaseService database, StockView stockView) {
        this.database = database;
        this.stockView = stockView;
        initComponents();
    }

    private void initComponents() {
        saleListPanel = new JPanel();
        saleTable = new JTable() {
            public Class getColumnClass(int column) {
                return Object.class;
            }
        };
        JButton addToStock = new JButton();
        JButton deleteItem = new JButton();
        JButton editItem = new JButton();
        searchText = new TextField();
        searchDate = new JComboBox<>();
        JButton search = new JButton();
        JScrollPane saleListScroll = new JScrollPane();

        saleTable.setRowHeight(30);

        addToStock.setText("Nova venda");
        addToStock.addActionListener(actionEvent -> {
            Integer result = JOptionPane.showConfirmDialog(null, "Deseja adicionar cliente a venda?", "Adicionar cliente", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                try {
                    JComboBox comboBox = new JComboBox();
                    ArrayList<Client> clients = (ArrayList<Client>) database.listClients();
                    ArrayList<String> strings = new ArrayList<>();
                    for (Client client : clients) {
                        strings.add(client.getName());
                    }
                    for (Client client : clients) {
                        strings.add(client.getCpf());
                    }
                    AutoCompleteSupport.install(comboBox, GlazedLists.eventListOf(strings.toArray(new String[0])));
                    comboBox.addAncestorListener(new AncestorListener() {
                        @Override
                        public void ancestorAdded(AncestorEvent ancestorEvent) {
                            comboBox.requestFocusInWindow();
                        }

                        @Override
                        public void ancestorRemoved(AncestorEvent ancestorEvent) {
                        }

                        @Override
                        public void ancestorMoved(AncestorEvent ancestorEvent) {
                        }
                    });
                    JOptionPane.showConfirmDialog(null, comboBox, "Cliente", JOptionPane.DEFAULT_OPTION);
                    if (comboBox.getSelectedItem() != null) {
                        Client cliente = new Client();
                        if (comboBox.getSelectedIndex() > 0) {
                            int index = comboBox.getSelectedIndex();
                            if(((String)comboBox.getSelectedItem()).matches("[0-9]+")) {
                                index -= strings.size()/2;
                            }
                            cliente = clients.get(index);
                        } else {
                            if(((String)comboBox.getSelectedItem()).matches("[0-9]+")) {
                                cliente.setCpf((String) comboBox.getSelectedItem());
                            } else {
                                cliente.setName((String) comboBox.getSelectedItem());
                            }
                        }
                        SaleItselfView frame = new SaleItselfView(database, cliente, listener);
                        frame.setVisible(true);
                    }
                } catch (ModelException e) {
                    e.printStackTrace();
                }
            } else {
                SaleItselfView frame = new SaleItselfView(database, null, listener);
                frame.setVisible(true);
            }
        });


        editItem.setText("Editar venda");
        editItem.addActionListener(actionEvent -> {
            if (saleTable.getSelectedRow() != -1) {//todo call edit sale screen
            }
        });

        deleteItem.setText("Excluir");
        deleteItem.addActionListener(actionEvent -> {
            if (saleTable.getSelectedRow() != -1) {
                Integer ok = JOptionPane.showConfirmDialog(null, "Você realmente gostaria de excluir a venda?",
                        "Excluir venda", JOptionPane.OK_CANCEL_OPTION);
                if (ok == JOptionPane.OK_OPTION) {
                    Integer cancel = JOptionPane.showConfirmDialog(null,
                            "Você gostaria de cancelar a venda e adicionar os itens ao estoque novamente?",
                            "Excluir venda", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (cancel == JOptionPane.NO_OPTION) {
                        Integer id = (Integer) tableModel.getValueAt(saleTable.getSelectedRow(), 3);
                        try {
                            database.deleteSale(id);
                            addSaleToList();
                        } catch (ModelException e) {
                            e.printStackTrace();
                        }
                    } else if (cancel == JOptionPane.YES_OPTION) {
                        Integer id = (Integer) tableModel.getValueAt(saleTable.getSelectedRow(), 3);
                        try {
                            database.cancelSale(id);
                            addSaleToList();
                        } catch (ModelException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        searchText.setFont(new java.awt.Font("Tahoma", Font.PLAIN, 20));
        searchText.addActionListener(actionEvent -> addSaleToList());
        search.addActionListener(actionEvent -> addSaleToList());

        tableModel.setColumnIdentifiers(new String[]{
                "Cliente", "Total", "Data", "Id"
        });

        saleTable.setModel(tableModel);
        saleListScroll.setViewportView(saleTable);
        saleTable.getColumnModel().getColumn(0).setPreferredWidth(700);
        saleTable.getColumnModel().getColumn(1).setPreferredWidth(50);
        saleTable.getColumnModel().getColumn(2).setPreferredWidth(50);
        saleTable.removeColumn(saleTable.getColumnModel().getColumn(3));
        saleTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        saleTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2 && saleTable.getSelectedRow() != -1) {
                    //todo call edit sale screen
                }
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(saleListPanel);
        saleListPanel.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(addToStock, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(editItem, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(deleteItem, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(searchText)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(searchDate, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                        .addComponent(saleListScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 1100, Short.MAX_VALUE)
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
                                        .addComponent(searchDate, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(search, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                                .addComponent(saleListScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 455, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        ImageIcon ii = new ImageIcon("iconImages/magnifying_glass.png");
        search.setIcon(new ImageIcon(ii.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH)));

        addSaleToList();
    }
    @Override
    public void addSaleToList() {
        new AddToSaleListAsync(this, searchText.getText(), (String) searchDate.getSelectedItem()).execute();
        stockView.addRowsToTable();
    }

    public JPanel getSaleListPanel() {
        return saleListPanel;
    }
}
