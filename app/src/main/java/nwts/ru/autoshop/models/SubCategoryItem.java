package nwts.ru.autoshop.models;

/**
 * Created by пользователь on 29.03.2017.
 */

public class SubCategoryItem {

    private int SubCategory_ID;
    private String SubCategory_name;
    private String SubCategory_image;
    private long SubCategory_Refresh_Time;
    private int id;

    public SubCategoryItem() {
    }

    public SubCategoryItem(int subCategory_ID, String subCategory_name, String subCategory_image) {
        SubCategory_ID = subCategory_ID;
        SubCategory_name = subCategory_name;
        SubCategory_image = subCategory_image;
    }

    public SubCategoryItem(int id, int subCategory_ID, String subCategory_name,
                           String subCategory_image, long subCategory_Refresh_Time) {
        SubCategory_ID = subCategory_ID;
        SubCategory_name = subCategory_name;
        SubCategory_image = subCategory_image;
        SubCategory_Refresh_Time = subCategory_Refresh_Time;
        this.id = id;
    }

    public int getSubCategory_ID() {
        return SubCategory_ID;
    }

    public String getSubCategory_name() {
        return SubCategory_name;
    }

    public String getSubCategory_image() {
        return SubCategory_image;
    }

    public long getSubCategory_Refresh_Time() {
        return SubCategory_Refresh_Time;
    }

    public int getId() {
        return id;
    }
}
