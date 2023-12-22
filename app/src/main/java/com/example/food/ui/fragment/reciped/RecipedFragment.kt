package com.example.food.ui.fragment.reciped

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food.R
import com.example.food.databinding.FragmentRecipedBinding
import com.example.food.ui.adapter.adapterRecipe
import com.example.food.util.NetworkListener
import com.example.food.util.NetworkResult
import com.example.food.util.observeOnce
import com.example.food.viewModel.MainViewModel
import com.example.food.viewModel.recipesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class recipedFragment : Fragment(), SearchView.OnQueryTextListener {


    val TAG = "recipedFragment"
    val args by navArgs<recipedFragmentArgs>()

    val mAdapter by lazy { adapterRecipe() }

    private lateinit var mainViewModel: MainViewModel
    private lateinit var recipesViewModell: recipesViewModel
    lateinit var networkListener: NetworkListener

    private var _binding: FragmentRecipedBinding? = null
    private val binding get() = _binding!!


    /*
    Create View
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        _binding = FragmentRecipedBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.lifecycleOwner = this

        /*
        init viewModel
         */
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        recipesViewModell = ViewModelProvider(requireActivity()).get(recipesViewModel::class.java)

        setHasOptionsMenu(true)
        binding.mainViewModel = mainViewModel


        initRacy()
        requestApiData()


        // ERROR
        recipesViewModell.readBackOnline.observe(viewLifecycleOwner) { it ->

            recipesViewModell.backOnline = it

        }


        lifecycleScope.launch {
            networkListener = NetworkListener()
            networkListener.checkNetworkAvailability(requireContext())
                .collect { status ->
                    run {
                        Log.e("$TAG -> network listener", status.toString())
                        recipesViewModell.network = status
                        recipesViewModell.showNetworkStatus()
                        readDatabase()


                    }
                }
        }

        /*
        Button Floating
         */
        binding.btnFloating.setOnClickListener {
            if (recipesViewModell.network) {
                findNavController().navigate(R.id.action_recipedFragment_to_blankBottomSheet2)

            } else {
                recipesViewModell.showNetworkStatus()
            }
        }
        return view

    }

    /******************************************
    Search
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.recipes_menu, menu)
        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchApiData(query)
        }


        return true
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        return true

    }

    /*
        Search
         *********************************************************/

    /*
    Read Data from Database
     */
    private fun readDatabase() {
        lifecycleScope.launch {
            mainViewModel.readRecipes.observeOnce(viewLifecycleOwner) { data ->
                if (data.isNotEmpty() && !args.backToHome) {
                    mAdapter.setData(data[0].foodRecipe)
                    hideShimmer()
                } else {
                    requestApiData()
                }
            }
        }
    }

    fun searchApiData(search: String) {
        showShimmer()
        mainViewModel.searchRecipes(recipesViewModell.applySearchQuery(search))
        mainViewModel.searchResponse.observe(viewLifecycleOwner) { result ->
            when (result) {
                is NetworkResult.Success -> {
                    hideShimmer()
                    val foodRec = result.data
                    foodRec?.let { mAdapter.setData(it) }
                }

                is NetworkResult.Error -> {
                    hideShimmer()
                    loadDataFromCache()
                    Toast.makeText(requireContext(), result.message.toString(), Toast.LENGTH_SHORT)
                        .show()


                }

                is NetworkResult.Loading -> {
                    showShimmer()
                }
            }
        }
    }

    fun loadDataFromCache() {
        lifecycleScope.launch {
            mainViewModel.readRecipes.observeOnce(viewLifecycleOwner) { data ->
                if (data.isNotEmpty()) {
                    mAdapter.setData(data[0].foodRecipe)
                }
            }
        }
    }

    fun requestApiData() {
        mainViewModel.getRecipes(recipesViewModell.applyQueries())
        mainViewModel.recipesResponse.observe(viewLifecycleOwner) { res ->
            when (res) {
                is NetworkResult.Success -> {
                    hideShimmer()
                    res.data?.let { mAdapter.setData(it) }
                }

                is NetworkResult.Error -> {
                    hideShimmer()
                    loadDataFromCache()
                    Toast.makeText(requireContext(), res.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }

                is NetworkResult.Loading -> {
                    showShimmer()
                }
            }
        }
    }


    private fun showShimmer() {
        binding.shimmerRecyclerView.showShimmer()
    }

    private fun hideShimmer() {
        binding.shimmerRecyclerView.hideShimmer()
    }

    private fun initRacy() {
        binding.recyclerViewRecipes.hasFixedSize()
        binding.recyclerViewRecipes.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewRecipes.adapter = mAdapter

    }



    /*
    OnDestroy
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}