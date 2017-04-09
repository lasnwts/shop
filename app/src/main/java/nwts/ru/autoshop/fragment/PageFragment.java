package nwts.ru.autoshop.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

import nwts.ru.autoshop.R;

/**
 * Created by пользователь on 20.03.2017.
 */

public class PageFragment extends Fragment {

    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";

    int pageNumber;
    int backColor;

   public static PageFragment newInstance(int page) {
        PageFragment pageFragment = new PageFragment();
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
        View view = inflater.inflate(R.layout.information_tab_item, null);

        TextView tvPage = (TextView) view.findViewById(R.id.textViewtInformation);
        ImageView tvImage = (ImageView) view.findViewById(R.id.imageButtonInformation);
        //tvPage.setText("Page " + pageNumber);
        switch (pageNumber){
            case 0:
                tvImage.setImageDrawable(getResources().getDrawable(R.drawable.shop_avto_about));
                tvImage.setVisibility(View.VISIBLE);
                tvPage.setText("Page " + getResources().getString(R.string.information_tab1));
                break;
            case 1:
                tvImage.setImageDrawable(getResources().getDrawable(R.drawable.shop_avto_about2));
                tvImage.setVisibility(View.VISIBLE);
                tvPage.setText("Page " + getResources().getString(R.string.information_tab2));
                break;
            case 2:
                tvImage.setImageDrawable(getResources().getDrawable(R.drawable.asimo));
                tvImage.setVisibility(View.VISIBLE);
                tvPage.setText("Page " + getResources().getString(R.string.information_tab3));
                break;
            default:
                break;
        }
        tvPage.setText("Page " + getResources().getString(R.string.information_tab1));
        tvPage.setBackgroundColor(backColor);

        return view;
    }
}
