package esgi.project.ratpdroid;

import esgi.project.ratpdroid.db.StopDAO;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class DetailStation extends Activity {

	private static final String TAG = "DetailStation";

	private TextView textViewNameStation;
	private TextView textViewLongitudeStation;
	private TextView textViewLatitudeStation;

	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail_station);

		Log.v(TAG, "Methode onCreate");
	}

	protected void onStart() {
		super.onStart();

		Log.v(TAG, "Methode onStart");

		Log.v(TAG, "Nom de la station : "
				+ Datas.GetInstance().GetCurrentStop());

		intent = new Intent(this, UpdateStation.class);

		textViewNameStation = (TextView) findViewById(R.id.textViewNameStation);
		textViewLongitudeStation = (TextView) findViewById(R.id.textViewLongitudeStation);
		textViewLatitudeStation = (TextView) findViewById(R.id.textViewLatitudeStation);

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
								Log.v(TAG, "Click sur Oui");

								StopDAO sdao = new StopDAO(that);
								sdao.open();

								sdao.remove(Datas.GetInstance()
										.GetCurrentStop());

								sdao.close();
								
								MyApplication app = ((MyApplication) ((Activity) that).getApplication());
								app.initDatas();

								intent = new Intent(that, ListStations.class);
								startActivity(intent);
								((Activity) that).finish();
							}
						}).setNegativeButton("Non", null).show();
	}
}
