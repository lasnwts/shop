package nwts.ru.autoshop.ui;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.List;

import nwts.ru.autoshop.BaseActivity;
import nwts.ru.autoshop.R;
import nwts.ru.autoshop.TODOApplication;
import nwts.ru.autoshop.models.ProductDetailImages;
import nwts.ru.autoshop.models.network.CabinetModel;
import nwts.ru.autoshop.models.network.CabinetModels;
import nwts.ru.autoshop.services.ServiceHelper;
import nwts.ru.autoshop.setting.BaseConstant;
import nwts.ru.autoshop.setting.PreferenceHelper;

import static nwts.ru.autoshop.R.id.fab;

public class CabinetBase extends AppCompatActivity {

    private Toolbar toolbar;
    PreferenceHelper preferenceHelper;
    private Drawer drawer = null;
    private String[] menu_items_Drawer;
    private int selectedDrawerItem = 0;
    private List<CabinetModel> mCabinetModels;
    FloatingActionButton fab;

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
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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
        Toast.makeText(this,mCabinetModels.get(0).getBalanceID(), Toast.LENGTH_LONG).show();
    }
}