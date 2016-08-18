package assignment;

import java.util.Random;

import processing.core.*;


public class MainApp extends PApplet {

	// Create a new variable of type MyBox
	float xpos = 50f, ypos = 50f;
	Wandering wander = new Wandering();
	KinematicArrive Karrive2 = new KinematicArrive();
	KinematicArrive Karrive1 = new KinematicArrive();
	KinematicArrive Karrive = new KinematicArrive();
	int acb = 0;
	float ofinal;
	float mox = 300, moy = 300;

	public void settings() {
		fullScreen();
	}

	Random rand = new Random();
	PVector location = new PVector(xpos, ypos);
	boolean flag = true;
	boolean karr = false;
	boolean karr1 = false;
	boolean regain = false;
	float vellim = 1;
	public void draw() {
		background(200, 190, 90);
		drawObstacles();
		
		if((xpos>=2 * width / 10 && ypos>= 1 * height / 7 && xpos< 4 * width / 10 && ypos< 2 * height / 7)||karr == true){
			flag = true;
			karr = true;
			mox = 5 * width / 10;
			moy = 5.5f * height / 7;
			vellim = 3;
			Karrive.draw();
			
			if(xpos > mox-10&&xpos <= mox+10 &&(ypos<=moy+10 && ypos>moy-10)){
				//wander.draw();
				karr = false;
				
				flag = false;
			}
		}else if((xpos>=6 * width / 10 && ypos>= 1 * height / 7 && xpos< 8 * width / 10 && ypos< 2 * height / 7 )||karr1 == true){
			flag = true;
			karr1 = true;
			mox = 5 * width / 10;
			moy = 3.75f * height / 7;
			vellim = 1;
			Karrive1.draw();
			if(xpos > mox-10&&xpos <= mox+10 &&(ypos<=moy+10 && ypos>moy-10)){
				//wander.draw();
				karr1 = false;
				regain = true;
				flag = false;
			}
		}/*else if(xpos>=4.5f * width / 10 && ypos>= 3 * height / 7 && xpos< 5.5f * width / 10 && ypos< 4.5f * height / 7&& flag ==true){
			wander.draw();}*/
		else if(regain == true){
			mox = 5 * width / 10;
			moy = 5.5f * height / 7;
			vellim = 3;
			Karrive2.draw();
			if(xpos > mox-10&&xpos <= mox+10 &&(ypos<=moy+10 && ypos>moy-10)){
				//wander.draw();
				regain = false;
				
				flag = false;
			}
			}
		else{
			wander.draw();
		}
		
		
	}
	// wander.align.targetRotation;

	public void drawObstacles() {
		// TODO Auto-generated method stub
		// obstacle 1 left eye
		
		fill(255,67,70);
		rect(2 * width / 10, 1 * height / 7, 4 * width / 10 - 2 * width / 10, 2f * height / 7 - 1 * height / 7, 10);
		
		fill(255,8,8);
		rect(6 * width / 10, 1 * height / 7, 8 * width / 10 - 6 * width / 10, 2f * height / 7 - 1 * height / 7, 10);
		fill(8,17,250);
		rect(4.5f * width / 10, 3 * height / 7, 5.5f * width / 10 - 4.5f * width / 10, 4.5f * height / 7 - 3 * height / 7, 10);
		fill(22,250,8);
		rect(2 * width / 10, 5 * height / 7, 8 * width / 10 - 2 * width / 10, 6f * height / 7 - 5 * height / 7, 10);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PApplet.main(new String[] { "--present", "assignment.MainApp" });
	}

	

	class Character {

		PVector destination1;
		PVector target;
		PVector direction;
		PVector velocity;
		PVector orientation;
		PVector rotation;
		PVector mouse;
		int length, Heightt;
		PShape arrow;
		float maxVelocity = (float) 10;
		float radiusofsat = (float) 30;
		// -steering arrive
		float timetoTarget = (float) 1;
		float acceleration = 2;
		float radiusofdecel = (float) 300;
		float orientation1 = 0;
		float rotation1 = 0;
		// --steering arrive
		PVector orVector;

		float one, two, three;

