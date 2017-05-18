package nwts.ru.autoshop.network.request;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.List;
import java.util.concurrent.TimeUnit;

import nwts.ru.autoshop.models.authority.AccessCreateUser;
import nwts.ru.autoshop.models.authority.UserModel;
import nwts.ru.autoshop.models.network.CabinetModel;
import nwts.ru.autoshop.models.network.OrderModel;
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

    //Create Login
    @Headers("Cache-Control: max-age=640000")
    @FormUrlEncoded
    @POST(Api.GET_CREATE_LOGIN)
    Call<AccessCreateUser> createUser(@Field("username") String userEmail, @Field("password") String userPassword, @Field("name") String userName);

    //Login get token
    @Headers("Cache-Control: max-age=640000")
    @FormUrlEncoded
    @POST(Api.GET_LOGIN)
    Call<UserModel> getUser(@Field("username") String userEmail, @Field("password") String userPassword);

    @GET(Api.GET_CATEGORY)
    Call<List<CategoryItem>> getData();

    @GET(Api.GET_PRODUCT_CATEGORY)
    Call<List<ProductCategory>> getProductCatalog(@Query("category_id") int id_category);

    @GET(Api.GET_PRODUCT_SUBCATEGORY)
    Call<List<SubCategoryItem>> getSubCatalog(@Query("category_id") int id_category);

    @GET(Api.GET_PRODUCT_LIST)
    Call<List<SubCategoryItem>> getProductList(@Query("category_id") int id_category);

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
