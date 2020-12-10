package com.example.projekt

import androidx.lifecycle.ViewModelProvider
import com.example.projekt.api.Repository
import com.example.projekt.api.Service
import com.example.projekt.viewmodels.RestaurantViewModel

// Class that handles object creation.
// Like this, objects can be passed as parameters in the constructors and then replaced for  testing, where needed.

object Injection {

    // Creates an instance of [GithubRepository] based on the [GithubService] and a  [GithubLocalCache]
    private fun provideRepository(): Repository {
        return Repository(Service.create())
    }

    // Provides the [ViewModelProvider.Factory] that is then used to get a reference to [ViewModel] objects.
    fun provideViewModelFactory(): ViewModelProvider.Factory {
        return RestaurantViewModel(provideRepository())
    }
}
