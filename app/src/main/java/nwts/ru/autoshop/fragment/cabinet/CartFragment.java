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
import nwts.ru.autoshop.adapter.cabinet.AdapterCart;
import nwts.ru.autoshop.adapter.interfaces.AdapterClickListener;
import nwts.ru.autoshop.models.network.BalanceModel;
import nwts.ru.autoshop.models.network.BalanceModels;
import nwts.ru.autoshop.models.network.CabinetModels;
import nwts.ru.autoshop.models.network.cart.CartModel;
import nwts.ru.autoshop.models.network.cart.CartModels;
import nwts.ru.autoshop.services.ServiceHelper;
import nwts.ru.autoshop.setting.BaseConstant;
import nwts.ru.autoshop.setting.PreferenceHelper;

/**
 * Created by пользователь on 21.05.2017.
 */

public class CartFragment extends Fragment {

    private static final String TAG = BaseConstant.TAG_CART_FRAGMENT;
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
    private static final int SPAN_COUNT = 1;
    private Activity activity_context;
    private List<CartModel> mCartModels;
    protected RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    ProgressBar prgLoading;
    TextView txtAlert;
    private enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }
    private isCartFragment mIsCartFragment;
    // create price format
    DecimalFormat formatData = new DecimalFormat("0.00");
    SimpleDateFormat dateformat = new SimpleDateFormat("dd.MM.yyyy");
    //
    private int lastVisibleItem;
    private int totalItemCount = 4;

    public CartFragment() {
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
        View view = inflater.inflate(R.layout.cabinet_list, container, false);
        view.setTag(TAG);
        mCartModels = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewCabinet);
        layoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);
        recyclerView.setLayoutManager(layoutManager);
        prgLoading = (ProgressBar) view.findViewById(R.id.prgLoadingCabinet);
        txtAlert = (TextView) view.findViewById(R.id.txtAlertCabinet);
        AdapterCart adapterCart = new AdapterCart(mCartModels, activity_context,
                new AdapterClickListener() {
                    @Override
                    public void adapterOnClickListener(int item) {
                        if (mIsCartFragment == null) {
                            mIsCartFragment = (isCartFragment) activity_context;
                        }
                        mIsCartFragment.startCart(item);
                    }
                });
        recyclerView.setAdapter(adapterCart);
        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        //
        if (mIsCartFragment == null) {
            mIsCartFragment = (isCartFragment) activity_context;
        }
        mIsCartFragment.fabCommandCart(0);
        //
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //
                //totalItemCount = linearLayoutManager.getItemCount();
                //lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                lastVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
                if (mIsCartFragment == null) {
                    mIsCartFragment = (isCartFragment) activity_context;
                }
                if (lastVisibleItem > totalItemCount ) {
                    mIsCartFragment.fabCommandCart(0);
                } else {
                    mIsCartFragment.fabCommandCart(1);
                }
            }
        });
        return view;
    }

    private void request() {
        Intent intentService = new Intent(getActivity(), ServiceHelper.class);
        intentService.setAction(BaseConstant.ACTION_SERVICE_GET_CART);
        intentService.putExtra(BaseConstant.API_GET_KEY, PreferenceHelper.getInstance().getUserId());
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


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(TAG, true);
        super.onSaveInstanceState(outState);
    }

    public interface isCartFragment {
        void startCart(int item);
        void fabCommandCart(int fabItem);
    }

    public void movedRecyclerViewOnTop(){
        if (recyclerView != null && recyclerView.getLayoutManager().canScrollVertically() && recyclerView.getLayoutManager().getItemCount() > 0) {
            recyclerView.smoothScrollToPosition(0);
            // recyclerView.getLayoutManager().scrollToPosition(0);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventOrders(CartModels event) {
        prgLoading.setVisibility(View.INVISIBLE);
        if (event.getCartModels().isEmpty() || event.getCartModels().size() < 1){
            txtAlert.setVisibility(View.VISIBLE);
        } else {
            txtAlert.setVisibility(View.INVISIBLE);
            mCartModels.clear();
            mCartModels.addAll(event.getCartModels());
            recyclerView.getAdapter().notifyDataSetChanged();
        }
    }

}
