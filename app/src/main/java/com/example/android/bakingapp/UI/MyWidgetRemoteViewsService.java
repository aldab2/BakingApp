package com.example.android.bakingapp.UI;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by Kingdom on 5/7/2019.
 */

public class MyWidgetRemoteViewsService extends RemoteViewsService {


    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new MyWidgetRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}
