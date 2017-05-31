package nwts.ru.autoshop.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import nwts.ru.autoshop.R;
import nwts.ru.autoshop.adapter.interfaces.AdapterClickListener;
import nwts.ru.autoshop.models.network.ProductComment;

/**
 * Created by пользователь on 31.05.2017.
 */

public class AdapterComment extends RecyclerView.Adapter<AdapterComment.ViewHolder> {

    private Context context;
    private Activity activity;
    private Drawable background;
    private AdapterClickListener mAdapterClickListener;
    List<ProductComment> mProductComments;
    SimpleDateFormat dateformat = new SimpleDateFormat("dd.MM.yyyy");

    public AdapterComment( List<ProductComment> productComments, Activity activity,
                           AdapterClickListener adapterClickListener) {
        this.activity = activity;
        mAdapterClickListener = adapterClickListener;
        mProductComments = productComments;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_item, parent, false);
        return new ViewHolder(view, mAdapterClickListener);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final ProductComment productComment = mProductComments.get(position);
        //holder.ratingImage =
        if (productComment.getMessage() == null || TextUtils.isEmpty(productComment.getMessage())){
            holder.txtComment.setText(R.string.comment_without_message);
        } else {
            holder.txtComment.setText(productComment.getMessage());
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            holder.itemView.setBackground(background);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.mAdapterClickListener.adapterOnClickListener(productComment.getProduct_ID());
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mProductComments == null) {
            return 0;
        }
        return mProductComments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtComment;
        ImageView ratingImage;
        AdapterClickListener mAdapterClickListener;

        public ViewHolder(View itemView, AdapterClickListener adapterClickListener) {
            super(itemView);
            txtComment = (TextView) itemView.findViewById(R.id.commnet_item_rating_txtview);
            ratingImage = (ImageView) itemView.findViewById(R.id.commnet_item_rating_image);
            mAdapterClickListener = adapterClickListener;
        }
    }
}
