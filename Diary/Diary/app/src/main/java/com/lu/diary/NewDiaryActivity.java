package com.lu.diary;

import android.app.Activity;
import android.os.Bundle;

import com.lu.diary.databinding.ActivityNewDiaryBinding;
import android.databinding.DataBindingUtil;
import android.view.View;
import android.widget.Toast;

import java.util.Date;

public class NewDiaryActivity extends Activity {

    private ActivityNewDiaryBinding binding;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_diary);
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = binding.txtTitle.getText().toString();
                String entry = binding.txtEntry.getText().toString();
                // Validate
                if (title.trim().length() == 0) {
                    Toast.makeText(view.getContext(), "Title cannot be empty!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (entry.trim().length() == 0) {
                    Toast.makeText(view.getContext(), "Entry cannot be empty!", Toast.LENGTH_SHORT).show();
                    return;
                }

                DBManager dbManager = new DBManager(view.getContext());
                dbManager.open();

                Date today = new Date();
                dbManager.createDiary((int)(today.getTime() / 1000), title, entry);
                dbManager.close();

                Toast.makeText(view.getContext(), "New diary added", Toast.LENGTH_LONG).show();
                binding.txtTitle.setText("");
                binding.txtEntry.setText("");
            }
        });
    }
}
