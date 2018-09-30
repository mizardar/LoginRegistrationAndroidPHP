package in.mizardar.loginregisterexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class HomePageActivity extends AppCompatActivity {

    TextView _tv_firstName,_tv_lastName,_tv_institute;
    ImageView logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        //get all views from home page
        _tv_firstName = (TextView) findViewById(R.id.firstName);
        _tv_lastName = (TextView) findViewById(R.id.lastName);
        _tv_institute = (TextView) findViewById(R.id.institute);
        logout = (ImageView) findViewById(R.id.logout);

        //get bundle sent from home page;
        Bundle bundle = getIntent().getBundleExtra("bundle");
        //extract data from bundle
        String firstName = bundle.getString("firstName");
        String lastName = bundle.getString("lastName");
        String username = bundle.getString("username");
        String institute = bundle.getString("institute");


        _tv_firstName.setText(firstName);
        _tv_lastName.setText(lastName);
        _tv_institute.setText(institute);

        //create a listener for logout image
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //go to login activity
                Intent homeIntent = new Intent(HomePageActivity.this, LoginActivity.class);
                startActivity(homeIntent);
                //finish current activity
                finish();
            }
        });
    }
}
