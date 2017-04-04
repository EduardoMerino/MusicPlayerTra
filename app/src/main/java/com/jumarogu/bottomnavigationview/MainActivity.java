package com.jumarogu.bottomnavigationview;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.jumarogu.bottomnavigationview.fragments.AlbumesFragment;
import com.jumarogu.bottomnavigationview.fragments.ArtistasFragment;
import com.jumarogu.bottomnavigationview.fragments.BuscarFragment;
import com.jumarogu.bottomnavigationview.fragments.CancionesFragment;
import com.jumarogu.bottomnavigationview.fragments.ReproductorFragment;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by jumarogu on 28/02/17.
 */
public class MainActivity extends AppCompatActivity implements CancionesFragment.OnFragmentInteractionListener, ReproductorFragment.OnFragmentPlayerInteractionListener{

    private ArrayList<HashMap<String, String>> songsList;
    private MediaPlayer mediaPlayer;
    private String actualFragmentTag;
    private boolean isShuffle, isLoop;
    private int currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = (BottomNavigationView) findViewById(R.id.bottom_nav_view);

        this.isShuffle = false;
        this.isLoop = false;

        this.setFragment(R.id.buscar);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if(item.getItemId() == R.id.buscar  ) {

                    setFragment(R.id.buscar);

                } else if(item.getItemId() == R.id.canciones) {

                    setFragment(R.id.canciones);

                } else if(item.getItemId() == R.id.artist) {

                    setFragment(R.id.artist);

                } else if(item.getItemId() == R.id.albums) {

                    setFragment(R.id.albums);

                }
                return false;
            }
        });

        ExtractorArchivos ea = new ExtractorArchivos(this);
        this.songsList = ea.getMusic();

    }

    public void setFragment(int id) {

        if(id == R.id.buscar) {

            showToolBar(getResources().getString(R.string.buscar), false);
            this.deleteFragment(this.actualFragmentTag);
            this.actualFragmentTag = "search";

            BuscarFragment fragmentB = new BuscarFragment();

            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.container, fragmentB, "search")
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.commit();

        } else if(id == R.id.canciones) {

            showToolBar(getResources().getString(R.string.canciones), false);
            this.deleteFragment(this.actualFragmentTag);
            this.actualFragmentTag = "songs";

            Bundle b=new Bundle();
            b.putSerializable("array",this.songsList);

            CancionesFragment fragmentC = new CancionesFragment();
            fragmentC.setArguments(b);

            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.container, fragmentC, "songs")
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.commit();

        } else if(id == R.id.artist) {

            showToolBar(getResources().getString(R.string.artistas), false);
            this.deleteFragment(this.actualFragmentTag);
            this.actualFragmentTag = "artists";

            ArtistasFragment fragmentArtist = new ArtistasFragment();

            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.container, fragmentArtist, "artists")
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.commit();

        } else if(id == R.id.albums) {

            showToolBar(getResources().getString(R.string.albumes), false);
            this.deleteFragment(this.actualFragmentTag);
            this.actualFragmentTag = "albums";

            AlbumesFragment fragmentAlbum = new AlbumesFragment();

            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.container, fragmentAlbum, "albums")
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.commit();
        }
    }

    public void showToolBar(String tittle, boolean upButton) {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }

    @Override
    public void reproductorFragment(int position) {

        this.deleteFragment(this.actualFragmentTag);
        this.actualFragmentTag = "rep";

        this.currentPosition = position;

        Bundle b=new Bundle();
        b.putInt("position", position);
        b.putString("songName", this.songsList.get(position).get("songTitle"));
        b.putString("songArtist", this.songsList.get(position).get("songArtist"));
        b.putString("songAlbum", this.songsList.get(position).get("songAlbum"));

        ReproductorFragment repFragment = new ReproductorFragment();
        repFragment.setArguments(b);
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container, repFragment, "rep")
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.commit();
    }

    @Override
    public void playSongSelected(int position) {

        System.out.println(position);

        try {

            String filePath = this.songsList.get(position).get("songPath");
            Uri myUri = Uri.parse(filePath);

            if(this.mediaPlayer == null){
                this.mediaPlayer = MediaPlayer.create(this, myUri);
            }

            if(this.mediaPlayer.isPlaying()){

                this.mediaPlayer.stop();
                this.mediaPlayer.reset();
                this.mediaPlayer.release();
                this.mediaPlayer = MediaPlayer.create(this, myUri);
            }

            this.mediaPlayer.start();
            this.reproductorFragment(position);

        } catch(Exception ex) {
            ex.printStackTrace();
        }

    }

    public void deleteFragment(String tag){

        FragmentManager manager = getFragmentManager();
        Fragment f = manager.findFragmentByTag(tag);
        FragmentTransaction transaction = manager.beginTransaction();

        if(f != null) {
            System.out.println("deleting  fragment " + tag);
            transaction.remove(f);
        }
        transaction.commit();
    }

    @Override
    public void play(int position) {

        try{

            if(this.mediaPlayer.isPlaying()) {

                this.mediaPlayer.pause();
            }
            else {

                this.mediaPlayer.start();
            }

        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void next(int position) {

        if(position < this.songsList.size() - 1) {

            this.playSongSelected(position + 1 );
        }

    }

    @Override
    public void back(int position) {
        if(position != 0) {
            this.playSongSelected(position-1);
        }

    }

    @Override
    public void loopMode() {
        if(this.isLoop) {
            this.isLoop = false;
            this.mediaPlayer.setLooping(false);
        }
        else {
            this.isLoop = true;
            this.mediaPlayer.setLooping(true);
        }
    }

    @Override
    public void shuffleMode() {
        if(!this.isShuffle) {
            this.isShuffle = true;
        }
        else {
            this.isShuffle = false;
        }
    }

    @Override
    public boolean isPlaying() {

        if(this.mediaPlayer != null ){
            return this.mediaPlayer.isPlaying();
        }
        else {
            return false;
        }
    }

    @Override
    public int getCurrentPosition() {
        return this.currentPosition;
    }

    @Override
    public boolean isInLoop() {
        return this.isLoop;
    }

    @Override
    public boolean isInShuffle() {
        return this.isShuffle;
    }
}
