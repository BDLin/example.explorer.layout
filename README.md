example.explorer.layout
=======================
此 Example 可相容 *Phone* 和 *Tablet* ，個別有呈現的方式，如下圖：

**Phone**

![](https://github.com/BDLin/example.explorer.layout/raw/master/phone_demo1.png "Phone Demo1") ![](https://github.com/BDLin/example.explorer.layout/raw/master/phone_demo2.png "Phone Demo2")

**Tablet**

![](https://github.com/BDLin/example.explorer.layout/raw/master/tablet_demo1.png "tablet Demo1")
![](https://github.com/BDLin/example.explorer.layout/raw/master/tablet_demo2.png "tablet Demo1")

Usage
-----
如果想呈現這種效果，必須新增兩個layout如下：

1.`res\layout\news_articles.xml：`
```
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/fragment_container"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```

2.`res\layout-large\new_articles.xml：`
```
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/tablet_linear_layout"
    android:orientation="horizontal"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
    
    <fragment android:name="nkfust.selab.android.explorer.layout.model.TabFragment"
              android:id="@+id/headlines_fragment"
              android:layout_weight="1"
        	  android:layout_width="0dp"
        	  android:layout_height="match_parent"
              />

    <fragment android:name="nkfust.selab.android.explorer.layout.model.ContentFragment"
              android:id="@+id/article_fragment"
              android:layout_weight="2"
              android:layout_width="0dp"
              android:layout_height="match_parent">        
    </fragment>
</LinearLayout>
```

**Note：**這兩個xml檔的檔名必須要相同，由於APP執行時它會針對目前實機的尺寸大小去抓相對應的layout，如是 *Tablet*      則系統會去 `layout-large` 取xml檔，換言之 *Phone* 就會去 `layout` 取xml檔。

**想了解如何判斷兩種尺寸，且個別呈現方式，請下載此專案參考裡面的程式碼。**

Library
-------
**[android.explorer_layout](https://github.com/BDLin/android.explorer_layout)**

Reference
---------
[Building a Flexible UI](http://developer.android.com/training/basics/fragments/fragment-ui.html)
