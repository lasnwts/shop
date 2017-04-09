package nwts.ru.autoshop.models;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by пользователь on 22.03.2017.
 */

public class ProductDetail {

    private int Menu_ID;
    private String Menu_name;
    private double Price;
    private String Menu_image;
    private String Serve_for;
    private String Description;
    private int Category_ID;
    private int Product_ID;
    private int SubCategory_ID;
    private int Quantity;


    private ArrayList<String> imagesDetail; //доп.фотографии товара
    private ArrayList<String> descDetail; //возможно доп.описание товара

    // create price format
    DecimalFormat formatData = new DecimalFormat("#.##");

    public ProductDetail() {
    }

    public ProductDetail(int menu_ID, String menu_image, String description) {
        Menu_ID = menu_ID;
        Menu_image = menu_image;
        Description = description;
    }

    public ProductDetail(int menu_ID, String menu_name, double price,
                         String menu_image, String serve_for, String description) {
        Menu_ID = menu_ID;
        Menu_name = menu_name;
        Price = price;
        Menu_image = menu_image;
        Serve_for = serve_for;
        Description = description;
    }


    public ProductDetail(int menu_ID, String menu_name, double price, String menu_image, String serve_for,
                         String description, int category_ID, int product_ID, int subCategory_ID, int quantity) {
        Menu_ID = menu_ID;
        Menu_name = menu_name;
        Price = price;
        Menu_image = menu_image;
        Serve_for = serve_for;
        Description = description;
        Category_ID = category_ID;
        Product_ID = product_ID;
        SubCategory_ID = subCategory_ID;
        Quantity = quantity;
    }

    public void setMenu_ID(int menu_ID) {
        Menu_ID = menu_ID;
    }

    public void setMenu_name(String menu_name) {
        Menu_name = menu_name;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public void setMenu_image(String menu_image) {
        Menu_image = menu_image;
    }

    public void setServe_for(String serve_for) {
        Serve_for = serve_for;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setImagesDetail(ArrayList<String> imagesDetail) {
        this.imagesDetail = imagesDetail;
    }

    public void setDescDetail(ArrayList<String> descDetail) {
        this.descDetail = descDetail;
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

    public String getServe_for() {
        return Serve_for;
    }

    public String getDescription() {
        return Description;
    }

    public ArrayList<String> getImagesDetail() {
        return imagesDetail;
    }

    public ArrayList<String> getDescDetail() {
        return descDetail;
    }
}
