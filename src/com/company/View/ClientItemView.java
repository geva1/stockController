package com.company.View;

import com.company.Database.DatabaseService;
import com.company.Interfaces.AddClientListener;
import com.company.Models.Client;
import murilo.libs.model.exception.ModelException;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class ClientItemView extends JFrame {
    private AddClientListener listener;
    private Boolean isEdit;
    private Client client;
    private DatabaseService database;
    
    ClientItemView(DatabaseService database, AddClientListener listener, Client client) {
        this.client = client;
        this.listener = listener;
        this.database = database;
        initComponents();
    }

    ClientItemView(DatabaseService database, Boolean isEdit, Client client) {
        this.client = client;
        this.database = database;
        this.isEdit = isEdit;
        initComponents();
    }
    
    @SuppressWarnings("unchecked")                  
    private void initComponents() {

        name = new Label();
        nameText = new TextField();
        cpf = new Label();
        cpfText = new TextField();
        cellphone = new Label();
        cellphoneText = new TextField();
        phone = new Label();
        phoneText = new TextField();
        email = new Label();
        emailText = new TextField();
        confirm = new JButton();
        cancel = new JButton();
        jLabel1 = new JLabel();
        address = new Label();
        addressText = new TextField();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        if(client != null && client.getName() != null) {
            nameText.setText(client.getName());
        }
        if(client != null && client.getCpf() != null) {
            cpfText.setText(client.getCpf());
        }
        if(client != null && client.getAdress() != null) {
            addressText.setText(client.getAdress());
        }
        if(client != null && client.getCellphone() != null) {
            cellphoneText.setText(client.getCellphone());
        }
        if(client != null && client.getEmail() != null) {
            emailText.setText(client.getEmail());
        }
        if(client != null && client.getPhone() != null) {
            phoneText.setText(client.getPhone());
        }

        name.setText("Nome completo*");

        cpf.setText("CPF*");

        cellphone.setText("Celular");

        phone.setText("Telefone");

        email.setText("Email");

        confirm.setText("Confirmar");
        confirm.addActionListener(actionEvent -> {
            ArrayList<String> isOk = verifyAllFields();
            if(isOk.size() == 0) {
                if (isEdit != null && isEdit) {
                    try {
                        client.setName(nameText.getText());
                        client.setAdress(addressText.getText());
                        client.setCellphone(cellphoneText.getText());
                        client.setPhone(phoneText.getText());
                        database.updateClient(client);
                    } catch (ModelException e) {
                        e.printStackTrace();
                    }
                } else {
                    client = new Client();
                    client.setName(nameText.getText());
                    client.setCpf(cpfText.getText());
                    client.setAdress(addressText.getText());
                    client.setCellphone(cellphoneText.getText());
                    client.setPhone(phoneText.getText());
                    if (listener != null) {
                        listener.setClient(client);
                    }
                    try {
                        client.setId(database.createClient(client));
                    } catch (ModelException e) {
                        e.printStackTrace();
                    }
                }
                dispose();
            } else {
                StringBuilder stringToShow = new StringBuilder("Preencha corretamente os campos: \n");
                for (String string : isOk) {
                    stringToShow.append(string).append("\n");
                }
                JOptionPane.showMessageDialog(null, stringToShow.toString());
            }
        });

        cancel.setText("Cancelar");
        cancel.addActionListener(actionEvent -> dispose());

        jLabel1.setText("* Campos obrigatórios");

        address.setText("Endereço");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(cancel)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(confirm))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(cpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(cellphone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(address, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(emailText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(nameText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                        .addComponent(cpfText, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                        .addComponent(cellphoneText, javax.swing.GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(phone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(phoneText, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(addressText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(nameText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(cpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(cpfText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(cellphone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(cellphoneText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(phone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(phoneText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(emailText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addComponent(address, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(addressText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(confirm)
                                        .addComponent(cancel)
                                        .addComponent(jLabel1))
                                .addContainerGap())
        );

        pack();
    }

    private ArrayList<String> verifyAllFields() {
        ArrayList<String> fields = new ArrayList<>();
        if (nameText.getText().equals("")) {
            fields.add("Nome");
        }
        if (cpfText.getText().equals("")) {
            fields.add("CPF");
        }
        return fields;
    }

    private Label cellphone;
    private TextField cellphoneText;
    private Label cpf;
    private TextField cpfText;
    private Label email;
    private TextField emailText;
    private JButton confirm;
    private JButton cancel;
    private JLabel jLabel1;
    private Label name;
    private TextField nameText;
    private Label phone;
    private TextField phoneText;
    private java.awt.Label address;
    private java.awt.TextField addressText;
}
