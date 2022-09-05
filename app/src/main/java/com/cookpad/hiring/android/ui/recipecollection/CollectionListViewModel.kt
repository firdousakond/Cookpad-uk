package com.cookpad.hiring.android.ui.recipecollection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cookpad.hiring.android.data.CollectionListRepository
import com.cookpad.hiring.android.data.entities.Collection
import com.cookpad.hiring.android.ui.recipecollection.CollectionListViewState.Success
import com.cookpad.hiring.android.ui.recipecollection.CollectionListViewState.Error
import com.cookpad.hiring.android.ui.recipecollection.CollectionListViewState.Loading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionListViewModel @Inject constructor(private val repository: CollectionListRepository) :
    ViewModel() {

    private val _viewState = MutableStateFlow<CollectionListViewState>(Success(emptyList()))
    val viewState: StateFlow<CollectionListViewState> = _viewState

    init {
        loadCollections()
    }

    fun refresh() {
        loadCollections()
    }

    private fun loadCollections() {
        _viewState.value = Loading

        viewModelScope.launch {
            runCatching {
                repository.getCollectionList()
            }.onFailure {
                _viewState.value = Error
            }.onSuccess { collection ->
                _viewState.value = Success(collection)
            }
        }
    }
}

sealed class CollectionListViewState {
    object Loading : CollectionListViewState()
    object Error : CollectionListViewState()
    data class Success(val collection: List<Collection>) : CollectionListViewState()
}