package com.example.noteapp_using_crud.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Notes(
    @PrimaryKey(autoGenerate = true)
    val id:Int?=null,

    val title:String,
    val content:String

)
