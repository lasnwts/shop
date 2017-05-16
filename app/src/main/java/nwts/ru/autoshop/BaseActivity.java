package nwts.ru.autoshop;

import android.app.Application;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import nwts.ru.autoshop.network.ValidateToken;
import nwts.ru.autoshop.ui.About;
import nwts.ru.autoshop.fragment.HomeMenu;
import nwts.ru.autoshop.fragment.ProductCatalog;
import nwts.ru.autoshop.fragment.SubCatalog;
import nwts.ru.autoshop.setting.ToolBarTitle;
import nwts.ru.autoshop.ui.CabinetBase;
import nwts.ru.autoshop.ui.Information;
import nwts.ru.autoshop.fragment.Category;
import nwts.ru.autoshop.fragment.SplashScreen;
import nwts.ru.autoshop.services.ServiceHelper;
import nwts.ru.autoshop.setting.BaseConstant;
import nwts.ru.autoshop.setting.PreferenceHelper;
import nwts.ru.autoshop.ui.LoginActivity;
import nwts.ru.autoshop.ui.ProductDetailView;


public class BaseActivity extends AppCompatActivity implements HomeMenu.OnLinkItemSelectedListener,
        ToolBarTitle, Category.iCategoty, SubCatalog.iSubCatalog, ProductCatalog.iProductCatalog {

    /*
    Variables
     */
    private final String TAG_LIFE_MAIN_ACTIVITY = "f_base_life"; //Тэг активности activity Если true активность пересоздана
    FragmentManager fragmentManager;
    PreferenceHelper preferenceHelper;
    private Intent intentService;
    private Application application;

    private Drawer drawer = null;
    private Toolbar toolbar;

    private String[] menu_items_Drawer;
    private int selectedDrawerItem = 0;
    private Fragment fragment;
    ValidateToken validateToken;

    ProductCatalog productCatalogFragment;


    /*
    Ended Variables
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        PreferenceHelper.getInstance().init(getApplicationContext());
        preferenceHelper = PreferenceHelper.getInstance();
        fragmentManager = getFragmentManager();
        application = getApplication();

        //test***
        startServiceHelper();
        //End of test ****

        //create menu
        //initializing views
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        menu_items_Drawer = getResources().getStringArray(R.array.menu_items);
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
                .withActivity(BaseActivity.this)
                .withAccountHeader(accountHeader)
                .withDisplayBelowStatusBar(true)
                .withToolbar(toolbar)
                .withActionBarDrawerToggleAnimated(true)
                .withSavedInstance(savedInstanceState)
                .addDrawerItems(primaryDrawerItems)
                .addStickyDrawerItems(
                        new SecondaryDrawerItem()
                                .withName(getString(R.string.exit))
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
                                        getShopPage(0); //mAIN PAGE;
                                        break;
                                    case 2:
                                        getShopPage(1);
                                        //    DialogFragmentInputStr editNameDialogFragment = new DialogFragmentInputStr();
                                        //    editNameDialogFragment.show(manager, "fragment_edit_name");
                                        break;
                                    case 3:
                                        //    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                                        //   shareIntent.setType("text/plain");
                                        //    shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.subject));
                                        //    shareIntent.putExtra(Intent.EXTRA_TEXT, putEmailAndFireBasePathtoClient());
                                        //    startActivity(Intent.createChooser(shareIntent, getString(R.string.share_to)));
                                        break;
                                    case 4:
                                        if (!TODOApplication.getInstance().isValidateToken()){
                                            if (validateToken.getValidateToken()) {
                                                TODOApplication.getInstance().setValidateToken(true);
                                            } else {
                                                Intent loginIntent = (Intent) new Intent(BaseActivity.this, LoginActivity.class);
                                                startActivity(loginIntent);
                                            }
                                        } else {
                                           // Toast.makeText(BaseActivity.this,"Token "+PreferenceHelper.getInstance().getAuthToken()+" is Valid.",Toast.LENGTH_LONG).show();
                                            Intent cabinetIntent = (Intent) new Intent(BaseActivity.this, CabinetBase.class);
                                            startActivity(cabinetIntent);
                                        }
                                        break;
                                    case 5: //масштабировать
                                        //   setScale();
                                        break;
                                    case 6: //About
                                        //    showAbout();
                                        //testView();
                                        getShopPage(7);
                                        break;
                                    case 7: //About
                                        //   showSettings();
                                        getShopPage(8);
                                        // testView2();
                                        break;
                                    default:
                                        getShopPage(0); //mAIN PAGE;
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

        // get screen device width and height
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        fragmentManager = getFragmentManager();


        // checking internet connection
//        if (!Constant.isNetworkAvailable(MainActivity.this)) {
//            Toast.makeText(MainActivity.this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
//        }

        if (savedInstanceState != null) {
            if (!savedInstanceState.getBoolean(TAG_LIFE_MAIN_ACTIVITY)) {
                getShopPage(0);
                runSplash();
            }
        } else {
            getShopPage(0);
            runSplash();
        }

        validateToken = ValidateToken.getInstance();
        if (!TODOApplication.getInstance().isValidateToken()){
            if (validateToken.getValidateToken()) {
                TODOApplication.getInstance().setValidateToken(true);
            }
        }
    }

    private void setToolbarAndSelectedDrawerItem(String title, int selectedDrawerItem) {
        toolbar.setTitle(title);
        drawer.setSelection(selectedDrawerItem, false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //  return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_base, menu);
        MenuItem splashItem = menu.findItem(R.id.action_splash);
        splashItem.setChecked(preferenceHelper.getBoolean(BaseConstant.SPLASH_IS_INVISIBLE));
        return true;
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Fragment fragment = getFragmentManager().findFragmentById(R.id.content_frame);
        String tag = (String) fragment.getTag();
        if (tag != null) {
            if (tag.equalsIgnoreCase(BaseConstant.TAG_MAIN_MENU_FRAGMENT)) {
                BaseActivitySteToolBarTitle(getResources().getString(R.string.toolbar_main_menu));
            } else {
                if (tag.equalsIgnoreCase(BaseConstant.TAG_CATEGORY_FRAGMENT)) {
                    BaseActivitySteToolBarTitle(getResources().getString(R.string.toolbar_main_category));
                } else {
                    if (tag.equalsIgnoreCase(BaseConstant.TAG_SUBCATEGORY_FRAGMENT)) {
                        BaseActivitySteToolBarTitle(getResources().getString(R.string.toolbar_main_subcategory));
                    } else {
                        if (tag.equalsIgnoreCase(BaseConstant.TAG_PRODUCT_CATALOG_FRAGMENT)) {
                            BaseActivitySteToolBarTitle(getResources().getString(R.string.toolbar_main_product_catalog));
                        }
                    }
                }
            }
        }

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


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(TAG_LIFE_MAIN_ACTIVITY, true);
        super.onSaveInstanceState(outState);
    }

    public void runSplash() {

        if (!preferenceHelper.getBoolean(BaseConstant.SPLASH_IS_INVISIBLE)) {
            TODOApplication.getInstance().setFlag_run_Splash(true);
            SplashScreen splashFragment = new SplashScreen();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, splashFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }


    public void getFragmentMainMenu() {
        HomeMenu home = new HomeMenu();
        if (fragmentManager.getBackStackEntryCount() != 0) {
            fragmentManager.popBackStack();
        }
        fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .replace(R.id.content_frame, home, BaseConstant.TAG_MAIN_MENU_FRAGMENT).commit();
    }


    private void startServiceHelper() {
        intentService = new Intent(application, ServiceHelper.class);
        startService(intentService);
    }

    @Override
    protected void onDestroy() {
        if (intentService != null) {
            stopService(intentService);
        }
        super.onDestroy();
    }


    //function selector Activity or Fragment
    public void getShopPage(int id) {
        switch (id) {
            case 0:
                getFragmentMainMenu();
                break;
            case 1:
                clearFRagmentBackStack();
                Category category = new Category();
                fragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                        .replace(R.id.content_frame, category, BaseConstant.TAG_CATEGORY_FRAGMENT).commit();
                break;
            case 7: //information
                Intent intentInformation = new Intent(this, Information.class);
                overridePendingTransition(R.anim.open_main, R.anim.close_next);
                startActivity(intentInformation);
                break;
            case 8: //about
                Intent intent = new Intent(this, About.class);
                overridePendingTransition(R.anim.open_main, R.anim.close_next);
                startActivity(intent);
                break;
            case 20: //SubCategory подкаталог
//                Intent intentServiceSubCategory = new Intent(this, ServiceHelper.class);
//                intentServiceSubCategory.setAction(BaseConstant.ACTION_SERVICE_GET_SUBCATEGORY_LIST);
//                intentServiceSubCategory.putExtra(BaseConstant.API_GET_KEY, TODOApplication.getSubCategory_Id());
//                startService(intentServiceSubCategory);
                SubCatalog subCatalog = new SubCatalog();
                getFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                        .replace(R.id.content_frame, subCatalog, BaseConstant.TAG_SUBCATEGORY_FRAGMENT).commit();
                break;
            default:
                break;
        }
    }

    public void clearFRagmentBackStack() {
        if (fragmentManager.getBackStackEntryCount() != 0) {
            do
                fragmentManager.popBackStack();
            while (fragmentManager.getBackStackEntryCount() > 0);
        }
    }

    @Override
    public void onBackPressed() {
        Log.d(BaseConstant.TAG, "onBackPressed:TODOApplication:Category_Id:" + TODOApplication.getCategory_Id());
        Fragment fr = getFragmentManager().findFragmentById(R.id.content_frame);
        if (fr != null) {
            if (fr.getTag() != null) {
                if (fr.getTag().equals(BaseConstant.TAG_CATEGORY_FRAGMENT)) {
                    clearFRagmentBackStack();
                    getShopPage(0);
                } else {
                    //TAG1
                    if (fr.getTag().equals(BaseConstant.TAG_SUBCATEGORY_FRAGMENT)) {
                        clearFRagmentBackStack();
                        getShopPage(1);
                    } else {
                        //TAG2
                        if (fr.getTag().equals(BaseConstant.TAG_PRODUCT_CATALOG_FRAGMENT)) {
                            clearFRagmentBackStack();
                            getShopPage(20);
                        } else {
                            super.onBackPressed();
                        }
                        //TAG2
                    }
                    //TAG1
                }
            } else {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }

    //From HomeMenu fragment
    @Override
    public void omItemSelectedMenu(int position) {
        Log.d(BaseConstant.TAG, "BaseActivity:position:" + position);
        switch (position) {
            case 0:
                getShopPage(1);
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            default:
                break;
        }
    }

    @Override
    public void BaseActivitySteToolBarTitle(String setTitles) {
        toolbar.setTitle(setTitles);
    }

    /**
     * Запускает получение информации о под-категориях магазина и зхагружает нужный фрагмент фрагмент
     * (2-ой уровент)
     *
     * @param item
     */
    @Override
    public void startSubCategory(int item) {
        //Передаем в ServiceHelper апрос на данные
        TODOApplication.setSubCategory_Id(item);;
//        Intent intentService = new Intent(this, ServiceHelper.class);
//        intentService.setAction(BaseConstant.ACTION_SERVICE_GET_SUBCATEGORY_LIST);
//        intentService.putExtra(BaseConstant.API_GET_KEY, item);
//        startService(intentService);
        SubCatalog subCatalog = new SubCatalog();
        getFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .replace(R.id.content_frame, subCatalog, BaseConstant.TAG_SUBCATEGORY_FRAGMENT)
                .commit();
    }

    /**
     * Запускаем каталог продуктов подкатегория (3-ий уровень)
     *
     * @param item
     */
    @Override
    public void startProductCatalog(int item) {
        TODOApplication.setProductCatalog_Id(item);
//        Intent intentService = new Intent(this, ServiceHelper.class);
//        intentService.setAction(BaseConstant.ACTION_SERVICE_GET_PRODUCT_LIST);
//        intentService.putExtra(BaseConstant.API_GET_KEY, item);
//        this.startService(intentService);
        ProductCatalog productCatalog = new ProductCatalog();
        getFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                .replace(R.id.content_frame, productCatalog,
                        BaseConstant.TAG_PRODUCT_CATALOG_FRAGMENT).commit();
    }

    /**
     * Запускаем деталировку пролукта
     *
     * @param item
     */
    @Override
    public void startProductDetailView(int item) {
        TODOApplication.setProductDetail_Id(item);
//        Intent intentService = new Intent(this, ServiceHelper.class);
//        intentService.setAction(BaseConstant.ACTION_SERVICE_GET_PRODUCT_DETAIL);
//        intentService.putExtra(BaseConstant.API_GET_KEY, item);
//        this.startService(intentService);
        Intent intentProductDetail = new Intent(this, ProductDetailView.class);
        this.startActivity(intentProductDetail);
    }
}
