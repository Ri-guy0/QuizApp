package com.example.quizapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class QuestionOpener(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(QuestionContract.SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(QuestionContract.SQL_DROP_TABLE)
        onCreate(db)
    }

    companion object {
        const val DATABASE_VERSION=1
        const val DATABASE_NAME="QuestionReader.db"
    }

    fun addData(curQuestion: QuizQuestion, quizId: Long) {
        val values = ContentValues().apply {
            put(QuestionContract.QuestionEntry.COL_TITLE, curQuestion.getTitle())
            put(QuestionContract.QuestionEntry.COL_QUIZ_ID, quizId)
            put(QuestionContract.QuestionEntry.COL_CORRECT, curQuestion.getCorrect())
            put(QuestionContract.QuestionEntry.COL_INCORRECT_ONE, curQuestion.getIncorrect()[0])
            put(QuestionContract.QuestionEntry.COL_INCORRECT_TWO, curQuestion.getIncorrect()[1])
            put(QuestionContract.QuestionEntry.COL_INCORRECT_THREE, curQuestion.getIncorrect()[2])
        }
        this.writableDatabase.insert(QuestionContract.QuestionEntry.TABLE_NAME, null, values)
    }

    fun loadQuizQuestions(quizId: Long): MutableList<QuizQuestion> {
        val questionList = mutableListOf<QuizQuestion>()

        val select = "${QuestionContract.QuestionEntry.COL_QUIZ_ID} = ?"
        val selectArgs = arrayOf(quizId.toString());

        val cursor = this.readableDatabase.query(
            QuestionContract.QuestionEntry.TABLE_NAME,
            null,
            select,
            selectArgs,
            null,
            null,
            null
        )

        with(cursor) {
            while (moveToNext()) {
                val curId = getLong(getColumnIndexOrThrow(BaseColumns._ID))
                val curTitle = getString(getColumnIndexOrThrow(QuestionContract.QuestionEntry.COL_TITLE))
                val curCorrect = getString(getColumnIndexOrThrow(QuestionContract.QuestionEntry.COL_CORRECT))
                val curIncorrect = arrayListOf<String>(
                    getString(getColumnIndexOrThrow(QuestionContract.QuestionEntry.COL_INCORRECT_ONE)),
                    getString(getColumnIndexOrThrow(QuestionContract.QuestionEntry.COL_INCORRECT_TWO)),
                    getString(getColumnIndexOrThrow(QuestionContract.QuestionEntry.COL_INCORRECT_THREE))
                )
                val curQuestion = QuizQuestion()

                curQuestion.makeQuestion(quizId, curId, curTitle, curCorrect, curIncorrect)
                questionList.add(curQuestion)
            }
        }
        cursor.close()

        return questionList
    }

}