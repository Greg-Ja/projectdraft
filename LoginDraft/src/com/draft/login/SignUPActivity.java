package com.draft.login;

import com.draft.login.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUPActivity extends Activity
{
	EditText editTextUserName,editTextPassword,editTextConfirmPassword;
	Button btnCreateAccount;
	
	LoginDataBaseAdapter loginDataBaseAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup);
		
		// obtenir l'instance de l'adaptateur de base de données
		loginDataBaseAdapter=new LoginDataBaseAdapter(this);
		loginDataBaseAdapter=loginDataBaseAdapter.open();
		
		// Obtenez des références de vues
		editTextUserName=(EditText)findViewById(R.id.editTextUserName);
		editTextPassword=(EditText)findViewById(R.id.editTextPassword);
		editTextConfirmPassword=(EditText)findViewById(R.id.editTextConfirmPassword);
		
		btnCreateAccount=(Button)findViewById(R.id.buttonCreateAccount);
		btnCreateAccount.setOnClickListener(new View.OnClickListener() {
		
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			String userName=editTextUserName.getText().toString();
			String password=editTextPassword.getText().toString();
			String confirmPassword=editTextConfirmPassword.getText().toString();
			
			// vérifier si tous les champs sont vide
			if(userName.equals("")||password.equals("")||confirmPassword.equals(""))
			{
					Toast.makeText(getApplicationContext(), "Champ vide", Toast.LENGTH_LONG).show();
					return;
			}
			// vérifier si les deux mot de passe correspond
			if(!password.equals(confirmPassword))
			{
				Toast.makeText(getApplicationContext(), "Mot de passe invalide", Toast.LENGTH_LONG).show();
				return;
				
			}
			else
			{
			    // Enregistrer les données dans la base
			    loginDataBaseAdapter.insertEntry(userName, password);
			    Toast.makeText(getApplicationContext(), "Compte créé avec succés ", Toast.LENGTH_LONG).show();
			    
			  //Et l'utilisateur est diriger vers la page d'accueil
				Intent intent = new Intent(SignUPActivity.this, LoginDisplay.class);
				startActivity(intent);
			}
		}
	});
}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		loginDataBaseAdapter.close();
	}
}
