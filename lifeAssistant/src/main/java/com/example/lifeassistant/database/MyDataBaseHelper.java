package com.example.lifeassistant.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * 
 * @author ChnAdo
 *	记事本数据库的帮助类 用来新建数据库 新建数据表
 */
public class MyDataBaseHelper extends SQLiteOpenHelper {
	// 记事本数据库的名字
	private static final String DB_NAME = "notepad.db";
	// 记事本数据库的表名
	private static final String TABLE_NAME = "data";
	// 记事本数据库的版本号
	private static final int DB_VERSION = 1;
	// 创建表的语句；主键   title  body
	private static final String CREATE_TABLE = "create table "+TABLE_NAME+"(_id integer primary key autoincrement,title text(50) not null, body text not null)";
	//删除 清空表
	private static final String DROP_TABLE  ="drop if table exists "+TABLE_NAME;
//	SQlite 数据库的 数据类型：	integer		int 
//							text		varchar char  string 
//							blub		二进制类型
//							real		浮点类型   float  double 
//	弱类型 自动转型
	
	public MyDataBaseHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	// 当数据库初始化的时候 会执行一次------创建表
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE);//创建表
	}

	@Override
	// 比较newVersion 如果newVersion跟以前的Version 不一样 会回调一次 在这执行更新
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		//先备份数据
		db.execSQL(DROP_TABLE);
		onCreate(db);
	}

}
