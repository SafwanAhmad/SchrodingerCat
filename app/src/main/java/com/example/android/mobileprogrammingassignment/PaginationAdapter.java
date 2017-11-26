package com.example.android.mobileprogrammingassignment;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.mobileprogrammingassignment.model.User;
import com.example.android.mobileprogrammingassignment.utils.PaginationAdapterCallback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by safwanx on 11/25/17.
 */

public class PaginationAdapter extends RecyclerView.Adapter<PaginationAdapter.HeaderVH> {

    private List<User> users;
    private Context context;
    private PaginationAdapterCallback mCallback;
    protected RecyclerView mChildRecyclerView;

    public PaginationAdapter(Context context) {
        this.context = context;
        this.mCallback = (PaginationAdapterCallback) context;
        users = new ArrayList<>();
    }

    @Override
    public HeaderVH onCreateViewHolder(ViewGroup parent, int viewType) {
        HeaderVH viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View viewItem = inflater.inflate(R.layout.each_user_list, parent, false);
        viewHolder = new HeaderVH(viewItem);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HeaderVH holder, int position) {
        User user = users.get(position);

        // Set the user name
        holder.mUserName.setText(user.getName());

        String newUrl = user.getImageLink().replaceAll("http", "https");

        // Load the image of the user
        Picasso.with(context)
                .load(newUrl)
                .placeholder(R.drawable.ic_launcher)
                .into(holder.mUserImg);


        // Set the animator for our child recycler view
        mChildRecyclerView.setItemAnimator(new DefaultItemAnimator());

        // Attach an adapter for child recycler view
        final ChildAdapter childAdapter = new ChildAdapter(context);
        // Update the data
        childAdapter.addAll(user.getItems());
        mChildRecyclerView.setAdapter(childAdapter);

        // Check the number of links for a user
        final int length = user.getItems().size();

        GridLayoutManager mLayoutManager = (GridLayoutManager)mChildRecyclerView.getLayoutManager();

        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch(childAdapter.getItemViewType(position)){
                    case ChildAdapter.TYPE_2x2:
                        return 2;
                    case ChildAdapter.TYPE_1x1:
                        return 1;
                    default:
                        return -1;
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return users == null ? 0 : users.size();
    }

    /*
   View Holder
   _________________________________________________________________________________________________
    */

    /**
     * Header ViewHolder
     */
    protected class HeaderVH extends RecyclerView.ViewHolder {
        private TextView mUserName;
        private ImageView mUserImg;

        public HeaderVH(View itemView) {
            super(itemView);

            mUserName = (TextView) itemView.findViewById(R.id.user_name);
            mUserImg = (ImageView) itemView.findViewById(R.id.profile_image);
            mChildRecyclerView = (RecyclerView) itemView.findViewById(R.id.child_recycler);
        }
    }

     /*
        Helpers - Pagination
   _________________________________________________________________________________________________
    */

    public void add(User r) {
        users.add(r);
        notifyDataSetChanged();
    }

    public void addAll(List<User> allUsers) {
        for (User user : allUsers) {
            add(user);
        }
    }

    public void remove(User r) {
        int position = users.indexOf(r);
        if (position > -1) {
            users.remove(position);
            notifyItemRemoved(position);
        }
    }
}