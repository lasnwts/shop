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
import com.squareup.picasso.Picasso;

import java.util.List;

import nwts.ru.autoshop.R;
import nwts.ru.autoshop.TODOApplication;
import nwts.ru.autoshop.adapter.interfaces.AdapterClickListener;
import nwts.ru.autoshop.databases.DataManager;
import nwts.ru.autoshop.models.CategoryItem;
import nwts.ru.autoshop.models.SubCategoryItem;

import static com.mikepenz.iconics.Iconics.TAG;
import static nwts.ru.autoshop.network.api.Api.GET_IMAGES;

/**
 * Адаптер показа подкатегорий товаров (второй уровень)
 * Created by пользователь on 30.03.2017.
 */

public class AdapterSubCategory extends RecyclerView.Adapter<AdapterSubCategory.ViewHolder> {

    private Context context;
    private Activity activity;
    private Drawable background;
    private List<SubCategoryItem> subCategoryList;
    private AdapterClickListener mAdapterClickListener;

    public AdapterSubCategory(List<SubCategoryItem> subCategoryList, Activity activity, AdapterClickListener mAdapterClickListener) {
        this.activity = activity;
        this.subCategoryList = subCategoryList;
        this.mAdapterClickListener = mAdapterClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sub_category_item, parent, false);
        return new ViewHolder(view, mAdapterClickListener);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final SubCategoryItem subCategoryItem = subCategoryList.get(position);
        holder.nameTextView.setText(subCategoryItem.getSubCategory_name());
        holder.priceTextView.setText(subCategoryItem.getSubCategory_name());//??
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            holder.itemView.setBackground(background);
        }

        picassoGetImages(holder, subCategoryItem);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TODOApplication.setCategory_Id(subCategoryItem.getSubCategory_ID()); //установка занчения
                holder.mAdapterClickListener.adapterOnClickListener(subCategoryItem.getSubCategory_ID());
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
        AdapterClickListener mAdapterClickListener;

        public ViewHolder(View itemView, AdapterClickListener adapterClickListener) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.textViewItemSubCatalog);
            flowerImageView = (ImageView) itemView.findViewById(R.id.imgItemSubCatalog);
            priceTextView = (TextView) itemView.findViewById(R.id.txtSubCatalog);
            mAdapterClickListener = adapterClickListener;
        }
    }

    /**
     * Get Images Picasso and save images in cahce dir
     *
     * @param holder          -   this is holder item
     * @param subCategoryItem -   this is product subcategory
     */
    private void picassoGetImages(final ViewHolder holder, final SubCategoryItem subCategoryItem) {
        DataManager.getInstance().getPicasso()
                .load(GET_IMAGES + subCategoryItem.getSubCategory_image())
                .centerInside()
                .resize(220, 200)
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
                                .load(GET_IMAGES + subCategoryItem.getSubCategory_image())
                                .centerCrop()
                                .resize(220, 200)
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
    }

}
