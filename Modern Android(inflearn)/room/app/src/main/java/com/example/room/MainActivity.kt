package com.example.room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.room.Room
import com.example.room.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "database-name")
            .allowMainThreadQueries().build()


        // this(=MainActivity) 에서 getAll()의 결과를 항시 관측할꺼임, 바뀐게 있다면(onChanged) {} 안의 람다 수행
        db.todoDao().getAll().observe(this) {
            binding.resultText.text = it.toString()
        }

        binding.addButton.setOnClickListener {
            db.todoDao().insert(Todo(binding.todoEdit.text.toString()))
        }

    }
}