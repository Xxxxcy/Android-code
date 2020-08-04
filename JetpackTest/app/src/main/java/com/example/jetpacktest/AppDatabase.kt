package com.example.jetpacktest

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

//Room 第三步 定义 Database
//定义：数据库版本号 包含哪些实体类 提供Dao层的访问实例
//@Database(version = 1, entities = [User::class])

//改写升级逻辑
//@Database(version = 2, entities = [User::class,Book::class])
@Database(version = 3, entities = [User::class, Book::class])


//AppDatabase 必须继承自  RoomDatabase 且必须声明为抽象类
//提供相应的抽象方法 获取之前编写的Dao的实例
abstract class AppDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun bookDao(): BookDao

    //编写一个单例模式 原则上只存在一份 AppDatabase实例
    companion object{

        //实现一个匿名类  表示数据库从1 升级到 2的时候 执行其中的逻辑
        private val MIGRATION_1_2 = object : Migration(1, 2) {

            //在 migrate方法中 编写建表语句
            //Book表汇总的建表语句必须和Book实体类中相同 不然会抛出异常
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("create table Book (id integer primary key autoincrement not null, name text not null, pages integer not null)")
            }
        }

        private val MIGRATION_2_3 = object : Migration(2, 3) {

            //在 migrate方法中 编写建表语句
            //Book表汇总的建表语句必须和Book实体类中相同 不然会抛出异常
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("alter table Book add column author text not null default 'unknown'")
            }
        }

        private var instance: AppDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): AppDatabase {
            //不为空直接返回
            instance?.let{
                return it
            }

            //否则 用 Room.databaseBuilder 创建一个 AppDatabase实例
            //第一个参数 必须使用 context.applicationContext 否则可能内存泄露
            //第二个参数 AppDatabase实例 的Class类型
            //第三个参数 数据库名
            return Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "app_database")
                .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                .build()
                .apply {
                    instance = this
                }
        }
    }

}