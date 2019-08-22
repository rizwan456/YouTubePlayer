package droids.rizz.youtubeplayer.customeview;

import android.content.Context;
import android.util.AttributeSet;

import com.google.android.exoplayer2.ui.PlayerView;

public class VideoPlayerView extends PlayerView {
    public VideoPlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public final void setEndPadding(float value) {
        this.setPadding(0, 0, (int) value, 0);
    }
}
