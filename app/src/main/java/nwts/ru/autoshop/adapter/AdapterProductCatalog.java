package nwts.ru.autoshop.adapter;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import nwts.ru.autoshop.R;
import nwts.ru.autoshop.TODOApplication;
import nwts.ru.autoshop.models.ProductCategory;
import nwts.ru.autoshop.services.ServiceHelper;
import nwts.ru.autoshop.setting.BaseConstant;
import nwts.ru.autoshop.ui.ProductDetailView;

import static nwts.ru.autoshop.api.Api.AdminPageURL;

/**
 * Created by пользователь on 22.03.2017.
 */

public class AdapterProductCatalog extends RecyclerView.Adapter<AdapterProductCatalog.ViewHolder> {

    private Context context;
    private Activity activity;
    private Drawable background;
    FragmentManager fragmentManager;
    private List<ProductCategory> productCategoryList;
    // create price format
    DecimalFormat formatData = new DecimalFormat("0.00");

    public AdapterProductCatalog(List<ProductCategory> productCategoryList, Activity activity) {
        this.activity = activity;
        this.productCategoryList = productCategoryList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_catalog_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ProductCategory productCategory = productCategoryList.get(position);
        holder.nameTextView.setText(productCategory.getMenu_name());
        holder.priceTextView.setText(context.getResources().getString(R.string.price_name) +": "+formatData.format(productCategory.getPrice()));
        holder.quantityTextView.setText(context.getResources().getString(R.string.quantity_name) +": "+productCategory.getQuantity());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            holder.itemView.setBackground(background);
        }
        Picasso mPicasso = Picasso.with(context);
        mPicasso.setIndicatorsEnabled(true);
        mPicasso.setLoggingEnabled(true);
        mPicasso.load(AdminPageURL + productCategory.getMenu_image())
                .resize(200, 150)
                .centerCrop()
                .error(R.drawable.error_load_image)
                .into(holder.flowerImageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TODOApplication.setProduct_Id(productCategory.getProduct_ID());
                //Передаем в ServiceHelper апрос на данные
//                Intent intentService = new Intent(context, ServiceHelper.class);
//                intentService.setAction(BaseConstant.ACTION_SERVICE_GET_PRODUCT_DETAIL);
//                intentService.putExtra(BaseConstant.API_GET_KEY,productCategory.getMenu_ID());
//                context.startService(intentService);
//                Intent intentProductDetail = new Intent(context, ProductDetailView.class);
//                context.startActivity(intentProductDetail);
                //Новый вариант
                Intent intentService = new Intent(context, ServiceHelper.class);
                intentService.setAction(BaseConstant.ACTION_SERVICE_GET_PRODUCT_DETAIL);
                intentService.putExtra(BaseConstant.API_GET_KEY,productCategory.getProduct_ID());
                TODOApplication.setDetail_category_Id(productCategory.getCategory_ID());
                TODOApplication.setDetail_product_Id(productCategory.getProduct_ID());
                TODOApplication.setDetail_subcategory_Id(productCategory.getSubCategory_ID());
                TODOApplication.setDetail_description(productCategory.getDescription());
                TODOApplication.setDetail_productName(productCategory.getMenu_name());
                TODOApplication.setDetail_price(productCategory.getPrice());
                TODOApplication.setDetail_quantity(productCategory.getQuantity());
                TODOApplication.setUrl_Image(productCategory.getMenu_image());
//                intentService.putExtra(BaseConstant.EX_NAME,productCategory.getMenu_name());
//                intentService.putExtra(BaseConstant.EX_CATEGORY_ID,productCategory.getCategory_ID());
//                intentService.putExtra(BaseConstant.EX_SUBCATEGORY_ID,productCategory.getSubCategory_ID());
//                intentService.putExtra(BaseConstant.EX_PRICE,productCategory.getPrice());
//                intentService.putExtra(BaseConstant.EX_QQUANTITY,productCategory.getQuantity());
//                intentService.putExtra(BaseConstant.EX_DESCRIPTION,productCategory.getDescription());
                context.startService(intentService);
                Intent intentProductDetail = new Intent(context, ProductDetailView.class);
                context.startActivity(intentProductDetail);
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

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.textViewItemProductCatalog);
            flowerImageView = (ImageView) itemView.findViewById(R.id.imgItemProductCatalog);
            priceTextView = (TextView) itemView.findViewById(R.id.txtPriceProductCatalog);
            quantityTextView = (TextView) itemView.findViewById(R.id.txtQuantityProductCatalog);
        }
    }
}
