package com.example.jsonparsingusingasync;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends Activity {

    EditText edtEmail, edtPwd;
    Button btnLogin;

    //    private static String url = "http://api/androidhive/info/login.php";
    private String url = "http://api/androidhive/info/login.php";
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPwd = (EditText) findViewById(R.id.edtPwd);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                new LoginAsync().execute();
            }
        });
    }

    private class LoginAsync extends AsyncTask<Void, Void, Void> {

        @SuppressWarnings("rawtypes")
        private List nameValuePair;

        @SuppressWarnings({"unchecked", "rawtypes"})
        protected void onPreExecute() {
            super.onPreExecute();

            mProgressDialog = new ProgressDialog(LoginActivity.this);
            mProgressDialog.setMessage("Please Wait...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();

            /*code of send parameter for GET & POST Method is same*/
            nameValuePair = new ArrayList();
            nameValuePair.add(new BasicNameValuePair("auth_key", "ksahkjhakhfcnzmxhcja"));
            nameValuePair.add(new BasicNameValuePair("username", edtEmail
                    .getText().toString()));
            nameValuePair.add(new BasicNameValuePair("password", edtPwd
                    .getText().toString()));

            System.out.println("=====nameValuePair====" + nameValuePair);
//           Output : =====nameValuePair====[auth_key=ksahkjhakhfcnzmxhcja, username=qwerty, password=123123]
        }

        @SuppressWarnings("unchecked")
        @Override
        protected Void doInBackground(Void... params) {
            ServiceHandler sh = new ServiceHandler();
            String response = sh.makeServiceCall(url, ServiceHandler.POST,
                    nameValuePair);

            System.out.println("=====response====" + response);
//          Output :  =====response===={"code":0,"message":"success","admin_id":5}

            int code = 0;
            String mesg = null;
            JSONObject jsonDoc = null;

            if (response != null) {
                try {
                    jsonDoc = new JSONObject(response);
                    code = jsonDoc.getInt("code");
                    mesg = jsonDoc.getString("message");

                    if (code == 0) {
                        /* JSON Parsing */
                        int admin_id = jsonDoc.getInt("admin_id");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(LoginActivity.this,
                        "Couldn't get any data from the url", Toast.LENGTH_LONG)
                        .show();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
                mProgressDialog = null;
            }

            Toast.makeText(LoginActivity.this, "Successfully Login!",
                    Toast.LENGTH_LONG).show();
            Intent i = new Intent(LoginActivity.this, GetLatLongActivity.class);
            startActivity(i);
        }

    }

}
