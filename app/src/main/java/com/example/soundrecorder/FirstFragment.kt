package com.example.soundrecorder

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.soundrecorder.databinding.FragmentFirstBinding
import java.util.jar.Manifest


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val REQUEST_AUDIO_PERMISSION_CODE = 101
    private lateinit var toastRecordingStarted: Toast
    private lateinit var toastRecordingEnded: Toast
    private lateinit var mediaRecorder: MediaRecorder
    private var isRecording: Boolean = false;
    private var seconds: Int = 0;
    private var path: String? = null;
    val requestMultiplePermissions = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        permissions.entries.forEach {

        }
    }



    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    @RequiresApi(Build.VERSION_CODES.S)
    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toastRecordingStarted = Toast.makeText(requireContext(), R.string.recording_started, Toast.LENGTH_SHORT)
        toastRecordingEnded = Toast.makeText(requireContext(), R.string.recording_ended, Toast.LENGTH_SHORT)
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S && isAdded) {
            mediaRecorder = MediaRecorder(requireContext())
        } else {
            mediaRecorder = MediaRecorder()
        }



        binding.buttonRecord.setOnTouchListener { _, event ->
            if (isRecordingPermissionEnabled()) {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        toastRecordingEnded.cancel()
                        toastRecordingStarted.show();
                    }
                    MotionEvent.ACTION_UP   -> {
                        toastRecordingStarted.cancel()
                        toastRecordingEnded.show();
                        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
                    }
                }
            } else {
                if(isAdded) {
                    Toast.makeText(requireContext(), R.string.no_recording_permission, Toast.LENGTH_LONG).show()
                    requestRecordingPermission()
                }
            }

            false
        }
        /**
        binding.buttonRecord.setOnClickListener {

        }**/
    }

    private fun isRecordingPermissionEnabled(): Boolean {
        if(isAdded) {
           return ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
        }
        return false
    }

    private fun requestRecordingPermission() {
        ActivityCompat.requestPermissions(requireActivity(),
                                          arrayOf(android.Manifest.permission.RECORD_AUDIO),
                                          REQUEST_AUDIO_PERMISSION_CODE)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (permissions[0] == android.Manifest.permission.RECORD_AUDIO && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(requireContext(), R.string.permission_success, Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(requireContext(), R.string.permission_failed, Toast.LENGTH_SHORT).show()
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

}