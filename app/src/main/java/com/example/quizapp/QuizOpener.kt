package com.example.quizapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class QuizOpener(context: Context): SQLiteOpenHelper(context,
        DATABASE_NAME, null,
        DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(QuizContract.SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(QuizContract.SQL_DROP_TABLE)
        onCreate(db)
    }

    fun addData(curQuiz: FullQuiz, context: Context) {
        val values = ContentValues().apply {
            put(QuizContract.QuizEntry.COL_THEME, curQuiz.getTheme())
            put(QuizContract.QuizEntry.COL_DIFFICULTY, curQuiz.getDifficulty())
        }

        val newRowId = this.writableDatabase.insert(QuizContract.QuizEntry.TABLE_NAME, null, values)

        for (i in 0..4) {
            val curOpener = QuestionOpener(context)
            curOpener.addData(curQuiz.getQuestions()[i], newRowId)
        }
    }

    fun loadData(context: Context): MutableList<FullQuiz> {
        val savedList = mutableListOf<FullQuiz>()
        val cursor = this.readableDatabase.rawQuery("select * from " +QuizContract.QuizEntry.TABLE_NAME,null)

        with(cursor) {
            while (moveToNext()) {
                val curId = getLong(getColumnIndexOrThrow(BaseColumns._ID))
                val curTheme = getString(getColumnIndexOrThrow(QuizContract.QuizEntry.COL_THEME))
                val curDifficulty = getString(getColumnIndexOrThrow(QuizContract.QuizEntry.COL_DIFFICULTY))
                val curQuestion = QuestionOpener(context).loadQuizQuestions(curId)
                val curQuiz = FullQuiz()

                curQuiz.makeQuiz(curId, curTheme, curDifficulty, curQuestion)
                savedList.add(curQuiz)
            }
        }
        cursor.close()

        return savedList
    }

    companion object {
        const val DATABASE_VERSION=1
        const val DATABASE_NAME="QuizReader.db"
    }
}