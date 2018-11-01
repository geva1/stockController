package com.company.AsyncFunctions;

import com.company.View.StockView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class RenderPhotoAsync {
    private Thread images;

    public RenderPhotoAsync(StockView stockView, String photo, int position) {
        images = new Thread(() -> {
            try {
                File image = new File(photo);
                BufferedImage imageIO = ImageIO.read(image);
                int width = imageIO.getWidth(null);
                int height = imageIO.getHeight(null);
                int greater;
                if (width > height) greater = width;
                else greater = height;
                height = (int) (60 * ((float) height / (float) greater));
                width = (int) (60 * ((float) width / (float) greater));
                stockView.tableModel.setValueAt(new ImageIcon(imageIO.getScaledInstance(width, height, Image.SCALE_SMOOTH)),
                        position, 0);
            } catch (IOException ignored) {}
        });
    }

    public void execute() {
        images.start();
    }
}
