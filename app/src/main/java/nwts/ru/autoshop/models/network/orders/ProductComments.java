package nwts.ru.autoshop.models.network.orders;

import java.util.List;

import nwts.ru.autoshop.models.network.ProductComment;

/**
 * Created by пользователь on 31.05.2017.
 */

public class ProductComments {


    public List<ProductComment> mProductComments;
    private  int errors;

    public ProductComments(List<ProductComment> productComments, int errors) {
        mProductComments = productComments;
        this.errors = errors;
    }

    public List<ProductComment> getProductComments() {
        return mProductComments;
    }

    public void setProductComments(List<ProductComment> productComments) {
        mProductComments = productComments;
    }

    public int getErrors() {
        return errors;
    }

    public void setErrors(int errors) {
        this.errors = errors;
    }
}

