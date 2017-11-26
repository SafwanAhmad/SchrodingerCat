package com.example.android.mobileprogrammingassignment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by safwanx on 11/25/17.
 */

public class ChildAdapter extends RecyclerView.Adapter<ChildAdapter.ChildVH>{

    List<String> links;
    private Context context;

    public static final int TYPE_2x2 = 0;
    public static final int TYPE_1x1 = 1;

    public ChildAdapter(Context context) {
        this.context = context;
        links = new ArrayList<>();
    }

    @Override
    public ChildVH onCreateViewHolder(ViewGroup parent, int viewType) {
        ChildVH viewHolder;

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());


        View viewItem = inflater.inflate(R.layout.each_image_child, parent, false);

        viewHolder = new ChildVH(viewItem);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ChildVH holder, int position) {
        String link = links.get(position);

        String newUrl = link.replaceAll("http", "https");

        // Load the image of the user
        Picasso.with(context)
                .load(newUrl)
                .placeholder(R.drawable.ic_launcher)
                .into(((ChildVH) holder).mLinkImg);
    }

    @Override
    public int getItemCount() {
        return links == null ? 0 : links.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0 && links.size()%2 != 0){
            return TYPE_2x2;
        } else
            return TYPE_1x1;
    }

    /**
     * Child ViewHolder
     */
    class ChildVH extends RecyclerView.ViewHolder {
        private ImageView mLinkImg;

        public ChildVH(View itemView) {
            super(itemView);
            mLinkImg = (ImageView) itemView.findViewById(R.id.child_image);
            //mLinkImg.setScaleType(ImageView.ScaleType.FIT_END);
        }
    }


            /*
            Helpers - Pagination
        _________________________________________________________________________________________
            */

    public void add(String link) {
        links.add(link);
        notifyItemInserted(links.size() - 1);
    }

    public void addAll(List<String> allLinks) {
        for (String link : allLinks) {
            add(link);
        }
    }

    public void remove(String r) {
        int position = links.indexOf(r);
        if (position > -1) {
            links.remove(position);
            notifyItemRemoved(position);
        }
    }

}
