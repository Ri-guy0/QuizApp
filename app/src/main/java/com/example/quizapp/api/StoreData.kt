package com.example.quizapp.api

import com.example.quizapp.localclasses.FullQuiz

interface StoreData {
    fun storedData(curQuiz: FullQuiz)
}