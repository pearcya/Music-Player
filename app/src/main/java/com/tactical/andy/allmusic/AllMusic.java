package com.tactical.andy.allmusic;


import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.tactical.andy.musicplayer.R;
import com.tactical.andy.player.Player;


import java.io.File;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllMusic extends Fragment {

    ListView allMusicList;
    ArrayAdapter<String> musicArrayAdapter;
    String songs[];
    ArrayList<File> musics;


    public AllMusic() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_music, container, false);

        allMusicList = view.findViewById(R.id.musiclist);





        //Asking for permission to access media
        Dexter.withActivity(getActivity()).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {

                musics = findMusicsFiles(Environment.getExternalStorageDirectory());

                songs = new String[musics.size()];

                for(int i = 0; i < musics.size(); i++){

                    songs[i] = musics.get(i).getName();


                }

                musicArrayAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1, songs);

                allMusicList.setAdapter(musicArrayAdapter);

                allMusicList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent player = new Intent(getActivity(), Player.class);
                        player.putExtra("songFileList",musics);
                        player.putExtra("position", position);
                        startActivity(player);
                    }
                });

            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                //continue asking permession everytime app is open until granted
                token.continuePermissionRequest();
            }
        }).check();

        return view;
    }

    private ArrayList<File> findMusicsFiles(File file) {


        ArrayList<File> allMusicFilesObject = new ArrayList<>();
        File [] files = file.listFiles();

        for (File currentFile : files) {
            if (currentFile.isDirectory() && !currentFile.isHidden()) {
                allMusicFilesObject.addAll(findMusicsFiles(currentFile));

            } else {

                //only music files are selected
                if(currentFile.getName().endsWith(".mp3") || currentFile.getName().endsWith(".wav")){
                    allMusicFilesObject.add(currentFile);

                }


            }
        }
        return allMusicFilesObject;
    }


}
