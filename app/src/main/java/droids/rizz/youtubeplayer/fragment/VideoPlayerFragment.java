package droids.rizz.youtubeplayer.fragment;


import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.motion.MotionLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
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

import droids.rizz.youtubeplayer.MainActivity;
import droids.rizz.youtubeplayer.R;
import droids.rizz.youtubeplayer.adadpters.VideoViewAdapter;
import droids.rizz.youtubeplayer.databinding.FragmentVideoPlayerBinding;
import droids.rizz.youtubeplayer.interfaces.IVideoPlayer;
import droids.rizz.youtubeplayer.supporters.Utility;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VideoPlayerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideoPlayerFragment extends Fragment implements IVideoPlayer {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private boolean isFirst = false;

    public FragmentVideoPlayerBinding videoPlayerBinding;

    private SimpleExoPlayer player;

    private long playbackPosition;
    private int currentWindow;
    private boolean playWhenReady = true;

    public VideoPlayerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VideoPlayerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VideoPlayerFragment newInstance(String param1, String param2) {
        VideoPlayerFragment fragment = new VideoPlayerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static VideoPlayerFragment newInstance(String param1, String param2, boolean isFirst) {
        VideoPlayerFragment fragment = new VideoPlayerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putBoolean("First", isFirst);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            isFirst = getArguments().getBoolean("First", false);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        videoPlayerBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_video_player, container, false);

        setUp();
        //changeVideo(mParam1);
        if (isFirst) {
            changeVideo(mParam1);
        }
        return videoPlayerBinding.getRoot();
    }

    private void setUp() {
        //videoPlayerBinding.videoView.setImageResource(R.drawable.dark_knight);
        videoPlayerBinding.videoMotionLayout.setTransitionListener(new MotionLayout.TransitionListener() {
            @Override
            public void onTransitionChange(MotionLayout motionLayout, int i, int i1, float v) {
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).mainBinding.mainMotionLayout.setProgress(Math.abs(v));
                }
            }

            @Override
            public void onTransitionCompleted(MotionLayout motionLayout, int i) {

            }
        });

        videoPlayerBinding.videoRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        videoPlayerBinding.videoRecyclerView.setAdapter(new VideoViewAdapter(getActivity(), Utility.generateVideoList(), this));

       /* videoPlayerBinding.videoViewContainer.setOnClickListener(v->{
            videoPlayerBinding.videoView.setUseController(false);
        });*/
    }


    @Override
    public void changeVideo(String videoUrl) {
        //videoPlayerBinding.videoView.setImageURI(videoUrl);
        initializePlayer();
        videoPlayerBinding.videoMotionLayout.post(() -> {
            videoPlayerBinding.videoMotionLayout.transitionToEnd();
        });
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

            videoPlayerBinding.videoView.setPlayer(player);
            videoPlayerBinding.videoView.setUseController(false);

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
