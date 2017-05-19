package nwts.ru.autoshop.fragment.cabinet;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import nwts.ru.autoshop.R;
import nwts.ru.autoshop.TODOApplication;
import nwts.ru.autoshop.adapter.cabinet.AdapterOrders;
import nwts.ru.autoshop.adapter.interfaces.AdapterClickListener;
import nwts.ru.autoshop.adapter.interfaces.OnLoadMoreListener;
import nwts.ru.autoshop.models.network.OrderModel;
import nwts.ru.autoshop.models.network.OrderModels;
import nwts.ru.autoshop.services.ServiceHelper;
import nwts.ru.autoshop.setting.BaseConstant;
import nwts.ru.autoshop.setting.PreferenceHelper;
import nwts.ru.autoshop.ui.CabinetBase;

/**
 * Created by пользователь on 17.05.2017.
 */

public class OrdersFragment extends Fragment {

    private static final String TAG = BaseConstant.TAG_ORDERS_FRAGMENT;
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    private static final int SPAN_COUNT = 1;
    private Activity activity_context;
    private List<OrderModel> mOrderModelList;
    protected RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    ProgressBar prgLoading;
    TextView txtAlert;
    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }
    private isOrdersFragment mIsOrdersFragment;
    // create price format
    DecimalFormat formatData = new DecimalFormat("0.00");
    SimpleDateFormat dateformat = new SimpleDateFormat("dd.MM.yyyy");
    //
    private int lastVisibleItem;
    private int totalItemCount = 4;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity_context = getActivity();
        request();;
    }

    public OrdersFragment() {
        //
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cabinet_list, container, false);
        view.setTag(TAG);
        mOrderModelList = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewCabinet);
        layoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);
        recyclerView.setLayoutManager(layoutManager);
        prgLoading = (ProgressBar) view.findViewById(R.id.prgLoadingCabinet);
        txtAlert = (TextView) view.findViewById(R.id.txtAlertCabinet);
        AdapterOrders adapterOrders = new AdapterOrders(mOrderModelList, activity_context, new AdapterClickListener() {
            @Override
            public void adapterOnClickListener(int item) {
                //item click...
                if (mIsOrdersFragment == null) {
                    mIsOrdersFragment = (isOrdersFragment) activity_context;
                }
                mIsOrdersFragment.startOrder(item);
            }
        });
        recyclerView.setAdapter(adapterOrders);
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //
                //totalItemCount = linearLayoutManager.getItemCount();
                //lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                lastVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
                if (mIsOrdersFragment == null) {
                    mIsOrdersFragment = (isOrdersFragment) activity_context;
                }
                if (lastVisibleItem > totalItemCount ) {
                    mIsOrdersFragment.fabCommand(0);
                } else {
                    mIsOrdersFragment.fabCommand(1);
                }
            }
        });
        return view;
    }

    private void request(){
        Intent intentService = new Intent(getActivity(), ServiceHelper.class);
        intentService.setAction(BaseConstant.ACTION_SERVICE_GET_ORDERS);
        intentService.putExtra(BaseConstant.API_GET_KEY, PreferenceHelper.getInstance().getUserId());
        getActivity().startService(intentService);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventOrders(OrderModels event) {
        prgLoading.setVisibility(View.INVISIBLE);
        if (event.getOrderModels().isEmpty() || event.getOrderModels().size() < 1){
            txtAlert.setVisibility(View.VISIBLE);
        } else {
            txtAlert.setVisibility(View.INVISIBLE);
            mOrderModelList.clear();
            mOrderModelList.addAll(event.getOrderModels());
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(TAG, true);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public interface isOrdersFragment {
        void startOrder(int item);
        void fabCommand(int fabItem);
    }

    public void movedRecyclerViewOnTop(){
        if (recyclerView != null && recyclerView.getLayoutManager().canScrollVertically() && recyclerView.getLayoutManager().getItemCount() > 0) {
            recyclerView.smoothScrollToPosition(0);
            // recyclerView.getLayoutManager().scrollToPosition(0);
        }
    }
}
