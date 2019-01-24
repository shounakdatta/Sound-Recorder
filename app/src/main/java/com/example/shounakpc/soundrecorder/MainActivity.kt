package com.example.shounakpc.soundrecorder

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
//import com.astuetz.PagerSlidingTabStrip

class MainActivity : AppCompatActivity() {

//    private lateinit var tabs: PagerSlidingTabStrip
    private lateinit var viewPager: ViewPager
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager = findViewById(R.id.pager)
//        tabs = findViewById(R.id.tabStrip)
        toolbar = findViewById(R.id.toolbar)

        viewPager.adapter = MyPagerAdapter(supportFragmentManager)
//        tabs.setViewPager(viewPager)

        if (toolbar != null) {
            setSupportActionBar(toolbar)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when(item.itemId) {
//            R.id.settings_id ->
//        }
        return super.onOptionsItemSelected(item)
    }


    class MyPagerAdapter(fragmentManager: FragmentManager) :
        FragmentPagerAdapter(fragmentManager){

        private val titles = arrayOf("Record", "Saved Records")

        override fun getItem(position: Int): Fragment {
            when(position) {
                0 -> return RecordFragment.newInstance()
                else -> return RecordFragment.newInstance()
            }
        }

        override fun getCount(): Int {
            return titles.size
        }

        override fun getPageTitle(position: Int): CharSequence {
            return titles[position]
        }
    }
}
