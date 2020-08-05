package com.example.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.first_layout.*

class FirstActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.first_layout)

        val person = Person("tom", 20)
        //person.name = "tom"
        //person.age = 20

        button1.setOnClickListener {

            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("person_data", person)
            startActivity(intent)
        }
    }
}