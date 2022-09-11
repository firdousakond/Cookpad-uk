package com.cookpad.hiring.android.ui.favouriterecipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.cookpad.hiring.android.R
import com.cookpad.hiring.android.data.Resource
import com.cookpad.hiring.android.data.entities.Collection
import com.cookpad.hiring.android.databinding.FragmentFavListBinding
import com.cookpad.hiring.android.util.hide
import com.cookpad.hiring.android.util.show
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavListFragment : Fragment() {

    private lateinit var favRecipeAdapter: FavRecipeAdapter
    private val favViewModel: FavRecipeViewModel by viewModels()

    private lateinit var binding: FragmentFavListBinding
    private var favRecipes: MutableList<Collection>? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_fav_list, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        setupObserver()
        favViewModel.getFavouriteRecipe()
    }

    private fun setupObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                favViewModel.favViewState.collectLatest { viewState ->
                    when (viewState) {
                        is Resource.Success -> {
                            binding.loadingCircularProgressIndicator.visibility = View.GONE
                            favRecipes = viewState.collection.toMutableList()
                            showHideEmptyList()
                        }
                        Resource.Error -> {
                            binding.loadingCircularProgressIndicator.visibility = View.GONE

                            Snackbar.make(
                                binding.root,
                                R.string.generic_error_message,
                                Snackbar.LENGTH_INDEFINITE
                            )
                                .setAction(R.string.retry) { favViewModel.getFavouriteRecipe() }
                                .show()
                        }
                        Resource.Loading -> {
                            binding.loadingCircularProgressIndicator.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    private fun setUpRecyclerView() {
        binding.rvFavList.apply {
            favRecipeAdapter = FavRecipeAdapter { collection ->
                favViewModel.setFavouriteRecipe(collection.id, collection.favourite.not())
                favRecipes?.remove(collection)
                favRecipeAdapter.refreshList(favRecipes)
                showHideEmptyList()
            }
            adapter = favRecipeAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun showHideEmptyList() {
        if(!favRecipes.isNullOrEmpty()) {
            favRecipeAdapter.submitList(favRecipes)
            binding.tvNoRecipe.hide()
        }else{
            binding.tvNoRecipe.show()
        }
    }

}