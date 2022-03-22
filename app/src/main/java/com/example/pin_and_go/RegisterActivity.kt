package com.example.pin_and_go

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.pin_and_go.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

lateinit var registerBinding: ActivityRegisterBinding
private lateinit var auth : FirebaseAuth
lateinit var databaseReference: DatabaseReference
private lateinit var pref: SharedPreferences

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        registerBinding = ActivityRegisterBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(registerBinding.root)
        pref = getSharedPreferences("user_info", MODE_PRIVATE)
        registerBinding.loginTv.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        registerBinding.register.setOnClickListener{
            var name = registerBinding.editTextTextPersonName.toString()
            var pass = registerBinding.editTextTextPassword.toString()
            if(name == "") {
                Toast.makeText(this , "Вы не ввели имя" , Toast.LENGTH_SHORT).show()
            }
            if(pass == "") {
                Toast.makeText(this , "Вы не ввели пароль" , Toast.LENGTH_SHORT).show()
            } else{
                auth = FirebaseAuth.getInstance()
                auth.createUserWithEmailAndPassword(name+"@gmail.com", pass)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            val currentUser = auth.currentUser
                            val userId : String = currentUser!!.uid
                            databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId)
                            var hashMap : HashMap<String , String> = HashMap()
                            hashMap.put("userId" , userId)
                            hashMap.put("username" , name)
                            databaseReference.setValue(hashMap).addOnCompleteListener(this) {
                                if (it.isSuccessful) {
                                    Toast.makeText(this , userId , Toast.LENGTH_SHORT).show()
                                    startActivity(Intent(this , MainActivity::class.java))
                                    val creator = pref.edit()
                                    creator.putString("Name" , name)
                                    creator.putString("UserId" , userId)
                                    creator.apply()
                                    Log.d("Name" , pref.getString("Name" , null).toString())
                                    Log.d("UserId" , pref.getString("UserId" , null).toString())
                                    finish()
                                }
                            }
                        } else {
                            Toast.makeText(this , "Try again!" , Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }
}