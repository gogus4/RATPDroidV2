package esgi.project.ratpdroid.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.util.Log;

public class DBHelper {

	private static final String TAG = "DBHelper";
	
	private DBHelper()
	{}
 
	private static DBHelper instance = new DBHelper();
 
	public static DBHelper getInstance()
	{	
		return instance;
	}
	
	public void resetBDD(Context context) {
		try {

			String destPath = "/data/data/" + context.getPackageName() + "/databases/ratp.db";

			Log.v(TAG, destPath);

			File f = new File(destPath);

			Log.v(TAG, "Replace db");
			InputStream in = context.getAssets().open("ratp.db");
			OutputStream out = new FileOutputStream(destPath);

			byte[] buffer = new byte[1024];
			int length;
			while ((length = in.read(buffer)) > 0) {
				out.write(buffer, 0, length);
			}
			in.close();
			out.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			Log.v(TAG, "ioexeption");
			e.printStackTrace();
		}
	}
}
