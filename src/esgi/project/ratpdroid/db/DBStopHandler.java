package esgi.project.ratpdroid.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

@Deprecated
public class DBStopHandler extends SQLiteOpenHelper {

	public static final String TABLE_STOP = "STOP";
		public static final int STOP_VERSION = 1;
		public static final String STOP_ID = "ID";
		public static final String STOP_NAME = "NAME";
		public static final String STOP_LAT = "LAT";
		public static final String STOP_LON = "LON";
		public static final String STOP_NUM = "NUM";
		public static final String STOP_IDLINE = "IDLINE";
		
		public static final String [] STOP_CURSOR_QUERY = new String [] {STOP_ID, STOP_NAME, STOP_LAT, STOP_LON, STOP_NUM, STOP_IDLINE};
		
	public static final String TABLE_STOP_CREATE = "CREATE TABLE " + TABLE_STOP + " (" +
													STOP_ID + " INTEGER PRIMARY KEY NOT NULL, " +
													STOP_NAME + " TEXT NOT NULL, " +
													STOP_LAT + " TEXT NOT NULL, " +
													STOP_LON + " TEXT NOT NULL, " +
													STOP_NUM + " TEXT NOT NULL, " +
													STOP_IDLINE + " TEXT NOT NULL);";
	
	public static final String TABLE_STOP_DROP = "DROP TABLE IF EXISTS " + TABLE_STOP + ";";

		
	public DBStopHandler(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}
	
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(TABLE_STOP_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL(TABLE_STOP_DROP);
		onCreate(db);
	}

}