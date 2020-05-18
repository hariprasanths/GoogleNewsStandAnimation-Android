package hari.allagisample;

import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import hari.allagi.Allagi;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<String> menuList = new ArrayList<>();     //menu titles
        List<Integer> imagesList = new ArrayList<>();      //menu backgrounds
        List<Fragment> fragmentsList = new ArrayList<>();      //fragments for each menu headers in second activity

        menuList.add("UPCOMING");       //add titles
        menuList.add("EVENTS");
        menuList.add("GUEST LECTURES");
        menuList.add("SCHEDULE");
        menuList.add("PROFILE");
        menuList.add("MAP");
        menuList.add("VISUALIZE");
        menuList.add("HUMAN LIBRARY");

        imagesList.add(R.drawable.upcoming);        //add background images
        imagesList.add(R.drawable.events);
        imagesList.add(R.drawable.guest_lectures);
        imagesList.add(R.drawable.schedule);
        imagesList.add(R.drawable.profile);
        imagesList.add(R.drawable.map);
        imagesList.add(R.drawable.visualise);
        imagesList.add(R.drawable.human_library);

        fragmentsList.add(SampleFragment.newInstance("Upcoming fragment"));      //add fragment instances
        fragmentsList.add(SampleFragment.newInstance("Events fragment"));
        fragmentsList.add(SampleFragment.newInstance("Guest lectures fragment"));
        fragmentsList.add(SampleFragment.newInstance("Schedule fragment"));
        fragmentsList.add(SampleFragment.newInstance("Profile fragment"));
        fragmentsList.add(SampleFragment.newInstance("Map fragment"));
        fragmentsList.add(SampleFragment.newInstance("Visualize fragment"));
        fragmentsList.add(SampleFragment.newInstance("Human library fragment"));

        Allagi allagi = Allagi.initialize(MainActivity.this, menuList, imagesList, fragmentsList);
        allagi.start();         //start the menu list activity

    }
}
