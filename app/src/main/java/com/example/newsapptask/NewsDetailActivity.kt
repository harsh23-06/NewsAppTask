package com.example.newsapptask
/**
 * This activity displays the details of a news article including the title, author, content,
 * and an image. It also shows a button to open the full article in a web browser. Additionally,
 * it includes a horizontal RecyclerView to display a preview of other news articles, excluding
 * the currently viewed article.
 */
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.newsapptask.R
import com.example.newsapptask.adapter.NewsAdapterRV
import com.example.newsapptask.databinding.ActivityNewsDetailBinding
import com.example.newsapptask.models.NewsArticle
import com.example.newsapptask.adapter.NewsPreviewAdapter
import com.example.newsapptask.viewModel.NewsViewModel

class NewsDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewsDetailBinding
    private lateinit var newsPreviewAdapter: NewsPreviewAdapter
    private lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val newsItem = intent.getSerializableExtra("NewsItem") as NewsArticle

        binding.newsContent.text = newsItem.description
        Glide.with(this).load(newsItem.urlToImage).placeholder(R.drawable.news_report)
            .error(R.drawable.news_report).into(binding.newsImage)
        binding.authorName.text = newsItem.author
        binding.newsTitle.text = newsItem.title
        binding.tvKnowMore.setOnClickListener{
            val intentToBrowser = Intent(Intent.ACTION_VIEW, Uri.parse(newsItem.url))
            startActivity(intentToBrowser)
        }
        binding.newsTitle.setOnClickListener{
            val intentToBrowser = Intent(Intent.ACTION_VIEW, Uri.parse(newsItem.url))
            startActivity(intentToBrowser)
        }
        viewModel = ViewModelProvider(this).get(NewsViewModel::class.java)

        // Set up RecyclerView adapter
        setupRecyclerView()
        observeNewsList()
        fetchNews()
    }

    private fun setupRecyclerView() {
        newsPreviewAdapter = NewsPreviewAdapter(emptyList(), this)
        binding.horizontalRecyclerView.apply {
            adapter = this@NewsDetailActivity.newsPreviewAdapter
        }
    }

    private fun observeNewsList() {
        viewModel.newsList.observe(this, Observer { newsList ->
            if (!newsList.isNullOrEmpty()) {
                // Get the current news item
                val currentNewsItem = intent.getSerializableExtra("NewsItem") as NewsArticle

                // Filter out the current news item
                val filteredNewsList = newsList.filter { it != currentNewsItem }

                // Take the first 5 news articles
                val newsItems = filteredNewsList.take(5)

                // Update the RecyclerView adapter with the filtered list
                newsPreviewAdapter.updateData(newsItems)
            }
        })

        viewModel.error.observe(this, Observer { errorMessage ->
            // Handle error
        })
    }

    private fun fetchNews() {
        viewModel.fetchNews()
    }
}

