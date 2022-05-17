package com.hindu.quizzy

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        signUpBtn.setOnClickListener {
            signUp()
        }

        toSignIn.setOnClickListener {
            val intent = Intent(this,SignInActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun signUp(){
        val fullname = fullName.text.toString()
        val email = email_signup.text.toString()
        val password = password_signup.text.toString()

        when {
            TextUtils.isEmpty(fullname.toString())->{
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show()
            }
            TextUtils.isEmpty(email_signup.toString())->{
                Toast.makeText(this, "Please enter the email", Toast.LENGTH_SHORT).show()
            }
            TextUtils.isEmpty(email_re_enter.toString())->{
                Toast.makeText(this, "Please Re-enter the email", Toast.LENGTH_SHORT).show()
            }
            TextUtils.isEmpty(password_signup.toString())->{
                Toast.makeText(this, "Please enter the password", Toast.LENGTH_SHORT).show()
            }
            else->{
                val progressDialog = ProgressDialog(this)
                progressDialog.setTitle("Please Wait")
                progressDialog.setCanceledOnTouchOutside(false)
                progressDialog.show()

                val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

                mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener { task->
                        if (task.isSuccessful){
                            saveData(fullname,email,password,progressDialog)
                        }else{
                            val message = task.exception.toString()
                            Toast.makeText(this, "Some Error Occurred:$message", Toast.LENGTH_SHORT).show()
                            mAuth.signOut()
                            progressDialog.dismiss()
                        }
                    }
            }

        }

    }

    private fun saveData(userName: String, emailId: String, password: String, progressDialog: ProgressDialog) {
        val currentUserId = FirebaseAuth.getInstance().currentUser!!.uid
        val firebaseUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser
        val database = Firebase.database
        val userRef = database.reference.child("Users")

        val dataMap = HashMap<String,Any>()
        dataMap["uid"] = currentUserId
        dataMap["userName"] = userName
        dataMap["emailId"] = emailId
        dataMap["password"] = password

        userRef.child(currentUserId).setValue(dataMap).addOnCompleteListener {task->
            if (task.isSuccessful){
                progressDialog.dismiss()

                Toast.makeText(this,"SignUp Success",Toast.LENGTH_LONG).show()

                val intent = Intent(this@SignUpActivity,MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()

            }else{
                val message = task.exception.toString()
                Toast.makeText(this,"Some Error Occurred:$message",Toast.LENGTH_LONG).show()
                FirebaseAuth.getInstance().signOut()
                progressDialog.dismiss()
            }
        }
    }
}