package com.example.shounakpc.soundrecorder

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.os.SystemClock
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.Chronometer
import android.widget.TextView
import java.io.File

class RecordFragment: Fragment() {

    private lateinit var recordButton: FloatingActionButton
    private lateinit var pauseButton: Button
    private lateinit var recordingPrompt: TextView
    private lateinit var chronometer: Chronometer
    var recordPromptCount = 0
    var startRecording = true
    var pauseRecording = true

//  Time when user clicks pause button
    var timeWhenPaused = 0

    companion object {
        fun newInstance(position: Int): RecordFragment {
            var recordFragment = RecordFragment()
            var newBundle = Bundle()
            newBundle.putInt("position", position)
            recordFragment.arguments = newBundle
            return recordFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var view = inflater.inflate(R.layout.record_fragment_layout, container, false)
        chronometer = view.findViewById(R.id.chronometer)
        recordingPrompt = view.findViewById(R.id.recording_status)
        recordButton = view.findViewById(R.id.recordButton)
        recordButton.rippleColor = resources.getColor(R.color.colorPrimaryDark)

        recordButton.setOnClickListener {
            onRecord(startRecording)
            startRecording = !startRecording
        }

        pauseButton.visibility = View.GONE
        pauseButton.setOnClickListener {
//            onPauseRecord(pauseRecording)
            pauseRecording = !pauseRecording
        }

        return view
    }

    private fun onRecord(startRecording: Boolean) {
//        val recordIntent = Intent(activity, RecordingService.class)

        if (startRecording) {
            val filePath = Environment.getExternalStorageDirectory().toString() + "/SoundRecorder"
            val folder = File(filePath)

            if (!folder.exists()) {
                folder.mkdir()
            }

            chronometer.base = SystemClock.elapsedRealtime()
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
                }

                recordPromptCount++
            }

//        activity.startService(recordIntent)
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

            recordingPrompt.text = "Recording."
            recordPromptCount++
        }
        else {
            chronometer.stop()
            chronometer.base = SystemClock.elapsedRealtime()
            timeWhenPaused = 0
            recordingPrompt.text = "Tap Button to Start Recording"
//            activity.stopService(recordIntent)
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }

    }

}