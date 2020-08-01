package com.example.contactstest

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.media.projection.MediaProjection
import android.net.Uri
import java.nio.ByteOrder

//创建自己的 ContentProvider
class Myprovider : ContentProvider() {

    private val table1Dir = 0
    private val table1Item = 1
    private val table2Dir = 2
    private val table2Item = 3

    //借助 UriMatcher 类 快速匹配URI 判断希望访问的表
    //创建实例
    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

    init {
        //将 URI 的3部分 传入
        uriMatcher.addURI("com.example.app.provider", "table1", table1Dir)
        uriMatcher.addURI("com.example.app.provider", "table1/#", table1Item)

        uriMatcher.addURI("com.example.app.provider", "table2", table2Dir)
        uriMatcher.addURI("com.example.app.provider", "table2/#", table2Item)
    }

    override fun onCreate(): Boolean {
        return false
    }

    //当 query 被调用时，通过 match 对传入 Uri 进行匹配
    //若成功则返回自定义代码
    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        /*
        when (uriMatcher.match(uri)){

            table1Dir -> {
                //查询table1表中的所有数据
                }

            table1Item -> {
                //查询table1表中的单条数据
                }

            table2Dir -> {
                //查询table2表中的所有数据
                }

            table2Item -> {
                //查询table2表中的单条数据
                }
            }
        */
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
    }

    //用于获取 Uri对象的MIME 类型
    override fun getType(uri: Uri) = when (uriMatcher.match(uri)) {

        table1Dir -> "vnd.android.cursor.dir/vnd.com.example.app.provider.table1"
        table1Item -> "vnd.android.cursor.dir/vnd.com.example.app.provider.table1"
        table2Dir -> "vnd.android.cursor.dir/vnd.com.example.app.provider.table2"
        table2Item -> "vnd.android.cursor.dir/vnd.com.example.app.provider.table2"
        else -> null
    }
}