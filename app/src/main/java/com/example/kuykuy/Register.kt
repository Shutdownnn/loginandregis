package com.example.kuykuy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class Register : AppCompatActivity() {
    lateinit var txtEmailcreate: EditText
    lateinit var txtPasswordcreate: EditText
    lateinit var buttonSubmit: Button

    lateinit var email: String
    lateinit var password: String

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        txtEmailcreate = findViewById<EditText>(R.id.txtEmailcreate!!)
        txtPasswordcreate = findViewById<EditText>(R.id.txtPasswordcreate!!)
        buttonSubmit = findViewById<Button>(R.id.buttonSubmit!!)

        mAuth = FirebaseAuth.getInstance()
        buttonSubmit!!.setOnClickListener {
            createAccount()
        }
    }

    override fun onStart(){
        super.onStart()
        val currentUser = mAuth!!.currentUser
        updateUI(currentUser)
    }

    private fun  createAccount(){
        email = txtEmailcreate!!.text.toString()
        password = txtPasswordcreate!!.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {

            mAuth!!.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("MyApp","Create New User Success!")
                    val user = mAuth!!.currentUser
                    updateUI(user)
                } else{
                    Log.w("MyApp","Failure Process!",task.exception)
                    Toast.makeText(this@Register,"Authentication Failed",Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
        }else {
            Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI(user: FirebaseUser?){
        if(user != null){
            val email = user.email
            Toast.makeText(this@Register,"Welcome: $email",Toast.LENGTH_SHORT).show()
            val intent = Intent(this,List::class.java)
            startActivity(intent)
        }
    }
}