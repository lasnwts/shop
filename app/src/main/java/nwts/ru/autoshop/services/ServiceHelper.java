package nwts.ru.autoshop.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import nwts.ru.autoshop.TODOApplication;
import nwts.ru.autoshop.setting.BaseConstant;
import nwts.ru.autoshop.setting.PreferenceHelper;

import static android.R.attr.rating;
import static nwts.ru.autoshop.setting.BaseConstant.ACTION_SERVICE_GET_CATEGORY_LIST;
import static nwts.ru.autoshop.setting.BaseConstant.ACTION_SERVICE_GET_PRODUCT_DETAIL;
import static nwts.ru.autoshop.setting.BaseConstant.ACTION_SERVICE_GET_PRODUCT_DETAIL_ID;
import static nwts.ru.autoshop.setting.BaseConstant.ACTION_SERVICE_GET_PRODUCT_LIST;
import static nwts.ru.autoshop.setting.BaseConstant.ACTION_SERVICE_GET_SUBCATEGORY_LIST;
import static nwts.ru.autoshop.setting.BaseConstant.API_GET_KEY;
import static nwts.ru.autoshop.setting.BaseConstant.API_KEY_ID;
import static nwts.ru.autoshop.setting.BaseConstant.API_RATING;

/**
 * Created by пользователь on 13.03.2017.
 */

