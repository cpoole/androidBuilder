package fragments;

/**
 * Created by connor on 12/14/14.
 */

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import managers.userManager;
import punchbug.sunninCafe.R;
import views.DrawView;
import views.punchMark;

public class PunchCardFragment extends Fragment {
    private static final int ZBAR_SCANNER_REQUEST = 0;
    private static final int ZBAR_QR_SCANNER_REQUEST = 1;
    private int width;
    private int height;
    //private userManager user;
    private View linearView;
    private LinearLayout linearLayout;
    private DrawView rootView;
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section number.
     */
    public static PunchCardFragment newInstance(int sectionNumber) {
        PunchCardFragment fragment = new PunchCardFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public PunchCardFragment() {
    }

    @Override
    public DrawView onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = new DrawView(getActivity());
        linearView = inflater.inflate(R.layout.punchcard_fragment, container, false);
        linearLayout = (LinearLayout) linearView.findViewById(R.id.rootLinear);
        rootView.setBackgroundResource(R.drawable.bg1);
        for (int i=0; i< userManager.getNumPunches(); i++){
            linearLayout.addView(new punchMark(getActivity(),i));
        }
        rootView.invalidate();
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));

    }

    @Override
    public void  onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data)
//    {
//        if (resultCode == Activity.RESULT_OK)
//        {
//            if(data.getStringExtra(ZBarConstants.SCAN_RESULT).toString().equals("redeem")){
//                Toast.makeText(getActivity(), "Code Successfull from punch", Toast.LENGTH_SHORT).show();
//                //addPunch();
//            }else{
//                Toast.makeText(getActivity(), "failure", Toast.LENGTH_SHORT).show();
//
//            }
//            // Scan result is available by making a call to data.getStringExtra(ZBarConstants.SCAN_RESULT)
//            // Type of the scan result is available by making a call to data.getStringExtra(ZBarConstants.SCAN_RESULT_TYPE)
//            //Toast.makeText(getActivity(), "Scan Result = " + data.getStringExtra(ZBarConstants.SCAN_RESULT).toString(), Toast.LENGTH_SHORT).show();
//            //Toast.makeText(getActivity(), "Scan Result Type = " + data.getIntExtra(ZBarConstants.SCAN_RESULT_TYPE, 0), Toast.LENGTH_SHORT).show();
//            // The value of type indicates one of the symbols listed in Advanced Options below.
//        } else if(resultCode == Activity.RESULT_CANCELED) {
//            //Toast.makeText(getActivity(), "Camera unavailable", Toast.LENGTH_SHORT).show();
//        }
//    }
    public void addPunch(){
        int lastNum = userManager.getNumPunches();
        userManager.addPunch();
        int currentNum = lastNum++;
        linearLayout.addView(new punchMark(getActivity(),currentNum));
    }

}