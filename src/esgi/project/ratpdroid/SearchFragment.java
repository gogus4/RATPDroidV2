package esgi.project.ratpdroid;

import esgi.project.ratpdroid.model.Stop;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class SearchFragment extends Fragment {
	
	private static final String TAG = "SearchFragment";
	
	private ImageView buttonSearch;
	private EditText editTextSearch;
	private Intent intent;
	
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
		View view = inflater.inflate(R.layout.fragment_search, container, false);
		
		buttonSearch = (ImageView) view.findViewById(R.id.searchButton);
		editTextSearch = (EditText) view.findViewById(R.id.editTextSearch);
		
		Log.v(TAG, "onCreateView");
		
		buttonSearch.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Log.v(TAG, "Click sur le bouton search");

				for (Stop stop : Datas.GetInstance().GetStops()) {
					if (stop.getName().contains(
							editTextSearch.getText().toString().toUpperCase())) {
						Datas.GetInstance().SetCurrentStop(stop);

						intent = new Intent(v.getContext(), DetailStation.class);
						startActivity(intent);

						break;
					}
				}
			}
		});
		
		return view;
	}
}
