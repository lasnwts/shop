package nwts.ru.autoshop.models;

import java.util.List;

/**
 * Created by пользователь on 30.03.2017.
 */

public class ProductDetailImages {

    public List<ProductDetailImage> productDetailImages;
    private int errors;

    public ProductDetailImages() {
    }

    public ProductDetailImages(List<ProductDetailImage> productDetailImages, int errors) {
        this.productDetailImages = productDetailImages;
        this.errors = errors;
    }

    public List<ProductDetailImage> getProductDetailImages() {
        return productDetailImages;
    }

    public int getErrors() {
        return errors;
    }
}
