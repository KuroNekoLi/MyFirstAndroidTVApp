package com.cindy.myfirstandroidtvapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentStatePagerAdapter
import com.cindy.myfirstandroidtvapp.model.Data
import com.cindy.myfirstandroidtvapp.model.MovieList
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_top_tab_navication.*

class TopTabNavicationActivity : AppCompatActivity() {

    private val TAG: String = javaClass.simpleName
    private var mMovieList: MovieList? = null
    private var mMovieListData: List<Data>? = null
    private var mViewPagerAdapter: TopTabViewPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_tab_navication)

        processData()
        processView()

    }

    fun processData(){
        val jsonFileString: String = resources.openRawResource(R.raw.movielist).bufferedReader().use { it.readText() }
        if(BuildConfig.DEBUG) Log.d(TAG, "jsonFileString: $jsonFileString")
        mMovieList = Gson().fromJson(jsonFileString, MovieList::class.java)
        if(BuildConfig.DEBUG) Log.i(TAG, "mMovieList: $mMovieList")

        if(mMovieList!=null){
            mMovieListData = mMovieList!!.data
            if(mMovieListData!=null && mMovieListData!!.isNotEmpty()){
                mViewPagerAdapter = TopTabViewPagerAdapter(supportFragmentManager,
                    FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, mMovieListData)
            }
        }

    }

    fun processView(){

        if(mViewPagerAdapter!=null){
            view_pager.adapter = mViewPagerAdapter
        }
        tab_layout.setupWithViewPager(view_pager)

    }

}