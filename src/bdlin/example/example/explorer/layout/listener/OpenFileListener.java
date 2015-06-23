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
package bdlin.example.example.explorer.layout.listener;

import java.util.ArrayList;
import java.util.List;

import nkfust.selab.android.explorer.layout.model.ContentFragment;
import nkfust.selab.android.explorer.layout.model.TabFragment;
import poisondog.vfs.FileType;
import poisondog.vfs.IFile;
import poisondog.vfs.LocalFolder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import bdlin.example.example.explorer.layout.R;
import bdlin.example.example.explorer.layout.view.SdcardListFragment;

public class OpenFileListener{

	private static ContentFragment mContentFragment;
	private SdcardListFragment mFragment;
	private FragmentActivity mActivity;

	public OpenFileListener(ContentFragment content, FragmentActivity activity,
			SdcardListFragment fragment) {
		mContentFragment = content;
		mActivity = activity;
		mFragment = fragment;
	}// End of ListOnClick construct
	
	public void open(int position) {
		List<IFile> aIFileArray = new ArrayList<IFile>();
		aIFileArray.addAll(mFragment.getIFileList());
		try {
			if (aIFileArray.get(position).getType() == FileType.DATA) {
				if (TabFragment.getFrameLayout() != null) {
					mContentFragment.setTabFragment((TabFragment)TabFragment.getTabFragment());
					mContentFragment.setIFile(aIFileArray.get(position));
					mContentFragment.setIFileList(aIFileArray);
					Bundle args = new Bundle();
					args.putInt("position", position);
					mContentFragment.setArguments(args);
					mContentFragment.setReadArgument(false);
					mActivity.getSupportFragmentManager().beginTransaction()
							.add(R.id.fragment_container, mContentFragment)
							.addToBackStack(null).commit();
				} else {
					mContentFragment.setIFileList(aIFileArray);
					mContentFragment.updateBrowseView(aIFileArray.get(position));
				}// End of inner if-else condition

			} else {
				LocalFolder folder = (LocalFolder) aIFileArray.get(position);
				mFragment.setAdapter(folder.getUrl());
			}// End of if-else condition
		} catch (Exception e) {
			e.printStackTrace();
		}// End of try-catch
	}// End of onItemClick Function
	
	public static void initContent() {
		mContentFragment = null;
	}
	
	public static ContentFragment getContent() {
		return mContentFragment;
	}
}// End of ListOnClick Class