package droids.rizz.youtubeplayer;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

public class VideoView extends AppCompatImageView {
    public VideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public final void setEndPadding(float value) {
        this.setPadding(0, 0, (int) value, 0);
    }
}
