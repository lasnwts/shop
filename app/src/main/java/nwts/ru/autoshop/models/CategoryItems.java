package nwts.ru.autoshop.models;

import java.util.List;

/**
 * Created by пользователь on 17.03.2017.
 */

public class CategoryItems {

    public List<CategoryItem> categoryItems;
    private int errors;

    public CategoryItems(List<CategoryItem> categoryItems, int errors) {
        this.categoryItems = categoryItems;
        this.errors = errors;
    }

    public List<CategoryItem> getCategoryItems() {
        return categoryItems;
    }

    public int getErrors() {
        return errors;
    }
}
