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
    SimpleDateFormat dateformat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    private boolean messageMore = false;

    public AdapterComment(List<ProductComment> productComments, Activity activity,
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
        setFullOrShortComment(holder, productComment);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            holder.itemView.setBackground(background);
        }
        holder.txtcDate.setText(" Дата и время сообщения : " + dateformat.format(productComment.getcDate() * 1000l));
        switch (productComment.getRating()) {
            case 0:
                holder.ratingImage.setImageResource(R.drawable.rating0);
                break;
            case 1:
                holder.ratingImage.setImageResource(R.drawable.rating1);
                break;
            case 2:
                holder.ratingImage.setImageResource(R.drawable.rating2);
                break;
            case 3:
                holder.ratingImage.setImageResource(R.drawable.rating3);
                break;
            case 4:
                holder.ratingImage.setImageResource(R.drawable.rating4);
                break;
            case 5:
                holder.ratingImage.setImageResource(R.drawable.ratingbase);
                break;
            default:
                holder.ratingImage.setImageResource(R.drawable.rating0);
                break;
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (messageMore) {
                    messageMore = false;
                } else {
                    messageMore = true;
                }
                setFullOrShortComment(holder, productComment);
                holder.mAdapterClickListener.adapterOnClickListener(productComment.getProduct_ID());
            }
        });
    }

    private void setFullOrShortComment(ViewHolder holder, ProductComment productComment) {
        if (productComment.getMessage() == null || TextUtils.isEmpty(productComment.getMessage())) {
            holder.txtComment.setText(R.string.comment_without_message);
        } else {
            if (productComment.getMessage().toString().length() > 85) {
                if (messageMore) {
                    holder.txtComment.setText(productComment.getMessage());
                    holder.txtMore.setText(R.string.show_min_comments);
                } else {
                    holder.txtComment.setText(productComment.getMessage().substring(0, 85));
                    holder.txtMore.setText(R.string.show_max_comments);
                }
            } else {
                holder.txtComment.setText(productComment.getMessage());
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mProductComments == null) {
            return 0;
        }
        return mProductComments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtComment, txtcDate, txtMore;
        ImageView ratingImage;
        AdapterClickListener mAdapterClickListener;

        public ViewHolder(View itemView, AdapterClickListener adapterClickListener) {
            super(itemView);
            txtComment = (TextView) itemView.findViewById(R.id.commnet_item_rating_txtview);
            txtcDate = (TextView) itemView.findViewById(R.id.commnet_item_rating_cdate);
            txtMore = (TextView) itemView.findViewById(R.id.commnet_item_rating_txtьщку);
            ratingImage = (ImageView) itemView.findViewById(R.id.commnet_item_rating_image);
            mAdapterClickListener = adapterClickListener;
        }
    }


}
