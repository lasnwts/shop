package nwts.ru.autoshop.models.network.cart;

import java.util.List;

/**
 * Created by пользователь on 30.05.2017.
 */

public class ErrorModels {

    private List<ErrorModel> mErrorModels;
    private  int errors;

    public ErrorModels(List<ErrorModel> errorModels, int errors) {
        mErrorModels = errorModels;
        this.errors = errors;
    }

    public List<ErrorModel> getErrorModels() {
        return mErrorModels;
    }

    public void setErrorModels(List<ErrorModel> errorModels) {
        mErrorModels = errorModels;
    }

    public int getErrors() {
        return errors;
    }

    public void setErrors(int errors) {
        this.errors = errors;
    }
}
