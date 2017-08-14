package dev.qbikkx.keepsolidone.network;

import dev.qbikkx.keepsolidone.models.NewsResponce;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * @author <a href="mailto:qbikkx@gmail.com">qbikkx</a>
 */
public interface NewsAPI {
    String BASE_URL = "https://newsapi.org/";
    String API_KEY = "b6b3c53a06714ef7a7018341d896aca3";
    String SOURCE = "the-times-of-india";

    @GET("v1/articles?source=" + SOURCE + "&apiKey=" + API_KEY)
    Call<NewsResponce> getNews(@Query("sortBy") String sortBy);
}
