package com.cookpad.hiring.android.ui.recipecollection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cookpad.hiring.android.data.Resource
import com.cookpad.hiring.android.domain.usecase.CollectionListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionListViewModel @Inject constructor(private val useCase: CollectionListUseCase) :
    ViewModel() {

    private val _viewState = MutableStateFlow<Resource>(Resource.Success(emptyList()))
    val viewState: StateFlow<Resource> = _viewState

    init {
        loadCollections()
    }

    fun refresh() {
        loadCollections()
    }

    private fun loadCollections() {
        _viewState.value = Resource.Loading

        viewModelScope.launch(Dispatchers.IO){
            runCatching {
                useCase.getCollectionList()
            }.onFailure {
                _viewState.value = Resource.Error
            }.onSuccess { collection ->
                _viewState.value = Resource.Success(collection)
            }
        }
    }
}
