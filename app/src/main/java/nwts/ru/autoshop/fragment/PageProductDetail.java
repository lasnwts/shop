package nwts.ru.autoshop.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.Random;

import nwts.ru.autoshop.R;
import nwts.ru.autoshop.TODOApplication;
import nwts.ru.autoshop.databases.DataManager;
import nwts.ru.autoshop.models.CategoryItem;
import uk.co.senab.photoview.PhotoView;

import static com.mikepenz.iconics.Iconics.TAG;
import static nwts.ru.autoshop.network.api.Api.GET_IMAGES;

/**
 * Created by пользователь on 20.03.2017.
 */

public class PageProductDetail extends Fragment {

    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";

    int pageNumber;
    int backColor;

   public static PageProductDetail newInstance(int page) {
        PageProductDetail pageFragment = new PageProductDetail();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        pageFragment.setArguments(arguments);
        return pageFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);

        Random rnd = new Random();
        backColor = Color.argb(40, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_product_images, null);

        final PhotoView photoView = (PhotoView) view.findViewById(R.id.iv_photo);

        if (TODOApplication.getUrlProductDetailImages().size()>0
                && TODOApplication.getUrlProductDetailImages().get(pageNumber) !=null ) {

//        Picasso mPicasso = Picasso.with(getContext());
//        mPicasso.setIndicatorsEnabled(true);
//        mPicasso.setLoggingEnabled(true);
//        mPicasso.load(TODOApplication.getUrlProductDetailImages().get(pageNumber))
//                .error(R.drawable.error_fresco404)
//                .into(photoView);
            //
            getPicassoImages(photoView);
            photoView.setVisibility(View.VISIBLE);
        }

        return view;
    }

    private void getPicassoImages(final PhotoView photoView) {
        DataManager.getInstance().getPicasso()
                .load(TODOApplication.getUrlProductDetailImages().get(pageNumber))
                .networkPolicy(NetworkPolicy.OFFLINE)
                .error(R.drawable.error_fresco404)
                .placeholder(R.drawable.error_fresco404)
                .into(photoView, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG, "load from cache");
                    }

                    @Override
                    public void onError() {
                        DataManager.getInstance().getPicasso()
                                .load(TODOApplication.getUrlProductDetailImages().get(pageNumber))
                                .error(R.drawable.error_fresco404)
                                .placeholder(R.drawable.error_fresco404)
                                .into(photoView, new Callback() {
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
