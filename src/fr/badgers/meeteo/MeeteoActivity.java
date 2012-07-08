package fr.badgers.meeteo;

import java.util.List;
import java.util.Vector;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import fr.badgers.meeteo.MeeteoFragment.LocationSource;
import fr.badgers.meeteo.SearchLocationFragment.UpdateSP;

public class MeeteoActivity extends FragmentActivity implements LocationSource,
		LocationReceiver, UpdateSP {

	private Toast Toast;
	private ViewPager mViewPager;
	public List<Fragment> fragments;
	private PagerAdapter mPagerAdapter;
	private Location currLocation;
	private boolean searchselected;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.main);
		fragments = new Vector<Fragment>();
		fragments.add(Fragment.instantiate(this,
				SearchLocationFragment.class.getName()));
		fragments.add(Fragment.instantiate(this,
				SavedLocationsFragment.class.getName()));
		fragments
				.add(Fragment.instantiate(this, MeeteoFragment.class.getName()));
		mViewPager = (ViewPager) findViewById(R.id.viewpager);
		mPagerAdapter = new PagerAdapter(super.getSupportFragmentManager());
		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.setCurrentItem(1);
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				if (arg0 == 0) {
					searchselected = true;
				}
				else if (searchselected) {
					imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
//					imm.hideSoftInputFromWindow(mViewPager.getWindowToken(), 0);
					searchselected = false;
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}

	private class PagerAdapter extends FragmentPagerAdapter {

		public PagerAdapter(FragmentManager fm) {
			super(fm);
			MeeteoActivity.this.fragments = fragments;
		}

		@Override
		public Fragment getItem(int position) {
			return MeeteoActivity.this.fragments.get(position);
		}

		@Override
		public int getCount() {
			return MeeteoActivity.this.fragments.size();
		}

	}

	@Override
	public Location getSelectedLocation() {
		return currLocation;
	}

	@Override
	public void selectLocation(Location l) {
		currLocation = l;
	}

	@Override
	public void updateSP() {
		((UpdateSP) fragments.get(1)).updateSP();
	}
}
