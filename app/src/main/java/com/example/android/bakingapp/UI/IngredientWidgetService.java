package com.example.android.bakingapp.UI;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.Context;

import com.example.android.bakingapp.POJOs.Recipe;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class IngredientWidgetService extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_UPDATE_WIDGET = "com.example.android.bakingapp.UI.action.UPDATE_WIDGET";

    // TODO: Rename parameters
    private static final String EXTRA_SELECTED_RECIPE = "com.example.android.bakingapp.UI.extra.SELECTED_RECIPE";

    public IngredientWidgetService() {
        super("IngredientWidgetService");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */

    public static void  startActionUdpateWidget(Context context, Recipe selectedRecipe){
        Intent intent = new Intent(context, IngredientWidgetService.class);
        intent.setAction(ACTION_UPDATE_WIDGET);
        intent.putExtra(EXTRA_SELECTED_RECIPE,selectedRecipe);
        context.startService(intent);


    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_WIDGET.equals(action)) {
                final Recipe selectedRecipe = (Recipe) intent.getSerializableExtra(EXTRA_SELECTED_RECIPE);
                handleActionUpdateWidget(selectedRecipe);

            }
        }
    }


    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionUpdateWidget(Recipe selectedRecipe) {
        // TODO: Handle action Foo
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds =
                appWidgetManager.getAppWidgetIds(new ComponentName(this,IngredientWidgetProvider.class));
        IngredientWidgetProvider.updateIngredientWidgets(this,appWidgetManager,selectedRecipe,appWidgetIds);

    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
