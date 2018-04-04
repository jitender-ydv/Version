package com.nitt.a205117019.version;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;




public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

   EditText _emailText;
   EditText _passwordText;
   Button _loginButton ;
   TextView _signupLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        _emailText = (EditText)findViewById((R.id.input_email));
        _passwordText = (EditText)findViewById(R.id.input_password);
        _loginButton = (Button)findViewById((R.id.btn_login));
        _signupLink = (TextView)findViewById(R.id.link_signup);

       _loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

    }

    public void login(){
        Log.d(TAG,"Login");

        if(!validate()){
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                0);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();



        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                },3000);



    }

    @Override

   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {


                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        finish();
    }

    public boolean validate(){
        boolean valid = true;

        String user_email  = _emailText.getText().toString();
        String user_password = _passwordText.getText().toString();

        if(user_email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(user_email)
                .matches()){
            _emailText.setError("enter a valid email address");
            valid = false;
        }else{
            _emailText.setError(null);
        }

        if(user_password.isEmpty() || !(user_password.length()>4 || user_password.length()<12)){
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        }else{
            _passwordText.setError(null);
        }

        return valid;
    }

    public void onLoginFailed(){
        Toast.makeText(getBaseContext(),"Login failed",Toast.LENGTH_LONG).show();
        _loginButton.setEnabled(true);
    }

}
