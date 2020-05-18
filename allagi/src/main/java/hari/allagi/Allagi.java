package hari.allagi;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hari on 15/4/18.
 */

public class Allagi {

    static List<Fragment> fragments;
    private Activity activity;
    private List<String> list = new ArrayList<>();
    private List<Integer> imagesList = new ArrayList<>();
    static long transitionDuration = 1000;

    private Allagi(@NonNull Activity activity, List<String> list,
                   List<Integer> imagesList, List<Fragment> fragmentsList) {
        this.activity = activity;
        this.list = list;
        this.imagesList = imagesList;
        fragments = fragmentsList;
    }

    public static Allagi initialize(@NonNull Activity activity, List<String> list,
                                    List<Integer> imagesList, List<Fragment> fragmentsList) {
        return new Allagi(activity, list, imagesList, fragmentsList);
    }

    public static List<Fragment> getFragments() {
        return fragments;
    }

    public void start() {
        activity.finish();
        MenuListActivity.startActivity(activity, list, imagesList);
    }

    public void setTransitionDuration(long milliSeconds) {
        transitionDuration = milliSeconds;
    }

    public static long getTransitionDuration() {
        return transitionDuration;
    }

}
