package hari.allagi;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hari on 15/4/18.
 */

public class MenuListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ScrollableMenuRecyclerViewAdapter adapter;
    List<String> list = new ArrayList<>();
    List<Integer> imagesList = new ArrayList<>();

    boolean clickable = true;
    boolean firstTime = true;

    public static void startActivity(@NonNull Activity activity,
                                     List<String> list,
                                     List<Integer> imagesList) {
        Intent intent = new Intent(activity, MenuListActivity.class);
        intent.putStringArrayListExtra("list", (ArrayList<String>) list);
        intent.putIntegerArrayListExtra("imagesList", (ArrayList<Integer>) imagesList);
        activity.startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_list);

        recyclerView = findViewById(R.id.recycler_view);

        clickable = true;

        if (recyclerView != null && firstTime) {
            firstTime = false;
            list = getIntent().getStringArrayListExtra("list");
            imagesList = getIntent().getIntegerArrayListExtra("imagesList");
            adapter = new ScrollableMenuRecyclerViewAdapter(this, list, imagesList);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
            recyclerView.smoothScrollToPosition(list.size() - 1);
            recyclerView.setHasFixedSize(true);
        }

        adapter.setOnClickListener(new ScrollableMenuRecyclerViewAdapter.OnClickListener() {
            @Override
            public void onClick(View view, int position, Pair[] pairs) {

                if (!clickable) {
                    return;
                }

                clickable = false;

                System.gc();

                Intent intent = new Intent(MenuListActivity.this, ScrollableMenuActivity.class);
                intent.putExtra("viewPagerInitialPosition", position);
                intent.putStringArrayListExtra("list", (ArrayList<String>) list);
                intent.putIntegerArrayListExtra("imagesList", (ArrayList<Integer>) imagesList);


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && (list.size() <= 8)) {

                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                            MenuListActivity.this, pairs);
                    startActivity(intent, options.toBundle());
                } else {
                    startActivity(intent);
                }
            }

            @Override
            public void scrollCallback(double pos) {
                recyclerView.smoothScrollToPosition(0);
            }

        });

    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {

        clickable = true;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            postponeEnterTransition();
        }

        final int position = ScrollableMenuActivity.getPosition(resultCode, data);

        if (position != -1) {
            recyclerView.scrollToPosition(position);
        }

        recyclerView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                recyclerView.getViewTreeObserver().removeOnPreDrawListener(this);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startPostponedEnterTransition();
                }
                return true;
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        clickable = true;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {

            onActivityReenter(resultCode, data);

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
