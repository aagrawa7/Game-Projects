package assignment;

import java.util.ArrayList;
import java.util.Collections;

import assignment.GraphPath.Graph.Connections;
//import assignment.GraphPath.Graph.FollowPath;
import assignment.GraphPath.Graph.Node;
import assignment.GraphPath.Graph.NodeRecord;
import assignment.GraphPath.Graph.Character;
import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;

public class GraphPath extends PApplet {
	int mx = -10, my = -10;
	NodeRecord start, end;
	int hero = 1;
	ArrayList<Node> nodes = new ArrayList<Node>();
	ArrayList<Connections> connections = new ArrayList<Connections>();
	ArrayList<NodeRecord> nodeRecords = new ArrayList<NodeRecord>();
	ArrayList<Connections> Path = new ArrayList<Connections>();
	PShape arrow;
	Character character;
	PVector currentpos, nextpos;
	PVector mouse;
	int iter = 1;
	// FollowPath flpath;

	public static void main(String[] args) {
		PApplet.main(new String[] { "--present", "assignment.GraphPath" });
	}

	public void settings() {
		// fullScreen();
		size(1300, 700);

		draw1();
		character = obj.new Character(23, 41, 0, 0, 45, 30);
	}

	public void draw1() {
		int a = 1300 / 100;
		int b = 700 / 60;
		int count = 0;
		int x2 = 1300 / 20;
		int y2 = 700 / 20;
		int x = 1300;
		int y = 700;
		// 1--

		for (int i = a; i < 8 * x2; i += 10) {
			for (int j = b; j < 5 * y2; j += 10) {
				nodes.add(obj.new Node(i, j, count));
				count++;
			}
		}

		// 2
		for (int i = a; i < 2 * x2; i += 10) {
			for (int j = 7 * y2; j < 12 * y2; j += 10) {
				nodes.add(obj.new Node(i, j, count));
				count++;
			}
		}
		// 3
		for (int i = 2 * x2; i < 6 * x2; i += 10) {
			for (int j = 5 * y2; j < 12 * y2; j += 10) {
				nodes.add(obj.new Node(i, j, count));
				count++;
			}
		}
		// 4
		for (int i = 6 * x2; i < 8 * x2; i += 10) {
			for (int j = 9 * y2; j < 14 * y2; j += 10) {
				nodes.add(obj.new Node(i, j, count));
				count++;
			}
		}
		// 5
		for (int i = a; i < 8 * x2; i += 10) {
			for (int j = 14 * y2; j < y; j += 10) {
				nodes.add(obj.new Node(i, j, count));
				count++;
			}
		}
		// 6
		for (int i = 10 * x2; i < 13 * x2; i += 10) {
			for (int j = 12 * y2; j < y; j += 10) {
				nodes.add(obj.new Node(i, j, count));
				count++;
			}
		}
		// 7
		for (int i = 14 * x2; i < 18 * x2; i += 10) {
			for (int j = b; j < 2 * y2; j += 10) {
				nodes.add(obj.new Node(i, j, count));
				count++;
			}
		}
		// 8
		for (int i = 8 * x2; i < x; i += 10) {
			for (int j = 2 * y2; j < 7 * y2; j += 10) {
				nodes.add(obj.new Node(i, j, count));
				count++;
			}
		}
		// 9
		for (int i = 8 * x2; i < 10 * x2; i += 10) {
			for (int j = 7 * y2; j < 9 * y2; j += 10) {
				nodes.add(obj.new Node(i, j, count));
				count++;
			}
		}
		// 10
		for (int i = 14 * x2; i < x; i += 10) {
			for (int j = 7 * y2; j < 9 * y2; j += 10) {
				nodes.add(obj.new Node(i, j, count));
				count++;
			}
		}
		// 11
		for (int i = 8 * x2; i < x; i += 10) {
			for (int j = 9 * y2; j < 12 * y2; j += 10) {
				nodes.add(obj.new Node(i, j, count));
				count++;
			}
		}
		// 12
		for (int i = 15 * x2; i < x; i += 10) {
			for (int j = 12 * y2; j < 15 * y2; j += 10) {
				nodes.add(obj.new Node(i, j, count));
				count++;
			}
		}
		// 13
		for (int i = 15 * x2; i < 18 * x2; i += 10) {
			for (int j = 15 * y2; j < 17 * y2; j += 10) {
				nodes.add(obj.new Node(i, j, count));
				count++;
			}
		}
		// 14
		for (int i = 15 * x2; i < x; i += 10) {
			for (int j = 17 * y2; j < y; j += 10) {
				nodes.add(obj.new Node(i, j, count));
				count++;
			}
		}

		for (int i = 0; i < nodes.size(); i++) {
			nodeRecords.add(obj.new NodeRecord(nodes.get(i), null, 0, 0));
		}
		// start = nodeRecords.get(20);
		for (int i = 0; i < nodes.size(); i++) {
			for (int j = 0; j < nodes.size(); j++) {
				if (findDistance(nodes.get(i), nodes.get(j)) < 15) {
					connections.add(obj.new Connections(nodes.get(i), nodes.get(j)));
				}
			}
		}

	}

