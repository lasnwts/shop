package nwts.ru.autoshop.models.network.cart;

import java.util.List;


/**
 * Created by пользователь on 20.05.2017.
 */

public class CartModels {

    public List<CartModel> mCartModels;
    private  int errors;

    public CartModels(List<CartModel> cartModels, int errors) {
        mCartModels = cartModels;
        this.errors = errors;
    }

    public List<CartModel> getCartModels() {
        return mCartModels;
    }

    public void setCartModels(List<CartModel> cartModels) {
        mCartModels = cartModels;
    }

    public int getErrors() {
        return errors;
    }

    public void setErrors(int errors) {
        this.errors = errors;
    }
}
