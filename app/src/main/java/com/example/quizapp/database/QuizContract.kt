package com.example.quizapp.database

import android.provider.BaseColumns

object QuizContract {
    internal const val SQL_CREATE_ENTRIES =
        "CREATE TABLE ${QuizEntry.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${QuizEntry.COL_THEME} TEXT," +
                "${QuizEntry.COL_DIFFICULTY} TEXT)"

    internal const val SQL_DROP_TABLE =
        "DROP TABLE IF EXISTS ${QuizEntry.TABLE_NAME}"

    internal const val BaseColumnsId = BaseColumns._ID

    object QuizEntry : BaseColumns {
        const val TABLE_NAME = "Quiz"
        const val COL_THEME = "Theme"
        const val COL_DIFFICULTY = "Difficulty"
    }
}