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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import nkfust.selab.android.explorer.layout.model.ContentFragment;
import poisondog.android.view.list.ComplexListItem;
import poisondog.vfs.FileType;
import poisondog.vfs.IFile;
import poisondog.vfs.LocalData;
import poisondog.vfs.LocalFolder;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.epapyrus.plugpdf.SimpleDocumentReader;
import com.epapyrus.plugpdf.SimpleDocumentReaderListener;
import com.epapyrus.plugpdf.SimpleReaderFactory;
import com.epapyrus.plugpdf.core.PlugPDF;
import com.epapyrus.plugpdf.core.PlugPDFException.InvalidLicense;
import com.epapyrus.plugpdf.core.viewer.DocumentState;

public class ListOnClick implements OnItemClickListener {

	private ContentFragment article;
	private Context context;
	private List<ComplexListItem> array;
	private View prevView;
	private SdcardListFragment fileData;

	public ListOnClick(ContentFragment article, Context context,
			List<ComplexListItem> array, SdcardListFragment fileData) {
		this.fileData = fileData;
		this.article = article;
		this.context = context;
		this.array = array;
	}// End of ListOnClick construct

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		try {
			if (((IFile) array.get(position).getData()).getType() == FileType.DATA) {
				
				
//				TextView text = new TextView(context);
//				LocalData data = (LocalData) array.get(position).getData();
//				text.setText(readFromSDcard(data));
//				text.setTextSize(25);
//				article.updateArticleView(text);
				InputStream is = ((LocalData) array.get(position).getData()).getInputStream();
				int size = is.available();
				Log.i("ListOnClick", "File Size:" + size);
                if (size > 0) {
                	byte[] data = new byte[size];
                	is.read(data);
                	open(data);
                }
                is.close();
                
				if (prevView != null && prevView != view) {
					prevView.setBackgroundColor(0);
					view.setBackgroundColor(Color.DKGRAY);
					prevView = view;
				} else if (prevView == null) {
					view.setBackgroundColor(Color.DKGRAY);
					prevView = view;
				}// End of if else-is condition
			} else {
				LocalFolder folder = (LocalFolder) array.get(position)
						.getData();
				fileData.setAdapter(folder.getUrl());
			}// End of if-else condition
		} catch (Exception e) {
			e.printStackTrace();
		}// End of try-catch
	}// End of onItemClick Function
	
	public void open(byte[] data) {
		// pdfviewer create.
		SimpleDocumentReader viewer = SimpleReaderFactory.createSimpleViewer(
				article.getActivity(), m_listener);
		article.updateArticleView(viewer.getReaderView());
		// pdf data load.
		viewer.openData(data, data.length, "");
 
	}
 
	// create a listener for receiving provide pdf loading results
	SimpleDocumentReaderListener m_listener = new SimpleDocumentReaderListener() {
 
		@Override
		public void onLoadFinish(DocumentState.OPEN state) {
		}
	};

	private String readFromSDcard(LocalData file) {
		StringBuilder sb = new StringBuilder();
		try {
			InputStream fin = file.getInputStream();
			byte[] data = new byte[fin.available()];
			while (fin.read(data) != -1) {
				sb.append(new String(data));
			}// End of while loop
			fin.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}// End try-catch
		return sb.toString();
	}// End of readFromSDcard function
}// End of ListOnClick Class