		// float maxSpeed = 2;

		Character(float speedx, float speedy, int length_, int height_) {
			velocity = new PVector(speedx, speedy);
			length = length_;
			Heightt = height_;
			target = new PVector(0, 0);
			direction = new PVector();
		}

		void update() {

			mouse = new PVector(mox, moy);

			target.x = mouse.x - location.x;
			target.y = mouse.y - location.y;

			// location.add(mouse);
			getNewOrientation(mouse);

			direction = target.copy();

			velocity.add(direction);
			velocity.limit(vellim);
			location.add(velocity);

		}

		void update1() {

			mouse = new PVector(mox, moy);

			target = mouse;

			getNewOrientation(mouse);
			SteeringOutput steer = getsteering();
			if (steer != null) {
				velocity.add(steer.linear);

				velocity.limit((float) 2);
				location.add(velocity);

			}

		}

		public void getNewOrientation(PVector location2) {
			// TODO Auto-generated method stub
			if ((location2.y != location.y) && (location2.x != location.x))
				ofinal = PApplet.atan2((float) (location2.y - location.y), (float) (location2.x - location.x));

		}

		void checkEdges() {
			if ((Math.abs(target.x) <= radiusofsat && Math.abs(target.y) <= radiusofsat)) {
				velocity.x = 0;
				velocity.y = 0;

			}

		}

		public void checkEdges2() {

			if (location.x > width) {
				location.x = 0;
			}
			if (location.y > height)
				location.y = 0;
			if (location.x < 0)
				location.x = width;
			if (location.y < 0) {
				location.y = height;
			}
		}

		public void updateLocation(SteeringOutput steering) {

			if (steering != null) {

				location.add(velocity);
				// rotation1 = rotation1 + steering.getAngular();
				orientation1 = orientation1 + steering.getAngular();
				if (steering.getAngular() > 0 && orientation1 < 0)
					orientation1 = 0;
				else if (steering.getAngular() > 0 && orientation1 < 0)
					orientation1 = 0;
				if (velocity.mag() > maxVelocity) {
					velocity.normalize();
					velocity = velocity.mult(maxVelocity);
				}
				velocity.x = (float) Math.cos(orientation1);
				velocity.y = (float) Math.sin(orientation1);
				velocity.mult(2);

			}

		}

		public SteeringOutput getsteering() {
			SteeringOutput steer = new SteeringOutput();
			float targetSpeed;
			PVector targetVelocity;
			PVector direction = new PVector(target.x - location.x, target.y - location.y);
			float distance = direction.mag();

			if (distance < radiusofsat)
				return null;

			if (distance > radiusofdecel) {
				targetSpeed = maxVelocity;
			} else {
				targetSpeed = maxVelocity * (distance / radiusofdecel);
			}

			targetVelocity = direction;
			targetVelocity.normalize();
			targetVelocity = targetVelocity.mult(targetSpeed);
			PVector temp = targetVelocity.sub(velocity);
			temp = temp.div(timetoTarget);

			if (temp.mag() > acceleration) {
				temp = temp.normalize();
				temp = temp.mult(acceleration);
			}

			steer.setLinear(temp);
			steer.setAngular(0);

			return steer;
		}

		public float getheight() {

			return Heightt;
		}

		public float getlength() {

			return length;
		}

		double getx() {
			return location.x;

		}

		double gety() {
			return location.y;

		}

		void setx() {
			location.x = xpos;

		}

		void sety() {
			location.y = ypos;
		}

	}

	public class SteeringOutput {
		PVector linear;
		float angular;

		public PVector getLinear() {
			return linear;
		}

		public void setLinear(PVector linear) {
			this.linear = linear;
		}

		public float getAngular() {
			return angular;
		}

		public void setAngular(float angular) {
			this.angular = angular;
		}
	}

	class KinematicArrive {
		PShape arrow;
		Character character;
		float mx = 600;
		float my = 600;

		float ocurrent = 0;
		float ostart = 0;
		// int z = 1;
		int n = 100;

		KinematicArrive() {
			character = new Character(0, 0, 45, 30);
		}

