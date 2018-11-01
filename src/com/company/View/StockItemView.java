package com.company.View;

import com.company.Database.DatabaseService;
import com.company.Interfaces.AddItemsToTableListener;
import com.company.Models.Stock;
import murilo.libs.model.exception.ModelException;
import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

class StockItemView extends JFrame {
    private File imageFile;
    private JPanel stockPanel = new JPanel();
    private JButton addToDb;
    private JComboBox<String> trademarkCombo;
    private TextField weightText;
    private JLabel photo;
    private TextField colorText;
    private JButton cancelUpdate;
    private TextField localizationText;
    private TextField metersText;
    private TextField observationText;
    private TextField priceText;
    private TextField quantityText;
    private TextField descriptionText;
    private JComboBox<String> categoryCombo;
    private TextField startText;
    private TextField barCodeText;
    private DatabaseService database;
    private Integer itemId;
    private AddItemsToTableListener listener;

    StockItemView(DatabaseService database, AddItemsToTableListener listener) {
        this.database = database;
        this.listener = listener;
        initComponents();
    }

    StockItemView(DatabaseService database, AddItemsToTableListener listener, String barcode, String start) {
        this.database = database;
        this.listener = listener;
        initComponents();
        setValues(barcode, start);
    }

    private void setValues(String barcode, String start) {
        try {
            itemId = database.getItemId(barcode, start);
            Stock stock = database.getItem(itemId);
            barCodeText.setText(stock.getBarcode());
            colorText.setText(stock.getColor());
            startText.setText(stock.getStart());
            descriptionText.setText(stock.getDescription());
            categoryCombo.setSelectedItem(stock.getCategory());
            weightText.setText(String.valueOf(stock.getWeight()).replace(".", ","));
            trademarkCombo.setSelectedItem(stock.getTrademark());
            metersText.setText(String.valueOf(stock.getMeters()));
            localizationText.setText(stock.getLocation());
            priceText.setText(String.format("%.02f", stock.getPrice()).replace(".", ","));
            quantityText.setText(String.valueOf(stock.getQuantity()).replace(".", ","));
            observationText.setText(stock.getObservation());
            imageFile = new File(stock.getImage());
            BufferedImage imageIO = ImageIO.read(imageFile);
            int width = imageIO.getWidth(null);
            int height = imageIO.getHeight(null);
            int greater;
            if (width > height) greater = width;
            else greater = height;
            height = (int) (150 * ((float) height / (float) greater));
            width = (int) (150 * ((float) width / (float) greater));
            photo.setText("                                                ");
            photo.setName(stock.getImage());
            photo.setIcon(new ImageIcon(imageIO.getScaledInstance(width, height, Image.SCALE_SMOOTH)));
            photo.setHorizontalTextPosition(JLabel.CENTER);
            photo.setVerticalTextPosition(JLabel.CENTER);
        } catch (ModelException | IOException e) {
            e.printStackTrace();
        }
    }

    private void saveNewPicture() {
        File output = new File(photo.getName());
        try {
            FileUtils.copyFile(imageFile, output);
        } catch (IOException ignored) {}
    }

