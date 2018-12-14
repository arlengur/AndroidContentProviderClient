package ru.arlen.androidcontentproviderclient;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    public static final String TITLE = "TITLE";
    public static final String NOTE_ID = "id";
    public static final String NOTE_TITLE = "title";
    public static final String NOTE_CONTENT = "content";
    public static final String ROWS_UPDATED_EXT = "ROWS_UPDATED";
    public static final String ROWS_DELETED_EXT = "ROWS_DELETED";
    public static final String URI_EXT = "URI";
    public static final Uri NOTE_URI = Uri.parse("content://ru.arlen.note/notes");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String uri = getIntent().getStringExtra(URI_EXT);
        if (uri != null) {
            Toast.makeText(this, uri, Toast.LENGTH_SHORT).show();
        }
        int rowsUpdated = getIntent().getIntExtra(ROWS_UPDATED_EXT, -1);
        if (rowsUpdated != -1) {
            Toast.makeText(this, String.valueOf(rowsUpdated), Toast.LENGTH_SHORT).show();
        }
        int rowsDeleted = getIntent().getIntExtra(ROWS_DELETED_EXT, -1);
        if (rowsDeleted != -1) {
            Toast.makeText(this, String.valueOf(rowsDeleted), Toast.LENGTH_SHORT).show();
        }

        Cursor cursor = getContentResolver().query(NOTE_URI, null, null, null, null);
        final ListView titlesList = findViewById(R.id.titles);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, getTitles(cursor));
        titlesList.setAdapter(adapter);
        titlesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, EditNoteActivity.class);
                intent.putExtra(TITLE, (String) titlesList.getItemAtPosition(position));
                startActivity(intent);
            }
        });

        View add = findViewById(R.id.addBtn);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddNoteActivity.class));
            }
        });
    }

    private static List<String> getTitles(Cursor cursor) {
        List<String> notes = new ArrayList<>();
        while (cursor.moveToNext()){
            String title = cursor.getString(cursor.getColumnIndex(NOTE_TITLE));
            notes.add(title);
        }
        cursor.close();
        return notes;
    }
}
