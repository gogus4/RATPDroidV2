package esgi.project.ratpdroid;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ListTransports extends Activity {

	private static final String TAG = "ListTransports";
	private TextView textViewListTransports;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_transports);

		Log.v(TAG, "Methode onCreate");
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.v(TAG, "Methode onStart");
		
		initFragments();
	}
	
	private void initFragments()
	{
		Log.v(TAG, "Init fragment ListTransportsFragment with value : " + getIntent().getStringExtra("Transport"));
		
		FragmentManager fragmentManager = this.getFragmentManager();
		ListTransportsFragment fragmentTransport = (ListTransportsFragment)fragmentManager.findFragmentById(R.id.listTransportsFragment);
		
		fragmentTransport.setTransports(getIntent().getStringExtra("Transport"));
		fragmentTransport.init();
		
		/*ListStationsFragment fragmentStations = (ListStationsFragment)fragmentManager.findFragmentById(R.id.listStationsFragment);
		
		if(fragmentStations != null)
		{
			fragmentStations.init();
		}*/
	}
}