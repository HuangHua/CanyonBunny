package com.packtpub.libgdx.CanyonBunny.Game.Objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class AbstractGameObject {
	public Vector2 position;
	public Vector2 dimension;
	public Vector2 origin;
	public Vector2 scale;
	public float rotation;
	public Vector2 velocity;
	public Vector2 terminalVelocity;
	public Vector2 friction;
	public Vector2 acceleration;
	public Rectangle bounds;
	
	public AbstractGameObject() {
		position = new Vector2();
		dimension = new Vector2(1, 1);
		origin = new Vector2();
		scale = new Vector2(1, 1);
		rotation = 0;
		velocity = new Vector2();
		terminalVelocity = new Vector2();
		friction = new Vector2();
		acceleration = new Vector2();
		bounds = new Rectangle();
	}
	
	public void update(double deltaTime) {
		updateMotionX(deltaTime);
		updateMotionY(deltaTime);
		// move to new position
		position.x += velocity.x * deltaTime;
		position.y += velocity.y * deltaTime;
	}
	
	public abstract void render(SpriteBatch batch);
	
	protected void updateMotionX(double deltaTime) {
		if(velocity.x != 0) {
			// apply friction
			if(velocity.x > 0) {
				velocity.x = Math.max(velocity.x - friction.x*(float)deltaTime, 0);
			} else {
				velocity.x = Math.min(velocity.x + friction.x*(float)deltaTime, 0);
			}
		}
		// apply acceleration
		velocity.x += acceleration.x*deltaTime;
		// make sure the object's velocity does not exceed the positive
		// or negative terminal velocity
		velocity.x = MathUtils.clamp(velocity.x, -terminalVelocity.x, terminalVelocity.x);
	}
	
	protected void updateMotionY(double deltaTime) {
		if(velocity.y != 0) {
			// apply friction
			if(velocity.y > 0) {
				velocity.y = Math.max(velocity.y - friction.y*(float)deltaTime, 0);
			} else {
				velocity.y = Math.min(velocity.y + friction.y*(float)deltaTime, 0);
			}
		}
		// apply acceleration
		velocity.y += acceleration.y*deltaTime;
		// make sure the object's velocity does not exceed the positive
		// or negative terminal velocity
		velocity.y = MathUtils.clamp(velocity.y, -terminalVelocity.y, terminalVelocity.y);
	}
}
