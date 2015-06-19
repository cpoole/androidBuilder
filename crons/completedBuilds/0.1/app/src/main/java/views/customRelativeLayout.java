package views;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.RelativeLayout;

import fragments.MenuCategoryFragment;
import fragments.MenuFragment;
import listeners.RelativeLayoutOnGestureListener;
import listeners.ViewPagerChangeListener;

/**
 * Created by connor on 1/26/15.
 */
public class customRelativeLayout  extends RelativeLayout {
    private String DEBUG_TAG = "sunninCafe";
    public int touchSlop;
    private float mPrevY;
    private boolean isScrolling;
    LinearLayoutManager currentLayoutManager;

    public customRelativeLayout(Context context){
        super(context);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }
    public customRelativeLayout(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }
    public customRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    /*

     */

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event){
        int action = MotionEventCompat.getActionMasked(event);
        switch(action) {
            case (MotionEvent.ACTION_DOWN):
                //Log.d(DEBUG_TAG, "Action was DOWN");
                mPrevY = MotionEvent.obtain(event).getRawY();
                RelativeLayoutOnGestureListener.startY = mPrevY;
                RelativeLayoutOnGestureListener.lastY = mPrevY;
                isScrolling = false;
                MenuCategoryFragment currentFragment = (MenuCategoryFragment) MenuFragment.customPagerAdapter.getRegisteredFragment(ViewPagerChangeListener.currentPageIndex);
                currentLayoutManager = (LinearLayoutManager) currentFragment.getRecyclerView().getLayoutManager();
                return false;
            case (MotionEvent.ACTION_MOVE):
                //Log.d(DEBUG_TAG, "Action was MOVE");

                final float eventY = event.getRawY();
                final float yDiff = Math.abs(eventY - mPrevY);
                float direction = mPrevY - eventY;

                if(MenuFragment.headerImage.getHeight() > 0 || ((direction < 0) && currentLayoutManager.findFirstCompletelyVisibleItemPosition()==0 )) {
                    if (isScrolling) {
                        return true;
                    }

                    if (Math.abs(yDiff) > touchSlop) {
                        isScrolling = true;
                        return true;
                    }
                }else {
                    Log.d(DEBUG_TAG,"returned false");
                    return false;
                }
            case (MotionEvent.ACTION_UP):
                //Log.d(DEBUG_TAG, "Action was UP");
                return false;
            case (MotionEvent.ACTION_CANCEL):
                //Log.d(DEBUG_TAG, "Action was CANCEL");
                return false;
            case (MotionEvent.ACTION_OUTSIDE):
                //Log.d(DEBUG_TAG, "Movement occurred outside bounds " +
               //         "of current screen element");
                return false;
            default:
                return false;

        }
    }
    /*
    returns true if event was handled, false otherwise
     */
    @Override
    public boolean onTouchEvent(MotionEvent event){
        if(MenuFragment.headerImage.getHeight()>0) {
            return true;
        }else  {
            return false;
        }
    }
}