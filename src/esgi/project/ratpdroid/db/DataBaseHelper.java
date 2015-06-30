package esgi.project.ratpdroid.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
	
	private static String DB_NAME = "ratp.db";
		
	public static final String TABLE_LINE = "LINE";
		public static final int LINE_VERSION = 1;
		public static final String LINE_ID = "ID";
		public static final String LINE_SHORT_NAME = "SHORT_NAME";
		public static final String LINE_LONG_NAME = "LONG_NAME";
		public static final String LINE_TYPE = "TYPE";
		
		public static final String [] LINE_CURSOR_QUERY = new String [] {LINE_ID, LINE_SHORT_NAME, LINE_LONG_NAME, LINE_TYPE};
		
		
	public static final String TABLE_STOP = "STOP";
		public static final int STOP_VERSION = 1;
		public static final String STOP_ID = "ID";
		public static final String STOP_NAME = "NAME";
		public static final String STOP_LAT = "LAT";
		public static final String STOP_LON = "LON";
		public static final String STOP_NUM = "NUM";
		public static final String STOP_IDLINE = "IDLINE";
		
		public static final String [] STOP_CURSOR_QUERY = new String [] {STOP_ID, STOP_NAME, STOP_LAT, STOP_LON, STOP_NUM, STOP_IDLINE};

	
	public DataBaseHelper(Context context) {
    	super(context, DB_NAME, null, 1);
    }	
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
	}

}
