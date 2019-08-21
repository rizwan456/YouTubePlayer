package droids.rizz.youtubeplayer.customeview;

import android.content.Context;
import android.util.AttributeSet;

import com.facebook.drawee.view.SimpleDraweeView;

public class VideoView extends SimpleDraweeView {
    public VideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public final void setEndPadding(float value) {
        this.setPadding(0, 0, (int) value, 0);
    }
}
