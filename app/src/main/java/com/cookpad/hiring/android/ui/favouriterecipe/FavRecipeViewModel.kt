package com.cookpad.hiring.android.ui.favouriterecipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cookpad.hiring.android.data.Resource
import com.cookpad.hiring.android.domain.usecase.FavouriteRecipeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavRecipeViewModel @Inject constructor(private val useCase: FavouriteRecipeUseCase) :
    ViewModel() {

    private val _favViewState = MutableStateFlow<Resource>(
        Resource.Success(
            emptyList()
        )
    )

    val favViewState: StateFlow<Resource> = _favViewState

    fun setFavouriteRecipe(id: Int, isFavourite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.setFavouriteRecipe(id, isFavourite)
        }
    }

    fun getFavouriteRecipe() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                useCase.getFavouriteRecipe()
            }.onFailure {
                _favViewState.value = Resource.Error
            }.onSuccess { collection ->
                _favViewState.value = Resource.Success(collection)
            }
        }
    }
}
