package nwts.ru.autoshop.ui;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.app.FragmentManager;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import nwts.ru.autoshop.R;
import nwts.ru.autoshop.fragment.cabinet.OrdersFragment;
import nwts.ru.autoshop.models.network.CabinetModel;
import nwts.ru.autoshop.models.network.CabinetModels;
import nwts.ru.autoshop.services.ServiceHelper;
import nwts.ru.autoshop.setting.BaseConstant;
import nwts.ru.autoshop.setting.PreferenceHelper;

public class CabinetBase extends AppCompatActivity implements OrdersFragment.isOrdersFragment {

    private Toolbar toolbar;
    PreferenceHelper preferenceHelper;
    private Drawer drawer = null;
    private String[] menu_items_Drawer;
    private int selectedDrawerItem = 0;
    private List<CabinetModel> mCabinetModels;
    private FloatingActionButton fab;
    private TextView mTextView, mTextViewNameFragment;
    private TextView mTextViewCart, mTextViewSumma;
    private Fragment fragment;
    // create price format
    DecimalFormat formatData = new DecimalFormat("0.00");
    SimpleDateFormat dateformat = new SimpleDateFormat("dd.MM.yyyy' 'HH:mm:ss");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cabinet);
        mCabinetModels = new ArrayList<>();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        PreferenceHelper.getInstance().init(getApplicationContext());
        preferenceHelper = PreferenceHelper.getInstance();
        if (null != toolbar) {
            toolbar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.primary_color)));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle(getResources().getString(R.string.product_detail_name));
            toolbar.inflateMenu(R.menu.menu_base);
        }

        getMenuDriwer(savedInstanceState, toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fabCabinet);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action,", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mTextView = (TextView) findViewById(R.id.name_cabinet);
        mTextViewCart = (TextView) findViewById(R.id.cart_content_cabinet);
        mTextViewSumma = (TextView) findViewById(R.id.summa_cart_cabinet);
        mTextViewNameFragment = (TextView) findViewById(R.id.name_fragment_cabinet);

        BottomNavigationView bnv = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bnv.setOnNavigationItemSelectedListener(getBottomNavigationListener());
        Fragment fragment = getFragmentManager().findFragmentById(R.id.content_frame_cabinet);
        //String tag = (String) fragment.getTag();
        getOrders();
    }

    private void getOrders(){
        OrdersFragment ordersFragment = new OrdersFragment();
        if ( getFragmentManager().getBackStackEntryCount() != 0) {
            getFragmentManager().popBackStack();
        }
        getFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .replace(R.id.content_frame_cabinet,ordersFragment,BaseConstant.TAG_ORDERS_FRAGMENT).commit();
    }

    @NonNull
    private BottomNavigationView.OnNavigationItemSelectedListener getBottomNavigationListener() {
        return new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_orders:
                        mTextViewNameFragment.setText(R.string.name_liost_orders);
//                        textFavorites.setVisibility(View.VISIBLE);
//                        textCollection.setVisibility(View.GONE);
//                        textFriends.setVisibility(View.GONE);
                        break;

                    case R.id.action_balance:
/*                        textFavorites.setVisibility(View.GONE);
                        textCollection.setVisibility(View.VISIBLE);
                        textFriends.setVisibility(View.GONE);*/
                        break;

                    case R.id.action_cart:
//                        textFavorites.setVisibility(View.GONE);
//                        textCollection.setVisibility(View.GONE);
//                        textFriends.setVisibility(View.VISIBLE);
                        break;
                }
                return true;
            }
        };
    }

    private void getMenuDriwer(Bundle savedInstanceState, Toolbar toolbar) {
        menu_items_Drawer = getResources().getStringArray(R.array.menu_cabinet_items);
        PrimaryDrawerItem[] primaryDrawerItems = new PrimaryDrawerItem[menu_items_Drawer.length];

        int[] drawableId = new int[]{R.drawable.ic_view_list_white_18dp,
                R.drawable.ic_add_box_white_18dp, R.drawable.ic_mail_white_18dp,
                R.drawable.ic_location_disabled_white_18dp, R.drawable.ic_account_circle_black_24dp,
                R.drawable.ic_android_white_18dp, R.drawable.ic_settings_applications_white_18dp,
                R.drawable.ic_settings_applications_white_18dp};

        for (int i = 0; i < menu_items_Drawer.length; i++) {
            primaryDrawerItems[i] = new PrimaryDrawerItem()
                    .withName(menu_items_Drawer[i])
                    .withIdentifier(i)
                    .withIcon(drawableId[i])
                    .withSelectable(false);
        }

        AccountHeader accountHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .build();

        drawer = new DrawerBuilder(this)
                .withActivity(CabinetBase.this)
                .withAccountHeader(accountHeader)
                .withDisplayBelowStatusBar(true)
                .withToolbar(toolbar)
                .withActionBarDrawerToggleAnimated(true)
                .withSavedInstance(savedInstanceState)
                .addDrawerItems(primaryDrawerItems)
                .addStickyDrawerItems(
                        new SecondaryDrawerItem()
                                .withName(getString(R.string.ret_exit))
                                .withIdentifier(menu_items_Drawer.length - 1)
                                .withIcon(R.drawable.ic_exit_to_app_white_18dp)
                                .withSelectable(false)
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        //
                        selectedDrawerItem = position;
                        if (drawerItem != null) {
                            if (drawerItem.getIdentifier() >= 0 && selectedDrawerItem != -1) {
                                setToolbarAndSelectedDrawerItem(
                                        menu_items_Drawer[selectedDrawerItem - 1],
                                        (selectedDrawerItem - 1)
                                );

                                Log.d(BaseConstant.TAG, "Drawer:selectedDrawerItem:" + selectedDrawerItem);

                                switch (selectedDrawerItem) {
                                    case 1:

                                        break;
                                    case 2:


                                        break;
                                    case 3:

                                        break;
                                    case 4:

                                        break;
                                    case 5:

                                        break;
                                    case 6: //About

                                        break;
                                    case 7: //About

                                        break;
                                    default:

                                        break;
                                }

                            } else if (selectedDrawerItem == -1) {
                                overridePendingTransition(R.anim.open_next, R.anim.close_main);
                                finish();
                            }
                        }
                        return false;
                        //
                    }
                })
                .withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(true)
                .build();
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.open_main, R.anim.close_next);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_base, menu);
        MenuItem splashItem = menu.findItem(R.id.action_splash);
        splashItem.setChecked(preferenceHelper.getBoolean(BaseConstant.SPLASH_IS_INVISIBLE));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_splash:
                item.setChecked(!item.isChecked());
                PreferenceHelper.getInstance().putBoolean(BaseConstant.SPLASH_IS_INVISIBLE, item.isChecked());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setToolbarAndSelectedDrawerItem(String title, int selectedDrawerItem) {
        if (toolbar !=  null) {
            toolbar.setTitle(title);
        }
        drawer.setSelection(selectedDrawerItem, false);
    }

    private void request() {
        Log.d(BaseConstant.TAG, getClass().getName()+":request:");
        Intent intentService = new Intent(this, ServiceHelper.class);
        intentService.setAction(BaseConstant.ACTION_SERVICE_GET_CABINET);
        startService(intentService);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        request();
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventCabinet(CabinetModels event){
        mCabinetModels.addAll(event.getCabinetModels());
        mTextView.setText(preferenceHelper.getUserName());
        mTextViewCart.setText(dateformat.format(mCabinetModels.get(0).getDateOperation()*1000l));
        mTextViewSumma.setText(formatData.format(mCabinetModels.get(0).getCartSumma()));
        Toast.makeText(this,""+mCabinetModels.get(0).getBalanceID(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void startOrder(int item) {
        Toast.makeText(this,"id = "+ item,Toast.LENGTH_LONG).show();
    }
}
