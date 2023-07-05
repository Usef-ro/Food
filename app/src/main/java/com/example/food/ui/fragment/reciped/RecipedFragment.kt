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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.food.databinding.FragmentRecipedBinding
import com.example.food.ui.adapter.adapterRecipe
import com.example.food.util.NetworkResult
import com.example.food.util.constants.API_KEY
import com.example.food.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class recipedFragment : Fragment() {

    val mAdapter by lazy { adapterRecipe() }

    private lateinit var mainViewModel:MainViewModel


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

        mainViewModel=ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        initRecy()
        requestApiData()
        return view

    }

    private fun showShimmer() {
        binding.shimmerRecyclerView.showShimmer()
    }
    private fun hideShimmer() {
        binding.shimmerRecyclerView.hideShimmer()
    }
    fun initRecy(){
        binding.recyclerViewRecipes.hasFixedSize()
        binding.recyclerViewRecipes.layoutManager=LinearLayoutManager(requireContext())
        binding.recyclerViewRecipes.adapter=mAdapter

    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun requestApiData(){
        mainViewModel.getRecipes(applyQueries())
        mainViewModel.recipesResponse.observe(viewLifecycleOwner,{ res->
            when(res){
                is NetworkResult.Success->{
                    hideShimmer()
                    res.data?.let { mAdapter.setData(it) }
                }
                is NetworkResult.Error->{
                    hideShimmer()
                    Toast.makeText(requireContext(),res.message.toString(),Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading->{
                    showShimmer()
                }
            }
        })
    }

    fun applyQueries():HashMap<String,String>{
        val query= HashMap<String, String>()

        query["number"]="50"
        query["apiKey"]=API_KEY
        query["type"]="finger"
        query["diet"]="vegan"
        query["addRecipeInformation"]="true"
//        query["fillIngredients"]="true"

        return query
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}