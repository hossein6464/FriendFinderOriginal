package diana.soleil.instagramprojectversion1plus;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ParseUser parseUser;
    EditText username, password;
    TextView textViewLogIn;
    Button signUpButton;
    Boolean buttonCheck;

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == textViewLogIn.getId()) {
            if (buttonCheck) {
                buttonCheck = false;
                signUpButton.setText("Log In");
                textViewLogIn.setText("or, Sign Up...");

            } else {
                buttonCheck = true;
                signUpButton.setText("Sign Up");
                textViewLogIn.setText("or, Log In...");

            }
        }
    }
    public void signUporSignIn(View view) {
        if (signUpButton.getText().equals("Log In")) {
            logIn();
        } else if (signUpButton.getText().equals("Sign Up")) {
            signUp();
        }
    }


    public void signUp () {

        if (username.getText().toString().equals("") || password.getText().toString().equals("")) {
            Toast.makeText(this, "Please Enter a Password or Username", Toast.LENGTH_SHORT).show();
        } else {
        parseUser = new ParseUser();
        parseUser.setUsername(username.getText().toString());
        parseUser.setPassword(password.getText().toString());
        parseUser.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.i("Sign Up","User " + username.getText().toString() + " was successfully Signed Up");
                    Intent intent = new Intent(getApplicationContext(), UserListActivity.class);
                    startActivity(intent);
                    Toast.makeText(MainActivity.this, "User " + username.getText().toString() + " was successfully Signed Up", Toast.LENGTH_SHORT).show();
                } else {
                    Log.i("Error", e.getMessage());
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        }

    }
    public void logIn () {
        ParseUser.logInInBackground(username.getText().toString(), password.getText().toString(), new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
            if (e == null && user != null) {
                Log.i("LogIn", "LogIn Successful");
                Intent intent = new Intent(getApplicationContext(), UserListActivity.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "User " + username.getText().toString() + " was successfully Signed In", Toast.LENGTH_SHORT).show();
            }   else {
                Log.i("Log In", e.getMessage());
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
            }
        });

    }
    public void currentlyLoggedIn () {
        if (ParseUser.getCurrentUser() != null) {
            Intent intent = new Intent(getApplicationContext(), UserListActivity.class);
            startActivity(intent);

        }
    }
    public void logOut () {
        ParseUser.logOutInBackground(new LogOutCallback() {
            @Override
            public void done(ParseException e) {
            if (e == null) {
                Log.i("Log Out" , "User logged out Successfully");
            } else {
                Log.i ("LogOut", e.getMessage());
            }
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (EditText) findViewById(R.id.editTextUsername);
        password = (EditText) findViewById(R.id.editTextPassword);
        textViewLogIn = (TextView) findViewById(R.id.textView);
        signUpButton = (Button) findViewById(R.id.button);
        buttonCheck = true;

        username.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        textViewLogIn.setOnClickListener(this);
    }
}