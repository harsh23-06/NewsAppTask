/**
 * NewsAdapterRV is a RecyclerView adapter responsible for displaying news articles in a list format.
 * It also provides filtering functionality based on title, description, and author of the news articles.
 *
 * @param newsList The list of news articles to be displayed.
 * @param context The context of the activity or fragment.
 */


package com.example.newsapptask.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
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

class NewsAdapterRV(private var newsList: List<NewsArticle>, private val context: Context) : RecyclerView.Adapter<NewsAdapterRV.NewsViewHolder>() {

    private var filteredNewsList: List<NewsArticle> = newsList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val newsArticle = filteredNewsList[position]
        holder.bind(newsArticle)
    }

    override fun getItemCount(): Int {
        return filteredNewsList.size
    }

    fun updateData(newList: List<NewsArticle>) {
        newsList = newList
        filteredNewsList = newList
        notifyDataSetChanged()
    }

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.textTitle)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.textDescription)
        private val authorTextView: TextView = itemView.findViewById(R.id.textAuthor)
        private val newsImageView: ImageView = itemView.findViewById(R.id.imageNews)

        fun bind(newsArticle: NewsArticle) {
            // Check if title is not null or blank
            if (!newsArticle.title.isNullOrBlank()) {
                titleTextView.text = newsArticle.title
            } else {
                titleTextView.text = "--"
            }

            // Check if description is not null or blank
            if (!newsArticle.description.isNullOrBlank()) {
                descriptionTextView.text = newsArticle.description
            } else {
                descriptionTextView.text = "--"
            }

            // Check if author is not null or blank
            //handled for no data but still showing null
            if (!newsArticle.author.isNullOrBlank()) {
                authorTextView.text = newsArticle.author
            } else {
                authorTextView.text = "--"
            }

            // Load image if URL is not null or blank

                Glide.with(itemView)
                    .load(newsArticle.urlToImage)
                    .placeholder(R.drawable.news_report)
                    .error(R.drawable.news_report)
                    .into(newsImageView)


            itemView.setOnClickListener {
                // Handle click event
                val intent = Intent(context, NewsDetailActivity::class.java)
                intent.putExtra("NewsItem", newsArticle)
                context.startActivity(intent)
            }
        }
    }

    fun filter(text: String) {
        filteredNewsList = if (text.isEmpty()) {
            newsList
        } else {
            newsList.filter { newsArticle ->
                newsArticle.title!!.toLowerCase().contains(text.toLowerCase()) ||
                        newsArticle.description!!.toLowerCase().contains(text.toLowerCase())||
                        newsArticle.author!!.toLowerCase().contains(text.toLowerCase())
            }
        }
        notifyDataSetChanged()
    }
}
