package com.example.projekt.viewmodels

import androidx.lifecycle.*
import com.example.projekt.api.Repository
import com.example.projekt.models.RepoSearchResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// ViewModel for the [SearchRepositoriesActivity] screen. The ViewModel works with the [GithubRepository] to get the data.

class ListViewModel(private val repository: Repository) : ViewModel() {

    companion object {
        private const val VISIBLE_THRESHOLD = 3
    }

    private val queryLiveData = MutableLiveData<String>()
    val restaurantResult: LiveData<RepoSearchResult> = queryLiveData.switchMap { queryMap ->
        liveData {
            val restaurants = repository.getSearchResultStream(queryMap).asLiveData(Dispatchers.Main)
            emitSource(restaurants)
        }
    }

    // Search a repository based on a query string.
    fun searchRepo(queryString: String) {
        queryLiveData.postValue(queryString)
    }

    fun listScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int) {
        if (visibleItemCount + lastVisibleItemPosition + VISIBLE_THRESHOLD >= totalItemCount) {
            val immutableQuery = queryLiveData.value
            if (immutableQuery != null) {
                viewModelScope.launch {
                    repository.requestMore(immutableQuery)
                }
            }
        }
    }
}