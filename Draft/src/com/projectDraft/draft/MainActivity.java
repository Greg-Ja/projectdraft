package com.projectDraft.draft;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		//Déclaration d'une variable globale button
        Button bouton = (Button) findViewById(R.id.btm);
        
        //la méthode setOnClickListener() elle joue le role de addActionListener() en JAVA 
        //et pour quitter l’application vous pouvez utiliser l’instruction java standard System.exit(0);
        bouton.setOnClickListener(new OnClickListener() {

        	@Override
        	         public void onClick(View v) {
        	                           System.exit(0);
        	         }
        	});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
}
