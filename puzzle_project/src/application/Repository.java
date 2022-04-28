package application;

import java.util.ArrayList;

import application.Abstract.Pipe;
import application.Abstract.Tile;
import javafx.scene.shape.Path;

public class Repository {
	public static ArrayList<Pipe> pipeList = new ArrayList<Pipe>();
	public static Path path = new Path();
	public static Tile[][] twoDim = new Tile[4][4];
	public static ArrayList<Tile> tileList = new ArrayList<Tile>();

	public static int[] findTileInTwoDim(Tile[][] list, Tile tile) {// find position as dot
		int[] positions = new int[2];

		for (int i = 0; i < list.length; i++) {
			for (int j = 0; j < list[i].length; j++) {
				if (list[i][j].getTileId().equals(tile.getTileId())) {
					positions[0] = j;
					positions[1] = i;
					return positions;
				}

			}
		}
		return positions;

	}

	public static int[] findXYCoordinate(Tile tile) {// find position as pixel
		int[] coordinates = new int[2];
		int x = (findTileInTwoDim(twoDim, tile)[0] * 100 + 50);
		int y = (findTileInTwoDim(twoDim, tile)[1] * 100 + 50);
		coordinates[0] = x;
		coordinates[1] = y;
		return coordinates;
	}
}
