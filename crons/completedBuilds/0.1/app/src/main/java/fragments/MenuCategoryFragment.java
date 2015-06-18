package fragments;

/**
 * Created by connor on 1/19/15.
 */

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import DAO.menuItem;
import activities.MainActivity;
import adapters.ParallaxRecyclerAdapter;
import managers.menuManager;
import punchbug.sunninCafe.R;

public class MenuCategoryFragment extends Fragment {
    private RecyclerView recyclerView;
    private ActionBar headerToolbar;
    private GestureDetectorCompat detector;
    private String DEBUG_TAG = "sunninCafe";


    public static float logicalDensity;
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section number.
     */
    public static MenuCategoryFragment newInstance(int sectionNumber) {
        MenuCategoryFragment fragment = new MenuCategoryFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        Log.d("sunninCafe", "section number = " + sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
    public MenuCategoryFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.menu_category_fragment, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        headerToolbar = ((ActionBarActivity) getActivity()).getSupportActionBar();
        //MenuFragment.headerText.setText(MenuFragment.customPagerAdapter.getPageTitle(getArguments().getInt(ARG_SECTION_NUMBER)));
        Log.d("SunninCafe", "hello from onCreateView");

//        recyclerView.addOnItemTouchListener(
//                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
//
//                    @Override
//                    public void onItemClick(View view, int position) {
////						Intent intent = new Intent(getActivity(), ZBarScannerActivity.class);
////						intent.putExtra(ZBarConstants.SCAN_MODES,  new int[]{Symbol.QRCODE});
////						startActivityForResult(intent, ZBAR_SCANNER_REQUEST);
////						//Log.d("itp341","hello from on click");
//
//                    }
//               }));

        //recyclerView.addOnItemTouchListener(new ParallaxRecyclerTouchListener());
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        logicalDensity = metrics.density;

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));

    }

    @Override
    public void  onActivityCreated(Bundle savedInstanceState){
        Log.d("sunninCafe","hello from onactivitycreated");
        Log.d("sunninCare","onactivity sectionNumber= "+ getArguments().getInt(ARG_SECTION_NUMBER));
        super.onActivityCreated(savedInstanceState);
        final ArrayList<menuItem> foodEntries;
        switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
            
            case 1:
                foodEntries = menuManager.getCategory("Appetizer");
                break;
            
            case 2:
                foodEntries = menuManager.getCategory("Soups and Salads");
                break;
            
            case 3:
                foodEntries = menuManager.getCategory("Sides");
                break;
            
            case 4:
                foodEntries = menuManager.getCategory("Entree");
                break;
            
            default:
                foodEntries = new ArrayList<>();
                break;
        }

        //set layoutManger
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);


        ParallaxRecyclerAdapter<menuItem> mAdapter = new ParallaxRecyclerAdapter<menuItem>(menuManager.getMenuEntries());
        mAdapter.implementRecyclerAdapterMethods(new ParallaxRecyclerAdapter.RecyclerAdapterMethods(){
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i){
                View itemLayoutView = (LayoutInflater.from(getActivity()).inflate(R.layout.item_row, viewGroup, false));
                SimpleViewHolder mViewHolder = new SimpleViewHolder(itemLayoutView);
                return mViewHolder;
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position){
                ((SimpleViewHolder) viewHolder).foodTitle.setText(foodEntries.get(position).getTitle());
                ((SimpleViewHolder) viewHolder).foodDescription.setText(foodEntries.get(position).getDescription());
                float price = foodEntries.get(position).getPrice();
                String strPrice = String.valueOf(price);
                ((SimpleViewHolder) viewHolder).foodPrice.setText(strPrice);

            }
            @Override
            public int getItemCount() {
                return foodEntries.size();
            }
        });
//        mAdapter.setParallaxHeader(LayoutInflater.from(getActivity()).inflate(R.layout.parallax_header,recyclerView, false), recyclerView);

        //mAdapter.setParallaxHeader(headerImage, recyclerView);
//		mAdapter.setOnParallaxScroll(new ParallaxRecyclerAdapter.OnParallaxScroll() {
//			@Override
//			public void onParallaxScroll(float percentage, float offset, View parallax) {
//				 Drawable c = mToolbar.getBackground();
//				 c.setAlpha(Math.round(percentage * 255));
//				 mToolbar.setBackground(c);
//
//			}
//		});

        //set adapter
        recyclerView.setAdapter(mAdapter);
        //set item animator to DefaultAnimator
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    public RecyclerView getRecyclerView(){
        return recyclerView;
    }


    static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public TextView foodTitle;
        public TextView foodDescription;
        public TextView foodPrice;
        public SimpleViewHolder(View itemView) {
            super(itemView);
            foodTitle = (TextView) itemView.findViewById(R.id.firstLine);
            foodDescription = (TextView) itemView.findViewById(R.id.secondLine);
            foodPrice = (TextView) itemView.findViewById(R.id.price);
        }
    }
}