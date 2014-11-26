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

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.widget.EditText;

public class CreateFolder {

	private EditText editText;
	private Context context;
	private DialogListener listener;

	public CreateFolder(Context context, SdcardListFragment sdFrag) {
		this.context = context;
		editText = new EditText(context);
		editText.setHint("NewFolder");
		listener = new DialogListener(context, sdFrag, editText);
	}

	public void DisplayDialog() {
		Builder dialog = new AlertDialog.Builder(context);
		dialog.setTitle("Create Folder")
				.setMessage("Please input the folder name.").setView(editText)
				.setNegativeButton("Cancel", null)
				.setPositiveButton("Done", listener).show();
	}// End of DisplayDialog function
}// End of CreateFolder class
