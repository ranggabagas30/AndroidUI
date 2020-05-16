package com.example.androidui.customview.tallycounter

interface TallyCounter {
    fun reset() // reset the counter
    fun increment() // increment counter
    fun getCount(): Int // the current count of the counter
    fun setCount(count: Int) // set counter value
}