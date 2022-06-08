package com.example.room

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.example.room.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val viewModel = ViewModelProviders.of(this)[MainViewModel::class.java]


        Log.d("AAAAA", "onCreate: ${viewModel.getApplication<Application>()}")

        // this(=MainActivity) 에서 getAll()의 결과를 항시 관측할꺼임, 바뀐게 있다면(onChanged) {} 안의 람다 수행
        viewModel.getAll().observe(this) {
            binding.resultText.text = it.toString()
        }

        binding.addButton.setOnClickListener {
            lifecycleScope.launch (Dispatchers.IO){
                viewModel.insert(Todo(binding.todoEdit.text.toString()))
            }
        }

    }
}