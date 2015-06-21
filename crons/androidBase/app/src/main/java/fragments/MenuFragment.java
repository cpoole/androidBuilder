package fragments;

import android.content.Context;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import activities.MainActivity;
import adapters.myPagerAdapter;
import listeners.RelativeLayoutOnGestureListener;
import listeners.ViewPagerChangeListener;
import pager.PagerSlidingTabStrip;
import punchbug.sunnincafe.R;
import views.customRelativeLayout;

public class MenuFragment extends Fragment {
    private PagerSlidingTabStrip mSlidingTabStrip;
    public static ViewPager mViewPager;
    public static myPagerAdapter customPagerAdapter;
    public static customRelativeLayout menuLayout;
    public static RelativeLayout headerLayout;
    public static ImageView headerImage;
    public static TextView headerText;
    private String DEBUG_TAG = "sunninCafe";
    private GestureDetectorCompat gestureListener;
    private Context _context;

    private RecyclerView mRecyclerView;
    private static final String ARG_SECTION_NUMBER = "section_number";

    public static MenuFragment newInstance(int sectionNumber) {
        MenuFragment fragment = new MenuFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public MenuFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_menu, container, false);
        menuLayout = (customRelativeLayout) rootView.findViewById(R.id.root_menu_layout);
        headerLayout = (RelativeLayout) rootView.findViewById(R.id.header_layout);
        headerImage = (ImageView) rootView.findViewById(R.id.header_image);
        headerText = (TextView) rootView.findViewById(R.id.menuHeader_text);
        mViewPager = (ViewPager) rootView.findViewById(R.id.pager);

        gestureListener = new GestureDetectorCompat(getActivity(), new RelativeLayoutOnGestureListener());
        menuLayout.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                return gestureListener.onTouchEvent(event);
            }
        });
        if(_context != null){
          customPagerAdapter = new myPagerAdapter(getActivity().getSupportFragmentManager(),_context);
        }
        mViewPager.setAdapter(customPagerAdapter);

        mSlidingTabStrip = (PagerSlidingTabStrip) rootView.findViewById(R.id.tabs);
        mSlidingTabStrip.setOnPageChangeListener(new ViewPagerChangeListener());
        mSlidingTabStrip.setViewPager(mViewPager);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        _context = getActivity().getApplicationContext();
        ((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));

    }
}
