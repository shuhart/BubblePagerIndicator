# BubblePagerIndicator
A view pager indicator view to deal with a large amount of pages. Nice scale and transition animations are supported.

<img src="/images/sample.gif" alt="Sample" width="300px" />

Usage
-----

1. Add jcenter() to repositories block in your gradle file.
2. Add `implementation 'com.shuhart.bubblepagerindicator:bubblepagerindicator:1.0.9'` to your dependencies.
2. Add `BubblePageIndicator` into your layouts or view hierarchy.
3. Look into the sample for additional details on how to use and configure the library.

Example:

```xml
<com.shuhart.bubblepagerindicator.BubblePageIndicator
	android:id="@+id/indicator"
	android:layout_width="wrap_content"
	android:layout_height="wrap_content"
	android:layout_alignParentBottom="true"
	android:layout_centerInParent="true"
	android:layout_marginBottom="64dp"
	app:fillColor="@color/colorAccent"
	app:pageColor="@color/colorPrimary"
	app:radius="3dp" />

```
License
=======

    Copyright 2017 Bogdan Kornev.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
