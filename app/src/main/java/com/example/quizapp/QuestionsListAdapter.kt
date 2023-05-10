package com.example.quizapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.databinding.QuestionViewBinding

class QuestionsListAdapter(
    private val list: MutableLiveData<List<QuestionModel>> = MutableLiveData<List<QuestionModel>>(),
    private val activity: FragmentActivity?,
    private val viewModel: QuestionsViewModel
) : RecyclerView.Adapter<QuestionsListAdapter.ViewHolder>() {

    private lateinit var listener: ButtonClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)

        val binding = QuestionViewBinding.inflate(view)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.value!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.questionNumber.text = ((position + 1).toString() + ".")
        list.value?.get(position)?.let { holder.bind(it) }
        listener = activity as ButtonClickListener

        holder.itemView.setOnClickListener {
            viewModel.currentQuestion = position
            viewModel.getCurrentQuestionModel(position)
            listener?.launchDetailsFragment()

        }

    }

    class ViewHolder(val binding: QuestionViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: QuestionModel) {
            binding.questionModel = item
        }
    }
}