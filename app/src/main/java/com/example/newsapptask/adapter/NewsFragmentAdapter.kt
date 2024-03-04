
/**
 * NewsFragmentAdapter is a RecyclerView adapter responsible for displaying news articles in a horizontal or vertical layout.
 *
 * @param newsList The list of news articles to be displayed.
 * @param context The context of the activity or fragment.
 * @param isHorizontal A boolean indicating whether the adapter is used for horizontal or vertical layout.
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

class NewsFragmentAdapter(private var newsList: List<NewsArticle>,private val context: Context,private val isHorizontal:Boolean) :
    RecyclerView.Adapter<NewsFragmentAdapter.NewsViewHolder>() {
    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val headImage: ImageView = itemView.findViewById(R.id.headImage)
        val headText: TextView = itemView.findViewById(R.id.headTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val layoutResId =
            if (isHorizontal) R.layout.horizontal_item_layout else R.layout.vertical_item_layout
        val view = LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)

        return NewsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }
    fun updateData(newList: List<NewsArticle>) {
        newsList = newList
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val newsItem = newsList[position]
        Glide.with(context).load(newsItem.urlToImage).placeholder(R.drawable.news_report)
            .error(R.drawable.news_report).into(holder.headImage)


        holder.headText.text =if(!newsItem.description.isNullOrEmpty()) newsItem.description else "Sorry there is no data available for this news."
        holder.itemView.setOnClickListener {
            val intent = Intent(context, NewsDetailActivity::class.java)
            intent.putExtra("NewsItem", newsItem)
            context.startActivity(intent)
        }
    }
}
