package com.example.shounakpc.soundrecorder

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.io.File

class RecordingList: Fragment() {
    private lateinit var list_items: ArrayList<String>;

    companion object {
        fun newInstance(folder: File): RecordingList {
            var recordListFragment = RecordingList();
            var newBundle = Bundle();
            var items : ArrayList<String> = getRecordList(folder);
            newBundle.putStringArrayList("items", items);
            recordListFragment.arguments = newBundle;
            return recordListFragment;
        }

        private fun getRecordList(folder: File): ArrayList<String> {
            return folder.listFiles().map {
                it.name;
            } as ArrayList<String>;
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.record_list_layout, container, false);
        var recording_list = view.findViewById<RecyclerView>(R.id.recording_list);
        var list_items : ArrayList<String> = this.arguments?.getStringArrayList("items") as ArrayList<String>;
        recording_list.layoutManager = LinearLayoutManager(activity);
        recording_list.adapter = RecyclerViewAdapter(list_items, this.context!!);
        return view;
    }

    private fun getRecordList(folder: File): ArrayList<String> {
        return folder.listFiles().map {
            it.name;
        } as ArrayList<String>;
    }
}