	public float findDistance(Node n1, Node n2) {
		Connections temp = obj.new Connections(n1, n2);
		return temp.getCost();
	}

	Graph obj = new Graph();
	float ofinal;
	float ocurrent = 0;
	float ostart = 0;
	int z = 1;
	int n = 100;
	float radiusofsat = (float) 1;
	float radiusofdecel = (float) 10;
	int ab = 0;

	public void draw() {
		if (z > 101)
			z = 0;
		smooth();
		background(20, 139, 149);
		arrow = loadShape("abc.svg");
		shapeMode(CENTER);

		stroke(10);
		strokeWeight(1);

		// obj.draw(width - width / 110, height - height / 80);
		obj.orderBasicGraph(1300 / 100, 700 / 100, 1300, 700);
		obj.drawPath(Path);

		// for(int i = 0;i<Path.size();i++){
		if (Path.size() > 0) {

			// System.out.println(Path.size()+" qweqwe");
			if (iter < Path.size()) {
				mouse = new PVector(Path.get(iter).b1.x, Path.get(iter).b1.y);
				// System.out.println(mouse+" mouse");

				character.update();

				// System.out.println("C:"+character.location);
				if ((Math.floor(character.location.x) == mouse.x || Math.ceil(character.location.x) == mouse.x)
						&& (Math.floor(character.location.y) == mouse.y
								|| Math.ceil(character.location.y) == mouse.y)) {
					iter++;
					// System.out.println(iter);
				}

				character.checkEdges();
				// System.out.println(character.location+"character");
				/*
				 * System.out.println(Math.ceil(character.location.x)+" "
				 * +Math.floor(character.location.x));
				 * System.out.println(Math.ceil(character.location.y)+" "
				 * +Math.floor(character.location.y));
				 */

			}

		}

		// character.checkEdges();
		translate((float) character.getx(), (float) character.gety());
		rotate(ofinal);
		translate(-(float) character.getx(), -(float) character.gety());
		shape(arrow, (float) character.getx(), (float) character.gety(), character.getlength(), character.getheight());
		// if(mx>=0&&my>=0)
		// obj.Astar(mx,my);
	}

	public void mousePressed() {
		mx = mouseX;
		my = mouseY;
		start = end;

		if (nodeExists(mx, my)) {
			Path.clear();
			obj.Astar(mx, my, start);
			ab++;
			iter = 1;
		}

	}

	public boolean nodeExists(float x1, float y1) {
		Node temp = obj.new Node(x1, y1);
		for (int i = 0; i < nodeRecords.size(); i++) {

			if ((Math.abs(temp.a1 - nodeRecords.get(i).curr.x) + Math.abs(temp.b1 - nodeRecords.get(i).curr.y)) <= 15) {
				return true;

			}
		}
		return false;
	}

