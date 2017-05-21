package nwts.ru.autoshop.adapter.cabinet;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
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

    public AdapterCart(List<CartModel> cartModels, Activity activity, AdapterClickListener adapterClickListener) {
        this.activity = activity;
        mAdapterClickListener = adapterClickListener;
        mCartModels = cartModels;
    }

    @Override
    public AdapterCart.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_element_orders, parent, false);
        return new ViewHolder(view, mAdapterClickListener);
    }

    @Override
    public void onBindViewHolder(final AdapterCart.ViewHolder holder, int position) {
        final CartModel cartModel = mCartModels.get(position);
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
        TextView txtDate,txtSumma,txtStatus;
        AdapterClickListener mAdapterClickListener;

        public ViewHolder(View itemView, AdapterClickListener adapterClickListener) {
            super(itemView);
            txtDate = (TextView) itemView.findViewById(R.id.txtDateOrders);
            txtSumma = (TextView) itemView.findViewById(R.id.txtSummaOrders);
            txtStatus = (TextView) itemView.findViewById(R.id.txtStatusOrders);
            mAdapterClickListener = adapterClickListener;
        }
    }

}
