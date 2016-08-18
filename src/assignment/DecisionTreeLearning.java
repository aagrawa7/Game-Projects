package assignment;

import java.util.Random;

import processing.core.*;
import processing.data.Table;
import processing.data.TableRow;

public class DecisionTreeLearning extends PApplet {

	float xpos = 800f, ypos = 600f;
	float vxpos = 50f, vypos = 50f;
	Wandering wander = new Wandering();
	KinematicArrive karrive = new KinematicArrive();
	VillanKinematicArrive vkarrive = new VillanKinematicArrive();
	VillanWandering vwander = new VillanWandering();
	int acb = 0;
	int vacb = 0;
	float ofinal;
	float vofinal;
	float mox = 300, moy = 300;
	float vmox = 300, vmoy = 300;
	Table table;
	PVector location = new PVector(xpos, ypos);
	PVector vlocation = new PVector(vxpos, vypos);
	int logcount =1;
	int logcount1 =1;
	int logcount2 =1;
	int logcount3=1;
	int logcountx=1;
	 int countTerrain=0,countRemain=0,countRighteye=0,countGenerate=0,countLefteye=0;
	public void settings() {
		fullScreen();
		//table = loadTable("LogFile.csv", "header");
		table = loadTable("LogFile1.csv", "header");
		String[][] treeformed = new String[5][6];
		for (TableRow row : table.rows()) {
			  String area1 = row.getString("area");
			  String type1 = row.getString("Charactertpe");
			  float dist1 = row.getFloat("dist");
			  float vredvel1 = row.getFloat("ene_speed");
			  float redvel1 = row.getFloat("hero_speed");
			  String mode1 = row.getString("ene_mode");
			  String success1 = row.getString("ene_success");
			  
			 
				if(area1=="terrain" && type1=="enemy" && dist1<=400 && vredvel1==3.0f && success1=="seek"){
					countTerrain++;
				}
				else if(area1=="generate" && type1=="enemy" && dist1<=400 && vredvel1==3.0f && success1=="seek"){
					countGenerate++;
				}
				else if(area1=="lefteye" && type1=="enemy" && dist1<=400 && vredvel1==3.0f && success1=="seek"){
					countLefteye++;
				}
				else if(area1=="righteye" && type1=="character" && (dist1<=400||dist1<=300) && vredvel1==3.0f && success1=="seek"){
					countRighteye++;
				}
				else{
					countRemain++;
				}
			  
			}
	}

	int timer = 1;
	String mode = "wander";
	String success = "false";
	boolean infected = false;
	float vredvel = 3;
	float redvel = 2;
	String area = "terrain";
	String type = "enemy";
	boolean vre = false;
	float probfac = 0.4f;
	

	public void generatelog() {
		// TODO Auto-generated method stub
		TableRow newRow = table.addRow();
		//newRow.setFloat("timestamp", System.currentTimeMillis());
		newRow.setString("area", area);
		newRow.setString("Charactertpe", type);
		/*newRow.setFloat("hero_x", (float) wander.character.getx());
		newRow.setFloat("hero_y", (float) wander.character.gety());
		newRow.setFloat("ene_x", vxpos);
		newRow.setFloat("ene_y", vypos);
		*/newRow.setFloat("dist", dist((float) vwander.character.getx(), (float) vwander.character.gety(), vxpos, vypos));
		newRow.setFloat("ene_speed", vredvel);
		newRow.setFloat("hero_speed", redvel);
		newRow.setString("ene_mode", mode);
		newRow.setString("ene_success", success);

		saveTable(table, "data/LogFile1.csv");
	}

	public float distvh() {
		// TODO Auto-generated method stub
		return dist((float) vwander.character.getx(), (float) vwander.character.gety(), (float) wander.character.getx(),
				(float) wander.character.gety());
	}

	// wander.align.targetRotation;

	public void drawObstacles() {
		// TODO Auto-generated method stub
		// obstacle 1 left eye
		fill(255, 67, 70);
		rect(2 * width / 10, 1 * height / 7, 4 * width / 10 - 2 * width / 10, 2f * height / 7 - 1 * height / 7, 10);
		fill(255, 8, 8);
		rect(6 * width / 10, 1 * height / 7, 8 * width / 10 - 6 * width / 10, 2f * height / 7 - 1 * height / 7, 10);
		fill(8, 17, 250);
		rect(4.5f * width / 10, 3 * height / 7, 5.5f * width / 10 - 4.5f * width / 10,
				4.5f * height / 7 - 3 * height / 7, 10);
		fill(22, 250, 8);
		rect(2 * width / 10, 5 * height / 7, 8 * width / 10 - 2 * width / 10, 6f * height / 7 - 5 * height / 7, 10);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PApplet.main(new String[] { "--present", "assignment.DecisionTreeLearning" });
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
			velocity.limit(redvel);
			location.add(velocity);

		}

		public void getNewOrientation(PVector location2) {
			// TODO Auto-generated method stub
			if ((location2.y != location.y) && (location2.x != location.x))
				ofinal = PApplet.atan2((float) (location2.y - location.y), (float) (location2.x - location.x));

		}

