package com.packtpub.libgdx.CanyonBunny.Game.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.packtpub.libgdx.CanyonBunny.Game.Assets;
import com.packtpub.libgdx.CanyonBunny.Util.AudioManager;
import com.packtpub.libgdx.CanyonBunny.Util.CharactorSkin;
import com.packtpub.libgdx.CanyonBunny.Util.Constants;
import com.packtpub.libgdx.CanyonBunny.Util.GamePreferences;

public class BunnyHead extends AbstractGameObject {
	public static final String TAG = BunnyHead.class.getName();
	private final float JUMP_TIME_MAX = 0.3f;
	private final float JUMP_TIME_MIN = 0.1f;
	private final float JUMP_TIME_OFFSET_FLYING = JUMP_TIME_MAX - 0.018f;
	
	public enum VIEW_DIRECTION {
		LEFT, RIGHT
	}
	public enum JUMP_STATE {
		GROUNDED, FALLING, JUMP_RISING, JUMP_FALLING
	}
	
	private TextureRegion regHead;
	public VIEW_DIRECTION viewDirection;
	public float timeJumping;
	public JUMP_STATE jumpState;
	public boolean hasFeatherPowerup;
	public float timeLeftFeatherPowerup;
	public ParticleEffect dustParticles = new ParticleEffect();
	
	public BunnyHead() {
		init();
	}
	
	private void init() {
		dimension.set(1, 1);
		regHead = Assets.instance.bunny.head;
		// center image on game object 
		origin.set(dimension.x/2, dimension.y/2);
		// bounding box for collision detection
		bounds.set(0, 0, dimension.x, dimension.y);
		// set physics values
		terminalVelocity.set(3.0f, 4.0f);
		friction.set(12.0f, 0);
		acceleration.set(0.0f, 25.0f);
		// view direction
		viewDirection = VIEW_DIRECTION.RIGHT;
		// jump state
		jumpState = JUMP_STATE.FALLING;
		timeJumping = 0;
		// power-ups
		hasFeatherPowerup = false;
		timeLeftFeatherPowerup = 0;
		// particles
		dustParticles.load(Gdx.files.internal("particles/dust.pfx"), 
				Gdx.files.internal("particles"));
	}
	
	public void setJumping(boolean jumpKeyPressed) {
        switch (jumpState) {
        case GROUNDED: // Character is standing on a platform
            if (jumpKeyPressed) {
            	AudioManager.instance.play(Assets.instance.sounds.jump);
                // Start counting jump time from the beginning
                timeJumping = 0;
                jumpState = JUMP_STATE.JUMP_RISING;
            }
            break;
        case JUMP_RISING: // Rising in the air
            if (!jumpKeyPressed)
                jumpState = JUMP_STATE.JUMP_FALLING;
            break;
        case FALLING:// Falling down
        case JUMP_FALLING: // Falling down after jump
            if (jumpKeyPressed && hasFeatherPowerup) {
            	AudioManager.instance.play(Assets.instance.sounds.jumpWithFeather);
                timeJumping = JUMP_TIME_OFFSET_FLYING;
                jumpState = JUMP_STATE.JUMP_RISING;
            }
            break;
        }
    }

	public void setFeatherPowerup(boolean pickedUp) {
        hasFeatherPowerup = pickedUp;
        if (pickedUp) {
            timeLeftFeatherPowerup = Constants.ITEM_FEATHER_POWERUP_DURATION;
        }
    }

    public boolean hasFeatherPowerup() {
        return hasFeatherPowerup && timeLeftFeatherPowerup > 0;
    }

    @Override
    public void update(double deltaTime) {
    	super.update(deltaTime);
    	if(velocity.x != 0) {
    		viewDirection = velocity.x < 0 ? VIEW_DIRECTION.LEFT : VIEW_DIRECTION.RIGHT;
    	}
    	if(timeLeftFeatherPowerup > 0) {
    		timeLeftFeatherPowerup -= deltaTime;
    		if(timeLeftFeatherPowerup < 0) {
    			// disable power up
    			timeLeftFeatherPowerup = 0;
    			setFeatherPowerup(false);
    		}
    	}
    	dustParticles.update((float)deltaTime);
    }
    
    @Override
    protected void updateMotionY(double deltaTime) {
        switch (jumpState) {
        case GROUNDED:
            jumpState = JUMP_STATE.FALLING;
            if(velocity.x != 0) {
            	dustParticles.setPosition(position.x+dimension.x/2, position.y);
            	dustParticles.start();
            }
            break;
        case JUMP_RISING:
            // Keep track of jump time
            timeJumping += deltaTime;
            // Jump time left?
            if (timeJumping <= JUMP_TIME_MAX) {
                // Still jumping
                velocity.y = terminalVelocity.y;
            }
            break;
        case FALLING:
        case JUMP_FALLING:
            // Add delta times to track jump time
            timeJumping += deltaTime;
            // Jump to minimal height if jump key was pressed too short
            if (timeJumping > 0 && timeJumping >= JUMP_TIME_MIN) {
                // Still falling
                velocity.y = -terminalVelocity.y;
            }
        }
        if (jumpState != JUMP_STATE.GROUNDED) {
        	dustParticles.allowCompletion();
            super.updateMotionY(deltaTime);
        }
    }

	@Override
	public void render(SpriteBatch batch) {
		TextureRegion reg = null;
		// Draw particles
		dustParticles.draw(batch);
		// Apply Skin Color
		batch.setColor(CharactorSkin.values()[GamePreferences.instance.charSkin].getColor());
		// set special color when game object has feather power-up
		if(hasFeatherPowerup)
			batch.setColor(1.0f, 0.8f, 0, 1);
		// draw image
		reg = regHead;
		batch.draw(reg.getTexture(), position.x, position.y, origin.x, origin.y,
				dimension.x, dimension.y, scale.x, scale.y, rotation, 
				reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(), 
				viewDirection == VIEW_DIRECTION.LEFT, false);
		// reset color to white
		batch.setColor(1, 1, 1 ,1);
	}

}
