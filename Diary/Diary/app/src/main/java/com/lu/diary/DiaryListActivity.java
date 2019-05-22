package com.lu.diary;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import android.databinding.DataBindingUtil;
import com.lu.diary.databinding.ActivityDiaryListBinding;

public class DiaryListActivity extends Activity {
    private DBManager myDBManager;
    private ActivityDiaryListBinding binding;
    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_ENTRY = "entry";

    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_diary_list);

        myDBManager = new DBManager(this);
        myDBManager.open();

        setListItemsEvent();
    }

    public void onStop() {
        super.onStop();
        myDBManager.close();
    }

    private void setListItemsEvent() {
        List<Diary> theDiaryList = new LinkedList<Diary>();
        Cursor cur = myDBManager.getAllDiaries();

        if (cur.getCount() != 0) {
            while(cur.moveToNext()) {
                long id = cur.getLong(cur.getColumnIndex(DBManager.DIARY_KEY_ROWID));
                long date = cur.getLong(cur.getColumnIndex(DBManager.DIARY_KEY_DATE_DIARY));
                String title = cur.getString(cur.getColumnIndex(DBManager.DIARY_KEY_TITLE_DIARY));
                String content = cur.getString(cur.getColumnIndex(DBManager.DIARY_KEY_CONTENT_DIARY));
                Diary theNewDiary = new Diary(id, date, title, content);
                theDiaryList.add(theNewDiary);
            }
        }

        ArrayAdapter<Diary> theArrayAdapter = new ArrayAdapter<Diary>(this, android.R.layout.simple_list_item_1, theDiaryList);

        binding.lviewDiaries.setAdapter(theArrayAdapter);

        AdapterView.OnItemClickListener theItemL = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View parentView, int position, long id) {
                Diary selectedDiary = (Diary)(binding.lviewDiaries.getItemAtPosition(position));

                // Intent to create a DiaryActivity
                Intent intent = new Intent(parentView.getContext(), DiaryActivity.class);
                intent.putExtra(EXTRA_TITLE, selectedDiary.getTitle());
                intent.putExtra(EXTRA_ENTRY, selectedDiary.getContent());
                startActivity(intent);
            }
        };

        binding.lviewDiaries.setOnItemClickListener(theItemL);

    }
}




