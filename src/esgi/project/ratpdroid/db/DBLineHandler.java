package esgi.project.ratpdroid.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

@Deprecated
public class DBLineHandler extends SQLiteOpenHelper {

	public static final String TABLE_LINE = "LINE";
		public static final int LINE_VERSION = 1;
		public static final String LINE_ID = "ID";
		public static final String LINE_SHORT_NAME = "SHORT_NAME";
		public static final String LINE_LONG_NAME = "LONG_NAME";
		public static final String LINE_TYPE = "TYPE";
		
		public static final String [] LINE_CURSOR_QUERY = new String [] {LINE_ID, LINE_SHORT_NAME, LINE_LONG_NAME, LINE_TYPE};
		
		
	public static final String TABLE_LINE_CREATE = "CREATE TABLE " + TABLE_LINE + " (" +
		    										LINE_ID + " INTEGER PRIMARY KEY NOT NULL, " +
		    										LINE_SHORT_NAME + " TEXT NOT NULL, " +
		    										LINE_LONG_NAME + " TEXT NOT NULL, " +
		    										LINE_TYPE + " INT NOT NULL);";
	
	public static final String TABLE_LINE_DROP = "DROP TABLE IF EXISTS " + TABLE_LINE + ";";

		
	public DBLineHandler(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}
	
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(TABLE_LINE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL(TABLE_LINE_DROP);
		onCreate(db);
	}

}
