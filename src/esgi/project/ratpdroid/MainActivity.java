package esgi.project.ratpdroid;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import esgi.project.ratpdroid.utils.ConfigHelper;
import esgi.project.ratpdroid.utils.DBHelper;
import esgi.project.ratpdroid.utils.UIHelper;

public class MainActivity extends Activity {

	private static final String TAG = "MainActivity";
	private Intent intent;

	private Button buttonRER;
	private Button buttonBUS;
	private Button buttonMETRO;
	private Button buttonTRAMWAY;
	private Button buttonReset;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		getActionBar().setDisplayShowHomeEnabled(false);

		Log.v(TAG, "START MAIN ACTIVITY");
	}

	@Override
	protected void onStart() {
		super.onStart();

		Log.v(TAG, "Methode onStart");
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
			launchRingDialog(this.findViewById(R.layout.activity_main));
			break;
		case R.id.french:
			ConfigHelper.getInstance().changeLang(getBaseContext(), "fr");
			break;
		case R.id.english:
			ConfigHelper.getInstance().changeLang(getBaseContext(), "en");
			break;
		}

		updateTexts();

		return super.onOptionsItemSelected(item);
	}

	private void updateTexts() {
		buttonRER = (Button) findViewById(R.id.buttonRER);
		buttonBUS = (Button) findViewById(R.id.buttonBUS);
		buttonMETRO = (Button) findViewById(R.id.buttonMETRO);
		buttonTRAMWAY = (Button) findViewById(R.id.buttonTRAMWAY);
		buttonReset = (Button) findViewById(R.id.buttonReset);

		buttonRER.setText(R.string.rer);
		buttonBUS.setText(R.string.bus);
		buttonMETRO.setText(R.string.metro);
		buttonTRAMWAY.setText(R.string.tramway);
		buttonReset.setText(R.string.reset);
	}

	public void launchRingDialog(View view) {
		final ProgressDialog ringProgressDialog = ProgressDialog
				.show(MainActivity.this,
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

	public void onButtonRERClick(View view) {
		Log.v(TAG, "Methode onButtonRERClick");

		intent = new Intent(this, ListTransports.class);
		intent.putExtra("Transport", "RER");
		startActivity(intent);
	}

	public void onButtonMETROClick(View view) {
		Log.v(TAG, "Methode onButtonMETROClick");

		intent = new Intent(this, ListTransports.class);
		intent.putExtra("Transport", "METRO");
		startActivity(intent);
	}

	public void onButtonBUSClick(View view) {
		Log.v(TAG, "Click sur le bouton BUS");

		intent = new Intent(this, ListTransports.class);
		intent.putExtra("Transport", "BUS");
		startActivity(intent);
	}

	public void onButtonTRAMWAYClick(View view) {
		Log.v(TAG, "Methode onButtonBUSClick");

		intent = new Intent(this, ListTransports.class);
		intent.putExtra("Transport", "TRAM");
		startActivity(intent);
	}

	public void onButtonResetClick(View view) {
		Log.v(TAG, "Methode onButtonResetClick");

		launchRingDialog(view);
	}
}