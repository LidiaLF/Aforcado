package com.example.afogado.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.example.afogado.model.Word;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {;
    private static final String DB_NAME = "aforcado.db";
    private static String DB_PATH;
    private static final int DB_VERSION = 1;
    private static SQLiteDatabase m_database;
    private final Context m_context;
    private static DataBaseHelper mInstance;

    public static synchronized DataBaseHelper getInstance(final Context context) {
        if (mInstance == null) {
            mInstance = new DataBaseHelper(context);
        }
        return mInstance;
    }

    /**
     * Constructor Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     *
     * @param context
     */
    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.m_context = context;
    }

    /**
     * Creates an empty database on the system and rewrites it with your own database.
     */
    public void createDataBase() throws IOException {
        DB_PATH = m_context.getDatabasePath(DB_NAME).getAbsolutePath();
        boolean dbExist = checkDataBase();
        if (dbExist) {
            Log.e("DB", "Database exists");
            m_context.deleteDatabase(DB_NAME);
            try {
                copydatabase();
            } catch (IOException e) {
                Log.e("DB", e.getMessage());
                throw new Error("Error copying database");
            }
        }

        if (!dbExist) {
            try {
                copydatabase();
            } catch (IOException e) {
                Log.e("DB", e.getMessage());
                throw new Error("Error copying database");
            }
        }
    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     *
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase() {
        File dbFile = m_context.getDatabasePath(DB_NAME);
        return dbFile.exists();
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database
     * in the system folder, from where it can be accessed and handled.
     * This is done by transferring byte stream.
     */
    private void copydatabase() throws IOException {
        //Open your local db as the input stream
        InputStream myinput = m_context.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outfilename = DB_PATH;

        //Open the empty db as the output stream
        OutputStream myoutput = new FileOutputStream(outfilename);

        // transfer byte to inputfile to outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myinput.read(buffer)) > 0) {
            myoutput.write(buffer, 0, length);
        }

        //Close the streams
        myoutput.flush();
        myoutput.close();
        myinput.close();
        checkDataBase();
    }


    public  ArrayList<Word> loadAllWords(){
        SQLiteDatabase db= getReadableDatabase();
        ArrayList<Word> listWords = new ArrayList<>();
        try{
            Cursor datosConsulta = db.rawQuery("SELECT * FROM aforcado", null);
            if (datosConsulta.moveToFirst()) {
                Word word;
                while (!datosConsulta.isAfterLast()) {
                    word = new Word(datosConsulta.getString(0),
                            datosConsulta.getString(1));
                    listWords.add(word);
                    datosConsulta.moveToNext();
                }
            }}catch (NullPointerException e){
            Log.e("ERROR BD", e.getMessage());
        }
        return listWords;
    }

    @Override
    public synchronized void close() {
        if (m_database != null) m_database.close();
        super.close();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        m_database = db;

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }



    @Override
    protected void finalize() throws Throwable {
        this.close();
        super.finalize();
    }


}

