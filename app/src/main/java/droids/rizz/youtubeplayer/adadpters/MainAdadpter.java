package droids.rizz.youtubeplayer.adadpters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import droids.rizz.youtubeplayer.MainActivity;
import droids.rizz.youtubeplayer.R;
import droids.rizz.youtubeplayer.databinding.MainViewItemsBinding;
import droids.rizz.youtubeplayer.fragment.BlankFragment;
import droids.rizz.youtubeplayer.fragment.VideoPlayerFragment;
import droids.rizz.youtubeplayer.model.VideoInfo;

public class MainAdadpter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    MainViewItemsBinding itemsBinding;

    Context context;
    List<VideoInfo> infoList;
    boolean status = false;

    public MainAdadpter(Context context, List<VideoInfo> infoList) {
        this.context = context;
        this.infoList = infoList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        itemsBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.main_view_items, viewGroup, false);
        return new ItemViewHolder(itemsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof ItemViewHolder) {
            ((ItemViewHolder) viewHolder).bindData(i);
        }
    }

    @Override
    public int getItemCount() {
        return infoList.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        MainViewItemsBinding itemsBinding;

        ItemViewHolder(MainViewItemsBinding itemsBinding) {
            super(itemsBinding.getRoot());
            this.itemsBinding = itemsBinding;
        }

        void bindData(int pos) {
            itemsBinding.channelThumbnail.setImageResource(R.drawable.google);
            itemsBinding.videoThumbnail.setImageURI(Uri.parse(infoList.get(pos).getImageUrl()));
            itemsBinding.videoSubTitle.setText(infoList.get(pos).getSubTitle());
            itemsBinding.videoTitle.setText(infoList.get(pos).getTitle());

            itemsBinding.videoThumbnail.setOnClickListener(v -> {
                if (context instanceof MainActivity) {

                    Fragment fragment = ((MainActivity) context).getSupportFragmentManager().findFragmentById(R.id.container);

                    if (fragment instanceof VideoPlayerFragment) {
                        //when video is playing
                        VideoPlayerFragment videoPlayerFragment = (VideoPlayerFragment) fragment;
                        videoPlayerFragment.changeVideo(infoList.get(pos).getImageUrl(), infoList.get(pos).getTitle());
                    } else if (fragment instanceof BlankFragment) {
                        //when video closed
                        ((MainActivity) context).addBlankFragment(fragment);
                    } else {
                        //when fragment null
                        ((MainActivity) context).addVideoFragment(infoList.get(pos).getImageUrl(), infoList.get(pos).getTitle());
                    }

                }
            });
        }
    }


}
