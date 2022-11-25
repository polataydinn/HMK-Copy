package com.application.hmkcopy.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.application.hmkcopy.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        instance = this
    }

    companion object {
        var instance: MainActivity? = null
    }
}