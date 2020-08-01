package com.example.databasetest

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri

class DatabaseProvider : ContentProvider() {

    //定义4个变量 分别访问2个表的 所有数据 和 单条数据
    private val bookDir = 0
    private val bookItem = 1
    private val categoryDir = 2
    private val categoryItem = 3

    private val authority = "com.example.databasetest.provider"
    private var dbHelper: MyDatabaseHelper? = null

    //by lazy 懒加载技术 当 UriMatcher 首次被调用时候 被执行 且将最后一行代码的返回值 赋予 UriMatcher
    private val uriMatcher by lazy {
        //对 UriMatcher 初始化
        val matcher = UriMatcher(UriMatcher.NO_MATCH)

        matcher.addURI(authority, "book", bookDir)
        matcher.addURI(authority, "book/#", bookItem)
        matcher.addURI(authority, "category", categoryDir)
        matcher.addURI(authority, "category/#", categoryItem)
        matcher
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?) = dbHelper?.let {

        val db = it.writableDatabase
        val deletedRows = when (uriMatcher.match(uri)){

            bookDir -> db.delete("Book", selection, selectionArgs)

            bookItem -> {
                val bookId = uri.pathSegments[1]
                db.delete("Book", "id = ?", arrayOf(bookId))
            }

            categoryDir -> db.delete("Category", selection, selectionArgs)

            categoryItem -> {
                val bookId = uri.pathSegments[1]
                db.delete("Category", "id = ?", arrayOf(bookId))
            }
            else -> 0
        }
        deletedRows
    }?: 0

    override fun getType(uri: Uri) = when (uriMatcher.match(uri)){

        bookDir -> "vnd.android.cursor.dir/vnd.com.example.databasetest.provider.book"
        bookItem -> "vnd.android.cursor.dir/vnd.com.example.databasetest.provider.book"
        categoryDir -> "vnd.android.cursor.dir/vnd.com.example.databasetest.provider.category"
        categoryItem -> "vnd.android.cursor.dir/vnd.com.example.databasetest.provider.category"

        else -> null
    }

    // insert方法返回一个能表示新增数据的 URI
    // 调用 Uri.parse 将 内容URI 析构成 Uri
    override fun insert(uri: Uri, values: ContentValues?)  = dbHelper?.let {

        val db = it.writableDatabase
        val uriReturn = when (uriMatcher.match(uri)){
            bookDir, bookItem -> {
                val newBookId = db.insert("Book", null, values)
                Uri.parse("content://$authority//book//$newBookId")
            }

            categoryDir, categoryItem -> {
                val newCategoryId = db.insert("Category", null, values)
                Uri.parse("content://$authority//book//$newCategoryId")
            }
            else -> null
        }
        uriReturn
    }

    /*
    调用 getContext 语法糖借助 ?. 和 let 判断返回值是否为空
    若为空 使用 ?. 返回false  ContentProvide 初始化失败
    若不为空 则运行 let 中的语句
     */
    override fun onCreate() = context?.let {
        //构造一个 MyDatabaseHelper对象 确定 名称和版本号
        //当版本号 大于1 可执行onUpgrade
        dbHelper = MyDatabaseHelper(it, "BookStore.db", 2)
        //表示 初始化成功
        true
    } ?: false

    /*
    先获取 SQLiteDatabase 实例
    根据传入的 Uri 判断需要访问的表
    调用 query 查询 并返回 Cursor对象
     */
    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ) = dbHelper?.let {

        //读取数据库
        val db = it.readableDatabase
        val cursor = when (uriMatcher.match(uri)){
            bookDir -> db.query("Book", projection, selection, selectionArgs,
            null, null ,sortOrder)

            //pathSegments 会将 Uri 按照 / 分割 0：路径 1：id
            //得到 id 以后 通过selection, selectionArgs 约束 实现单句查找功能
            bookItem -> {
                val bookId = uri.pathSegments[1]
                db.query("Book", projection, "id = ?", arrayOf(bookId), null, null ,sortOrder)
            }

            categoryDir -> db.query("Category", projection, selection, selectionArgs,
                null, null ,sortOrder)

            categoryItem -> {
                val bookId = uri.pathSegments[1]
                db.query("Category", projection, "id = ?", arrayOf(bookId), null, null ,sortOrder)
            }

            else -> null
        }
        cursor
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    )= dbHelper?.let {

        //对数据库改写
        val db = it.writableDatabase
        val updatedRows = when (uriMatcher.match(uri)){

            bookDir -> db.update("Book", values, selection, selectionArgs)

            bookItem -> {
                val boodId = uri.pathSegments[1]
                db.update("Book", values, "id = ?", arrayOf(boodId))
            }

            categoryDir -> db.update("Category", values, selection, selectionArgs)

            categoryItem -> {
                val boodId = uri.pathSegments[1]
                db.update("Category", values, "id = ?", arrayOf(boodId))
            }
            else -> 0
        }
        //受影响的行数 将作为返回值返回
        updatedRows
    }?: 0
}
