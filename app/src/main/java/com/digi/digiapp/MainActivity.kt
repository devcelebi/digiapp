package com.digi.digiapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.digi.digiapp.adapter.FilmCategoryAdapter
import com.digi.digiapp.network.FilmCategoryListModel
import com.digi.digiapp.network.Genre
import com.digi.digiapp.viewModel.MainActivityViewModel
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var viewModel : MainActivityViewModel
    private lateinit var pagerAdapter: FilmCategoryAdapter

    var genres: ArrayList<Genre>?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadFilmCategories()
    }

    private fun loadFilmCategories(){
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        viewModel.getFilmCategories().observe(this, Observer<FilmCategoryListModel>{
            if(it!=null){
                genres = arrayListOf()
                genres = it.genres
                pagerAdapter = FilmCategoryAdapter(supportFragmentManager,it.genres)
                vpPager.adapter = pagerAdapter
                pagerAdapter.notifyDataSetChanged()
                vpPager.offscreenPageLimit = it.genres.size
                tabLayNewCategory.setupWithViewPager(vpPager)
            }else{
                Toast.makeText(this,getString(R.string.error),Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.makeApiCall("3bb3e67969473d0cb4a48a0dd61af747")

        vpPager.addOnPageChangeListener(object: ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                pagerAdapter.notifyDataSetChanged()
            }
        })
    }


}