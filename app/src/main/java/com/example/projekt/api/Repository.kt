package com.example.projekt.api

import android.util.Log
import com.example.projekt.api.Service
import com.example.projekt.models.RepoSearchResult
import com.example.projekt.models.Restaurant
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

// Repository class that works with local and remote data sources.
@ExperimentalCoroutinesApi
class Repository(private val service: Service) {

    // keep the list of all results received
    private val inMemoryCache = mutableListOf<Restaurant>()
    // keep channel of results. The channel allows us to broadcast updates so the subscriber will have the latest data
    private val searchResults = ConflatedBroadcastChannel<RepoSearchResult>()
    // keep the last requested page. When the request is successful, increment the page number.
    private var lastRequestedPage = STARTING_PAGE_INDEX
    // avoid triggering multiple requests in the same time
    private var isRequestInProgress = false

    //  Search repositories whose names match the query, exposed as a stream of data that will emit every time we get more data from the network.
    suspend fun getSearchResultStream(query: String): Flow<RepoSearchResult> {
        Log.d("Repository", "New query: $query")
        lastRequestedPage = 1
        inMemoryCache.clear()
        requestAndSaveData(query)

        return searchResults.asFlow()
    }

    suspend fun requestMore(query: String) {
        if (isRequestInProgress)
            return
        val successful = requestAndSaveData(query)
        if (successful) {
            lastRequestedPage++
        }
    }

    private suspend fun requestAndSaveData(query: String): Boolean {
        isRequestInProgress = true
        var successful = false

        try {
            val response = service.searchRepos(query, lastRequestedPage, NETWORK_PAGE_SIZE)
            Log.d("Repository", "response $response")
            val repos = response.items ?: emptyList()
            inMemoryCache.addAll(repos)
            searchResults.offer(RepoSearchResult.Success(repos))
            successful = true
        }
        catch (exception: IOException) {
            searchResults.offer(RepoSearchResult.Error(exception))
        }
        catch (exception: HttpException) {
            searchResults.offer(RepoSearchResult.Error(exception))
        }
        isRequestInProgress = false
        return successful
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 20
    }
}
