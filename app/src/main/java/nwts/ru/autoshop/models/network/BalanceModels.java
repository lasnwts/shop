package nwts.ru.autoshop.models.network;

import java.util.List;

/**
 * Created by пользователь on 19.05.2017.
 */

public class BalanceModels {

    public List<BalanceModel> mBalanceModels;
    private  int errors;

    public BalanceModels(List<BalanceModel> balanceModels, int errors) {
        mBalanceModels = balanceModels;
        this.errors = errors;
    }

    public List<BalanceModel> getBalanceModels() {
        return mBalanceModels;
    }

    public void setBalanceModels(List<BalanceModel> balanceModels) {
        mBalanceModels = balanceModels;
    }

    public int getErrors() {
        return errors;
    }

    public void setErrors(int errors) {
        this.errors = errors;
    }
}
