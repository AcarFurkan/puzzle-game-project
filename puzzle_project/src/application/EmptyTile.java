package application;

import application.Abstract.Movable;
import application.Abstract.Visual;

public class EmptyTile extends Visual implements Movable {

	public EmptyTile(String id, String type, String property, String path) {
		super(id, type, property, path);
		// TODO Auto-generated constructor stub
	}

	public EmptyTile(EmptyTile tile) {
		super(tile);
		// TODO Auto-generated constructor stub
	}

}
