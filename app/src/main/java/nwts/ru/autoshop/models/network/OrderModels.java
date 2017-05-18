package nwts.ru.autoshop.models.network;

import java.util.List;

/**
 * Created by пользователь on 17.05.2017.
 */

public class OrderModels {

    public List<OrderModel> mOrderModels;
    private  int errors;

    public OrderModels(List<OrderModel> orderModels, int errors) {
        mOrderModels = orderModels;
        this.errors = errors;
    }

    public List<OrderModel> getOrderModels() {
        return mOrderModels;
    }

    public void setOrderModels(List<OrderModel> orderModels) {
        mOrderModels = orderModels;
    }

    public int getErrors() {
        return errors;
    }

    public void setErrors(int errors) {
        this.errors = errors;
    }
}