	class Graph {

		//// -------remove connections

		// NodeRecord[] nodeRecords = new NodeRecord[25];

		

		public void orderBasicGraph(float ix, float iy, float ex, float ey) {

			float xby = ex / 20;
			float yby = ey / 20;
			fill(0, 0, 255);
			rect(ix, 5.2f * yby, 1.6f * xby, 1.5f * yby, 10);
			rect(ix, 12.2f * yby, 5.6f * xby, 1.5f * yby, 10);
			rect(6.05f * xby, 5.2f * yby, 1.7f * xby, 3.5f * yby, 10);
			rect(8.05f * xby, iy, 5.8f * xby, 1.55f * yby, 10);
			rect(10.05f * xby, 7.2f * yby, 3.8f * xby, 1.5f * yby, 10);
			rect(8.2f * xby, 12.4f * yby, 1.5f * xby, 7.5f * yby, 10);
			rect(13.2f * xby, 12.4f * yby, 1.5f * xby, 7.5f * yby, 10);
			rect(18.2f * xby, 15.2f * yby, 1.7f * xby, 1.5f * yby, 10);
			rect(18.2f * xby, iy, 1.7f * xby, 1.5f * yby, 10);

			/*for (int i = 0; i < nodes.size(); i++) {
				nodes.get(i).drawNode();
			}*/
			/*
			 * for (int i = 0; i < connections.size(); i++) {
			 * connections.get(i).drawConnections(); }
			 */
		}

		public void colorOpen(Node node) {
			// node.drawOpenNode();
		}

		public void colorClosed(Node node) {
			// node.drawClosedNode();
		}

		public ArrayList<Connections> getConnection(Node from) {
			ArrayList<Connections> Clist = new ArrayList<Connections>();
			for (int i = 0; i < connections.size(); i++) {
				if (from == connections.get(i).getFromNode()) {
					Clist.add(connections.get(i));
				}
			}

			return Clist;
		}

		public float getnodeCost(Connections connect) {
			return connect.getCost();
		}

		public Node getNodeToConnection(Connections connect) {
			return connect.getToNode();
		}

		public NodeRecord getNodeRecord(Node node) {
			NodeRecord temp = nodeRecords.get(0);
			for (int i = 0; i < nodeRecords.size(); i++) {
				if (node.equals(nodeRecords.get(i).curr)) {
					temp = nodeRecords.get(i);
					break;
				}
			}
			return temp;
		}

		public NodeRecord setNodeRecord(NodeRecord nodeRecord, Connections connections2, float c, float d) {
			nodeRecord.cconnection = connections2;
			nodeRecord.costSoFar = c;
			nodeRecord.estimatedTotal = d;
			return nodeRecord;
		}

		public float heuristic(Node temp) {
			// Euclidean
			// return
			// dist(temp.x,temp.y,nodeRecords[24].curr.x,nodeRecords[24].curr.y)
			// Manhattan

			return (Math.abs(temp.x - end.curr.x) + Math.abs(temp.y - end.curr.y));
		}

