package com.digi.digiapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.digi.digiapp.adapter.FilmListAdapter
import com.digi.digiapp.network.FilmInfoList
import com.digi.digiapp.viewModel.CategoryViewModel
import kotlinx.android.synthetic.main.category_fragment.*


class CategoryFragment : Fragment() {

    private lateinit var filmListAdapter: FilmListAdapter
    lateinit var viewModel: CategoryViewModel

    companion object {
        const val ID_KEY = "idKey"
        fun newInstance(id: String): CategoryFragment {
            val fr = CategoryFragment()
            if (id != null) {
                val args = Bundle()
                args.putString(ID_KEY, id)
                fr.arguments = args
            }
            return fr
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.category_fragment, container, false)
        initFilmList()
        return view
    }

    fun initFilmList(){

        viewModel = ViewModelProvider(requireActivity()).get(CategoryViewModel::class.java)
        viewModel.getFilmInfo().observe(requireActivity(),Observer<FilmInfoList>{
            if(it!=null){
                filmListAdapter = FilmListAdapter(it.results)
                rvFilms.adapter = filmListAdapter
                rvFilms.layoutManager = LinearLayoutManager(
                    activity,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                filmListAdapter.setonItemClickListener(object : FilmListAdapter.onItemClickListener{
                    override fun onItemClick(position: Int) {
                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.mainLayout, ExxoPlayerFragment.newInstance(it.results[position].title))
                            .addToBackStack("exxo")
                            .commit()

                    }

                })

            }else{
                Toast.makeText(requireContext(),getString(R.string.error), Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.makeApiCall("3bb3e67969473d0cb4a48a0dd61af747",arguments?.getString(ID_KEY)!!)






    }
}