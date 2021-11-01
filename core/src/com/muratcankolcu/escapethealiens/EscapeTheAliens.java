package com.muratcankolcu.escapethealiens;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

public class EscapeTheAliens extends ApplicationAdapter {

	SpriteBatch batch;
	Texture background;
	Texture ship;
	float shipX;
	float shipY;
	int gameState=0;
	float velocity=0;
	float gravity=0.4f;
	Texture alien1;
	Texture alien2;
	Texture alien3;
	int numberOfAliens=4;
	float[] alienX=new float[numberOfAliens];
	float[] alienOffSet=new float[numberOfAliens];
	float[] alienOffSet2=new float[numberOfAliens];
	float[] alienOffSet3=new float[numberOfAliens];
	float distance=0;
	float alienVelocity=2;
	Random random;
	Circle shipCircle;
	//ShapeRenderer shapeRenderer;
	Circle[] alienCircles;
	Circle[] alienCircles2;
	Circle[] alienCircles3;
	int score=0;
	int scoredAlien=0;
	BitmapFont font;
	BitmapFont font2;

	@Override
	public void create () {

		batch=new SpriteBatch();
		background=new Texture("full-background.png");
		ship=new Texture("airship.png");
		alien1=new Texture("ufo_enemy_game_character.png");
		alien2=new Texture("ufo_enemy_game_character.png");
		alien3=new Texture("ufo_enemy_game_character.png");
		distance=Gdx.graphics.getWidth()/2;
		random=new Random();
		shipX=Gdx.graphics.getWidth()/5-ship.getHeight()/2;
		shipY=Gdx.graphics.getHeight()/3;
		//shapeRenderer=new ShapeRenderer();

		shipCircle=new Circle();
		alienCircles=new Circle[numberOfAliens];
		alienCircles2=new Circle[numberOfAliens];
		alienCircles3=new Circle[numberOfAliens];

		font=new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(4);
		font2=new BitmapFont();
		font.setColor(Color.WHITE);
		font2.getData().setScale(6);

		for(int i=0;i<numberOfAliens;i++){

			alienOffSet[i]=(random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-200);
			alienOffSet2[i]=(random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-200);
			alienOffSet3[i]=(random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-200);

			alienX[i]=Gdx.graphics.getWidth()-alien1.getHeight()/2+i*distance;

			alienCircles[i]=new Circle();
			alienCircles2[i]=new Circle();
			alienCircles3[i]=new Circle();
		}
	}

	@Override
	public void render () {

		batch.begin();
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		if (gameState == 1) {

			if(alienX[scoredAlien]<Gdx.graphics.getWidth()/2-ship.getHeight()/2) {

				score++;

				if(scoredAlien<numberOfAliens-1){

					scoredAlien++;
				}
				else{

					scoredAlien=0;
				}
			}

			if (Gdx.input.justTouched()) {

				velocity = -9;
			}

			for (int i = 0; i < numberOfAliens; i++) {

				if (alienX[i] < Gdx.graphics.getWidth() / 15) {

					alienX[i] = alienX[i] + numberOfAliens * distance;

					alienOffSet[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					alienOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					alienOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
				} else {

					alienX[i] = alienX[i] - alienVelocity*4;
				}

				batch.draw(alien1, alienX[i], Gdx.graphics.getHeight() / 2 + alienOffSet[i], Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 10);
				batch.draw(alien2, alienX[i], Gdx.graphics.getHeight() / 2 + alienOffSet2[i], Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 10);
				batch.draw(alien3, alienX[i], Gdx.graphics.getHeight() / 2 + alienOffSet3[i], Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight() / 10);
				alienCircles[i] = new Circle(alienX[i] + Gdx.graphics.getWidth() / 30, Gdx.graphics.getHeight() / 2 + alienOffSet[i] + Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth() / 30);
				alienCircles2[i] = new Circle(alienX[i] + Gdx.graphics.getWidth() / 30, Gdx.graphics.getHeight() / 2 + alienOffSet[i] + Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth() / 30);
				alienCircles3[i] = new Circle(alienX[i] + Gdx.graphics.getWidth() / 30, Gdx.graphics.getHeight() / 2 + alienOffSet[i] + Gdx.graphics.getHeight() / 20, Gdx.graphics.getWidth() / 30);
			}

			if(shipY>0){

				velocity=velocity+gravity;
				shipY=shipY-velocity;
			}

			else{

				gameState=2;
			}
		}
		else if(gameState==0){

			if(Gdx.input.justTouched()){

				gameState=1;
			}
		}

		else if(gameState==2){

			font2.draw(batch,"Game Over! Tap To Play",100,Gdx.graphics.getHeight()/2);

			if(Gdx.input.justTouched()){

				gameState=1;
				shipY=Gdx.graphics.getHeight()/3;
				for(int i=0;i<numberOfAliens;i++){

					alienOffSet[i]=(random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-200);
					alienOffSet2[i]=(random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-200);
					alienOffSet3[i]=(random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-200);

					alienX[i]=Gdx.graphics.getWidth()-alien1.getHeight()/2+i*distance;

					alienCircles[i]=new Circle();
					alienCircles2[i]=new Circle();
					alienCircles3[i]=new Circle();
				}

				velocity=0;
				scoredAlien=0;
				score=0;
			}
		}

		batch.draw(ship,shipX,shipY,Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/10);
		font.draw(batch,String.valueOf(score),100,200);
		batch.end();
		shipCircle.set(shipX+Gdx.graphics.getWidth()/30,shipY+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30);
		//shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		//shapeRenderer.setColor(Color.BLACK);
		//shapeRenderer.circle(shipCircle.x,shipCircle.y,shipCircle.radius);

		for(int i=0;i<numberOfAliens;i++){

			//shapeRenderer.circle(alienX[i]+Gdx.graphics.getWidth()/30,Gdx.graphics.getHeight()/2+alienOffSet[i]+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30);
			//shapeRenderer.circle(alienX[i]+Gdx.graphics.getWidth()/30,Gdx.graphics.getHeight()/2+alienOffSet2[i]+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30);
			//shapeRenderer.circle(alienX[i]+Gdx.graphics.getWidth()/30,Gdx.graphics.getHeight()/2+alienOffSet3[i]+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30);

			if(Intersector.overlaps(shipCircle,alienCircles[i]) || Intersector.overlaps(shipCircle,alienCircles2[i]) || Intersector.overlaps(shipCircle,alienCircles3[3])){

				gameState=2;
			}
		}

		//shapeRenderer.end();
	}
	
	@Override
	public void dispose () {

	}
}
