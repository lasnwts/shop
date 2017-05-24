package nwts.ru.autoshop.fragment.cabinet;

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

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import nwts.ru.autoshop.R;
import nwts.ru.autoshop.TODOApplication;
import nwts.ru.autoshop.adapter.cabinet.AdapterBalOrder;
import nwts.ru.autoshop.adapter.interfaces.AdapterClickListener;
import nwts.ru.autoshop.models.network.BalanceModel;
import nwts.ru.autoshop.models.network.cart.CartModel;
import nwts.ru.autoshop.models.network.cart.CartModels;
import nwts.ru.autoshop.models.network.orders.BalOrderModel;
import nwts.ru.autoshop.models.network.orders.BalOrderModels;
import nwts.ru.autoshop.services.ServiceHelper;
import nwts.ru.autoshop.setting.BaseConstant;
import nwts.ru.autoshop.setting.PreferenceHelper;
import nwts.ru.autoshop.setting.ToolBarTitle;

/**
 * Created by пользователь on 24.05.2017.
 */

public class BalOrderFragment extends Fragment {

    private static final String TAG = BaseConstant.TAG_BAL_ORDER_FRAGMENT;
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    private static final int SPAN_COUNT = 1;
    private Activity activity_context;
    private List<BalOrderModel> mBalOrderModels;
    protected RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    ProgressBar prgLoading;
    TextView txtAlert;
    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }
    private isBalOrderFragment mIsBalOrderFragment;
    // create price format
    DecimalFormat formatData = new DecimalFormat("0.00");
    SimpleDateFormat dateformat = new SimpleDateFormat("dd.MM.yyyy");
    //
    private int lastVisibleItem;
    private int totalItemCount = 4;
    private ToolBarTitle toolBarTitle;
    private boolean isActivityCreated = false;

    public BalOrderFragment() {
        //
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity_context = getActivity();
        request();;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cabinet_cart_list, container, false);
        view.setTag(TAG);
        mBalOrderModels = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewCabinet);
        layoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);
        recyclerView.setLayoutManager(layoutManager);
        prgLoading = (ProgressBar) view.findViewById(R.id.prgLoadingCabinet);
        txtAlert = (TextView) view.findViewById(R.id.txtAlertCabinet);
        AdapterBalOrder adapterBalOrder = new AdapterBalOrder(mBalOrderModels, activity_context, new AdapterClickListener() {
            @Override
            public void adapterOnClickListener(int item) {
                if (mIsBalOrderFragment == null) {
                    mIsBalOrderFragment = (isBalOrderFragment) activity_context;
                }
                mIsBalOrderFragment.startBalOrder(item);
            }
        });
        recyclerView.setAdapter(adapterBalOrder);
        return view;
    }

    private void request() {
        Intent intentService = new Intent(getActivity(), ServiceHelper.class);
        intentService.setAction(BaseConstant.ACTION_SERVICE_GET_BALANCE_ID);
        getActivity().startService(intentService);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(TAG, true);
        super.onSaveInstanceState(outState);
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
    public void onEventOrders(BalOrderModels event) {
        prgLoading.setVisibility(View.INVISIBLE);
        if (event.getBalOrderModels().isEmpty() || event.getBalOrderModels().size() < 1) {
            txtAlert.setVisibility(View.VISIBLE);
        } else {
            txtAlert.setVisibility(View.INVISIBLE);
            mBalOrderModels.clear();
            mBalOrderModels.addAll(event.getBalOrderModels());
            recyclerView.getAdapter().notifyDataSetChanged();
            if (isActivityCreated) {
                toolBarTitle = (ToolBarTitle) getActivity();
                if (mBalOrderModels.get(0).getCredit() == 0) {
                    toolBarTitle.BaseActivitySteToolBarTitle(getResources().getString(R.string.name_list_orders));
                } else {
                    toolBarTitle.BaseActivitySteToolBarTitle(getResources().getString(R.string.name_list_add_balance));
                }
            }
        }
    }

    public interface isBalOrderFragment {
        void startBalOrder(int item);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        toolBarTitle = (ToolBarTitle) getActivity();
        toolBarTitle.BaseActivitySteToolBarTitle(getResources().getString(R.string.name_list_orders));
        isActivityCreated = true;
    }
}
