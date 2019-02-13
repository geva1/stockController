package com.company.View;

import com.company.AsyncFunctions.AddToClientListAsync;
import com.company.Database.DatabaseService;
import com.company.Interfaces.AddClientToListListener;
import com.company.Models.Client;
import murilo.libs.model.exception.ModelException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;

public class ClientView extends JPanel implements AddClientToListListener {
    public DatabaseService database;
    public DefaultTableModel tableModel = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public ClientView(DatabaseService database) {
        this.database = database;
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        addToDb = new JButton();
        editClient = new JButton();
        deleteClient = new JButton();
        JButton searchButton = new JButton();
        searchText = new TextField();
        JScrollPane clientListScroll = new JScrollPane();
        clientTable = new JTable();

        clientTable.setRowHeight(30);

        addToDb.setText("Adicionar...");
        addToDb.addActionListener(actionEvent -> {
            ClientItemView frame = new ClientItemView(database,this, false, new Client());
            frame.setVisible(true);
        });

        editClient.setText("Editar...");
        editClient.addActionListener(actionEvent -> {
            if(clientTable.getSelectedRow() != -1) {
                int row = clientTable.getSelectedRow();
                Client client = new Client((String) tableModel.getValueAt(row, 0), (String) tableModel.getValueAt(row,1),
                        (String) tableModel.getValueAt(row,2), (String) tableModel.getValueAt(row,3),
                        (String)  tableModel.getValueAt(row,4), (String) tableModel.getValueAt(row,5));
                ClientItemView frame = new ClientItemView(database, this, false, client);
                frame.setVisible(true);
            }
        });

        deleteClient.setText("Excluir");
        deleteClient.addActionListener(actionEvent -> {
            if (clientTable.getSelectedRow() != -1) {
                Integer ok = JOptionPane.showConfirmDialog(null, "Você realmente gostaria de excluir o cliente?", "Excluir cliente", JOptionPane.OK_CANCEL_OPTION);
                if(ok == JOptionPane.OK_OPTION) {
                    try {
                        database.deleteClient(database.getClientId((String) tableModel.getValueAt(clientTable.getSelectedRow(), 0)));
                        addToClientList();
                    } catch (ModelException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        searchText.setFont(new Font("Tahoma", Font.PLAIN, 20)); // NOI18N

        tableModel.setColumnIdentifiers(new String[] {"CPF", "Nome", "Endereço", "Telefone", "Celular", "Email"});

        clientTable.setModel(tableModel);
        clientListScroll.setViewportView(clientTable);

        clientTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        clientTable.getColumnModel().getColumn(1).setPreferredWidth(400);
        clientTable.getColumnModel().getColumn(2).setPreferredWidth(200);
        clientTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        clientTable.getColumnModel().getColumn(4).setPreferredWidth(100);
        clientTable.getColumnModel().getColumn(5).setPreferredWidth(210);

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(addToDb, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(editClient, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(deleteClient, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(searchText, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(searchButton, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                        .addComponent(clientListScroll, GroupLayout.DEFAULT_SIZE, 1100, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(deleteClient, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(editClient, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(addToDb, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(searchButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(searchText, GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                                .addComponent(clientListScroll, GroupLayout.DEFAULT_SIZE, 455, GroupLayout.PREFERRED_SIZE))
        );

        ImageIcon ii = new ImageIcon("iconImages/magnifying_glass.png");
        searchButton.setIcon(new ImageIcon(ii.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH)));

        addToClientList();
    }

    private void addToClientList() {
        new AddToClientListAsync(this, searchText.getText()).execute();
    }

    @Override
    public void addClientToList() {
        addToClientList();
    }

    private JButton addToDb;
    private JTable clientTable;
    private JButton deleteClient;
    private JButton editClient;
    private TextField searchText;
}
