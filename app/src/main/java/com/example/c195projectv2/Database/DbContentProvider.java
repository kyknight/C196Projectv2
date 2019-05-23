package com.example.c195projectv2.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 *
 */
public abstract class DbContentProvider {
    private SQLiteDatabase mDb;

    /**
     * This method constructs the provider
     * @param db
     */
    public DbContentProvider(SQLiteDatabase db) {
        this.mDb = db;
    }

    /**
     * This method converts a SQLite Cursor to instance of the appropriate model class
     * @param cursor
     * @param <T>
     * @return
     */
    protected abstract <T> T cursorToEntity(Cursor cursor);

    /**
     * This method deletes a DB entry
     * @param tableName
     * @param selection
     * @param selectionArgs
     * @return
     */
    protected int delete(String tableName, String selection,
                         String[] selectionArgs) {
        return mDb.delete(tableName, selection, selectionArgs);
    }

    /**
     * The method inserts a DB entry
     * @param tableName
     * @param values
     * @return
     */
    protected long insert(String tableName, ContentValues values) {
        return mDb.insert(tableName, null, values);
    }

    /**
     * This method selects entry(ies) from the DB
     * @param tableName
     * @param columns
     * @param selection
     * @param selectionArgs
     * @param sortOrder
     * @return
     */
    protected Cursor query(String tableName, String[] columns,
                           String selection, String[] selectionArgs,
                           String sortOrder) {
        return mDb.query(tableName, columns,
                selection, selectionArgs, null, null, sortOrder);
    }

    /**
     * This method updates a DB entry
     * @param tableName
     * @param values
     * @param selection
     * @param selectionArgs
     * @return
     */
    protected int update(String tableName, ContentValues values,
                         String selection, String[] selectionArgs) {
        return mDb.update(tableName, values, selection, selectionArgs);
    }
}
