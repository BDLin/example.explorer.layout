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
package bdlin.example.example.explorer.layout.processor;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import poisondog.vfs.IFile;
import poisondog.vfs.LocalFileFactory;
import poisondog.vfs.LocalFolder;

public class SdcardFileData {

	private String mFilePath;
	private LocalFolder mFile;
	private List<IFile> mFiles;
	private LocalFileFactory mFactory;

	public SdcardFileData(String filePath) throws IOException,
			URISyntaxException {
		mFilePath = filePath + "/";
		mFactory = new LocalFileFactory();
		mFile = (LocalFolder) mFactory.getFile(mFilePath);
		mFiles = mFile.getChildren();
	}// End of SdcardFileData construct

	public List<IFile> getFileList() {
		return mFiles;
	}// End of getFileList function
}// End of SdcardFileData class
