package com.example.quizapp

import android.util.Log

class QuizQuestion {
    private var question = ""
    private var correctAnswer = ""
    private var incorrectAnswers: List<String> = listOf("", "", "")

    fun makeQuestion (question: String, correctAnswer: String, incorrectAnswers: List<String>) {
        this.question=question
        this.correctAnswer=correctAnswer
        this.incorrectAnswers=incorrectAnswers
    }

    fun printQuestion() {
        Log.e("Q", question)
        Log.e("A", correctAnswer)
        Log.e("W", incorrectAnswers.toString())
    }

    fun getIncorrect(): List<String>{
        return incorrectAnswers
    }

    fun getCorrect(): String{
        return correctAnswer
    }

    fun getTitle(): String{
        return question
    }
}