package nwts.ru.autoshop.network;

import android.os.Build;
import android.support.compat.BuildConfig;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by пользователь on 12.05.2017.
 */

public class HeaderLoggingInterceptor {

    //Logs from retrofit
    HttpLoggingInterceptor loggingRetrofit = new HttpLoggingInterceptor();


    public HeaderLoggingInterceptor() {
//        if (BuildConfig.DEBUG){
//            this.loggingRetrofit.setLevel(HttpLoggingInterceptor.Level.BODY);
//        } else {
//            this.loggingRetrofit.setLevel(HttpLoggingInterceptor.Level.NONE);
//        }
        //Debug
        this.loggingRetrofit.setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    public HttpLoggingInterceptor getLoggingRetrofit() {
        return this.loggingRetrofit;
    }
}
