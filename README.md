# SQLite Android 


## What?

The purpose of this project is to use the Android NDK to create a custom version of SQLite to be embedded within an application and run on any Android device with Android API 19 or later.
This library can be further customized by adding additional SQLite extensions functions.

References: https://www.sqlite.org/android/doc/trunk/www/index.wiki

## How to?
### How to build the native library
To build this project you will need to download and extract the [Android NDK](https://developer.android.com/tools/sdk/ndk/index.html "Title").

You will then need to modify your local.properties file and add a line that points to your Android NDK directory.

`ndk.dir=C\:\\Android\\AndroidStudio\\sdk\\ndk-bundle`

The gradle file is configured to ignore the C and C++ files. This is due to an issue with Android Studio not being able to build properly. 
        
     externalNativeBuild {
            ndkBuild {
                path 'src/main/jni/Android.mk'
            }
        }

### Java Interface

Most of the code is taken directly from this [repository](http://www.sqlite.org/android/tree?ci=api-level-15 "Title").
One of the main differences with this repository is that the code is broken up into two separate modules.

1. An app that can be used for testing the custom SQLite database.
2. The library project containing all relevant C, C++, and Java code for the custom SQLite database. This compiles into an aar that can be used for any application.

### SQLite

The initial code for SQLite was pulled from this [repository](http://www.sqlite.org/android/tree?ci=api-level-15 "Title") but the version of SQLite has since been update to 3.20.1.
As SQLite is update this can be updated in this repository easily by downloading the amalgamation source from [here](http://www.sqlite.org/download.html "Title"). 
Extract the source from the zip file and copy the updated files into the jni folder. That is all that needs to be done to update the version of SQLite.

### Extensions functions

This extension will provide common mathematical and string functions in SQL queries using the operating system libraries or provided definitions.

References: http://www.sqlite.org/contrib/download/extension-functions.c?get=25

It includes the following functions:

Math: acos, asin, atan, atn2, atan2, acosh, asinh, atanh, difference,
degrees, radians, cos, sin, tan, cot, cosh, sinh, tanh, coth, exp,
log, log10, power, sign, sqrt, square, ceil, floor, pi.

String: replicate, charindex, leftstr, rightstr, ltrim, rtrim, trim,
replace, reverse, proper, padl, padr, padc, strfilter.

Aggregate: stdev, variance, mode, median, lower_quartile,
upper_quartile.

The string functions ltrim, rtrim, trim, replace are included in
recent versions of SQLite and so by default do not build.

## How to use

Add the line below to the dependencies section in the ```build.gradle``` :
```
compile 'com.github.graviton57:sqlite-android:1.0.1'
```
Now you just need to change ```import``` part of your code to ```import org.sqlite.database.sqlite.SQLiteDatabase;``` and anything else will remain exactly how you use normal android sqlite classes.

Follow the steps below for how to use a extension in your project:

    1. Before performing any database operations you must call the following code:
        
        System.loadLibrary("sqliteX"); // loads the custom sqlite library
        System.loadLibrary("sqlitefunctions"); // loads the extension library
        
    2. After opening or creating a database run the following code:
    
        final Cursor load = db.rawQuery("SELECT load_extension(?)", new String[]{"sqlitefunctions"});
        if (load == null || !load.moveToFirst()) {
            throw new RuntimeException("Functions Extension load failed!");
        }
       
    3. Or use another method:
        SQLiteDatabase db = helper.getWritableDatabase();
               db.loadExtension("libsqlitefunctions");
               db.execSQL("SELECT median(field_name), lower_quartile(field_name) FROM YOUR_TABLE_NAME");
               db.execSQL("SELECT cos(radians(45))");
        


License
=======

    Copyright 2017 Igor Gavrilyuk.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.




   