package com.activity;
 
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ongletandroid.R;
 
public class Formulaire extends Activity {
 
  private Spinner spinner1;
  private Button btnSubmit;
  
  /** Barre d'action **/

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
 
	
	addListenerOnButton();
	addListenerOnSpinnerItemSelection();
  }
 
  // ajouter des éléments  dynamique dans spinner
  public void addListenerOnSpinnerItemSelection() {
	spinner1 = (Spinner) findViewById(R.id.spinner1);
	spinner1.setOnItemSelectedListener(new MenuDeroulantFormulaire());
  }
 
  // obtenir la valeur de la liste déroulante sélectionné
  public void addListenerOnButton() {
 
	spinner1 = (Spinner) findViewById(R.id.spinner1);
	btnSubmit = (Button) findViewById(R.id.btnSubmit);
 
	btnSubmit.setOnClickListener(new OnClickListener() {
 
	  @Override
	  public void onClick(View v) {
 
	    Toast.makeText(Formulaire.this,
		"OnClickListener : " + 
                "\nSpinner 1 : "+ String.valueOf(spinner1.getSelectedItem()),
			Toast.LENGTH_SHORT).show();
	  }
 
	});
  }
}