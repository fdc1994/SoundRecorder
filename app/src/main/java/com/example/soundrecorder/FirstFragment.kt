package com.example.soundrecorder

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.soundrecorder.databinding.FragmentFirstBinding


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private lateinit var toastRecordingStarted: Toast
    private lateinit var toastRecordingEnded: Toast

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toastRecordingStarted = Toast.makeText(requireContext(), R.string.recording_started, Toast.LENGTH_SHORT)
        toastRecordingEnded = Toast.makeText(requireContext(), R.string.recording_ended, Toast.LENGTH_SHORT)
        binding.buttonRecord.setOnTouchListener(OnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                        toastRecordingEnded.cancel()
                    toastRecordingStarted.show();
                }

                MotionEvent.ACTION_UP   -> {
                        toastRecordingStarted.cancel()
                    toastRecordingEnded.show();
                }
            }
            false
        })
        /**
        binding.buttonRecord.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }**/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}