package com.example.pin_and_go

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pin_and_go.databinding.ActivityMainBinding

lateinit var binding: ActivityMainBinding
private lateinit var pref: SharedPreferences

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState : Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        pref = getSharedPreferences("user_info", MODE_PRIVATE)

        if (pref.getString("Name", null)?.toString() == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        supportFragmentManager.beginTransaction().replace(R.id.frame, MapFragment.newInstance()).commit()
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.map -> supportFragmentManager.beginTransaction().replace(R.id.frame, MapFragment.newInstance()).commit()
                R.id.loyalty -> supportFragmentManager.beginTransaction().replace(R.id.frame, LoyaltyFragment.newInstance()).commit()
                R.id.profile -> supportFragmentManager.beginTransaction().replace(R.id.frame, ProfileFragment.newInstance()).commit()
            }
            true
        }
    }
}