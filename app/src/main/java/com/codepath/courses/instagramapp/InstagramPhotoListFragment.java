package com.codepath.courses.instagramapp;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.courses.instagramapp.beans.InstagramPhoto;
import com.codepath.courses.instagramapp.di.AppController;
import com.codepath.courses.instagramapp.service.InstagramService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

public class InstagramPhotoListFragment extends Fragment {

    private static String TAG = "InstagramPhotoListFragment";

    @Inject
    InstagramService mInstagramService;

    private List<InstagramPhoto> mInstagramPhotos;

    private RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((AppController) getActivity().getApplication()).getAppComponent().inject(this);

        mRecyclerView = (RecyclerView) inflater.inflate(
                R.layout.fragment_instagram_photo_list, container, false);
        setupRecyclerView(mRecyclerView);
        mRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (mInstagramPhotos.get(position).videoUrl != null && !mInstagramPhotos.get(position).videoUrl.equals("")) {
                            getActivity().startActivity(VideoPlayerActivity.getIntent(getContext(), mInstagramPhotos.get(position).videoUrl));
                        } else {
                            Toast.makeText(getContext(), "not a video", Toast.LENGTH_LONG);
                        }
                    }
                })
        );
        new AsyncHttpTask().execute();
        return mRecyclerView;
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
    }

    private List<String> getRandomSublist(String[] array, int amount) {
        ArrayList<String> list = new ArrayList<>(amount);
        Random random = new Random();
        while (list.size() < amount) {
            list.add(array[random.nextInt(array.length)]);
        }
        return list;
    }

    public static class InstagramPhotoRecyclerViewAdapter
            extends RecyclerView.Adapter<InstagramPhotoRecyclerViewAdapter.ViewHolder> {

        private final TypedValue mTypedValue = new TypedValue();
        private int mBackground;
        private List<InstagramPhoto> mInstagramPhotos;
        private Context mContext;


        public InstagramPhotoRecyclerViewAdapter(Context context, List<InstagramPhoto> instagramPhotos) {
            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
            mBackground = mTypedValue.resourceId;
            mInstagramPhotos = instagramPhotos;
            mContext = context;
        }


        public InstagramPhoto getValueAt(int position) {
            return mInstagramPhotos.get(position);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false);
            view.setBackgroundResource(mBackground);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, int position) {
            viewHolder.mInstagramPhoto = mInstagramPhotos.get(position);
            viewHolder.mTVTextView.setText(viewHolder.mInstagramPhoto.getRelativeTime());
            viewHolder.mTVUserNameTextView.setText(viewHolder.mInstagramPhoto.username);
            viewHolder.mTVCaptionTextView.setText(viewHolder.mInstagramPhoto.caption);
            //viewHolder.mTVLikesTextView.setText(viewHolder.mInstagramPhoto.likesCount);
            viewHolder.mTVComment1TextView.setText(viewHolder.mInstagramPhoto.comment1);
            viewHolder.mTVComment2TextView.setText(viewHolder.mInstagramPhoto.comment2);

            //viewHolder.mProfileImageView.setImageResource(0);
            //viewHolder.mImagePhotoImageView.setImageResource(0);
            DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
            viewHolder.mImagePhotoImageView.getLayoutParams().height = displayMetrics.widthPixels;
            Log.d("onBindViewHolder", "Profile url" + viewHolder.mInstagramPhoto.profileUrl);
            Log.d("onBindViewHolder", "Image url " + viewHolder.mInstagramPhoto.imageUrl);
            Picasso.with(mContext).load(viewHolder.mInstagramPhoto.profileUrl).into(viewHolder.mProfileImageView);
            Picasso.with(mContext).load(viewHolder.mInstagramPhoto.imageUrl).placeholder(R.mipmap.ic_launcher).into(viewHolder.mImagePhotoImageView);
            if (viewHolder.mInstagramPhoto.videoUrl != null && !viewHolder.mInstagramPhoto.videoUrl.equals("")) {
                viewHolder.mImagePhotoImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {

                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return mInstagramPhotos.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final ImageView mProfileImageView;
            public final TextView mTVTextView;
            public final TextView mTVUserNameTextView;
            public final ImageView mImagePhotoImageView;
            public final TextView mTVCaptionTextView;
            public final TextView mTVLikesTextView;
            public final TextView mTVComment1TextView;
            public final TextView mTVComment2TextView;
            public InstagramPhoto mInstagramPhoto;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mProfileImageView = (ImageView) view.findViewById(R.id.imgProfile);
                mImagePhotoImageView = (ImageView) view.findViewById(R.id.imgPhoto);
                mTVTextView = (TextView) view.findViewById(R.id.tvTime);
                mTVUserNameTextView = (TextView) view.findViewById(R.id.tvUsername);
                mTVCaptionTextView = (TextView) view.findViewById(R.id.tvCaption);
                mTVLikesTextView = (TextView) view.findViewById(R.id.tvLikes);
                mTVComment1TextView = (TextView) view.findViewById(R.id.tvComment1);
                mTVComment2TextView = (TextView) view.findViewById(R.id.tvComment2);

            }
        }
    }


    public class AsyncHttpTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Integer doInBackground(String... params) {
            Integer result = 1;
            mInstagramPhotos = mInstagramService.getPopularPhotos();
            return result;
        }

        @Override
        protected void onPostExecute(Integer result) {
            final InstagramPhotoRecyclerViewAdapter instagramPhotoRecyclerViewAdapter = new InstagramPhotoRecyclerViewAdapter(InstagramPhotoListFragment.this.getActivity(), mInstagramPhotos);
            mRecyclerView.setAdapter(instagramPhotoRecyclerViewAdapter);

        }
    }
}

