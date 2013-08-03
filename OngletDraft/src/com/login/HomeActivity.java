package com.login;

import com.example.ongletandroid.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class HomeActivity extends Activity {
	Button btnSignIn, btnSignUp;
	LoginDataBaseAdapter loginDataBaseAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// créer une instance de la base de données SQLite
		loginDataBaseAdapter = new LoginDataBaseAdapter(this);
		loginDataBaseAdapter = loginDataBaseAdapter.open();

		// Obtenez la référence de boutons
		btnSignIn = (Button) findViewById(R.id.buttonSignIN);
		btnSignUp = (Button) findViewById(R.id.buttonSignUP);

		// Réglez OnClickListener sur le bouton Créer un compte
		btnSignUp.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// / Créer l'intention pour l'Inscription et commence l'activité
				Intent intentSignUP = new Intent(getApplicationContext(),
						SignUPActivity.class);
				startActivity(intentSignUP);
			}
		});
	}

	// Méthodes pour gérer l'événement Click du bouton Connexion
	public void signIn(View V) {
		final Dialog dialog = new Dialog(HomeActivity.this);
		dialog.setContentView(R.layout.login);
		dialog.setTitle("Login");

		// obtenir les références des vues
		final EditText editTextUserName = (EditText) dialog
				.findViewById(R.id.editTextUserNameToLogin);
		final EditText editTextPassword = (EditText) dialog
				.findViewById(R.id.editTextPasswordToLogin);

		Button btnSignIn = (Button) dialog.findViewById(R.id.buttonSignIn);

		// Réglez Onclicklistener
		btnSignIn.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// obtenir l'identifiant et le mot de passe
				String userName = editTextUserName.getText().toString();
				String password = editTextPassword.getText().toString();

				// récupérer le mots de passe de la base de données pour les
				// utilisateurs respectif
				String storedPassword = loginDataBaseAdapter
						.getSinlgeEntry(userName);

				// vérifier si le mot de passe saisie correspond bien au mot de
				// passe saisis par l'utilisateur
				if (password.equals(storedPassword)) {
					// Si l'identification est validée un message "Bienvenue"
					// s'affiche
					Toast.makeText(HomeActivity.this, "Bienvenue",
							Toast.LENGTH_LONG).show();
					dialog.dismiss();

					// Et l'utilisateur est diriger vers la page d'accueil
					Intent intent = new Intent(HomeActivity.this,
							LoginDisplay.class);
					startActivity(intent);
				} else {
					Toast.makeText(HomeActivity.this,
							"Nom d'utilisateur ou mot de passe incorrect",
							Toast.LENGTH_LONG).show();
				}
			}
		});

		dialog.show();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// Ferme la base de donnée
		loginDataBaseAdapter.close();
	}
}
