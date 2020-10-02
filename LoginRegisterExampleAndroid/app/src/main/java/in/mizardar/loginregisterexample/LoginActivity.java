package in.mizardar.loginregisterexample;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {

    private EditText _et_username,_et_password;
    private Button _btn_login,_btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //get all views from login screen
        _et_username = (EditText) findViewById(R.id.username);
        _et_password = (EditText) findViewById(R.id.password);
        _btn_login = (Button) findViewById(R.id.btnLogin);
        _btn_register = (Button) findViewById(R.id.btnRegister);

        //create a click listener for login button
        _btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performLogin();
            }
        });

        //create a click listener for register button
        _btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performRegister();
            }
        });

    }

    protected void performLogin() {

        if (!_et_username.getText().toString().isEmpty()){
            if (!_et_password.getText().toString().isEmpty()){
                callService();
            }else {
                Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "Please enter your username", Toast.LENGTH_SHORT).show();
        }


    }

    private void callService() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        //place your server IP address
        String loginURL = "http://192.168.0.101:8080/LoginRegisterExample/login.php";
        AsyncHttpClient client = new AsyncHttpClient();

        RequestParams params = new RequestParams();

        params.put("username",_et_username.getText().toString());
        params.put("password",_et_password.getText().toString());

        client.post(this, loginURL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                //request success
                try{
                    //dismiss the progressDialog first
                    progressDialog.dismiss();
                    //convert byte to String
                    String response = new String(responseBody,"UTF-8");
                    // convert response into a json object
                    JSONObject responseObject = new JSONObject(response);
                    //get variables from json
                    boolean isError = responseObject.getBoolean("Error");

                    if (isError){
                        // show dialog for invalid credentials
                         /* example error message
                       {
                            "Error":true,
                            "Message":"Invalid credentials"
                        }
                        */
                        AlertDialog.Builder builder  = new AlertDialog.Builder(LoginActivity.this);
                        builder.setMessage("Invaild credentials!!! please try again.");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });

                        builder.show();
                    }else {
                        //login successful go to home activity
                        /* example success message
                        {
                            "Error":false,
                            "ID":3,
                            "FirstName":"mithi",
                            "LastName":"mithi",
                            "Username":"mithi",
                            "Institute":"Sinhgad"
                        }
                        */
						parse_response(responseObject);
                        
                    }


                }catch (Exception e){
                    //dismiss the progressDialog first
                    progressDialog.dismiss();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                //request is failed print the result and give a message to user
                try{
                    //dismiss the progressDialog first
                    progressDialog.dismiss();
                    //convert byte to String
                    String response = new String(responseBody,"UTF-8");
                    Log.e("Login Activity Response", "Response = "+response);
                    Log.e("Login Activity Error", "Error = "+error.getMessage());
                    Toast.makeText(LoginActivity.this, "Not able to connect to server right now\nsee log for details", Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });


    }
	
	private void parse_response(JSONObject responseObject) {
		// get user data from response
        int ID =responseObject.getInt("ID");
        String firstName = responseObject.getString("FirstName");
        String lastName = responseObject.getString("LastName");
        String username = responseObject.getString("Username");
        String institute = responseObject.getString("Institute");

         //create a bundle to pass the data to home page
        Bundle bundle = new Bundle();
        //put all values inside the bundle
        bundle.putInt("ID",ID);
        bundle.putString("firstName",firstName);
        bundle.putString("lastName",lastName);
        bundle.putString("username",username);
        bundle.putString("institute",institute);

        Intent homeIntent = new Intent(LoginActivity.this, HomePageActivity.class);
        //put the bundle inside intent
        homeIntent.putExtra("bundle",bundle);

        startActivity(homeIntent);
        finish();
	}

    protected void performRegister() {

        //go to register activity
        Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(registerIntent);
    }
}
