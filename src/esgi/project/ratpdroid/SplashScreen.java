package esgi.project.ratpdroid;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class SplashScreen extends Activity {

	private static final String TAG = "SplashScreen";
	private static int SPLASH_TIME_OUT = 3000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);

		initDB();
		
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {

				Intent i = new Intent(SplashScreen.this, MainActivity.class);
				startActivity(i);
				finish();
			}
		}, SPLASH_TIME_OUT);
	}
	
	public void initDB(){
		try {

			String destPath = "/data/data/" + getPackageName()
					+ "/databases/";
			
			File dir = new File(destPath);
			
			if(!dir.exists())
				dir.mkdirs();

			Log.v(TAG, destPath);

			File f = new File(dir, "ratp.db");
			if (!f.exists()) {
				Log.v(TAG, "File Not Exist");
				InputStream in = getAssets().open("ratp.db");
				OutputStream out = new FileOutputStream(f);

				byte[] buffer = new byte[1024];
				int length;
				while ((length = in.read(buffer)) > 0) {
					out.write(buffer, 0, length);
				}
				in.close();
				out.close();
				Log.v(TAG, destPath);
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
		
		MyApplication app = ((MyApplication) this.getApplication());
		app.initDatas();
	}
}
