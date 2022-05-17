package com.hindu.quizzy

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        signInBtn.setOnClickListener {
            logIn()
        }

        toSignup.setOnClickListener {
            val intent = Intent(this,SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }


    }

    private fun logIn(){
        val email = email_signin.text.toString()
        val password = password_signin.text.toString()

        when{
            TextUtils.isEmpty(email_signin.text.toString())->{
                Toast.makeText(this,"Please enter your email", Toast.LENGTH_SHORT).show()
            }
            TextUtils.isEmpty(password_signin.text.toString())->{
                Toast.makeText(this,"Please enter your Password", Toast.LENGTH_SHORT).show()
            }else->{
            val progressDialog  = ProgressDialog(this@SignInActivity)
            progressDialog.setMessage("Please wait")
            progressDialog.setCanceledOnTouchOutside(false)
            progressDialog.show()

            val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task->
                    if (task.isSuccessful){
                        FirebaseAuth.getInstance().currentUser!!.reload()?.addOnCompleteListener { task->

                                progressDialog.dismiss()
                                val intent = Intent(this@SignInActivity, MainActivity::class.java)
                                startActivity(intent)

                        }
                    }else{
                        progressDialog.dismiss()
                    }
                }

        }

        }
    }
}