package nwts.ru.autoshop.adapter.cabinet;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import nwts.ru.autoshop.R;
import nwts.ru.autoshop.adapter.interfaces.AdapterClickListener;
import nwts.ru.autoshop.models.network.cart.CartModel;

import static nwts.ru.autoshop.R.id.txtDateOrders;
import static nwts.ru.autoshop.R.id.txt_cart_name;

/**
 * Created by пользователь on 21.05.2017.
 */

public class AdapterCart extends RecyclerView.Adapter<AdapterCart.ViewHolder> {

    private Context context;
    private Activity activity;
    private Drawable background;
    private AdapterClickListener mAdapterClickListener;
    List<CartModel> mCartModels;
    DecimalFormat formatData = new DecimalFormat("0.00");
    SimpleDateFormat dateformat = new SimpleDateFormat("dd.MM.yyyy");
/*
    //detemine width display
    DisplayMetrics dm;
    //display width in dp
    double density;*/

    public AdapterCart(List<CartModel> cartModels, Activity activity, AdapterClickListener adapterClickListener) {
        this.activity = activity;
        mAdapterClickListener = adapterClickListener;
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
        return new ViewHolder(view, mAdapterClickListener);
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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.mAdapterClickListener.adapterOnClickListener(cartModel.getTovar_ID());
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
        //        TextView divider1, divider2;
        AdapterClickListener mAdapterClickListener;

        public ViewHolder(View itemView, AdapterClickListener adapterClickListener) {
            super(itemView);
            txtDate = (TextView) itemView.findViewById(txt_cart_name);
            txtSumma = (TextView) itemView.findViewById(R.id.txt_cart_count);
            txtStatus = (TextView) itemView.findViewById(R.id.txt_cart_summa);
//            divider1 = (TextView) itemView.findViewById(R.id.cart_diviuder1);
//            divider2 = (TextView) itemView.findViewById(R.id.cart_diviuder2);
//            if (txtDate.getHeight() > divider1.getHeight()){
//                divider1.setHeight(txtDate.getHeight());
//            }
            mAdapterClickListener = adapterClickListener;
        }
    }

}
