package com.example.quizapp

class FullQuiz {
    private var _id = 0L
    private var themeName = ""
    private var difficulty = ""
    private var questionList: List<QuizQuestion> = listOf()

    fun MakeQuiz (themeName: String, difficulty: String, questionList: List<QuizQuestion>){
        this.themeName=themeName
        this.difficulty=difficulty
        this.questionList=questionList
    }

    fun makeQuiz (_id: Long, themeName: String, difficulty: String, questionList: List<QuizQuestion>){
        this._id=_id
        this.themeName=themeName
        this.difficulty=difficulty
        this.questionList=questionList
    }

    fun PrintQuiz (){
        for (i in 0..4) {
            questionList[i].printQuestion()
        }
    }

    fun getTheme(): String{
        return themeName
    }

    fun getDifficulty(): String{
        return difficulty
    }

    fun getQuestions():List<QuizQuestion>{
        return questionList
    }
}