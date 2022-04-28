package application.Abstract;

abstract public class Pipe extends Tile {

	public Pipe(String id, String type, String property, String path) {
		super(id, type, property, path);
		// TODO Auto-generated constructor stub
	}

	abstract public boolean isContinue(Tile[][] twoDim, Tile previousTile);

	abstract public void addPath();

	public Pipe(Pipe pipe) {
		super(pipe);
		// TODO Auto-generated constructor stub
	}

}
