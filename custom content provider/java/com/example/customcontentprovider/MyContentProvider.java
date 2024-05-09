package com.example.customcontentprovider;

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

import java.util.HashMap;

public class MyContentProvider extends ContentProvider {
    public MyContentProvider() {
    }

    static final String PROVIDER_NAME = "com.demo.user.provider";
    static final String URI = "content://"+ PROVIDER_NAME + "/users";
    static final Uri CONTENT_URI = Uri.parse(URI);
    static final String id = "id";
    static final String name = "name";
    static final int uriCode = 1;
    static final UriMatcher uriMatcher;
    private static HashMap<String,String> values;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME,"users",uriCode);
        uriMatcher.addURI(PROVIDER_NAME,"users/*",uriCode);
    }



    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        switch (uriMatcher.match(uri)){
            case uriCode:
                return "vnd.android.cursor.dir/users";

            default:
                throw new IllegalArgumentException("unsupported URI: "+ uri);
        }
//        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        Context context = getContext();
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        if(db!=null){
            return true;
        }
        return false;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.

        long rowID = db.insert(TABLE_NAME," ",values);
        if(rowID>0){
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI,rowID);
            getContext().getContentResolver().notifyChange(_uri,null);
            return _uri;
        }
        throw new SQLiteException("Failed to add a record into "+ uri);
//        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.

        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(TABLE_NAME);
        switch (uriMatcher.match(uri)){
            case uriCode:
                qb.setProjectionMap(values);
                break;
            default:
                throw new IllegalArgumentException("unknown uri "+ uri);
        }
        if(sortOrder == null || sortOrder == ""){
            sortOrder = id;
        }
        Cursor c = qb.query(db,projection,selection,selectionArgs,null,null,sortOrder);
        c.setNotificationUri(getContext().getContentResolver(), uri);

        return c;
//        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.

        int count =0;
        switch (uriMatcher.match(uri)){
            case uriCode:
                count = db.update(TABLE_NAME,values,selection,selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI "+ uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return count;
//        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
//        throw new UnsupportedOperationException("Not yet implemented");

        int count =0;
        switch (uriMatcher.match(uri)){
            case uriCode:
                count = db.delete(TABLE_NAME,selection,selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI "+ uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return count;
    }

    private SQLiteDatabase db;
    static final String DATABASE_NAME = "UserDB";

    static final String TABLE_NAME = "Users";
    static final int DATABASE_VERSION = 1;

    static final String CREATE_DB_TABLE = "CREATE TABLE "+ TABLE_NAME + "(id INTEGER PRIMARY KEY AUTOINCREMENT,"
            +" name TEXT NOT NULL);";

    private static class DatabaseHelper extends SQLiteOpenHelper{
        DatabaseHelper(Context context){
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_DB_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }
}