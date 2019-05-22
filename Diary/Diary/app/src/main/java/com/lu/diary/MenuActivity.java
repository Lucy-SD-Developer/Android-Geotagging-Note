package com.lu.diary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.databinding.DataBindingUtil;
import android.view.View;

import com.lu.diary.databinding.ActivityMenuBinding;

public class MenuActivity extends Activity {

    private ActivityMenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_menu);

        binding.btnNewEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), NewDiaryActivity.class);
                startActivity(intent);
            }
        });

        binding.btnViewList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DiaryListActivity.class);
                startActivity(intent);
            }
        });
    }
}
