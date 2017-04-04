package com.jumarogu.bottomnavigationview;

/**
 * Created by Merino on 24/02/17.
 */


import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Merino on 24/02/17.
 */

public class ExtractorArchivos {

    public ArrayList<HashMap<String, String>> songsList;
    private Activity act;

    /* Empty constructor
     * pre: none.
     * Post: create Extractor de archivos.
     */
    public ExtractorArchivos(Activity activity) {
        songsList = new ArrayList<HashMap<String, String>>();
        act = activity;
    }

    /* Arraylis Constructor.
     * pre: none.
     * Post: create Extractor de archivos with a reference to the songsList
     */
    public ExtractorArchivos(Activity activity, ArrayList<HashMap<String, String>> list) {
        songsList = list;
        act = activity;
    }


    public ArrayList<HashMap<String, String>> getMusic() {
        ContentResolver cr = act.getContentResolver();

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.DATA + " LIKE ? ";
        String[] musicPath =  new String[]{"%/storage/emulated/0/Music/%"};
        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";
        Cursor cur = cr.query(uri, null, selection, musicPath, sortOrder);

        int count = 0;

        if (cur != null) {
            count = cur.getCount();

            if (count > 0) {
                while (cur.moveToNext()) {
                    String data = cur.getString(cur.getColumnIndex(MediaStore.Audio.Media.DATA));
                    // Add code to get more column here

                    // Save to your list here
                    HashMap<String, String> songMap = new HashMap<String, String>();
                    songMap.put("songTitle", cur.getString(cur.getColumnIndex(MediaStore.Audio.Media.TITLE)));
                    songMap.put("songArtist", cur.getString(cur.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
                    songMap.put("songAlbum", cur.getString(cur.getColumnIndex(MediaStore.Audio.Media.ALBUM)));
                    //songMap.put("songArt", cur.getString(cur.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART)));
                    songMap.put("songPath", cur.getString(cur.getColumnIndex(MediaStore.Audio.Media.DATA)));

                    Log.d("", songMap.get("songTitle"));

                    // Adding each song to SongList
                    songsList.add(songMap);
                }
            }
        }
        cur.close();
        return songsList;
    }
}
