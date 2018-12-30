package com.takusemba.jethub.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.takusemba.jethub.model.Repository
import com.takusemba.jethub.repository.SearchReposRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class SearchReposViewModel @Inject constructor(
  private val searchReposRepository: SearchReposRepository
) : ViewModel(), CoroutineScope {

  override val coroutineContext: CoroutineContext = Job() + Dispatchers.Main

  private val searchedReposResult = MutableLiveData<List<Repository>>()

  val searchedRepos: LiveData<List<Repository>> = searchedReposResult

  init {
    launch {
      val repositories = searchReposRepository.searchRepos("")
      searchedReposResult.value = repositories
    }
  }

  fun search(query: String) {
    launch {
      val repositories = searchReposRepository.searchRepos(query)
      searchedReposResult.value = repositories
    }
  }

  override fun onCleared() {
    super.onCleared()
    coroutineContext.cancel()
  }
}