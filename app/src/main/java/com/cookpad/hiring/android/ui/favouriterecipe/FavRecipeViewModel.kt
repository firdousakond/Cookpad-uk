package com.cookpad.hiring.android.ui.favouriterecipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cookpad.hiring.android.data.Resource
import com.cookpad.hiring.android.data.repository.FavCollectionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavRecipeViewModel @Inject constructor(private val repository: FavCollectionRepository) :
    ViewModel() {

    private val _favViewState = MutableStateFlow<Resource>(
        Resource.Success(
            emptyList()
        )
    )

    val favViewState: StateFlow<Resource> = _favViewState

    fun setFavouriteRecipe(id: Int, isFavourite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.setFavouriteRecipe(id, isFavourite)
        }
    }

    fun getFavouriteRecipe() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                repository.getFavouriteRecipe()
            }.onFailure {
                _favViewState.value = Resource.Error
            }.onSuccess { collection ->
                _favViewState.value = Resource.Success(collection)
            }
        }
    }
}
