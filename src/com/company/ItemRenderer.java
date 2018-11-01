package com.company;

import com.company.Objects.ItemObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

public class ItemRenderer extends JPanel implements ListCellRenderer<ItemObject> {
    private JLabel lbIcon = new JLabel();
    private JLabel lbName = new JLabel();
    private JLabel lbPrice = new JLabel();
    private JLabel lbTotalPrice = new JLabel();
    private JLabel lbQuantity = new JLabel();
    private JPanel panelText = new JPanel(new BorderLayout(3, 20));
    private JPanel panelPrice = new JPanel(new BorderLayout(2, 20));

    public ItemRenderer() {
        lbName.setFont(new java.awt.Font("Arial", Font.PLAIN, 28)); // NOI18N
        lbPrice.setFont(new java.awt.Font("Arial", Font.PLAIN, 28)); // NOI18N
        lbTotalPrice.setFont(new java.awt.Font("Arial", Font.PLAIN, 28)); // NOI18N
        lbQuantity.setFont(new java.awt.Font("Arial", Font.PLAIN, 14)); // NOI18N

        lbIcon.setMinimumSize(new Dimension(60,60));
        lbIcon.setPreferredSize(new Dimension(60,60));
        lbIcon.setMaximumSize(new Dimension(60,60));
        setLayout(new BorderLayout(2, 20));

        panelText.add(lbName, BorderLayout.WEST);
        panelText.add(lbQuantity, BorderLayout.CENTER);
        panelPrice.add(lbTotalPrice, BorderLayout.EAST);
        panelPrice.add(lbPrice, BorderLayout.WEST);
        panelText.add(panelPrice, BorderLayout.EAST);
        add(lbIcon, BorderLayout.WEST);
        add(panelText, BorderLayout.CENTER);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends ItemObject> list, ItemObject itemObject, int i, boolean b, boolean b1) {
        try {
            if(itemObject.getImage() != null) {
                BufferedImage imageIO = ImageIO.read(new File(itemObject.getImage()));
                int width = imageIO.getWidth(null);
                int height = imageIO.getHeight(null);
                int greater;
                if(width > height) greater = width;
                else greater = height;
                height = (int) (60 * ((float) height / (float) greater));
                width = (int) (60 * ((float) width / (float) greater));
                lbIcon.setText("                   ");
                lbIcon.setIcon(new ImageIcon(imageIO.getScaledInstance(width, height, Image.SCALE_SMOOTH)));
                lbIcon.setHorizontalTextPosition(JLabel.CENTER);
                lbIcon.setVerticalTextPosition(JLabel.CENTER);
                lbIcon.revalidate();
            }
        } catch (IOException e) {
            lbIcon.setIcon(new ImageIcon());
        }
        lbName.setText(itemObject.getName());
        lbPrice.setText("R$ " + String.format("%.02f", Float.valueOf(itemObject.getPrice())).replace(".", ","));
        lbQuantity.setText("x" + itemObject.getQuantity());
        lbTotalPrice.setText("R$ " + String.format("%.02f",Float.valueOf(itemObject.getPrice()) * itemObject.getQuantity()).replace(".", ","));

        lbName.setOpaque(true);
        lbPrice.setOpaque(true);
        lbIcon.setOpaque(true);
        lbQuantity.setOpaque(true);
        panelText.setOpaque(true);
        setOpaque(true);

        if (b) {
            lbName.setBackground(Color.LIGHT_GRAY);
            lbPrice.setBackground(Color.LIGHT_GRAY);
            lbTotalPrice.setBackground(Color.LIGHT_GRAY);
            lbIcon.setBackground(Color.LIGHT_GRAY);
            lbQuantity.setBackground(Color.LIGHT_GRAY);
            panelText.setBackground(Color.LIGHT_GRAY);
            panelPrice.setBackground(Color.LIGHT_GRAY);
            setBackground(Color.LIGHT_GRAY);
        } else {
            lbName.setBackground(Color.WHITE);
            lbPrice.setBackground(Color.WHITE);
            lbTotalPrice.setBackground(Color.WHITE);
            lbIcon.setBackground(Color.WHITE);
            lbQuantity.setBackground(Color.WHITE);
            panelText.setBackground(Color.WHITE);
            panelPrice.setBackground(Color.WHITE);
            setBackground(Color.WHITE);
        }
        return this;
    }
}
