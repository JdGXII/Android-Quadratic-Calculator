package company.com.quadraticcalculator;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.ToggleButton;

public class PreferenceActivity extends Activity {

    private ToggleButton toggleB;
    private Spinner decimalsSpinner;
   // private int toggle_flag;
    //private boolean check_status;
    private static final String PREFS_NAME = "TestData";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);
        toggleB = (ToggleButton)findViewById(R.id.sessionDataToggle);
        //toggle_flag = 0;
        decimalsSpinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.decimals_array,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        decimalsSpinner.setAdapter(adapter);
        getCheckStatus();



        /*toggleB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){

                    /*Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);
                    toggle_flag = 1;
                    Intent intent = new Intent(getApplicationContext(),
                            MainActivity.class);
                    intent.putExtra(getPackageName() + ".save_preference",
                            toggle_flag);
                }
                else{
                    toggle_flag = 0;
                    Intent intent = new Intent(getApplicationContext(),
                            MainActivity.class);
                    intent.putExtra(getPackageName() + ".save_preference",
                            toggle_flag);

                }
            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_preference, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {

            case R.id.main_menu:
                startActivity(new Intent(getApplicationContext(),
                        MainActivity.class));
                return true;

            /*case R.id.menu_preferences:
                startActivity(new Intent(getApplicationContext(),
                        Preferences.class));
                return true;*/
            case R.id.menu_help:
                startActivity(new Intent(getApplicationContext(),
                        Help.class));
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }


    }

    private void putCheckedStatus()
    {
        // Get a SharedPreferences file
        SharedPreferences state = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // Get a SharedPreferences editor
        SharedPreferences.Editor editor = state.edit();




        editor.putBoolean(getPackageName() + ".isChecked", toggleB.isChecked());



        // Commit the editor additions
        editor.commit();
    }

    private void getCheckStatus()
    {


        // Get SharedPreferences
        SharedPreferences prefState = getSharedPreferences(PREFS_NAME,
                MODE_PRIVATE);

        toggleB.setChecked(prefState.getBoolean(getPackageName() + ".isChecked", false));



    }

    @Override
    protected void onPause()
    {
        super.onPause();
        Log.i(getClass().getSimpleName(), "onPause");

        // Put toggle button check status

            putCheckedStatus();
    }
}
