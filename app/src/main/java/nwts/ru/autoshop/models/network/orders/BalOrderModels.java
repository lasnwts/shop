package nwts.ru.autoshop.models.network.orders;

import java.util.List;

/**
 * Created by пользователь on 24.05.2017.
 */

public class BalOrderModels {

    public List<BalOrderModel> mBalOrderModels;
    private  int errors;

    public BalOrderModels(List<BalOrderModel> balOrderModels, int errors) {
        mBalOrderModels = balOrderModels;
        this.errors = errors;
    }

    public List<BalOrderModel> getBalOrderModels() {
        return mBalOrderModels;
    }

    public void setBalOrderModels(List<BalOrderModel> balOrderModels) {
        mBalOrderModels = balOrderModels;
    }

    public int getErrors() {
        return errors;
    }

    public void setErrors(int errors) {
        this.errors = errors;
    }
}
