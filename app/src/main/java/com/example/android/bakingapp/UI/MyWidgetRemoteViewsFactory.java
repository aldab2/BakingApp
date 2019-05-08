package com.example.android.bakingapp.UI;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Binder;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.bakingapp.POJOs.Ingredient;
import com.example.android.bakingapp.POJOs.Recipe;
import com.example.android.bakingapp.R;

import java.util.List;

public class MyWidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private Cursor mCursor;
    Recipe selectedRecipe;

    public MyWidgetRemoteViewsFactory(Context applicationContext, Intent intent) {
        mContext = applicationContext;
        this.selectedRecipe= (Recipe) intent.getSerializableExtra("recipe");
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

        if (mCursor != null) {
            mCursor.close();
        }
        mCursor = getCursorFromList(selectedRecipe.getIngredients());
     /*   final long identityToken = Binder.clearCallingIdentity();
        Uri uri = Contract.PATH_TODOS_URI;
        mCursor = mContext.getContentResolver().query(uri,
                null,
                null,
                null,
                Contract._ID + " DESC");

        Binder.restoreCallingIdentity(identityToken);*/

    }
    public Cursor getCursorFromList(List<Ingredient> ingredients) {
        MatrixCursor cursor = new MatrixCursor(
                new String[] {"ingredient", "measure", "quantity"}
        );

        for ( Ingredient ingredient: ingredients) {
            cursor.newRow()
                    .add("ingredient", ingredient.getIngredient())
                    .add("measure", ingredient.getMeasure())
                    .add("quantity", ingredient.getQuantity()+"");

        }

        return cursor;
    }

    @Override
    public void onDestroy() {
        if (mCursor != null) {
            mCursor.close();
        }
    }

    @Override
    public int getCount() {
        return mCursor == null ? 0 : mCursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (position == AdapterView.INVALID_POSITION ||
                mCursor == null || !mCursor.moveToPosition(position)) {
            return null;
        }

        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.ingredient_step_item);
        rv.setTextViewText(R.id.tv_ingredient_ingredient, mCursor.getString(1));

        return rv;
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
    public long getItemId(int position) {
        return mCursor.moveToPosition(position) ? mCursor.getLong(0) : position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

}