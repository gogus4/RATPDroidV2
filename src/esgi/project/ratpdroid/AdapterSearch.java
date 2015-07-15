package esgi.project.ratpdroid;

import java.util.List;

import esgi.project.ratpdroid.model.Stop;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class AdapterSearch extends CursorAdapter {

	private List<Stop> stops;

	private TextView text;

	public AdapterSearch(Context context, Cursor cursor, List<Stop> stops) {

		super(context, cursor, false);

		this.stops = stops;
	}

	@Override
	public void bindView(View arg0, Context arg1, Cursor arg2) {
		text.setText(stops.get(arg2.getPosition()).toString());
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View view = inflater.inflate(R.layout.item, parent, false);

		text = (TextView) view.findViewById(R.id.item);

		return view;
	}
}
