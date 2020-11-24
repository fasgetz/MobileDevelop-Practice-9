package com.example.practice_9

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.lang.Exception


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun AddStudent(view: View?) {
        try {
            val intent = Intent(this@MainActivity, AddStudent::class.java)
            startActivity(intent)
        }
        catch (ex: Exception) {
            val myToast = Toast.makeText(this, ex.message, Toast.LENGTH_LONG)
            myToast.show()
        }

    }
}