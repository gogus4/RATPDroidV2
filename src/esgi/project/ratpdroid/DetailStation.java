package esgi.project.ratpdroid;

import esgi.project.ratpdroid.utils.ConfigHelper;
import esgi.project.ratpdroid.utils.DBHelper;
import esgi.project.ratpdroid.utils.UIHelper;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetailStation extends Activity {

	private static final String TAG = "DetailStation";

	private TextView textViewNameStation;
	private TextView textViewLongitudeStation;
	private TextView textViewLatitudeStation;

	private Button buttonUpdateStation;
	private Button buttonDeleteStation;
	private TextView nameDetailStation;

	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_station);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle(R.string.title_activity_detail_station);
		getActionBar().setSubtitle(
				Datas.GetInstance().GetCurrentStop().toString());

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
			launchRingDialog(this
					.findViewById(R.layout.activity_detail_station));
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
		getActionBar().setTitle(R.string.title_activity_detail_station);

		buttonUpdateStation.setText(R.string.update);
		buttonDeleteStation.setText(R.string.delete);
		nameDetailStation.setText(R.string.name);
	}

	protected void onStart() {
		super.onStart();

		Log.v(TAG, "Methode onStart");

		//Log.v(TAG, "Nom de la station : "
				//+ Datas.GetInstance().GetCurrentStop());

		intent = new Intent(this, UpdateStation.class);

		textViewNameStation = (TextView) findViewById(R.id.textViewNameStation);
		textViewLongitudeStation = (TextView) findViewById(R.id.textViewLongitudeStation);
		textViewLatitudeStation = (TextView) findViewById(R.id.textViewLatitudeStation);

		buttonUpdateStation = (Button) findViewById(R.id.buttonUpdateStation);
		buttonDeleteStation = (Button) findViewById(R.id.buttonDeleteStation);
		nameDetailStation = (TextView) findViewById(R.id.nameDetailStation);

		textViewNameStation.setText(Datas.GetInstance().GetCurrentStop()
				.toString());

		textViewLongitudeStation.setText(""
				+ Datas.GetInstance().GetCurrentStop().getLon());

		textViewLatitudeStation.setText(""
				+ Datas.GetInstance().GetCurrentStop().getLat());
	}

	public void onButtonUpdateClick(View view) {

		Log.v(TAG, "Methode onButtonUpdateClick");

		startActivity(intent);
	}

	public void onButtonDeleteClick(View view) {
		Log.v(TAG, "Methode onButtonDeleteClick");

		final Context that = this;
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Supprimer la station")
				.setMessage("Etes-vous sur de vouloir supprimer la station ?")
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setPositiveButton("Oui",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {

								DBHelper.getInstance().removeStop(that);

								MyApplication app = ((MyApplication) ((Activity) that)
										.getApplication());
								app.initDatas();

								intent = new Intent(that, ListStations.class);
								startActivity(intent);
								((Activity) that).finish();
							}
						}).setNegativeButton("Non", null).show();
	}

	public void launchRingDialog(View view) {
		final ProgressDialog ringProgressDialog = ProgressDialog
				.show(DetailStation.this,
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
