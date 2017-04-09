package nwts.ru.autoshop.models;

import java.util.List;

/**
 * Created by пользователь on 29.03.2017.
 */

public class SubCategoryItems {

    public List<SubCategoryItem> subCategoryItems;
    private int errors;

    public SubCategoryItems(List<SubCategoryItem> subCategoryItems, int errors) {
        this.subCategoryItems = subCategoryItems;
        this.errors = errors;
    }

    public List<SubCategoryItem> getSubCategoryItems() {
        return subCategoryItems;
    }

    public int getErrors() {
        return errors;
    }
}
