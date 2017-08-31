package org.sqlite.app.customsqlite.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import org.sqlite.database.sqlite.SQLiteDatabase;

import java.io.File;


public class TestContentProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    static final int TESTS = 1000;
    static final int TEST_WITH_ID = 1001;

    private TestDBHelper openHelper;

    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = TestContract.CONTENT_AUTHORITY;
        matcher.addURI(authority, TestContract.PATH_TEST, TESTS);
        matcher.addURI(authority, TestContract.PATH_TEST +"/#", TEST_WITH_ID);
        return matcher;
    }

    public TestContentProvider() {
    }

    @Override
    public boolean onCreate() {
        Log.d("TestContentProvider","onCreate");
        File f = getContext().getDatabasePath(TestDBHelper.DATABASE_NAME);
        // Make sure we have a path to the file
        f.getParentFile().mkdirs();
        Log.d("TestContentProvider", "File f.getPath =" + f.getPath());
        openHelper = new TestDBHelper(getContext(), f.getPath());
        openHelper.getReadableDatabase().loadExtension("libsqlitefunctions");
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor retCursor;
        //openHelper.getWritableDatabase().loadExtension("libsqlitefunctions");
        //SQLiteDatabase db = openHelper.getWritableDatabase();
        //db.loadExtension("libsqlitefunctions");
        Log.d("TestContentProvider","query");
        switch (sUriMatcher.match(uri)) {
            case TESTS: {
                retCursor = openHelper.getWritableDatabase().query(
                        TestContract.TestEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = openHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        switch (match) {
            case TESTS:
                rowsDeleted = db.delete(
                        TestContract.TestEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Because a null deletes all rows
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public String getType(Uri uri) {
        // Use the Uri Matcher to determine what kind of URI this is.
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case TESTS:
                return TestContract.TestEntry.CONTENT_TYPE;
            case TEST_WITH_ID:
                return TestContract.TestEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = openHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;
        switch (match) {
            case TESTS: {
                long _id = db.insert(TestContract.TestEntry.TABLE_NAME, null, values);
                if ( _id > 0 )
                    returnUri = TestContract.TestEntry.buildTestUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        final SQLiteDatabase db = openHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;
        switch (match) {
            case TESTS:
                rowsUpdated = db.update(TestContract.TestEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }
}
