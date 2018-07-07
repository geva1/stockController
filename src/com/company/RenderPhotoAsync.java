package com.company;

import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;

class RenderPhotoAsync {
    private Thread images;

    RenderPhotoAsync(Main main, String photo, int position) {
        images = new Thread(() -> {
            try {
                int width = ImageIO.read(new ByteArrayInputStream(new BASE64Decoder()
                        .decodeBuffer(photo))).getWidth(null);
                int height = ImageIO.read(new ByteArrayInputStream(new BASE64Decoder()
                        .decodeBuffer(photo))).getHeight(null);
                int greater;
                if (width > height) greater = width;
                else greater = height;
                main.tableModel.setValueAt(new ImageIcon(ImageIO.read(new ByteArrayInputStream(
                        new BASE64Decoder().decodeBuffer(photo)))
                        .getScaledInstance((int) (60 * ((float) width / (float) greater)),
                                (int) (60 * ((float) height / (float) greater)), Image.SCALE_SMOOTH)), position, 0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    void execute() {
        images.start();
    }
}
