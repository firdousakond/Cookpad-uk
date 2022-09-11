package com.cookpad.hiring.android.data

import com.cookpad.hiring.android.data.entities.Collection

sealed class Resource {
    object Loading : Resource()
    object Error : Resource()
    data class Success(val collection: List<Collection>) : Resource()
}
