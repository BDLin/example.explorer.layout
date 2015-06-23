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

import java.util.ArrayList;
import java.util.List;

import nkfust.selab.android.explorer.layout.model.ContentFragment;
import nkfust.selab.android.explorer.layout.model.TabView;
import poisondog.android.view.list.ComplexListItem;
import poisondog.android.view.list.ImageListAdapter;
import poisondog.string.ExtractPath;
import poisondog.vfs.IFile;
import poisondog.vfs.LocalFileFactory;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import bdlin.example.example.explorer.layout.R;
import bdlin.example.example.explorer.layout.listener.OpenFileListener;
import bdlin.example.example.explorer.layout.processor.CreateFolder;
import bdlin.example.example.explorer.layout.processor.FileDoSort;
import bdlin.example.example.explorer.layout.processor.SdcardFileData;

public class SdcardListFragment extends ListFragment implements TabView{

	private List<ComplexListItem> array;
	private List<IFile> iFileList;
	private ImageButton remoteBtn;
	private ContentFragment mContentFragment;
	private String tempPath;
	private String rootPath;
	private int menuRes;
	
	public SdcardListFragment(Context context, int img_id, int menuRes,
			ContentFragment content, String filePath) {
		this.menuRes = menuRes;
		mContentFragment = content;
		rootPath = filePath;
		tempPath = filePath;
		array = new ArrayList<ComplexListItem>();
		iFileList = new ArrayList<IFile>();
		remoteBtn = new ImageButton(context);
		remoteBtn.setImageResource(img_id);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			setAdapter(tempPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onViewCreated (View view, Bundle savedInstanceState){
		super.onViewCreated(view, savedInstanceState);
		// Set listener of list item
		final SdcardListFragment fragment = this;
		getListView().setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				new OpenFileListener(mContentFragment, getActivity(), fragment).open(position);
			}
		});
	}

	@Override
	public Fragment getFragment() {
		return this;
	}

	@Override
	public int getMenuResource() {
		return menuRes;
	}
	
	@Override
	public boolean onOptionsMenuItemSelected(MenuItem item) {
		switch (item.getItemId()){
			case R.id.create_folder:
				new CreateFolder(getActivity(), this).DisplayDialog();
				break;
			case R.id.sort_by_name:
				doSortByName();
				reloadList();
				break;
			case R.id.sort_by_time:
				doSortByTime();
				reloadList();
				break;
		}
		return true;
	}

	@Override
	public String getActionBarTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setAdapter(String path) throws Exception {
		array.clear();
		tempPath = path;
		SdcardFileData fileData = new SdcardFileData(path);
		
		for (IFile file : fileData.getFileList())
			if (!file.isHidden())
				array.add(new SdcardFileTransform(file));
		
		refreshIFileList(array);
		updateMusicList();
		reloadList();
	}
	
	public ImageButton getIndexButton() {
		return remoteBtn;
	}
	
	public String getCurrentPath() {
		return tempPath;
	}

	public Boolean isEqualsRootPath() {
		return new ExtractPath().process(tempPath).equals(rootPath);
	}
	
	public void doSortByName() {
		array = FileDoSort.doSortByName(array);
		refreshIFileList(array);
		updateMusicList();
	}

	public void doSortByTime() {
		array = FileDoSort.doSortByTime(array);
		refreshIFileList(array);
		updateMusicList();
	}
	
	public void refreshIFileList(List<ComplexListItem> array){
		iFileList.clear();
		for(ComplexListItem item : array){
			try {
				iFileList.add(new LocalFileFactory().getFile((String)item.getData()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void updateMusicList(){
		if (mContentFragment.getMusicView() != null && 
				new ExtractPath().process(tempPath).equals(mContentFragment.getMusicView().getSongsPath()))
			mContentFragment.updateMusicList();
	}

	public void reloadList() {
		setListAdapter(new ImageListAdapter(getActivity(), array));
	}
	
	public List<IFile> getIFileList(){
		return iFileList;
	}
}