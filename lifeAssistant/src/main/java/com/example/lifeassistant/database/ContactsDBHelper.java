package com.example.lifeassistant.database;


import com.example.lifeassistant.util.Constants;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class ContactsDBHelper extends SQLiteOpenHelper {
	public ContactsDBHelper(Context context) {
		super(context, Constants.DATABASE_NAME, null,
				Constants.DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	public ContactsDBHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}



	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String createTable = "create table " + Constants.TABLE_NAME + "("
				+ Constants.C_ID + " integer primary key autoincrement ,"
				+ Constants.C_CONTACTS_NAME + " text(50) ,"
				+ Constants.C_CONTACTS_NUMBER + " text(20)" + ")";
		db.execSQL(createTable);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
