package droids.rizz.youtubeplayer.adadpters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import droids.rizz.youtubeplayer.interfaces.IVideoPlayer;
import droids.rizz.youtubeplayer.R;
import droids.rizz.youtubeplayer.databinding.VideoViewItemBinding;
import droids.rizz.youtubeplayer.model.VideoInfo;

public class VideoViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    VideoViewItemBinding itemBinding;

    Context context;
    List<VideoInfo> infoList;
    IVideoPlayer iVideoPlayer;

    public VideoViewAdapter(Context context, List<VideoInfo> infoList, IVideoPlayer iVideoPlayer) {
        this.context = context;
        this.infoList = infoList;
        this.iVideoPlayer = iVideoPlayer;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        itemBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.video_view_item, viewGroup, false);
        return new ItemViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder=(ItemViewHolder) viewHolder;
            itemViewHolder.bindData(i);
        }
    }

    @Override
    public int getItemCount() {
        return infoList.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        VideoViewItemBinding itemBinding;

        ItemViewHolder(VideoViewItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }

        void bindData(int pos) {
            itemBinding.videoThumbnail.setImageURI(Uri.parse(infoList.get(pos).getImageUrl()));
            itemBinding.videoTitle.setText(infoList.get(pos).getTitle());
            itemBinding.videoSubTitle.setText(infoList.get(pos).getSubTitle());

            itemBinding.getRoot().setOnClickListener(v->{
                iVideoPlayer.changeVideo(infoList.get(pos).getImageUrl());
            });
        }
    }
}
