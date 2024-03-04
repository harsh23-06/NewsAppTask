
/**
 * NewsPreviewAdapter is a RecyclerView adapter responsible for displaying a preview of news articles.
 * It binds the news article data to the corresponding views in the item layout.
 * When a news item is clicked, it opens the NewsDetailActivity to display the full details of the news article.
 *
 * @param newsItems The list of news articles to be displayed.
 * @param context The context of the activity or fragment.
 */

package com.example.newsapptask.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapptask.NewsDetailActivity
import com.example.newsapptask.R
import com.example.newsapptask.models.NewsArticle

class NewsPreviewAdapter(private var newsItems: List<NewsArticle>,private val context: Context) :
    RecyclerView.Adapter<NewsPreviewAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val newsPreviewImage: ImageView = itemView.findViewById(R.id.newsPreviewImage)
//        val newsPreviewTitle: TextView = itemView.findViewById(R.id.newsPreviewTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_detail_item, parent, false)
        return ViewHolder(view)
    }
    fun updateData(newList: List<NewsArticle>) {
        newsItems = newList
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentItem = newsItems[position]
        Glide.with(context).load(currentItem.urlToImage).placeholder(R.drawable.news_report)
            .error(R.drawable.news_report).into(holder.newsPreviewImage)
//        holder.newsPreviewTitle.text = currentItem.title
        holder.itemView.setOnClickListener {
            val intent = Intent(context, NewsDetailActivity::class.java)
            intent.putExtra("NewsItem", currentItem)
            context.startActivity(intent)

        }
    }

    override fun getItemCount(): Int {
        return newsItems.size
    }
}
