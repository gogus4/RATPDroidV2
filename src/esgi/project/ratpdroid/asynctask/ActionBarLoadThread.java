package esgi.project.ratpdroid.asynctask;

import java.util.ArrayList;
import java.util.List;
import esgi.project.ratpdroid.AdapterSearch;
import esgi.project.ratpdroid.model.Stop;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.database.MatrixCursor;
import android.os.Handler;
import android.util.Log;
import android.widget.SearchView;

public class ActionBarLoadThread extends Thread {

	private static final String TAG = "MyThread";

	protected Activity mActivity;
	protected Handler mHandler;

	private String query;
	private List<Stop> stops;
	private List<Stop> stopsSearch;
	private Context context;
	private MatrixCursor cursor;
	private SearchView search;

	public ActionBarLoadThread(String query, List<Stop> stops, Context context,
			SearchView search) {
		Log.v(TAG, "MyThread Constructor");

		this.query = query;
		this.stops = stops;
		this.context = context;
		this.search = search;
	}

	@Override
	public void run() {
		super.run();

		Log.v(TAG, "running...");

		try {
			stopsSearch = new ArrayList<Stop>();

			String[] columns = new String[] { "_id", "text" };
			Object[] temp = new Object[] { 0, "default" };

			cursor = new MatrixCursor(columns);

			for (int i = 0; i < stops.size(); i++) {
				if (stops.get(i).getName().toString().startsWith(query)) {
					stopsSearch.add(stops.get(i));

					temp[0] = i;
					temp[1] = stops.get(i);

					cursor.addRow(temp);
				}
			}

			Activity activity = (Activity) context;
			if (activity != null && !activity.isFinishing()) {
				activity.runOnUiThread(new Runnable() {
					@Override
					public void run() {

						SearchManager manager = (SearchManager) context
								.getSystemService(Context.SEARCH_SERVICE);
						search.setSuggestionsAdapter(new AdapterSearch(context,
								cursor, stopsSearch));
					}
				});
			}
		} catch (Exception e) {
			Log.e(TAG, "InterruptedException", e);
		}
	}
}
