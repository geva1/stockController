package com.company.View;

import com.company.Database.DatabaseService;
import com.company.ItemRenderer;
import com.company.Models.Client;
import com.company.Models.Stock;
import com.company.Objects.ItemObject;
import murilo.libs.model.exception.ModelException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

class SaleItselfView extends JFrame {
    private TextField barCodeToAdd;
    private JList itemList;
    private JPanel salePanel;
    private Label total2Times;
    private Label total3Times;
    private Label totalPrice;
    private DatabaseService database;
    private DefaultListModel<ItemObject> model = new DefaultListModel<>();
    private Client client;

    SaleItselfView(DatabaseService database) {
        this.database = database;
        initComponents();
    }

    SaleItselfView(DatabaseService database, Client client) {
        this.database = database;
        this.client = client;
        setTitle(client.getName());
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        salePanel = new JPanel();
        JScrollPane jScrollPane1 = new JScrollPane();
        itemList = new JList();
        JButton confirm = new JButton();
        JButton cancel = new JButton();
        barCodeToAdd = new TextField();
        JButton addToList = new JButton();
        Label total = new Label();
        total2Times = new Label();
        total3Times = new Label();
        totalPrice = new Label();
        JButton delete = new JButton();

        itemList.setFont(new Font("Arial", Font.PLAIN, 24));
        itemList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                if (mouseEvent.getClickCount() == 2) {
                    String quantidade = JOptionPane.showInputDialog(null, "Quantidade: ", "Atualizar quantidade", JOptionPane.QUESTION_MESSAGE);
                    if (quantidade != null) {
                        ItemObject itemObject = model.getElementAt(itemList.getSelectedIndex());
                        model.set(itemList.getSelectedIndex(), new ItemObject(itemObject.getName(), itemObject.getPrice(),
                                itemObject.getImage(),
                                Float.valueOf(quantidade.replace(",", ".")), itemObject.getBarcode(), itemObject.getStart()));
                        setTotalPriceTextLabel();
                    }
                }
            }
        });
        jScrollPane1.setViewportView(itemList);

        confirm.setText("Confirmar");
        confirm.addActionListener(actionEvent -> {
            setVisible(false);
            SaleFinishView f = new SaleFinishView(database, client, model);
            f.setVisible(true);
            JFrame thisFrame = this;
            f.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent windowEvent) {
                    boolean confirmed = f.getConfirmation();
                    if(!confirmed) {
                        thisFrame.setVisible(true);
                    }
                    super.windowClosed(windowEvent);
                }
            });
        });

        delete.setText("Exluir item");
        delete.addActionListener(actionEvent -> deleteItemFromList());

        cancel.setText("Cancelar");
        cancel.addActionListener(actionEvent -> dispose());

        barCodeToAdd.addActionListener(actionEvent -> insertItemIntoList());

        addToList.setText("Adicionar");
        addToList.addActionListener(actionEvent -> insertItemIntoList());

        total.setFont(new Font("Arial", Font.PLAIN, 48));
        total.setText("/ R$ ");

        totalPrice.setFont(new Font("Arial", Font.PLAIN, 48));
        totalPrice.setText("0,00");

        total2Times.setFont(new Font("Arial", Font.PLAIN, 48));
        total2Times.setText("/ 2x R$ 0,00");
        total3Times.setFont(new Font("Arial", Font.PLAIN, 48));
        total3Times.setText("3x R$ 0,00");

        GroupLayout salePanelLayout = new GroupLayout(salePanel);
        salePanel.setLayout(salePanelLayout);
        salePanelLayout.setHorizontalGroup(
                salePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1)
                        .addGroup(salePanelLayout.createSequentialGroup()
                                .addGroup(salePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(GroupLayout.Alignment.TRAILING, salePanelLayout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(delete)
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(cancel)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(confirm))
                                        .addGroup(salePanelLayout.createSequentialGroup()
                                                .addGap(111, 297, Short.MAX_VALUE)
                                                .addComponent(total3Times, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(total2Times, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(total, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(totalPrice, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(salePanelLayout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(barCodeToAdd, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(addToList)))
                                .addContainerGap())
        );
        salePanelLayout.setVerticalGroup(
                salePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(salePanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(salePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(addToList, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(barCodeToAdd, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 308, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                                .addGroup(salePanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(total, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(totalPrice, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(total3Times, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(total2Times, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(salePanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(confirm)
                                        .addComponent(cancel)
                                        .addComponent(delete))
                                .addContainerGap())
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(salePanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(salePanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        itemList.setCellRenderer(new ItemRenderer());
        itemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        itemList.setModel(model);

        pack();
    }

    private void setTotalPriceTextLabel() {
        float price = 0;
        for (int i = 0; i < model.size(); i++) {
            price += (Float.valueOf((model.getElementAt(i)).getPrice()) * (model.getElementAt(i)).getQuantity());
        }
        totalPrice.setText(String.format("%.02f", (Math.ceil((price - (price * 0.05)) * 100)) / 100)
                .replace(".", ","));
        total2Times.setText(String.format("/ 2x R$ %.02f", (Math.ceil(((price - (price * 0.03)) / 2) * 100)) / 100)
                .replace(".", ","));
        total3Times.setText(String.format("3x R$ %.02f", (Math.ceil((price / 3) * 100)) / 100)
                .replace(".", ","));
        salePanel.validate();
    }

    private void insertItemIntoList() {
        if (!barCodeToAdd.getText().equals("") && barCodeToAdd.getText() != null) {
            ArrayList<Stock> stockItems = new ArrayList<>();
            try {
                stockItems.addAll(database.getItemByBarcode(barCodeToAdd.getText()));
            } catch (ModelException e) {
                e.printStackTrace();
            }
            boolean hasInList = false;
            for (int i = 0; i < model.size(); i++) {
                ItemObject itemObject = model.getElementAt(i);
                if (itemObject.getBarcode().equals(barCodeToAdd.getText()) && stockItems.size() < 2) {
                    model.set(i, new ItemObject(itemObject.getName(), itemObject.getPrice(), itemObject.getImage(),
                            itemObject.getQuantity() + 1, itemObject.getBarcode(), itemObject.getStart()));
                    setTotalPriceTextLabel();
                    hasInList = true;
                    break;
                }
            }
            if (!hasInList) {
                    if (stockItems.size() == 1) {
                        String description = stockItems.get(0).getDescription();
                        Double price = stockItems.get(0).getPrice();
                        String imageUrl = stockItems.get(0).getImage();
                        String start = stockItems.get(0).getStart();
                        model.addElement(new ItemObject(description, String.valueOf(price), imageUrl,
                                (float) 1, barCodeToAdd.getText(), start));
                        setTotalPriceTextLabel();
                    } else if(stockItems.size() > 1) {
                        //todo: ask item start
                    } else {
                        JOptionPane.showMessageDialog(null, "Esse item não existe no estoque");
                    }
            }
        }
        barCodeToAdd.requestFocus();
        barCodeToAdd.setText("");
    }

    private void deleteItemFromList() {
        if (itemList.getSelectedIndex() >= 0) {
            model.remove(itemList.getSelectedIndex());
            setTotalPriceTextLabel();
        }
        barCodeToAdd.requestFocus();
    }
}
