package listeners;

import android.support.v4.view.ViewPager;

import fragments.MenuFragment;

/**
 * Created by connor on 1/24/15.
 */
public class ViewPagerChangeListener extends ViewPager.SimpleOnPageChangeListener {
    public static int currentPageIndex = 0;
    public void onPageSelected(int position) {
        currentPageIndex = position;
        MenuFragment.headerText.setText(MenuFragment.customPagerAdapter.getPageTitle(position));
    }
}