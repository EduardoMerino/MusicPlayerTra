package com.jumarogu.bottomnavigationview.fragments;


import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.jumarogu.bottomnavigationview.ArrayListAdapter;
import com.jumarogu.bottomnavigationview.ExtractorArchivos;
import com.jumarogu.bottomnavigationview.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by jumarogu on 28/02/17.
 */
public class  CancionesFragment extends Fragment implements AdapterView.OnItemClickListener{

    private OnFragmentInteractionListener mListener;
    private ArrayList<HashMap<String, String>> songsList;
    private ListView lvSongs;
    private ArrayListAdapter songAdapter;
    private FloatingActionButton fabPlay;
    private MediaPlayer mediaPlayer;

    public CancionesFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view = inflater.inflate(R.layout.fragment_canciones, container, false);

        this.fabPlay = (FloatingActionButton)view.findViewById(R.id.bPlay);
        lvSongs = (ListView) view.findViewById(R.id.lvSongs);

        this.fabPlay.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if(mListener.isPlaying()){
                    mListener.play(mListener.getCurrentPosition());
                    fabPlay.setImageDrawable(getResources().getDrawable(R.drawable.ic_play_arrow, getContext().getTheme()));
                }
                else {
                    mListener.playSongSelected(0);
                }
            }
        });

        if(mListener.isPlaying()) {
            this.fabPlay.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause, getContext().getTheme()));
        }

        Bundle b = this.getArguments();
        this.songsList = (ArrayList<HashMap<String, String>>)b.getSerializable("array");
        System.out.println(this.songsList.toArray());

        this.songAdapter = new ArrayListAdapter(getActivity(), this.songsList);
        this.lvSongs.setAdapter(songAdapter);
        this.lvSongs.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        System.out.println(this.songsList.get(position).get("songPath"));
        mListener.playSongSelected(position);
    }

    public interface OnFragmentInteractionListener {
        void reproductorFragment(int position);
        void playSongSelected(int position);
        boolean isPlaying();
        int getCurrentPosition();
        void play(int position);
    }

    public void onPause() {
        super.onPause();
    }
}

