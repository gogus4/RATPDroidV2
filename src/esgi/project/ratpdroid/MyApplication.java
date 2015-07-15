package esgi.project.ratpdroid;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import esgi.project.ratpdroid.db.StopDAO;
import android.app.Application;
import android.util.Log;

public class MyApplication extends Application {
	private static final String TAG = "MyApplication";
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		//initDB();
		
		//initDatas();
		
		Log.v(TAG, "onCreate");
	}

	public void initDatas() {
		Log.v(TAG, "initDatas");
		
		Datas.GetInstance();
		
		StopDAO sdao = new StopDAO(this);
		sdao.open();
		
		Datas.GetInstance().SetStops(sdao.getAll());
		
		sdao.close();
	}
	
	public void initDB(){
		try {

			String destPath = "/data/data/" + getPackageName()
					+ "/databases/ratp.db";

			// Log.v(TAG, destPath);

			File f = new File(destPath);
			f.delete();
			if (!f.exists()) {
				Log.v(TAG, "File Not Exist");
				InputStream in = getAssets().open("ratp.db");
				OutputStream out = new FileOutputStream(destPath);

				byte[] buffer = new byte[1024];
				int length;
				while ((length = in.read(buffer)) > 0) {
					out.write(buffer, 0, length);
				}
				in.close();
				out.close();
			}
			else {
				Log.v(TAG, "File Already Exist");
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			Log.v(TAG, "ioexeption");
			e.printStackTrace();
		}
	}
}