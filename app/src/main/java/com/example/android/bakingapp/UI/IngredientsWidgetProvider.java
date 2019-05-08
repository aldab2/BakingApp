package com.example.android.bakingapp.UI;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.android.bakingapp.POJOs.Ingredient;
import com.example.android.bakingapp.POJOs.Recipe;
import com.example.android.bakingapp.R;

import java.util.ArrayList;
import java.util.List;


public class IngredientsWidgetProvider extends AppWidgetProvider {

    public  static final String RECIPE = "mRecipe";
    static List<Ingredient> ingredients = new ArrayList<>();
    private static String text;

    private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                        int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredient_widget_provider);
        views.setTextViewText(R.id.fxtv_ingredients_title, text);

        Intent intent = new Intent(context, IngredientsWidgetService.class);
        views.setRemoteAdapter(R.id.lv_widget_ingredients, intent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
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

        if (intent.hasExtra(RECIPE)) {
            Recipe recipe = (Recipe) intent.getSerializableExtra(RECIPE);
            text = recipe.getName();
            ingredients = recipe.getIngredients();
            Log.e("XXXXX", "onReceive: !st ingredient name: "+ingredients.get(0).getIngredient() );
        } else {
            text = context.getString(R.string.no_recipe_selected);
        }

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context.getApplicationContext());
        ComponentName thisWidget = new ComponentName(context.getApplicationContext(), IngredientsWidgetProvider.class);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.lv_widget_ingredients);
        if (appWidgetIds != null && appWidgetIds.length > 0) {
            onUpdate(context, appWidgetManager, appWidgetIds);
        }


        super.onReceive(context, intent);
    }
}
