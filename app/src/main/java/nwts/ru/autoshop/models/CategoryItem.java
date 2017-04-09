package nwts.ru.autoshop.models;

/**
 * Created by пользователь on 17.03.2017.
 */

public class CategoryItem {

    private int Category_ID;
    private String Category_name;
    private String Category_image;
    private long Category_Refresh_Time;
    private int id;

    public CategoryItem() {
    }

    public CategoryItem(int category_ID, String category_name, String category_image) {
        Category_ID = category_ID;
        Category_name = category_name;
        Category_image = category_image;
    }

    public CategoryItem( int id, int category_ID, String category_name, String category_image, long category_Refresh_Time) {
        Category_ID = category_ID;
        Category_name = category_name;
        Category_image = category_image;
        Category_Refresh_Time = category_Refresh_Time;
        this.id = id;
    }

    public int getCategory_ID() {
        return Category_ID;
    }

    public String getCategory_name() {
        return Category_name;
    }

    public String getCategory_image() {
        return Category_image;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
