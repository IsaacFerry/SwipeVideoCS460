package com.example.swipevideocs460;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * MainActivity class manages the video swipe feature. It allows users to swipe through different video items
 * using ViewPager2, and preloads adjacent videos for smoother transitions.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Called when the activity is starting. Sets up the layout and initializes video content.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           this Bundle contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        final ViewPager2 videoViewPager = findViewById(R.id.videosViewPager);

        List<VideoItem> videoItemsList = new ArrayList<>();


        // Adding video items with their URL, title, and description.
        VideoItem videoCelebration = new VideoItem();
        videoCelebration.videoURL = "https://firebasestorage.googleapis.com/v0/b/swipevideo-cs460.appspot.com/o/1406-147169807_small.mp4?alt=media&token=9c3a5173-2e22-481d-9e03-ff5d772b6882";
        videoCelebration.videoTitle = "Mercedes Benz";
        videoCelebration.videoDescription = "Buy yourself a Mercedes today";
        videoCelebration.videoID = "ID: " + "12812379";
        videoItemsList.add(videoCelebration);

        VideoItem videoCelebration1 = new VideoItem();
        videoCelebration1.videoURL = "https://firebasestorage.googleapis.com/v0/b/swipevideo-cs460.appspot.com/o/31301-385265740_small.mp4?alt=media&token=18a8fde5-8abe-4f43-b367-c32005bde61f";
        videoCelebration1.videoTitle = "Mountain time laps";
        videoCelebration1.videoDescription = "A relaxing timelapse of a cabin house in the mountains";
        videoCelebration1.videoID = "ID: " + "87988791";
        videoItemsList.add(videoCelebration1);

        VideoItem videoCelebration2 = new VideoItem();
        videoCelebration2.videoURL = "https://firebasestorage.googleapis.com/v0/b/swipevideo-cs460.appspot.com/o/CS%202%20but%20it's%20tiktok%20%F0%9F%95%BA_%20%23cs2%20%23csgo%20%23csgovine%20%23cs2clips%20%23counterstrike%20%23tiktok%20%23shorts.mp4?alt=media&token=a0a02f21-b335-49cc-b041-d153f053b0ca";
        videoCelebration2.videoTitle = "New CS2 update";
        videoCelebration2.videoDescription = "They finally updated their anti cheat";
        videoCelebration2.videoID = "ID: " + "988349";
        videoItemsList.add(videoCelebration2);

        VideoItem videoCelebration3 = new VideoItem();
        videoCelebration3.videoURL = "https://firebasestorage.googleapis.com/v0/b/swipevideo-cs460.appspot.com/o/197898-905833761_small.mp4?alt=media&token=a000edab-d2ac-42a5-b15a-545225432bb3";
        videoCelebration3.videoTitle = "Off road";
        videoCelebration3.videoDescription = "Off road champ";
        videoCelebration3.videoID = "ID: " + "7908279";
        videoItemsList.add(videoCelebration3);



        videoViewPager.setAdapter(new VideoAdapter(videoItemsList));

        // Register a page change callback to preload adjacent videos for smoother transitions.
        videoViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                // Preload the next video if available.
                int nextPosition = position + 1;
                if (nextPosition < videoItemsList.size()) {
                    preloadVideo(videoItemsList.get(nextPosition).videoURL);
                }

                // Preload the previous video if available.
                int prevPosition = position - 1;
                if (prevPosition >= 0) {
                    preloadVideo(videoItemsList.get(prevPosition).videoURL);
                }
            }
        });
    }
    /**
     * Preloads a video to buffer the content before it is viewed, improving the user experience by reducing
     * load times when the user swipes to the next or previous video.
     *
     * @param videoURL The URL of the video to preload.
     */
    private void preloadVideo(String videoURL) {
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(videoURL);
            mediaPlayer.prepareAsync(); // Preload in background

            mediaPlayer.setOnPreparedListener(mp -> {
                // Media is now preloaded and ready to be played
                Log.d("Preload", "Video preloaded: " + videoURL);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



