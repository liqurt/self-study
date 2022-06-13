package com.example.room

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) :
    AndroidViewModel(application) { //AndroidViewModel은 application을 생성자로 받음.

    class Factory(val application: Application) :
        ViewModelProvider.Factory { // 오준석 수업에 없던 2022식 솔루션
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(application) as T
        }
    }

    var todos: LiveData<List<Todo>>
    var newTodo: String? = null
    private var db: AppDatabase

    init {
        db = Room.databaseBuilder(application, AppDatabase::class.java, "database-name")
            .build() // init에서 db를 먼저 초기화 시켜야 함!
        todos = getAll()
    }


    fun getAll(): LiveData<List<Todo>> {
        if (db == null) { // IDE 에서 이것이 항상 false라고 했지만, db를 init 블록 밖에서 초기화 할때는 이 문장이 true가 되었음. 항상 체크하는 습관을 들이자... IDE 믿지마! 로그를 믿어!
            Log.d("AAAAA", "getAll: db is null// ${db}")
        } else {
            Log.d("AAAAA", "getAll: db is not null// ${db}")
        }
        return db.todoDao().getAll()
    }

    fun insert(todo: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            if(todo == null){
                Log.d("AAAAA", "insert: [TODO IS NULL]")
            }else{
                db.todoDao().insert(Todo(todo))
            }
        }
    }

    fun check() : Boolean{
        Log.d("AAAAA", "CHECK: ")
        Log.d("AAAAA", "TODOS: $todos")
        Log.d("AAAAA", "NEW_TODO: $newTodo")
        return true
    }

}