package com.lu.diary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.databinding.DataBindingUtil;

import com.lu.diary.databinding.ActivityDiaryBinding;

public class DiaryActivity extends Activity {

    private ActivityDiaryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_diary);

        Intent intent = getIntent();
        String title = intent.getStringExtra(DiaryListActivity.EXTRA_TITLE);
        String entry = intent.getStringExtra(DiaryListActivity.EXTRA_ENTRY);
        binding.txtTitle.setText(title);
        binding.txtEntry.setText(entry);
    }
}
