package com.example.philipp.photogallery.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.example.philipp.photogallery.FetchItemsTask;
import com.example.philipp.photogallery.R;
import com.example.philipp.photogallery.model.GalleryItem;

import java.util.ArrayList;

public class PhotoGalleryFragment extends Fragment implements FetchItemsTask.FetchItemTaskCallBack {
    private static final String LOG_TAG = PhotoGalleryFragment.class.getSimpleName();
    GridView mGridView;
    ArrayList<GalleryItem> mItems;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "onCreate()");
        setRetainInstance(true);
        new FetchItemsTask(this).execute();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photo_gallery, container, false);
        Log.d(LOG_TAG, "onCreateView()");
        mGridView = (GridView) v.findViewById(R.id.gridView);
        setupAdapter();

        return v;
    }

    public void setupAdapter() {
        Log.d(LOG_TAG, "setupAdapter()");
        if (getActivity() == null || mGridView == null) return;

        if (mItems != null) {
            Log.d(LOG_TAG, "mItems.size()" + mItems.size());
            mGridView.setAdapter(new ArrayAdapter<>(getActivity(),
                    android.R.layout.simple_gallery_item, mItems));
        } else {
            Log.d(LOG_TAG, "mItems == null");
            mGridView.setAdapter(null);
        }
    }

    @Override
    public void doSomething(ArrayList<GalleryItem> items) {
        mItems = items;
        setupAdapter();
    }
}