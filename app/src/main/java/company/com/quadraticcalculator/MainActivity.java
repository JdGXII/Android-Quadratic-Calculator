package company.com.quadraticcalculator;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import eu.inmite.android.lib.validations.form.annotations.NotEmpty;

public class MainActivity extends Activity {

    @NotEmpty(messageId = R.string.validation_empty)
    private EditText aText;
    @NotEmpty(messageId = R.string.validation_empty)
    private EditText bText;
    @NotEmpty(messageId = R.string.validation_empty)
    private EditText cText;
    private Button submit;
    private TextView result1Text;
    private TextView result2Text;
    private Button clear;
    private GraphView gView;
    private boolean save_preference;
    private String decimal_place;
    private static final String PREFS_NAME = "TestData";

    //Stores the quotients input by the user
    private double a; private double b; private double  c;
    private double result1=0; private double result2=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ToggleButton tb;
        tb = (ToggleButton)findViewById(R.id.sessionDataToggle);

        SharedPreferences prefState = getSharedPreferences(PREFS_NAME,
                MODE_PRIVATE);

        save_preference =  prefState.getBoolean(getPackageName() + ".isChecked", false);
        decimal_place = prefState.getString(getPackageName()+".decimalPlaceContent", "");



        aText = (EditText)findViewById(R.id.a);
        bText = (EditText)findViewById(R.id.b);
        cText = (EditText)findViewById(R.id.c);
        submit = (Button)findViewById(R.id.submit);
        result1Text = (TextView)findViewById(R.id.result1);
        result2Text= (TextView)findViewById(R.id.result2);
        clear = (Button)findViewById(R.id.clear_button);
        gView = (GraphView)findViewById(R.id.graphView);

        if (save_preference){

            getQuotientPreferences();
        }

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                aText.setText("");
                bText.setText("");
                cText.setText("");
                result2Text.setText("");
                result1Text.setText("");

            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String translation1 = "";
                String translation2 = "";
                a = Double.parseDouble(aText.getText().toString());
                b = Double.parseDouble(bText.getText().toString());
                c = Double.parseDouble(cText.getText().toString());
                result1 = (-b + (Math.sqrt((b * b) - (4 * a * c)))) / (2 * a);
                result2 = (-b - (Math.sqrt((b * b) - (4 * a * c)))) / (2 * a);
                boolean flag1 = Double.isNaN(result1);
                boolean flag2 = Double.isNaN(result2);
                String format = "%."+decimal_place+"f";
                if (!flag1) {


                    if(decimal_place.equals("") || decimal_place.equals("Show all")){

                        translation1 = Double.toString(result1);

                    }
                    else{


                        translation1 = String.format(format, result1);
                    }

                } else {
                    translation1 = "Doesn't exist";
                }

                if (!flag2) {

                    if(decimal_place.equals("") || decimal_place.equals("Show all")){

                        translation2 = Double.toString(result2);

                    }
                    else{

                        translation2 = String.format(format, result2);
                    }


                } else {
                    translation2 = "Doesn't exist";
                }

                String empt = "";
                result1Text.setText(translation1);
                result2Text.setText(translation2);
                gView.setA(a);
                gView.setB(b);
                gView.setC(c);
                gView.setResult1(result1);
                gView.setResult2(result2);
                gView.invalidate();


            }
        });





    }

    @Override
    protected void onPause()
    {
        super.onPause();
        Log.i(getClass().getSimpleName(), "onPause");

        // Put questions in SharedPreferences
        if (save_preference){

            putQuotientPreferences();

        }

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
        switch (item.getItemId()) {


            case R.id.menu_preferences:
                startActivity(new Intent(getApplicationContext(),
                        PreferenceActivity.class));
                return true;

            case R.id.menu_help:
                startActivity(new Intent(getApplicationContext(),
                        Help.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(getClass().getSimpleName(), "onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(getClass().getSimpleName(), "onRestoreInstanceState");
    }

    private void putQuotientPreferences()
    {
        // Get a SharedPreferences file
        SharedPreferences state = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // Get a SharedPreferences editor
        SharedPreferences.Editor editor = state.edit();





        editor.putString(getPackageName() + ".aText", aText.getText().toString());
        editor.putString(getPackageName() + ".bText", bText.getText().toString());
        editor.putString(getPackageName() + ".cText", cText.getText().toString());


        // Commit the editor additions
        editor.commit();
    }

    private void getQuotientPreferences()
    {


        // Get SharedPreferences
        SharedPreferences prefState = getSharedPreferences(PREFS_NAME,
                MODE_PRIVATE);


            aText.setText(prefState.getString(getPackageName()+".aText",""));
            bText.setText(prefState.getString(getPackageName()+".bText",""));
            cText.setText(prefState.getString(getPackageName()+".cText",""));


    }
}
