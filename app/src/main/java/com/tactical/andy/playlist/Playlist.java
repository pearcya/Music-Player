package com.tactical.andy.playlist;


import android.content.ContentResolver;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.tactical.andy.musicplayer.R;

import java.io.File;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Playlist extends Fragment {


    TextView addPlaylist;
    Uri uri;
    ContentResolver contentResolver;
    ListView allMusicList;
    ArrayAdapter<String> musicArrayAdapter;
    String songs[];
    ArrayList<File> song;
    
    public Playlist() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_playlist, container, false);





    }


}
