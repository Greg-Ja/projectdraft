package com.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ongletandroid.R;



public class Action extends Activity {
	
	//Déclarations des variables
	private int points = 0;
	private int resultat;
	Button deuxPoints;
	Button moinsDeuxPoints;
	TextView score;

	/** Barre d'action **/

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_action);
	
	//Récupèration de tous les éléments de notre interface graphique grâce aux ID
			deuxPoints = (Button) findViewById(R.id.deux_points);
			moinsDeuxPoints = (Button) findViewById(R.id.moins_deux_points);
			score = (TextView) findViewById(R.id.score);
			
			//Attribution d'un écouteur d'évènement au bouton deuxPoints
			deuxPoints.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						plusDeuxPoints();
					}
			});
			
			//Attribution d'un écouteur d'événement au bouton moinsDeuxPoints
			moinsDeuxPoints.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					moinsDeuxPoints();					
				}
			});
		}
		
		//Méthode qui est exécutée lorsqu'on clique sur le bouton deuxPoints
	    public void plusDeuxPoints() {
	    	
	    	resultat += points + 2;
	    	score.setText(String.valueOf(resultat));
		    score.getText();
	    }	
	    
	    //Méthode qui est exécutée lorsqu'on clique sur le bouton moinsDeuxPoints
	    public void moinsDeuxPoints() {
	    	resultat = resultat - 2;
	    	score.setText(String.valueOf(resultat));
	    	score.getText();
	    }
}