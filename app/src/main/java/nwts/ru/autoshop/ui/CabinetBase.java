package nwts.ru.autoshop.ui;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
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
import nwts.ru.autoshop.TODOApplication;
import nwts.ru.autoshop.fragment.cabinet.BalOrderFragment;
import nwts.ru.autoshop.fragment.cabinet.BalanceFragment;
import nwts.ru.autoshop.fragment.cabinet.CartFragment;
import nwts.ru.autoshop.fragment.cabinet.OrdersFragment;
import nwts.ru.autoshop.fragment.dialogs.DialogFragmentAddBalance;
import nwts.ru.autoshop.fragment.dialogs.DialogFragmentCartCount;
import nwts.ru.autoshop.fragment.dialogs.DialogFragmentCartProcessing;
import nwts.ru.autoshop.models.ProductCategoris;
import nwts.ru.autoshop.models.ProductCategory;
import nwts.ru.autoshop.models.ProductDetails;
import nwts.ru.autoshop.models.network.CabinetModel;
import nwts.ru.autoshop.models.network.CabinetModels;
import nwts.ru.autoshop.services.ServiceHelper;
import nwts.ru.autoshop.services.ServiceIntentGetDataMore;
import nwts.ru.autoshop.setting.BaseConstant;
import nwts.ru.autoshop.setting.PreferenceHelper;
import nwts.ru.autoshop.setting.ToolBarTitle;

import static android.R.attr.fragment;
import static android.R.attr.tag;
import static nwts.ru.autoshop.R.id.prgLoading;
import static nwts.ru.autoshop.R.id.txtAlert;


