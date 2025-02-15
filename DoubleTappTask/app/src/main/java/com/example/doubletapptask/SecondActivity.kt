package com.example.doubletapptask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.doubletapptask.databinding.ActivitySecondBinding


class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Log", "SecondActivity onCreate")
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val number = intent.getIntExtra(MainActivity.EXTRA_NUMBER, 0)
        binding.numTextView3.text = (number * number).toString()

        binding.button3.setOnClickListener {
            finish()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(MainActivity.NUMBER, binding.numTextView3.text.toString().toInt())
        super.onSaveInstanceState(outState)
        Log.d("Log","SecondActivity onSaveInstanceState")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        binding.numTextView3.text = savedInstanceState.getInt(MainActivity.NUMBER, 1).toString()
        super.onRestoreInstanceState(savedInstanceState)
        Log.d("Log","SecondActivity onRestoreInstanceState")
    }

    override fun onStart() {
        super.onStart()
        Log.d("Log","SecondActivity onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("Log","SecondActivity onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("Log","SecondActivity onPause")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("Log","SecondActivity onRestart")
    }

    override fun onStop() {
        super.onStop()
        Log.d("Log","SecondActivity onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Log","SecondActivity onDestroy")
    }
}