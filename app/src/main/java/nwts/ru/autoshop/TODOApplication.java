package nwts.ru.autoshop;

import android.app.Application;

import nwts.ru.autoshop.databases.DBHelper;

/**
 * Created by пользователь on 13.03.2017.
 */

public class TODOApplication extends Application {

    public boolean Flag_run_Splash = false;
    public DBHelper dbHelper;
    private static TODOApplication instance;
    private static int category_Id;
    private static int subCategory_Id;
    private static int menu_Id;
    private static String url_Image;
    private static int product_Id;

    private static double detail_price;
    private static int detail_quantity;
    private static int detail_subcategory_Id;
    private static int detail_category_Id;
    private static String detail_description;
    private static String detail_productName;
    private static int detail_product_Id;

    public static double getDetail_price() {
        return TODOApplication.detail_price;
    }

    public static void setDetail_price(double detail_price) {
        TODOApplication.detail_price = detail_price;
    }

    public static int getDetail_quantity() {
        return TODOApplication.detail_quantity;
    }

    public static void setDetail_quantity(int detail_quantity) {
        TODOApplication.detail_quantity = detail_quantity;
    }

    public static int getDetail_subcategory_Id() {
        return TODOApplication.detail_subcategory_Id;
    }

    public static void setDetail_subcategory_Id(int detail_subcategory_Id) {
        TODOApplication.detail_subcategory_Id = detail_subcategory_Id;
    }

    public static int getDetail_category_Id() {
        return TODOApplication.detail_category_Id;
    }

    public static void setDetail_category_Id(int detail_category_Id) {
        TODOApplication.detail_category_Id = detail_category_Id;
    }

    public static String getDetail_description() {
        return TODOApplication.detail_description;
    }

    public static void setDetail_description(String detail_description) {
        TODOApplication.detail_description = detail_description;
    }

    public static String getDetail_productName() {
        return TODOApplication.detail_productName;
    }

    public static void setDetail_productName(String detail_productName) {
        TODOApplication.detail_productName = detail_productName;
    }

    public static int getDetail_product_Id() {
        return TODOApplication.detail_product_Id;
    }

    public static void setDetail_product_Id(int detail_product_Id) {
        TODOApplication.detail_product_Id = detail_product_Id;
    }

    public static int getProduct_Id() {
        return TODOApplication.product_Id;
    }

    public static void setProduct_Id(int product_Id) {
        TODOApplication.product_Id = product_Id;
    }

    public static void setUrl_Image(String url_Image) {
        TODOApplication.url_Image = url_Image;
    }

    public static String getUrl_Image() {
        return TODOApplication.url_Image;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        dbHelper = new DBHelper(this);
    }

    public static TODOApplication getInstance(){
        return instance;
    }

    public void setFlag_run_Splash(boolean flag_run_Splash) {
        this.Flag_run_Splash = flag_run_Splash;
    }

    public boolean isFlag_run_Splash() {
        return Flag_run_Splash;
    }

    public static int getMenu_Id() {
        return menu_Id;
    }

    public static void setMenu_Id(int menu_Id) {
        TODOApplication.menu_Id = menu_Id;
    }

    public static void setCategory_Id(int category_Id) {
        TODOApplication.category_Id = category_Id;
    }

    public static int getCategory_Id() {
        return TODOApplication.category_Id;
    }

    public static void setSubCategory_Id (int category_Id) {
        TODOApplication.subCategory_Id = category_Id;
    }

    public static int getSubCategory_Id() {
        return TODOApplication.subCategory_Id;
    }
}