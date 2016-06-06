package com.example.philipp.photogallery.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.philipp.photogallery.R;
import com.example.philipp.photogallery.adapters.GalleryAdapter;
import com.example.philipp.photogallery.model.FetchItemsTask;
import com.example.philipp.photogallery.model.GalleryItem;
import com.example.philipp.photogallery.model.ThumbnailDownloader;

import java.util.ArrayList;

public class PhotoGalleryFragment extends Fragment implements FetchItemsTask.FetchItemTaskCallBack {
    private static final String TAG = PhotoGalleryFragment.class.getSimpleName();
    GridView mGridView;/** NOT USED NOW */
    ArrayList<GalleryItem> mItems;
    RecyclerView rView;
    ThumbnailDownloader<ImageView> mThumbnailThread;
    public static final int COUNT_OF_ITEMS_GREAD = 3;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
        setRetainInstance(true);
        new FetchItemsTask(this).execute();

        mThumbnailThread = new ThumbnailDownloader(new Handler());
        mThumbnailThread.setListener(new ThumbnailDownloader.Listener<ImageView>() {
            public void onThumbnailDownloaded(ImageView imageView, Bitmap thumbnail) {
                if (isVisible()) {
                    imageView.setImageBitmap(thumbnail);
                }
            } });
        mThumbnailThread.start();
        mThumbnailThread.getLooper();
        Log.d(TAG, "Background thread started!!");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photo_gallery, container, false);
        Log.d(TAG, "onCreateView()");

        rView = (RecyclerView) v.findViewById(R.id.recycler_view);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(new GridLayoutManager(v.getContext(), COUNT_OF_ITEMS_GREAD));
        setRecyclerAdapter();

        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mThumbnailThread.quit();
        mThumbnailThread.clearQueue();
        Log.d(TAG, "Background thread destroyed!!");
    }

    /**
     * method callBack() is resolve from asyncTask
     */
    @Override
    public void callBack(ArrayList<GalleryItem> items) {
        mItems = items;
        Log.d(TAG, "callBack: mItems.size()" + mItems.size());
        setRecyclerAdapter();
    }

    public void setRecyclerAdapter() {
        Log.d(TAG, "setRecyclerAdapter()");
        if (getActivity() == null || rView == null) return;

        if (mItems != null) {
            Log.d(TAG, "mItems.size()" + mItems.size());
            rView.setAdapter(new GalleryAdapter(getActivity(), mItems, mThumbnailThread));
        } else {
            Log.d(TAG, "mItems == null");
            rView.setAdapter(null);
        }

    }

    /**
     * simple array adapter NOT USED NOW!
     */
    public void setupAdapter() {
        Log.d(TAG, "setupAdapter()");
        if (getActivity() == null || mGridView == null) return;

        if (mItems != null) {
            Log.d(TAG, "mItems.size()" + mItems.size());
            mGridView.setAdapter(new ArrayAdapter<GalleryItem>(getActivity(),
                    android.R.layout.simple_gallery_item, mItems));
        } else {
            Log.d(TAG, "mItems == null");
            mGridView.setAdapter(null);
        }
    }
}