package com.hussein.startup

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Update


//TODO:  1- Convert Class to Entity
@Entity
data class Note(
    @PrimaryKey(autoGenerate = true) val ID: Int,
   @ColumnInfo(name = "Title") val Title: String,
    @ColumnInfo(name = "Description") val Description: String
                 )

//TODO: 2- Define Doa
@Dao
interface NoteDao{
     @Insert
     fun insert(vararg note: Note)
     @Delete
     fun delet(note: Note)
     @Update
     fun update(note: Note)

     @Query("Select * from  Note where Title like :title")
     fun loadByTitle(title:String):List<Note
             >
}
// TODO: 3- create database
@Database(entities = arrayOf(Note::class), version = 1)
abstract class NoteDatabase:RoomDatabase(){
    abstract fun NoteDao():NoteDao
}

//TODO: 4- Create database instance

class  DBManager{
    @Volatile
    private var instance:NoteDatabase?=null
    fun getDatabase(context: Context):NoteDatabase?{

        if (instance==null){
            synchronized(NoteDatabase::class.java){
                if (instance==null){
                    instance = Room.databaseBuilder(context.applicationContext,
                            NoteDatabase::class.java!!,"MyNotesDB")
                        .allowMainThreadQueries()
                        .build()
                }
            }
        }
        return instance
    }
}
