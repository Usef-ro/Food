package com.example.food.ui.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.example.food.R
import com.example.food.databinding.ActivityDetailBinding
import com.example.food.ui.fragment.overView.IngredientFragment
import com.example.food.ui.fragment.overView.InstructionFragment
import com.example.food.ui.fragment.overView.OverViewFragment

class DetailActivity : AppCompatActivity() {
    private val args by navArgs<DetailActivityArgs>()
    lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)



        setSupportActionBar(binding.toolbar)
        binding.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fragment = ArrayList<Fragment>()
        fragment.add(OverViewFragment())
        fragment.add(IngredientFragment())
        fragment.add(InstructionFragment())

        val title = ArrayList<String>()
        title.add("OverView")
        title.add("Ingredient")
        title.add("Instruction")


        val resultBundle = Bundle()
        resultBundle.putParcelable("result", args.result)


        val adapter = com.example.food.ui.adapter.PagerAdapter(
            resultBundle,
            fragment,
            title,
            supportFragmentManager
        )

        binding.viewPager.adapter = adapter
        binding.tablayout.setupWithViewPager(binding.viewPager)


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}