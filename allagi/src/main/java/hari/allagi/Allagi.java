package hari.allagi;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import java.util.ArrayList;

/**
 * Created by hari on 15/4/18.
 */

public class Allagi {

    static ArrayList<Fragment> fragments;
    private Activity activity;
    private ArrayList<String> list = new ArrayList<>();
    private ArrayList<Integer> imagesList = new ArrayList<>();

    private Allagi(@NonNull Activity activity, ArrayList<String> list,
                   ArrayList<Integer> imagesList, ArrayList<Fragment> fragmentsList) {
        this.activity = activity;
        this.list = list;
        this.imagesList = imagesList;
        fragments = fragmentsList;
    }

    public static Allagi initialize(@NonNull Activity activity, ArrayList<String> list,
                                    ArrayList<Integer> imagesList, ArrayList<Fragment> fragmentsList) {
        return new Allagi(activity, list, imagesList, fragmentsList);
    }

    public static ArrayList<Fragment> getFragments() {
        return fragments;
    }

    public void start() {
        activity.finish();
        MenuListActivity.startActivity(activity, list, imagesList);
    }

}
