package com.example.shounakpc.soundrecorder

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.os.SystemClock
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.Chronometer
import android.widget.ProgressBar
import android.widget.TextView
import kotlinx.android.synthetic.main.record_fragment_layout.view.*
import java.io.File

class RecordFragment: Fragment() {

    private lateinit var recordButton: FloatingActionButton
    private lateinit var pauseButton: Button
    private lateinit var stopButton: Button
    private lateinit var recordingPrompt: TextView
    private lateinit var chronometer: Chronometer
    private lateinit var view1: View
    var recordPromptCount = 0

//  Time when user clicks pause button
    var timeWhenPaused = 0

    companion object {
        fun newInstance(): RecordFragment {
            return RecordFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val position = this.arguments?.get("position") ?: 0

        if (position == 0) {
            view1 = inflater.inflate(R.layout.record_fragment_layout, container, false)
        }
        else {
            view1 = inflater.inflate(R.layout.record_fragment_layout, container, false)
        }
        val view = inflater.inflate(R.layout.record_fragment_layout, container, false)
        chronometer = view.findViewById(R.id.chronometer)
        recordingPrompt = view.findViewById(R.id.recording_status)
        recordButton = view.findViewById(R.id.recordButton)
        pauseButton = view.findViewById(R.id.pauseButton)
        stopButton = view.findViewById(R.id.stopButton)

        recordButton.isEnabled = true
        pauseButton.isEnabled = false
        stopButton.isEnabled = false

        recordButton.setOnClickListener {
            onRecord()
            recordButton.isEnabled = false
            pauseButton.isEnabled = true
            stopButton.isEnabled = true
        }

        pauseButton.setOnClickListener {
            onPauseRecord()
            recordButton.isEnabled = true
            pauseButton.isEnabled = false
            stopButton.isEnabled = true
        }

        stopButton.setOnClickListener {
            onStopRecord()
            recordButton.isEnabled = true
            pauseButton.isEnabled = false
            stopButton.isEnabled = false
        }

        return view
    }

    private fun onRecord() {
//        val recordIntent = Intent(activity, RecordingService.class)
        val filePath = Environment.getExternalStorageDirectory().toString() + "/SoundRecorder"
        val folder = File(filePath)

        if (!folder.exists()) {
            folder.mkdir()
        }

        Log.d("filePath", filePath)
        Log.d("pauseTime", timeWhenPaused.toString())

        if (timeWhenPaused == 0) {
            Log.d("pauseTime", "reset")
            chronometer.base = SystemClock.elapsedRealtime()
        }
        else {
            Log.d("pauseTime", "using last")
            chronometer.base = timeWhenPaused.toLong() + SystemClock.elapsedRealtime()
        }
        chronometer.start()
        chronometer.setOnChronometerTickListener {
            if (recordPromptCount == 0) {
                recordingPrompt.text = "Recording."
            }
            else if (recordPromptCount == 1) {
                recordingPrompt.text = "Recording.."
            }
            else if (recordPromptCount == 2) {
                recordingPrompt.text = "Recording..."
                recordPromptCount = 0
            }

            recordPromptCount++
        }

//        activity.startService(recordIntent)
//            activity.window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        recordingPrompt.text = "Recording..."
        recordPromptCount++

    }

    private fun onPauseRecord() {
        timeWhenPaused = (chronometer.base - SystemClock.elapsedRealtime()).toInt()
        chronometer.stop()
        Log.d("pauseTime", timeWhenPaused.toString())
        recordingPrompt.text = "Paused. Tap Mic to Continue or Stop to End"
    }

    private fun onStopRecord() {
        timeWhenPaused = 0
        chronometer.stop()
        chronometer.base = SystemClock.elapsedRealtime()
        recordingPrompt.text = "Tap Mic to Start Recording"
    }

}