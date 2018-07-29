package app.news.mc.com.newsapp.ui.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import app.news.mc.com.newsapp.R
import app.news.mc.com.newsapp.data.model.Article
import app.news.mc.com.newsapp.data.repository.NewsApiService
import app.news.mc.com.newsapp.ui.adapter.NewsListAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.news_list_activity.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class NewsListActivity : AppCompatActivity() {

    private lateinit var newsListView: RecyclerView

    private val newsApiServe by lazy {

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val retrofit = Retrofit.Builder()
                .baseUrl("https://newsapi.org/")
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        retrofit.create(NewsApiService::class.java)

    }

    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.news_list_activity)
        initViews()
    }

    private fun initViews() {

        btn_search.setOnClickListener{
            if (edit_search.text.toString().isNotEmpty()) {
                search(edit_search.text.toString())
            }}

        newsListView = recycler_list
        newsListView.layoutManager = LinearLayoutManager(this)

    }

    private fun updateList(articles: List<Article>) {
        newsListView.adapter = NewsListAdapter(articles)
    }

    private fun search(search: String) {
        disposable =
                newsApiServe.getArticles(query=search)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { result -> updateList(result.articles)},
                                { error -> Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show() }
                        )
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }
}


