package app.news.mc.com.newsapp.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.news.mc.com.newsapp.R
import app.news.mc.com.newsapp.data.model.Article
import kotlinx.android.synthetic.main.news_list_item.view.*
import java.text.SimpleDateFormat

class NewsListAdapter(private val articles: List<Article>) :
        RecyclerView.Adapter<NewsListAdapter.NewsItemViewHolder>() {

    class NewsItemViewHolder(view: View ) : RecyclerView.ViewHolder(view) {

        fun bind(article: Article) {
            itemView.txt_title.text = article.title
            itemView.txt_description.text = article.description
            itemView.txt_published.text = SimpleDateFormat("dd/MM/yyy").format(article.publishedAt).toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): NewsListAdapter.NewsItemViewHolder {

        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.news_list_item, parent, false) as View

        return NewsItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NewsItemViewHolder, position: Int) {
        val article = articles[position]
        holder.bind(article)
    }

    override fun getItemCount() = articles.size


}
