package com.example.android2

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button : Button = findViewById(R.id.button_ok)

        val textView : TextView = findViewById(R.id.textView)
        val textView2 : TextView = findViewById(R.id.textView2)

        button.setOnClickListener { v ->
            val dataClass = DataClass(5, "Hello World")
            textView.setText("a: ${dataClass.a} \ns: ${dataClass.s}")

            val obj = ObjectExample
            textView2.setText(obj.copy.toString())
        }


        val cycles = Cycles()
        cycles.hw()
    }
}