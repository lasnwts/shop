package nwts.ru.autoshop.fragment.cabinet;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import nwts.ru.autoshop.adapter.cabinet.AdapterBalance;
import nwts.ru.autoshop.adapter.interfaces.AdapterClickListener;
import nwts.ru.autoshop.models.network.BalanceModel;
import nwts.ru.autoshop.models.network.BalanceModels;
import nwts.ru.autoshop.models.network.OrderModels;
import nwts.ru.autoshop.services.ServiceHelper;
import nwts.ru.autoshop.setting.BaseConstant;
import nwts.ru.autoshop.setting.PreferenceHelper;
import nwts.ru.autoshop.setting.ToolBarTitle;

/**
 * Created by пользователь on 20.05.2017.
 */

public class BalanceFragment extends Fragment {

    private static final String TAG = BaseConstant.TAG_BALANCE_FRAGMENT;
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    private static final int SPAN_COUNT = 1;
    private Activity activity_context;
    private List<BalanceModel> mBalanceModelList;
    protected RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    ProgressBar prgLoading;
    TextView txtAlert;
    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }
    private isBalanceFragment mIsBalanceFragment;
    // create price format
    DecimalFormat formatData = new DecimalFormat("0.00");
    SimpleDateFormat dateformat = new SimpleDateFormat("dd.MM.yyyy");
    //
    private int lastVisibleItem;
    private int totalItemCount = 4;
    private ToolBarTitle toolBarTitle;

    public BalanceFragment() {
        //
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity_context = getActivity();
        request();;

    }

    private void request() {
        Intent intentService = new Intent(getActivity(), ServiceHelper.class);
        intentService.setAction(BaseConstant.ACTION_SERVICE_GET_BALANCE);
        intentService.putExtra(BaseConstant.API_GET_KEY, PreferenceHelper.getInstance().getUserId());
        getActivity().startService(intentService);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cabinet_list, container, false);
        view.setTag(TAG);
        mBalanceModelList = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewCabinet);
        layoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);
        recyclerView.setLayoutManager(layoutManager);
        prgLoading = (ProgressBar) view.findViewById(R.id.prgLoadingCabinet);
        txtAlert = (TextView) view.findViewById(R.id.txtAlertCabinet);
        AdapterBalance adapterBalance = new AdapterBalance( mBalanceModelList,
                activity_context, new AdapterClickListener() {
            @Override
            public void adapterOnClickListener(int item) {
                if (mIsBalanceFragment == null) {
                    mIsBalanceFragment = (isBalanceFragment) activity_context;
                }

                mIsBalanceFragment.startBalance(item);
            }
        });
        recyclerView.setAdapter(adapterBalance);
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        //
        if (mIsBalanceFragment == null) {
            mIsBalanceFragment = (isBalanceFragment) activity_context;
        }
        mIsBalanceFragment.fabCommandBalance(0);
        //
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //
                //totalItemCount = linearLayoutManager.getItemCount();
                //lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                lastVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
                if (mIsBalanceFragment == null) {
                    mIsBalanceFragment = (isBalanceFragment) activity_context;
                }
                if (lastVisibleItem > totalItemCount ) {
                    mIsBalanceFragment.fabCommandBalance(0);
                } else {
                    mIsBalanceFragment.fabCommandBalance(1);
                }
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        toolBarTitle = (ToolBarTitle) getActivity();
        toolBarTitle.BaseActivitySteToolBarTitle(getResources().getString(R.string.bottom_nav_text_balance));
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


    public interface isBalanceFragment {
        void startBalance(int item);
        void fabCommandBalance(int fabItem);
    }


    public void movedRecyclerViewOnTop(){
        if (recyclerView != null && recyclerView.getLayoutManager().canScrollVertically() && recyclerView.getLayoutManager().getItemCount() > 0) {
            recyclerView.smoothScrollToPosition(0);
            // recyclerView.getLayoutManager().scrollToPosition(0);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventOrders(BalanceModels event) {
        prgLoading.setVisibility(View.INVISIBLE);
        if (event.getBalanceModels().isEmpty() || event.getBalanceModels().size() < 1){
            txtAlert.setVisibility(View.VISIBLE);
        } else {
            txtAlert.setVisibility(View.INVISIBLE);
            mBalanceModelList.clear();
            mBalanceModelList.addAll(event.getBalanceModels());
            recyclerView.getAdapter().notifyDataSetChanged();
        }
    }


}
