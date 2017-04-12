package nwts.ru.autoshop.network;

import java.io.IOException;

import nwts.ru.autoshop.setting.PreferenceHelper;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by пользователь on 10.04.2017.
 */

public class HeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        PreferenceHelper pm = PreferenceHelper.getInstance();;

        Request original = chain.request();

        Request.Builder requestBuilder = original.newBuilder()
                .header("X-Access-Token", pm.getAuthToken())
                .header("Request-User-Id", pm.getUserId())
                .header("User-Agent", "autoshop")
                .header("Cache-Control", "max-age=" + (60 * 60 * 24));

        Request request = requestBuilder.build();

        return chain.proceed(request);

    }
}
