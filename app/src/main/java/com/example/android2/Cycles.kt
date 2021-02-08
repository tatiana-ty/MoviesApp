package com.example.android2

class Cycles {
    fun hw () {
        for(i in 1..10) {
            print("$i ")
        }
        println()

        for(i in 30 downTo 10 step 4) {
            print("$i ")
        }
        println()

        val someList: List<Int> = listOf(5, 4, 3, 2, 1)
        for(value in someList) {
            print("$value ")
        }
        println()

        for (i in 0 until someList.size) {
            print("$i ")
        }
    }
}