package com.example.quizapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.quizapp.databinding.FragmentSetupScreenBinding

class SetupScreenFragment : Fragment() {

    private lateinit var listener: ButtonClickListener
    private lateinit var binding: FragmentSetupScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val fragmentBinding = FragmentSetupScreenBinding.inflate(inflater, container, false)
        binding = fragmentBinding

        listener = activity as ButtonClickListener

        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            setUpListener = listener
        }
    }
}