package com.example.c195projectv2.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.c195projectv2.Database.Daos.AssessmentDAO;
import com.example.c195projectv2.Database.Daos.AssessmentSchema;
import com.example.c195projectv2.Database.Daos.CourseDAO;
import com.example.c195projectv2.Database.Daos.CourseSchema;
import com.example.c195projectv2.Database.Daos.MentorDAO;
import com.example.c195projectv2.Database.Daos.MentorSchema;
import com.example.c195projectv2.Database.Daos.NoteDAO;
import com.example.c195projectv2.Database.Daos.NoteSchema;
import com.example.c195projectv2.Database.Daos.TermDAO;
import com.example.c195projectv2.Database.Daos.TermSchema;

/**
 *
 */
public class Database {
    private static final String DATABASE_NAME = "studentapp.db";
    private static final int DATABASE_VERSION = 2;

    private DatabaseHelper mDbHelper;
    private final Context mContext;
    public static TermDAO termDAO;
    public static CourseDAO courseDAO;
    public static AssessmentDAO assessmentDAO;
    public static MentorDAO mentorDAO;
    public static NoteDAO noteDAO;

    /**
     * This method is the constructor
     * @param context
     */
    public Database(Context context) {
        this.mContext = context;
    }

    /**
     * This method opens the database and attaches yo DAOs to yo instance
     * @return
     */
    public Database open() {
        mDbHelper = new DatabaseHelper(mContext);
        SQLiteDatabase mDb = mDbHelper.getWritableDatabase();

        termDAO = new TermDAO(mDb);
        courseDAO = new CourseDAO(mDb);
        assessmentDAO = new AssessmentDAO(mDb);
        mentorDAO = new MentorDAO(mDb);
        noteDAO = new NoteDAO(mDb);
        return this;
    }


    /**
     * This method closes yo instance
     */
    public void close() {
        mDbHelper.close();
    }

    /**
     * This method is yo DB helper and provides basic migration functionality
     */
    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(TermSchema.TERMS_CREATE);
            db.execSQL(CourseSchema.COURSES_CREATE);
            db.execSQL(AssessmentSchema.ASSESSMENTS_CREATE);
            db.execSQL(MentorSchema.MENTORS_CREATE);
            db.execSQL(NoteSchema.NOTES_CREATE);
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int o, int n) {
            db.execSQL("DROP TABLE IF EXISTS " + TermSchema.TABLE_TERMS);
            db.execSQL("DROP TABLE IF EXISTS " + CourseSchema.TABLE_COURSES);
            db.execSQL("DROP TABLE IF EXISTS " + AssessmentSchema.TABLE_ASSESSMENTS);
            db.execSQL("DROP TABLE IF EXISTS " + MentorSchema.TABLE_MENTORS);
            db.execSQL("DROP TABLE IF EXISTS " + NoteSchema.TABLE_NOTES);

            onCreate(db);
        }
    }
}
