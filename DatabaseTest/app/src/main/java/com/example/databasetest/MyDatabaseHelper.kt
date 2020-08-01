package com.example.databasetest

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast


class MyDatabaseHelper(val context: Context, name: String, version: Int):
    SQLiteOpenHelper(context, name, null, version){

    private val createBook = "create table Book(" +
            " id integer primary key autoincrement,"+
            " author text," +
            " price real," +
            " pages integer," +
            " name text, " +
            "category_id integer)"

    private val createCategory = "create table Category(" +
            " id integer primary key autoincrement,"+
            " category_name text," +
            " category_code intger )"

    override fun onCreate(db: SQLiteDatabase) {
        // execSQL 执行建表语句
        db.execSQL(createBook)
        db.execSQL(createCategory)
        Toast.makeText(context, "Create succeeded", Toast.LENGTH_SHORT).show()
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

        // 当表存在时候 执行2条语句删除 重新创建  暴力升级法
        //db.execSQL("drop table if exists Book")
        //db.execSQL("drop table if exists Category")
        //onCreate(db)

        if (oldVersion <= 1){
            db.execSQL(createCategory)
        }

        if (oldVersion <= 2){
            db.execSQL("alter table Book add column category_id integer")
        }
    }
}