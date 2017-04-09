package nwts.ru.autoshop.api;

/**
 * Created by пользователь on 13.03.2017.
 */

public interface Api {
    public String BASE_URL ="http://services.hanselandpetal.com";
    public String BASE_URL_FULL ="http://services.hanselandpetal.com/";
    public String GET_FLOWERS ="feeds/flowers.json";
    public String URL_SEPARATOR = "/";


    public String BASE_URL_SHOP = "http://192.168.56.1:81";
    public String BASE_URL_SHOP_FULL = "http://192.168.56.1:81/";
    public String GET_CATEGORY = "ecommerce/apiv2/get-all-category-data.php?accesskey=12345";
    public String GET_PRODUCT_CATEGORY = "ecommerce/apiv2/get-data-by-productlist-id.php?accesskey=12345";
    public String GET_PRODUCT_LIST_OLD = "ecommerce/apiv2/get-menu-data-by-category-id.php?accesskey=12345";
    public String GET_PRODUCT_LIST = "ecommerce/apiv2/get-data-by-productlist-id.php?accesskey=12345";
    public String GET_PRODUCT_SUBCATEGORY = "ecommerce/apiv2/get-menu-data-by-subcategory-id.php?accesskey=12345";
    public String GET_PRODUCT_DETAIL = "ecommerce/apiv2/get-data-by-product_detail.php?accesskey=12345";
    public String GET_PRODUCT_DETAIL_OLD = "ecommerce/apiv2/get-menu-detail.php?accesskey=12345";
    public String GET_IMAGES = "ecommerce/upload/images/";
    public String AdminPageURL = "http://192.168.56.1:81/ecommerce/";


    public String CategoryAPI = "http://192.168.56.1:81/ecommerce/api/get-all-category-data.php";
    public String MenuAPI = "http://192.168.56.1:81/ecommerce/api/get-menu-data-by-category-id.php";
    public String TaxCurrencyAPI = "http://192.168.56.1:81/ecommerce/api/get-tax-and-currency.php";
    public String MenuDetailAPI = "http://192.168.56.1:81/ecommerce/api/get-menu-detail.php";
    public String SendDataAPI = "http://192.168.56.1:81/ecommerce/api/add-reservation.php";
    // change this access similar with accesskey in admin panel for security reason
    public String AccessKey = "12345";
}
