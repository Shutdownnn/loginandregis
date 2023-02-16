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
import kotlin.collections.List

class MainActivity : AppCompatActivity() {
    lateinit var txtEmail: EditText
    lateinit var txtPassword: EditText
    lateinit var buttonLogin: Button
    lateinit var buttonRegister: Button

    lateinit var email: String
    lateinit var password: String

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtEmail = findViewById<EditText>(R.id.txtEmail!!)
        txtPassword = findViewById<EditText>(R.id.txtPassword!!)
        buttonLogin = findViewById<Button>(R.id.buttonLogin!!)
        buttonRegister = findViewById<Button>(R.id.buttonRegister!!)

        mAuth = FirebaseAuth.getInstance()

        buttonRegister!!.setOnClickListener {
            val intent = Intent(this@MainActivity,Register::class.java)
            startActivity(intent)
        }

        buttonLogin!!.setOnClickListener {
            loginEmail()
        }
    }

    override fun onStart(){
        super.onStart()
        val currentUser = mAuth!!.currentUser
        updateUI(currentUser)
    }

    private fun loginEmail(){
        email = txtEmail!!.text.toString()
        password = txtPassword!!.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {

            mAuth!!.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("MyApp","Create New User Success!")
                    val user = mAuth!!.currentUser
                    updateUI(user)
                    } else{
                        Log.w("MyApp","Failure Process!",task.exception)
                        Toast.makeText(this@MainActivity,"Authentication Failed",Toast.LENGTH_SHORT).show()
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
            Toast.makeText(this@MainActivity,"Welcome: $email",Toast.LENGTH_SHORT).show()
            val intent = Intent(this,List::class.java)
            startActivity(intent)
        }
    }
}