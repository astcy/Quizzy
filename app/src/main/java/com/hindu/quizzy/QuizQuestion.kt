package com.hindu.unity_quizz

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat

class QuizQuestionActivity : AppCompatActivity(),View.OnClickListener {

    private var mCurrentPosition: Int = 1
    private var mQuestionList:ArrayList<Question>? = null
    private var mSelectedOptionPosition : Int = 0
    private var mUserName : String? = null
    private var mCorrectAnswers : Int = 0

    private var progressBar: ProgressBar? = null
    private var tvProgressBar: TextView? = null
    private var tvQuestion: TextView? = null
    private var ivImage: ImageView? = null

    private var tvOptionOne: TextView? = null
    private var tvOptionTwo: TextView? = null
    private var tvOptionThree: TextView? = null
    private var tvOptionFour: TextView? = null
    private var btn_submit : Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz_question)

        mUserName = intent.getStringExtra(Constants.USER_NAME)

        progressBar = findViewById(R.id.progressbar)
        tvProgressBar = findViewById(R.id.tv_progress)
        tvQuestion = findViewById(R.id.tv_question)
        ivImage =  findViewById(R.id.iv_image)
        tvOptionOne = findViewById(R.id.option_one)
        tvOptionTwo = findViewById(R.id.option_two)
        tvOptionThree = findViewById(R.id.option_three)
        tvOptionFour = findViewById(R.id.option_four)
        btn_submit = findViewById(R.id.btn_submit)

        tvOptionOne?.setOnClickListener(this)
        tvOptionTwo?.setOnClickListener(this)
        tvOptionThree?.setOnClickListener(this)
        tvOptionFour?.setOnClickListener(this)
        btn_submit?.setOnClickListener  (this)

        mQuestionList = Constants.getQuestion()
        setQuestion()

    }

    private fun setQuestion() {

        defaultOptionsView()
        val question: Question = mQuestionList!![mCurrentPosition - 1]
        ivImage?.setImageResource(question.image)
        progressBar?.progress = mCurrentPosition
        tvProgressBar?.text = "$mCurrentPosition/${progressBar?.max}"
        tvQuestion?.text = question.question
        tvOptionOne?.text = question.optionOne
        tvOptionTwo?.text = question.optionTwo
        tvOptionThree?.text = question.optionThree
        tvOptionFour?.text = question.optionFour


        if(mCurrentPosition == mQuestionList!!.size){
            btn_submit?.text="Finish"
        }else{
            btn_submit?.text = "SUBMIT"
        }
    }

    private fun defaultOptionsView(){
        val options = ArrayList<TextView>()
        tvOptionOne?.let {
            options.add( 0, it)
        }
        tvOptionTwo?.let {
            options.add(1,it)
        }
        tvOptionThree?.let {
            options.add(2,it)
        }
        tvOptionFour?.let {
            options.add(3,it)
        }
        for(option in options ){
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT
            option.background  = ContextCompat.getDrawable(
                this,
                R.drawable.default_option_border_bg
            )
        }


    }

    private fun  selectedOptionView(tv:TextView,selectedOptionNum:Int){
        defaultOptionsView()

        mSelectedOptionPosition = selectedOptionNum

        tv.setTextColor(Color.parseColor("#363A43"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(
            this,
            R.drawable.selected_option_border_bg
        )

    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.option_one -> {
                tvOptionOne?.let {
                    selectedOptionView(it, 1)
                }
            }
            R.id.option_two -> {
                tvOptionTwo?.let {
                    selectedOptionView(it, 2)
                }
            }
            R.id.option_three -> {
                tvOptionThree?.let {
                    selectedOptionView(it, 3)
                }
            }
            R.id.option_four -> {
                tvOptionFour?.let {
                    selectedOptionView(it, 4)
                }
            }
            R.id.btn_submit -> {
                if (mSelectedOptionPosition == 0){
                    mCurrentPosition++

                    when{
                        mCurrentPosition <= mQuestionList!!.size ->{
                            setQuestion()
                        }
                        else ->{

                            val intent = Intent(this,Result_activity::class.java)
                            intent.putExtra(Constants.USER_NAME , mUserName)
                            intent.putExtra(Constants.CORRECT_ANSWERS , mCorrectAnswers )

                            intent.putExtra(Constants.TOTAL_QUESTIONS , mQuestionList?.size)
                            startActivity(intent)
                            finish()
                        }
                    }
                } else {
                    val question = mQuestionList?.get(mCurrentPosition -1)
                    if (question!!.correctAnswer != mSelectedOptionPosition){
                        answerView(mSelectedOptionPosition,R.drawable.wrong_option_border_bg )
                    }else{
                        mCorrectAnswers++
                    }
                    answerView(question.correctAnswer,R.drawable.correct_option_border_bg)

                    if(mCurrentPosition == mQuestionList!!.size){
                        btn_submit?.text = "FINISH"
                    }else{
                        btn_submit?.text = "GO TO NEXT QUESTION"
                    }

                    mSelectedOptionPosition = 0
                }
            }

        }
    }
    private fun answerView(answer: Int, drawableView : Int){
        when (answer){
            1 -> {
                tvOptionOne?.background = ContextCompat.getDrawable(
                    this,
                    drawableView

                )
            }
            2-> {
                tvOptionTwo?.background = ContextCompat.getDrawable(
                    this,
                    drawableView

                )
            }
            3->{
                tvOptionThree?.background = ContextCompat.getDrawable(
                    this,
                    drawableView

                )
            }
            4 ->{
                tvOptionFour?.background = ContextCompat.getDrawable(
                    this,
                    drawableView

                )
            }
        }

    }

}