		public void Astar(float m1x, float m1y, NodeRecord star) {

			Node temp = new Node(m1x, m1y);

			if (hero == 1) {
				star = nodeRecords.get(20);
				// System.out.println(star.curr.x + "x" + star.curr.y);
				hero++;
			}
			for (int i = 0; i < nodeRecords.size(); i++) {

				if ((Math.abs(temp.a1 - nodeRecords.get(i).curr.x)
						+ Math.abs(temp.b1 - nodeRecords.get(i).curr.y)) <= 15) {
					end = nodeRecords.get(i);
					break;
				}
			}

			ArrayList<NodeRecord> open = new ArrayList<NodeRecord>();
			ArrayList<NodeRecord> closed = new ArrayList<NodeRecord>();

			ArrayList<Connections> Clist = new ArrayList<Connections>();
			open.add(star);
			colorOpen(star.curr);
			NodeRecord lowNode = open.get(0);
			while (open.size() > 0) {
				float max = MAX_FLOAT;
				for (int i = 0; i < open.size(); i++) {
					if (open.get(i).estimatedTotal < max) {
						max = open.get(i).estimatedTotal;
						lowNode = open.get(i);
					}
				}

				if (lowNode.equals(end))
					break;
				Clist = getConnection(lowNode.curr);
				for (int i = 0; i < Clist.size(); i++) {

					if (open.contains(getNodeRecord(getNodeToConnection(Clist.get(i))))) {
						for (int j = 0; j < open.size(); j++) {
							if (Clist.get(i).getToNode().equals(open.get(j).curr)) {
								if (open.get(j).costSoFar > lowNode.costSoFar + getnodeCost(Clist.get(i))) {
									open.get(j).costSoFar = lowNode.costSoFar + getnodeCost(Clist.get(i));
									open.get(j).estimatedTotal = heuristic(getNodeToConnection(Clist.get(i)))
											+ open.get(j).costSoFar;
									open.get(j).cconnection = Clist.get(i);
									break;
								}
							}
						}
					} else if (closed.contains(getNodeRecord(getNodeToConnection(Clist.get(i))))) {
						for (int j = 0; j < closed.size(); j++) {
							if (Clist.get(i).getToNode().equals(closed.get(j).curr)) {
								if (closed.get(j).costSoFar > lowNode.costSoFar + getnodeCost(Clist.get(i))) {
									closed.get(j).costSoFar = lowNode.costSoFar + getnodeCost(Clist.get(i));
									closed.get(j).estimatedTotal = heuristic(getNodeToConnection(Clist.get(i)))
											+ closed.get(j).costSoFar;
									closed.get(j).cconnection = Clist.get(i);
									open.add(closed.get(j));
									colorOpen(closed.get(j).curr);
									closed.remove(closed.get(j));
									break;
								}
							}

						}

					} else if (!open.contains(getNodeRecord(getNodeToConnection(Clist.get(i))))
							&& !closed.contains(getNodeRecord(getNodeToConnection(Clist.get(i))))) {
						float c = lowNode.costSoFar + getnodeCost(Clist.get(i));
						float estimate = heuristic(getNodeToConnection(Clist.get(i)));
						open.add(setNodeRecord(getNodeRecord(getNodeToConnection(Clist.get(i))), Clist.get(i), c,
								c + estimate));
						colorOpen(getNodeToConnection(Clist.get(i)));
					}
				}
				open.remove(lowNode);
				closed.add(lowNode);

			}
			for (int i = 0; i < closed.size(); i++) {
				colorClosed(closed.get(i).curr);
			}

			while (lowNode.cconnection.getFromNode() != star.curr) {

				Path.add(lowNode.cconnection);
				lowNode = getNodeRecord(lowNode.cconnection.getFromNode());
			}
			// Path.add(connections[0]);
			Collections.reverse(Path);
			// drawPath(Path);
			open.clear();
			closed.clear();
			// Path.clear();
			Clist.clear();
		}

		public void drawPath(ArrayList<Connections> path) {
			int x = 0;
			while (x < path.size()) {

				// path.get(x).drawPathConnections();
				path.get(x).getToNode().drawPathNodes();
				x++;
				// System.out.println("-------------"+ Path.get(x).a1.x);
				// nextpos = new PVector(Path.get(x).b1.x, Path.get(x).b1.y);
				// System.out.println(nextpos);
				// character.update(nextpos);
			} /*
				 * for (int i = 0; i < Path.size(); i++) { currentpos = new
				 * PVector(Path.get(i).a1.x, Path.get(i).a1.y); nextpos = new
				 * PVector(Path.get(i).b1.x, Path.get(i).b1.y); }
				 * character.update(nextpos); character.checkEdges();
				 */
		}

		class Node {
			int x;
			int y;
			float a1, b1;
			int count;

			Node(int a, int b, int i) {
				this.x = a;
				this.y = b;
				this.count = i;
			}

