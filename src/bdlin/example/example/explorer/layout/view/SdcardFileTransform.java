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

import java.net.URLDecoder;

import poisondog.android.view.list.ComplexListItem;
import poisondog.format.TimeFormatUtils;
import poisondog.string.ExtractFileName;
import poisondog.string.ExtractPath;
import poisondog.vfs.FileType;
import poisondog.vfs.IFile;
import poisondog.vfs.LocalFolder;
import android.widget.ImageView;
import android.widget.TextView;
import bdlin.example.example.explorer.layout.R;

public class SdcardFileTransform implements ComplexListItem {

	private IFile mFile;

	public SdcardFileTransform(IFile file) {
		mFile = file;
	}

	@Override
	public String getTitle() {
		try {
			return new ExtractFileName().process(URLDecoder.decode(mFile.getUrl()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public Long getTime() {
		try {
			return mFile.getLastModifiedTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Long(0);
	}

	@Override
	public String getHideMessage() {
		return null;
	}

	@Override
	public String getData() {
		try {
			return new ExtractPath().process(URLDecoder.decode(mFile.getUrl()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void setData(Object object) {}

	@Override
	public void setSubTitle(TextView view) {
		try {
			view.setText(TimeFormatUtils.toString(TimeFormatUtils.SIMPLE,
					mFile.getLastModifiedTime()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setComment(TextView view) {}

	@Override
	public void setImage(ImageView view) {
		try {
			if (mFile.getType() == FileType.DATA)
				view.setImageResource(R.drawable.file_blue_48);
			else {
				LocalFolder folder = (LocalFolder) mFile;
				if (folder.getChildren().size() == 0)
					view.setImageResource(R.drawable.folder_empty);
				else
					view.setImageResource(R.drawable.folder_documents);
			}// End of if-else condition
		} catch (Exception e) {
			e.printStackTrace();
		}// End of try-catch
	}// End of setImage function

	@Override
	public void setState(ImageView view) {}
}