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
package bdlin.example.example.explorer.layout;

import java.util.ArrayList;
import java.util.List;

import nkfust.selab.android.explorer.layout.model.ContentFragment;
import nkfust.selab.android.explorer.layout.model.TabView;
import poisondog.android.view.list.ComplexListItem;
import poisondog.android.view.list.ImageListAdapter;
import poisondog.string.ExtractPath;
import poisondog.vfs.IFile;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.widget.ImageButton;

public class SdcardListFragment extends ListFragment implements TabView {

	private List<ComplexListItem> array;
	private ImageButton remoteBtn;
	private ContentFragment article;
	private String tempPath;
	private String rootPath;

	public SdcardListFragment(Context context, int img_id,
			ContentFragment article, String filePath) {
		rootPath = filePath;
		tempPath = filePath;
		this.article = article;
		array = new ArrayList<ComplexListItem>();
		remoteBtn = new ImageButton(context);
		remoteBtn.setImageResource(img_id);
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			setAdapter(tempPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setAdapter(String path) throws Exception {
		array.clear();
		tempPath = path;
		SdcardFileData fileData = new SdcardFileData(path);

		for (IFile file : fileData.getFileList())
			if (!file.isHidden())
				array.add(new SdcardFileTransform(file));

		reloadList();
	}

	public ImageButton getIndexButton() {
		return remoteBtn;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// Set listener of list item
		getListView().setOnItemClickListener(new ListOnClick(this.article, getActivity(), array, this));
	}

	public String getCurrentPath() {
		return tempPath;
	}

	public Boolean isEqualsRootPath() {
		return new ExtractPath().process(tempPath).equals(rootPath);
	}

	public void doSortByName() {
		array = FileDoSort.doSortByName(array);
	}

	public void doSortByTime() {
		array = FileDoSort.doSortByTime(array);
	}

	public void reloadList() {
		setListAdapter(new ImageListAdapter(getActivity(), array));
	}

	@Override
	public Fragment getFragment() {
		return this;
	}
}
