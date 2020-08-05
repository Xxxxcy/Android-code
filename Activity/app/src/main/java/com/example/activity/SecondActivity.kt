package com.example.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.second_layout.*

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.second_layout)

        //val person = intent.getSerializableExtra("person_data") as Person
        val person = intent.getParcelableExtra<Person>("person_data") as Person

        button2.setOnClickListener {

            Toast.makeText(this, person.name, Toast.LENGTH_SHORT).show()

            //打印日志
            LogUtil.d("TAG", "debug log")

            LogUtil.w("TAG", "warn log")
        }
    }
}