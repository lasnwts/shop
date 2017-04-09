package nwts.ru.autoshop.adapter;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
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
import nwts.ru.autoshop.fragment.ProductCatalog;
import nwts.ru.autoshop.fragment.SubCatalog;
import nwts.ru.autoshop.models.CategoryItem;
import nwts.ru.autoshop.services.ServiceHelper;
import nwts.ru.autoshop.setting.BaseConstant;

import static nwts.ru.autoshop.api.Api.AdminPageURL;

/**
 * Created by пользователь on 17.03.2017.
 */

public class AdapterCategoryList extends RecyclerView.Adapter<AdapterCategoryList.ViewHolder>{

    private final static String PHOTO_URL = AdminPageURL;
    private List<CategoryItem> categoryItems;
    private Context context;
    private Activity activity;
    private Drawable background;
    FragmentManager fragmentManager;
    private static final String TAG = BaseConstant.TAG_SUBCATEGORY_FRAGMENT;


    public AdapterCategoryList(List<CategoryItem> categoryItems, Activity activity) {
        this.categoryItems = categoryItems;
        this.activity = activity;
//        background = ResourcesCompat.getDrawable(context.getResources(),
//                R.drawable.button_top_style_1, null);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        final View view = LayoutInflater.from(parent.getContext())
          //      .inflate(R.layout.list_item, parent, false);
        .inflate(R.layout.category_list_item, parent, false);
        //
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final CategoryItem categoryItem = categoryItems.get(position);
        holder.nameTextView.setText(categoryItem.getCategory_name());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            holder.itemView.setBackground(background);
        }
        // holder.flowerImageView.setImageDrawable(context.getResources().getDrawable(R.drawable.cart));
        Log.d(BaseConstant.TAG,"onBindViewHolder:");
        Log.d(BaseConstant.TAG,"onBindViewHolder:categoryItem:name:"+categoryItem.getCategory_name());
        Log.d(BaseConstant.TAG,"onBindViewHolder:BASE_URL_SHOP_FULL + categoryItem.getCategory_image:"+AdminPageURL + categoryItem.getCategory_image());
        Picasso mPicasso = Picasso.with(context);
        mPicasso.setIndicatorsEnabled(true);
        mPicasso.setLoggingEnabled(true);
        mPicasso.load(AdminPageURL + categoryItem.getCategory_image())
                .resize(200, 150)
                .centerCrop()
                .error(R.drawable.error_load_image)
                .into(holder.flowerImageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(BaseConstant.TAG,"onBindViewHolder:"+categoryItem.getCategory_name());
                Log.d(BaseConstant.TAG,"onBindViewHolder:getCategory_ID:"+categoryItem.getCategory_ID());
                //Передаем в ServiceHelper апрос на данные
                Intent intentService = new Intent(context, ServiceHelper.class);
         //       intentService.setAction(BaseConstant.ACTION_SERVICE_GET_PRODUCT_LIST);
                intentService.setAction(BaseConstant.ACTION_SERVICE_GET_SUBCATEGORY_LIST);
                intentService.putExtra(BaseConstant.API_GET_KEY,categoryItem.getCategory_ID());
                context.startService(intentService);
                TODOApplication.setSubCategory_Id(categoryItem.getCategory_ID()); //установка занчения
         //       ProductCatalog productCatalog = new ProductCatalog();
                SubCatalog subCatalog = new SubCatalog();
                activity.getFragmentManager().beginTransaction().replace(R.id.content_frame,subCatalog,TAG)
                        .commit();
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

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.txtText);
            flowerImageView = (ImageView) itemView.findViewById(R.id.imgThumb);
//            nameTextView = (TextView) itemView.findViewById(R.id.nameTextView);
//            flowerImageView = (ImageView) itemView.findViewById(itemImageView);
        }
    }}
