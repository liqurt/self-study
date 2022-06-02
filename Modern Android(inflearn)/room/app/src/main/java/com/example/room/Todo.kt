package com.example.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Todo(
    var title: String,
){
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}

// 빌드오류 : var가 아닌 val 이면 setter가 없음.
