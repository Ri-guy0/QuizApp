package com.example.quizapp.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.example.quizapp.localclasses.QuizQuestion

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

    /*
    * Adds data to the database
    * @param       curQuestion            the question to add to teh database
    * @param       quizId                 the quiz id to assign to the database
    **/
    fun addData(curQuestion: QuizQuestion, quizId: Long) {
        //Log.e("Made", quizId.toString())
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

    /*
    * Loads questions for a specific quiz
    * @param       quizId                        the quiz id to use to load the questions
    * @return      MutableList<QuizQuestions>    the list of questions for the quiz
    **/
    fun loadQuizQuestions(quizId: Long): MutableList<QuizQuestion> {
        //Log.e("Loaded", quizId.toString())
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
                //Log.e("length", curIncorrect.size.toString())
            }
        }
        cursor.close()

        return questionList
    }

    /*
    * Deletes the questions for a specific quiz
    * @param       quizId                 the quiz id to delete from the database
    **/
    fun deleteQuizQuestion (quizId: Long){
        val select = "${QuestionContract.QuestionEntry.COL_QUIZ_ID} = ?"
        val selectArg = arrayOf(quizId.toString())

        this.writableDatabase.delete(
            QuestionContract.QuestionEntry.TABLE_NAME,
            select,
            selectArg)

    }

}