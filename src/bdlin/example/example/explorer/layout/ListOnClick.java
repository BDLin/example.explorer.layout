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

import java.util.List;

import nkfust.selab.android.explorer.layout.model.ContentFragment;
import poisondog.android.view.list.ComplexListItem;
import poisondog.vfs.FileType;
import poisondog.vfs.IFile;
import poisondog.vfs.LocalFolder;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class ListOnClick implements OnItemClickListener {

	private static ContentFragment content;
	private List<ComplexListItem> array;
	private View prevView;
	private SdcardListFragment fileData;
	private FragmentActivity activity;

	public ListOnClick(ContentFragment article, FragmentActivity activity,
			List<ComplexListItem> array, SdcardListFragment fileData) {
		this.fileData = fileData;
		this.activity = activity;
		content = article;
		this.array = array;
	}// End of ListOnClick construct
	
	public static void initContent(){
		content = null;
	}
	
	public static ContentFragment getContent(){
		return content;
	}

	public void setFocuseView (View view){
		if (prevView != null && prevView != view) {
			prevView.setBackgroundColor(0);
			view.setBackgroundColor(Color.DKGRAY);
			prevView = view;
		} else if (prevView == null) {
			view.setBackgroundColor(Color.DKGRAY);
			prevView = view;
		}// End of if else-is condition
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

		try {
			if (((IFile) array.get(position).getData()).getType() == FileType.DATA) {
				
				if(content == null){
					content = new ContentFragment();
					content.setIFile((IFile)array.get(position).getData());
					Bundle args = new Bundle();
					args.putInt("position", position);
					content.setArguments(args);
					activity.getSupportFragmentManager()
						    .beginTransaction()
						    .add(R.id.fragment_container,content)
						    .addToBackStack(null)
						    .commit();
				}else{
					content.setPosition(position);
					content.updateArticleView((IFile) array.get(position).getData());
				}// End of inner if-else condition
					
				setFocuseView(view);
			} else {
				LocalFolder folder = (LocalFolder) array.get(position).getData();
				fileData.setAdapter(folder.getUrl());
			}// End of if-else condition
		} catch (Exception e) {
			e.printStackTrace();
		}// End of try-catch
	}// End of onItemClick Function
}// End of ListOnClick Class