public class ServiceHelper extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.d(BaseConstant.TAG, "Start ServiceHelper:onStartCommand: services..");
        if (intent != null) {
            if (intent.getAction() != null) {
                if (intent.getAction().equals(BaseConstant.ACTION_SERVICE_GET_BALANCE_ADD)) {
                    if (intent.getStringExtra(BaseConstant.API_BAL_SYS) != null &&
                            intent.getDoubleExtra(BaseConstant.API_BAL_SUM, 0) != 0) {

                        Intent intent1Service = new Intent(getApplication(), ServiceIntentGetDataMore.class);
                        intent1Service.putExtra(BaseConstant.API_PAGE, BaseConstant.ACTION_SERVICE_GET_BALANCE_ADD);
                        intent1Service.putExtra(API_GET_KEY, PreferenceHelper.getInstance().getUserId());
                        intent1Service.putExtra(BaseConstant.API_BAL_SUM, intent.getDoubleExtra(BaseConstant.API_BAL_SUM, 0));
                        intent1Service.putExtra(BaseConstant.API_BAL_SYS, intent.getStringExtra(BaseConstant.API_BAL_SYS));
                        startService(intent1Service);

                    }
                }
                if (intent.getAction().equals(BaseConstant.ACTION_SERVICE_GET_CART)) {
                    Intent intent1Service = new Intent(getApplication(), ServiceIntentGetDataMore.class);
                    intent1Service.putExtra(BaseConstant.API_PAGE, BaseConstant.ACTION_SERVICE_GET_CART);
                    intent1Service.putExtra(API_GET_KEY, PreferenceHelper.getInstance().getUserId());
                    startService(intent1Service);
                }
                if (intent.getAction().equals(BaseConstant.ACTION_SERVICE_ADD_COMMENTS)) {
                    Intent intent1Service = new Intent(getApplication(), ServiceIntentGetDataMore.class);
                    intent1Service.putExtra(BaseConstant.API_PAGE, BaseConstant.ACTION_SERVICE_ADD_COMMENTS);
                    intent1Service.putExtra(API_GET_KEY, PreferenceHelper.getInstance().getUserId());
                    intent1Service.putExtra(API_RATING, intent.getIntExtra(BaseConstant.API_RATING,0));
                    intent1Service.putExtra(BaseConstant.API_COOMENT, intent.getStringExtra(BaseConstant.API_COOMENT));
                    intent1Service.putExtra(BaseConstant.API_PRODUCT_ID, TODOApplication.getDetail_product_Id());
                    startService(intent1Service);
                }
                if (intent.getAction().equals(BaseConstant.ACTION_SERVICE_GET_COMMENTS_ID)) {
                    Intent intent1Service = new Intent(getApplication(), ServiceIntentGetDataMore.class);
                    intent1Service.putExtra(BaseConstant.API_PAGE, BaseConstant.ACTION_SERVICE_GET_COMMENTS_ID);
                    intent1Service.putExtra(API_GET_KEY, TODOApplication.getDetail_product_Id());
                    startService(intent1Service);
                }
                if (intent.getAction().equals(BaseConstant.ACTION_SERVICE_GET_CART_INPUT)) {
                    Intent intent1Service = new Intent(getApplication(), ServiceIntentGetDataMore.class);
                    intent1Service.putExtra(BaseConstant.API_PAGE, BaseConstant.ACTION_SERVICE_GET_CART_INPUT);
                    intent1Service.putExtra(API_GET_KEY, intent.getIntExtra(BaseConstant.API_GET_KEY, 1));
                    intent1Service.putExtra(API_KEY_ID, intent.getIntExtra(BaseConstant.API_KEY_ID, 0));
                    startService(intent1Service);
                }
                if (intent.getAction().equals(BaseConstant.ACTION_SERVICE_DEL_CART)) {
                    Intent intent1Service = new Intent(getApplication(), ServiceIntentGetDataMore.class);
                    intent1Service.putExtra(BaseConstant.API_PAGE, BaseConstant.ACTION_SERVICE_DEL_CART);
                    intent1Service.putExtra(BaseConstant.API_QUANTITY, intent.getIntExtra(BaseConstant.API_QUANTITY, 0));
                    intent1Service.putExtra(BaseConstant.API_PRODUCT_ID, intent.getIntExtra(BaseConstant.API_PRODUCT_ID, 0));
                    intent1Service.putExtra(BaseConstant.API_BAL_SYS, intent.getDoubleExtra(BaseConstant.API_BAL_SYS, 0));
                    startService(intent1Service);
                }
                if (intent.getAction().equals(BaseConstant.ACTION_SERVICE_GET_BALANCE_ID)) {
                    Intent intent1Service = new Intent(getApplication(), ServiceIntentGetDataMore.class);
                    intent1Service.putExtra(BaseConstant.API_PAGE, BaseConstant.ACTION_SERVICE_GET_BALANCE_ID);
                    startService(intent1Service);
                }
                if (intent.getAction().equals(BaseConstant.ACTION_SERVICE_GET_FIND_PRODUCT)) {
                    Intent intent1Service = new Intent(getApplication(), ServiceIntentGetData.class);
                    intent1Service.putExtra(BaseConstant.API_PAGE, BaseConstant.ACTION_SERVICE_GET_FIND_PRODUCT);
                    intent1Service.putExtra(API_GET_KEY, intent.getStringExtra(API_GET_KEY));
                    startService(intent1Service);
                }
                if (intent.getAction().equals(BaseConstant.ACTION_SERVICE_GET_PROCESSING_ID)) {
                    Intent intent1Service = new Intent(getApplication(), ServiceIntentGetDataMore.class);
                    intent1Service.putExtra(BaseConstant.API_PAGE, BaseConstant.ACTION_SERVICE_GET_PROCESSING_ID);
                    intent1Service.putExtra(API_GET_KEY, PreferenceHelper.getInstance().getUserId());
                    startService(intent1Service);
                }
                if (intent.getAction().equals(BaseConstant.ACTION_SERVICE_GET_ORDERS)) {
                    Log.d(BaseConstant.TAG, "Start ServiceHelper:BaseConstant.ACTION_SERVICE_GET_ORDERS..");
                    Intent intent1Service = new Intent(getApplication(), ServiceIntentGetData.class);
                    intent1Service.putExtra(BaseConstant.API_PAGE, BaseConstant.ACTION_SERVICE_GET_ORDERS);
                    intent1Service.putExtra(API_GET_KEY, PreferenceHelper.getInstance().getUserId());
                    startService(intent1Service);
                }
                if (intent.getAction().equals(BaseConstant.ACTION_SERVICE_GET_BALANCE)) {
                    Log.d(BaseConstant.TAG, "Start ServiceHelper:BaseConstant.ACTION_SERVICE_GET_BALANCE..");
                    Intent intent1Service = new Intent(getApplication(), ServiceIntentGetData.class);
                    intent1Service.putExtra(BaseConstant.API_PAGE, BaseConstant.ACTION_SERVICE_GET_BALANCE);
                    intent1Service.putExtra(API_GET_KEY, PreferenceHelper.getInstance().getUserId());
                    startService(intent1Service);
                }
                if (intent.getAction().equals(BaseConstant.ACTION_SERVICE_GET_CABINET)) {
                    Log.d(BaseConstant.TAG, "Start ServiceHelper:BaseConstant.ACTION_SERVICE_GET_CABINET..");
                    Intent intent1Service = new Intent(getApplication(), ServiceIntentGetData.class);
                    intent1Service.putExtra(BaseConstant.API_PAGE, BaseConstant.ACTION_SERVICE_GET_CABINET);
                    startService(intent1Service);
                }
                if (intent.getAction().equals(BaseConstant.ACTION_SERVICE_GET_CATEGORY_LIST)) {
                    Log.d(BaseConstant.TAG, "Start ServiceHelper:BaseConstant.ACTION_SERVICE_GET_CATEGORY_LIST..");
                    Intent intent1Service = new Intent(getApplication(), ServiceIntentGetData.class);
                    intent1Service.putExtra(BaseConstant.API_PAGE, BaseConstant.ACTION_SERVICE_GET_CATEGORY_LIST);
                    intent1Service.putExtra(API_GET_KEY, BaseConstant.GET_CATEGORY);
                    startService(intent1Service);
                }
                if (intent.getAction().equals(ACTION_SERVICE_GET_PRODUCT_LIST)) {
                    Log.d(BaseConstant.TAG, "Start ServiceHelper:BaseConstant.ACTION_SERVICE_GET_PRODUCT_LIST..");
                    int key_id = intent.getIntExtra(API_GET_KEY, 1);
                    Log.d(BaseConstant.TAG, "Start ServiceHelper:BaseConstant.ACTION_SERVICE_GET_PRODUCT_LIST:key_id :" + key_id);
                    Intent intent1Service = new Intent(getApplication(), ServiceIntentGetData.class);
                    intent1Service.putExtra(BaseConstant.API_PAGE, BaseConstant.ACTION_SERVICE_GET_PRODUCT_LIST);
                    intent1Service.putExtra(API_GET_KEY, key_id);
                    startService(intent1Service);
                }
                if (intent.getAction().equals(ACTION_SERVICE_GET_SUBCATEGORY_LIST)) {
                    Log.d(BaseConstant.TAG, "Start ServiceHelper:BaseConstant.ACTION_SERVICE_GET_SUBCATEGORY_LIST..");
                    int key_id = intent.getIntExtra(API_GET_KEY, 1);
                    Log.d(BaseConstant.TAG, "Start ServiceHelper:BaseConstant.ACTION_SERVICE_GET_SUBCATEGORY_LIST:key_id :" + key_id);
                    Intent intent1Service = new Intent(getApplication(), ServiceIntentGetData.class);
                    intent1Service.putExtra(BaseConstant.API_PAGE, BaseConstant.ACTION_SERVICE_GET_SUBCATEGORY_LIST);
                    intent1Service.putExtra(API_GET_KEY, key_id);
                    startService(intent1Service);
                }
                if (intent.getAction().equals(ACTION_SERVICE_GET_PRODUCT_DETAIL)) {
                    Log.d(BaseConstant.TAG, "Start ServiceHelper:BaseConstant.ACTION_SERVICE_GET_PRODUCT_DETAIL..");
                    int key_id = intent.getIntExtra(API_GET_KEY, 2);
                    Log.d(BaseConstant.TAG, "Start ServiceHelper:BaseConstant.ACTION_SERVICE_GET_PRODUCT_LIST:key_id :" + key_id);
                    Intent intent1Service = new Intent(getApplication(), ServiceIntentGetData.class);
                    intent1Service.putExtra(BaseConstant.API_PAGE, BaseConstant.ACTION_SERVICE_GET_PRODUCT_DETAIL);
                    intent1Service.putExtra(API_GET_KEY, key_id);
                    startService(intent1Service);
                }
                if (intent.getAction().equals(ACTION_SERVICE_GET_PRODUCT_DETAIL_ID)) {
                    int key_id = intent.getIntExtra(API_GET_KEY, 2);
                    Intent intent1Service = new Intent(getApplication(), ServiceIntentGetData.class);
                    intent1Service.putExtra(BaseConstant.API_PAGE, BaseConstant.ACTION_SERVICE_GET_PRODUCT_DETAIL_ID);
                    intent1Service.putExtra(API_GET_KEY, key_id);
                    startService(intent1Service);
                }
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        Log.d(BaseConstant.TAG, "ServiceHelper: destroy..");
        super.onDestroy();
    }
}

