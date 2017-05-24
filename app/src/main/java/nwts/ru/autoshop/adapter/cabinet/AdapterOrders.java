package nwts.ru.autoshop.adapter.cabinet;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
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
import nwts.ru.autoshop.models.network.OrderModel;

/**
 * Created by пользователь on 18.05.2017.
 */

public class AdapterOrders  extends RecyclerView.Adapter<AdapterOrders.ViewHolder> {

    private Context context;
    private Activity activity;
    private Drawable background;
    private List<OrderModel> mOrderModelList;
    private AdapterClickListener mAdapterClickListener;
    //final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
    // create price format
    DecimalFormat formatData = new DecimalFormat("0.00");
    SimpleDateFormat dateformat = new SimpleDateFormat("dd.MM.yyyy");


    public AdapterOrders(List<OrderModel> orderModelList, Activity activity,
                         AdapterClickListener adapterClickListener) {
        this.activity = activity;
        mOrderModelList = orderModelList;
        mAdapterClickListener = adapterClickListener;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_element_orders, parent, false);
        return new ViewHolder(view, mAdapterClickListener);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final OrderModel mOrderModel = mOrderModelList.get(position);
        holder.txtDate.setText(dateformat.format(mOrderModel.getDateOperation() * 1000l));
        holder.txtSumma.setText(formatData.format(mOrderModel.getSumma()));
        holder.txtStatus.setText(mOrderModel.getStatusName());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            holder.itemView.setBackground(background);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.mAdapterClickListener.adapterOnClickListener(mOrderModel.getOper_ID());
            }
        });

    }

    @Override
    public int getItemCount() {
        if (mOrderModelList == null) {
            return 0;
        }
        return mOrderModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtDate,txtSumma,txtStatus;
        AdapterClickListener mAdapterClickListener;

        public ViewHolder(View itemView,  AdapterClickListener adapterClickListener) {
            super(itemView);
            txtDate = (TextView) itemView.findViewById(R.id.txtDateOrders);
            txtSumma = (TextView) itemView.findViewById(R.id.txtSummaOrders);
            txtStatus = (TextView) itemView.findViewById(R.id.txtStatusOrders);
            mAdapterClickListener = adapterClickListener;
        }
    }
}
