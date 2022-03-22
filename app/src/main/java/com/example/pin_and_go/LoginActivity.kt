package com.example.pin_and_go

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.pin_and_go.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

lateinit var loginBinding: ActivityLoginBinding
private lateinit var auth : FirebaseAuth
 private lateinit var pref: SharedPreferences

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(loginBinding.root)
        pref = getSharedPreferences("user_info", MODE_PRIVATE)
        loginBinding.registerTv.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        loginBinding.button.setOnClickListener {

            val ename = loginBinding.nameEt.toString()
            val epass = loginBinding.passEt.toString()
            if(ename != "" && epass != ""){

                auth = FirebaseAuth.getInstance()
                auth.signInWithEmailAndPassword(ename+"@gmail.com", epass)
                    .addOnCompleteListener(this) {
                        if (it.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Login", "signInWithEmail:success")
                            startActivity(Intent(this, MainActivity::class.java))
                            val currentUser = auth.currentUser
                            val userId:String = currentUser!!.uid
                            val creator = pref.edit()
                            creator.putString("Name", ename)
                            creator.putString("UserId", userId)
                            creator.apply()
                            Log.d("Name", pref.getString("Name", null).toString())
                            Log.d("UserId", pref.getString("UserId", null).toString())
                            finish()
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                        }
                    }


            }else{
                Toast.makeText(this, "Попробуйте еще раз!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}