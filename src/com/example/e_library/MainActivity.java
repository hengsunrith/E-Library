package com.example.e_library;

import java.util.List;

import android.app.Activity;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.SearchView;
import android.widget.TextView;

public class MainActivity extends Activity implements SearchView.OnQueryTextListener{
	
	private SearchView mSearchView;
	private TextView mStatusview;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		setContentView(R.layout.activity_main);
		mStatusview = (TextView) findViewById(R.id.status_text);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	
		super.onCreateOptionsMenu(menu);
		
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.searchview_in_menu, menu);
		MenuItem searchItem = menu.findItem(R.id.action_search);
		mSearchView = (SearchView) searchItem.getActionView();
		setupSearchView(searchItem);
		
		return true;
	}
	
	private void setupSearchView(MenuItem searchItem) {
		if (isAlwaysExpanded()) {
			mSearchView.setIconifiedByDefault(false);
		}else{
			searchItem.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
		}
		
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		if (searchManager != null) {
			List<SearchableInfo> searchableInfos = searchManager.getSearchablesInGlobalSearch();
			SearchableInfo info = searchManager.getSearchableInfo(getComponentName());
			for (SearchableInfo inf : searchableInfos) {
				if (inf.getSuggestAuthority() != null && inf.getSuggestAuthority().startsWith("applications")) {
					info = inf;
				}
			}
			mSearchView.setSearchableInfo(info);
		}
		mSearchView.setOnQueryTextListener(this);
	}
	
	public boolean onQueryTextChange(String newText) {
		mStatusview.setText("Query = " + newText);
		return false;
	}
	
	public boolean onQueryTextSubmit(String query) {
		mStatusview.setText("Query = " + query + ": sumitted");
		return false;	
	}
	
	public boolean onClose() {
		mStatusview.setText("closed!");
		return false;
	}
	protected boolean isAlwaysExpanded() {
		return false;
	}

}
