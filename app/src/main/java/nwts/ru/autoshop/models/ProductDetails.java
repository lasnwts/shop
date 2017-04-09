package nwts.ru.autoshop.models;

import java.util.List;

/**
 * Created by пользователь on 22.03.2017.
 */

public class ProductDetails {

    public List<ProductDetail> productDetails;
    private int errors;
    private String Menu_name;
    private double Price;
    private String Description;
    private int Category_ID;
    private int SubCategory_ID;
    private int Quantity;
    private int product_Id;

    public ProductDetails(List<ProductDetail> productDetails, int errors) {
        this.productDetails = productDetails;
        this.errors = errors;
    }

    public ProductDetails(List<ProductDetail> productDetails, int errors,
                          String menu_name, double price, String description, int category_ID, int subCategory_ID, int quantity, int product_Id) {
        this.productDetails = productDetails;
        this.errors = errors;
        Menu_name = menu_name;
        Price = price;
        Description = description;
        Category_ID = category_ID;
        SubCategory_ID = subCategory_ID;
        Quantity = quantity;
        product_Id = product_Id;
    }

    public int getProduct_Id() {
        return product_Id;
    }

    public List<ProductDetail> getProductDetails() {
        return productDetails;
    }

    public int getErrors() {
        return errors;
    }

    public String getMenu_name() {
        return Menu_name;
    }

    public double getPrice() {
        return Price;
    }

    public String getDescription() {
        return Description;
    }

    public int getCategory_ID() {
        return Category_ID;
    }

    public int getSubCategory_ID() {
        return SubCategory_ID;
    }

    public int getQuantity() {
        return Quantity;
    }
}
