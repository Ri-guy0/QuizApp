package com.example.quizapp.test

import android.content.Context

class TestHub(context: Context) {
    var testDb = TestDatabase(context)
    var testApi = TestApi(context)

    init {
        testDb.quizValidator()
        testDb.testJunit()
        testDb.questionValidator()
        testApi.themeValidator_convert()
        testApi.quizValidator("History", "easy")
        testApi.quizValidator("Politics", "medium")
        testApi.quizValidator("Anime", "hard")
    }

}