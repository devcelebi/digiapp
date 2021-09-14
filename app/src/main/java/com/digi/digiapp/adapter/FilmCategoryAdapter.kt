package com.digi.digiapp.adapter

import android.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.digi.digiapp.CategoryFragment
import com.digi.digiapp.network.Genre


class FilmCategoryAdapter(fragmentManager: FragmentManager, private val genreList: ArrayList<Genre>) :
    FragmentPagerAdapter(fragmentManager) {
    override fun getCount(): Int {
        return genreList.size
    }

    override fun getItem(position: Int): Fragment {
        return CategoryFragment.newInstance(genreList[position].id)

    }
    override fun getPageTitle(position: Int): CharSequence? {
        return genreList[position].name
    }

    fun newInstanceFragment(id:String){
        CategoryFragment.newInstance(id)
    }

}