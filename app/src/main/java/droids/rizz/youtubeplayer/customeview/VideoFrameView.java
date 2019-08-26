package droids.rizz.youtubeplayer.customeview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class VideoFrameView extends FrameLayout {
    public VideoFrameView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public final void setEndPadding(float value) {
        this.setPadding(0, 0, (int) value, 0);
    }
}
