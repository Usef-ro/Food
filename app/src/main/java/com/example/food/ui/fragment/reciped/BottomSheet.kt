package com.example.food.ui.fragment.reciped

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.example.food.app
import com.example.food.databinding.FragmentBlankBottomSheetBinding
import com.example.food.util.constants
import com.example.food.viewModel.recipesViewModel
import com.example.food.viewModel.viewmodelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint

class BottomSheet : BottomSheetDialogFragment() {

        lateinit var recipesViewMod: recipesViewModel
//        val recipesViewModel:recipesViewModel by activityViewModels()
    var mealType = constants.DEFAULT_MEAL_TYPE
    var mealTypeId = 0
    var dietType = constants.DEFAULT_DIET_TYPE
    var dietTypeId = 0


    private var _binding: FragmentBlankBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//recipesViewModel= ViewModelProvider(requireActivity())[recipesViewModel::class.java]
//        val app=app()
//        recipesViewModel=viewmodelFactory(app).create(recipesViewModel::class.java)
        recipesViewMod=ViewModelProviders.of(requireActivity()).get(
           recipesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBlankBottomSheetBinding
            .inflate(inflater, container, false)
        val view = binding.root

//        val app=app()
//        val recipesViewModel:recipesViewModel=ViewModelProviders.of(requireActivity()).get(recipesViewModel::class.java)




        recipesViewMod.readMealAndDietType.asLiveData().observe(viewLifecycleOwner) { value ->
            mealType = value.selectMealsType
            dietType = value.selectDietType
            updateChip(value.selectMealsTypeId, _binding!!.maelTypeChip)
            updateChip(value.selectDieTypeId, _binding!!.chipGroupeDiet)
        }
        binding.maelTypeChip.setOnCheckedChangeListener { group, checkedIds ->
        val chip=group.findViewById<Chip>(checkedIds)
        val selectMealType=chip.text.toString().toLowerCase(Locale.ROOT)
        mealType=selectMealType
        mealTypeId=checkedIds
    }

        binding.chipGroupeDiet.setOnCheckedChangeListener { group, checkedIds ->
            val chip=group.findViewById<Chip>(checkedIds)
            val selectDietType=chip.text.toString().toLowerCase(Locale.ROOT)
            dietType=selectDietType
            dietTypeId=checkedIds
        }
        binding.buttonSubmit.setOnClickListener {
            recipesViewMod.saveMealAndDietType(
    mealType,
    mealTypeId,
    dietType,
    dietTypeId
)

            val action=
               BottomSheetDirections.actionBlankBottomSheetToRecipedFragment2(true)
            findNavController().navigate(action)
        }



        return view
    }

    private fun updateChip(selectDieTypeId: Int, chipGroupeDiet: ChipGroup) {
        if (selectDieTypeId != 0) {
            try {
                chipGroupeDiet.findViewById<Chip>(selectDieTypeId).isChecked = true
            } catch (e: Exception) {
                Log.e("updateChip", "" + e.message.toString())
            }
        }
    }

}