public class CabinetBase extends AppCompatActivity implements OrdersFragment.isOrdersFragment,
        BalanceFragment.isBalanceFragment, CartFragment.isCartFragment, DialogFragmentAddBalance.DialogPositiveClick,
        DialogFragmentCartProcessing.isCartProcessing, BalOrderFragment.isBalOrderFragment, ToolBarTitle {

    private Toolbar toolbar;
    PreferenceHelper preferenceHelper;
    private Drawer drawer = null;
    private String[] menu_items_Drawer;
    private int selectedDrawerItem = 0;
    private List<CabinetModel> mCabinetModels;
    private FloatingActionButton fab;
    private TextView mTextView, mTextViewNameFragment;
    private TextView mTextViewCart, mTextViewSumma, mTextcartBalans;
    private TextView mTextViewCabinetData, mTextViewCabinetSumma, mTextViewCabinetSummaName;
    private TextView mTextViewImageView, mTextViewDivider;
    //private Fragment fragment;
    // create price format
    DecimalFormat formatData = new DecimalFormat("0.00");
    SimpleDateFormat dateformat = new SimpleDateFormat("dd.MM.yyyy' 'HH:mm:ss");
    private int fabTypeComand = 0;
    OrdersFragment ordersFragment;
    BalanceFragment mBalanceFragment;
    CartFragment mCartFragment;
    BalOrderFragment mBalOrderFragment;
    BottomNavigationView bnv;
    DialogFragmentAddBalance mDialogFragmentAddBalance;
    DialogFragmentCartProcessing mDialogFragmentCartProcessing;
    List<ProductCategory> productCategoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cabinet);
        mCabinetModels = new ArrayList<>();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
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
//                Snackbar.make(view, "Replace with your own action,", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Fragment fragment = getFragmentManager().findFragmentById(R.id.content_frame_cabinet);
                String tag = (String) fragment.getTag();
                if (tag.equals(BaseConstant.TAG_ORDERS_FRAGMENT)) {
                    ordersFragment.movedRecyclerViewOnTop();
                }
                if (tag.equals(BaseConstant.TAG_BALANCE_FRAGMENT)) {
                    if (fabTypeComand == 0) {
                        mBalanceFragment.movedRecyclerViewOnTop();
                    } else {
                        //
                        mDialogFragmentAddBalance = new DialogFragmentAddBalance();
                        mDialogFragmentAddBalance.show(getFragmentManager(), "dialog");
                    }

                }
                if (tag.equals(BaseConstant.TAG_CART_FRAGMENT)) {
                    if (TODOApplication.getCartSumma() > 0) {
                        mDialogFragmentCartProcessing = new DialogFragmentCartProcessing();
                        mDialogFragmentCartProcessing.show(getFragmentManager(), "dialog_cart");
                    } else {
                        //show message что корзина пуста
                        Snackbar.make(view, R.string.show_cart_null_message, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
            }
        });

        mTextView = (TextView) findViewById(R.id.name_cabinet);
        mTextViewCart = (TextView) findViewById(R.id.cart_content_cabinet);
        mTextViewSumma = (TextView) findViewById(R.id.summa_cart_cabinet);
        mTextViewNameFragment = (TextView) findViewById(R.id.name_fragment_cabinet);
        mTextcartBalans = (TextView) findViewById(R.id.cart_content_balans);
        mTextViewImageView = (TextView) findViewById(R.id.cart_content_imageview);
        mTextViewDivider = (TextView) findViewById(R.id.productDetailDivider9);
        //
        mTextViewCabinetData = (TextView) findViewById(R.id.cabinet_data);
        mTextViewCabinetSumma = (TextView) findViewById(R.id.cabinet_summa);
        mTextViewCabinetSummaName = (TextView) findViewById(R.id.cabinet_summa_name);


        bnv = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bnv.setOnNavigationItemSelectedListener(getBottomNavigationListener());
        Fragment fragment = getFragmentManager().findFragmentById(R.id.content_frame_cabinet);
        //String tag = (String) fragment.getTag();
        if (savedInstanceState != null) {
            if (savedInstanceState.get(BaseConstant.TAG_CABINET) != null) {
                if (savedInstanceState.get(BaseConstant.TAG_CABINET).equals(BaseConstant.TAG_ORDERS_FRAGMENT)) {
                    getOrders();
                    /*
                    View view = bottomNavigationView.findViewById(R.id.menu_action_dashboard);
                    view.performClick();
                     */
                    bnv.getMenu().getItem(0).setChecked(true);
                }
                if (savedInstanceState.get(BaseConstant.TAG_CABINET).equals(BaseConstant.TAG_BALANCE_FRAGMENT)) {
                    getBalance();
                    bnv.getMenu().getItem(1).setChecked(true);

                }
                if (savedInstanceState.get(BaseConstant.TAG_CABINET).equals(BaseConstant.TAG_CART_FRAGMENT)) {
                    getCart();
                    bnv.getMenu().getItem(2).setChecked(true);
                    showFab();
                }
                if (savedInstanceState.get(BaseConstant.TAG_CABINET).equals(BaseConstant.TAG_BAL_ORDER_FRAGMENT)) {
                    getBalOrder();
                    hideFab();
                    // bnv.getMenu().getItem(2).setChecked(true);
                }
            } else {
                getOrders();
            }
        } else {
            getOrders();
        }


        //Если нужна корзина
        if (TODOApplication.getCabinmet_form() == 1) {
            getCart();
            bnv.getMenu().getItem(2).setChecked(true);
            showFab();
        }

    }

    private void getOrders() {
        mTextViewNameFragment.setText(R.string.name_liost_orders);
        mTextViewCabinetData.setText(R.string.cabinet_date);
        mTextViewCabinetSumma.setText(R.string.cabinet_summa);
        mTextViewCabinetSummaName.setText(R.string.cabinet_summa_name);
        mTextViewImageView.setVisibility(View.GONE);
        mTextViewDivider.setVisibility(View.GONE);
        ordersFragment = new OrdersFragment();
        if (getFragmentManager().getBackStackEntryCount() != 0) {
            getFragmentManager().popBackStack();
        }
        getFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .replace(R.id.content_frame_cabinet, ordersFragment, BaseConstant.TAG_ORDERS_FRAGMENT).commit();
    }


    private void getBalance() {
        mTextViewNameFragment.setText(R.string.bottom_nav_text_operation);
        mTextViewCabinetData.setText(R.string.cabinet_date);
        mTextViewCabinetSumma.setText(R.string.cabinet_summa);
        mTextViewCabinetSummaName.setText(R.string.cabinet_summa_direction);
        mTextViewImageView.setVisibility(View.GONE);
        mTextViewDivider.setVisibility(View.GONE);
        mBalanceFragment = new BalanceFragment();
        if (getFragmentManager().getBackStackEntryCount() != 0) {
            getFragmentManager().popBackStack();
        }
        getFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .replace(R.id.content_frame_cabinet, mBalanceFragment, BaseConstant.TAG_BALANCE_FRAGMENT).commit();
    }

    private void getCart() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            mTextViewImageView.setVisibility(View.GONE);
            mTextViewDivider.setVisibility(View.GONE);
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mTextViewImageView.setVisibility(View.VISIBLE);
            mTextViewImageView.setText(R.string.cabinet_cart_image);
            mTextViewDivider.setVisibility(View.VISIBLE);
        }
        mTextViewNameFragment.setText(R.string.bottom_nav_text_cart);
        mTextViewCabinetData.setText(R.string.cabinet_tovar_name);
        mTextViewCabinetSumma.setText(R.string.cabinet_tovar_count);
        mTextViewCabinetSummaName.setText(R.string.cabinet_summa);
        mCartFragment = new CartFragment();
        if (getFragmentManager().getBackStackEntryCount() != 0) {
            getFragmentManager().popBackStack();
        }
        getFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .replace(R.id.content_frame_cabinet, mCartFragment, BaseConstant.TAG_CART_FRAGMENT).commit();
    }

    @NonNull
    private BottomNavigationView.OnNavigationItemSelectedListener getBottomNavigationListener() {
        return new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_orders:
                        getOrders();
//                        textFavorites.setVisibility(View.VISIBLE);
//                        textCollection.setVisibility(View.GONE);
//                        textFriends.setVisibility(View.GONE);
                        break;

                    case R.id.action_balance:
                        getBalance();

/*                        textFavorites.setVisibility(View.GONE);
                        textCollection.setVisibility(View.VISIBLE);
                        textFriends.setVisibility(View.GONE);*/
                        break;

                    case R.id.action_cart:
                        getCart();
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

        int[] drawableId = new int[]{R.drawable.ic_cart_shop_web,
                R.drawable.ic_orders,
                R.drawable.ic_balance,
                R.drawable.ic_shop_about};

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
                                        getCart();
                                        bnv.getMenu().getItem(2).setChecked(true);
                                        showFab();
                                        break;
                                    case 2:
                                        getOrders();
                                        bnv.getMenu().getItem(0).setChecked(true);
                                        break;
                                    case 3:
                                        getBalance();
                                        bnv.getMenu().getItem(1).setChecked(true);
                                        break;
                                    case 4:
                                        Intent intentInformation = new Intent(CabinetBase.this, Information.class);
                                        overridePendingTransition(R.anim.open_main, R.anim.close_next);
                                        startActivity(intentInformation);
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
        getMenuInflater().inflate(R.menu.menu_cabinet, menu);
        MenuItem splashItem = menu.findItem(R.id.action_splash);
        splashItem.setChecked(preferenceHelper.getBoolean(BaseConstant.SPLASH_IS_INVISIBLE));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_refresh:
                refresh_func();
                break;
            case R.id.action_splash:
                item.setChecked(!item.isChecked());
                PreferenceHelper.getInstance().putBoolean(BaseConstant.SPLASH_IS_INVISIBLE, item.isChecked());
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setToolbarAndSelectedDrawerItem(String title, int selectedDrawerItem) {
        if (toolbar != null) {
            toolbar.setTitle(title);
        }
        drawer.setSelection(selectedDrawerItem, false);
    }

    private void request() {
        Log.d(BaseConstant.TAG, getClass().getName() + ":request:");
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
    public void onEventCabinet(CabinetModels event) {
        mCabinetModels.clear();
        mCabinetModels.addAll(event.getCabinetModels());
        mTextView.setText(preferenceHelper.getUserName());
        Fragment fragment = getFragmentManager().findFragmentById(R.id.content_frame_cabinet);
        String tag = (String) fragment.getTag();
        if (tag.equals(BaseConstant.TAG_CART_FRAGMENT)) {
            mTextViewCart.setText("Сумма в корзине");
            mTextViewSumma.setText(formatData.format(mCabinetModels.get(0).getCartSumma()));
        } else {
            mTextViewCart.setText(getString(R.string.cabinet_rest_name));
            mTextViewSumma.setText(formatData.format(mCabinetModels.get(0).getBalance()));
        }
        TODOApplication.setBalSumma(mCabinetModels.get(0).getBalance());
        TODOApplication.setCartSumma(mCabinetModels.get(0).getCartSumma());
    }

    @Override
    public void fabCommand(int fabItem) {
        fabTypeComand = fabItem;
        //fab.setImageDrawable(R.drawable.);
        // fab.setImageIcon(R.mipmap.ic_fab_add);
        if (fabTypeComand == 0) {
            showFab();
            fab.setImageResource(R.drawable.ic_fab_action_top);
        } else {
            hideFab();
            fab.setImageResource(R.drawable.ic_action_plus_white);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Fragment fragment = getFragmentManager().findFragmentById(R.id.content_frame_cabinet);
        String tag = (String) fragment.getTag();
        outState.putString(BaseConstant.TAG_CABINET, tag);
        super.onSaveInstanceState(outState);
    }

    private void selectorFragments() {
        Fragment fragment = getFragmentManager().findFragmentById(R.id.content_frame_cabinet);
        String tag = (String) fragment.getTag();
        if (tag != null) {
            if (tag.equals(BaseConstant.TAG_ORDERS_FRAGMENT)) {
                getOrders();
            }
            if (tag.equals(BaseConstant.TAG_BALANCE_FRAGMENT)) {
                getBalance();
            }
            if (tag.equals(BaseConstant.TAG_CART_FRAGMENT)) {
                getCart();
            }
        }
    }

    /*
    fab
     */
    //magic happens here
    private void hideFab() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            final Point point = new Point();
            CabinetBase.this.getWindow().getWindowManager().getDefaultDisplay().getSize(point);
            final float translation = fab.getY() - point.y;
            fab.animate().translationYBy(-translation).start();
        } else {
            Animation animation = AnimationUtils.makeOutAnimation(CabinetBase.this, true);
            animation.setFillAfter(true);

            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    fab.setClickable(false);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            fab.startAnimation(animation);
        }
    }

    private void showFab() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            fab.animate().translationY(0).start();
        } else {
            Animation animation = AnimationUtils.makeInAnimation(CabinetBase.this, false);
            animation.setFillAfter(true);

            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    fab.setClickable(true);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            fab.startAnimation(animation);
        }
    }
     /*
     fab 2
     */


    @Override
    public void startBalance(int item) {
        TODOApplication.setKey_id(item);
        getBalOrder();
        hideFab();
    }

    @Override
    public void startOrder(int item) {
        TODOApplication.setKey_id(item);
        getBalOrder();
        hideFab();
    }

    @Override
    public void fabCommandBalance(int fabItem) {
        fabTypeComand = fabItem;
        //fab.setImageDrawable(R.drawable.);
        // fab.setImageIcon(R.mipmap.ic_fab_add);
        if (fabTypeComand == 0) {
            showFab();
            fab.setImageResource(R.drawable.ic_fab_action_top);
        } else {
            // hideFab();
            showFab();
            fab.setImageResource(R.drawable.ic_action_plus_white);
        }
    }

    @Override
    public void startCart(int item) {
        //  Toast.makeText(this, "ID =" + item, Toast.LENGTH_SHORT).show();
        if (item != 0) {
            startProductDetailView(item);
        }
    }

    @Override
    public void delCartPosition(final int item, final double summa, final int quantity) {

        AlertDialog.Builder builder = new AlertDialog.Builder(CabinetBase.this);
        builder.setTitle(R.string.show_question_title)
                .setMessage(R.string.show_question_what)
                .setIcon(R.drawable.ic_error)
                .setCancelable(false)
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intentService = new Intent(CabinetBase.this, ServiceHelper.class);
                        intentService.setAction(BaseConstant.ACTION_SERVICE_DEL_CART);
                        intentService.putExtra(BaseConstant.API_QUANTITY, quantity);
                        intentService.putExtra(BaseConstant.API_PRODUCT_ID, item);
                        intentService.putExtra(BaseConstant.API_BAL_SYS, summa);
                        startService(intentService);
                    }
                })
                .setNegativeButton("Нет",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alertBalance = builder.create();
        alertBalance.show();
    }

    @Override
    public void fabCommandCart(int fabItem) {
        fabTypeComand = fabItem;
        showFab();
        fab.setImageResource(R.drawable.ic_action_cart_pay);
    }

    @Override
    public void getMoney(String money, String paySystem) {
        String paySystems;
        double parseSum;

        if (money == null || TextUtils.isEmpty(money)) {
            return;
        } else {
            parseSum = Double.parseDouble(money);
        }
        if (paySystem == null || TextUtils.isEmpty(paySystem)) {
            paySystems = "Не определено";
        } else {
            paySystems = paySystem;
        }
        addBalance(parseSum, paySystems);
        Toast.makeText(this, "Вы ввели cумму:" + money + " по системе: " + paySystems, Toast.LENGTH_SHORT).show();
    }

    //add balance
    private void addBalance(double sumBal, String paysys) {
        Intent intentService = new Intent(this, ServiceHelper.class);
        intentService.setAction(BaseConstant.ACTION_SERVICE_GET_BALANCE_ADD);
        intentService.putExtra(BaseConstant.API_GET_KEY, PreferenceHelper.getInstance().getUserId());
        intentService.putExtra(BaseConstant.API_BAL_SUM, sumBal);
        intentService.putExtra(BaseConstant.API_BAL_SYS, paysys);
        this.startService(intentService);
    }


    private void getBalOrder() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            mTextViewImageView.setVisibility(View.GONE);
            mTextViewDivider.setVisibility(View.GONE);
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mTextViewImageView.setVisibility(View.VISIBLE);
            mTextViewImageView.setText(R.string.cabinet_cart_image);
            mTextViewDivider.setVisibility(View.VISIBLE);
        }
        mTextViewNameFragment.setText(R.string.order_tovar);
        mTextViewCabinetData.setText(R.string.cabinet_tovar_name);
        mTextViewCabinetSumma.setText(R.string.cabinet_tovar_count);
        mTextViewCabinetSummaName.setText(R.string.cabinet_summa);
        mBalOrderFragment = new BalOrderFragment();
        if (getFragmentManager().getBackStackEntryCount() != 0) {
            getFragmentManager().popBackStack();
        }
        getFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .replace(R.id.content_frame_cabinet, mBalOrderFragment, BaseConstant.TAG_BAL_ORDER_FRAGMENT).commit();
    }

    @Override
    public void startBalOrder(int item) {
        //Тут можно сделдать переход на сайт платежа...
        //Это не продукт
        // Toast.makeText(this, "ID= " + item, Toast.LENGTH_SHORT).show();
        if (item != 0) {
            startProductDetailView(item);
        }
    }

    @Override
    public void BaseActivitySteToolBarTitle(String setTitle) {
        if (toolbar != null) {
            toolbar.setTitle(setTitle);
            Fragment fragment = getFragmentManager().findFragmentById(R.id.content_frame_cabinet);
            if (fragment != null) {
                String tag = (String) fragment.getTag();
                if (tag.equals(BaseConstant.TAG_BAL_ORDER_FRAGMENT)) {
                    mTextViewNameFragment.setText(setTitle);
                }
            }
        }
    }

    @Override
    public void startCartProcessing() {
        getCart();
    }

    public void startProductDetailView(int item) {
        //Подготовка
        TODOApplication.setProductDetail_Id(item);
        Intent intentService = new Intent(this, ServiceHelper.class);
        intentService.setAction(BaseConstant.ACTION_SERVICE_GET_PRODUCT_DETAIL_ID);
        intentService.putExtra(BaseConstant.API_GET_KEY, item); //продукт id
        startService(intentService);

//
//        Intent intentProductDetail = new Intent(this, ProductDetailView.class);
//        this.startActivity(intentProductDetail);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventCategoryList(ProductDetails event) {
        if (event.getProductCategories().isEmpty() || event.getProductCategories().size() < 1) {
            AlertDialog.Builder builder = new AlertDialog.Builder(CabinetBase.this);
            builder.setTitle(R.string.show_error_detail)
                    .setMessage(R.string.show_error_message_detail)
                    .setIcon(R.drawable.ic_error)
                    .setCancelable(false)
                    .setNegativeButton("Да, поятненько  :(",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
            AlertDialog alertBalance = builder.create();
            alertBalance.show();
        } else {
            TODOApplication.setDetail_category_Id(event.getProductCategories().get(0).getCategory_ID());
            TODOApplication.setDetail_product_Id(event.getProductCategories().get(0).getProduct_ID());
            TODOApplication.setDetail_subcategory_Id(event.getProductCategories().get(0).getSubCategory_ID());
            TODOApplication.setDetail_description(event.getProductCategories().get(0).getDescription());
            TODOApplication.setDetail_productName(event.getProductCategories().get(0).getMenu_name());
            TODOApplication.setDetail_price(event.getProductCategories().get(0).getPrice());
            TODOApplication.setDetail_quantity(event.getProductCategories().get(0).getQuantity());
            TODOApplication.setUrl_Image(event.getProductCategories().get(0).getMenu_image());
            TODOApplication.setDetail_rating(event.getProductCategories().get(0).getRating());
            //
            Intent intentProductDetail = new Intent(this, ProductDetailView.class);
            this.startActivity(intentProductDetail);
        }
    }

    //Refresh
    private void refresh_func() {
        Fragment fragment = getFragmentManager().findFragmentById(R.id.content_frame_cabinet);
        if (fragment.getTag() != null) {
            if (fragment.getTag().equals(BaseConstant.TAG_ORDERS_FRAGMENT)) {
                getOrders();
                bnv.getMenu().getItem(0).setChecked(true);
            }
            if (fragment.getTag().equals(BaseConstant.TAG_BALANCE_FRAGMENT)) {
                getBalance();
                bnv.getMenu().getItem(1).setChecked(true);

            }
            if (fragment.getTag().equals(BaseConstant.TAG_CART_FRAGMENT)) {
                getCart();
                bnv.getMenu().getItem(2).setChecked(true);
                showFab();
            }
            if (fragment.getTag().equals(BaseConstant.TAG_BAL_ORDER_FRAGMENT)) {
                getBalOrder();
                hideFab();
                // bnv.getMenu().getItem(2).setChecked(true);
            }
        } else {
            getOrders();
        }
    }

}