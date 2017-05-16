package nwts.ru.autoshop.setting;

/**
 * Created by пользователь on 13.03.2017.
 */

public interface BaseConstant {

    //BASE CONSTANT
    public static final String SPLASH_IS_INVISIBLE = "splash_is_invisible";
    public final String LOG_TAG = "myLogs";
    public final String TAG = "myLogs";
    public final String errorLogin = "ErrorLogin";

    //API Constant
    public final String API_URL = "192.168.56.1:81";
    public final String API_KEY_ID = "api_id";
    public int API_GET_FLOWERS = 10001;
    public int API_GET_FLOWER = 10002;
    public final String API_GET_KEY = "api_get_key"; //что выбирать ключ
    public final String API_PAGE = "api_page"; //какую страницу выбрать
    public final int GET_CATEGORY = 0; // список категорий
    public final int GET_PRODUCTS = 1; // список проудктов из категории
    public final String KEY_CATEGORY_ID = "key_category_id"; //каталог продуктов какой категории

    //Action
    public final String ACTION_SERVICE_GET_FLOWERS = "ru.nwts.get_flowers";
    public final String ACTION_SERVICE_GET_CATEGORY_LIST = "ru.nwts.get_category_list";
    public final String ACTION_SERVICE_GET_SUBCATEGORY_LIST = "ru.nwts.get_subcategory_list";
    public final String ACTION_SERVICE_GET_PRODUCT_LIST = "ru.nwts.get_product_list"; //каталог проуктов из одной категории
    public final String ACTION_SERVICE_GET_PRODUCT_DETAIL = "ru.nwts.get_product_detail"; //детализация продукта
    public final String ACTION_SERVICE_GET_CABINET = "ru.nwts.get-cabinet-data"; //информаиця о кабинете

    //URL between activities
    public final String URL_IMAGE_DOWNLOADED = "url_downloaded";
    public final String URL_IMAGE_COUNTS = "url_images_count";

    //TAGS FRAGMENT
    public final static String TAG_CATEGORY_FRAGMENT = "tag_category_fragment";
    public final static String TAG_SUBCATEGORY_FRAGMENT = "tag_subcategory_fragment";
    public final static String TAG_PRODUCT_CATALOG_FRAGMENT = "tag_product_catalog_fragment";
    public final static String TAG_PRODUCT_DETAIL_FRAGMENT = "tag_product_detail_fragment";
    public final static String TAG_MAIN_MENU_FRAGMENT = "tag_main_menu_fragment";

    //EXTRA
    public final static String EX_PRICE = "price";
    public final static String EX_DESCRIPTION = "description";
    public final static String EX_QQUANTITY = "quantity";
    public final static String EX_NAME = "name";
    public final static String EX_CATEGORY_ID = "categoty_id";
    public final static String EX_SUBCATEGORY_ID = "subcategory_id";

}
