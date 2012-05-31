package edu.ucsb.cs.cs185.aaronshepard;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteDataBaseHelper extends SQLiteOpenHelper {
	//DB name/version
	private static final String DATABASE_NAME = "transactions.db";
	
	
	public static final int DATABASE_VERSION = 5;
	
	//Table names
	public static final String TABLE_TRANS = "Transaction_Table";
	
	//Table columns
	public static final String COL_NAME = "Name";
	public static final String COL_AMOUNT = "Amount";
	public static final String COL_CATEGORY = "Category";
	public static final String COL_DATE = "Date";
	
	public static final String COLUMN_ID = "_id";
	
	//example of multi-column table row: http://www.sqlite.org/datatype3.html
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_TRANS + "( " + COLUMN_ID + " integer primary key autoincrement, " 
			+ COL_NAME + " text not null, "
			+ COL_AMOUNT + " real, " 
			+ COL_CATEGORY + " text not null, "
			+ COL_DATE +  " text not null);";


	/*
	 * SQLite helper: http://www.vogella.com/articles/AndroidSQLite/article.html#databasetutorial
	 */
	public SQLiteDataBaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	public void dropTable(SQLiteDatabase db) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANS);
		db.execSQL(DATABASE_CREATE);
	}
	
	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANS);
		onCreate(db);
	}
	
	
}
