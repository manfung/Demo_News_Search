package app.news.mc.com.newsapp.data.repository

import app.news.mc.com.newsapp.BuildConfig
import app.news.mc.com.newsapp.data.model.News
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NewsApiService {

    @GET("/v2/everything")
    fun getArticles(@Header("Authorization") api: String = BuildConfig.NEWS_API_KEY,
                    @Query("q") query: String,
                    @Query("language") language: String = "en",
                    @Query("sortby") sortBy: String = "publishedAt"): Observable<News>

}