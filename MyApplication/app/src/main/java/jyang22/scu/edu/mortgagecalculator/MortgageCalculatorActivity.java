package jyang22.scu.edu.mortgagecalculator;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;

public class MortgageCalculatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mortgage_calculator);

        // amount borrowed
        final EditText amount = (EditText)findViewById(R.id.principal);

        // interest rate
        final EditText rate = (EditText)findViewById(R.id.interest_year);

        // loan term
        final RadioGroup radio_group = (RadioGroup)findViewById(R.id.radio_group);

        // taxes and insurance
        final CheckBox check_box = (CheckBox)findViewById(R.id.checkbox_tax);

        // payment monthly
        final TextView text_pay = (TextView)findViewById(R.id.text_pay);

        Button btn = (Button)findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String principal = amount.getText().toString();
                String monthly_rate = rate.getText().toString();

                // check the principal number
                float p;
                try {
                    p = Float.parseFloat(principal);
                }
                catch (NumberFormatException nfe) {
                    showToast("Invalid amount borrowed");
                    return;
                }
                if (p < 0) {
                    showToast("Negative amount borrowed");
                    return;
                }

                // check the interest rate
                float j = 7.0f;
                try {
                    j = Float.parseFloat(monthly_rate);
                }
                catch (NumberFormatException nfe) {
                    showToast("Invalid interest rate");
                    return;
                }
                if (j < 0 || j > 20) {
                    showToast("Interest rate out of range");
                    return;
                }
                j = j / 1200f;

                // set the month of loan
                int radioButtonID = radio_group.getCheckedRadioButtonId();
                View radioButton = radio_group.findViewById(radioButtonID);
                int idx = radio_group.indexOfChild(radioButton);
                float n = 30f;
                switch (idx) {
                    case 0:
                        n = 7f;
                        break;
                    case 1:
                        n = 15f;
                        break;
                    case 2:
                        n = 30f;
                        break;
                }
                n = n * 12f;

                // set if taxes and insurance are included
                float t = 0f;
                if (check_box.isChecked()) {
                    t = 0.001f;
                }

                float pay = (float) ((p * j / (1f - Math.pow((1f + j), -1f * n))) + p * t);
                BigDecimal pay_value = new BigDecimal(pay);
                pay_value = pay_value.setScale(2, 4);
                text_pay.setText("Your need to pay " + pay_value + " each month.");
                showToast(pay_value.toString());

            }
        });
    }

    public void showToast(CharSequence text) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

}
