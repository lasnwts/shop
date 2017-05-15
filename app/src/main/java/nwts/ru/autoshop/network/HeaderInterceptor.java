package nwts.ru.autoshop.network;

import java.io.IOException;

import nwts.ru.autoshop.setting.PreferenceHelper;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by пользователь on 10.04.2017.
 *
 private Request addBasicAuthHeaders(Request request) {
 final String login = "your_login";
 final String password = "p@s$w0rd";
 String credential = Credentials.basic(login, password);
 return request.newBuilder().header("Authorization", credential).build();
 }
 */

public class HeaderInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        PreferenceHelper pm = PreferenceHelper.getInstance();
        String credential = Credentials.basic("token", PreferenceHelper.getInstance().getAuthToken());

        Request original = chain.request();

        Request.Builder requestBuilder = original.newBuilder()
//                .header("X-Access-Token", pm.getAuthToken())
//                .header("Request-User-Id", pm.getUserId())
                .header("Authorization", credential)
                .header("User-Agent", "android-autoshop")
                .header("Cache-Control", "max-age=" + (60 * 60 * 24));

        Request request = requestBuilder.build();

        return chain.proceed(request);

    }
}
