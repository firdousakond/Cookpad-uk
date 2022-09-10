package com.cookpad.hiring.android.ui.recipecollection

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.cookpad.hiring.android.R
import com.cookpad.hiring.android.databinding.FragmentCollectionListBinding
import com.cookpad.hiring.android.ui.favouriterecipe.FavRecipeViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CollectionListFragment : Fragment(R.layout.fragment_collection_list) {

    private lateinit var collectionListAdapter: CollectionListAdapter
    private val viewModel: CollectionListViewModel by viewModels()
    private val favViewModel: FavRecipeViewModel by viewModels()

    private var _binding: FragmentCollectionListBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentCollectionListBinding.bind(view)

        setUpRecyclerView()

        binding.swipeToRefresh.apply {
            setOnRefreshListener {
                isRefreshing = false
                viewModel.refresh()
            }
        }
        setupObserver()

    }

    private fun setupObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewState.collect { viewState ->
                    when (viewState) {
                        is CollectionListViewState.Success -> {
                            binding.loadingCircularProgressIndicator.visibility = View.GONE
                            collectionListAdapter.submitList(viewState.collection)
                        }
                        CollectionListViewState.Error -> {
                            binding.loadingCircularProgressIndicator.visibility = View.GONE

                            Snackbar.make(binding.root, R.string.generic_error_message, Snackbar.LENGTH_INDEFINITE)
                                .setAction(R.string.retry) { viewModel.refresh() }
                                .show()
                        }
                        CollectionListViewState.Loading -> {
                            binding.loadingCircularProgressIndicator.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    private fun setUpRecyclerView() {
        binding.collectionList.apply {
            collectionListAdapter = CollectionListAdapter{ collection->
                favViewModel.setFavouriteRecipe(collection.id,collection.favourite.not())
            }
            adapter = collectionListAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}