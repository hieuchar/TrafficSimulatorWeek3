package exercise1;

public enum Orientation {
	NORTH(270),
	SOUTH(90),
	EAST(0),
	WEST(180);
	int rotation;
	Orientation(int rotation){
		this.rotation = rotation;
	}
	int getRotation(){
		return rotation;
	}
}
