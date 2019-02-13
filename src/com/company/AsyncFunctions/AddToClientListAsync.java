package com.company.AsyncFunctions;

import com.company.Models.Client;
import com.company.View.ClientView;
import murilo.libs.model.exception.ModelException;

import java.util.ArrayList;

public class AddToClientListAsync {
    private Thread thread;

    public AddToClientListAsync(ClientView clientView, String field) {
        thread = new Thread(() -> {
            try {
                if (clientView.tableModel.getRowCount() > 0) {
                    clientView.tableModel.setRowCount(0);
                }
                ArrayList<Client> items = (ArrayList<Client>) clientView.database.listClients();
                for (Client item : items) {
                    Object[] stockItems = new Object[6];
                    stockItems[0] = item.getCpf();
                    stockItems[1] = item.getName();
                    stockItems[2] = item.getAdress();
                    stockItems[3] = item.getPhone();
                    stockItems[4] = item.getCellphone();
                    stockItems[5] = item.getEmail();
                    if (!field.equals("")) {
                        for (int j = 0; j < 6; j++) {
                            if (String.valueOf(stockItems[j]).toUpperCase().contains(field.toUpperCase())) {
                                clientView.tableModel.addRow(stockItems);
                                break;
                            }
                        }
                    } else {
                        clientView.tableModel.addRow(stockItems);
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
