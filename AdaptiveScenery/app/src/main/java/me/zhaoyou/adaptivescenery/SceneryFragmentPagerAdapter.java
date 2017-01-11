package me.zhaoyou.adaptivescenery;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by ZhaoYou on 11/01/2017.
 */
public class SceneryFragmentPagerAdapter extends FragmentPagerAdapter {
  // 1:
  final int PAGE_COUNT = 2;
  private String tabTitles[] = new String[] { "Scenery", "Details" };

  public SceneryFragmentPagerAdapter(FragmentManager fm) {
    super(fm);
  }

  @Override
  public int getCount() {
    // 2:
    return PAGE_COUNT;
  }

  @Override
  public Fragment getItem(int position) {
    // 3:
    switch (position) {
      case 0:
        return new ImageFragment();
      case 1:
        return new InfoFragment();
      default:
        return null;
    }
  }

  @Override
  public CharSequence getPageTitle(int position) {
    // 4:
    return tabTitles[position];
  }
}
