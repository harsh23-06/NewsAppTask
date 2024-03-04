package com.example.newsapptask.viewModel
/*
 * NewsViewModel is a ViewModel class responsible for handling the data
 * related to news articles. It fetches news articles from a remote server
 * using FetchNewsTask and provides LiveData objects to observe the news
 * list and error messages.
 *
 * The fetchNews function initiates the process of fetching news articles
 * asynchronously. It uses FetchNewsTask to perform the network operation.
 * When news articles are successfully fetched, the parsed data is stored
 * in the _newsList LiveData object. If an error occurs during the fetch
 * operation, an error message is stored in the _error LiveData object.
 *
 * The parseNewsJson function is a helper method used to parse the JSON
 * string containing news data. It extracts relevant information from the
 * JSON object and creates NewsArticle objects, which are then added to
 * the list of news articles.
 */
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapptask.models.NewsArticle
import com.example.newsapptask.models.Source
import com.example.newsapptask.network.FetchNewsTask
import org.json.JSONObject

class NewsViewModel : ViewModel() {

    private val _newsList = MutableLiveData<List<NewsArticle>>()
    val newsList: LiveData<List<NewsArticle>> = _newsList

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun fetchNews() {
        FetchNewsTask(object : FetchNewsTask.OnNewsFetchedListener {
            override fun onNewsFetched(newsJson: String) {
                val newsList = parseNewsJson(newsJson)
                _newsList.value = newsList
            }

            override fun onNewsFetchError(errorMessage: String) {
                _error.value = errorMessage
            }
        }).execute()
    }

    private fun parseNewsJson(jsonString: String): List<NewsArticle> {
        val newsList = mutableListOf<NewsArticle>()
        try {
            val jsonObject = JSONObject(jsonString)
            val articlesArray = jsonObject.getJSONArray("articles")
            for (i in 0 until articlesArray.length()) {
                val articleObject = articlesArray.getJSONObject(i)
                val sourceObject = articleObject.getJSONObject("source")
                val source = Source(
                    sourceObject.getString("id"),
                    sourceObject.getString("name")
                )
                val article = NewsArticle(
                    source,
                    articleObject.optString("author"),
                    articleObject.optString("title"),
                    articleObject.optString("description"),
                    articleObject.optString("url"),
                    articleObject.optString("urlToImage"),
                    articleObject.optString("publishedAt"),
                    articleObject.optString("content")
                )
                newsList.add(article)
            }
        } catch (e: Exception) {
            _error.value = "Error parsing news data."
        }
        return newsList
    }
}
