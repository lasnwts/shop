package nwts.ru.autoshop;

import android.app.Application;
import com.facebook.stetho.Stetho;
import org.greenrobot.greendao.database.Database;
import java.util.ArrayList;
import nwts.ru.autoshop.models.DaoMaster;
import nwts.ru.autoshop.models.DaoSession;

/**
 * Created by пользователь on 13.03.2017.
 */

public class TODOApplication extends Application {

    public boolean Flag_run_Splash = false;
    private static TODOApplication instance;
    private static DaoSession sDaoSession;
    private static int category_Id;
    private static int subCategory_Id;
    private static int menu_Id;
    private static String url_Image;
    private static int product_Id;
    private static int productDetail_Id;
    private static int productCatalog_Id;

    private static double detail_price;
    private static int detail_quantity;
    private static int detail_subcategory_Id;
    private static int detail_category_Id;
    private static String detail_description;
    private static String detail_productName;
    private static int detail_product_Id;
    private static ArrayList<String> urlProductDetailImages;
    private boolean validateToken;
    private static String urlGetBalance;
    private static String dialogImageSourceUrl;
    private static String dialogMessageText;
    private static int key_id;
    private static double cartSumma; //sum all tovars in cart
    private static double balSumma; //rest from balance cabimnet
    private static int statusID; //status pay 0 - from cabinet, 1 from cache
    private static int detail_rating; //rating from product
    private static int cabinmet_form; //0 - хзаказы, 1 - корзина
    private static String keyWord; //наименование товара


    public boolean isValidateToken() {
        return this.validateToken;
    }

    public void setValidateToken(boolean validateToken) {
        this.validateToken = validateToken;
    }

    public static ArrayList<String> getUrlProductDetailImages() {
        return urlProductDetailImages;
    }

    public static void setUrlProductDetailImages(ArrayList<String> urlProductDetailImages) {
        TODOApplication.urlProductDetailImages = urlProductDetailImages;
    }

    public static void clearUrlProductDetailImages(){
        TODOApplication.urlProductDetailImages.clear();
    }

    public static int getProductCatalog_Id() {
        return TODOApplication.productCatalog_Id;
    }

    public static void setProductCatalog_Id(int productCatalog_Id) {
        TODOApplication.productCatalog_Id = productCatalog_Id;
    }

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

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "shop-db");
        Database db = helper.getWritableDb();
        sDaoSession = new DaoMaster(db).newSession();
        urlProductDetailImages = new ArrayList<>();
        validateToken = false;

        //**************************************************************************
        // stetho
        //**************************************************************************
        // Create an InitializerBuilder
        Stetho.InitializerBuilder initializerBuilder =
                Stetho.newInitializerBuilder(this);
        // Enable Chrome DevTools
        initializerBuilder.enableWebKitInspector(
                Stetho.defaultInspectorModulesProvider(this)
        );

        // Enable command line interface
        initializerBuilder.enableDumpapp(
                Stetho.defaultDumperPluginsProvider(this)
        );

        // Use the InitializerBuilder to generate an Initializer
        Stetho.Initializer initializer = initializerBuilder.build();

        // Initialize Stetho with the Initializer
        Stetho.initialize(initializer);
        //**************************************************************************
        // stetho
        //**************************************************************************
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

    public static DaoSession getDaoSession() {
        return sDaoSession;
    }

    public static int getProductDetail_Id() {
        return TODOApplication.productDetail_Id;
    }

    public static void setProductDetail_Id(int productDetail_Id) {
        TODOApplication.productDetail_Id = productDetail_Id;
    }

    public static String getUrlGetBalance() {
        return urlGetBalance;
    }

    public static void setUrlGetBalance(String urlGetBalance) {
        TODOApplication.urlGetBalance = urlGetBalance;
    }

    public static String getDialogImageSourceUrl() {
        return dialogImageSourceUrl;
    }

    public static void setDialogImageSourceUrl(String dialogImageSourceUrl) {
        TODOApplication.dialogImageSourceUrl = dialogImageSourceUrl;
    }

    public static String getDialogMessageText() {
        return dialogMessageText;
    }

    public static void setDialogMessageText(String dialogMessageText) {
        TODOApplication.dialogMessageText = dialogMessageText;
    }

    public static int getKey_id() {
        return key_id;
    }

    public static void setKey_id(int key_id) {
        TODOApplication.key_id = key_id;
    }

    public static double getCartSumma() {
        return cartSumma;
    }

    public static void setCartSumma(double cartSumma) {
        TODOApplication.cartSumma = cartSumma;
    }

    public static double getBalSumma() {
        return balSumma;
    }

    public static void setBalSumma(double balSumma) {
        TODOApplication.balSumma = balSumma;
    }

    public static int getStatusID() {
        return statusID;
    }

    public static void setStatusID(int statusID) {
        TODOApplication.statusID = statusID;
    }

    public static int getDetail_rating() {
        return detail_rating;
    }

    public static void setDetail_rating(int detail_rating) {
        TODOApplication.detail_rating = detail_rating;
    }

    public static int getCabinmet_form() {
        return cabinmet_form;
    }

    public static void setCabinmet_form(int cabinmet_form) {
        TODOApplication.cabinmet_form = cabinmet_form;
    }

    public static String getKeyWord() {
        return keyWord;
    }

    public static void setKeyWord(String keyWord) {
        TODOApplication.keyWord = keyWord;
    }
}
