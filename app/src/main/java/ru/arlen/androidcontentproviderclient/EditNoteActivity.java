package ru.arlen.androidcontentproviderclient;

import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import static ru.arlen.androidcontentproviderclient.MainActivity.*;

public class EditNoteActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        String titleParam = getIntent().getStringExtra(TITLE);
        final EditText title = findViewById(R.id.title);
        final EditText content = findViewById(R.id.content);
        final long nodeId;
        final Cursor cursor = getContentResolver().query(NOTE_URI, null, "title=?", new String[]{titleParam}, null);
        if (cursor.moveToNext()) {
            title.setText(cursor.getString(cursor.getColumnIndex(NOTE_TITLE)));
            content.setText(cursor.getString(cursor.getColumnIndex(NOTE_CONTENT)));
            nodeId = cursor.getLong(cursor.getColumnIndex(NOTE_ID));
            cursor.close();
        } else {
            nodeId = -1;
        }

        View update = findViewById(R.id.updateBtn);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues cv = new ContentValues();
                cv.put("title", title.getText().toString());
                cv.put("content", content.getText().toString());
                Uri uri = ContentUris.withAppendedId(NOTE_URI, nodeId);
                int rowsUpdated = getContentResolver().update(uri, cv, null, null);

                Intent intent = new Intent(EditNoteActivity.this, MainActivity.class);
                intent.putExtra(ROWS_UPDATED_EXT, rowsUpdated);
                startActivity(intent);
            }
        });

        View delete = findViewById(R.id.deleteBtn);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = ContentUris.withAppendedId(NOTE_URI, nodeId);
                int rowsDeleted = getContentResolver().delete(uri, null, null);

                Intent intent = new Intent(EditNoteActivity.this, MainActivity.class);
                intent.putExtra(ROWS_DELETED_EXT, rowsDeleted);
                startActivity(intent);
            }
        });

        View cancel = findViewById(R.id.cancelBtn);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditNoteActivity.this, MainActivity.class));
            }
        });
    }
}
