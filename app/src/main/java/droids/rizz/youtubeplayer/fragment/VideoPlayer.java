package droids.rizz.youtubeplayer.fragment;


import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.dash.DashChunkSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import droids.rizz.youtubeplayer.R;
import droids.rizz.youtubeplayer.databinding.VideoPlayerBinding;


public class VideoPlayer extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    VideoPlayerBinding videoPlayerBinding;

    private String mParam1;
    private String mParam2;

    //for video player
    private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();
    private SimpleExoPlayer player;
    private long playbackPosition;
    private int currentWindow;
    private boolean playWhenReady = true;


    public VideoPlayer() {
        // Required empty public constructor
    }

    public static VideoPlayer newInstance(String param1, String param2) {
        VideoPlayer fragment = new VideoPlayer();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        videoPlayerBinding = DataBindingUtil.inflate(inflater, R.layout.video_player, container, false);
        setUp();
        return videoPlayerBinding.getRoot();
    }

    private void setUp() {
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //hideSystemUi();
        if ((Util.SDK_INT <= 23 || player == null)) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    private void initializePlayer() {
        if (player == null) {
            TrackSelection.Factory adaptiveTrackSelectionFactory = new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);
            player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getActivity()),
                    new DefaultTrackSelector(adaptiveTrackSelectionFactory), new DefaultLoadControl());

            videoPlayerBinding.videoPlayer.setPlayer(player);
            videoPlayerBinding.videoPlayer.setUseController(false);

            player.setPlayWhenReady(playWhenReady);
            player.seekTo(currentWindow, playbackPosition);
        }
        MediaSource mediaSource = buildMediaSource(Uri.parse(getString(R.string.media_url_dash)));
        player.prepare(mediaSource, true, false);
    }

    private MediaSource buildMediaSource(Uri uri) {
        DashChunkSource.Factory dashChunkSourceFactory = new DefaultDashChunkSource.Factory(
                new DefaultHttpDataSourceFactory("ua", BANDWIDTH_METER));
        DataSource.Factory manifestDataSourceFactory = new DefaultHttpDataSourceFactory("ua");
        return new DashMediaSource.Factory(dashChunkSourceFactory, manifestDataSourceFactory).
                createMediaSource(uri);
    }

    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }

}
