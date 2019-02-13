package com.company.AsyncFunctions;

import com.company.Objects.SaleItem;
import com.company.View.SaleView;
import murilo.libs.model.exception.ModelException;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class AddToSaleListAsync {
    private Thread thread;

    public AddToSaleListAsync(SaleView saleView, String field, String dateToSearch) {
        thread = new Thread(() -> {
            try {
                if (saleView.tableModel.getRowCount() > 0) {
                    saleView.tableModel.setRowCount(0);
                }
                Object[] items = saleView.database.listSales().toArray();

                Collections.reverse(Arrays.asList(items));

                ArrayList<String> dates = new ArrayList<>();
                dates.add("Todas as datas");
                for (Object item : items) {
                    SaleItem item1 = (SaleItem) item;
                    Object[] stockItems = new Object[13];
                    boolean add = false;
                    String[] date = String.valueOf(item1.getDate()).split("-");
                    String year = date[0];
                    String month = date[1];
                    String day = date[2];
                    stockItems[0] = item1.getName();
                    stockItems[1] = String.format("%.02f", item1.getTotal()).replace(".", ",");
                    stockItems[2] = day + "/" + month + "/" + year;
                    stockItems[3] = item1.getId();
                    if (!field.equals("") && dateToSearch != null && !dateToSearch.equals("Todas as datas")) {
                        for (String description : item1.getDescriptionList()) {
                            if (String.valueOf(description).toUpperCase().contains(field.toUpperCase())) {
                                if (dateToSearch == stockItems[2]) {
                                    add = true;
                                    break;
                                }
                            }
                        }
                    } else if (!field.equals("") && (dateToSearch == null || dateToSearch.equals("Todas as datas"))) {
                        for (String description : item1.getDescriptionList()) {
                            if (String.valueOf(description).toUpperCase().contains(field.toUpperCase())) {
                                add = true;
                                break;
                            }
                        }
                    } else if (dateToSearch != null && !dateToSearch.equals("Todas as datas")) {
                        if (dateToSearch.equals(stockItems[2].toString())) {
                            add = true;
                        }
                    } else {
                        add = true;
                    }
                    if (add) {
                        saleView.tableModel.addRow(stockItems);
                    }
                    if (!dates.contains(stockItems[2].toString())) {
                        dates.add(day + "/" + month + "/" + year);
                    }
                }
                saleView.searchDate.setModel(new DefaultComboBoxModel<>(dates.toArray(new String[0])));
            } catch (ModelException e) {
                e.printStackTrace();
            }
        });
    }

    public void execute() {
        thread.start();
    }
}
