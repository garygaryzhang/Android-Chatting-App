package com.chatt.demo;

import android.app.Application;

import com.parse.Parse;

/**
 * The Class ChattApp is the Main Application class of this app. The onCreate
 * method of this class initializes the Parse.
 */
public class ChattApp extends Application
{

	/* (non-Javadoc)
	 * @see android.app.Application#onCreate()
	 */
	@Override
	public void onCreate()
	{
		super.onCreate();

		 Parse.initialize(this, "arsuUwe4c60DfLFyd9gDDJjnupn4zTQk1kNYIr63", "LwD7wOtD5GRC9tNHueTx7TzN54ZEj5QtgI2H6oXE");

	}
}
