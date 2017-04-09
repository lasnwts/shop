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

import java.util.List;

import nwts.ru.autoshop.R;
import nwts.ru.autoshop.TODOApplication;
import nwts.ru.autoshop.fragment.ProductCatalog;
import nwts.ru.autoshop.models.ProductCategory;
import nwts.ru.autoshop.models.SubCategoryItem;
import nwts.ru.autoshop.services.ServiceHelper;
import nwts.ru.autoshop.setting.BaseConstant;

import static nwts.ru.autoshop.api.Api.AdminPageURL;

/**
 * Created by пользователь on 30.03.2017.
 */

public class AdapterSubCategory extends RecyclerView.Adapter<AdapterSubCategory.ViewHolder> {

    private Context context;
    private Activity activity;
    private Drawable background;
    FragmentManager fragmentManager;
    private List<SubCategoryItem> subCategoryList;
    private static final String TAG = BaseConstant.TAG_PRODUCT_CATALOG_FRAGMENT;

    public AdapterSubCategory(List<SubCategoryItem> subCategoryList, Activity activity) {
        this.activity = activity;
        this.subCategoryList = subCategoryList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sub_category_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final SubCategoryItem subCategoryItem = subCategoryList.get(position);
        holder.nameTextView.setText(subCategoryItem.getSubCategory_name());
        holder.priceTextView.setText(subCategoryItem.getSubCategory_name());//??
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            holder.itemView.setBackground(background);
        }
        Picasso mPicasso = Picasso.with(context);
        mPicasso.setIndicatorsEnabled(true);
        mPicasso.setLoggingEnabled(true);
        mPicasso.load(AdminPageURL + subCategoryItem.getSubCategory_image())
                .resize(200, 150)
                .centerCrop()
                .error(R.drawable.error_load_image)
                .into(holder.flowerImageView);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentService = new Intent(context, ServiceHelper.class);
                intentService.setAction(BaseConstant.ACTION_SERVICE_GET_PRODUCT_LIST);
                intentService.putExtra(BaseConstant.API_GET_KEY,subCategoryItem.getSubCategory_ID());
                context.startService(intentService);
                TODOApplication.setCategory_Id(subCategoryItem.getSubCategory_ID()); //установка занчения
                ProductCatalog productCatalog = new ProductCatalog();
                activity.getFragmentManager().beginTransaction().replace(R.id.content_frame,productCatalog,TAG).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (subCategoryList == null) {
            return 0;
        }
        return subCategoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView;
        ImageView flowerImageView;
        TextView priceTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.textViewItemSubCatalog);
            flowerImageView = (ImageView) itemView.findViewById(R.id.imgItemSubCatalog);
            priceTextView = (TextView) itemView.findViewById(R.id.txtSubCatalog);
        }
    }
}
