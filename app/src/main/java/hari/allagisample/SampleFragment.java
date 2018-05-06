package hari.allagisample;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SampleFragment extends Fragment{

    Context context;
    TextView titleView;

    public SampleFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sample, container, false);
        String title = "Fragment";
        if (getArguments() != null) {
            title = getArguments().getString("title");
        }
        titleView = view.findViewById(R.id.fragmentTitle);
        titleView.setText(title);
        return view;
    }

    public static SampleFragment newInstance(String title) {

        Bundle args = new Bundle();
        args.putString("title", title);
        SampleFragment fragment = new SampleFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
