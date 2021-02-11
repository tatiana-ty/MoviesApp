package com.example.android2

object ObjectExample {
    val dataClass = DataClass(5, "Hello World")
    val copy = dataClass.copy(s = "COPY");
}