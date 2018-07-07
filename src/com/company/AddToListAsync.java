package com.company;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class AddToListAsync {
    private Thread thread;

    AddToListAsync(Main main) {
        thread = new Thread(() -> {
            try {
                if (main.tableModel.getRowCount() > 0) {
                    main.tableModel.setRowCount(0);
                }
                PreparedStatement newSt = main.st.getConnection().prepareStatement("SELECT * FROM stock");
                ResultSet rs = newSt.executeQuery();
                Object[] stockItems = new Object[13];
                int position = 0;
                while (rs.next()) {
                            /*int width = ImageIO.read(new ByteArrayInputStream(new BASE64Decoder()
                                    .decodeBuffer(rs.getString("image")))).getWidth(null);
                            int height = ImageIO.read(new ByteArrayInputStream(new BASE64Decoder()
                                    .decodeBuffer(rs.getString("image")))).getHeight(null);
                            int greater;
                            if (width > height) greater = width;
                            else greater = height;
                            stockItems[0] = new ImageIcon(ImageIO.read(new ByteArrayInputStream(
                                    new BASE64Decoder().decodeBuffer(rs.getString("image"))))
                                    .getScaledInstance((int) (60 * ((float) width / (float) greater)),
                                            (int) (60 * ((float) height / (float) greater)), Image.SCALE_SMOOTH));*/
                    new RenderPhotoAsync(main, rs.getString("image"), position).execute();
                    stockItems[1] = rs.getString("description");
                    stockItems[2] = rs.getString("color");
                    stockItems[3] = rs.getString("start");
                    stockItems[4] = rs.getString("category");
                    stockItems[5] = rs.getString("trademark");
                    stockItems[6] = rs.getFloat("weight");
                    stockItems[7] = rs.getInt("meters");
                    stockItems[8] = rs.getString("location");
                    stockItems[9] = String.format("%.02f", rs.getFloat("price")).replace(".", ",");
                    stockItems[10] = String.format("%.03f", rs.getFloat("quantity")).replace(".", ",");
                    stockItems[11] = rs.getString("observation");
                    stockItems[12] = rs.getString("barcode");
                    main.tableModel.addRow(stockItems);
                    position++;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    void execute() {
        thread.start();
    }
}
