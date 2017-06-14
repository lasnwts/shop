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
    public final String errorNetworkValidation = "ErrorNetValidation";


    //API Constant
    /*
        Local variables
     */
//    public final String API_URL = "192.168.56.1:81";
    /*
        Remote
     */
    public final String API_URL = "www.nwts.ru";


    public final String API_KEY_ID = "api_id";
    public final String API_GET_KEY = "api_get_key"; //что выбирать ключ
    public final String API_BAL_SUM = "api_bul_sum";
    public final String API_BAL_SYS = "api_bul_sys";
    public final String API_RATING = "api_rate_sys";
    public final String API_QUANTITY = "api_quantity_sys";
    public final String API_PRODUCT_ID = "api_product_sys";
    public final String API_COOMENT = "api_comment_sys";


    public final String API_PAGE = "api_page"; //какую страницу выбрать
    public final int GET_CATEGORY = 0; // список категорий
    public final int GET_PRODUCTS = 1; // список проудктов из категории
    public final String KEY_CATEGORY_ID = "key_category_id"; //каталог продуктов какой категории

    //Action
    public final String ACTION_SERVICE_GET_CATEGORY_LIST = "ru.nwts.get_category_list";
    public final String ACTION_SERVICE_GET_SUBCATEGORY_LIST = "ru.nwts.get_subcategory_list";
    public final String ACTION_SERVICE_GET_PRODUCT_LIST = "ru.nwts.get_product_list"; //каталог проуктов из одной категории
    public final String ACTION_SERVICE_GET_PRODUCT_DETAIL = "ru.nwts.get_product_detail"; //детализация продукта
    public final String ACTION_SERVICE_GET_PRODUCT_DETAIL_ID = "ru.nwts.get_product_detail_id"; //детализация продукта по id
    public final String ACTION_SERVICE_GET_CABINET = "ru.nwts.get-cabinet-data"; //информаиця о кабинете
    public final String ACTION_SERVICE_GET_ORDERS = "ru.nwts.get-cabinet-orders"; //информаиця о заказах
    public final String ACTION_SERVICE_GET_BALANCE = "ru.nwts.get-cabinet-balance"; //информаиця о балансе
    public final String ACTION_SERVICE_GET_CART = "ru.nwts.get-cabinet-cart"; //информаиця о корзине
    public final String ACTION_SERVICE_GET_BALANCE_ADD = "ru.nwts.get-cabinet-add-balancer"; //пополнить баланс
    public final String ACTION_SERVICE_GET_BALANCE_ID = "ru.nwts.get-cabinet-balance-id";
    public final String ACTION_SERVICE_GET_PROCESSING_ID = "ru.nwts.get-cabinet-processing-id"; //обработка в корзине
    public final String ACTION_SERVICE_GET_CART_INPUT = "ru.nwts.get-cabinet-cart-input-tovar"; //товар в корзину
    public final String ACTION_SERVICE_GET_COMMENTS_ID = "ru.nwts.get-data-by-comments_id"; //получить отзывы о продукте
    public final String ACTION_SERVICE_ADD_COMMENTS = "ru.nwts.add-comment-to-tovar"; //add comments to tovar
    public final String ACTION_SERVICE_DEL_CART = "ru.nwts.get-data-by-delete-product-from-cart"; //del tovar from cart
    public final String ACTION_SERVICE_GET_FIND_PRODUCT = "ru.nwts.get-data-by-productlist-name"; //find catalog by name
    //URL between activities
    public final String URL_IMAGE_DOWNLOADED = "url_downloaded";
    public final String URL_IMAGE_COUNTS = "url_images_count";

    //TAGS FRAGMENT
    public final static String TAG_CATEGORY_FRAGMENT = "tag_category_fragment";
    public final static String TAG_SUBCATEGORY_FRAGMENT = "tag_subcategory_fragment";
    public final static String TAG_PRODUCT_CATALOG_FRAGMENT = "tag_product_catalog_fragment";
    public final static String TAG_PRODUCT_DETAIL_FRAGMENT = "tag_product_detail_fragment";
    public final static String TAG_MAIN_MENU_FRAGMENT = "tag_main_menu_fragment";
    //Cabinet fragment
    public final static String TAG_CABINET = "tag_cabinet";
    public final static String TAG_ORDERS_FRAGMENT = "tag_orders_fragment";
    public final static String TAG_BALANCE_FRAGMENT = "tag_balance_fragment";
    public final static String TAG_CART_FRAGMENT = "tag_cart_fragment";
    public final static String TAG_BAL_ORDER_FRAGMENT = "tag_bal_order_fragment";

    //EXTRA
    public final static String EX_PRICE = "price";
    public final static String EX_DESCRIPTION = "description";
    public final static String EX_QQUANTITY = "quantity";
    public final static String EX_NAME = "name";
    public final static String EX_CATEGORY_ID = "categoty_id";
    public final static String EX_SUBCATEGORY_ID = "subcategory_id";

}
