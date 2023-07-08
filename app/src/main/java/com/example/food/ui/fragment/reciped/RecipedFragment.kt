package com.example.food.ui.fragment.reciped

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.food.R
import com.example.food.databinding.FragmentRecipedBinding
import com.example.food.ui.adapter.adapterRecipe
import com.example.food.util.NetworkResult
import com.example.food.util.observeOnce
import com.example.food.viewModel.MainViewModel
import com.example.food.viewModel.recipesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class recipedFragment : Fragment() {

    val mAdapter by lazy { adapterRecipe() }

    private lateinit var mainViewModel:MainViewModel
    private lateinit var recipesViewModell:recipesViewModel


    private var _binding: FragmentRecipedBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        _binding = FragmentRecipedBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.lifecycleOwner=this

        mainViewModel=ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        recipesViewModell=ViewModelProvider(requireActivity()).get(recipesViewModel::class.java)

        binding.mainViewModel=mainViewModel
        initRacy()
        requestApiData()
        readDatabase()

        binding.btnFloating.setOnClickListener {
            findNavController().navigate(R.id.action_recipedFragment_to_blankBottomSheet2)
        }
        return view

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun readDatabase() {
     lifecycleScope.launch {
         mainViewModel.readRecipes.observeOnce(viewLifecycleOwner) { data ->
             if (data.isNotEmpty()) {
                 mAdapter.setData(data[0].foodRecipe)
                 hideShimmer()
             }else{
                 requestApiData()
             }
         }
     }
    }
fun loadDataFromCache(){
    lifecycleScope.launch {
        mainViewModel.readRecipes.observeOnce(viewLifecycleOwner){data->
            mAdapter.setData(data[0].foodRecipe)
        }
    }
}
    private fun showShimmer() {
        binding.shimmerRecyclerView.showShimmer()
    }
    private fun hideShimmer() {
        binding.shimmerRecyclerView.hideShimmer()
    }
    private fun initRacy(){
        binding.recyclerViewRecipes.hasFixedSize()
        binding.recyclerViewRecipes.layoutManager=LinearLayoutManager(requireContext())
        binding.recyclerViewRecipes.adapter=mAdapter

    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun requestApiData(){
        mainViewModel.getRecipes(recipesViewModell.applyQueries())
        mainViewModel.recipesResponse.observe(viewLifecycleOwner,{ res->
            when(res){
                is NetworkResult.Success->{
                    hideShimmer()
                    res.data?.let { mAdapter.setData(it) }
                }
                is NetworkResult.Error->{
                    hideShimmer()
                    loadDataFromCache()
                    Toast.makeText(requireContext(),res.message.toString(),Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading->{
                    showShimmer()
                }
            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}