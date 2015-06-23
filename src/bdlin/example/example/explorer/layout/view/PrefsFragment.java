/* Copyright (C) 2014 Zi-Xiang Lin <bdl9437@gmail.com>
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
package bdlin.example.example.explorer.layout.view;

import nkfust.selab.android.explorer.layout.model.TabView;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.preference.PreferenceFragment;
import android.view.MenuItem;
import android.widget.ImageButton;
import bdlin.example.example.explorer.layout.R;

public class PrefsFragment extends PreferenceFragment implements TabView {

	private ImageButton mSettingBtn;
	private int mMenuRes;

	public PrefsFragment(Context context, int imageID) {
		mSettingBtn = new ImageButton(context);
		mSettingBtn.setImageResource(imageID);
		mMenuRes = R.menu.customer_menu2;
		
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Load the preferences from an XML resource
		addPreferencesFromResource(R.xml.preferences);
	}

	public ImageButton getIndexButton() {
		return mSettingBtn;
	}

	@Override
	public Fragment getFragment() {
		return this;
	}

	@Override
	public int getMenuResource() {
		return mMenuRes;
	}
	
	@Override
	public boolean onOptionsMenuItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getActionBarTitle() {
		// TODO Auto-generated method stub
		return null;
	}
}