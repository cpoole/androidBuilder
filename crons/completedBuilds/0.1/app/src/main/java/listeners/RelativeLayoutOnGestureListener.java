package listeners;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewGroup;

import fragments.MenuCategoryFragment;
import fragments.MenuFragment;

/**
 * Created by connor on 1/26/15.
 */
public class RelativeLayoutOnGestureListener extends GestureDetector.SimpleOnGestureListener {
    public static float startY;
    public static float lastY;
    @Override
    public boolean onDown(MotionEvent e){
        Log.d("sunninCafe","hello from onDown");
        startY = e.getRawY();
        lastY = e.getRawY();
        return super.onDown(e);
    }
    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {

        // handle single tap
        Log.d("sunninCafe", "yo");

        return super.onSingleTapConfirmed(e);
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY){

        float dy = lastY-e2.getRawY();
        //Log.d("sunninCafe", "delta y = " + dy);
        lastY = e2.getRawY();

        ViewGroup.LayoutParams imageParams = (ViewGroup.LayoutParams) MenuFragment.headerImage.getLayoutParams();

        if (dy > 0) {
            if (imageParams.height - dy > 0) {
                // Animation animation = new TranslateAnimation(Animation.ABSOLUTE,0, Animation.ABSOLUTE,0,Animation.ABSOLUTE,imageParams.height,Animation.ABSOLUTE,imageParams.height -dy);
                //MenuFragment.headerLayout.setTranslationY(dy);
                //ValueAnimator anim = ValueAnimator.ofInt(imageParams.)
                imageParams.height = imageParams.height - (int)dy;
                MenuFragment.headerImage.setLayoutParams(imageParams);
                return super.onScroll(e1,e2,velocityX,velocityY);

            } else {
                imageParams.height = 0;
                MenuFragment.headerImage.setLayoutParams(imageParams);

                Log.d("sunninCafe", "false from gesture detector");

                return false;
                //return super.onScroll(e1,e2,velocityX,velocityY);
            }
        } else {
            if (imageParams.height - dy < ((int) Math.ceil(200 * MenuCategoryFragment.logicalDensity))) {
                imageParams.height = imageParams.height - (int)dy;
                MenuFragment.headerImage.setLayoutParams(imageParams);
                return super.onScroll(e1,e2,velocityX,velocityY);


            }else{
                return super.onScroll(e1,e2,velocityX,velocityY);

            }
        }


    }
    @Override
    public void onLongPress(MotionEvent e) {

        // handle long press
        Log.d("sunninCafe","yo2");


        super.onLongPress(e);
    }
}