			public void drawPathNodes() {
				// TODO Auto-generated method stub
				fill(0);

				strokeWeight(1);
				fill(0, 255, 0);
				ellipse(x, y, 6, 6);
			}

			public Node(float x2, float y2) {
				// TODO Auto-generated constructor stub
				this.a1 = x2;
				this.b1 = y2;
			}

			public void drawNode() {
				fill(0);
				strokeWeight(1);
				ellipse(x, y, 3, 3);
			}

			public void drawOpenNode() {
				strokeWeight(1);
				fill(0, 0, 255);
				ellipse(x, y, 5, 5);
			}

			public void drawClosedNode() {
				strokeWeight(1);
				fill(0, 255, 0);
				ellipse(x, y, 5, 5);
				fill(0, 0, 255);
				ellipse(x, y, 1, 1);
			}
		}

		class NodeRecord {
			Node curr;
			Connections cconnection;
			float costSoFar;
			float estimatedTotal;

			NodeRecord(Node nde, Connections iConnect, float cost, float estimate) {
				curr = nde;
				cconnection = iConnect;
				costSoFar = cost;
				estimatedTotal = estimate;
			}
		}

		class Connections {
			Node a1;
			Node b1;

			Connections(Node x, Node y) {
				this.a1 = x;
				this.b1 = y;
			}

			public void drawConnections() {
				fill(0);
				line(a1.x, a1.y, b1.x, b1.y);
			}

			public void drawPathConnections() {

				stroke(255, 0, 0);
				strokeWeight(4);
				line(a1.x, a1.y, b1.x, b1.y);
			}

			public float getCost() {

				return (dist(a1.x, a1.y, b1.x, b1.y));
			}

			public Node getFromNode() {
				return a1;
			}

			public Node getToNode() {
				return b1;
			}
		}

		class Character {
			PVector location;
			PVector target;
			PVector direction;
			PVector velocity;
			PVector orientation;
			PVector rotation;

			int length, height;
			PShape arrow;
			float maxVelocity = (float) 5;
			float timetoTarget = (float) 1;
			float acceleration = 10;

			Character(int x, int y, float speedx, float speedy, int length_, int height_) {
				location = new PVector(x, y);
				velocity = new PVector(speedx, speedy);
				length = length_;
				height = height_;
				target = new PVector(0, 0);
				direction = new PVector();
			}

			void update() {
				target = mouse;
				getNewOrientation(mouse);

				/*
				 * direction = target.copy();
				 * 
				 * radiusofdecel = Math.abs(target.mag()) * (2/5); if
				 * (Math.abs(target.mag()) < radiusofdecel) {
				 * direction.normalize(); System.out.println(direction);
				 * 
				 * acceleration = (maxVelocity - velocity.mag())/timetoTarget;
				 * direction.mult(acceleration); }
				 */
				SteeringOutput steer = getsteering();
				if (steer != null) {
					velocity.add(steer.linear);

					velocity.limit((float) 5);
					location.add(velocity);
					// System.out.println(location);

				}

			}

			public void getNewOrientation(PVector location2) {
				// TODO Auto-generated method stub
				ofinal = PApplet.atan2((float) (location2.y - location.y), (float) (location2.x - location.x));
				z = 0;
			}

			void checkEdges() {
				if ((Math.abs(target.x) <= radiusofsat && Math.abs(target.y) <= radiusofsat)) {
					location.x = mouse.x;
					location.y = mouse.y;
					velocity.x = 0;
					velocity.y = 0;

					z = 0;
				}
				if (Math.abs(target.x) <= radiusofdecel && Math.abs(target.y) <= radiusofdecel
						&& Math.abs(target.x) > radiusofsat && Math.abs(target.y) > radiusofsat) {

					target.sub(150, 150);

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
				// TODO Auto-generated method stub
				return height;
			}

			public float getlength() {
				// TODO Auto-generated method stub
				return length;
			}

			double getx() {
				return location.x;

			}

			double gety() {
				return location.y;

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

	}

}
