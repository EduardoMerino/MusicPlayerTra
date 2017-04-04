package com.jumarogu.bottomnavigationview.fragments;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jumarogu.bottomnavigationview.R;

/**
 * Created by jumarogu on 28/02/17.
 */
public class ReproductorFragment extends Fragment implements View.OnClickListener{

    private FloatingActionButton bPlay, bPrev, bNext, bShuffle, bRepeat;
    private TextView tvSongName, tvSongAlbum, tvSongArtist;
    private OnFragmentPlayerInteractionListener pListener;
    private int position;

    public ReproductorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view =  inflater.inflate(R.layout.fragment_reproductor, container, false);

        this.bPlay = (FloatingActionButton)view.findViewById(R.id.pauseButton);
        this.bPlay.setOnClickListener(this);

        this.bPrev = (FloatingActionButton)view.findViewById(R.id.prevButton);
        this.bPrev.setOnClickListener(this);

        this.bNext = (FloatingActionButton)view.findViewById(R.id.nextButton);
        this.bNext.setOnClickListener(this);

        this.bShuffle = (FloatingActionButton)view.findViewById(R.id.shuffleButton);
        this.bShuffle.setOnClickListener(this);

        this.bRepeat = (FloatingActionButton)view.findViewById(R.id.repeatButton);
        this.bRepeat.setOnClickListener(this);

        this.tvSongName = (TextView)view.findViewById(R.id.tvSongName);
        this.tvSongArtist = (TextView)view.findViewById(R.id.tvSongArtis);
        this.tvSongAlbum = (TextView)view.findViewById(R.id.tvSongAlbum);

        Bundle b = this.getArguments();
        this.position = b.getInt("position");

        String songName = b.getString("songName");
        String songArtist = b.getString("songArtist");
        String songAlbum = b.getString("songAlbum");

        this.tvSongName.setText(songName);
        this.tvSongArtist.setText(songArtist);
        this.tvSongAlbum.setText(songAlbum);

        this.setButtons();

        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.pauseButton){

            if(pListener.isPlaying()) {
                this.bPlay.setImageDrawable(getResources().getDrawable(R.drawable.ic_play_arrow, getContext().getTheme()));
            }
            else {
                this.bPlay.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause, getContext().getTheme()));
            }

            pListener.play(this.position);
            System.out.println("Button play pressed");
        }
        else if (id == R.id.nextButton){

            pListener.next(this.position);
            System.out.println("Button next pressed");
        }
        else if (id == R.id.prevButton){

            pListener.back(this.position);
            System.out.println("Button previous pressed");
        }
        else if (id == R.id.shuffleButton){

            pListener.shuffleMode();
            System.out.println("Button shuffle pressed");
        }
        else if (id == R.id.repeatButton){

            pListener.loopMode();
            System.out.println("Button repeat pressed");
        }

        this.setButtons();
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ReproductorFragment.OnFragmentPlayerInteractionListener) {
            pListener = (ReproductorFragment.OnFragmentPlayerInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ReproductorFragment.OnFragmentPlayerInteractionListener");
        }
    }

    public void setButtons() {
        if(this.pListener.isInShuffle()){
            this.bShuffle.setImageDrawable(getResources().getDrawable(R.drawable.ic_shuffle_black, getContext().getTheme()));
        } else {
            this.bShuffle.setImageDrawable(getResources().getDrawable(R.drawable.ic_shuffle, getContext().getTheme()));
        }
        if(this.pListener.isInLoop()) {
            this.bRepeat.setImageDrawable(getResources().getDrawable(R.drawable.ic_repeat_black, getContext().getTheme()));
        } else {
            this.bRepeat.setImageDrawable(getResources().getDrawable(R.drawable.ic_repeat, getContext().getTheme()));
        }
    }

    public interface OnFragmentPlayerInteractionListener {
        void play(int position);
        void next(int position);
        void back(int position);
        void loopMode();
        void shuffleMode();
        boolean isPlaying();
        boolean isInLoop();
        boolean isInShuffle();
    }
}
