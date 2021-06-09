package com.example.cnblog.database;

import com.example.cnblog.instance.AppStatic;
import com.example.cnblog.instance.Article;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SqlArticlesHelper extends SQLiteOpenHelper {

	
	private static final String TB_NEWS = "newstab";
	private static final String TB_BLOGS = "blogstab";
	private  String dbname ;

	public SqlArticlesHelper(Context context, String name) {
		super(context, name, null, 1);
		this.dbname=name;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String sqlnews = "create table " + " if not exists " + TB_NEWS
				+ "(_id integer primary key autoincrement not null,"
				+ "url varchar(100)  unique not null, "
				+ "title varchar(200) not null," + "newsId int not null)";
		db.execSQL(sqlnews);
		String sqlblogs = "create table " + " if not exists " + TB_BLOGS
				+ "(_id integer primary key autoincrement,"
				+ "blogTitle varchar(200) ," + "bloger    varchar(100) ,"+"avatar  varchar(200),"
				+ "blogerUrl varchar(200) ," + "blogId    integer unique,"+ "blogApp    varchar(100) ,"
				+ "blogSummary varchar(200)," + "storeTime long ,"
				+ "updateTime varchar ," + "blogText  blob ,"
				+ "blogUrl   varchar(200) not null)";
		db.execSQL(sqlblogs);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	public void insertNews(int pos) {
		SQLiteDatabase db = getWritableDatabase();
		String sql = "insert into " + TB_NEWS
				+ " (url,title,newsId) values (?,?,?)";
		Article art = AppStatic.articles.get(pos);
		String args[] = { art.getUrl(), art.getTitle(), art.getId() + "" };
		
		db.execSQL(sql, args);
	}

	public Cursor queryAllNews() {
		SQLiteDatabase db = getWritableDatabase();
		String sql = "select * from " + TB_NEWS;
		Cursor all = db.rawQuery(sql, null);
		return all;

	}
	public Cursor queryById(int i) {
		SQLiteDatabase db = getWritableDatabase();
		String sql = "select * from " + TB_NEWS+" where newsId="+i;
		Cursor all = db.rawQuery(sql, null);
		return all;

	}
	public void deletebyId(int id){
		SQLiteDatabase db=getWritableDatabase();
		String sql="delete from " + TB_NEWS + " where newsId=" + id;
		db.execSQL(sql);
	}
	public void deleteBlogbyId(int id){
		SQLiteDatabase db=getWritableDatabase();
		String sql="delete from " + TB_BLOGS + " where blogId=" + id;
		db.execSQL(sql);
	}
	public void deleteAllBlog(){
		SQLiteDatabase db=getWritableDatabase();
		String sql="delete from " + TB_BLOGS ;
		db.execSQL(sql);
	}
	public Cursor queryBlogById(int id) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = getWritableDatabase();
		String sql = "select * from " + TB_BLOGS+" where blogId="+id;
		Cursor all = db.rawQuery(sql, null);
		return all;
		
	}
	
	public Cursor queryAllBlogs() {
		SQLiteDatabase db = getWritableDatabase();
		String sql = "select * from " + TB_BLOGS;
		Cursor all = db.rawQuery(sql, null);
		return all;

	}
	public long insertBlog(int pos,String text) {
		// TODO Auto-generated method stub
		SQLiteDatabase db = getWritableDatabase();
		ContentValues cv=new ContentValues();
		cv.clear();
		Article art = AppStatic.articles.get(pos);
		String blogSummary = art.getSummary();
		String blogTitle = art.getTitle();
		String bloger = art.getSourceName();
		String blogerUrl = art.getAuthorurl();
		String blogLink = art.getUrl();
		String blogerAvatar=art.getAvatar();
		String blogerApp=art.getBlogApp();
		long storeTime = System.currentTimeMillis();
		String updateTime = art.getUpdated();
		int blogId = art.getId();
		Log.i("msg", bloger);
		cv.put("blogSummary", blogSummary);
		cv.put("blogTitle", blogTitle);
		cv.put("bloger", bloger);
		cv.put("blogerUrl", blogerUrl);
		cv.put("blogUrl", blogLink);
		cv.put("storeTime", storeTime);
		cv.put("updateTime", updateTime);
		cv.put("blogId", blogId);
		cv.put("avatar", blogerAvatar);
		cv.put("blogApp", blogerApp);
		cv.put("blogText", text);
		long i=db.insert(TB_BLOGS, null, cv);
		return i;
	}
}
