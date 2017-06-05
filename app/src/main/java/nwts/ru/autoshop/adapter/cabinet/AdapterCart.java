package nwts.ru.autoshop.adapter.cabinet;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import nwts.ru.autoshop.R;
import nwts.ru.autoshop.adapter.interfaces.AdapterClickListener;
import nwts.ru.autoshop.adapter.interfaces.AdatpterLongClickListener;
import nwts.ru.autoshop.databases.DataManager;
import nwts.ru.autoshop.models.network.cart.CartModel;

import static com.mikepenz.iconics.Iconics.TAG;
import static nwts.ru.autoshop.network.api.Api.GET_IMAGES;
import static nwts.ru.autoshop.R.id.txt_cart_name;

/**
 * Created by пользователь on 21.05.2017.
 */

public class AdapterCart extends RecyclerView.Adapter<AdapterCart.ViewHolder> {

    private Context context;
    private Activity activity;
    private Drawable background;
    private AdapterClickListener mAdapterClickListener;
    private AdatpterLongClickListener mAdatpterLongClickListener;
    List<CartModel> mCartModels;
    DecimalFormat formatData = new DecimalFormat("0.00");
    SimpleDateFormat dateformat = new SimpleDateFormat("dd.MM.yyyy");
/*
    //detemine width display
    DisplayMetrics dm;
    //display width in dp
    double density;*/

    public AdapterCart(List<CartModel> cartModels, Activity activity, AdapterClickListener adapterClickListener,
                       AdatpterLongClickListener adatpterLongClickListener) {
        this.activity = activity;
        mAdapterClickListener = adapterClickListener;
        mAdatpterLongClickListener = adatpterLongClickListener;
        mCartModels = cartModels;
        //     dm = activity.getResources().getDisplayMetrics();

    }

    @Override
    public AdapterCart.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        final View view;
//        if (dm.density * 160 < 5){
//             view = LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.item_element_cart, parent, false);
//        } else {
//             view = LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.item_element_cart, parent, false);
//        }
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_element_cart, parent, false);
        return new ViewHolder(view, mAdapterClickListener, mAdatpterLongClickListener);
    }

    @Override
    public void onBindViewHolder(final AdapterCart.ViewHolder holder, int position) {
        final CartModel cartModel = mCartModels.get(position);
//        if (dm.density * 160 < 5){
//            holder.txtDate.setText(cartModel.getMenu_name().substring(0,22));
//        } else {
//            holder.txtDate.setText(cartModel.getMenu_name());
//        }
        holder.txtDate.setText(cartModel.getMenu_name());
        holder.txtSumma.setText(cartModel.getTovar_CountS());
        holder.txtStatus.setText(formatData.format(cartModel.getSumma()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            holder.itemView.setBackground(background);
        }
        //get image
        if (holder.mImageView != null) {
            picassoGetImages(holder, cartModel);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.mAdapterClickListener.adapterOnClickListener(cartModel.getTovar_ID());
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                holder.mAdatpterLongClickListener.adatpterLongClickListener(cartModel.getTovar_ID(),
                        cartModel.getSumma(),cartModel.getTovar_Count());
                return true;
             }
        });
    }


    @Override
    public int getItemCount() {
        if (mCartModels == null) {
            return 0;
        }
        return mCartModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtDate, txtSumma, txtStatus;
        ImageView mImageView;
        //        TextView divider1, divider2;
        AdapterClickListener mAdapterClickListener;
        AdatpterLongClickListener mAdatpterLongClickListener;

        public ViewHolder(View itemView, AdapterClickListener adapterClickListener, AdatpterLongClickListener adatpterLongClickListener) {
            super(itemView);
            txtDate = (TextView) itemView.findViewById(txt_cart_name);
            txtSumma = (TextView) itemView.findViewById(R.id.txt_cart_count);
            txtStatus = (TextView) itemView.findViewById(R.id.txt_cart_summa);
            mImageView = (ImageView) itemView.findViewById(R.id.img_view_cart);
//            divider1 = (TextView) itemView.findViewById(R.id.cart_diviuder1);
//            divider2 = (TextView) itemView.findViewById(R.id.cart_diviuder2);
//            if (txtDate.getHeight() > divider1.getHeight()){
//                divider1.setHeight(txtDate.getHeight());
//            }
            mAdapterClickListener = adapterClickListener;
            mAdatpterLongClickListener = adatpterLongClickListener;
        }
    }


    private void picassoGetImages(final ViewHolder holder, final CartModel cartModel) {
        DataManager.getInstance().getPicasso()
                .load(GET_IMAGES + cartModel.getMenu_image())
                //.fit()
                .centerInside()
//                .centerCrop()
                .resize(200, 150)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .error(R.drawable.error_load_image)
                .placeholder(R.drawable.error_load_image)
                .into(holder.mImageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "load from cache");
                    }

                    @Override
                    public void onError() {
                        DataManager.getInstance().getPicasso()
                                .load(GET_IMAGES + cartModel.getMenu_image())
                                //  .fit()
                                .centerCrop()
                                .resize(200, 150)
                                .error(R.drawable.error_load_image)
                                .placeholder(R.drawable.error_load_image)
                                .into(holder.mImageView, new Callback() {
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
