package com.cookpad.hiring.android.ui.favouriterecipe

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.cookpad.hiring.android.R
import com.cookpad.hiring.android.data.Resource
import com.cookpad.hiring.android.databinding.FragmentFavListBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavListFragment : Fragment(R.layout.fragment_fav_list) {

    private lateinit var favRecipeAdapter: FavRecipeAdapter
    private val favViewModel: FavRecipeViewModel by viewModels()

    private var _binding: FragmentFavListBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFavListBinding.bind(view)
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
                            favRecipeAdapter.submitList(viewState.collection)
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
            }
            adapter = favRecipeAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}