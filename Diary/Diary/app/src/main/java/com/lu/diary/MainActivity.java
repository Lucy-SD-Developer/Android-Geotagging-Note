package com.lu.diary;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.lu.diary.databinding.ActivityMainBinding;
import android.databinding.DataBindingUtil;

public class MainActivity extends Activity {
    private static final String PREFS_PW = "Config";
    private static final String PW_STR = "local_password";
    private String password;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        SharedPreferences settings = getSharedPreferences(PREFS_PW, 0);
        password = settings.getString(PW_STR, "");

        if (password != null && !password.isEmpty()) {
            // Password exists
            binding.txtInstruction.setText(R.string.instruction_login);
            binding.btnLogin.setVisibility(View.VISIBLE);
            binding.btnCreate.setVisibility(View.GONE);

        } else {
            // Create a new password
            binding.txtInstruction.setText(R.string.instruction_create);
            binding.btnLogin.setVisibility(View.GONE);
            binding.btnCreate.setVisibility(View.VISIBLE);
        }

        binding.btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String localPassword = binding.inputPw.getText().toString();
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setMessage("OK to save the password?");
                builder.setCancelable(false);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        saveLocalPasswordPreference(localPassword);
                        invokeActivity();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String localPassword = binding.inputPw.getText().toString();
                if (localPassword.equals(password)) {
                    invokeActivity();
                } else {
                    Toast.makeText(view.getContext(), "Password incorrect. Please enter again.",
                        Toast.LENGTH_SHORT).show();
                    binding.inputPw.setText("");
                }
            }
        });
    }

    //save the pw info
    public void saveLocalPasswordPreference(String password) {
        SharedPreferences settings = getSharedPreferences(PREFS_PW, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("local_password", password);
        editor.commit();

        Toast.makeText(this, "Saved Password!", Toast.LENGTH_LONG).show();
    }

    private void invokeActivity() {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

}
