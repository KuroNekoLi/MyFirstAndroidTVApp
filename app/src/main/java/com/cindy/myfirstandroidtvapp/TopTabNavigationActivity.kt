package com.cindy.myfirstandroidtvapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.leanback.app.RowsSupportFragment
import androidx.viewpager.widget.ViewPager
import com.cindy.myfirstandroidtvapp.Model.Data
import com.cindy.myfirstandroidtvapp.Model.MovieList
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_top_tab_navigation.*

class TopTabNavigationActivity : FragmentActivity() {
    companion object {
        var viewPagerInstance: ViewPager? = null
    }
    private val TAG: String = javaClass.simpleName
    private var mMovieList: MovieList? = null
    private var mMovieListData: List<Data>? = null
    private var mViewPagerAdapter: TopTabViewPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_tab_navigation)

        processData()
        processView()

        // 初始化ViewPager的静态引用
        viewPagerInstance = view_pager

        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                // 当页面被选中时，通知Fragment
                val fragment = mViewPagerAdapter?.instantiateItem(view_pager, position) as? RowsSupportFragment

            }

            override fun onPageScrollStateChanged(state: Int) {}
        })


    }

    fun processData(){
        val jsonFileString: String = resources.openRawResource(R.raw.movielist).bufferedReader().use { it.readText() }
        if(BuildConfig.DEBUG) Log.d(TAG, "jsonFileString: $jsonFileString")
        mMovieList = Gson().fromJson(jsonFileString, MovieList::class.java)
        if(BuildConfig.DEBUG) Log.i(TAG, "mMovieList: $mMovieList")
    }

    fun processView(){

        if(mMovieList!=null){
            mMovieListData = mMovieList!!.data
            if(mMovieListData!=null && mMovieListData!!.isNotEmpty()){
                mViewPagerAdapter = TopTabViewPagerAdapter(supportFragmentManager,
                    FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, mMovieListData)
            }
        }

        if(mViewPagerAdapter!=null){
            view_pager.adapter = mViewPagerAdapter
        }
        tab_layout.setupWithViewPager(view_pager)
    }
}