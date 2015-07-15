package esgi.project.ratpdroid;

import java.util.List;
import esgi.project.ratpdroid.db.StopDAO;
import esgi.project.ratpdroid.model.Stop;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ListStationsFragment extends Fragment {

	private static final String TAG = "ListStationsFragment";

	private ListView myList;
	private TextView textViewListStations;
	private Button buttonAddStations;

	private Intent intent;

	private List<Stop> stops;

	private View view;

	private String transportName;

	public String getTransportName() {
		return transportName;
	}

	public void setTransportName(String transportName) {
		this.transportName = transportName;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_list_stations, container,
				false);

		myList = (ListView) view.findViewById(R.id.listAllStations);
		textViewListStations = (TextView) view
				.findViewById(R.id.textViewListStations);
		buttonAddStations = (Button) view.findViewById(R.id.buttonAddStations);

		return view;
	}

	public void init() {
		Log.v(TAG, "Init");

		try
		{
			textViewListStations.setText("Stations " + Datas.GetInstance().GetCurrentLine());
			buttonAddStations.setText(R.string.add);
	
			// Log.v(TAG, "After setText");
	
			StopDAO sdao = new StopDAO(view.getContext());
			sdao.open();
	
			// Log.v(TAG, "After StopDAO");
	
			stops = sdao.getByLine(Datas.GetInstance().GetCurrentLine().getShortName());
	
			sdao.close();
		}
		catch(Exception E){
			// Log.v(TAG, E.getMessage());
		}

		myList.setAdapter(new ArrayAdapter<Stop>(view.getContext(),
				R.layout.activity_list, stops));

		intent = new Intent(view.getContext(), DetailStation.class);

		myList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Stop stop = stops.get(arg2);
				// Log.v(TAG, "Stop : " + stop);

				Datas.GetInstance().SetCurrentStop(stop);
				startActivity(intent);
			}
		});

		buttonAddStations.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				Log.v(TAG, "Methode onButtonAddStationsClick");

				intent = new Intent(view.getContext(), AddStation.class);
				intent.putExtra("Transport", transportName);
				startActivity(intent);
			}
		});
	}
}
