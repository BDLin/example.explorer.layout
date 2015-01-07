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
import poisondog.vfs.FileType;
import poisondog.vfs.IFile;
import poisondog.vfs.LocalFolder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class ListOnClick implements OnItemClickListener {

	private static ContentFragment aContent;
	private SdcardListFragment fileData;
	private FragmentActivity activity;

	public ListOnClick(ContentFragment article, FragmentActivity activity,
			SdcardListFragment fileData) {
		this.fileData = fileData;
		this.activity = activity;
		aContent = article;
	}// End of ListOnClick construct
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		List<IFile> aIFileArray = new ArrayList<IFile>();
		aIFileArray.addAll(fileData.getIFileList());
		try {
			if (aIFileArray.get(position).getType() == FileType.DATA) {

				if (aContent == null) {
					aContent = new ContentFragment();
					aContent.setIFile(aIFileArray.get(position));
					aContent.setIFileList(aIFileArray);
					Bundle args = new Bundle();
					args.putInt("position", position);
					aContent.setArguments(args);
					activity.getSupportFragmentManager().beginTransaction()
							.add(R.id.fragment_container, aContent)
							.addToBackStack(null).commit();
				} else {
					aContent.setIFileList(aIFileArray);
					aContent.updateArticleView(aIFileArray.get(position));
				}// End of inner if-else condition

			} else {
				LocalFolder folder = (LocalFolder) aIFileArray.get(position);
				fileData.setAdapter(folder.getUrl());
			}// End of if-else condition
		} catch (Exception e) {
			e.printStackTrace();
		}// End of try-catch
	}// End of onItemClick Function
	
	public static void initContent() {
		aContent = null;
	}
	
	public static ContentFragment getContent() {
		return aContent;
	}
}// End of ListOnClick Class