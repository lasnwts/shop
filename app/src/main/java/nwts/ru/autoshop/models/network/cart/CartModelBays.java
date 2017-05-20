package nwts.ru.autoshop.models.network.cart;

import java.util.List;

/**
 * Created by пользователь on 20.05.2017.
 */

public class CartModelBays {

    private List<CartModelBay> mCartModelBays;
    private  int errors;

    public CartModelBays(List<CartModelBay> cartModelBays, int errors) {
        mCartModelBays = cartModelBays;
        this.errors = errors;
    }

    public List<CartModelBay> getCartModelBays() {
        return mCartModelBays;
    }

    public void setCartModelBays(List<CartModelBay> cartModelBays) {
        mCartModelBays = cartModelBays;
    }

    public int getErrors() {
        return errors;
    }

    public void setErrors(int errors) {
        this.errors = errors;
    }
}
