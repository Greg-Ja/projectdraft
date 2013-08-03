package com.login;


import com.activity.Accueil;
import com.example.ongletandroid.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class LoginDisplay extends Activity {

	final String EXTRA_LOGIN = "userName";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_display);
	
		final Button loginButton = (Button) findViewById(R.id.accueil);
		loginButton.setOnClickListener(new OnClickListener() {
					
		  @Override
		  public void onClick(View v) {
			Intent intent = new Intent(LoginDisplay.this, Accueil.class);
			startActivity(intent);
			}
		});
		
		
		/**	Bouton pour quitter l'application **/
		Button btm = (Button) findViewById(R.id.button_exit);
		btm.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				AlertDialog.Builder builder = new AlertDialog.Builder(LoginDisplay.this);
				builder.setMessage("Voulez-vous vraiment quitter Draft ?");
				builder.setCancelable(false);
				builder.setPositiveButton("Oui",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {

								LoginDisplay.this.finish();
							}
						});
				builder.setNegativeButton("Non",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								dialog.cancel();
							}
						});
				AlertDialog alert = builder.create();
				alert.show();
			}
		});
        
       Intent intent = getIntent();
       TextView loginDisplay = (TextView) findViewById(R.id.email_display);
       
       
       if (intent != null) {
    	   loginDisplay.setText(intent.getStringExtra(EXTRA_LOGIN)); 
       }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }    
}