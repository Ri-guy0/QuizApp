package com.example.quizapp

import android.provider.BaseColumns

object QuestionContract {
    internal const val SQL_CREATE_ENTRIES =
        "CREATE TABLE ${QuestionEntry.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${QuestionEntry.COL_QUIZ_ID} INTEGER," +
                "${QuestionEntry.COL_TITLE} TEXT," +
                "${QuestionEntry.COL_CORRECT} TEXT," +
                "${QuestionEntry.COL_INCORRECT_ONE} TEXT," +
                "${QuestionEntry.COL_INCORRECT_TWO} TEXT," +
                "${QuestionEntry.COL_INCORRECT_THREE} TEXT)"

    internal const val SQL_DROP_TABLE =
        "DROP TABLE IF EXITS ${QuestionEntry.TABLE_NAME}"

    object QuestionEntry : BaseColumns{
        const val TABLE_NAME = "Question"
        const val COL_QUIZ_ID = "QuizID"
        const val COL_TITLE = "Title"
        const val COL_CORRECT = "Correct"
        const val COL_INCORRECT_ONE = "IncorrectOne"
        const val COL_INCORRECT_TWO = "IncorrectTwo"
        const val COL_INCORRECT_THREE = "IncorrectThree"
    }


}