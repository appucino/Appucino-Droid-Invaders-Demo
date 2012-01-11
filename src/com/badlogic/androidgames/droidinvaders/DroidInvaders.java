package com.badlogic.androidgames.droidinvaders;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

import com.appucino.Appucino;
import com.appucino.AppucinoConstants;
import com.appucino.AppucinoDelegate;
import com.badlogic.androidgames.framework.Screen;
import com.badlogic.androidgames.framework.impl.GLGame;

public class DroidInvaders extends GLGame {
	boolean firstTimeCreate = true;

	@Override
	public Screen getStartScreen() {
		return new MainMenuScreen(this);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		super.onSurfaceCreated(gl, config);
		if (firstTimeCreate) {
			Settings.load(getFileIO());
			Assets.load(this);
			firstTimeCreate = false;
		} else {
			Assets.reload();
		}
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//Required for Map based leaderboard to work - Refer Technical Notes
		Appucino.getInstance().setMapAPIKey("0oGogwgYroGQHIrbA4ul6OIHQ4L-2NgJ0iSuNkQ");
		
		//change the last parameter to AppucinoConstants.APPMODE_LIVE when making live.
		Appucino.getInstance().init("1",this,AppucinoConstants.APPMODE_LIVE);
		
		LinearLayout rl = new LinearLayout(this);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT);
		rl.setGravity(Gravity.BOTTOM);
	
		addContentView(rl,params);
		Appucino.getInstance().addAppucinoBar(rl);
		
		Appucino.getInstance().setDelegate(new AppucinoDelegate(){
			@Override
			public void startChallenge(final long score, final String mode_id) {
				runOnUiThread(new Runnable() {
				    public void run() {
				    	
				    	//Show Toast
				    	if(!Appucino.getInstance().getChallengeScoreObject().score.equalsIgnoreCase(""))
     					{
     						Toast.makeText(DroidInvaders.this,"Your score to beat is "+score,Toast.LENGTH_LONG).show();
     					}
				    	else
				    	{
				    		Toast.makeText(DroidInvaders.this,"Set a score to capture "+Appucino.getInstance().getChallengeScoreObject().place_name,Toast.LENGTH_LONG).show();
				    	}
				    	try {
						} catch (Exception e) {
						}
						
						// Start the game 
						setScreen(new GameScreen(DroidInvaders.this));
				    }
				    });
				
				
			}
		}
		);
		
		
	}
	
	
	//Helper Methods to do appucino calls from internal files
	public void showAppucinoBar()
	{
		runOnUiThread(new Runnable() {
		    public void run() {
		    	
		    	Appucino.getInstance().showAppucinoBar();
		    	
		    }});
	}
	public void hideAppucinoBar()
	{
		runOnUiThread(new Runnable() {
		    public void run() {
		    	
		    	Appucino.getInstance().hideAppucinoBar();
		    	
		    }});
	}
	public void updateScoreAppucino(Long score,String mode)
	{
		Appucino.getInstance().updateScore(score, mode);
	}
	public void doScoreCheckin() {
		// TODO Auto-generated method stub
		Appucino.getInstance().doScoreCheckin();
	}
	
	@Override
	public void onPause() {
		super.onPause();
		if (Settings.soundEnabled)
			Assets.music.pause();
	}

	
}