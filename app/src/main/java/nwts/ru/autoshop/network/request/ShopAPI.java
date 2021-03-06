package nwts.ru.autoshop.network.request;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.List;
import java.util.concurrent.TimeUnit;

import nwts.ru.autoshop.models.authority.AccessCreateUser;
import nwts.ru.autoshop.models.authority.UserModel;
import nwts.ru.autoshop.models.network.BalanceModel;
import nwts.ru.autoshop.models.network.CabinetModel;
import nwts.ru.autoshop.models.network.OrderModel;
import nwts.ru.autoshop.models.network.ProductComment;
import nwts.ru.autoshop.models.network.ProductSearch;
import nwts.ru.autoshop.models.network.cart.CartModel;
import nwts.ru.autoshop.models.network.cart.ErrorModel;
import nwts.ru.autoshop.models.network.orders.BalOrderModel;
import nwts.ru.autoshop.network.HeaderInterceptor;
import nwts.ru.autoshop.network.HeaderLoggingInterceptor;
import nwts.ru.autoshop.network.api.Api;
import nwts.ru.autoshop.models.CategoryItem;
import nwts.ru.autoshop.models.ProductCategory;
import nwts.ru.autoshop.models.ProductDetailImage;
import nwts.ru.autoshop.models.SubCategoryItem;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by пользователь on 17.03.2017.
 */

/*
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.mustakimov.retrofittutorial.PostModel;

public interface UmoriliApi {
    @GET("/api/get")
    Call<List<PostModel>> getData(@Query("name") String resourceName, @Query("num") int count);
}

200	OK
201	Created
304	Not Modified
400	Bad Request
401	Unauthorized
403	Forbidden
404	Not Found
422	Unprocessable Entity
500	Internal Server Error

 */

public interface ShopAPI {

    //validate token
    //@Headers("Cache-Control: max-age=640000")
    @GET(Api.GET_VALIDATE_APIKEY)
    Call<UserModel> getValidateToken();

    @GET(Api.GET_CABINET_APIKEY)
    Call<List<CabinetModel>> getCabinet();

    //get Orders
    @GET(Api.GET_CABINET_ORDERS)
    Call<List<OrderModel>> getCabinetOrders();

    //get Balance
    @GET(Api.GET_CABINET_BALANCE)
    Call<List<BalanceModel>> getCabinetBalance();

    //get Balance
    @GET(Api.GET_CABINET_CART)
    Call<List<CartModel>> getCart();

    //search
    @GET(Api.GET_PRODUCT_CATEGORY_BY_NAME)
    Call<List<ProductSearch>> getProductSearch(@Query("category_id") String nameProduct);

    //find
    @GET(Api.GET_PRODUCT_CATEGORY_BY_FIND_NAME)
    Call<List<ProductCategory>> getProductCatalogFindByName(@Query("category_name") String nameFindProducts);

    //add balance
    @Headers("Cache-Control: max-age=640000")
    @FormUrlEncoded
    @POST(Api.GET_CABINET_ADD_BALANCE)
    Call<ErrorModel> addBalance(@Field("summa") double summa, @Field("paysys") String paySystem);

    //Post processing
    @FormUrlEncoded
    @POST(Api.GET_CABINET_PROCESSIN_ID)
    Call<ErrorModel> getProcessingCart(@Field("user_id") int userID, @Field("status_id") int statusID);

    @FormUrlEncoded
    @POST(Api.GET_CABINET_CART_INPUT)
    Call<List<ErrorModel>> getInputCart(@Field("user_id") int userID, @Field("product_id") int productID,@Field("quantity") int quantity);

    //id balance
    @FormUrlEncoded
    @POST(Api.GET_CABINET_BALANCE_ID)
    Call<List<BalOrderModel>> getBalOrdersId(@Field("order_id") int orderId);

    //Create Login
    @Headers("Cache-Control: max-age=640000")
    @FormUrlEncoded
    @POST(Api.GET_CREATE_LOGIN)
    Call<AccessCreateUser> createUser(@Field("username") String userEmail, @Field("password") String userPassword, @Field("name") String userName);

    //add comments
    @FormUrlEncoded()
    @POST(Api.GET_PRODUCT_ADD_COMMENT_ID)
    Call<List<ErrorModel>>  addComment(@Field("user_id") int userId, @Field("product_id") int productID,@Field("rating") int rating, @Field("messages") String messages);

    //del from cart
    @FormUrlEncoded
    @POST(Api.GET_PRODUCT_DEL_BY_CART_ID)
    Call<List<ErrorModel>> getDeleteFromCart(@Field("product_id") int productID, @Field("summa_del") double summaDel, @Field("quantity_del") int quantityDel);

    //Login get token
    @Headers("Cache-Control: max-age=640000")
    @FormUrlEncoded
    @POST(Api.GET_LOGIN)
    Call<UserModel> getUser(@Field("username") String userEmail, @Field("password") String userPassword);

    @GET(Api.GET_CATEGORY)
    Call<List<CategoryItem>> getData();

    @GET(Api.GET_PRODUCT_CATEGORY)
    Call<List<ProductCategory>> getProductCatalog(@Query("category_id") int id_category);

    @GET(Api.GET_PRODUCT_BY_ID)
    Call<List<ProductCategory>> getProductDetailID(@Query("category_id") int productID);

    @GET(Api.GET_PRODUCT_SUBCATEGORY)
    Call<List<SubCategoryItem>> getSubCatalog(@Query("category_id") int id_category);

    @GET(Api.GET_PRODUCT_LIST)
    Call<List<SubCategoryItem>> getProductList(@Query("category_id") int id_category);

    @GET(Api.GET_PRODUCT_COMMENT_ID)
    Call<List<ProductComment>> getComments(@Query("category_id") int product_id);

    @GET(Api.GET_PRODUCT_DETAIL)
    Call<List<ProductDetailImage>> getProductDetail(@Query("category_id") int id_category);


    OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(new HeaderInterceptor())
            .addInterceptor((Interceptor) new HeaderLoggingInterceptor().getLoggingRetrofit())
            .addNetworkInterceptor(new StethoInterceptor())
            .build();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Api.BASE_URL_SHOP_FULL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build();
}
