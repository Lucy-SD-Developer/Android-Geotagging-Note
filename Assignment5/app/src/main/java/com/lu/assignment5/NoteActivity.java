package com.lu.assignment5;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.databinding.DataBindingUtil;
import com.lu.assignment5.databinding.ActivityNoteBinding;

public class NoteActivity extends Activity {

    private ActivityNoteBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_note);

        Intent intent = getIntent();
        String title = intent.getStringExtra(NoteMapActivity.EXTRA_TITLE);
        String entry = intent.getStringExtra(NoteMapActivity.EXTRA_ENTRY);
        binding.txtTitle.setText(title);
        binding.txtEntry.setText(entry);

        binding.txtLat.setText(Double.toString(intent.getDoubleExtra(NoteMapActivity.EXTRA_LATITUDE, 0.0)));
        binding.txtLong.setText(Double.toString(intent.getDoubleExtra(NoteMapActivity.EXTRA_LONGITUDE, 0.0)));
        binding.txtMethod.setText(intent.getStringExtra(NoteMapActivity.EXTRA_METHOD));

    }
}
