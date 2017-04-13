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
import nwts.ru.autoshop.adapter.AdapterSubCategory;
import nwts.ru.autoshop.adapter.interfaces.AdapterClickListener;
import nwts.ru.autoshop.models.SubCategoryItem;
import nwts.ru.autoshop.models.SubCategoryItems;
import nwts.ru.autoshop.services.ServiceHelper;
import nwts.ru.autoshop.setting.BaseConstant;
import nwts.ru.autoshop.setting.ToolBarTitle;

/** Фрагмент полказа подкатегорий (2-ой урорвень)
 * Created by пользователь on 30.03.2017.
 */

public class SubCatalog extends Fragment {

    ProgressBar prgLoading;
    private static final String TAG = BaseConstant.TAG_SUBCATEGORY_FRAGMENT;
    protected RecyclerView recyclerView;
    private Activity activity_context;
    List<SubCategoryItem> subCategoryItems;
    TextView txtAlert;
    private static final int SPAN_COUNT = 1;
    protected RecyclerView.LayoutManager layoutManager;
    private ToolBarTitle toolBarTitle;
    private iSubCatalog mISubCatalog;


    public SubCatalog() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity_context = getActivity();
        getRequest();;
//        if (savedInstanceState != null) {
//            if (savedInstanceState.getBoolean(TAG)) {
//                getRequest();;
//            }
//        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        toolBarTitle = (ToolBarTitle)getActivity();
        toolBarTitle.BaseActivitySteToolBarTitle(getResources().getString(R.string.toolbar_main_subcategory));
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
        subCategoryItems = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewCategoryProdCatalog);
        layoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);
        recyclerView.setLayoutManager(layoutManager);
        prgLoading = (ProgressBar) view.findViewById(R.id.prgLoadingProdCatalog);
        txtAlert = (TextView) view.findViewById(R.id.txtAlertProdCatalog);
        AdapterSubCategory adapterSubCategory = new AdapterSubCategory(subCategoryItems, activity_context, new AdapterClickListener() {
            @Override
            public void adapterOnClickListener(int item) {
                mISubCatalog = (iSubCatalog) activity_context;
                mISubCatalog.startProductCatalog(item);
            }
        });
        recyclerView.setAdapter(adapterSubCategory);
        return view;
    }


    private void getRequest(){
        Intent intentService = new Intent(getActivity(), ServiceHelper.class);
        intentService.setAction(BaseConstant.ACTION_SERVICE_GET_SUBCATEGORY_LIST);
        intentService.putExtra(BaseConstant.API_GET_KEY, TODOApplication.getSubCategory_Id());
        getActivity().startService(intentService);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventSubCategoryList(SubCategoryItems event) {
        subCategoryItems.addAll(event.getSubCategoryItems());
        prgLoading.setVisibility(View.INVISIBLE);
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    public interface iSubCatalog{
        void startProductCatalog(int item);
    }
}
