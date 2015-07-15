package esgi.project.ratpdroid;

import java.util.Random;

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
import esgi.project.ratpdroid.utils.ConfigHelper;
import esgi.project.ratpdroid.utils.DBHelper;
import esgi.project.ratpdroid.utils.UIHelper;

public class UpdateStation extends Activity {

	private static final String TAG = "UpdateStation";

	private TextView textViewUpdateStation;
	private EditText editTextNameUpdateStation;
	private EditText editTextLatitudeUpdateStation;
	private EditText editTextLongitudeUpdateStation;
	
	private TextView textViewNameUpdateStation;
	private Button buttonUpdateStation;
	
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_station);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle(R.string.title_activity_update_station);
		getActionBar().setSubtitle(Datas.GetInstance().GetCurrentStop().toString());

		Log.v(TAG, "Methode onCreate");
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_menu, menu);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			ActionBar actionBar = getActionBar();
			actionBar.setDisplayShowHomeEnabled(false);
			
			UIHelper.getInstance().setSearchActionBar(this,menu,getComponentName());
		}

		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {	
				
		switch (item.getItemId()) {
			case R.id.resetDB:
				launchRingDialog(this.findViewById(R.layout.activity_update_station));
				break;
			case R.id.french:
				ConfigHelper.getInstance().changeLang(getBaseContext(),"fr");
				updateTexts();
				break;
			case R.id.english:
				ConfigHelper.getInstance().changeLang(getBaseContext(),"en");
				updateTexts();
				break;	
		}
				
		return super.onOptionsItemSelected(item);
	}
	
	private void updateTexts()
	{
		getActionBar().setTitle(R.string.title_activity_update_station);
		
		textViewNameUpdateStation.setText(R.string.name);
		buttonUpdateStation.setText(R.string.update);
	}

	protected void onStart() {
		super.onStart();

		Log.v(TAG, "Methode onStart");

		Log.v(TAG, "Nom de la station : "
				+ Datas.GetInstance().GetCurrentStop());

		editTextNameUpdateStation = (EditText) findViewById(R.id.editTextNameUpdateStation);
		editTextLongitudeUpdateStation = (EditText) findViewById(R.id.editTextLongitudeUpdateStation);
		editTextLatitudeUpdateStation = (EditText) findViewById(R.id.editTextLatitudeUpdateStation);
		textViewNameUpdateStation = (TextView) findViewById(R.id.textViewNameUpdateStation);
		buttonUpdateStation = (Button) findViewById(R.id.buttonUpdateStation);

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
			DBHelper.getInstance().updateStop(this.getBaseContext(), editTextLatitudeUpdateStation.getText().toString(), editTextLatitudeUpdateStation.getText().toString(), editTextNameUpdateStation.getText().toString());

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
	
	public void launchRingDialog(View view) {
		final ProgressDialog ringProgressDialog = ProgressDialog
				.show(UpdateStation.this,
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