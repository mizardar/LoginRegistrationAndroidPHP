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

public class RegisterActivity extends AppCompatActivity {

    private EditText _et_first_name,_et_last_name,_et_institute,_et_username,_et_password,_et_cnf_password;
    private Button _btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //get all views from register screen
        _et_first_name = (EditText) findViewById(R.id.firstName);
        _et_last_name = (EditText) findViewById(R.id.lastName);
        _et_institute = (EditText) findViewById(R.id.institute);
        _et_username = (EditText) findViewById(R.id.username);
        _et_password = (EditText) findViewById(R.id.password);
        _et_cnf_password = (EditText) findViewById(R.id.cnfPassword);
        _btn_register = (Button) findViewById(R.id.btnRegister);


        _btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performRegister();
            }
        });

    }

    protected void performRegister() {

        String firstName = _et_first_name.getText().toString();
        String lastName = _et_last_name.getText().toString();
        String institute = _et_institute.getText().toString();
        String username = _et_username.getText().toString();
        String password = _et_password.getText().toString();
        String cnfPassword  = _et_cnf_password.getText().toString();

        if (firstName.isEmpty()){
            Toast.makeText(this, "Please enter your first name", Toast.LENGTH_SHORT).show();
        }else if (lastName.isEmpty()){
            Toast.makeText(this, "Please enter your last name", Toast.LENGTH_SHORT).show();
        }else if (institute.isEmpty()){
            Toast.makeText(this, "Please enter your institute name", Toast.LENGTH_SHORT).show();
        }else if (username.isEmpty()){
            Toast.makeText(this, "Please enter your username", Toast.LENGTH_SHORT).show();
        }else if (password.isEmpty()){
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
        }else if (cnfPassword.isEmpty()){
            Toast.makeText(this, "Please confirm your password", Toast.LENGTH_SHORT).show();
        }else if (!cnfPassword.equalsIgnoreCase(password)){
            Toast.makeText(this, "confirm password and password does not match", Toast.LENGTH_SHORT).show();
        }else {
            callService();
        }

    }

    private void callService() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        //place your server IP address
        String loginURL = "http://192.168.0.101:8080/LoginRegisterExample/register.php";
        AsyncHttpClient client = new AsyncHttpClient();

        RequestParams params = new RequestParams();

        params.put("firstName" ,_et_first_name.getText().toString());
        params.put("lastName" ,_et_last_name.getText().toString());
        params.put("institute" ,_et_institute.getText().toString());
        params.put("username" ,_et_username.getText().toString());
        params.put("password" ,_et_password.getText().toString());


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
                        AlertDialog.Builder builder  = new AlertDialog.Builder(RegisterActivity.this);
                        builder.setMessage("failed to register, user may already exist!\n please try again.");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });

                        builder.show();
                    }else {
                        //login successful go to Login activity
                        Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(loginIntent);
                        finish();
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
                    Log.e("Register Activity Response", "Response = "+response);
                    Log.e("Register Activity Error", "Error = "+error.getMessage());
                    Toast.makeText(RegisterActivity.this, "Not able to connect to server right now\nsee log for details", Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });


    }
}
