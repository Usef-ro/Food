package com.example.food.ui.fragment.overView

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import coil.load
import com.example.food.R
import com.example.food.databinding.FragmentOverViewBinding
import com.example.food.domain.model.Result


class OverViewFragment : Fragment() {
    private var _binding: FragmentOverViewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment

        _binding = FragmentOverViewBinding.inflate(inflater, container, false)
        val view = binding.root

        val args = arguments
        val myBundle: Result? = args?.getParcelable("recipeBundle")
        binding.mainImageView.load(myBundle?.image)

        binding.txtTitleOver.text = myBundle?.title

        binding.textHealthy.text = myBundle?.aggregateLikes.toString()
        binding.textTime.text = myBundle?.readyInMinutes.toString()
        binding.txtSummery.text = myBundle?.summary
        myBundle?.let {
//            var summary =Json.parse(it).text
//            binding.txtSummery.text = summary
        }

        if (myBundle?.vegan == true) {
            binding.imageVegan.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
            binding.textVegan.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if (myBundle?.vegetarian == true) {
            binding.imageVegeterian.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
            binding.textVegetrian.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
        }

        if (myBundle?.cheap == true) {
            binding.imageCheap.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
            binding.textCheap.setTextColor(ContextCompat.getColor(requireContext(), R.color.green))
        }

        if (myBundle?.dairyFree == true) {
            binding.imageDiaryFree.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
            binding.textDiaryFree.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
        }
        if (myBundle?.veryHealthy == true) {
            binding.imageHealthy.setColorFilter(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
            binding.textHealthy.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.green
                )
            )
        }

        return view


    }


}