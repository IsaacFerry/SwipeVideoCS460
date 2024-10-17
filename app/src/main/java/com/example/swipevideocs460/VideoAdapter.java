package com.example.swipevideocs460;

import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


/**
 * VideoAdapter is a custom adapter for a RecyclerView that handles a list of video items.
 * It binds VideoItem objects to a RecyclerView, allowing users to swipe through and view videos.
 */
public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder>{


    private List<VideoItem> videoItems;
    /**
     * Constructor for VideoAdapter.
     *
     * @param videoItems A list of VideoItem objects that the adapter will display.
     */
    public VideoAdapter(List<VideoItem> videoItems){
        this.videoItems = videoItems;
    }

    /**
     * Called when RecyclerView needs a new ViewHolder to represent an item.
     *
     * @param parent   The parent ViewGroup.
     * @param viewType The type of the new view.
     * @return A new VideoViewHolder object.
     */
    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VideoViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container_video, parent, false)
        );
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     *
     * @param holder   The ViewHolder which should be updated.
     * @param position The position of the item in the data set.
     */
    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        holder.setVideoData(videoItems.get(position));
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The size of the videoItems list.
     */

    @Override
    public int getItemCount() {
        return videoItems.size();
    }

    /**
     * ViewHolder class for video items. Manages video playback and item views.
     */
    static class VideoViewHolder extends RecyclerView.ViewHolder{
        TextView textVideoTitle1, textVideoDescription1, textVideoID;
        VideoView videoView;
        ProgressBar progressBar;

        /**
         * Constructor for VideoViewHolder.
         *
         * @param itemView The view of the individual video item.
         */
        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            videoView = itemView.findViewById(R.id.videoView);
            textVideoTitle1 = itemView.findViewById(R.id.textVideoTitle);
            textVideoDescription1 = itemView.findViewById(R.id.textVideoDescription);
            textVideoID = itemView.findViewById(R.id.textVideoID);

            progressBar = itemView.findViewById(R.id.videoProgressBar);
        }

        /**
         * Binds the video data to the views.
         *
         * @param videoItem The VideoItem object that contains video details like title, description, and URL.
         */
        void setVideoData(VideoItem videoItem){
            textVideoTitle1.setText(videoItem.videoTitle);
            textVideoDescription1.setText(videoItem.videoDescription);
            videoView.setVideoPath(videoItem.videoURL);
            textVideoID.setText(videoItem.videoID);

            // Handle video preparation and playback.
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    progressBar.setVisibility(View.GONE);  // Hide progress bar when the video is ready.
                    mp.start(); // Start video playback.

                    // Adjust the video scale to maintain aspect ratio.
                    float videoRatio = mp.getVideoWidth() / (float) mp.getVideoHeight();
                    float screenRatio = videoView.getWidth() / (float) videoView.getHeight();
                    float scale = videoRatio / screenRatio;

                    if(scale >= 1f){
                        videoView.setScaleX(scale); // Scale horizontally if video is wider.
                    } else {
                        videoView.setScaleY(1f / scale); // Scale vertically if video is taller.
                    }

                }

            });

            // Loop the video when it finishes playing.
            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.start(); // Restart video playback when completed.
                }
            });




        }
    }


}
