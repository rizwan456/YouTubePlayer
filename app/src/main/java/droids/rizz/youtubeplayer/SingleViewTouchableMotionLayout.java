package droids.rizz.youtubeplayer;

import android.content.Context;
import android.graphics.Rect;
import android.support.constraint.motion.MotionLayout;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


public class SingleViewTouchableMotionLayout extends MotionLayout {
    private View viewToDetectTouch;

    public synchronized View getViewToDetectTouch() {
        if (viewToDetectTouch == null) {
            viewToDetectTouch = findViewById(R.id.videoViewContainer);
        }
        return findViewById(R.id.videoViewContainer);
    }


    private Rect viewRect = new Rect();
    private boolean touchStarted = false;
    private List<TransitionListener> transitionListenerList = new ArrayList<>();

    {
        addTransitionListener(new TransitionListener() {

            @Override
            public void onTransitionChange(MotionLayout motionLayout, int i, int i1, float v) {

            }

            @Override
            public void onTransitionCompleted(MotionLayout motionLayout, int i) {
                touchStarted = false;
            }
        });

        super.setTransitionListener(new TransitionListener() {

            @Override
            public void onTransitionChange(MotionLayout motionLayout, int i, int i1, float v) {
                //removeNulls(transitionListenerList);
                transitionListenerList.removeAll(Collections.singleton(null));
                for (TransitionListener list : transitionListenerList) {
                    list.onTransitionChange(motionLayout, i, i1, v);
                }
            }

            @Override
            public void onTransitionCompleted(MotionLayout motionLayout, int i) {
                transitionListenerList.removeAll(Collections.singleton(null));
                for (TransitionListener list : transitionListenerList) {
                    list.onTransitionCompleted(motionLayout, i);
                }
            }
        });
    }

    public SingleViewTouchableMotionLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setTransitionListener(TransitionListener listener) {
        addTransitionListener(listener);
    }

    public void addTransitionListener(TransitionListener listener) {
        transitionListenerList.add(listener);
    }

    GestureDetector gestureDetector = new GestureDetector(super.getContext(), new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            transitionToEnd();
            return false;
        }
    });

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_UP:
                touchStarted = false;
                return super.onTouchEvent(event);
            case MotionEvent.ACTION_CANCEL:
                touchStarted = false;
                return super.onTouchEvent(event);
        }
        if (!touchStarted) {
            getViewToDetectTouch().getHitRect(viewRect);
            touchStarted = viewRect.contains(((int) event.getX()), ((int) event.getY()));
        }

        return touchStarted && super.onTouchEvent(event);
    }

    public static <T> void removeNulls(List<T> list) {
        Iterator<T> itr = list.iterator();
        while (itr.hasNext()) {
            T curr = itr.next();
            if (curr == null)
                itr.remove();    // remove nulls
        }
    }
}
