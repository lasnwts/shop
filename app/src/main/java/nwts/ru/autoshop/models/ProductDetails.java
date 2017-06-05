package nwts.ru.autoshop.models;

import java.util.List;

/**
 * Created by пользователь on 05.06.2017.
 */

public class ProductDetails {

    public List<ProductCategory> productCategories;
    private int errors;

    public ProductDetails(List<ProductCategory> productCategories, int errors) {
        this.productCategories = productCategories;
        this.errors = errors;
    }

    public List<ProductCategory> getProductCategories() {
        return productCategories;
    }

    public void setProductCategories(List<ProductCategory> productCategories) {
        this.productCategories = productCategories;
    }

    public int getErrors() {
        return errors;
    }

    public void setErrors(int errors) {
        this.errors = errors;
    }
}
