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

import com.squareup.picasso.Picasso;

import java.util.List;

import nwts.ru.autoshop.R;
import nwts.ru.autoshop.TODOApplication;
import nwts.ru.autoshop.adapter.interfaces.AdapterClickListener;
import nwts.ru.autoshop.models.CategoryItem;
import nwts.ru.autoshop.setting.BaseConstant;

import static nwts.ru.autoshop.network.api.Api.GET_IMAGES;

/**
 * Адаптер показа всех категорий (корневой каталог категорий)
 * Created by пользователь on 17.03.2017.
 */

public class AdapterCategoryList extends RecyclerView.Adapter<AdapterCategoryList.ViewHolder>{

    private final static String PHOTO_URL = GET_IMAGES;
    private List<CategoryItem> categoryItems;
    private Context context;
    private Activity activity;
    private AdapterClickListener mAdapterClickListener;
    private Drawable background;

    public AdapterCategoryList(List<CategoryItem> categoryItems, Activity activity, AdapterClickListener mAdapterClickListener) {
        this.categoryItems = categoryItems;
        this.activity = activity;
        this.mAdapterClickListener = mAdapterClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        final View v = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.category_list_item, parent, false);
        ViewHolder view = new ViewHolder(v, mAdapterClickListener);
        return view;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final CategoryItem categoryItem = categoryItems.get(position);
        holder.nameTextView.setText(categoryItem.getCategory_name());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            holder.itemView.setBackground(background);
        }
        Log.d(BaseConstant.TAG,"onBindViewHolder:");
        Log.d(BaseConstant.TAG,"onBindViewHolder:categoryItem:name:"+categoryItem.getCategory_name());
        Log.d(BaseConstant.TAG,"onBindViewHolder:BASE_URL_SHOP_FULL + categoryItem.getCategory_image:"+ GET_IMAGES + categoryItem.getCategory_image());
        Picasso mPicasso = Picasso.with(context);
        mPicasso.setIndicatorsEnabled(true);
        mPicasso.setLoggingEnabled(true);
        mPicasso.load(GET_IMAGES + categoryItem.getCategory_image())
                .resize(200, 150)
                .centerCrop()
                .error(R.drawable.error_load_image)
                .into(holder.flowerImageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TODOApplication.setSubCategory_Id(categoryItem.getCategory_ID()); //установка занчения
                holder.mAdapterClickListener.adapterOnClickListener(categoryItem.getCategory_ID());
            }
        });
    }

    @Override
    public int getItemCount() {
        if (categoryItems == null) {
            return 0;
        }
        return categoryItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        ImageView flowerImageView;
        AdapterClickListener mAdapterClickListener;

        public ViewHolder(View itemView, AdapterClickListener adapterClickListener) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.txtText);
            flowerImageView = (ImageView) itemView.findViewById(R.id.imgThumb);
            mAdapterClickListener = adapterClickListener;
        }
    }}
