package nwts.ru.autoshop.fragment;


import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import nwts.ru.autoshop.R;
import nwts.ru.autoshop.TODOApplication;
import nwts.ru.autoshop.adapter.AdapterProductCatalog;
import nwts.ru.autoshop.adapter.interfaces.AdapterClickListener;
import nwts.ru.autoshop.models.ProductCategoris;
import nwts.ru.autoshop.models.ProductCategory;
import nwts.ru.autoshop.services.ServiceHelper;
import nwts.ru.autoshop.setting.BaseConstant;
import nwts.ru.autoshop.setting.ToolBarTitle;

/**
 *  Фрагмент показа списка товаров (3-ий уровень)
 * Created by пользователь on 22.03.2017.
 */

public class ProductCatalog extends Fragment {

    private static final String TAG = BaseConstant.TAG_PRODUCT_CATALOG_FRAGMENT;
    private int saved_Category_id;
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    protected RecyclerView mRecyclerView;
    ProgressBar prgLoading;
    TextView txtAlert;
    List<ProductCategory> productCategoryList;
    RecyclerView recyclerView;
    private static final int SPAN_COUNT = 1;
    protected RecyclerView.LayoutManager layoutManager;
    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }
    protected LayoutManagerType mCurrentLayoutManagerType;
    private Activity activity_context;
    private ToolBarTitle toolBarTitle;
    private iProductCatalog mIProductCatalog;


    public ProductCatalog() {
        //
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity_context = getActivity();
        request();
//        if (savedInstanceState != null) {
//            if (savedInstanceState.getBoolean(TAG)) {
//                request();;
//            }
//        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        toolBarTitle = (ToolBarTitle)getActivity();
        toolBarTitle.BaseActivitySteToolBarTitle(getResources().getString(R.string.toolbar_main_product_catalog));
    }

    private void request(){
        Intent intentService = new Intent(getActivity(), ServiceHelper.class);
        intentService.setAction(BaseConstant.ACTION_SERVICE_GET_PRODUCT_LIST);
        intentService.putExtra(BaseConstant.API_GET_KEY, TODOApplication.getCategory_Id());
        getActivity().startService(intentService);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(TAG, true);
        super.onSaveInstanceState(outState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.product_catalog, container, false);
        view.setTag(TAG);
        productCategoryList = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewCategoryProdCatalog);
        layoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);
        recyclerView.setLayoutManager(layoutManager);
        AdapterProductCatalog adapter = new AdapterProductCatalog(productCategoryList, activity_context, new AdapterClickListener() {
            @Override
            public void adapterOnClickListener(int item) {
                mIProductCatalog = (iProductCatalog) activity_context;
                mIProductCatalog.startProductDetailView(item);
            }
        });
        prgLoading = (ProgressBar) view.findViewById(R.id.prgLoadingProdCatalog);
        txtAlert = (TextView) view.findViewById(R.id.txtAlertProdCatalog);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventCategoryList(ProductCategoris event) {
        productCategoryList.addAll(event.getProductCategories());
        prgLoading.setVisibility(View.INVISIBLE);
        if (event.getProductCategories().isEmpty() || event.getProductCategories().size() < 1) {
            txtAlert.setVisibility(View.VISIBLE);
        } else {
            txtAlert.setVisibility(View.INVISIBLE);
            recyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    public interface iProductCatalog{
        void startProductDetailView(int item);
    }
}
