package com.example.quizapp

data class QuestionModel (
    val question: String,
    val options: ArrayList<String>,
    val answer: String,
    var answerStatus: Int = 0,
    var bookmark: Int = 0,
    var selectedCorrectOption: Boolean = false,
    var selectedOption: Int = 0,
    val correctOption: Int = 0
)
