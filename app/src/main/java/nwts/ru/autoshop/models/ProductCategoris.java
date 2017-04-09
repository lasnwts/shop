package nwts.ru.autoshop.models;

import java.util.List;

/**
 * Created by пользователь on 21.03.2017.
 */

public class ProductCategoris {

    public List<ProductCategory> productCategories;
    private int errors;

    public ProductCategoris(List<ProductCategory> productCategories, int errors) {
        this.productCategories = productCategories;
        this.errors = errors;
    }

    public List<ProductCategory> getProductCategories() {
        return productCategories;
    }

    public int getErrors() {
        return errors;
    }
}
