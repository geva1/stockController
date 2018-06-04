package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class ItemRenderer extends JPanel implements ListCellRenderer<ItemObject> {
    private JLabel lbIcon = new JLabel();
    private JLabel lbName = new JLabel();
    private JLabel lbPrice = new JLabel();
    private JLabel lbQuantity = new JLabel();
    private JPanel panelText = new JPanel(new BorderLayout(3, 20));

    ItemRenderer() {
        lbName.setFont(new java.awt.Font("Arial", Font.PLAIN, 28)); // NOI18N
        lbPrice.setFont(new java.awt.Font("Arial", Font.PLAIN, 28)); // NOI18N
        lbQuantity.setFont(new java.awt.Font("Arial", Font.PLAIN, 14)); // NOI18N

        lbIcon.setBounds(0,0, 20,20);
        setLayout(new BorderLayout(2, 20));

        panelText.add(lbName, BorderLayout.WEST);
        panelText.add(lbQuantity, BorderLayout.CENTER);
        panelText.add(lbPrice, BorderLayout.EAST);
        add(lbIcon, BorderLayout.WEST);
        add(panelText, BorderLayout.CENTER);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends ItemObject> list, ItemObject itemObject, int i, boolean b, boolean b1) {
        try {
            if(itemObject.getImage() != null) {
                lbIcon.setIcon(new ImageIcon(ImageIO.read(new ByteArrayInputStream(new sun.misc.BASE64Decoder().decodeBuffer(itemObject.getImage())))));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        lbName.setText(itemObject.getName());
        lbPrice.setText("R$ " + itemObject.getPrice().replace(".", ","));
        lbQuantity.setText("x" + itemObject.getQuantity());

        lbName.setOpaque(true);
        lbPrice.setOpaque(true);
        lbIcon.setOpaque(true);
        lbQuantity.setOpaque(true);
        panelText.setOpaque(true);
        setOpaque(true);

        if (b) {
            lbName.setBackground(Color.blue);
            lbPrice.setBackground(Color.blue);
            lbIcon.setBackground(Color.blue);
            lbQuantity.setBackground(Color.blue);
            panelText.setBackground(Color.blue);
            setBackground(Color.blue);
        } else {
            lbName.setBackground(Color.WHITE);
            lbPrice.setBackground(Color.WHITE);
            lbIcon.setBackground(Color.WHITE);
            lbQuantity.setBackground(Color.WHITE);
            panelText.setBackground(Color.WHITE);
            setBackground(Color.WHITE);
        }
        return this;
    }
}
