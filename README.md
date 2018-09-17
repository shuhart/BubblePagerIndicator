# BubblePagerIndicator
A view pager indicator view to deal with a large amount of pages. Nice scale and transition animations are supported.

<img src="/images/sample.gif" alt="Sample" width="300px" />

Usage
-----

1. Add jcenter() to repositories block in your gradle file.
2. Add `implementation 'com.shuhart.bubblepagerindicator:bubblepagerindicator:1.1.0'` to your dependencies.
3. Add `BubblePageIndicator` into your layouts or view hierarchy:

```xml
<com.shuhart.bubblepagerindicator.BubblePageIndicator
    android:id="@+id/indicator"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_below="@id/pager"
    android:layout_centerHorizontal="true"
    android:layout_marginBottom="32dp"
    android:layout_marginTop="32dp"
    app:bpi_fillColor="@color/colorAccent"
    app:bpi_pageColor="@color/colorPrimary"
    app:bpi_radius="8dp"
    app:bpi_marginBetweenCircles="6dp"
    app:bpi_onSurfaceCount="@integer/default_bubble_indicator_on_surface_count"
    app:bpi_risingCount="@integer/default_bubble_indicator_rising_count"/>

```

4. Attach indicator to the ViewPager instance:

```java
pager = findViewById(R.id.pager);
indicator = findViewById(R.id.indicator);
adapter = new ViewPagerAdapter();
pager.setAdapter(adapter);
indicator.setViewPager(pager);
```
An adapter should be attached to the ViewPager before calling indicator.setViewPager(pager).

5. You can customize the behavior in runtime:
```java
indicator.setOnSurfaceCount(3);
indicator.setRisingCount(2);
// resolved color
indicator.setFillColor(ContextCompat.getColor(this, R.color.colorAccent));
// resolved color
indicator.setPageColor(ContextCompat.getColor(this, R.color.colorPrimary));
// in px
indicator.setRadius(getResources().getDimensionPixelSize(R.dimen.default_bubble_indicator_radius));
// in px
indicator.setMarginBetweenCircles(getResources().getDimensionPixelSize(
            R.dimen.default_bubble_indicator_circles_margin));
```

## Customization
| Attribute| Description | Default Value |
|-----------------------|-----------------------|--------|
| bpi_pageColor | Current page circle color. | #FFFFFF |
| bpi_fillColor | Circles color | #000000 |
| bpi_radius | Normal circle radius. | 3dp |
| bpi_marginBetweenCircles | Margin between centers of circles. | 3dp |
| bpi_onSurfaceCount | A number of circles with full radius (bpi_radius) | 5 |
| bpi_risingCount | A number of scaled circles. | 2 |

Maximum number of circles visible to the user is *bpi_onSurfaceCount* + *bpi_risingCount* * 2 (Default is 9).  
At the beginning only *bpi_onSurfaceCount* + *bpi_risingCount* (Default is 7) circles are visible.  
Radius for a rising circle is scaled by power of 2 with some correction applied.  
In every aspect the library is mimicking the Instagram indicator behavior.

## Special thanks
@tree1891 for his help in fixing many annoying issues.

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
