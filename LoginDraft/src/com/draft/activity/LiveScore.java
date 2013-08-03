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

public class LiveScore extends Activity{

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
		setContentView(R.layout.activity_live_score);

		
		// Bouton d'intéraction du LiveScore
		Button liveScoreButton = (Button) findViewById(R.id.live_score);
		liveScoreButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LiveScore.this, LiveScore.class);
				startActivity(intent);
			}
		});

		// Bouton d'intéraction du PLay By PLay
		Button playByPLayButton = (Button) findViewById(R.id.play_by_play);
		playByPLayButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LiveScore.this, PlayByPlay.class);
				startActivity(intent);
			}
		});

		// Bouton d'intéraction des Stats Game
		Button statsGameButton = (Button) findViewById(R.id.stats_game);
		statsGameButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LiveScore.this, StatsGame.class);
				startActivity(intent);
			}
		});
		
		// Bouton d'intéraction des Stats Game
		Button statsTeamButton = (Button) findViewById(R.id.stats_team);
		statsTeamButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LiveScore.this, StatsTeam.class);
				startActivity(intent);
			}
		});

		// Bouton d'intéraction Action
		Button actionButton = (Button) findViewById(R.id.action);
		actionButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LiveScore.this, Action.class);
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
					LiveScore.this.dem.setText(R.string.stoper);
					LiveScore.this.tempsTotal = 0;
					LiveScore.this.tempsDepart = System.currentTimeMillis();
					LiveScore.this.dec.setTextColor(Color.parseColor("#888888"));
					cont = true;
					break;
				case 'a':
					etat = 'r';
					LiveScore.this.dem.setText(R.string.redemarrer);
					cont = false;
					LiveScore.this.tempsTotal += tempsPasse;
					LiveScore.this.dec.setTextColor(Color.parseColor("#888888"));
					break;
				case 'r':
					etat = 'a';
					LiveScore.this.dem.setText(R.string.stoper);
					LiveScore.this.tempsDepart = System.currentTimeMillis();
					cont = true;
					LiveScore.this.dec.setTextColor(Color.parseColor("#888888"));
				}

			}
		});
		
		reinit.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				LiveScore.this.cont = false;
				LiveScore.this.dem.setText(R.string.pause_play);
				LiveScore.this.dec.setTextColor(Color.parseColor("#888888"));
				LiveScore.this.etat = 'd';
				LiveScore.this.dec.setText("00:00:00");
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

				LiveScore.this.runOnUiThread(new Runnable() {
					public void run() {
						if (cont) {
							LiveScore.this.tempsActuel = System.currentTimeMillis();
							LiveScore.this.tempsPasse = LiveScore.this.tempsActuel
									- LiveScore.this.tempsDepart;
							LiveScore.this.cal.setTimeInMillis(tempsTotal
									+ tempsPasse);
							LiveScore.this.dec.setText(LiveScore.format(LiveScore.this.cal.get(Calendar.MINUTE))
									+ ":"
									+ LiveScore.format(LiveScore.this.cal.get(Calendar.SECOND))
									+ ":"
									+ LiveScore.format((int) LiveScore.this.cal.get(Calendar.MILLISECOND) / 10));
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