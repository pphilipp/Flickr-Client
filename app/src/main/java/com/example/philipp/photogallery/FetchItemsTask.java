package com.example.philipp.photogallery;

import android.os.AsyncTask;
import android.util.Log;

import com.example.philipp.photogallery.model.GalleryItem;

import java.util.ArrayList;

public class FetchItemsTask extends AsyncTask<Void, Void, ArrayList<GalleryItem>> {
    private static final String LOG_TAG = FetchItemsTask.class.getSimpleName();
    public FetchItemTaskCallBack mCallBack;


    public FetchItemsTask(FetchItemTaskCallBack callBack) {
        mCallBack = callBack;
    }

    @Override
    protected ArrayList<GalleryItem> doInBackground(Void... params) {
        Log.d(LOG_TAG, "doInBackground()");
        return new FlickrFetchr().fetchItems();
    }

    @Override
    protected void onPostExecute(ArrayList<GalleryItem> galleryItems) {
        Log.d(LOG_TAG, "onPostExecute()");
        if(mCallBack != null)
            mCallBack.callBack(galleryItems);
    }

    /** interface for CallBack*/
    public interface FetchItemTaskCallBack {
        void callBack(ArrayList<GalleryItem> galleryItems);
    }

}
