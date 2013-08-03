package com.draft.activity;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.draft.login.R;

public class PlayByPlay extends Activity{

	private ScrollView sv;
	private LinearLayout l1;
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
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play_by_play);

		
		// Bouton d'intéraction du LiveScore
		Button liveScoreButton = (Button) findViewById(R.id.live_score);
		liveScoreButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(PlayByPlay.this, LiveScore.class);
				startActivity(intent);
			}
		});

		// Bouton d'intéraction du PLay By PLay
		Button playByPLayButton = (Button) findViewById(R.id.play_by_play);
		playByPLayButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(PlayByPlay.this, PlayByPlay.class);
				startActivity(intent);
			}
		});

		// Bouton d'intéraction des Stats Game
		Button statsGameButton = (Button) findViewById(R.id.stats_game);
		statsGameButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(PlayByPlay.this, StatsGame.class);
				startActivity(intent);
			}
		});
		
		// Bouton d'intéraction des Stats Game
		Button statsTeamButton = (Button) findViewById(R.id.stats_team);
		statsTeamButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(PlayByPlay.this, StatsTeam.class);
				startActivity(intent);
			}
		});

		// Bouton d'intéraction Action
		Button actionButton = (Button) findViewById(R.id.action);
		actionButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(PlayByPlay.this, Action.class);
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
					PlayByPlay.this.dem.setText(R.string.stoper);
					PlayByPlay.this.tempsTotal = 0;
					PlayByPlay.this.tempsDepart = System.currentTimeMillis();
					PlayByPlay.this.dec.setTextColor(Color.parseColor("#888888"));
					cont = true;
					break;
				case 'a':
					etat = 'r';
					PlayByPlay.this.dem.setText(R.string.redemarrer);
					cont = false;
					PlayByPlay.this.tempsTotal += tempsPasse;
					PlayByPlay.this.dec.setTextColor(Color.parseColor("#888888"));
					break;
				case 'r':
					etat = 'a';
					PlayByPlay.this.dem.setText(R.string.stoper);
					PlayByPlay.this.tempsDepart = System.currentTimeMillis();
					cont = true;
					PlayByPlay.this.dec.setTextColor(Color.parseColor("#888888"));
				}

			}
		});
		
		reinit.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				PlayByPlay.this.cont = false;
				PlayByPlay.this.dem.setText(R.string.pause_play);
				PlayByPlay.this.dec.setTextColor(Color.parseColor("#888888"));
				PlayByPlay.this.etat = 'd';
				PlayByPlay.this.dec.setText("00:00:00");
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

				PlayByPlay.this.runOnUiThread(new Runnable() {
					public void run() {
						if (cont) {
							PlayByPlay.this.tempsActuel = System.currentTimeMillis();
							PlayByPlay.this.tempsPasse = PlayByPlay.this.tempsActuel
									- PlayByPlay.this.tempsDepart;
							PlayByPlay.this.cal.setTimeInMillis(tempsTotal
									+ tempsPasse);
							PlayByPlay.this.dec.setText(PlayByPlay.format(PlayByPlay.this.cal.get(Calendar.MINUTE))
									+ ":"
									+ PlayByPlay.format(PlayByPlay.this.cal.get(Calendar.SECOND))
									+ ":"
									+ PlayByPlay.format((int) PlayByPlay.this.cal.get(Calendar.MILLISECOND) / 10));
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
