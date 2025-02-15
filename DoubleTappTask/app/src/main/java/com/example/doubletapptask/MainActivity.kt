package com.example.doubletapptask

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.doubletapptask.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Log", "MainActivity onCreate")
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra(EXTRA_NUMBER, binding.numTextView.text.toString().toInt())
            startActivity(intent)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(NUMBER, binding.numTextView.text.toString().toInt())
        super.onSaveInstanceState(outState)
        Log.d("Log","MainActivity onSaveInstanceState")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        binding.numTextView.text = (savedInstanceState.getInt(NUMBER, 8)+1).toString()
        super.onRestoreInstanceState(savedInstanceState)
        Log.d("Log","MainActivity onRestoreInstanceState")
    }

    override fun onStart() {
        super.onStart()
        Log.d("Log","MainActivity onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("Log","MainActivity onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("Log","MainActivity onPause")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("Log","MainActivity onRestart")
    }

    override fun onStop() {
        super.onStop()
        Log.d("Log","MainActivity onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Log","MainActivity onDestroy")
    }

    companion object{
        const val EXTRA_NUMBER = "number"
        const val NUMBER = "num"
    }
}