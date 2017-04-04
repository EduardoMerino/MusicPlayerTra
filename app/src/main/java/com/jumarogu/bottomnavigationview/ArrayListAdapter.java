package com.jumarogu.bottomnavigationview;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by jumarogu on 29/03/17.
 */

public class ArrayListAdapter extends BaseAdapter   {

    private ArrayList<HashMap<String, String>> data;
    private Activity activity;

    public ArrayListAdapter(Activity activity, ArrayList<HashMap<String, String>> data){
        this.data = data;
        this.activity = activity;
    }
    @Override
    public int getCount() {
        return this.data.size();
    }

    @Override
    public Object getItem(int position) {
       return this.data.get(position).values();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = activity.getLayoutInflater().inflate(R.layout.row_song, null);
        }

        TextView tvSongName = (TextView) convertView.findViewById(R.id.tvSongName);
        TextView tvSongArtis = (TextView) convertView.findViewById(R.id.tvSongArtis);

        tvSongName.setText(this.data.get(position).get("songTitle"));
        tvSongArtis.setText(this.data.get(position).get("songArtist"));

        return convertView;
    }
}
