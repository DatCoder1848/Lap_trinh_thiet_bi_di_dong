package com.example.ui_template;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
{
    private EditText editTextMail;
    TextView errortText;
    Button submitButton;

    @Override
    protected void onCreate (Bundle savedInstantState)
    {
        super.onCreate(savedInstantState);
        setContentView(R.layout.activity_mail);

        editTextMail = findViewById(R.id.editTextTextEmailAddress);
        errortText = findViewById(R.id.errorText);
        submitButton = findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextMail.getText().toString().trim();

                if (isValidEmail(email)) {
                    errortText.setVisibility(View.GONE);
                }
                else {
                    if (!email.contains("@"))
                        errortText.setText("Email khong dung dinh dang!");
                    else
                        errortText.setText("Email khong hop le!");
                    errortText.setVisibility((View.VISIBLE));
                }
            }
        });

    }

     private boolean isValidEmail(String email)
    {
        if (email == null)
        {
            return false;
        }
        else {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }

}
