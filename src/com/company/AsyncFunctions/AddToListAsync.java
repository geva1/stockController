package com.company.AsyncFunctions;

import com.company.Models.Stock;
import com.company.View.StockView;
import murilo.libs.model.exception.ModelException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class AddToListAsync {
    private Thread thread;

    public AddToListAsync(StockView stockView, String field) {
        thread = new Thread(() -> {
            try {
                if (stockView.tableModel.getRowCount() > 0) {
                    stockView.tableModel.setRowCount(0);
                }
                ArrayList<Stock> items = (ArrayList<Stock>) stockView.database.listItems();
                for (int i = 0; i < items.size(); i++) {
                    Object[] stockItems = new Object[13];
                    boolean add = false;
                    stockItems[1] = items.get(i).getDescription();
                    stockItems[2] = items.get(i).getColor();
                    stockItems[3] = items.get(i).getStart();
                    stockItems[4] = items.get(i).getCategory();
                    stockItems[5] = items.get(i).getTrademark();
                    stockItems[6] = items.get(i).getWeight();
                    stockItems[7] = items.get(i).getMeters();
                    stockItems[8] = items.get(i).getLocation();
                    stockItems[9] = String.format("%.02f", items.get(i).getPrice()).replace(".", ",");
                    stockItems[10] = String.format("%.03f", items.get(i).getQuantity()).replace(".", ",");
                    stockItems[11] = items.get(i).getObservation();
                    stockItems[12] = items.get(i).getBarcode();
                    if (!field.equals("")) {
                        for (int j = 0; j < 13; j++) {
                            if (String.valueOf(stockItems[j]).toUpperCase().contains(field.toUpperCase())) {
                                add = true;
                                break;
                            }
                        }
                    } else {
                        add = true;
                    }
                    if (add) {
                        new RenderPhotoAsync(stockView, items.get(i).getImage(), i).execute();
                        stockView.tableModel.addRow(stockItems);
                    }
                }
            } catch (ModelException e) {
                e.printStackTrace();
            }
        });
    }

    public void execute() {
        thread.start();
    }
}
