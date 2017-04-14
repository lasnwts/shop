package nwts.ru.autoshop.fragment;


import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import nwts.ru.autoshop.adapter.AdapterCategoryList;
import nwts.ru.autoshop.adapter.interfaces.AdapterClickListener;
import nwts.ru.autoshop.models.CategoryItem;
import nwts.ru.autoshop.models.CategoryItems;
import nwts.ru.autoshop.services.ServiceHelper;
import nwts.ru.autoshop.setting.BaseConstant;
import nwts.ru.autoshop.setting.ToolBarTitle;

/**
 *  Фрагмент показа всех категорий магазина (1-ый уровень)
 *  Сущесвует еше второй подуровень категорий  (2-ой уровень)
 *   и потом уже список товаров (3-ий уровень)
 *
 */
public class Category extends Fragment {

    private static final String TAG = BaseConstant.TAG_CATEGORY_FRAGMENT;
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    private static final int SPAN_COUNT = 2;
    private static final int DATASET_COUNT = 60;
    private iCategoty mICategoty;

    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }

    protected LayoutManagerType mCurrentLayoutManagerType;
    ProgressBar prgLoading;
    TextView txtAlert;
    List<CategoryItem> categoryItems;
    RecyclerView recyclerView;
    protected RecyclerView.LayoutManager layoutManager;
    private Activity activity_context;
    private ToolBarTitle toolBarTitle;

    AdapterCategoryList adapterCategoryList;

    public Category() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //init dataset
        activity_context = getActivity();
        getRequest();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        toolBarTitle = (ToolBarTitle) getActivity();
        toolBarTitle.BaseActivitySteToolBarTitle(getResources().getString(R.string.toolbar_main_category));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.category_list, container, false);
        view.setTag(TAG);
        categoryItems = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewCategory);
        layoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);

        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;

        recyclerView.setLayoutManager(layoutManager);
        AdapterCategoryList adapter = new AdapterCategoryList(categoryItems, activity_context, new AdapterClickListener() {
            @Override
            public void adapterOnClickListener(int item) {
                mICategoty = (iCategoty) activity_context;
                mICategoty.startSubCategory(item);
            }
        });
        recyclerView.setAdapter(adapter);

        prgLoading = (ProgressBar) view.findViewById(R.id.prgLoading);
        txtAlert = (TextView) view.findViewById(R.id.txtAlert);
        return view;
    }

    //Функция запроса данных
    private void getRequest() {
        //Передаем в ServiceHelper апрос на данные
        Intent intentService = new Intent(getActivity(), ServiceHelper.class);
        intentService.setAction(BaseConstant.ACTION_SERVICE_GET_CATEGORY_LIST);
        getActivity().startService(intentService);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventCategoryList(CategoryItems event) {
        Log.d(BaseConstant.TAG, "EventBus get! Subscribe :getErrors:result=" + event.getErrors());
        categoryItems.addAll(event.getCategoryItems());
        prgLoading.setVisibility(View.INVISIBLE);
        if (event.getCategoryItems().isEmpty() || event.getCategoryItems().size() < 1) {
            txtAlert.setVisibility(View.VISIBLE);
        } else {
            txtAlert.setVisibility(View.INVISIBLE);
            recyclerView.getAdapter().notifyDataSetChanged();
        }

/*        for (int i = 0; i < categoryItems.size(); i++) {
            Log.d(BaseConstant.TAG, "Category:onEventCategoryList: "
                    + categoryItems.get(i).getCategory_ID() + " "
                    + categoryItems.get(i).getCategory_name() + " "
                    + categoryItems.get(i).getCategory_image()
            );
        }
        Log.d(BaseConstant.TAG, "EventBus get! Subscribe  CategoryItems:" + categoryItems.toString());
        Log.d(BaseConstant.TAG, "EventBus get! Subscribe  CategoryItems:categoryItems.size():" + categoryItems.size());
        Log.d(BaseConstant.TAG, "EventBus get! Subscribe  CategoryItems:getAdapter().getItemCount():" + recyclerView.getAdapter().getItemCount());
        viewCategory();*/
    }

    private void viewCategory() {
        Log.d(BaseConstant.TAG, "EventBus get! Subscribe  : recyclerView:getItemCount=" + recyclerView.getAdapter().getItemCount());
        prgLoading.setVisibility(View.INVISIBLE);
        recyclerView.getAdapter().notifyDataSetChanged();
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


    public interface iCategoty {
        void startSubCategory(int item);
    }
}
