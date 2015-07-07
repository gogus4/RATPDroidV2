package esgi.project.ratpdroid;

import java.util.Random;

import esgi.project.ratpdroid.db.StopDAO;
import esgi.project.ratpdroid.model.Stop;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class UpdateStation extends Activity {

	private static final String TAG = "UpdateStation";

	private TextView textViewUpdateStation;
	private EditText editTextNameUpdateStation;
	private EditText editTextLatitudeUpdateStation;
	private EditText editTextLongitudeUpdateStation;

	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_station);

		Log.v(TAG, "Methode onCreate");
	}

	protected void onStart() {
		super.onStart();

		Log.v(TAG, "Methode onStart");

		Log.v(TAG, "Nom de la station : "
				+ Datas.GetInstance().GetCurrentStop());

		editTextNameUpdateStation = (EditText) findViewById(R.id.editTextNameUpdateStation);
		editTextLongitudeUpdateStation = (EditText) findViewById(R.id.editTextLongitudeUpdateStation);
		editTextLatitudeUpdateStation = (EditText) findViewById(R.id.editTextLatitudeUpdateStation);

		editTextNameUpdateStation.setText(Datas.GetInstance().GetCurrentStop()
				.getName());
		editTextLongitudeUpdateStation.setText(""
				+ Datas.GetInstance().GetCurrentStop().getLon());
		editTextLatitudeUpdateStation.setText(""
				+ Datas.GetInstance().GetCurrentStop().getLat());

		textViewUpdateStation = (TextView) findViewById(R.id.textViewUpdateStation);

		textViewUpdateStation.setText("Modifier la station "
				+ Datas.GetInstance().GetCurrentStop());
	}

	public void onButtonUpdateStationClick(View view) {

		Log.v(TAG, "Methode onButtonUpdateStationClick");

		try {
			Stop stop = Datas.GetInstance().GetCurrentStop();

			if (editTextLatitudeUpdateStation.getText().toString()
					.matches("[0-9]{1,13}(\\.[0-9]*)?")) {
				stop.setLat(Double.parseDouble(editTextLatitudeUpdateStation
						.getText().toString()));
			}

			else {
				stop.setLat(0);
			}

			if (editTextLongitudeUpdateStation.getText().toString()
					.matches("[0-9]{1,13}(\\.[0-9]*)?")) {
				stop.setLon(Double.parseDouble(editTextLongitudeUpdateStation
						.getText().toString()));
			}

			else {
				stop.setLon(0);
			}

			stop.setName(editTextNameUpdateStation.getText().toString());

			StopDAO sdao = new StopDAO(this);
			sdao.open();

			sdao.update(stop);

			sdao.close();
			
			MyApplication app = ((MyApplication) this.getApplication());
			app.initDatas();

			intent = new Intent(this, ListStations.class);
			startActivity(intent);
			this.finish();
		} catch (Exception E) {

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(
					"Problème lors de la modification de la station "
							+ Datas.GetInstance().GetCurrentStop())
					.setMessage(
							"Un problème est survenue lors de la modification de la station")
					.setIcon(android.R.drawable.ic_dialog_alert).show();

			Log.v(TAG, "CATCH " + E.getMessage());
		}
	}

	public int randInt(int min, int max) {

		Random rand = new Random();

		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}
}