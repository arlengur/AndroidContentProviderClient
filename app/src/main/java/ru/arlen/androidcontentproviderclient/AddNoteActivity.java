package ru.arlen.androidcontentproviderclient;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import static ru.arlen.androidcontentproviderclient.MainActivity.URI_EXT;

public class AddNoteActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        final Uri NOTE_URI = Uri.parse("content://ru.arlen.note/notes");

        final EditText title = findViewById(R.id.title);
        final EditText content = findViewById(R.id.content);
        View create = findViewById(R.id.createBtn);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!title.getText().toString().isEmpty() && !content.getText().toString().isEmpty()) {
                    ContentValues cv = new ContentValues();
                    cv.put("title", title.getText().toString());
                    cv.put("content", content.getText().toString());
                    Uri uri = getContentResolver().insert(NOTE_URI, cv);

                    Intent intent = new Intent(AddNoteActivity.this, MainActivity.class);
                    intent.putExtra(URI_EXT, uri.toString());

                    startActivity(intent);
                }
            }
        });

        View cancel = findViewById(R.id.cancelBtn);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddNoteActivity.this, MainActivity.class));
            }
        });
    }
}
