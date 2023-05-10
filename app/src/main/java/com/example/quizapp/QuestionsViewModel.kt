package com.example.quizapp

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject


private const val API_URL =
    "https://raw.githubusercontent.com/tVishal96/sample-english-mcqs/master/db.json"
private const val RESPONSE_ENTRY_KEY = "questions"
private const val QUESTION = "question"
private const val OPTIONS = "options"
private const val CORRECT_OPTION = "correct_option"

class QuestionsViewModel(application: Application) : AndroidViewModel(application),
    Response.Listener<String>, Response.ErrorListener {

    //  MutableLiveData to store the the questions
    private var questionLiveData: MutableLiveData<List<QuestionModel>> =
        MutableLiveData<List<QuestionModel>>()

    //  MutableLiveData to store the user's score
    private var score: MutableLiveData<Int> = MutableLiveData<Int>(0)

    //  MutableLiveData to store the remaining time
    var currentTime: MutableLiveData<Long> = MutableLiveData<Long>()

    //  Boolean to auto submit the test on time up
    var autoSubmit = MutableLiveData<Boolean>()

    //  variable to store current question number
    var currentQuestion: Int = -1

    //  MutableLive data to store current question Model
    var currentQuestionModel = MutableLiveData<QuestionModel>()

    fun setTime(time: Long) {
        currentTime.value = time
    }

    fun getScore(): MutableLiveData<Int> {
        return score
    }

    private fun increaseScore() {
        score.value = score.value?.plus(1)
    }

    private fun decreaseScore() {
        score.value = score.value?.minus(1)
    }

    fun getQuestionLiveData(): MutableLiveData<List<QuestionModel>> {
        return questionLiveData
    }

    private fun setAnswerStatus(position: Int) {
        questionLiveData.value?.get(position)?.answerStatus = 1
    }

    private fun setSelectedOption(position: Int, option: Int) {
        questionLiveData.value?.get(position)?.selectedOption = option
    }

    //  Function to check the user's option and update the score
    private fun isUserOptionCorrect(questionNumber: Int, playerOption: String): Boolean {
        val check = questionLiveData.value?.get(questionNumber)?.answer
        if (check.equals(playerOption)) {
            return true
        }
        return false
    }

    fun getCurrentQuestionModel(position: Int) {
        currentQuestionModel.postValue(questionLiveData.value?.get(position))
    }

    //  Function to set bookmark image on the QuestionDetailScreen according to the bookmark status in the viewModel
    fun setBookmarkStatus(position: Int) {
        if (questionLiveData.value?.get(position)?.bookmark == 0) {
            questionLiveData.value?.get(position)?.bookmark = 1
        } else {
            questionLiveData.value?.get(position)?.bookmark = 0
        }
        currentQuestionModel.postValue(questionLiveData.value!![position])
    }

    //  Function to update user Score
    fun updateUserScore(option: Int) {

        val s: String? = questionLiveData.value?.get(currentQuestion)?.options?.get(option - 1)

        if (s?.let { isUserOptionCorrect(currentQuestion, s) } == true) {
            if (questionLiveData.value?.get(currentQuestion)?.selectedCorrectOption == false) {
                increaseScore()
                questionLiveData.value?.get(currentQuestion)?.selectedCorrectOption = true
            }
        } else if (s?.let { isUserOptionCorrect(currentQuestion, s) } == false) {
            if (questionLiveData.value?.get(currentQuestion)?.selectedCorrectOption == true) {
                decreaseScore()
                questionLiveData.value?.get(currentQuestion)?.selectedCorrectOption = false
            }
        }
        setSelectedOption(currentQuestion, option)
        setAnswerStatus(currentQuestion)
    }

    //  Function to reinitialize viewModel on restarting the quiz
    //  Questions and their options are shuffled, score and other values are set to 0
    fun reinitializeData() {
        score.value = 0
        questionLiveData.value?.let { questions ->
            questionLiveData.value = questions.shuffled()
        }
        for (i in 0 until 10) {
            questionLiveData.value?.get(i)?.answerStatus = 0
            questionLiveData.value?.get(i)?.bookmark = 0
            questionLiveData.value?.get(i)?.selectedOption = 0
            questionLiveData.value?.get(i)?.selectedCorrectOption = false
            questionLiveData.value?.get(i)?.options?.shuffle()
        }
    }

    //  Fetching the data from api and storing in the questionLiveData
    init {
        val queue = Volley.newRequestQueue(application)
        val request = StringRequest(Request.Method.GET, API_URL, this, this)
        queue.add(request)
    }

    override fun onResponse(response: String) {
        try {
            val questionList: MutableList<QuestionModel> = parseResponse(response)
            questionList.shuffle()
            questionLiveData.postValue(questionList)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    override fun onErrorResponse(error: VolleyError?) {
        Toast.makeText(getApplication(), "Error Encountered", Toast.LENGTH_SHORT).show()
    }

    @Throws(JSONException::class)
    private fun parseResponse(response: String): MutableList<QuestionModel> {

        val models: ArrayList<QuestionModel> = ArrayList()
        val res = JSONObject(response)
        val entries = res.optJSONArray(RESPONSE_ENTRY_KEY)

        for (i in 0 until entries.length()) {

            val obj = entries[i] as JSONObject
            val question = obj.getString(QUESTION)
            val options = obj.getJSONArray(OPTIONS)
            val correctOption = obj.getInt(CORRECT_OPTION)

            val list: ArrayList<String> = ArrayList()
            list.add(options[0] as String)
            list.add(options[1] as String)
            list.add(options[2] as String)
            list.add(options[3] as String)

            val model = QuestionModel(question, list, list[correctOption],0, 0, false, 0, correctOption)
            models.add(model)
        }
        return models
    }

}

