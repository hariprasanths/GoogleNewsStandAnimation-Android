# Google-NewsStand-Animation-Android

Navigation pattern like in Google News Stand app with transitions

![Screenshots](https://media.giphy.com/media/wa9LB3wev3QYkNUh7N/giphy.gif)

# Getting Started
<h4>In your build.gradle</h4>

```groovy
dependencies {
    compile 'hari.allagi:allagi:0.1.0'
}
```
<h4>Usage</h4>

Choose one of the **NoActionBar** themes to use in `MenuListActivity` and override it to define your app color palette.

```xml
<style name="AppTheme.MenuListActivity.NoActionBar" parent="Theme.AppCompat.NoActionBar">
    <item name="colorPrimary">@color/colorPrimary</item>
    <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
    <item name="colorAccent" >@color/colorAccent</item>
</style>
```

Define the colors for `ScrollableMenuActivity` too.

```xml
<style name="AppTheme.ScrollableMenuActivity.NoActionBar" parent="Theme.AppCompat.NoActionBar">
    <item name="colorPrimary">@color/colorPrimary</item>
    <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
    <item name="colorAccent">@color/colorAccent</item>
</style>
```

Declare the **Allagi** activities in `AndroidManifest.xml` file using your new app themes.

```xml
<activity
    android:name="hari.allagi.MenuListActivity"
    android:theme="@style/AppTheme.MenuListActivity.NoActionBar" />
<activity
    android:name="hari.allagi.ScrollableMenuActivity"
    android:theme="@style/AppTheme.ScrollableMenuActivity.NoActionBar" />
```

Set largeHeap to true in `AndroidManifest.xml`.

```xml
<application
  ...
  android:largeHeap="true"/>
```

Open **Allagi** from a activity like so:

```java
ArrayList<String> menuList = new ArrayList<>();     //menu titles
ArrayList<Integer> imagesList = new ArrayList<>();      //menu backgrounds
ArrayList<Fragment> fragmentsList = new ArrayList<>();      //fragments for each menu headers in second activity

menuList.add("UPCOMING");       //add titles
menuList.add("EVENTS");         //limit to 8 items for the animation to work
...

imagesList.add(R.drawable.upcoming);        //add background images
imagesList.add(R.drawable.events);
...

fragmentsList.add(UpcomingFragment.newInstance());      //add fragment instances
fragmentsList.add(EventsFragment.newInstance());
...

Allagi allagi = Allagi.initialize(MainActivity.this, menuList, imagesList, fragmentsList);
allagi.start();         //start the menu list activity
```

Change the duration of the animation:

```java
allagi.setTransitionDuration(900);      //default value is 1000 milliseconds
```

## Libraries used in the project

* [Jake Wharton's ViewPagerIndicator](https://github.com/JakeWharton/ViewPagerIndicator) Paging indicator widget that highlights the selected page of the viewpager

## Credits
Inspired by and thanks to [Aurélien Salomon's Google Newsstand Navigation Pattern](https://dribbble.com/shots/2940231-Google-Newsstand-Navigation-Pattern)

## License

Copyright 2018 hariprasanths

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.