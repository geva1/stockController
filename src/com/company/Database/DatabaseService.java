package com.company.Database;

import com.company.Models.*;
import com.company.Objects.SaleItem;
import murilo.libs.database.Connector;
import murilo.libs.database.Drive;
import murilo.libs.model.Model;
import murilo.libs.model.exception.ModelException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DatabaseService {
    private Model<Stock> stock;
    private Model<Trademark> trademark;
    private Model<Category> category;
    private Model<Client> client;
    private Model<Sale> sale;
    private Model<SaleHasItem> saleHasItem;

    public DatabaseService() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Class<?> clazz = Class.forName("murilo.libs.database.Connector");
        Field f = clazz.getDeclaredField("drives");
        f.setAccessible(true);

        Map<String, Drive> drivers = (Map<String, Drive>) f.get(f);
        drivers.put("sqlite", new Drive("org.h2.Driver", "jdbc:h2:./stockDBFiles/stockDB;user=test;password=test", false, false));

        Connector.setDrive("sqlite");
        Connector.setSchema("");
        Connector.getConnection();

        stock = new Model<>("stock", "barcode", Stock.class);
        trademark = new Model<>("trademark", "trademark", Trademark.class);
        category = new Model<>("category", "category", Category.class);
        client = new Model<>("client", "cpf", Client.class);
        sale = new Model<>("sale", "category", Sale.class);
        saleHasItem = new Model<>("sale_has_item", "sale", SaleHasItem.class);
    }

    public void createItem(Stock item) throws ModelException {
        stock.insert(item);
    }

    public void deleteItem(Integer id) throws ModelException {
        Stock stock = getItem(id);
        this.stock.delete(stock);
    }

    public Integer getItemId(String barcode, String start) throws ModelException {
        List<Stock> items = stock.list();
        for (Stock item : items) {
            if (item.getBarcode().equals(barcode) && item.getStart().equals(start)) {
                return item.getId();
            }
        }
        return null;
    }

    public List<Stock> getItemByBarcode(String barcode) throws ModelException {
        List<Stock> items = stock.list();
        List<Stock> result = new ArrayList<>();
        for(Stock item : items) {
            if(item.getBarcode().equals(barcode)) {
                result.add(item);
            }
        }
        return result;
    }

    public void updateItem(Stock item) throws ModelException {
        stock.update(item);
    }

    public List<Stock> listItems() throws ModelException {
        return stock.list();
    }

    public Stock getItem(Integer id) throws ModelException {
        return stock.get("id", String.valueOf(id));
    }

    public List<String> listTrademarks() throws ModelException {
        List<Trademark> trademarks = trademark.list();
        List<String> result = new ArrayList<>();
        for (Trademark trademark : trademarks) {
            result.add(trademark.getTrademark());
        }
        return result;
    }

    public List<String> listCategories() throws ModelException {
        List<Category> categories = category.list();
        List<String> result = new ArrayList<>();
        for (Category category : categories) {
            result.add(category.getCategory());
        }
        return result;
    }

    public void deleteCategory(String category) throws ModelException {
        Category category1 = new Category();
        category1.setCategory(category);
        this.category.delete(category1);
    }

    public void deleteTrademark(String trademark) throws ModelException {
        Trademark trademark1 = new Trademark();
        trademark1.setTrademark(trademark);
        this.trademark.delete(trademark1);
    }

    public void createCategory(String category) throws ModelException {
        Category category1 = new Category();
        category1.setCategory(category);
        this.category.insert(category1);
    }

    public void createTrademark(String trademark) throws ModelException {
        Trademark trademark1 = new Trademark();
        trademark1.setTrademark(trademark);
        this.trademark.insert(trademark1);
    }

    public List<SaleItem> listSales() throws ModelException {
        List<SaleItem> sales = new ArrayList<>();
        List<Sale> saleList = sale.list();
        List<SaleHasItem> saleHasItems = saleHasItem.list();
        for (Sale item : saleList) {
            SaleItem saleItem = new SaleItem();
            saleItem.setTotal(0.0);
            saleItem.setName(item.getCpf() != null ? getClient(item.getCpf()).getName() : "");
            saleItem.setDate(item.getDate());
            saleItem.setId(item.getId());
            for (SaleHasItem saleHasItem : saleHasItems) {
                if (saleHasItem.getSale().equals(item.getId())) {
                    saleItem.setTotal(saleItem.getTotal() + (saleHasItem.getItem_value() * saleHasItem.getQuantity()));
                    saleItem.addDescription(getItem(getItemId(saleHasItem.getBarcode(), saleHasItem.getStart())).getDescription());
                }
            }
            sales.add(saleItem);
        }
        return sales;
    }

    public Client getClient(String cpf) throws ModelException {
        return client.get("cpf", cpf);
    }

    public List<Client> listClients() throws ModelException {
        return client.list();
    }

    public void deleteSale(Integer id) throws ModelException {
        sale.delete(getSale(id));
    }

    public Sale getSale(Integer id) throws ModelException {
        return sale.get("id", String.valueOf(id));
    }
}
