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

import poisondog.vfs.IFile;
import poisondog.vfs.LocalFileFactory;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;

public class DialogListener implements DialogInterface.OnClickListener {

	private EditText editText;
	private Context context;
	private SdcardListFragment sdFrag;

	public DialogListener(Context context, SdcardListFragment sdFrag,
			EditText editText) {
		this.sdFrag = sdFrag;
		this.context = context;
		this.editText = editText;
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		IFile file = null;
		try {
			file = new LocalFileFactory().getFile(sdFrag.getCurrentPath() + "/"
					+ editText.getText() + "/");
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
		try {
			if (!file.isExists()) {
				file.create();
				try {
					sdFrag.setAdapter(sdFrag.getCurrentPath());
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				Builder dialogError = new AlertDialog.Builder(context);
				dialogError.setTitle("Error!!")
						.setMessage("The folder name is repeat!!")
						.setNegativeButton("OK", null).show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}// End of onClick
}
