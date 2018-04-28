package hari.allagi;

import android.content.Context;
import android.os.Build;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by hari on 15/4/18.
 */

public class ScrollableMenuRecyclerViewAdapter extends RecyclerView.Adapter<ScrollableMenuRecyclerViewAdapter.ViewHolder>{
    Context context;
    ArrayList<String> menus;
    ArrayList<Integer> menusImages;
    OnClickListener onClickListener;
    ArrayList<Pair<View, String>> values;
    double itemCount = 4;

    public ScrollableMenuRecyclerViewAdapter(Context context, ArrayList<String> list, ArrayList<Integer> imagesList) {
        this.context = context;
        this.menus = list;
        values = new ArrayList<>();
        itemCount = AllagiUtils.getViewItemsCount(context);
        this.menusImages = imagesList;
    }

    public void setOnClickListener(OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.menu_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.listItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pair[] result = new Pair[values.size()];
                values.toArray(result);
                onClickListener.onClick(view, position, result);
            }
        });
        holder.textView.setText(menus.get(position));

        holder.imageView.setBackgroundResource(menusImages.get(position));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            holder.imageView.setTransitionName("image" + position);
            holder.textView.setTransitionName("text" + position);

            if(!values.contains(Pair.create((View) holder.imageView, ViewCompat.getTransitionName(holder.imageView))))
                values.add(Pair.create((View) holder.imageView, ViewCompat.getTransitionName(holder.imageView)));
            if(!values.contains(Pair.create((View) holder.textView, ViewCompat.getTransitionName(holder.textView))))
                values.add(Pair.create((View) holder.textView, ViewCompat.getTransitionName(holder.textView)));

            if(values.size() == 8)
            {
                onClickListener.scrollCallback(position - itemCount);
            }
        }
    }

    @Override
    public int getItemCount() {
        return menus.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout listItem;
        ImageView imageView;
        TextView textView;

        public ViewHolder(View view) {
            super(view);

            listItem = view.findViewById(R.id.relative_layout_list_item);
            textView = view.findViewById(R.id.text_view_list_item);
            imageView = view.findViewById(R.id.image_view_list_item);

        }
    }

    public interface OnClickListener{
        void onClick(View view, int position, Pair[] pairs);
        void scrollCallback(double position);
    }
    
}