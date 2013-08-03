package com.draft.activity;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import com.draft.login.R;
import com.draft.login.R.id;
import com.draft.login.R.layout;
import com.draft.login.R.menu;
import com.draft.login.R.string;

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

public class StatsTeam extends Activity{

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
		setContentView(R.layout.activity_stats_team);

		
		// Bouton d'intéraction du LiveScore
		Button liveScoreButton = (Button) findViewById(R.id.live_score);
		liveScoreButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(StatsTeam.this, LiveScore.class);
				startActivity(intent);
			}
		});

		// Bouton d'intéraction du PLay By PLay
		Button playByPLayButton = (Button) findViewById(R.id.play_by_play);
		playByPLayButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(StatsTeam.this, PlayByPlay.class);
				startActivity(intent);
			}
		});

		// Bouton d'intéraction des Stats Game
		Button statsGameButton = (Button) findViewById(R.id.stats_game);
		statsGameButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(StatsTeam.this, StatsGame.class);
				startActivity(intent);
			}
		});
		
		// Bouton d'intéraction des Stats Game
		Button statsTeamButton = (Button) findViewById(R.id.stats_team);
		statsTeamButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(StatsTeam.this, StatsTeam.class);
				startActivity(intent);
			}
		});

		// Bouton d'intéraction Action
		Button actionButton = (Button) findViewById(R.id.action);
		actionButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(StatsTeam.this, Action.class);
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
					StatsTeam.this.dem.setText(R.string.stoper);
					StatsTeam.this.tempsTotal = 0;
					StatsTeam.this.tempsDepart = System.currentTimeMillis();
					StatsTeam.this.dec.setTextColor(Color.parseColor("#888888"));
					cont = true;
					break;
				case 'a':
					etat = 'r';
					StatsTeam.this.dem.setText(R.string.redemarrer);
					cont = false;
					StatsTeam.this.tempsTotal += tempsPasse;
					StatsTeam.this.dec.setTextColor(Color.parseColor("#888888"));
					break;
				case 'r':
					etat = 'a';
					StatsTeam.this.dem.setText(R.string.stoper);
					StatsTeam.this.tempsDepart = System.currentTimeMillis();
					cont = true;
					StatsTeam.this.dec.setTextColor(Color.parseColor("#888888"));
				}

			}
		});
		
		reinit.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				StatsTeam.this.cont = false;
				StatsTeam.this.dem.setText(R.string.pause_play);
				StatsTeam.this.dec.setTextColor(Color.parseColor("#888888"));
				StatsTeam.this.etat = 'd';
				StatsTeam.this.dec.setText("00:00:00");
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

				StatsTeam.this.runOnUiThread(new Runnable() {
					public void run() {
						if (cont) {
							StatsTeam.this.tempsActuel = System.currentTimeMillis();
							StatsTeam.this.tempsPasse = StatsTeam.this.tempsActuel
									- StatsTeam.this.tempsDepart;
							StatsTeam.this.cal.setTimeInMillis(tempsTotal
									+ tempsPasse);
							StatsTeam.this.dec.setText(StatsTeam.format(StatsTeam.this.cal.get(Calendar.MINUTE))
									+ ":"
									+ StatsTeam.format(StatsTeam.this.cal.get(Calendar.SECOND))
									+ ":"
									+ StatsTeam.format((int) StatsTeam.this.cal.get(Calendar.MILLISECOND) / 10));
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
