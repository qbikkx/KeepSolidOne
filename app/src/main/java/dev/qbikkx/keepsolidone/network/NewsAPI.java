package dev.qbikkx.keepsolidone.network;

import java.util.Map;

import dev.qbikkx.keepsolidone.models.NewsResponce;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface NewsAPI {
    String BASE_URL = "https://newsapi.org/";
    String API_KEY = "b6b3c53a06714ef7a7018341d896aca3";
    String SOURCE = "the-times-of-india";

    @GET("v1/articles")
    Call<NewsResponce> getNews(@QueryMap Map<String, String> options);
}
