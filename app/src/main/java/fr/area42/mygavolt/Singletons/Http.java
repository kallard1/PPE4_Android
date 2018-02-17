package fr.area42.mygavolt.Singletons;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by allardk on 17/02/2018.
 */

public final class Http {

    public static final Http ourInstance = new Http();

    public static Http getInstance() {
        return ourInstance;
    }

    /**
     * @param url
     * @return
     * @throws IOException
     */
    public static String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        OkHttpClient httpClient = new OkHttpClient();

        Response response = httpClient.newCall(request).execute();

        return response.body().string();
    }
}
