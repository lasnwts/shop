package nwts.ru.autoshop.network.api;

import nwts.ru.autoshop.setting.BaseConstant;

/**
 * Created by пользователь on 13.03.2017.
 */

public interface Api {

    public String BASE_URL_SHOP_FULL = "http://"+ BaseConstant.API_URL+"/";
    public String GET_CATEGORY = "ecommerce/apiv2/get-all-category-data.php?accesskey=12345";
    public String GET_PRODUCT_CATEGORY = "ecommerce/apiv2/get-data-by-productlist-id.php?accesskey=12345";
    public String GET_PRODUCT_LIST = "ecommerce/apiv2/get-data-by-productlist-id.php?accesskey=12345";
    public String GET_PRODUCT_SUBCATEGORY = "ecommerce/apiv2/get-menu-data-by-subcategory-id.php?accesskey=12345";
    public String GET_PRODUCT_DETAIL = "ecommerce/apiv2/get-data-by-product_detail.php?accesskey=12345";
    public String GET_IMAGES = "http://"+BaseConstant.API_URL+"/ecommerce/";
    // change this access similar with accesskey in admin panel for security reason
    public String AccessKey = "12345";
    public String GET_LOGIN = "/ecommerce/v1/studentlogin";
    public String GET_CREATE_LOGIN  = "/ecommerce/v1/createstudent";
    public String GET_VALIDATE_APIKEY  = "/ecommerce/apiv2/get-validate_apikey.php";
    public String GET_CABINET_APIKEY  = "/ecommerce/apiv2/get-cabinet-data.php";
    public String GET_CABINET_CART  = "/ecommerce/apiv2/get-cabinet-cart.php";
    public String GET_CABINET_ORDERS  = "/ecommerce/apiv2/get-cabinet-orders.php";
    public String GET_CABINET_BALANCE  = "/ecommerce/apiv2/get-cabinet-balance.php";
    public String GET_CABINET_ADD_BALANCE  = "/ecommerce/apiv2/get-cabinet-add-balancer.php";
    public String GET_CABINET_BALANCE_ID  = "/ecommerce/apiv2/get-cabinet-balance-id.php";
    public String GET_CABINET_PROCESSIN_ID  = "/ecommerce/apiv2/get-cabinet-cart-processing-id.php";
    public String GET_CABINET_CART_INPUT = "/ecommerce/apiv2/get-cabinet-cart-input-tovar.php";
    public String GET_PRODUCT_COMMENT_ID = "/ecommerce/apiv2/get-data-by-comments_id.php";
    public String GET_PRODUCT_ADD_COMMENT_ID = "/ecommerce/apiv2/add-comment-to-tovar.php";
    public String GET_PRODUCT_BY_ID = "ecommerce/apiv2/get-data-by-product-id.php?accesskey=12345";
}
