package com.example.eightgame;

import android.R.string;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Checkable;

public class GameDb extends SQLiteOpenHelper{
	String create_state="create table state("
					   +"id text,"
					   +"way text)";
	
	public GameDb(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(create_state);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	public static String findWay(String id,SQLiteDatabase db)
	{
		Cursor cursor = db.rawQuery("select * from state where id=?", new String[]{id});
		if(cursor.moveToFirst())
		{
			String way=cursor.getString(cursor.getColumnIndex("way"));
			return way;
		}
		return null;
	}
	public static void insertWay(String id,String way,SQLiteDatabase db)
	{
		db.execSQL("insert into state values(?, ?)",new String[]{id,way});
		
	}

}
