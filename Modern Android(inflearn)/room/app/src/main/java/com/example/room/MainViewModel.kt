package com.example.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.room.Room

class MainViewModel(application: Application) : AndroidViewModel(application) { //AndroidViewModel은 application을 생성자로 받음.

    private val db = Room.databaseBuilder(application, AppDatabase::class.java, "database-name")
        .build()

    fun getAll(): LiveData<List<Todo>> {
        return db.todoDao().getAll()
    }

    suspend fun insert(todo : Todo){ // suspend 안 찍어도, MainActivity에서 코루틴 안에 넣으면 그만이긴 함. 근데, 강제성을 부여해줄수 있음. 그러므로 suspend는 지우지 않곘다.
        db.todoDao().insert(todo)
    }

    override fun <T : Application?> getApplication(): T {
        return super.getApplication()
    }
}