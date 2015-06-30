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

public class AddStation extends Activity {

	private static final String TAG = "AddStation";

	private TextView textViewAddStation;

	private EditText editTextNameAddStation;
	private EditText editTextLatitudeAddStation;
	private EditText editTextLongitudeAddStation;

	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_station);

		Log.v(TAG, "Methode onCreate");
	}

	protected void onStart() {
		super.onStart();

		Log.v(TAG, "Methode onStart");

		Log.v(TAG, "Value : " + Datas.GetInstance().GetCurrentLine());

		editTextNameAddStation = (EditText) findViewById(R.id.editTextNameAddStation);
		editTextLongitudeAddStation = (EditText) findViewById(R.id.editTextLongitudeAddStation);
		editTextLatitudeAddStation = (EditText) findViewById(R.id.editTextLatitudeAddStation);
		textViewAddStation = (TextView) findViewById(R.id.textViewAddStation);

		textViewAddStation.setText("Ajouter une station "
				+ Datas.GetInstance().GetCurrentLine());
	}

	public void onButtonAddStationsClick(View view) {

		Log.v(TAG, "Methode onButtonAddStationsClick");

		try {
			Stop stop = new Stop();
			stop.setId(randInt(0, 1634));
			stop.setIdLine(Datas.GetInstance().GetCurrentLine().getShortName());

			if (editTextLatitudeAddStation.getText().toString()
					.matches("[0-9]{1,13}(\\.[0-9]*)?")) {
				stop.setLat(Double.parseDouble(editTextLatitudeAddStation
						.getText().toString()));
			}

			else {
				stop.setLat(0);
			}

			if (editTextLongitudeAddStation.getText().toString()
					.matches("[0-9]{1,13}(\\.[0-9]*)?")) {
				stop.setLon(Double.parseDouble(editTextLongitudeAddStation
						.getText().toString()));
			}

			else {
				stop.setLon(0);
			}

			stop.setName(editTextNameAddStation.getText().toString());

			StopDAO sdao = new StopDAO(this);
			sdao.open();

			sdao.insert(stop);

			sdao.close();
			
			MyApplication app = ((MyApplication) this.getApplication());
			app.initDatas();

			intent = new Intent(this, ListStations.class);
			startActivity(intent);
			this.finish();
		} catch (Exception E) {

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Problème lors de l'ajout")
					.setMessage(
							"Un problème est survenue lors de l'ajout de la station")
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
