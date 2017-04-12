package nwts.ru.autoshop.fragment;

import android.app.Activity;
import android.app.Application;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

import nwts.ru.autoshop.R;
import nwts.ru.autoshop.TODOApplication;
import nwts.ru.autoshop.adapter.AdapaterGridView;
import nwts.ru.autoshop.models.GridViewItem;
import nwts.ru.autoshop.setting.BaseConstant;
import nwts.ru.autoshop.setting.ToolBarTitle;

import static android.R.attr.data;
import static android.R.attr.id;

/**
 * Фрагмент для показа основного меню магазина
 * Created by пользователь on 15.03.2017.
 */

public class HomeMenu extends Fragment implements AdapterView.OnItemClickListener {

    GridView gridview;
    AdapaterGridView gridviewAdapter;
    ArrayList<GridViewItem> data = new ArrayList<GridViewItem>();
    private OnLinkItemSelectedListener mListener;
    private ToolBarTitle toolBarTitle;

    public interface OnLinkItemSelectedListener{
        public void omItemSelectedMenu(int position);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        gridview = (GridView) view.findViewById(R.id.gridView1);
        gridview.setOnItemClickListener(this);

        Context context = container.getContext();

        data.add(new GridViewItem(getResources().getString(R.string.menu_product), getDrawableVersion(R.drawable.ic_product,context)));
        data.add(new GridViewItem(getResources().getString(R.string.menu_cart),  getDrawableVersion(R.drawable.ic_cart,context)));
        data.add(new GridViewItem(getResources().getString(R.string.menu_checkout), getDrawableVersion(R.drawable.ic_checkout,context)));
        data.add(new GridViewItem(getResources().getString(R.string.menu_profile),  getDrawableVersion(R.drawable.ic_profile,context)));

        setDataAdapter(context);
        return view;
    }

    //Deprecated getResources().getDrawable
    private Drawable getDrawableVersion(int id, Context context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return getResources().getDrawable(id, context.getTheme());
        } else {
            return getResources().getDrawable(id);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        toolBarTitle = (ToolBarTitle)getActivity();
        toolBarTitle.BaseActivitySteToolBarTitle(getResources().getString(R.string.toolbar_main_menu));
    }

    // Set the Data Adapter
    private void setDataAdapter(Context context) {
        gridviewAdapter = new AdapaterGridView(getActivity(), R.layout.fragment_list_item, data);
        gridview.setAdapter(gridviewAdapter);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnLinkItemSelectedListener){
            mListener = (OnLinkItemSelectedListener) activity;
        } else {
            throw new ClassCastException((activity.toString()));
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                Log.d(BaseConstant.TAG,"onItemClick:position=="+position);
                if (mListener != null){
                    mListener.omItemSelectedMenu(position);
                }
                break;
            case 1:
                Log.d(BaseConstant.TAG,"onItemClick:position=="+position);
                mListener.omItemSelectedMenu(position);
                break;
            case 2:
                Log.d(BaseConstant.TAG,"onItemClick:position=="+position);
                mListener.omItemSelectedMenu(position);
                break;
            case 4:
                Log.d(BaseConstant.TAG,"onItemClick:position=="+position);
                mListener.omItemSelectedMenu(position);
                break;
            default:
                Log.d(BaseConstant.TAG,"onItemClick:position=="+position);
                mListener.omItemSelectedMenu(position);
                break;
        }
    }
}
