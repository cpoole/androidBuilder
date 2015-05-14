package listeners;

import android.support.v4.view.MotionEventCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by connor on 1/25/15.
 */
public class RelativeLayoutOnTouchListener implements View.OnTouchListener {
    private String DEBUG_TAG = "sunninCafe";
    @Override
    public boolean onTouch(View view, MotionEvent e) {
        int action = MotionEventCompat.getActionMasked(e);
        Log.d(DEBUG_TAG, "code = " + action);
        switch(action) {
            case (MotionEvent.ACTION_DOWN):
                Log.d(DEBUG_TAG, "Action was DOWN");
                return true;
            case (MotionEvent.ACTION_MOVE):
                Log.d(DEBUG_TAG, "Action was MOVE");
                return true;
            case (MotionEvent.ACTION_UP):
                Log.d(DEBUG_TAG, "Action was UP");
                return true;
            case (MotionEvent.ACTION_CANCEL):
                Log.d(DEBUG_TAG, "Action was CANCEL");
                return true;
            case (MotionEvent.ACTION_OUTSIDE):
                Log.d(DEBUG_TAG, "Movement occurred outside bounds " +
                        "of current screen element");
                return true;
            default:
                return true;

        }
    }

}
