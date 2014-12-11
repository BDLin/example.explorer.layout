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

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLDecoder;

import poisondog.android.view.list.ComplexListItem;
import poisondog.format.TimeFormatUtils;
import poisondog.string.ExtractFileName;
import poisondog.vfs.FileType;
import poisondog.vfs.IFile;
import poisondog.vfs.LocalFolder;
import android.widget.ImageView;
import android.widget.TextView;

public class SdcardFileTransform implements ComplexListItem {

	private IFile file;

	public SdcardFileTransform(IFile file) {
		this.file = file;
	}

	@Override
	public String getTitle() {
		try {
			return new ExtractFileName().process(URLDecoder.decode(file.getUrl()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public IFile getFile() {
		return this.file;
	}

	public Long getTime() {
		try {
			return file.getLastModifiedTime();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Long(0);
	}

	@Override
	public String getHideMessage() {
		return null;
	}

	@Override
	public IFile getData() {
		return this.file;
	}

	@Override
	public void setData(Object object) {

	}

	@Override
	public void setSubTitle(TextView view) {
		try {
			view.setText(TimeFormatUtils.toString(TimeFormatUtils.SIMPLE,
					file.getLastModifiedTime()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setComment(TextView view) {

	}

	@Override
	public void setImage(ImageView view) {
		try {
			if (file.getType() == FileType.DATA)
				view.setImageResource(R.drawable.file_blue_48);
			else {
				LocalFolder folder = (LocalFolder) file;
				if (folder.getChildren().size() == 0)
					view.setImageResource(R.drawable.folder_empty);
				else
					view.setImageResource(R.drawable.folder_documents);
			}// End of if-else condition
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}// End of try-catch
	}// End of setImage function

	@Override
	public void setState(ImageView view) {

	}
}
