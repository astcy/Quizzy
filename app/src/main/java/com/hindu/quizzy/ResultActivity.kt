package com.hindu.quizzy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val tvName : TextView = findViewById(R.id.tv_name)
        val tvScore : TextView = findViewById(R.id.tv_score)
        val btnzFinish : Button = findViewById(R.id.btn_finish)

        tvName.text = intent.getStringExtra(Constants.USER_NAME)

        val totalQuestion = intent.getIntExtra(Constants.TOTAL_QUESTIONS , 0)
        val correctAnswers = intent.getIntExtra(Constants.CORRECT_ANSWERS , 0 )

        tvScore.text = "Your score is $correctAnswers out of $totalQuestion"
        btnzFinish.setOnClickListener {
            tvScore.text = "Your score is $correctAnswers out of $totalQuestion"
            startActivity(Intent(this, MainActivity::class.java))
            uploadData(tvScore)

        }
    }


    private fun uploadData(tvScore:TextView){
        val ref = FirebaseDatabase.getInstance().reference.child("Results")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)
        val helpId = ref.push().key

        val postMap = HashMap<String,Any>()
        postMap["resultId"] = helpId!!
        postMap["resultValue"] = tvScore.text.toString()
        postMap["quizName"] = "C++"

        ref.child(helpId).updateChildren(postMap)

        Toast.makeText(this,"Result Saved", Toast.LENGTH_LONG).show()
    }

}