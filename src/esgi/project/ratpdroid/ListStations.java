package esgi.project.ratpdroid;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;

public class ListStations extends Activity {

	private static final String TAG = "ListStations";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_stations);

		Log.v(TAG, "Methode onCreate");
	}

	@Override
	protected void onStart() {
		super.onStart();

		Log.v(TAG, "Methode onStart");

		Log.v(TAG, "Value : " + Datas.GetInstance().GetCurrentLine());
		
		initFragments();
	}
	
	private void initFragments()
	{
		// Log.v(TAG, "Init fragment ListTransportsFragment with value : " + getIntent().getStringExtra("Transport"));
		
		FragmentManager fragmentManager = this.getFragmentManager();
		ListStationsFragment fragmentStations = (ListStationsFragment) fragmentManager.findFragmentById(R.id.listStationsFragment);
		
		fragmentStations.init();
	}
}
