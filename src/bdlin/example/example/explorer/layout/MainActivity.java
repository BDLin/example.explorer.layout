/*
 * Copyright (C) 2012 The Android Open Source Project
 * Copyright (C) 2014 Zi-Xiang Lin <bdl9437@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package bdlin.example.example.explorer.layout;

import nkfust.selab.android.explorer.layout.model.ContentFragment;
import nkfust.selab.android.explorer.layout.model.DecideFileView;
import nkfust.selab.android.explorer.layout.model.TabFragment;
import nkfust.selab.android.explorer.layout.model.VideoControllerView;
import nkfust.selab.android.explorer.layout.model.VideoPlayerView;
import poisondog.string.ExtractParentUrl;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.FrameLayout;

public class MainActivity extends FragmentActivity {

	private TabFragment tabView;
	private ContentFragment article;
	private SdcardListFragment sdFrag;
	private SdcardListFragment offFrag;
	private PrefsFragment presFrag;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news_articles);

		if (findViewById(R.id.fragment_container) != null) {
			tabView = new TabFragment();
			TabFragment.setFrameLayout((FrameLayout)findViewById(R.id.fragment_container));
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
					             WindowManager.LayoutParams.FLAG_FULLSCREEN);
			getSupportFragmentManager().beginTransaction()
					.add(R.id.fragment_container, tabView).commit();
		} else
			tabView = (TabFragment) getSupportFragmentManager()
					.findFragmentById(R.id.headlines_fragment);

		article = (ContentFragment) getSupportFragmentManager()
				.findFragmentById(R.id.article_fragment);

		sdFrag = new SdcardListFragment(this, R.drawable.folder_remote, R.menu.title_file_list,
				article, Environment.getExternalStorageDirectory().getAbsolutePath());
		offFrag = new SdcardListFragment(this, R.drawable.download_folder_small_icon, R.menu.customer_menu1, 
				article, Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download");
		presFrag = new PrefsFragment(this, R.drawable.android_settings, R.menu.customer_menu2);
	}// End of onCreate

	@Override
	protected void onPause() {
		super.onPause();
		if (DecideFileView.getVideoView() != null)
			DecideFileView.getVideoView().stop();
	}

	@Override
	public void onStart() {
		super.onStart();
		if (tabView.isFragmentStatePagerAdapterNull()) {
			tabView.clean();
			tabView.addTabView(sdFrag);
			tabView.addTabView(offFrag);
			tabView.addTabView(presFrag);
		}
		setContentSize();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Checks the orientation of the screen
		VideoPlayerView video = DecideFileView.getVideoView();
		if (video != null) {
			setContentSize();
			video.setScreenSize();
		}
	}// End of onConfigurationChanged function
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu){
		tabView.setMenu(menu);
		getMenuInflater().inflate(R.menu.title_file_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return tabView.getCurrentTabView().onOptionsMenuItemSelected(item);
	}// End of onOptionsItemSelected

	public void onBackPressed() {
		if (findViewById(R.id.fragment_container) != null && ListOnClick.getContent() != null) {
			getActionBar().show();
			DecideFileView.ReleaseMediaPlayer();
			ListOnClick.initContent();
			super.onBackPressed();
		} else {
			if (tabView.getCurrentFragment() == sdFrag) {
				if (sdFrag.isEqualsRootPath()) {
					if (article != null)
						DecideFileView.ReleaseMediaPlayer();
					super.onBackPressed();
				} else
					try {
						sdFrag.setAdapter(new ExtractParentUrl().process(sdFrag.getCurrentPath()));
					} catch (Exception e) {
						e.printStackTrace();
					}
			} else if (tabView.getCurrentFragment() == offFrag) {
				if (offFrag.isEqualsRootPath()) {
					if (article != null)
						DecideFileView.ReleaseMediaPlayer();
					super.onBackPressed();
				} else
					try {
						offFrag.setAdapter(new ExtractParentUrl().process(offFrag.getCurrentPath()));
					} catch (Exception e) {
						e.printStackTrace();
					}
			} else if (tabView.getCurrentFragment() == presFrag) {
				if (article != null)
					DecideFileView.ReleaseMediaPlayer();
				super.onBackPressed();
			}// End of if-else if
		}// End of if-else
	}// End of onBackPressed
	
	public void setContentSize(){
		Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
		if (findViewById(R.id.fragment_container) == null) 
			VideoControllerView.setContentSize(display.getHeight(),display.getWidth() * 2 / 3);
		else
			VideoControllerView.setContentSize(display.getHeight(),display.getWidth());
	}
}// End of MainActivity