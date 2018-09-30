package in.mizardar.loginregisterexample;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class WelcomeActivity extends AppCompatActivity {

    //3000 milisec = 3 sec timeout
    private static final long WELCOME_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //create a timer thread to launch login screen after 3 seconds

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your Login Activity
                Intent i = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, WELCOME_TIME_OUT);

    }
}
