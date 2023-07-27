package com.example.food.ui.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class PagerAdapter(
    val resultBundle: Bundle,
    val fragment: ArrayList<Fragment>,
    val title: ArrayList<String>,
    fm: FragmentManager
) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getCount(): Int {
        return fragment.size
    }

    override fun getItem(position: Int): Fragment {
        fragment[position].arguments = resultBundle
        return fragment[position]
    }

    override fun getPageTitle(position: Int): CharSequence {
        return title[position].toString()
    }
}