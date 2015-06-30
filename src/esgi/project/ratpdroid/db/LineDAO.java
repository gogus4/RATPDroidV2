package esgi.project.ratpdroid.db;

import java.util.ArrayList;
import java.util.List;

import esgi.project.ratpdroid.model.Line;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class LineDAO {

	private static final String TAG = "LineDAO";
	
	private SQLiteDatabase bdd;
	
	private DataBaseHelper dbHelper;
	
	public LineDAO(Context context){
		dbHelper = new DataBaseHelper(context);
	}
	
	public void open(){
		bdd = dbHelper.getWritableDatabase();
	}
	
	public void close(){
		bdd.close();
	}
	
	public SQLiteDatabase getBDD(){
		return bdd;
	}
	
	public long insert(Line line){
		ContentValues values = new ContentValues();
		
		values.put(DataBaseHelper.LINE_ID, line.getId());
		values.put(DataBaseHelper.LINE_SHORT_NAME, line.getShortName());
		values.put(DataBaseHelper.LINE_LONG_NAME, line.getLongName());
		values.put(DataBaseHelper.LINE_TYPE, line.getType());
		
		return bdd.insert(DataBaseHelper.TABLE_LINE, null, values);
	}
	
	public int update(Line line){
		ContentValues values = new ContentValues();
		
		values.put(DataBaseHelper.LINE_SHORT_NAME, line.getShortName());
		values.put(DataBaseHelper.LINE_LONG_NAME, line.getLongName());
		values.put(DataBaseHelper.LINE_TYPE, line.getType());
		String where = DataBaseHelper.LINE_ID + "=" + line.getId();
		
		return bdd.update(DataBaseHelper.TABLE_LINE, values, where, null);
	}
	
	public int remove(Line line){
		String where = DataBaseHelper.LINE_ID + "=" + line.getId();
		return bdd.delete(DataBaseHelper.TABLE_LINE, where, null);
	}
	
	public Line getById(int id){
		String where = "WHERE " + DataBaseHelper.LINE_ID + "=" + id;
		Cursor c = bdd.query(DataBaseHelper.TABLE_LINE, DataBaseHelper.LINE_CURSOR_QUERY, where, null, null, null, null);
		return cursorToLine(c);
	}
	
	public List<Line> getByType(int type){
		/*List<Line> all = getAll();
		List<Line> lines = new ArrayList<Line>();
		
		for(Line line : all){
			if(line.getType() == type){
				lines.add(line);
			}
		}
		
		return lines;*/
		String where = DataBaseHelper.LINE_TYPE + " = " + type;
		Cursor c = bdd.query(DataBaseHelper.TABLE_LINE, DataBaseHelper.LINE_CURSOR_QUERY, where, null, null, null, null);
		Log.v(TAG, "" + c.toString());
		return cursorToLines(c);
	}
	
	public List<Line> getByName(String name){
		String like = DataBaseHelper.LINE_SHORT_NAME + " LIKE \"" + name + "\"";
		Cursor c = bdd.query(DataBaseHelper.TABLE_LINE, DataBaseHelper.LINE_CURSOR_QUERY, like, null, null, null, null);
		return cursorToLines(c);
	}
	
	public List<Line> getAll(){
		Cursor c = bdd.query(DataBaseHelper.TABLE_LINE, DataBaseHelper.LINE_CURSOR_QUERY, null, null, null, null, null);
		Log.v(TAG, "" + c.toString());
		return cursorToLines(c);
	}
	
	public Line cursorToLine(Cursor c){
		if(c.getCount() == 0)
			return null;
		
		c.moveToFirst();
		
		Line line = new Line();
		line.setId(c.getInt(c.getColumnIndex(DataBaseHelper.LINE_ID)));
		line.setShortName(c.getString(c.getColumnIndex(DataBaseHelper.LINE_SHORT_NAME)));
		line.setLongName(c.getString(c.getColumnIndex(DataBaseHelper.LINE_LONG_NAME)));
		line.setType(c.getInt(c.getColumnIndex(DataBaseHelper.LINE_TYPE)));
		
		c.close();
		
		return line;
	}
	
	public List<Line> cursorToLines(Cursor c){
		if(c.getCount() == 0)
			return null;
		
		List<Line> lines = new ArrayList<Line>();
		
		for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			Line line = new Line();
			line.setId(c.getInt(c.getColumnIndex(DataBaseHelper.LINE_ID)));
			line.setShortName(c.getString(c.getColumnIndex(DataBaseHelper.LINE_SHORT_NAME)));
			line.setLongName(c.getString(c.getColumnIndex(DataBaseHelper.LINE_LONG_NAME)));
			line.setType(c.getInt(c.getColumnIndex(DataBaseHelper.LINE_TYPE)));
			lines.add(line);
		}
		
		c.close();
		
		return lines;
	}
	
	
	
	
	
}