		public void draw() {

			smooth();
			// background(20, 139, 149);

			arrow = loadShape("abc.svg");
			shapeMode(CENTER);
			character.update();
			character.checkEdges();

			translate((float) character.getx(), (float) character.gety());

			rotate(ofinal);
			translate(-(float) character.getx(), -(float) character.gety());

			shape(arrow, (float) character.getx(), (float) character.gety(), character.getlength(),
					character.getheight());
			
			xpos = (float) character.getx();
			ypos = (float) character.gety();
		}

	}

	class Wandering {
		PShape arrow;
		Character character;
		Align align;
		float r = 1.0f;
		int z;
		float ocurrent, ostart;

		public Wandering() {
			// size(width, height);
			character = new Character(0, 0, 45, 30);
			align = new Align(character, radians(1f), radians(1f), 0.001f, 20f);

		}

		public void draw() {
			noStroke();
			/*
			 * fill(0xee, 0xee, 0xff, 5); rect(0, 0, width, height);
			 */smooth();
			// background(20, 139, 149);
			arrow = loadShape("abc.svg");

			shapeMode(CENTER);
			align.update();
			character.checkEdges2();
			translate((float) character.getx(), (float) character.gety());

			rotate(character.orientation1);
			translate(-(float) character.getx(), -(float) character.gety());

			shape(arrow, (float) character.getx(), (float) character.gety(), character.getlength(),
					character.getheight());
			xpos = (float) character.getx();
			ypos = (float) character.gety();
		
		}

		class Align {
			Character hero;
			// PVector target;
			float maxangacceleration, maxRotation;
			float targetRadius, slowRadius;
			float timeToTarget;
			float targetRotation;
			float angularAcceleration;
			int i = 0;

			Align(Character myhero, float maxRotate, float maxangAcc, float trgtRad, float slowRad) {
				hero = myhero;
				// target = trgt;
				// hero.orientation = atan2(target.y - hero.location.y, target.x
				// - hero.location.x);
				maxRotation = maxRotate;
				maxangacceleration = maxangAcc;
				targetRadius = trgtRad;
				slowRadius = slowRad;
				timeToTarget = (float) 0.1;

			}

			float xx = 0;

			public SteeringOutput getSteering() {
				SteeringOutput steering = new SteeringOutput();
				float rotationSize;
				/*
				 * if (leftcollided == true) { float neworient = PI;//
				 * random(PI/2, PI); xx = neworient; leftcollided = false; }
				 * else if (centercollided == true) { float neworient = -PI;//
				 * random(PI/2, PI); xx = neworient; centercollided = false; }
				 * else if (rightcollided == true) { float neworient = -PI;//
				 * random(PI/2, PI); xx = neworient; rightcollided = false; }
				 * else
				 */if (acb == 0) {
					float neworient = random(-PI, PI);
					xx = neworient;
					acb++;
				} else if (i > 100) {
					float neworient = random(-PI, PI);
					xx = neworient;
					i = 0;
				}

				i++;

				// Get the naive direction to the target
				float rotation = xx;// -hero.orientation;
				rotationSize = abs(rotation);
				// Check if we are there, return no steering
				if (rotationSize < targetRadius)
					return null;

				// If we are outside the slowRadius, then use maximum rotation
				if (rotationSize > slowRadius)
					targetRotation = maxRotation;
				// Otherwise calculate a scaled rotation
				else {
					targetRotation = ((maxRotation * rotationSize) / slowRadius);

				}
				// The final target rotation combines speed (already in the
				// variable) and direction
				targetRotation *= rotation / rotationSize;

				// Acceleration tries to get to the target rotation
				steering.angular = targetRotation;
				steering.angular /= timeToTarget;
				// System.out.println("calculated steering angular");
				// Check if the acceleration is too great
				angularAcceleration = abs(steering.angular);
				if (angularAcceleration > maxangacceleration) {
					steering.angular /= angularAcceleration;
					steering.angular *= maxangacceleration;
				}
				// Output the steering
				steering.setLinear(new PVector(0, 0));

				return steering;
			}

			public void update() {

				hero.updateLocation(getSteering());
			}

		}
	}
}