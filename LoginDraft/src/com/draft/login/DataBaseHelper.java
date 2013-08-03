package com.draft.login;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelper extends SQLiteOpenHelper
{
	public DataBaseHelper(Context context, String name,CursorFactory factory, int version) 
    {
	           super(context, name, factory, version);
	}
	//Appelé quand aucune base de données existe dans le disque et a besoin la classe d'assistance
	// Pour en créer un nouveau.
	@Override
	public void onCreate(SQLiteDatabase _db) 
	{
			_db.execSQL(LoginDataBaseAdapter.DATABASE_CREATE);
			
	}
	// Appelée quand il existe une ancienne version de la base de données
	// La base de données sur le disque doit être mis à niveau vers la version actuelle.
	@Override
	public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion) 
	{
			// Connectez-vous à la version de mise à niveau.
			Log.w("TaskDBAdapter", "Upgrading from version " +_oldVersion + " to " +_newVersion + ", which will destroy all old data");
	
	
			// Mise à jour de la base de données existante pour se conformer à la nouvelle version. multiple
			// les versions antérieures peuvent être manipulés en comparant l'ancienne version et la nouvelle version
			//Le cas le plus simple est de laisser tomber la vieille table et en créer un nouveau.
			_db.execSQL("DROP TABLE IF EXISTS " + "TEMPLATE");
			// Créer une nouvelle.
			onCreate(_db);
	}
	

}
