package com.example.philipp.photogallery;

import android.support.v4.app.Fragment;

import com.example.philipp.photogallery.fragments.PhotoGalleryFragment;

public class PhotoGalleryActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new PhotoGalleryFragment();
    }
}
