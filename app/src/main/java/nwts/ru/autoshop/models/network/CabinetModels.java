package nwts.ru.autoshop.models.network;

import java.util.List;

/**
 * Created by пользователь on 16.05.2017.
 */

public class CabinetModels {

    public List<CabinetModel> mCabinetModels;
    private  int errors;

    public CabinetModels(List<CabinetModel> cabinetModels, int errors) {
        mCabinetModels = cabinetModels;
        this.errors = errors;
    }

    public List<CabinetModel> getCabinetModels() {
        return mCabinetModels;
    }

    public void setCabinetModels(List<CabinetModel> cabinetModels) {
        mCabinetModels = cabinetModels;
    }

    public int getErrors() {
        return errors;
    }
}
