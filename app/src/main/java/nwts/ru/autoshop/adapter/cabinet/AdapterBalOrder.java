package nwts.ru.autoshop.adapter.cabinet;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import nwts.ru.autoshop.R;
import nwts.ru.autoshop.adapter.interfaces.AdapterClickListener;
import nwts.ru.autoshop.databases.DataManager;
import nwts.ru.autoshop.models.network.orders.BalOrderModel;

import static com.mikepenz.iconics.Iconics.TAG;
import static nwts.ru.autoshop.R.id.txt_cart_name;
import static nwts.ru.autoshop.network.api.Api.GET_IMAGES;

/**
 * Created by пользователь on 24.05.2017.
 */

public class AdapterBalOrder extends RecyclerView.Adapter<AdapterBalOrder.ViewHolder> {

    private Context context;
    private Activity activity;
    private Drawable background;
    private AdapterClickListener mAdapterClickListener;
    List<BalOrderModel> mBalOrderModels;
    DecimalFormat formatData = new DecimalFormat("0.00");
    SimpleDateFormat dateformat = new SimpleDateFormat("dd.MM.yyyy");


    public AdapterBalOrder(List<BalOrderModel> balOrderModels, Activity activity, AdapterClickListener adapterClickListener) {
        this.activity = activity;
        mAdapterClickListener = adapterClickListener;
        mBalOrderModels = balOrderModels;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        final View view;
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_element_cart, parent, false);
        return new ViewHolder(view, mAdapterClickListener);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final BalOrderModel balOrderModel = mBalOrderModels.get(position);
        if (balOrderModel.getCredit() == 0) {
            holder.txtDate.setText(balOrderModel.getMenu_name());
            holder.txtSumma.setText("" + balOrderModel.getTovar_Count());
            holder.txtStatus.setText(formatData.format(balOrderModel.getSummaTovar()));
            //get image
            if (holder.mImageView != null) {
                picassoGetImages(holder, balOrderModel);
            }
        } else {
            if (TextUtils.isEmpty(balOrderModel.getPaySystem())) {
                holder.txtDate.setText(R.string.name_pay_sys);
            } else {
                holder.txtDate.setText(balOrderModel.getPaySystem());
            }
            holder.txtSumma.setText(R.string.count_pay_sys);
            holder.txtStatus.setText(formatData.format(balOrderModel.getSumma()));

            //get image
            if (holder.mImageView != null) {
                String namePaySystemFromStringsValue = activity.getResources().getString(R.string.qiwi);
                if (balOrderModel.getPaySystem().toLowerCase().equals(namePaySystemFromStringsValue.toLowerCase())) {
                    Picasso.with(context).load(R.drawable.qiwi2).into(holder.mImageView);
                }
                 namePaySystemFromStringsValue = activity.getResources().getString(R.string.yandex_money);
                if (balOrderModel.getPaySystem().toLowerCase().equals(namePaySystemFromStringsValue.toLowerCase())) {
                    Picasso.with(context).load(R.drawable.yandeks).into(holder.mImageView);
                }
                 namePaySystemFromStringsValue = activity.getResources().getString(R.string.paypal);
                if (balOrderModel.getPaySystem().toLowerCase().equals(namePaySystemFromStringsValue.toLowerCase())) {
                    Picasso.with(context).load(R.drawable.paypal).into(holder.mImageView);
                }
                 namePaySystemFromStringsValue = activity.getResources().getString(R.string.web_money);
                if (balOrderModel.getPaySystem().toLowerCase().equals(namePaySystemFromStringsValue.toLowerCase())) {
                    Picasso.with(context).load(R.drawable.webmoney).into(holder.mImageView);
                }
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            holder.itemView.setBackground(background);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.mAdapterClickListener.adapterOnClickListener(balOrderModel.getTovar_ID());
            }
        });
    }


    private void picassoGetImages(final ViewHolder holder, final BalOrderModel balOrderModel) {

        DataManager.getInstance().getPicasso()
                .load(GET_IMAGES+balOrderModel.getMenu_image())
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
                                .load(GET_IMAGES+balOrderModel.getMenu_image())
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

    @Override
    public int getItemCount() {
        if (mBalOrderModels == null) {
            return 0;
        }
        return mBalOrderModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtDate, txtSumma, txtStatus;
        AdapterClickListener mAdapterClickListener;
        ImageView mImageView;

        public ViewHolder(View itemView, AdapterClickListener adapterClickListener) {
            super(itemView);
            txtDate = (TextView) itemView.findViewById(txt_cart_name);
            txtSumma = (TextView) itemView.findViewById(R.id.txt_cart_count);
            txtStatus = (TextView) itemView.findViewById(R.id.txt_cart_summa);
            mImageView = (ImageView) itemView.findViewById(R.id.img_view_cart);

            mAdapterClickListener = adapterClickListener;
        }
    }
}
