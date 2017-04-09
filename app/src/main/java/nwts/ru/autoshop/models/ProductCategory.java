package nwts.ru.autoshop.models;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by пользователь on 21.03.2017.
 */

public class ProductCategory {

    private int Menu_ID;
    private String Menu_name;
    private double Price;
    private String Description;
    private String Menu_image;
    private int Category_ID;
    private int Product_ID;
    private int SubCategory_ID;
    private int Quantity;

    // create price format
    DecimalFormat formatData = new DecimalFormat("#.##");

    public ProductCategory() {
    }

    public ProductCategory(int menu_ID, String menu_name, double price, String menu_image) {
        Menu_ID = menu_ID;
        Menu_name = menu_name;
        Price = price;
        Menu_image = menu_image;
    }

    public ProductCategory(int menu_ID, String menu_name, double price, String description,
                           String menu_image, int category_ID, int product_ID, int subCategory_ID, int quantity) {
        Menu_ID = menu_ID;
        Menu_name = menu_name;
        Price = price;
        Description = description;
        Menu_image = menu_image;
        Category_ID = category_ID;
        Product_ID = product_ID;
        SubCategory_ID = subCategory_ID;
        Quantity = quantity;
    }

    public String getDescription() {
        return Description;
    }

    public int getCategory_ID() {
        return Category_ID;
    }

    public int getProduct_ID() {
        return Product_ID;
    }

    public int getSubCategory_ID() {
        return SubCategory_ID;
    }

    public int getQuantity() {
        return Quantity;
    }

    public int getMenu_ID() {
        return Menu_ID;
    }

    public String getMenu_name() {
        return Menu_name;
    }

    public double getPrice() {
        return Price;
    }

    public String getPriceString() {
        return formatData.format(Price);
    }

    public String getMenu_image() {
        return Menu_image;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
