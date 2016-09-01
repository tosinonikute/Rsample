package com.rsample.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.rsample.EmailValidator;
import com.rsample.R;
import com.rsample.model.PhoneBook;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    EditText name, lastName, email, phoneNumber;
    Button saveContact, viewAllContact;
    //initialize realm
    Realm realm;

    // The validator for the email input field.
    private EmailValidator mEmailValidator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        name = (EditText) findViewById(R.id.name);
        lastName = (EditText) findViewById(R.id.lastname);
        email = (EditText) findViewById(R.id.email);
        phoneNumber = (EditText) findViewById(R.id.phonenumber);
        saveContact = (Button) findViewById(R.id.savecontact);
        viewAllContact = (Button) findViewById(R.id.viewallcontact);

        // Setup field validators.
        mEmailValidator = new EmailValidator();
        email.addTextChangedListener(mEmailValidator);


        saveContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println("hello+++");

                if(name.getText().toString().equals("")){
                    name.setError("Empty first name field");
                    Snackbar.make(view, "Please enter your name", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                } else if(lastName.getText().toString().equals("")){
                    lastName.setError("Empty last name field");
                    Snackbar.make(view, "Please enter your last name", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                } else if(email.getText().toString().equals("")){
                    email.setError("Empty email field");
                    Snackbar.make(view, "Please enter your email", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                } else if (!mEmailValidator.isValid()) {
                    email.setError("Invalid email");
                    Snackbar.make(view, "Please enter a valid email", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                } else if(phoneNumber.getText().toString().equals("")){
                    Snackbar.make(view, "Please enter your phone number", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                } else {

                    // Get a Realm instance for this thread
                    realm = Realm.getInstance(getApplicationContext());

                    //Writing to Realm with Transaction blocks
                    realm.beginTransaction();

                    PhoneBook phoneBook = realm.createObject(PhoneBook.class);

                    // increment index
                    long nextID = (long) (realm.where(PhoneBook.class).max("id"));
                    long primaryKeyValue = nextID + 1;

                    try {
                        phoneBook.setId(primaryKeyValue);
                        phoneBook.setName(name.getText().toString());
                        phoneBook.setLastName(lastName.getText().toString());
                        phoneBook.setEmail(email.getText().toString());
                        phoneBook.setPhone(phoneNumber.getText().toString());
                        realm.commitTransaction();

                        //set the Edit text field to empty
                        name.setText("");
                        lastName.setText("");
                        email.setText("");
                        phoneNumber.setText("");

                    } catch (Exception e) {
                        Log.e("Realm Error", "error" + e.getLocalizedMessage());
                        realm.cancelTransaction();
                    }

                    //Notify user that contact has been saved using Green background
                    Snackbar snack = Snackbar.make(view, "New contact information saved", Snackbar.LENGTH_LONG).setAction("Action", null);
                    ViewGroup group = (ViewGroup) snack.getView();
                    group.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorGreen));
                    snack.show();



                }

            }
        });

        viewAllContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, ListActivity.class);
                context.startActivity(intent);
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
