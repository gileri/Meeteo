package fr.badgers.meeteo;

import java.util.List;
import java.util.Vector;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

public class LocationChooserActivity extends FragmentActivity {

	private Toast Toast;
	private ViewPager mViewPager;
	public List<Fragment> fragments;
	private PagerAdapter mPagerAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.locations);
		fragments = new Vector<Fragment>();
		fragments.add(Fragment.instantiate(this,
				SavedLocationsFragment.class.getName()));
		fragments.add(Fragment.instantiate(this,
				SearchLocationFragment.class.getName()));

		mViewPager = (ViewPager) findViewById(R.id.viewpager);
		mPagerAdapter = new PagerAdapter(super.getSupportFragmentManager());
		mViewPager.setAdapter(mPagerAdapter);
	}

	private class PagerAdapter extends FragmentPagerAdapter {

		public PagerAdapter(FragmentManager fm) {
			super(fm);
			LocationChooserActivity.this.fragments = fragments;
		}

		@Override
		public Fragment getItem(int position) {
			return LocationChooserActivity.this.fragments.get(position);
		}

		@Override
		public int getCount() {
			return LocationChooserActivity.this.fragments.size();
		}

	}
}
