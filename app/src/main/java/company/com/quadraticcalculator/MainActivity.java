package company.com.quadraticcalculator;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

    private EditText aText;
    private EditText bText;
    private EditText cText;
    private Button submit;
    private TextView result1Text;
    private TextView result2Text;

    private double a; private double b; private double  c;
    private double result1=0; private double result2=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        aText = (EditText)findViewById(R.id.a);
        bText = (EditText)findViewById(R.id.b);
        cText = (EditText)findViewById(R.id.c);
        submit = (Button)findViewById(R.id.submit);
        result1Text = (TextView)findViewById(R.id.result1);
        result2Text= (TextView)findViewById(R.id.result2);

        aText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                a = Double.parseDouble(aText.getText().toString());



            }

        });

        aText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    aText.setText("");
                }
                return false;
            }
        });



        bText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                b = Double.parseDouble(bText.getText().toString());

            }
        });

        bText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_DEL) {
                    bText.setText("");
                }
                return false;
            }
        });

        cText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                c = Double.parseDouble(cText.getText().toString());

            }
        });

        cText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if(keyCode == KeyEvent.KEYCODE_DEL){
                    cText.setText("");
                }
                return false;
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String translation1 = "";
                String translation2 = "";
                result1 = (-b + (Math.sqrt((b*b)-4*a*c)))/2*a;
                result2 = (-b - (Math.sqrt((b*b)-4*a*c)))/2*a;
                boolean flag1 = Double.isNaN(result1);
                boolean flag2 = Double.isNaN(result2);

                if(!flag1){
                    translation1 = Double.toString(result1);
                }
                else {
                    translation1 = "Doesn't exist";
                }

                if(!flag2){
                    translation2 = Double.toString(result2);
                }
                else {
                    translation2 = "Doesn't exist";
                }


                result1Text.setText(translation1);
                result2Text.setText(translation2);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
