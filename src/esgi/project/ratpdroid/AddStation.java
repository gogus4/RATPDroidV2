package esgi.project.ratpdroid;

import esgi.project.ratpdroid.utils.ConfigHelper;
import esgi.project.ratpdroid.utils.DBHelper;
import esgi.project.ratpdroid.utils.UIHelper;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddStation extends Activity {

	private static final String TAG = "AddStation";

	private TextView textViewAddStation;

	private EditText editTextNameAddStation;
	private EditText editTextLatitudeAddStation;
	private EditText editTextLongitudeAddStation;

	private TextView textViewNameStationAddAstation;
	private Button buttonAddStations;

	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_station);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle(R.string.title_activity_add_station);

		Log.v(TAG, "Methode onCreate");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_menu, menu);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			ActionBar actionBar = getActionBar();
			actionBar.setDisplayShowHomeEnabled(false);

			UIHelper.getInstance().setSearchActionBar(this, menu,
					getComponentName());
		}

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.resetDB:
			launchRingDialog(this.findViewById(R.layout.activity_add_station));
			break;
		case R.id.french:
			ConfigHelper.getInstance().changeLang(getBaseContext(), "fr");
			updateTexts();
			break;
		case R.id.english:
			ConfigHelper.getInstance().changeLang(getBaseContext(), "en");
			updateTexts();
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	private void updateTexts() {
		getActionBar().setTitle(R.string.title_activity_add_station);

		textViewNameStationAddAstation.setText(R.string.name_station);
		buttonAddStations.setText(R.string.add);
		textViewAddStation.setText(getResources().getString(
				R.string.title_activity_add_station)
				+ " " + Datas.GetInstance().GetCurrentLine().toString());
	}

	protected void onStart() {
		super.onStart();

		Log.v(TAG, "Methode onStart");

		Log.v(TAG, "Value : " + Datas.GetInstance().GetCurrentLine());

		editTextNameAddStation = (EditText) findViewById(R.id.editTextNameAddStation);
		editTextLongitudeAddStation = (EditText) findViewById(R.id.editTextLongitudeAddStation);
		editTextLatitudeAddStation = (EditText) findViewById(R.id.editTextLatitudeAddStation);
		textViewAddStation = (TextView) findViewById(R.id.textViewAddStation);
		textViewNameStationAddAstation = (TextView) findViewById(R.id.textViewNameStationAddAstation);
		buttonAddStations = (Button) findViewById(R.id.buttonAddStations);

		textViewAddStation.setText(getResources().getString(
				R.string.title_activity_add_station)
				+ " " + Datas.GetInstance().GetCurrentLine().toString());
	}

	public void onButtonAddStationsClick(View view) {

		Log.v(TAG, "Methode onButtonAddStationsClick");

		try {
			DBHelper.getInstance().addStop(this.getBaseContext(),
					editTextLatitudeAddStation.getText().toString(),
					editTextLongitudeAddStation.getText().toString(),
					editTextNameAddStation.getText().toString());

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

	public void launchRingDialog(View view) {
		final ProgressDialog ringProgressDialog = ProgressDialog
				.show(AddStation.this,
						"Patientez ...",
						"Merci de patientez pendant la réinitialisation de la base de données ...",
						true);
		ringProgressDialog.setCancelable(true);
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(2000);
					DBHelper.getInstance().resetBDD(getBaseContext());
				} catch (Exception e) {
				}
				ringProgressDialog.dismiss();
			}
		}).start();
	}
}
