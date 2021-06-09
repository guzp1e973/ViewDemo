package com.example.lifeassistant.database;

import java.util.ArrayList;
import java.util.List;

import com.example.lifeassistant.bean.NotePad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;



/**
 * 
 * @author ChnAdo
 *	对 记事本的数据库进行操作
 *包括 新建记事本保存数据
 *更新记事本
 *获取记事本的总条数
 *删除某一条数据
 */
public class NotePadUpdate {
	Context context;
	MyDataBaseHelper dbHelper;
	/**
	 * 构造方法 实例化dbHelper
	 * @param context
	 */
	public NotePadUpdate(Context context){
		dbHelper = new MyDataBaseHelper(context);
	}
	
	/**
	 * 新建记事本保存数据
	 * @param values
	 * @param id
	 */
	public void saveDate(ContentValues values,String id){
		 SQLiteDatabase db = dbHelper.getWritableDatabase();
		 if (id==null) {
			db.insert("data", null, values);
			db.close();
		}else {
			db.update("data", values, "_id=?", new String[]{String.valueOf(id)});
			db.close();
		}
	}
	/**
	 * 更新记事本
	 * @param values
	 * @param id
	 */
	public void updateData(ContentValues values,String id){
		 SQLiteDatabase db = dbHelper.getWritableDatabase();
		 db.update("data", values, "_id=?", new String[]{String.valueOf(id)});
		 db.close();
	}
	/**
	 * 获取数据总数   --data表中的总数据数量 显示到屏幕的右上角
	 * @return 总数
	 */
	public long getCount(){
		 SQLiteDatabase db = dbHelper.getWritableDatabase();
		 Cursor cursor = db.rawQuery("select count(*) from data", null);
		 cursor.moveToFirst();
		 long count = cursor.getLong(0);
		 cursor.close();
		 db.close();
		 return count;
	}
	/**
	 * 删除一条记录
	 * @param id 要删除的条目的主键id
	 * @param context
	 */
	public void deleteData(long id,Context context){
		 SQLiteDatabase db = dbHelper.getWritableDatabase();
		 db.delete("data", "_id=?", new String[]{String.valueOf(id)});
		 db.close();
	}
/**
 * 查询出 数据库中所有的数据 并加入集合 待会显示到ListView上
 * @param name  表名
 * @return
 */
	public List<NotePad> adapterData(){
		List<NotePad> list = new ArrayList<NotePad>();
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Cursor cursor = db.query("data", null, null, null, null, null, null);
		while (cursor.moveToNext()) {
			int id = cursor.getInt(cursor.getColumnIndex("_id"));
			String title = cursor.getString(cursor.getColumnIndex("title"));
			String body = cursor.getString(cursor.getColumnIndex("body"));
			NotePad notepad = new NotePad(id, title, body);
			list.add(notepad);
		}
		cursor.close();
		db.close();
		return list;
	}
	
}
