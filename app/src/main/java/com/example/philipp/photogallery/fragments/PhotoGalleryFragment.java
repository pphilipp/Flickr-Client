package com.example.philipp.photogallery.fragments;

import android.os.Bundle;
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

import com.example.philipp.photogallery.FetchItemsTask;
import com.example.philipp.photogallery.R;
import com.example.philipp.photogallery.adapters.GalleryAdapter;
import com.example.philipp.photogallery.model.GalleryItem;

import java.util.ArrayList;

public class PhotoGalleryFragment extends Fragment implements FetchItemsTask.FetchItemTaskCallBack {
    private static final String LOG_TAG = PhotoGalleryFragment.class.getSimpleName();
    GridView mGridView;
    ArrayList<GalleryItem> mItems;
    RecyclerView rView;

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

        rView = (RecyclerView) v.findViewById(R.id.recycler_view);
        rView.setHasFixedSize(true);
        rView.setLayoutManager(new GridLayoutManager(v.getContext(), 2));
        setRecyclerAdapter();

        return v;
    }

    /**
     * method callBack() is resolve from asyncTask
     */
    @Override
    public void callBack(ArrayList<GalleryItem> items) {
        mItems = items;
        Log.d(LOG_TAG, "callBack: mItems.size()" + mItems.size());
        setRecyclerAdapter();
    }

    public void setRecyclerAdapter() {
        Log.d(LOG_TAG, "setRecyclerAdapter()");
        if (getActivity() == null || rView == null) return;

        if (mItems != null) {
            Log.d(LOG_TAG, "mItems.size()" + mItems.size());
            rView.setAdapter(new GalleryAdapter(getActivity(), mItems));
        } else {
            Log.d(LOG_TAG, "mItems == null");
            rView.setAdapter(null);
        }

    }

    /**
     * simple array adapter
     */
    public void setupAdapter() {
        Log.d(LOG_TAG, "setupAdapter()");
        if (getActivity() == null || mGridView == null) return;

        if (mItems != null) {
            Log.d(LOG_TAG, "mItems.size()" + mItems.size());
            mGridView.setAdapter(new ArrayAdapter<GalleryItem>(getActivity(),
                    android.R.layout.simple_gallery_item, mItems));
        } else {
            Log.d(LOG_TAG, "mItems == null");
            mGridView.setAdapter(null);
        }
    }
}