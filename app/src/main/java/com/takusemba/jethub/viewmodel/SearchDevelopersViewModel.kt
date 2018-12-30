package com.takusemba.jethub.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.takusemba.jethub.model.SimpleDeveloper
import com.takusemba.jethub.repository.SearchDevelopersRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class SearchDevelopersViewModel @Inject constructor(
  private val searchDevelopersRepository: SearchDevelopersRepository
) : ViewModel(), CoroutineScope {

  override val coroutineContext: CoroutineContext = Job() + Dispatchers.Main

  private val searchedUsersResult = MutableLiveData<List<SimpleDeveloper>>()

  val searchedUsers: LiveData<List<SimpleDeveloper>> = searchedUsersResult

  init {
    launch {
      val repositories = searchDevelopersRepository.searchUsers("")
      searchedUsersResult.value = repositories
    }
  }

  fun search(query: String) {
    launch {
      val users = searchDevelopersRepository.searchUsers(query)
      searchedUsersResult.value = users
    }
  }

  override fun onCleared() {
    super.onCleared()
    coroutineContext.cancel()
  }
}