package com.example.age_checking;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
{
    private EditText editTextName, editTextAge;
    Button checkBtn;
    LinearLayout resultsContnr;
    TextView nameResult, ageResult, err;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName = findViewById(R.id.editTextName);
        editTextAge = findViewById(R.id.editTextAge);
        checkBtn = findViewById(R.id.checkButton);
        resultsContnr = findViewById(R.id.resultsContainer);
        nameResult = findViewById(R.id.nameResult);
        ageResult = findViewById(R.id.ageResult);
        err = findViewById(R.id.errorText);

        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputtedName = editTextName.getText().toString().trim();
                String inputtedAge = editTextAge.getText().toString().trim();

                if(inputtedName.isEmpty() || inputtedAge.isEmpty() )
                {
                    err.setText("Bạn cần nhập đầy đủ thông tin.");
                    err.setVisibility(View.VISIBLE);
                    resultsContnr.setVisibility(View.INVISIBLE);
                    return;
                }

                try
                {
                    ageResult.setText(AgeClassifying(Integer.parseInt(inputtedAge)));
                }
                catch (NumberFormatException e)
                {
                    err.setText("Tuổi bạn nhập không hợp lệ !");
                    err.setVisibility(View.VISIBLE);
                    resultsContnr.setVisibility(View.INVISIBLE);
                    return;
                }
                err.setVisibility(View.GONE);
                resultsContnr.setVisibility(View.VISIBLE);
                nameResult.setText(inputtedName);

            }
        });
    }

    private String AgeClassifying (int age)
    {
        if(age >= 0 && age < 2)
        {
            return "em bé.";
        }
        if(age >= 2 && age < 18)
        {
            return "trẻ em.";
        }
        if(age >= 18 && age < 65)
        {
            return "người lớn.";
        }
        if(age >= 65)
        {
            return "nguời cao tuổi.";
        }
        return "số tuổi bạn nhập khong hợp lệ, vui lòng nhập lại !";
    }
}