package nwts.ru.autoshop.api.request;

import java.util.List;
import java.util.concurrent.TimeUnit;

import nwts.ru.autoshop.api.Api;
import nwts.ru.autoshop.models.CategoryItem;
import nwts.ru.autoshop.models.ProductCategory;
import nwts.ru.autoshop.models.ProductDetail;
import nwts.ru.autoshop.models.ProductDetailImage;
import nwts.ru.autoshop.models.SubCategoryItem;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
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
 */

public interface ShopAPI {

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
            .build();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Api.BASE_URL_SHOP_FULL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build();
}
