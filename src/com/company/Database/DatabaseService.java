package com.company.Database;

import com.company.Models.*;
import com.company.Objects.ItemObject;
import com.company.Objects.SaleItem;
import murilo.libs.database.Connector;
import murilo.libs.database.Drive;
import murilo.libs.model.Model;
import murilo.libs.model.exception.ModelException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
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
        sale = new Model<>("sale", "id", Sale.class);
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
            saleItem.setName(item.getClient_id() != null ? getClient(item.getClient_id()).getName() : "");
            saleItem.setDate(item.getDate());
            saleItem.setId(item.getId());
            for (SaleHasItem saleHasItem : saleHasItems) {
                if (saleHasItem.getSale().equals(item.getId())) {
                    saleItem.addDescription(getItem(saleHasItem.getItem_id()).getDescription());
                }
            }
            saleItem.setTotal(item.getTotal());
            sales.add(saleItem);
        }
        return sales;
    }

    public Client getClient(Integer id) throws ModelException {
        return client.get("id", id.toString());
    }

    public List<Client> listClients() throws ModelException {
        return client.list();
    }

    public void deleteSale(Integer id) throws ModelException {
        sale.delete(getSale(id));
    }

    public void cancelSale(Integer id) throws ModelException {
        Sale sale = this.sale.get("id", String.valueOf(id));
        List<SaleHasItem> saleHasItems = this.saleHasItem.list();
        for(SaleHasItem saleHasItem : saleHasItems) {
            if(saleHasItem.getSale().equals(sale.getId())) {
                Stock stock = this.stock.get("id", saleHasItem.getItem_id().toString());
                stock.setQuantity(stock.getQuantity() + saleHasItem.getQuantity());
                updateItem(stock);
            }
        }
        this.sale.delete(sale);
    }

    public Sale getSale(Integer id) throws ModelException {
        return sale.get("id", String.valueOf(id));
    }

    public void createSale(ArrayList<ItemObject> items, Client client, Double total, String first, String second, String third) throws ModelException {
        Sale sale = new Sale();
        sale.setClient_id(client != null ? client.getId() : null);
        sale.setDate(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
        sale.setTotal(total);
        sale.setFirst_installment(first);
        sale.setSecond_installment(second);
        sale.setThird_installment(third);
        Integer id = this.sale.insertReturningGeneratedId(sale);
        for(ItemObject item : items) {
            SaleHasItem saleHasItem = new SaleHasItem();
            saleHasItem.setItem_id(item.getId());
            saleHasItem.setItem_value(Double.valueOf(item.getPrice()));
            saleHasItem.setQuantity(Double.valueOf(item.getQuantity()));
            saleHasItem.setSale(id);
            this.saleHasItem.insert(saleHasItem);
        }
    }

    public Integer createClient(Client client) throws ModelException {
        return this.client.insertReturningGeneratedId(client);
    }

    public void updateClient(Client client) throws ModelException {
        this.client.update(client);
    }
}