    private void initListeners() {
        photo.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                JFileChooser jf = new JFileChooser();
                int result = jf.showSaveDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    photo.setName("pictures/" + jf.getSelectedFile().getName());
                    imageFile = jf.getSelectedFile();
                    try {
                        BufferedImage imageIO = ImageIO.read(imageFile);
                        int width = imageIO.getWidth(null);
                        int height = imageIO.getHeight(null);
                        int greater;
                        if (width > height) greater = width;
                        else greater = height;
                        height = (int) (150 * ((float) height / (float) greater));
                        width = (int) (150 * ((float) width / (float) greater));
                        photo.setText("                                                ");
                        photo.setIcon(new ImageIcon(imageIO.getScaledInstance(width, height, Image.SCALE_SMOOTH)));
                        photo.setHorizontalTextPosition(JLabel.CENTER);
                        photo.setVerticalTextPosition(JLabel.CENTER);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        addToDb.addActionListener(actionEvent -> addToDbActionPerformed());

        cancelUpdate.addActionListener(actionEvent -> dispose());

        trademarkCombo.addActionListener(evt -> trademarkComboListener());
        categoryCombo.addActionListener(evt -> categoryComboListener());
    }

    private void addToDbActionPerformed() {
        ArrayList<String> isOk = verifyAllFields();
        if (isOk.size() == 0) {
            Stock itemToAdd = new Stock(null, String.valueOf(barCodeText.getText()), colorText.getText(),
                    startText.getText(), descriptionText.getText(), Double.valueOf(weightText.getText().replace(",", ".")), Integer.valueOf(metersText.getText()),
                    localizationText.getText(), Double.valueOf(priceText.getText().replace(",", ".")), Double.valueOf(quantityText.getText().replace(",", ".")),
                    observationText.getText(), photo.getName(), String.valueOf(trademarkCombo.getSelectedItem()), String.valueOf(categoryCombo.getSelectedItem()));
            try {
                if (itemId == null) {
                    if (database.getItemId(barCodeText.getText(), startText.getText()) != null) {
                        JOptionPane.showMessageDialog(null, "Esse código de barras já foi cadastrado");
                        return;
                    } else {
                        database.createItem(itemToAdd);
                    }
                } else {
                    itemToAdd.setId(itemId);
                    database.updateItem(itemToAdd);
                }
                saveNewPicture();
                dispose();
                listener.addItemsToTable();
            } catch (ModelException e) {
                e.printStackTrace();
            }
        } else {
            StringBuilder stringToShow = new StringBuilder("Preencha corretamente os campos: \n");
            for (String string : isOk) {
                stringToShow.append(string).append("\n");
            }
            JOptionPane.showMessageDialog(null, stringToShow.toString());
        }
    }

    private ArrayList<String> verifyAllFields() {
        ArrayList<String> fields = new ArrayList<>();
        if (barCodeText.getText().equals("")) {
            fields.add("Código de barras");
        }
        if (colorText.getText().equals("")) {
            fields.add("Cor");
        }
        if (startText.getText().equals("")) {
            fields.add("Partida");
        }
        if (descriptionText.getText().equals("")) {
            fields.add("Descrição");
        }
        if (String.valueOf(categoryCombo.getSelectedItem()).equals("Nova categoria...") ||
                String.valueOf(categoryCombo.getSelectedItem()).equals("Excluir categoria...")) {
            fields.add("Categoria");
        }
        if (String.valueOf(trademarkCombo.getSelectedItem()).equals("Nova marca...") ||
                String.valueOf(trademarkCombo.getSelectedItem()).equals("Excluir marca...")) {
            fields.add("Marca");
        }
        if (!weightText.getText().matches("^\\d+([./,]\\d{0,3}?)?$")) {
            fields.add("Peso");
        }
        if (!metersText.getText().matches("^\\d+?$")) {
            fields.add("Metros");
        }
        if (!priceText.getText().matches("^\\d+[./,]\\d{2}?$")) {
            fields.add("Preço de venda");
        }
        if (!quantityText.getText().matches("^\\d+([./,]\\d{0,3}?)?$")) {
            fields.add("Estoque");
        }
        if (observationText.getText().equals("")) {
            fields.add("Observações");
        }
        if (photo.getName().equals("")) {
            fields.add("Foto");
        }
        return fields;
    }

    private void setCategoryCombo() {
        ArrayList<String> categories = new ArrayList<>();
        categories.add("Nova categoria...");
        try {
            categories.addAll(database.listCategories());
        } catch (ModelException e) {
            e.printStackTrace();
        }
        categories.add("Excluir categoria...");
        categoryCombo.setModel(new DefaultComboBoxModel<>(categories.toArray(new String[0])));
        categoryCombo.setLightWeightPopupEnabled(false);
    }

    private void setTrademarkCombo() {
        ArrayList<String> trademarks = new ArrayList<>();
        trademarks.add("Nova marca...");
        try {
            trademarks.addAll(database.listTrademarks());
        } catch (ModelException e) {
            e.printStackTrace();
        }
        trademarks.add("Excluir marca...");
        trademarkCombo.setModel(new DefaultComboBoxModel<>(trademarks.toArray(new String[0])));
        trademarkCombo.setLightWeightPopupEnabled(false);
    }

    private void trademarkComboListener() {
        if (trademarkCombo.getSelectedIndex() == 0) {
            String marca = JOptionPane.showInputDialog(null, "Marca: ", "Inserir marca", JOptionPane.QUESTION_MESSAGE);
            try {
                if (marca != null) {
                    database.createTrademark(marca);
                    setTrademarkCombo();
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Marca já cadastrada");
            }
        } else if (trademarkCombo.getSelectedIndex() == trademarkCombo.getItemCount() - 1) {
            String marca = JOptionPane.showInputDialog(null, "Marca: ", "Excluir marca", JOptionPane.QUESTION_MESSAGE);
            try {
                database.deleteTrademark(marca);
                setTrademarkCombo();
            } catch (ModelException ignored) {
            }
        } else {
            trademarkCombo.transferFocus();
        }
    }

    private void categoryComboListener() {
        if (categoryCombo.getSelectedIndex() == 0) {
            String marca = JOptionPane.showInputDialog(null, "Categoria: ", "Inserir categoria",
                    JOptionPane.QUESTION_MESSAGE);
            try {
                if (marca != null) {
                    database.createCategory(marca);
                    setCategoryCombo();
                }
            } catch (ModelException e) {
                JOptionPane.showMessageDialog(null, "Categoria já cadastrada");
            }
        } else if (categoryCombo.getSelectedIndex() == categoryCombo.getItemCount() - 1) {
            String marca = JOptionPane.showInputDialog(null, "Categoria: ", "Excluir categoria",
                    JOptionPane.QUESTION_MESSAGE);
            try {
                database.deleteCategory(marca);
                setCategoryCombo();
            } catch (ModelException e) {
                JOptionPane.showMessageDialog(null, "Categoria não existente");
            }
        } else {
            categoryCombo.transferFocus();
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        barCodeText = new TextField();
        startText = new TextField();
        colorText = new TextField();
        descriptionText = new TextField();
        Label barCode = new Label();
        Label start = new Label();
        Label color = new Label();
        Label description = new Label();
        Label category = new Label();
        Label trademark = new Label();
        addToDb = new JButton();
        categoryCombo = new JComboBox();
        trademarkCombo = new JComboBox();
        Label localization = new Label();
        Label price = new Label();
        Label weight = new Label();
        Label meters = new Label();
        Label observations = new Label();
        weightText = new TextField();
        metersText = new TextField();
        localizationText = new TextField();
        priceText = new TextField();
        observationText = new TextField();
        Label quantity = new Label();
        quantityText = new TextField();
        photo = new JLabel();
        cancelUpdate = new JButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        barCode.setText("Código de barras");
        color.setText("Cor");
        start.setText("Partida");
        description.setText("Descrição");
        category.setText("Categoria");
        trademark.setText("Marca");
        localization.setText("Localização");
        price.setText("Preço de venda");
        weight.setText("Peso");
        meters.setText("Metros");
        observations.setText("Observações");
        quantity.setText("Estoque atual");

        photo.setFont(new Font("Arial", Font.PLAIN, 12));
        photo.setText(" Adicionar uma foto...");
        photo.setToolTipText("");
        photo.setFocusable(true);
        photo.setBorder(BorderFactory.createLineBorder(Color.black));
        photo.setHorizontalTextPosition(JLabel.CENTER);

        addToDb.setFont(new Font("Arial", Font.PLAIN, 18));
        addToDb.setText("Salvar");

        cancelUpdate.setFont(new Font("Arial", Font.PLAIN, 18));
        cancelUpdate.setText("Cancelar");

        barCodeText.addActionListener(evt -> barCodeText.transferFocus());
        startText.addActionListener(evt -> startText.transferFocus());
        colorText.addActionListener(evt -> colorText.transferFocus());
        descriptionText.addActionListener(evt -> descriptionText.transferFocus());
        weightText.addActionListener(evt -> weightText.transferFocus());
        metersText.addActionListener(evt -> metersText.transferFocus());
        localizationText.addActionListener(evt -> localizationText.transferFocus());
        priceText.addActionListener(evt -> priceText.transferFocus());
        observationText.addActionListener(evt -> observationText.transferFocus());
        quantityText.addActionListener(evt -> quantityText.transferFocus());

        GroupLayout stockPanelLayout = new GroupLayout(stockPanel);
        stockPanel.setLayout(stockPanelLayout);
        stockPanelLayout.setHorizontalGroup(
                stockPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(stockPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(stockPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(GroupLayout.Alignment.TRAILING, stockPanelLayout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addGroup(stockPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addGroup(stockPanelLayout.createSequentialGroup()
                                                                .addComponent(cancelUpdate, GroupLayout.PREFERRED_SIZE, 132, GroupLayout.PREFERRED_SIZE)
                                                                .addGap(15, 15, 15)
                                                                .addComponent(addToDb, GroupLayout.PREFERRED_SIZE, 132, GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(startText, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(stockPanelLayout.createSequentialGroup()
                                                .addComponent(description, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(stockPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                        .addComponent(descriptionText, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addGroup(GroupLayout.Alignment.LEADING, stockPanelLayout.createSequentialGroup()
                                                                .addGap(33, 33, 33)
                                                                .addGroup(stockPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                        .addComponent(categoryCombo, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(trademarkCombo, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE))
                                                                .addGap(0, 0, Short.MAX_VALUE))))
                                        .addGroup(stockPanelLayout.createSequentialGroup()
                                                .addGroup(stockPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addGroup(stockPanelLayout.createSequentialGroup()
                                                                .addComponent(barCode, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(barCodeText, GroupLayout.PREFERRED_SIZE, 132, GroupLayout.PREFERRED_SIZE))
                                                        .addComponent(category, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(trademark, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(quantity, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                .addGap(0, 0, Short.MAX_VALUE))
                                        .addGroup(stockPanelLayout.createSequentialGroup()
                                                .addGroup(stockPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(localization, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(price, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(observations, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                .addGap(15, 15, 15)
                                                .addGroup(stockPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(observationText, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addGroup(stockPanelLayout.createSequentialGroup()
                                                                .addGroup(stockPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                                        .addComponent(quantityText, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE)
                                                                        .addGroup(stockPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                                                                .addComponent(localizationText, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                                .addComponent(priceText, GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)))
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 662, Short.MAX_VALUE)
                                                                .addGroup(stockPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                        .addGroup(GroupLayout.Alignment.TRAILING, stockPanelLayout.createSequentialGroup()
                                                                                .addGroup(stockPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                                        .addComponent(start, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                                        .addGroup(stockPanelLayout.createSequentialGroup()
                                                                                                .addComponent(color, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, 18)
                                                                                                .addComponent(colorText, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE))))
                                                                        .addGroup(GroupLayout.Alignment.TRAILING, stockPanelLayout.createSequentialGroup()
                                                                                .addGroup(stockPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                                        .addComponent(weight, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                                        .addComponent(meters, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                                                .addGroup(stockPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                                        .addComponent(metersText, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE)
                                                                                        .addComponent(weightText, GroupLayout.PREFERRED_SIZE, 129, GroupLayout.PREFERRED_SIZE)))))))
                                        .addComponent(photo, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );
        stockPanelLayout.setVerticalGroup(
                stockPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(stockPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(stockPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(barCodeText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(barCode, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(colorText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(color, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(stockPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(startText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(start, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(stockPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addGroup(stockPanelLayout.createSequentialGroup()
                                                .addGroup(stockPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addGroup(stockPanelLayout.createSequentialGroup()
                                                                .addComponent(description, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(category, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(stockPanelLayout.createSequentialGroup()
                                                                .addComponent(descriptionText, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(stockPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                        .addGroup(stockPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                                                .addComponent(categoryCombo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(weightText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                                        .addComponent(weight, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(stockPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addGroup(stockPanelLayout.createSequentialGroup()
                                                                .addGroup(stockPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                        .addComponent(trademarkCombo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(trademark, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(meters, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(stockPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                                        .addComponent(localization, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(localizationText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                                        .addComponent(metersText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(stockPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(priceText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(price, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(quantity, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(quantityText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(stockPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(stockPanelLayout.createSequentialGroup()
                                                .addComponent(observations, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 139, Short.MAX_VALUE)
                                                .addGroup(stockPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(addToDb, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(cancelUpdate, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(stockPanelLayout.createSequentialGroup()
                                                .addComponent(observationText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(photo, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(stockPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(stockPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        initListeners();

        setTrademarkCombo();
        setCategoryCombo();

        pack();
    }
}
