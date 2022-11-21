package com.example.seoulpublicswimmingpool.database

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DBHelper(context: Context, dbName: String, version: Int) :
    SQLiteOpenHelper(context, dbName, null, version) {
    /*create table*/
    override fun onCreate(db: SQLiteDatabase?) {
        val query = """
            CREATE TABLE seoulSwimLessonTBL(
            id TEXT PRIMARY KEY,
            center TEXT,
            week TEXT,
            time TEXT,
            fee TEXT
            )
        """.trimIndent()
        db?.execSQL(query)
    }

    /*Callback function when version changes*/
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val query = """
            DROP TABLE seoulSwimLessonTBL
        """.trimIndent()
        db?.execSQL(query)
        this.onCreate(db)
    }

    /*Load all data*/
    fun selectAll(): ArrayList<SwimLesson>? {
        var lessonList: ArrayList<SwimLesson>? = arrayListOf<SwimLesson>()
        var cursor: Cursor? = null
        val db = this.readableDatabase
        val query = """
            SELECT * FROM seoulSwimLessonTBL
        """.trimIndent()
        try {
            cursor = db.rawQuery(query, null)
            if (cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(0)
                    val center = cursor.getString(1)
                    val week = cursor.getString(2)
                    val time = cursor.getString(3)
                    val fee = cursor.getString(4)
                    val swimLesson = SwimLesson(id, center, week, time, fee)
                    lessonList?.add(swimLesson)
                    Log.d("seoulpublicswimmingpool", "selectAll() SUCCESS")
                }
            } else {
                lessonList = null
            }
        } catch (e: Exception) {
            Log.d("seoulpublicswimmingpool", "selectAll() ${e.printStackTrace()}")
        } finally {
            cursor?.close()
            db.close()
        }
        return lessonList
    }

    /*put data into table*/
    fun insertData(swimLesson: SwimLesson): Boolean {
        var flag = false
        val query = """
            INSERT INTO seoulSwimLessonTBL(id, center, week, time, fee)
            VALUES('${swimLesson.id}', '${swimLesson.center}','${swimLesson.week}','${swimLesson.time}', '${swimLesson.fee}')
        """.trimIndent()
        val db = this.writableDatabase
        try{
            db.execSQL(query)
            flag = true
            Log.d("seoulpublicswimmingpool", "insertData() SUCCESS")
        }catch(e:Exception){
            Log.d("seoulpublicswimmingpool", "insertData() ${e.printStackTrace()}")
        }finally {
            db.close()
        }
        return flag
    }

    /*Load data of searched items*/
    fun searchCenter(query: String): MutableList<SwimLesson>? {
        var lessonList: MutableList<SwimLesson>? = mutableListOf<SwimLesson>()
        var cursor: Cursor? = null
        val db = this.readableDatabase
        val query = """
            SELECT * FROM seoulSwimLessonTBL WHERE center LIKE '${query}%'
        """.trimIndent()
        try {
            cursor = db.rawQuery(query, null)
            if (cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(0)
                    val center = cursor.getString(1)
                    val week = cursor.getString(2)
                    val time = cursor.getString(3)
                    val fee = cursor.getString(4)
                    val swimLesson = SwimLesson(id, center, week, time, fee)
                    lessonList?.add(swimLesson)
                    Log.d("seoulpublicswimmingpool", "serchCenter() SUCCESS")
                }
            } else {
                lessonList = null
            }
        } catch (e: Exception) {
            Log.d("seoulpublicswimmingpool", "serchCenter() ${e.printStackTrace()}")
        } finally {
            cursor?.close()
            db.close()
        }
        return lessonList
    }
}