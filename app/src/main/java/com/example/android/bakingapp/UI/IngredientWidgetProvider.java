package com.example.android.bakingapp.UI;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.example.android.bakingapp.POJOs.Recipe;
import com.example.android.bakingapp.R;

/**
 * Implementation of App Widget functionality.
 */
public class IngredientWidgetProvider extends AppWidgetProvider {
    Recipe selectedRecipe = new Recipe(1,"Ingredients",0);

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                Recipe selectedRecipe, int appWidgetId) {

/*
        CharSequence widgetText = context.getString(R.string.appwidget_text);
*/      String widgetText;
        if (selectedRecipe.getIngredients() !=null) {
            widgetText = selectedRecipe.getName()+"\n"+selectedRecipe.getIngredients();

        }
        else
            widgetText = selectedRecipe.getName()+"\n";

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredient_widget_provider);
        views.setTextViewText(R.id.fxtv_ingredients, widgetText);


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them

        IngredientWidgetService.startActionUdpateWidget(context, selectedRecipe);
        /*for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }*/
    }
    public static void updateIngredientWidgets(Context context, AppWidgetManager appWidgetManager, Recipe selectedRecipe, int[] appWidgetIds) {

        for (int appWidgetId : appWidgetIds) {
     	            updateAppWidget(context, appWidgetManager,selectedRecipe , appWidgetId);
            RemoteViews remoteViews = updateWidgetListView(context,
                    appWidgetId);
        }
        // TODO (4): Call startActionUpdatePlantWidgets in onUpdate as well as in AddPlantActivity and PlantDetailActivity (add and delete plants)
    	    }
    private static RemoteViews updateWidgetListView(Context context,
                                             int appWidgetId) {

        //which layout to show on widget
        RemoteViews remoteViews = new RemoteViews(
                context.getPackageName(),R.layout.ingredient_widget_provider);

        //RemoteViews Service needed to provide adapter for ListView
        Intent svcIntent = new Intent(context, IngredientWidgetProvider.class);
        //passing app widget id to that RemoteViews Service
        svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        //setting a unique Uri to the intent
        //don't know its purpose to me right now
        svcIntent.setData(Uri.parse(
                svcIntent.toUri(Intent.URI_INTENT_SCHEME)));
        //setting adapter to listview of the widget
        remoteViews.setRemoteAdapter(appWidgetId, R.id.lv_widget_ingredients,
                svcIntent);
        //setting an empty view in case of no data
        remoteViews.setEmptyView(R.id.lv_widget_ingredients, R.id.empty_view);
        return remoteViews;
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        if (action.equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
            AppWidgetManager mgr = AppWidgetManager.getInstance(context);
            ComponentName cn = new ComponentName(context, IngredientWidgetProvider.class);
            mgr.notifyAppWidgetViewDataChanged(mgr.getAppWidgetIds(cn), R.id.lv_widget_ingredients);

        }

        super.onReceive(context, intent);
    }

    public static void sendRefreshBroadcast(Context context, Recipe selectedRecipe) {
        Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.putExtra("recipe",selectedRecipe);
        intent.setComponent(new ComponentName(context, IngredientWidgetProvider.class));
        context.sendBroadcast(intent);
    }
}

