package com.example.quizapp

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizapp.databinding.FragmentQuestionsListBinding


class QuestionsListFragment : Fragment() {

    private lateinit var viewModel: QuestionsViewModel
    private lateinit var listener: ButtonClickListener
    private lateinit var binding: FragmentQuestionsListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val fragmentBinding = FragmentQuestionsListBinding.inflate(inflater, container, false)
        binding = fragmentBinding

        listener = activity as ButtonClickListener
        binding.recyclerview.layoutManager = LinearLayoutManager(context)
        viewModel = ViewModelProvider(requireActivity())[QuestionsViewModel::class.java]

        binding.recyclerview.adapter = QuestionsListAdapter(viewModel.getQuestionLiveData(), activity, viewModel)

        //  Updating time on the screen
        viewModel.currentTime.observe(viewLifecycleOwner, Observer {
            val sec = (it / 1000) % 60
            val min = (it / (1000 * 60)) % 60
            if (sec < 10) {
                binding.questionsListTime.text = "$min : 0$sec"
            } else {
                binding.questionsListTime.text = "$min : $sec"
            }
        })

        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = viewModel
            fragment = this@QuestionsListFragment
        }
    }

    //  Function to show a dialogue on clicking submit button
    fun showDialogue() {

        val builder: AlertDialog.Builder = AlertDialog.Builder(context).setTitle("Submit Quiz")
        builder.setMessage("Are you sure you want to Submit the test ?")
        builder.setPositiveButton("Yes",
            DialogInterface.OnClickListener { dialogInterface, i ->
                listener.launchSummaryFragment()
            })

        builder.setNegativeButton("No",
            DialogInterface.OnClickListener { dialogInterface, i -> })

        builder.show()
    }

}

