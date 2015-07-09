package esgi.project.ratpdroid;

import esgi.project.ratpdroid.utils.ConfigHelper;
import esgi.project.ratpdroid.utils.DBHelper;
import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class ListStations extends Activity {

	private static final String TAG = "ListStations";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_stations);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle(R.string.title_activity_list_stations);
		getActionBar().setSubtitle(Datas.GetInstance().GetCurrentLine().toString());

		Log.v(TAG, "Methode onCreate");
	}

	@Override
	protected void onStart() {
		super.onStart();

		Log.v(TAG, "Methode onStart");

		Log.v(TAG, "Value : " + Datas.GetInstance().GetCurrentLine());
		
		initFragments();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_menu, menu);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			ActionBar actionBar = getActionBar();
			actionBar.setDisplayShowHomeEnabled(false);
		}

		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {	
		
		SearchFragment fragmentSearch = (SearchFragment) getFragmentManager().findFragmentById(R.id.searchFragment);
		ListStationsFragment listStationsFragment = (ListStationsFragment) getFragmentManager().findFragmentById(R.id.listStationsFragment);
		
		switch (item.getItemId()) {
			case R.id.resetDB:
				launchRingDialog(this.findViewById(R.layout.activity_list_stations));
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
		
		fragmentSearch.update();
		listStationsFragment.init();
		
		return super.onOptionsItemSelected(item);
	}
	
	private void updateTexts()
	{
		getActionBar().setTitle(R.string.title_activity_list_stations);
	}
	
	public void launchRingDialog(View view) {
		final ProgressDialog ringProgressDialog = ProgressDialog
				.show(ListStations.this,
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
	
	private void initFragments()
	{
		FragmentManager fragmentManager = this.getFragmentManager();
		ListStationsFragment fragmentStations = (ListStationsFragment) fragmentManager.findFragmentById(R.id.listStationsFragment);
		
		fragmentStations.setTransportName(getIntent().getStringExtra("Transport"));
		fragmentStations.init();
	}
}
