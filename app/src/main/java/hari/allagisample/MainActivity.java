package hari.allagisample;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import hari.allagi.Allagi;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<String> menuList = new ArrayList<>();     //menu titles
        ArrayList<Integer> imagesList = new ArrayList<>();      //menu backgrounds
        ArrayList<Fragment> fragmentsList = new ArrayList<>();      //fragments for each menu headers in second activity

        menuList.add("UPCOMING");       //add titles
        menuList.add("EVENTS");
        menuList.add("GUEST LECTURES\n& CROSSFIRE");
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
