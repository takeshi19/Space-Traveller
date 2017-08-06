package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.math.Rectangle;

public class spaceTraveller extends ApplicationAdapter {
	SpriteBatch batch;
	Sprite fighter;
	Texture img;
    OrthographicCamera camera;
    ExtendViewport viewport;
    Vector3 touchPos;
    Rectangle airspace, scissors;
    boolean isAiming = false;
    float width = 0f;
    float height = 0f;

    /**
	 *Creates the sprites and textures from our assets folder.
     *Loads, creates, then draws.
	 */
	@Override
	public void create () {
	    camera = new OrthographicCamera();
	    viewport = new ExtendViewport(1800, 1500, camera);
        batch = new SpriteBatch();
        img = new Texture("sprites.png");
        touchPos = new Vector3();
        fighter = new Sprite(img);
        scissors = new Rectangle();
        airspace = new Rectangle(10, 22, 22, 22);
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        fighter.setPosition(width, height/2);
        fighter.setRotation(90);    //Default position that our fighter jet is pointing in.
	}

	/**
     *This method draws the sprite (a drone stealth jet) to the screen.
	 */
	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
        ScissorStack.calculateScissors(camera, batch.getTransformMatrix(), airspace, scissors);
        ScissorStack.pushScissors(scissors);
        fighter.draw(batch);
        batch.flush();
        ScissorStack.popScissors();
		batch.end();
		rangeOfMvmt();
	}

    /**
     * Defines where and how the fighter can move, based on the user touching the screen.
     * User is limited to moving the fighter only horizontally, starting from its center starting point
     * at the bottom of the screen.
     */
    public void rangeOfMvmt(){
        if(Gdx.input.isTouched()){      //When user touches screen, send fighter to the touch location.
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos); //Translate real-world coordinates to cam's coordinate system.
            fighter.setPosition(touchPos.x, touchPos.y);
        }
    }

    public boolean aim() {   //OFR = Open Firing Range. ROM = Range of Movement (aka airspace)
        //if(touchPos.get() == enemy.getPosition() || touchPos.get() == anyLocOfOFR){
            //fighter.setRotation(touchPos.getX(), touchPos.getY());
            //isAiming = true;
        // }

        //else if(touchPos.get() == airspace){ //we don't want to confuse mvmt of ship with the firing controls.
            //isAiming = false;
        //}
        //return isAiming;
        return false;
    }

	@Override
    /**
     The sprite batch now uses our OrthographicCamera and the viewport when the screen is resized.
     */
    public void resize(int width, int height){
	    viewport.update(width, height, true);
	    batch.setProjectionMatrix(camera.combined);
    }

	@Override
	public void dispose () {
	    batch.dispose();
	    img.dispose();
    }
}