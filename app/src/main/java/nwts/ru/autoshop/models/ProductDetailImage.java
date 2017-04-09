package nwts.ru.autoshop.models;

/**
 * Created by пользователь on 30.03.2017.
 */

public class ProductDetailImage {

    private int Menu_ID;
    private String Menu_image;
    private String Description;

    public ProductDetailImage() {
    }

    public ProductDetailImage(int menu_ID, String menu_image, String description) {
        Menu_ID = menu_ID;
        Menu_image = menu_image;
        Description = description;
    }

    public int getMenu_ID() {
        return Menu_ID;
    }

    public String getMenu_image() {
        return Menu_image;
    }

    public String getDescription() {
        return Description;
    }
}
