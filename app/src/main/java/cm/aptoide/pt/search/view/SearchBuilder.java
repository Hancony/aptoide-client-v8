/*
 * Copyright (c) 2016.
 * Modified by pedroribeiro on 19/01/2017
 */

package cm.aptoide.pt.search.view;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.SearchView;
import android.view.MenuItem;
import cm.aptoide.pt.search.SearchActionsHandler;
import cm.aptoide.pt.search.SearchNavigator;
import cm.aptoide.pt.search.suggestionsprovider.AppSuggestions;

/**
 * Created by neuro on 01-06-2016.
 */
public class SearchBuilder {

  private final SearchNavigator searchNavigator;
  private final SearchManager searchManager;
  private final String lastQuery;

  public SearchBuilder(@NonNull SearchManager searchManager,
      @NonNull SearchNavigator searchNavigator) {
    this(searchManager, searchNavigator, null);
  }

  public SearchBuilder(@NonNull SearchManager searchManager,
      @NonNull SearchNavigator searchNavigator, @Nullable String lastQuery) {
    this.searchManager = searchManager;
    this.searchNavigator = searchNavigator;
    this.lastQuery = lastQuery;
  }

  public void attachSearch(@NonNull Context context, @NonNull MenuItem menuItem) {
    final Context applicationContext = context.getApplicationContext();
    final SearchView searchView = (SearchView) menuItem.getActionView();

    final UnableToSearchAction unableToSearchAction =
        new WebSocketUnableToSearchAction(applicationContext);

    final WebSocketQueryResultRepository queryResultRepository =
        new WebSocketQueryResultRepository(searchView);

    final AppSuggestions appSuggestions = new AppSuggestions();

    final SearchActionsHandler actionsHandler =
        new SearchActionsHandler(appSuggestions.getSearchSocket(), menuItem,
            searchNavigator, unableToSearchAction, queryResultRepository, lastQuery);

    final ComponentName componentName = new ComponentName(applicationContext, SearchActivity.class);
    searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName));
    searchView.setOnQueryTextListener(actionsHandler);
    searchView.setOnSuggestionListener(actionsHandler);
    searchView.setOnQueryTextFocusChangeListener(actionsHandler);
    searchView.setOnSearchClickListener(actionsHandler);
    searchView.setQueryRefinementEnabled(true);
  }

  public boolean isValid() {
    return searchManager != null && searchNavigator != null;
  }
}
