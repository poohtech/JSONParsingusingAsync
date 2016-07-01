package com.example.jsonparsingusingasync;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SplashActivity extends Activity {

	private Button btnParsing, btnLatLong;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		btnParsing = (Button) findViewById(R.id.btnParsing);
		btnLatLong = (Button) findViewById(R.id.btnLatLong);

		btnParsing.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(SplashActivity.this,
						LoginActivity.class));
			}
		});

		btnLatLong.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(SplashActivity.this,
						GetLatLongActivity.class));
			}
		});

	}

}
