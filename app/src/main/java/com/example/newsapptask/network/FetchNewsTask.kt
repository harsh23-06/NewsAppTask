/**
 * FetchNewsTask is an AsyncTask class for fetching news data from a remote server.
 * It performs the network operation asynchronously and provides callbacks
 * to notify the caller about the result.
 *
 * The doInBackground function executes the network request, reads the response,
 * and returns the JSON string containing news data.
 *
 * The onPostExecute function checks if the result is not null and notifies
 * the listener about the fetched news or any error that occurred.
 *
 * The OnNewsFetchedListener interface defines callback methods for successful
 * fetch (onNewsFetched) and fetch errors (onNewsFetchError).
 */

package com.example.newsapptask.network

import android.os.AsyncTask
import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class FetchNewsTask(private val listener: OnNewsFetchedListener) : AsyncTask<Void, Void, String>() {

    override fun doInBackground(vararg params: Void?): String {
        val apiUrl = "https://candidate-test-data-moengage.s3.amazonaws.com/Android/news-api-feed/staticResponse.json"
        val url = URL(apiUrl)
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        connection.connectTimeout = 10000
        connection.readTimeout = 10000

        val responseCode = connection.responseCode
        if (responseCode == HttpURLConnection.HTTP_OK) {
            val inputStream = connection.inputStream
            val bufferedReader = BufferedReader(InputStreamReader(inputStream))
            val response = StringBuilder()

            var line: String?
            while (bufferedReader.readLine().also { line = it } != null) {
                response.append(line)
            }

            bufferedReader.close()
            inputStream.close()
            Log.d("Harsh",response.toString())
            return response.toString()
        } else {
            throw Exception("Failed to fetch news. Response code: $responseCode")
        }
    }


    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        if (result != null) {
            listener.onNewsFetched(result)
        } else {
            listener.onNewsFetchError("Failed to fetch news data.")
        }
    }

    interface OnNewsFetchedListener {
        fun onNewsFetched(newsJson: String)
        fun onNewsFetchError(errorMessage: String)
    }
}
