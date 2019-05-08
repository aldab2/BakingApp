package com.example.android.bakingapp.UI;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.bakingapp.POJOs.Ingredient;
import com.example.android.bakingapp.R;

import java.util.List;

public class IngredientsWidgetService extends RemoteViewsService {

    private List<Ingredient> ingredientList;


    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteListViewsFactory(getApplicationContext(),intent);
    }

    class RemoteListViewsFactory implements IngredientsWidgetService.RemoteViewsFactory {

        final Context mContext;
        Intent intent;

        RemoteListViewsFactory(Context mContext,Intent intent) {
            this.mContext = mContext;
            this.intent = intent;
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
            ingredientList = IngredientsWidgetProvider.ingredients;
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            if (ingredientList == null) return 0;
            return ingredientList.size();
        }

        @Override
        public RemoteViews getViewAt(int index) {
            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.ing_widget_item);
            Ingredient ingredient = ingredientList.get(index);
            int position = index + 1;

            String widgetItem = String.format("%1$2s %2$2s %3$2s %4$2s", position,
                    ingredient.getIngredient(), Double.toString(ingredient.getQuantity()), ingredient.getMeasure());
            views.setTextViewText(R.id.tv_widget_item, widgetItem);
            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}