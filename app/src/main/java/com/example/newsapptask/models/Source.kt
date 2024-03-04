/**
 * Source is a data class representing the source of a news article.
 * It contains the id and name of the news source.
 * The id and name properties are nullable strings.
 */

package com.example.newsapptask.models

import java.io.Serializable

data class Source(
    val id: String?,
    val name: String?
): Serializable