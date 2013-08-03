package com.ongletAndroid;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import com.ongletAndroid.Onglet;
import com.activity.Action;
import com.activity.Accueil;
import com.example.ongletandroid.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;

public class Onglet extends Activity {
	
	private TabHost monTabHost;
	
	private char etat = 'd';
	private boolean cont = false;
	private TextView dec;
	private Button dem;
	private long tempsDepart;
	private long tempsActuel;
	private long tempsPasse;
	private long tempsTotal;
	private Calendar cal = Calendar.getInstance();

	private Timer t = new Timer();
	
	/** Barre d'action **/

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	/** Gestion des onglets **/
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_onglet);
		
		// Récupération du TabHost
		monTabHost =(TabHost) findViewById(R.id.TabHost01);
		// Avant d’ajouter des onglets, il faut impérativement appeler la méthode
		// setup() du TabHost
		monTabHost.setup();
		
		// Nous ajoutons les 4 onglets dans notre TabHost
		
		// Nous paramétrons le 1er Onglet
		TabSpec spec = monTabHost.newTabSpec("onglet_1");
		// Nous paramétrons le texte qui s’affichera dans l’onglet
		// ainsi que l’image qui se positionnera
		// au dessus du texte.
		spec.setIndicator("Onglet 1",getResources().getDrawable(R.drawable.logo));
		// On spécifie le Layout qui s’affichera lorsque l’onglet sera sélectionné
		spec.setContent(R.id.Onglet1);
		// On ajoute l’onglet dans notre TabHost
		monTabHost.addTab(spec);
		
		// Vous pouvez ajouter des onglets comme ceci : 
		monTabHost.addTab(monTabHost.newTabSpec("onglet_2").setIndicator("Onglet 2").setContent(R.id.Onglet2));
		monTabHost.addTab(monTabHost.newTabSpec("onglet_3").setIndicator("Onglet 3").setContent(R.id.Onglet3));
		monTabHost.addTab(monTabHost.newTabSpec("onglet_4").setIndicator("Onglet 4").setContent(R.id.Onglet4));
		
		// Nous paramétrons un écouteur onTabChangedListner pour récupérer
		// le changement d’onglet.
		monTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener (){
			public void onTabChanged(String tabId){
			// Vous pourrez exécuter du code lorsqu’un onglet est cliqué. Pour déterminer
			// quel onglet a été cliqué, il vous suffira de vérifier le tabId envoyé lors
			// du clic et d’exécuter votre code en conséquence.
						Toast.makeText(Onglet.this, "L’onglet avec l’identifiant : "
								+ tabId + " a été cliqué", Toast.LENGTH_SHORT).show();
				}
			}
		);
	
		// Bouton d'intéraction Action
				Button actionButton = (Button) findViewById(R.id.action);
				actionButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent intent = new Intent(Onglet.this, com.activity.Action.class);
						startActivity(intent);
					}
				});	
		
	/**	CODE DE FONCTIONNEMENT DU CHRONO	**/
		
		dem = (Button) this.findViewById(R.id.pause_play);
		Button reinit = (Button) this.findViewById(R.id.reset);
		dec = (TextView) this.findViewById(R.id.decompte);

		launch();

		dem.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				switch (etat) {
				case 'd':
					etat = 'a';
					Onglet.this.dem.setText(R.string.stoper);
					Onglet.this.tempsTotal = 0;
					Onglet.this.tempsDepart = System.currentTimeMillis();
					Onglet.this.dec.setTextColor(Color.parseColor("#888888"));
					cont = true;
					break;
				case 'a':
					etat = 'r';
					Onglet.this.dem.setText(R.string.redemarrer);
					cont = false;
					Onglet.this.tempsTotal += tempsPasse;
					Onglet.this.dec.setTextColor(Color.parseColor("#888888"));
					break;
				case 'r':
					etat = 'a';
					Onglet.this.dem.setText(R.string.stoper);
					Onglet.this.tempsDepart = System.currentTimeMillis();
					cont = true;
					Onglet.this.dec.setTextColor(Color.parseColor("#888888"));
				}

			}
		});
		
		reinit.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Onglet.this.cont = false;
				Onglet.this.dem.setText(R.string.pause_play);
				Onglet.this.dec.setTextColor(Color.parseColor("#888888"));
				Onglet.this.etat = 'd';
				Onglet.this.dec.setText("00:00:00");
				cal.setTimeInMillis(0);
			}
		});
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		t.cancel();
	}

	private void launch() {
		t.schedule(new TimerTask() {
			@Override
			public void run() {

				Onglet.this.runOnUiThread(new Runnable() {
					public void run() {
						if (cont) {
							Onglet.this.tempsActuel = System.currentTimeMillis();
							Onglet.this.tempsPasse = Onglet.this.tempsActuel
									- Onglet.this.tempsDepart;
							Onglet.this.cal.setTimeInMillis(tempsTotal
									+ tempsPasse);
							Onglet.this.dec.setText(Onglet.format(Onglet.this.cal.get(Calendar.MINUTE))
									+ ":"
									+ Onglet.format(Onglet.this.cal.get(Calendar.SECOND))
									+ ":"
									+ Onglet.format((int) Onglet.this.cal.get(Calendar.MILLISECOND) / 10));
						}
					}
				});
			}
		}, 0, 100);
	}

	private static String format(int i) {
		String s = Integer.toString(i);
		if (s.length() == 1)
			s = "0" + s;
		return s;
	}
}
