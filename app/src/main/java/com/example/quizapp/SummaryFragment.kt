package com.example.quizapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.quizapp.databinding.FragmentSummaryBinding


class SummaryFragment : Fragment() {

    private lateinit var listener: ButtonClickListener
    private lateinit var viewModel: QuestionsViewModel
    private lateinit var binding: FragmentSummaryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val fragmentBinding = FragmentSummaryBinding.inflate(inflater, container, false)
        binding = fragmentBinding

        listener = activity as ButtonClickListener
        viewModel = ViewModelProvider(requireActivity())[QuestionsViewModel::class.java]

        binding.totalScore.text = viewModel.getScore().value.toString()

        //  Updating time on the screen
        viewModel.currentTime.observe(viewLifecycleOwner, Observer {
            val sec = ((600000 - it) / 1000) % 60
            val min = ((600000 - it) / (1000 * 60)) % 60
            if(sec < 10) {
                binding.totalTime.text = "$min : 0$sec"
            }
            else {
                binding.totalTime.text = "$min : $sec"
            }
        })

        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            summaryFragment = this@SummaryFragment
            summaryListener = listener
        }
    }

    fun exitQuiz() {
        activity?.finish()
    }
}