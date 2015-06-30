package esgi.project.ratpdroid.db;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import esgi.project.ratpdroid.model.Line;
import esgi.project.ratpdroid.model.Stop;

public class StopDAO {

private SQLiteDatabase bdd;
	
	private DataBaseHelper dbHelper;
	
	public StopDAO(Context context){
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
	
	public long insert(Stop stop){
		ContentValues values = new ContentValues();
		
		values.put(DataBaseHelper.STOP_ID, stop.getId());
		values.put(DataBaseHelper.STOP_NAME, stop.getName());
		values.put(DataBaseHelper.STOP_LAT, stop.getLat());
		values.put(DataBaseHelper.STOP_LON, stop.getLon());
		values.put(DataBaseHelper.STOP_NUM, stop.getPosition());
		values.put(DataBaseHelper.STOP_IDLINE, stop.getIdLine());
		
		return bdd.insert(DataBaseHelper.TABLE_STOP, null, values);
	}
	
	public int update(Stop stop){
		ContentValues values = new ContentValues();
		
		values.put(DataBaseHelper.STOP_NAME, stop.getName());
		values.put(DataBaseHelper.STOP_LAT, stop.getLat());
		values.put(DataBaseHelper.STOP_LON, stop.getLon());
		values.put(DataBaseHelper.STOP_NUM, stop.getPosition());
		values.put(DataBaseHelper.STOP_IDLINE, stop.getIdLine());
		
		String where = DataBaseHelper.STOP_ID + "=" + stop.getId();
		
		return bdd.update(DataBaseHelper.TABLE_STOP, values, where, null);
	}
	
	public int remove(Stop stop){
		String where = DataBaseHelper.STOP_ID + "=" + stop.getId();
		return bdd.delete(DataBaseHelper.TABLE_STOP, where, null);
	}
	
	public Stop getById(int id){
		String where = DataBaseHelper.STOP_ID + "=" + id;
		Cursor c = bdd.query(DataBaseHelper.TABLE_STOP, DataBaseHelper.STOP_CURSOR_QUERY, where, null, null, null, null);
		return cursorToStop(c);
	}
	
	public List<Stop> getByLine(String id_line){
		String where = DataBaseHelper.STOP_IDLINE + " = \"" + id_line + "\"";
		Cursor c = bdd.query(DataBaseHelper.TABLE_STOP, DataBaseHelper.STOP_CURSOR_QUERY, where, null, null, null, null);
		return cursorToStops(c);
	}
	
	public List<Stop> getAll(){
		Cursor c = bdd.query(DataBaseHelper.TABLE_STOP, DataBaseHelper.STOP_CURSOR_QUERY, null, null, null, null, null);
		return cursorToStops(c);
	}
	
	public Stop cursorToStop(Cursor c){
		if(c.getCount() == 0)
			return null;
		
		c.moveToFirst();
		
		Stop stop = new Stop();
		stop.setId(c.getInt(c.getColumnIndex(DataBaseHelper.STOP_ID)));
		stop.setName(c.getString(c.getColumnIndex(DataBaseHelper.STOP_NAME)));
		stop.setLat(c.getDouble(c.getColumnIndex(DataBaseHelper.STOP_LAT)));
		stop.setLon(c.getDouble(c.getColumnIndex(DataBaseHelper.STOP_LON)));
		stop.setPosition(c.getInt(c.getColumnIndex(DataBaseHelper.STOP_NUM)));
		stop.setIdLine(c.getString(c.getColumnIndex(DataBaseHelper.STOP_IDLINE)));
		
		c.close();
		
		return stop;
	}
	
	public List<Stop> cursorToStops(Cursor c){
		if(c.getCount() == 0)
			return null;
		
		List<Stop> lines = new ArrayList<Stop>();
		
		for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			Stop stop = new Stop();
			stop.setId(c.getInt(c.getColumnIndex(DataBaseHelper.STOP_ID)));
			stop.setName(c.getString(c.getColumnIndex(DataBaseHelper.STOP_NAME)));
			stop.setLat(c.getDouble(c.getColumnIndex(DataBaseHelper.STOP_LAT)));
			stop.setLon(c.getDouble(c.getColumnIndex(DataBaseHelper.STOP_LON)));
			stop.setPosition(c.getInt(c.getColumnIndex(DataBaseHelper.STOP_NUM)));
			stop.setIdLine(c.getString(c.getColumnIndex(DataBaseHelper.STOP_IDLINE)));
			lines.add(stop);
		}
		
		c.close();
		
		return lines;
	}
	
	
	
	
	
}
