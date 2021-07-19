package com.labed.todo.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "todo_table")
@Parcelize
data class ToDoData(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var title: String,
    var priority: Priority,
    var description: String
) : Parcelable

/*
we should use TypeConverter because it is not Primitive Room does not allow
for object reference between entities
LIKE Priority
Priority.High -> String (when writing to DB)
String -> Priority.High (when reading from DB)
*/