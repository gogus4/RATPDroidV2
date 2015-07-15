package esgi.project.ratpdroid;

import java.util.List;

import esgi.project.ratpdroid.db.LineDAO;
import esgi.project.ratpdroid.model.Line;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ListTransportsFragment extends Fragment {
	
	private static final String TAG = "ListTransportsFragment";
	
	private ListView myList;
	private TextView textViewListTransports;
	private List<Line> lines;
	
	private Intent intent;

	private String transports;
	
	private View view;
	
	public String getTransports() {
		return transports;
	}

	public void setTransports(String transports) {
		this.transports = transports;
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
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_list_transports, container, false);
		
		Log.v(TAG, "onCreateView");
		
		myList = (ListView) view.findViewById(R.id.listAllTransports);
		textViewListTransports = (TextView) view.findViewById(R.id.textViewListTransports);

		intent = new Intent(getActivity(), ListStations.class);
		
		return view;
	}
	
	private Integer getTransport(String transport) {
		if (transport.equalsIgnoreCase("TRAM"))
			return 0;

		else if (transport.equalsIgnoreCase("METRO"))
			return 1;

		else if (transport.equalsIgnoreCase("RER"))
			return 2;

		else
			return 3;
	}
	
	public void init()
	{
		Log.v(TAG, "onCreateView transports : " + transports);
		
		textViewListTransports.setText(transports);

		LineDAO ldao = new LineDAO(view.getContext());
		ldao.open();

		lines = ldao.getByType(getTransport(transports));
		
		ldao.close();

		for (Line line : lines) {
			Log.v(TAG, line.toString());
		}

		myList.setAdapter(new ArrayAdapter<Line>(view.getContext(), R.layout.activity_list,lines));

		myList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
				Line line = lines.get(arg2);
				Log.v(TAG, "Ligne : " + line);

				Datas.GetInstance().SetCurrentLine(line);
						
				ListStationsFragment fragment = (ListStationsFragment) getFragmentManager().findFragmentById(R.id.listStationsFragment);
				
				if (fragment != null && fragment.isInLayout()) {
					fragment.init();
				}
				
				else {
					//Log.v(TAG, "Before Start Activity");
					Log.v(TAG,"Line : " + Datas.GetInstance().GetCurrentLine());
					startActivity(intent);
					//Log.v(TAG, "After Start Activity");
				}
			}
		});
	}
}
