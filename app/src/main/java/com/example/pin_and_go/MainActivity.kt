package com.example.pin_and_go

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pin_and_go.databinding.ActivityMainBinding

lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState : Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
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