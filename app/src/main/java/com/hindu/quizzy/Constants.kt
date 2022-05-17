package com.hindu.quizzy

object Constants {
    const val USER_NAME : String = "user_name"
    const val TOTAL_QUESTIONS : String = "total_questions"
    const val CORRECT_ANSWERS:String = "correct_answer"
    fun getQuestion():ArrayList<Question>{
        val questionList = ArrayList<Question>()
        val que1 = Question(
            1, "What country does this flag belong ?",
            R.drawable.india,
            "India", "America",
            "Bombay","Uttar Pradesh",
            1
        )

        questionList.add(que1)
        val que2 = Question(
            2, "what country does this flag belongs to?",
            R.drawable.germany,
            "india","America",
            "germany","Pakistan",
            3
        )
        questionList.add(que2)


        return questionList

    }

}

