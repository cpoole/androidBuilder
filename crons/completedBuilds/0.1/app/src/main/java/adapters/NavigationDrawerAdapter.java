package adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import punchbug.sunninCafe.R;
import punchbug.sunnincafe.NavigationDrawerCallbacks;
import punchbug.sunnincafe.NavigationItem;

public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.ViewHolder> {

    private List<NavigationItem> mData;
    private NavigationDrawerCallbacks mNavigationDrawerCallbacks;
    private int mSelectedPosition;
    private int mTouchedPosition = -1;

    public NavigationDrawerAdapter(List<NavigationItem> data) {
        mData = data;
    }

    public NavigationDrawerCallbacks getNavigationDrawerCallbacks() {
        return mNavigationDrawerCallbacks;
    }

    public void setNavigationDrawerCallbacks(NavigationDrawerCallbacks navigationDrawerCallbacks) {
        mNavigationDrawerCallbacks = navigationDrawerCallbacks;
    }
    @Override
    public int getItemViewType(int position){
        if(position == 0){
            return 1;
        }else{
            return 0;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parentViewGroup, int viewType) {
        View v = LayoutInflater.from(parentViewGroup.getContext()).inflate(R.layout.drawer_row, parentViewGroup, false);
        ViewHolder mViewHolder = new ViewHolder(v);
        if(viewType == 1){
            //Log.d("Testing","creating first item view holder");
        }
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.textView.setText(mData.get(position).getText());
        viewHolder.textView.setCompoundDrawablesWithIntrinsicBounds(mData.get(position).getDrawable(), null, null, null);

        viewHolder.itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                   case MotionEvent.ACTION_DOWN:
                       touchPosition(position);
                       return false;
                   case MotionEvent.ACTION_CANCEL:
                       touchPosition(-1);
                       return false;
                   case MotionEvent.ACTION_MOVE:
                       return false;
                   case MotionEvent.ACTION_UP:
                       touchPosition(-1);
                       return false;
                }
            return true;
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mNavigationDrawerCallbacks != null)
                    mNavigationDrawerCallbacks.onNavigationDrawerItemSelected(position);
            }
        });

        //TODO: selected menu position, change layout accordingly
        if (mSelectedPosition == position || mTouchedPosition == position) {
            viewHolder.itemView.setBackgroundColor(viewHolder.itemView.getContext().getResources().getColor(R.color.selected_gray));
        } else {
            viewHolder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    /** notify adapter that the backgrounds have changed */
    private void touchPosition(int position) {
        int lastPosition = mTouchedPosition;
        mTouchedPosition = position;
        if (lastPosition >= 0)
            notifyItemChanged(lastPosition);
        if (position >= 0)
            notifyItemChanged(position);
    }

    /** notify adapter that the backgrounds have changed */
    public void selectPosition(int position) {
        Log.d("testing","selecting position");
        int lastPosition = mSelectedPosition;
        mSelectedPosition = position;
        notifyItemChanged(lastPosition);
        notifyItemChanged(position);
    }

    @Override
    public int getItemCount() {
        if(mData != null) {
            return mData.size();
        }else{
            return 0;
        }
        //return mData != null ? mData.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.item_name);
        }
    }
}
