package com.example.quizapp

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.quizapp.databinding.FragmentQuestionDetailsBinding


class QuestionDetailsFragment : Fragment() {

    private lateinit var binding: FragmentQuestionDetailsBinding
    private lateinit var viewModel: QuestionsViewModel
    private lateinit var listener: ButtonClickListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val fragmentBinding = FragmentQuestionDetailsBinding.inflate(inflater, container, false)
        binding = fragmentBinding

        viewModel = ViewModelProvider(requireActivity()).get(QuestionsViewModel::class.java)
        binding.questionNumber.text = (viewModel.currentQuestion + 1).toString()

        if (viewModel.currentQuestion == 9) {
            binding.nextBtn.setImageResource(0)
            binding.nextText.text = ""
        }
        else if (viewModel.currentQuestion == 0) {
            binding.previousBtn.setImageResource(0)
            binding.previousText.text = ""
        }

        listener = activity as ButtonClickListener

        //  updating time on QuestionDetailScreen with the help of viewModel
        viewModel.currentTime.observe(viewLifecycleOwner, Observer {
            val sec = (it / 1000) % 60
            val min = (it / (1000 * 60)) % 60
            if (sec < 10) {
                binding.questionDetailsTime.text = "$min : 0$sec"
            } else {
                binding.questionDetailsTime.text = "$min : $sec"
            }
        })

        onRadioGroupClick()

        //  setting the bookmark status in the viewModel
        binding.detailsBookmark.setOnClickListener {
            viewModel.setBookmarkStatus(viewModel.currentQuestion)
        }

        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.questionDetailsFragment = this@QuestionDetailsFragment
    }

    //  Function to handle clicks on the radioGroup
    private fun onRadioGroupClick() {

        binding.radioGroup.setOnCheckedChangeListener { radioGroup, i ->
            when (i) {
                R.id.option1 -> {
                    viewModel.updateUserScore(1)
                }
                R.id.option2 -> {
                    viewModel.updateUserScore(2)
                }
                R.id.option3 -> {
                    viewModel.updateUserScore(3)
                }
                R.id.option4 -> {
                    viewModel.updateUserScore(4)
                }
            }
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

    //  Function to handle next button click
    fun nextClick() {
        if (viewModel.currentQuestion < 9) {
            binding.radioGroup.clearCheck()
            viewModel.currentQuestion += 1
            binding.questionNumber.text = (viewModel.currentQuestion + 1).toString()
            viewModel.getCurrentQuestionModel(viewModel.currentQuestion)
        }
        if (viewModel.currentQuestion == 9) {
            binding.nextBtn.setImageResource(0)
            binding.nextText.text = ""
        }
        binding.previousBtn.setImageResource(R.drawable.baseline_arrow_back_ios_24)
        binding.previousText.text = "Previous"
    }

    //  Function to handle previous button click
    fun previousClick() {
        if (viewModel.currentQuestion > 0) {
            binding.radioGroup.clearCheck()
            viewModel.currentQuestion -= 1
            binding.questionNumber.text = (viewModel.currentQuestion + 1).toString()
            viewModel.getCurrentQuestionModel(viewModel.currentQuestion)
        }
        if (viewModel.currentQuestion < 9) {
            binding.nextBtn.setImageResource(R.drawable.baseline_navigate_next_24)
            binding.nextText.text = "Next"
        }
        if (viewModel.currentQuestion == 0) {
            binding.previousBtn.setImageResource(0)
            binding.previousText.text = ""
        }
    }
}