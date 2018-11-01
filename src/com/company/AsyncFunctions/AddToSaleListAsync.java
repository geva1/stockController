package com.company.AsyncFunctions;

import com.company.Objects.SaleItem;
import com.company.View.SaleView;
import murilo.libs.model.exception.ModelException;

import javax.swing.*;
import java.util.ArrayList;

public class AddToSaleListAsync {
    private Thread thread;

    public AddToSaleListAsync(SaleView saleView, String field, String dateToSearch) {
        thread = new Thread(() -> {
            try {
                if (saleView.tableModel.getRowCount() > 0) {
                    saleView.tableModel.setRowCount(0);
                }
                ArrayList<SaleItem> items = (ArrayList<SaleItem>) saleView.database.listSales();
                ArrayList<String> dates = new ArrayList<>();
                dates.add("Todas as datas");
                for (SaleItem item : items) {
                    Object[] stockItems = new Object[13];
                    boolean add = false;
                    String[] date = String.valueOf(item.getDate()).split("-");
                    String year = date[0];
                    String month = date[1];
                    String day = date[2];
                    stockItems[0] = item.getName();
                    stockItems[1] = String.format("%.02f", item.getTotal()).replace(".", ",");
                    stockItems[2] = day + "/" + month + "/" + year;
                    stockItems[3] = item.getId();
                    if (!field.equals("") && dateToSearch != null && !dateToSearch.equals("Todas as datas")) {
                        for (String description : item.getDescriptionList()) {
                            if (String.valueOf(description).toUpperCase().contains(field.toUpperCase())) {
                                if (dateToSearch == stockItems[2]) {
                                    add = true;
                                    break;
                                }
                            }
                        }
                    } else if(!field.equals("") && (dateToSearch == null || dateToSearch.equals("Todas as datas"))) {
                        for (String description : item.getDescriptionList()) {
                            if (String.valueOf(description).toUpperCase().contains(field.toUpperCase())) {
                                add = true;
                                break;
                            }
                        }
                    } else if(dateToSearch != null && !dateToSearch.equals("Todas as datas")) {
                        if (dateToSearch == stockItems[2]) {
                            add = true;
                        }
                    } else {
                        add = true;
                    }
                    if (add) {
                        saleView.tableModel.addRow(stockItems);
                    }
                    if (!dates.contains(String.valueOf(item.getDate()))) {
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
