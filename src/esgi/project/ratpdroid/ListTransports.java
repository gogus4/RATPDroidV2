package esgi.project.ratpdroid;

import java.util.Locale;

import esgi.project.ratpdroid.utils.ConfigHelper;
import esgi.project.ratpdroid.utils.DBHelper;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class ListTransports extends Activity {

	private static final String TAG = "ListTransports";

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_transports);
			
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setTitle(R.string.title_activity_list_transports);
		getActionBar().setSubtitle(getIntent().getStringExtra("Transport"));

		Log.v(TAG, "Methode onCreate");
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.v(TAG, "Methode onStart");
		
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
		
		ListTransportsFragment listTransportsfragment = (ListTransportsFragment) getFragmentManager().findFragmentById(R.id.listTransportsFragment);
		ListStationsFragment listStationsFragment = (ListStationsFragment) getFragmentManager().findFragmentById(R.id.listStationsFragment);
		SearchFragment fragmentSearch = (SearchFragment) getFragmentManager().findFragmentById(R.id.searchFragment);

		switch (item.getItemId()) {
			case R.id.resetDB:
				launchRingDialog(this.findViewById(R.layout.activity_list_transports));
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
		
		listTransportsfragment.init();
		fragmentSearch.update();
		
		if(listStationsFragment != null && listStationsFragment.isInLayout())
			listStationsFragment.init();
		
		return super.onOptionsItemSelected(item);
	}
	
	private void updateTexts()
	{
		getActionBar().setTitle(R.string.title_activity_list_transports);
	}
	
	private void initFragments()
	{
		Log.v(TAG, "Init fragment ListTransportsFragment with value : " + getIntent().getStringExtra("Transport"));
		
		FragmentManager fragmentManager = this.getFragmentManager();
		ListTransportsFragment fragmentTransport = (ListTransportsFragment)fragmentManager.findFragmentById(R.id.listTransportsFragment);
		
		fragmentTransport.setTransports(getIntent().getStringExtra("Transport"));
		fragmentTransport.init();
	}
	
	public void launchRingDialog(View view) {
		final ProgressDialog ringProgressDialog = ProgressDialog
				.show(ListTransports.this,
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