		public void getNewOrientation1(PVector location2) {
			// TODO Auto-generated method stub
			ofinal = PApplet.atan2((float) (location2.y - location.y), (float) (location2.x - location.x));
			// z = 0;
		}

		void checkEdges() {
			if ((Math.abs(target.x) <= radiusofsat && Math.abs(target.y) <= radiusofsat)) {
				velocity.x = 0;
				velocity.y = 0;

			}

		}

		void checkEdges1() {
			if ((Math.abs(target.x) <= radiusofsat && Math.abs(target.y) <= radiusofsat)) {

				velocity.x = 0;
				velocity.y = 0;

				// z = 0;
			}
			if (Math.abs(target.x) <= radiusofdecel && Math.abs(target.y) <= radiusofdecel
					&& Math.abs(target.x) > radiusofsat && Math.abs(target.y) > radiusofsat) {

				target.sub(150, 150);

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
				velocity.mult(redvel);

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
			location.x = 800;

		}

		void sety() {
			location.y = 600;
		}

	}
	public void draw() {
		background(200, 190, 90);
		drawObstacles();
		if ((vxpos >= 2 * width / 10 && vypos >= 1 * height / 7 && vxpos < 4 * width / 10 && vypos < 2 * height / 7)
				&& vre == false) {
			// reduce speed of the villan

			if (random(0, 1) <= probfac) {
				if (vredvel > 1)
					vredvel = (float) (vredvel - 0.7);
				vre = true;
				area = "lefteye";
				type = "enemy";
				if(logcount==1){
				generatelog();
				}
				logcount++;
			}
			
		}else{
			logcount =1;
			
		}

		if (vxpos >= 2 * width / 10 && vypos >= 5 * height / 7 && vxpos < 8 * width / 10 && vypos >= 6 * height / 7) {
			if (vredvel < 4) {
				vredvel = (float) (vredvel + 0.06);
				area = "generate";
				
				type = "enemy";
				if(logcount3==1){
					generatelog();
					}
					logcount3++;

			}
			vre = false;
		}
		else{
			logcount3 =1;
		}
		if (xpos >= 2 * width / 10 && ypos >= 5 * height / 7 && xpos < 8 * width / 10 && ypos >= 6 * height / 7) {
			if (redvel < 3.5) {
				redvel = (float) (redvel + 0.09);
				area = "generate";
				type = "hero";
				
				if(logcount1==1){
					generatelog();
					}
					logcount1++;
			}
			vre = false;
		}else{
			logcount1=1;
		}
		if ((xpos >= 6 * width / 10 && ypos >= 1 * height / 7 && xpos < 8 * width / 10 && ypos < 2 * height / 7)
				|| infected == true) {
			// suicide
			// seek enemy
			infected = true;
			mox = (float) vwander.character.getx();
			moy = (float) vwander.character.gety();
			karrive.draw();
			vre = false;
			vmox = (float) wander.character.getx();
			vmoy = (float) wander.character.gety();
			vkarrive.draw();
			area = "righteye";
			type = "hero";
			mode = "suicide";
			if(logcountx==1){
				generatelog();
				}
				logcountx++;
			if (distvh() < 20) {
				infected = false;
				area = "righteye";
				type = "hero";
				mode = "suicide";
				success = "true";
				if(logcount2==1){
					generatelog();
					}
					logcount2++;
				vwander.character.setx();
				vwander.character.sety();
				vre = false;
				wander.character.setx();
				wander.character.sety();
				redvel = 2;
				vredvel = 3;
				area = "terrain";
				type = "enemy";
				mode = "wander";
				success = "false";

			}
		} else if (distvh() < 300) {
			// closest();
			mox = (float) (5f * width / 10);
			moy = (float) (3.75f * height / 7);
			karrive.draw();vre = false;
			if ((float) wander.character.getx() > (4.5f * width / 10)
					&& (float) wander.character.getx() <= (5.5f * width / 10)
					&& ((float) wander.character.gety() <= (3.75f * height / 7)
							&& (float) wander.character.gety() > (3f * height / 7))) {
				vwander.draw();
				area = "terrain";
				type = "enemy";
				mode = "wander";
				success = "false";
				
				if(logcount2==1){
					generatelog();
					}
					logcount2++;
			} else {
				vmox = (float) wander.character.getx();
				vmoy = (float) wander.character.gety();
				vkarrive.draw();
				area = "terrain";
				type = "enemy";
				mode = "seek";
				success = "false";
				vre = false;
				if(logcount2==1){
					generatelog();
					}
					logcount2++;
				if (distvh() < 20) {
					area = "terrain";
					type = "enemy";
					mode = "seek";
					success = "true";
					if(logcount2==1){
						generatelog();
						}
						logcount2++;
					redvel = 2;
					vredvel = 3;
					vwander.character.setx();
					vwander.character.sety();
					vre = false;
					wander.character.setx();
					wander.character.sety();
					area = "terrain";
					type = "enemy";
					mode = "wander";
					success = "false";
				}
			}
		} else {
			logcountx=1;
			logcount2 =1;
			wander.draw();
			vwander.draw();
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
			arrow.disableStyle();
			fill(0, 102, 152);

			shapeMode(CENTER);
			character.update();
			character.checkEdges();
			pushMatrix();
			translate((float) character.getx(), (float) character.gety());

			rotate(ofinal);
			translate(-(float) character.getx(), -(float) character.gety());

			shape(arrow, (float) character.getx(), (float) character.gety(), character.getlength(),
					character.getheight());
			popMatrix();
			// initsarrive = 1;
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
			arrow.disableStyle();
			fill(0, 102, 152);
			shapeMode(CENTER);
			align.update();
			character.checkEdges2();
			pushMatrix();
			translate((float) character.getx(), (float) character.gety());

			rotate(character.orientation1);
			translate(-(float) character.getx(), -(float) character.gety());

			shape(arrow, (float) character.getx(), (float) character.gety(), character.getlength(),
					character.getheight());
			popMatrix();
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

				if (acb == 0) {
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

	/// ----------------------
	class Villan {

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

		Villan(float speedx, float speedy, int length_, int height_) {
			velocity = new PVector(speedx, speedy);
			length = length_;
			Heightt = height_;
			target = new PVector(0, 0);
			direction = new PVector();
		}

		void update() {

			mouse = new PVector(vmox, vmoy);

			target.x = mouse.x - vlocation.x;
			target.y = mouse.y - vlocation.y;

			// location.add(mouse);
			getNewOrientation(mouse);

			direction = target.copy();

			velocity.add(direction);
			velocity.limit(vredvel);
			vlocation.add(velocity);

		}

		public void getNewOrientation(PVector location2) {
			// TODO Auto-generated method stub
			if ((location2.y != vlocation.y) && (location2.x != vlocation.x))
				vofinal = PApplet.atan2((float) (location2.y - vlocation.y), (float) (location2.x - vlocation.x));

		}

		public void getNewOrientation1(PVector location2) {
			// TODO Auto-generated method stub
			vofinal = PApplet.atan2((float) (location2.y - vlocation.y), (float) (location2.x - vlocation.x));
			// z = 0;
		}

		void checkEdges() {
			if ((Math.abs(target.x) <= radiusofsat && Math.abs(target.y) <= radiusofsat)) {
				velocity.x = 0;
				velocity.y = 0;

			}

		}

		public void checkEdges2() {

			if (vlocation.x > width) {
				vlocation.x = 0;
			}
			if (vlocation.y > height)
				vlocation.y = 0;
			if (vlocation.x < 0)
				vlocation.x = width;
			if (vlocation.y < 0) {
				vlocation.y = height;
			}
		}

		public void updateLocation(SteeringOutput steering) {

			if (steering != null) {

				vlocation.add(velocity);
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
				velocity.mult(vredvel);

			}

		}

		public SteeringOutput getsteering() {
			SteeringOutput steer = new SteeringOutput();
			float targetSpeed;
			PVector targetVelocity;
			PVector direction = new PVector(target.x - vlocation.x, target.y - vlocation.y);
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
			return vlocation.x;

		}

		double gety() {
			return vlocation.y;

		}

		void setx() {
			vlocation.x = 50;

		}

		void sety() {
			vlocation.y = 50;
		}

	}

	public class VillanSteeringOutput {
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

	class VillanKinematicArrive {
		PShape arrow;
		Villan character;
		float mx = 600;
		float my = 600;

		float ocurrent = 0;
		float ostart = 0;
		// int z = 1;

		VillanKinematicArrive() {
			character = new Villan(0, 0, 45, 30);
		}

		public void draw() {

			smooth();
			// background(20, 139, 149);

			arrow = loadShape("abc.svg");
			shapeMode(CENTER);
			character.update();
			character.checkEdges();
			pushMatrix();
			translate((float) character.getx(), (float) character.gety());

			rotate(vofinal);
			translate(-(float) character.getx(), -(float) character.gety());

			shape(arrow, (float) character.getx(), (float) character.gety(), character.getlength(),
					character.getheight());
			popMatrix();
			vxpos = (float) character.getx();
			vypos = (float) character.gety();
		}

	}

	class VillanWandering {
		PShape arrow;
		Villan character;
		Align align;
		float r = 1.0f;
		int z;
		float ocurrent, ostart;

		public VillanWandering() {
			// size(width, height);
			character = new Villan(0, 0, 45, 30);
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
			pushMatrix();
			translate((float) character.getx(), (float) character.gety());

			rotate(character.orientation1);
			translate(-(float) character.getx(), -(float) character.gety());

			shape(arrow, (float) character.getx(), (float) character.gety(), character.getlength(),
					character.getheight());
			popMatrix();
			vxpos = (float) character.getx();
			vypos = (float) character.gety();

		}

		class Align {
			Villan hero;
			// PVector target;
			float maxangacceleration, maxRotation;
			float targetRadius, slowRadius;
			float timeToTarget;
			float targetRotation;
			float angularAcceleration;
			int i = 0;

			Align(Villan myhero, float maxRotate, float maxangAcc, float trgtRad, float slowRad) {
				hero = myhero;
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
				if (vacb == 0) {
					float neworient = random(-PI, PI);
					xx = neworient;
					vacb++;
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