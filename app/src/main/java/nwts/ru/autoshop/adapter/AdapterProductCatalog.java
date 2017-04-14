package nwts.ru.autoshop.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;

import java.text.DecimalFormat;
import java.util.List;

import nwts.ru.autoshop.R;
import nwts.ru.autoshop.TODOApplication;
import nwts.ru.autoshop.databases.DataManager;
import nwts.ru.autoshop.adapter.interfaces.AdapterClickListener;
import nwts.ru.autoshop.models.ProductCategory;

import static android.content.ContentValues.TAG;
import static nwts.ru.autoshop.network.api.Api.GET_IMAGES;

/**
 *  Адаптер показа товраров в подкатегории (3-ий уровень)
 *  Это уже не категории а товары
 * Created by пользователь on 22.03.2017.
 */

public class AdapterProductCatalog extends RecyclerView.Adapter<AdapterProductCatalog.ViewHolder> {

    private Context context;
    private Activity activity;
    private Drawable background;
    private List<ProductCategory> productCategoryList;
    // create price format
    DecimalFormat formatData = new DecimalFormat("0.00");
    private AdapterClickListener mAdapterClickListener;

    public AdapterProductCatalog(List<ProductCategory> productCategoryList,
                                 Activity activity, AdapterClickListener mAdapterClickListener) {
        this.activity = activity;
        this.productCategoryList = productCategoryList;
        this.mAdapterClickListener = mAdapterClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_catalog_item, parent, false);
        return new ViewHolder(view, mAdapterClickListener);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final ProductCategory productCategory = productCategoryList.get(position);
        holder.nameTextView.setText(productCategory.getMenu_name());
        holder.priceTextView.setText(context.getResources().getString(R.string.price_name) + ": " + formatData.format(productCategory.getPrice()));
        holder.quantityTextView.setText(context.getResources().getString(R.string.quantity_name) + ": " + productCategory.getQuantity());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            holder.itemView.setBackground(background);
        }

        DataManager.getInstance().getPicasso()
                .load(GET_IMAGES + productCategory.getMenu_image())
                //.fit()
                .centerInside()
//                .centerCrop()
                .resize(200, 150)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .error(R.drawable.error_load_image)
                .placeholder(R.drawable.error_load_image)
                .into(holder.flowerImageView, new Callback() {
                            @Override
                            public void onSuccess() {
                                Log.d(TAG, "load from cache");
                            }

                            @Override
                            public void onError() {
                                DataManager.getInstance().getPicasso()
                                        .load(GET_IMAGES + productCategory.getMenu_image())
                                      //  .fit()
                                        .centerCrop()
                                        .resize(200, 150)
                                        .error(R.drawable.error_load_image)
                                        .placeholder(R.drawable.error_load_image)
                                        .into(holder.flowerImageView, new Callback() {
                                            @Override
                                            public void onSuccess() {
                                                Log.d(TAG, "Save from network - fetch image");
                                            }

                                            @Override
                                            public void onError() {
                                                Log.d(TAG, "Could not fetch image");
                                            }
                                        });
                            }
                        });


/*

        Picasso mPicasso = Picasso.with(context);
        mPicasso.setIndicatorsEnabled(true);
        mPicasso.setLoggingEnabled(true);
        mPicasso.load(GET_IMAGES + productCategory.getMenu_image())
                .resize(200, 150)
                .centerCrop()
                .error(R.drawable.error_load_image)
                .into(holder.flowerImageView);

 */

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TODOApplication.setProduct_Id(productCategory.getProduct_ID());
                //Передаем в ServiceHelper апрос на данные
                TODOApplication.setDetail_category_Id(productCategory.getCategory_ID());
                TODOApplication.setDetail_product_Id(productCategory.getProduct_ID());
                TODOApplication.setDetail_subcategory_Id(productCategory.getSubCategory_ID());
                TODOApplication.setDetail_description(productCategory.getDescription());
                TODOApplication.setDetail_productName(productCategory.getMenu_name());
                TODOApplication.setDetail_price(productCategory.getPrice());
                TODOApplication.setDetail_quantity(productCategory.getQuantity());
                TODOApplication.setUrl_Image(productCategory.getMenu_image());
                holder.mAdapterClickListener.adapterOnClickListener(productCategory.getProduct_ID());
            }
        });
    }

    @Override
    public int getItemCount() {
        if (productCategoryList == null) {
            return 0;
        }
        return productCategoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        ImageView flowerImageView;
        TextView priceTextView;
        TextView quantityTextView;
        AdapterClickListener mAdapterClickListener;

        public ViewHolder(View itemView, AdapterClickListener adapterClickListener) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.textViewItemProductCatalog);
            flowerImageView = (ImageView) itemView.findViewById(R.id.imgItemProductCatalog);
            priceTextView = (TextView) itemView.findViewById(R.id.txtPriceProductCatalog);
            quantityTextView = (TextView) itemView.findViewById(R.id.txtQuantityProductCatalog);
            mAdapterClickListener = adapterClickListener;
        }
    }
}
