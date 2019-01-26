package com.example.shounakpc.soundrecorder

import android.Manifest
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager
    private lateinit var toolbar: Toolbar
    private lateinit var filepath: String
    private lateinit var folder: File

    var numRecordings = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        filepath = Environment.getExternalStorageDirectory().absolutePath + "/SoundRecorder";
        folder = File(filepath);

        if (!folder.exists()) {
            folder.mkdir()
        }

        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED ||
            checkSelfPermission(Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.RECORD_AUDIO
                ), 1
            );
        }

        viewPager = findViewById(R.id.pager)
        toolbar = findViewById(R.id.toolbar)
        viewPager.adapter = MyPagerAdapter(supportFragmentManager, folder)

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


    class MyPagerAdapter(
        fragmentManager: FragmentManager,
        private val folder: File
    ) :
        FragmentPagerAdapter(fragmentManager){

        private val titles = arrayOf("Record", "Saved Records")

        override fun getItem(position: Int): Fragment {
            when(position) {
                0 -> return RecordFragment.newInstance()
                else -> return RecordingList.newInstance(folder)
            }
        }

        override fun getCount(): Int {
            return titles.size
        }

        override fun getPageTitle(position: Int): CharSequence {
            return titles[position]
        }

//        private fun getRecordList(): ArrayList<String> {
//            return folder.listFiles().map {
//                it.name;
//            } as ArrayList<String>;
//        }
    }
}
