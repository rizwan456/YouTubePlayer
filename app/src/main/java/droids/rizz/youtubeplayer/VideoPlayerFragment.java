package droids.rizz.youtubeplayer;


import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.motion.MotionLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import droids.rizz.youtubeplayer.adadpters.VideoViewAdapter;
import droids.rizz.youtubeplayer.databinding.FragmentVideoPlayerBinding;
import droids.rizz.youtubeplayer.interfaces.IVideoPlayer;


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

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FragmentVideoPlayerBinding videoPlayerBinding;


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
        videoPlayerBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_video_player, container, false);
        setUp();
        return videoPlayerBinding.getRoot();
    }

    private void setUp() {

        videoPlayerBinding.videoView.setImageResource(R.drawable.dark_knight);
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
    }


    @Override
    public void changeVideo(String videoUrl) {
        videoPlayerBinding.videoView.setImageURI(Uri.parse(videoUrl));
    }
}
