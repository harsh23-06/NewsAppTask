
/**
 * NewsArticle is a data class representing a single news article.
 * It contains various properties such as source, author, title, description, etc.
 * All properties are nullable strings except for source, which is an instance of the Source class.
 */

package com.example.newsapptask.models

import java.io.Serializable

data class NewsArticle(
    val source: Source,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?
): Serializable