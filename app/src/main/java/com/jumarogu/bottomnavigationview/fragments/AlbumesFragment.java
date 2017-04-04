package com.jumarogu.bottomnavigationview.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jumarogu.bottomnavigationview.R;

/**
 * Created by jumarogu on 28/02/17.
 */
public class AlbumesFragment extends Fragment {


    public AlbumesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_albumes, container, false);
    }

}
