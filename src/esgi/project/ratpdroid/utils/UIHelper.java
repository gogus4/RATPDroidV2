package esgi.project.ratpdroid.utils;

import java.util.List;
import esgi.project.ratpdroid.Datas;
import esgi.project.ratpdroid.DetailStation;
import esgi.project.ratpdroid.R;
import esgi.project.ratpdroid.asynctask.ActionBarLoadThread;
import esgi.project.ratpdroid.db.StopDAO;
import esgi.project.ratpdroid.model.Stop;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.util.Log;
import android.view.Menu;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

public class UIHelper {

	private static final String TAG = "UIHelper";

	private List<Stop> stops;
	private Context context;
	private Menu menu;
	private Intent intent;

	private ActionBarLoadThread actionBarThread;

	SearchView search;

	private UIHelper() {
	}

	private static UIHelper instance = new UIHelper();

	public static UIHelper getInstance() {
		return instance;
	}

	public void setSearchActionBar(Context context, Menu menu,
			ComponentName componentName) {
		StopDAO sdao = new StopDAO(context);
		sdao.open();

		stops = sdao.getAll();

		this.context = context;
		this.menu = menu;

		SearchManager manager = (SearchManager) context
				.getSystemService(Context.SEARCH_SERVICE);

		search = (SearchView) menu.findItem(R.id.search).getActionView();

		search.setSearchableInfo(manager.getSearchableInfo(componentName));

		search.setOnQueryTextListener(new OnQueryTextListener() {

			@Override
			public boolean onQueryTextChange(String query) {

				loadHistory(query);

				return true;
			}

			@Override
			public boolean onQueryTextSubmit(String arg0) {
				return true;
			}
		});
	}

	private void loadHistory(String query) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			final SearchView search = (SearchView) menu.findItem(R.id.search)
					.getActionView();

			actionBarThread = new ActionBarLoadThread(query, stops, context,
					search);
			actionBarThread.start();

			search.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
				@Override
				public boolean onSuggestionSelect(int i) {
					Log.v(TAG, "onSuggestionSelect");
					return true;
				}

				@Override
				public boolean onSuggestionClick(int i) {
					Cursor cursor = (Cursor) search.getSuggestionsAdapter()
							.getItem(i);
					String feedName = cursor.getString(1);
					Log.v(TAG, "onSuggestionClick : " + feedName);

					for (Stop stop : Datas.GetInstance().GetStops()) {
						if (stop.getName().toString().equals(feedName)) {
							Log.v(TAG, "onQueryTextSubmit");
							Datas.GetInstance().SetCurrentStop(stop);

							intent = new Intent(context, DetailStation.class);
							context.startActivity(intent);

							search.clearFocus();

							return true;
						}
					}

					return false;
				}
			});
		}
	}
}
