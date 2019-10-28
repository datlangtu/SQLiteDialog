package com.example.student.thtuan11bai1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "book_list", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table Author(id_author integer primary key, name text, " +
                "address text, email text)");
        sqLiteDatabase.execSQL("create table Book(id_book integer primary key, title text, " +
                "id_author integer constraint id_author references Author(id_author) " +
                "on delete cascade on update cascade)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists book");
        sqLiteDatabase.execSQL("drop table if exists author");
        onCreate(sqLiteDatabase);
    }

    public boolean insertAuthor(Author author) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("address", author.getAuthorAddress());
        contentValues.put("email", author.getAuthorEmail());
        contentValues.put("id_author", author.getAuthorID());
        contentValues.put("name", author.getAuthorName());
        db.insert("Author", null, contentValues);
        return true;
    }

    public Author getAuthor(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Author where id_author=" + id, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        Author author = new Author(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
        cursor.close();
        db.close();
        return author;
    }

    public ArrayList<Author> getAllAuthor() {
        ArrayList<Author> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Author", null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        while (!cursor.isAfterLast()) {
            list.add(new Author(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3)));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return list;
    }

    public boolean updateAuthor(Author author) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("address", author.getAuthorAddress());
        contentValues.put("email", author.getAuthorEmail());
        contentValues.put("id_author", author.getAuthorID());
        contentValues.put("name", author.getAuthorName());
        db.update("Author", contentValues, "id_author = ?",
                new String[]{Integer.toString(author.getAuthorID())});
        return true;
    }

    public boolean deleteAuthor(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("delete from Author where id_author=" + id, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        cursor.close();
        db.close();
        return true;
    }
}