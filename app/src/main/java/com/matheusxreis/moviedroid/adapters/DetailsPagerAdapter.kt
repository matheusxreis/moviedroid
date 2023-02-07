package com.matheusxreis.moviedroid.adapters

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class DetailsPagerAdapter(
    private val resultBundle: Bundle,
    private val fragments: ArrayList<Fragment>,
    private val titles:ArrayList<String>,
    activity: FragmentActivity
    ): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment {
       fragments[position].arguments = resultBundle

    return fragments[position]
    }
    fun getPageTitle(position: Int):String = titles[position]
}