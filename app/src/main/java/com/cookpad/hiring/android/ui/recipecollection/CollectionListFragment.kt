package com.cookpad.hiring.android.ui.recipecollection

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.cookpad.hiring.android.R
import com.cookpad.hiring.android.data.Resource
import com.cookpad.hiring.android.databinding.FragmentCollectionListBinding
import com.cookpad.hiring.android.ui.favouriterecipe.FavRecipeViewModel
import com.cookpad.hiring.android.util.hide
import com.cookpad.hiring.android.util.show
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CollectionListFragment : Fragment() {

    private lateinit var collectionListAdapter: CollectionListAdapter
    private val viewModel: CollectionListViewModel by viewModels()
    private val favViewModel: FavRecipeViewModel by viewModels()

    private lateinit var binding: FragmentCollectionListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_collection_list,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
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
                        is Resource.Success -> {
                            binding.loadingCircularProgressIndicator.visibility = View.GONE
                            if (viewState.collection.isNotEmpty()) {
                                collectionListAdapter.submitList(viewState.collection)
                                binding.tvNoRecipe.hide()
                            } else {
                                binding.tvNoRecipe.show()
                            }
                        }
                        Resource.Error -> {
                            binding.loadingCircularProgressIndicator.visibility = View.GONE

                            Snackbar.make(
                                binding.root,
                                R.string.generic_error_message,
                                Snackbar.LENGTH_INDEFINITE
                            )
                                .setAction(R.string.retry) { viewModel.refresh() }
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
        binding.collectionList.apply {
            collectionListAdapter = CollectionListAdapter { collection ->
                favViewModel.setFavouriteRecipe(collection.id, collection.favourite.not())
            }
            adapter = collectionListAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.favourite) {
            val action =
                CollectionListFragmentDirections.actionCollectionListFragmentToFavListFragment()
            findNavController().navigate(action)
            return true
        }
        return NavigationUI.onNavDestinationSelected(
            item,
            findNavController()
        ) || super.onOptionsItemSelected(item)
    }
}