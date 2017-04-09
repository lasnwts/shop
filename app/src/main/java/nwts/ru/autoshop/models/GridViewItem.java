package nwts.ru.autoshop.models;

import android.graphics.drawable.Drawable;

/**
 * Created by пользователь on 15.03.2017.
 */

public class GridViewItem {
    String title;
    Drawable image;
    int errors;

    // Empty Constructor
    public GridViewItem() {

    }

    // Constructor
    public GridViewItem(String title, Drawable image) {
        super();
        this.title = title;
        this.image = image;
    }

    // Getter and Setter Method
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }
}
