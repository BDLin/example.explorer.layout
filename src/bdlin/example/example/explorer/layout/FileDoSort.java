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

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import poisondog.android.view.list.ComplexListItem;
import poisondog.vfs.LocalData;

public class FileDoSort {

	public static List<ComplexListItem> doSortByName(List<ComplexListItem> array) {
		Collections.sort(array, new Comparator<ComplexListItem>() {
			@Override
			public int compare(ComplexListItem lhs, ComplexListItem rhs) {
				return lhs.getTitle().compareTo(rhs.getTitle());
			}
		});
		return array;
	}

	public static List<ComplexListItem> doSortByTime(List<ComplexListItem> array) {
		Collections.sort(array, new Comparator<ComplexListItem>() {
			@Override
			public int compare(ComplexListItem lhs, ComplexListItem rhs) {
				return ((Long) ((LocalData) rhs.getData())
						.getLastModifiedTime())
						.compareTo((((Long) ((LocalData) lhs.getData())
								.getLastModifiedTime())));
			}
		});
		return array;
	}
}
