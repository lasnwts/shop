package nwts.ru.autoshop.network;

import android.content.Context;
import android.text.TextUtils;

import nwts.ru.autoshop.TODOApplication;
import nwts.ru.autoshop.models.authority.UserModel;
import nwts.ru.autoshop.network.request.ShopAPI;
import nwts.ru.autoshop.setting.PreferenceHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by пользователь on 15.05.2017.
 */

public class ValidateToken {

    private static ValidateToken INSTANCE = null;
    private Context mContext;

    boolean validateResult;

    public ValidateToken(Context context) {
        this.mContext = context;
        this.validateResult =  false;
    }

    public static ValidateToken getInstance(){
        if (INSTANCE == null) {
            INSTANCE = new ValidateToken(TODOApplication.getInstance());
        }
        return  INSTANCE;
    }

    public boolean getValidateToken(){

        if (TextUtils.isEmpty(PreferenceHelper.getInstance().getAuthToken())) {
            return false;
        }

        ShopAPI mShopAPI = ShopAPI.retrofit.create(ShopAPI.class);
        Call<UserModel> mCall = mShopAPI.getValidateToken();
        mCall.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.isSuccessful()){
                    if (response.code() == 200 && response.body() != null && !response.body().getError()) {
                        if (!response.body().getApikey().equals(PreferenceHelper.getInstance().getAuthToken())){
                            PreferenceHelper.getInstance().putAuthToken("");
                        } else {
                            validateResult = true;
                            TODOApplication.getInstance().setValidateToken(true);
                            PreferenceHelper.getInstance().putUserId(response.body().getId());
                            PreferenceHelper.getInstance().putEmail(response.body().getUsername());
                            PreferenceHelper.getInstance().putUserName(response.body().getName());
                        }
                    } else {
                        PreferenceHelper.getInstance().putAuthToken("");
                    }
                } else {
                    PreferenceHelper.getInstance().putAuthToken("");
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable throwable) {
                // log what non network
            }
        });

        return validateResult;
    }

}
