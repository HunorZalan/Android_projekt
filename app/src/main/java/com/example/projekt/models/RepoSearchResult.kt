package com.example.projekt.models

import java.lang.Exception

// RepoSearchResult from a search, which contains List<Repo> holding query data, and a String of network error state.
sealed class RepoSearchResult {
    data class Success(val data: List<Restaurant>) : RepoSearchResult()
    data class Error(val error: Exception) : RepoSearchResult()
}
