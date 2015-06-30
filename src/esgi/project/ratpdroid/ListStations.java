package esgi.project.ratpdroid;

import java.util.List;

import esgi.project.ratpdroid.db.LineDAO;
import esgi.project.ratpdroid.db.StopDAO;
import esgi.project.ratpdroid.model.Line;
import esgi.project.ratpdroid.model.Stop;
import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

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
