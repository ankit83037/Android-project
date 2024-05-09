package com.example.note_takingapplication;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import androidx.annotation.Nullable;

import java.util.HashMap;

public class MyContentProvider extends ContentProvider {
    public MyContentProvider() {
    }

    static  final String PROVIDER_NAME ="com.demo.note";
    static  final String URI  ="content://"+ PROVIDER_NAME + "/user";

    static  final Uri CONTENT_URI = Uri.parse(URI);
    static  final String id_Col = "id";
    static  final String title_Col = "title";
    static final String description_Col = "description";
    static  final int UriCode1 = 1;
    static  final int UriCode2 = 1;
    static  final UriMatcher uriMatcher;
    private static HashMap<String,String> values;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME,"user",UriCode1);
        uriMatcher.addURI(PROVIDER_NAME,"user/*",UriCode2);
    }



    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
//        throw new UnsupportedOperationException("Not yet implemented");

        int count = 0;
        switch (uriMatcher.match(uri)){
            case UriCode1:
                count = db.delete(TABLE_NAME,selection,selectionArgs);
                System.out.println("delete "+count);
                break;

            default:
                throw new IllegalArgumentException("unknown uri "+ uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return count;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.

        switch (uriMatcher.match(uri)){
            case UriCode1:
                return"vnd.android.cursor.dir/user";

            default:
                throw new IllegalArgumentException("unsupported uri "+uri);
        }
//        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
//        throw new UnsupportedOperationException("Not yet implemented");

        long rowID = db.insert(TABLE_NAME,"",values);
        if(rowID>0){
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI,rowID);
            getContext().getContentResolver().notifyChange(_uri,null);
            return _uri;
        }
        throw new SQLiteException("Failed to add a record into "+ uri);
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.

        Context context = getContext();
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        if (db!=null){
            return true;
        }
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
//        throw new UnsupportedOperationException("Not yet implemented");

        SQLiteQueryBuilder sqLiteQueryBuilder = new SQLiteQueryBuilder();
        sqLiteQueryBuilder.setTables(TABLE_NAME);

        switch (uriMatcher.match(uri)){
            case UriCode1:
                sqLiteQueryBuilder.setProjectionMap(values);
                break;
            default:
                throw new IllegalArgumentException("unknown uri "+ uri);
        }

        if(sortOrder == null || sortOrder == ""){
             sortOrder = id_Col;
        }

        Cursor cursor = sqLiteQueryBuilder.query(db,projection,selection,selectionArgs,null,null,sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(),uri);

        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
//        throw new UnsupportedOperationException("Not yet implemented");

        int count = 0;
        switch (uriMatcher.match(uri)){
            case UriCode1:
                count = db.update(TABLE_NAME,values,selection,selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("unknown uri"+ uri);
        }

        getContext().getContentResolver().notifyChange(uri,null);
        return count;
    }

    private SQLiteDatabase db;
    static final String DATABASE_NAME = "UserDB";
    static final String TABLE_NAME = "user";
    static final int DATABASE_VERSION =1;

    static final String CREATE_TABLE ="create table "+ TABLE_NAME + "(id INTEGER PRIMARY KEY AUTOINCREMENT,"
            +" title TEXT NOT NULL,"
            +" description TEXT NOT NULL);";



    private static class DatabaseHelper extends SQLiteOpenHelper{

        public DatabaseHelper(@Nullable Context context) {
            super(context, DATABASE_NAME,null,DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
            onCreate(db);
        }
    }

}