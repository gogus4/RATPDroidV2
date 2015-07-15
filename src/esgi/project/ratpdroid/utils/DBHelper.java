package esgi.project.ratpdroid.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;
import esgi.project.ratpdroid.Datas;
import esgi.project.ratpdroid.db.StopDAO;
import esgi.project.ratpdroid.model.Stop;
import android.content.Context;
import android.util.Log;

public class DBHelper {

	private static final String TAG = "DBHelper";

	private DBHelper() {
	}

	private static DBHelper instance = new DBHelper();

	public static DBHelper getInstance() {
		return instance;
	}

	public void resetBDD(Context context) {
		try {

			String destPath = "/data/data/" + context.getPackageName()
					+ "/databases/ratp.db";

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

	public void updateStop(Context context, String latitude, String longitude,
			String name) {
		Stop stop = Datas.GetInstance().GetCurrentStop();

		if (latitude.matches("[0-9]{1,13}(\\.[0-9]*)?")) {
			stop.setLat(Double.parseDouble(latitude));
		}

		else {
			stop.setLat(0);
		}

		if (longitude.matches("[0-9]{1,13}(\\.[0-9]*)?")) {
			stop.setLon(Double.parseDouble(longitude));
		}

		else {
			stop.setLon(0);
		}

		stop.setName(name);

		StopDAO sdao = new StopDAO(context);
		sdao.open();

		sdao.update(stop);

		sdao.close();
	}

	public void addStop(Context context, String latitude, String longitude,
			String name) {
		Stop stop = new Stop();
		stop.setId(randInt(0, 1634));
		stop.setIdLine(Datas.GetInstance().GetCurrentLine().getShortName());

		if (latitude.matches("[0-9]{1,13}(\\.[0-9]*)?")) {
			stop.setLat(Double.parseDouble(latitude));
		}

		else {
			stop.setLat(0);
		}

		if (longitude.matches("[0-9]{1,13}(\\.[0-9]*)?")) {
			stop.setLon(Double.parseDouble(longitude));
		}

		else {
			stop.setLon(0);
		}

		stop.setName(name);

		StopDAO sdao = new StopDAO(context);
		sdao.open();

		sdao.insert(stop);

		sdao.close();
	}

	public void removeStop(Context context) {
		StopDAO sdao = new StopDAO(context);
		sdao.open();

		sdao.remove(Datas.GetInstance().GetCurrentStop());

		sdao.close();
	}

	private int randInt(int min, int max) {

		Random rand = new Random();

		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}
}
