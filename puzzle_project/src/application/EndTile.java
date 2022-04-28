package application;

import application.Abstract.Pipe;
import application.Abstract.Tile;
import javafx.scene.shape.LineTo;

public class EndTile extends Pipe {

	public EndTile(String id, String type, String property, String path) {
		super(id, type, property, path);
		// TODO Auto-generated constructor stub
	}

	public int[] findTileInTwoDim(Tile[][] list, Tile tile) {
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

	public void addPath() {
		int thisX = Repository.findXYCoordinate(this)[0];
		int thisY = Repository.findXYCoordinate(this)[1];

		switch (this.getPropertiesFromTile()) {
		case HORIZONTAL:
			Repository.path.getElements().add(new LineTo(thisX, thisY));

			break;
		case VERTICAL:
			Repository.path.getElements().add(new LineTo(thisX, thisY));

			break;
		default:
			break;
		}
		// Repository.path.getElements().add(new LineTo(50, 250));
	}

	@Override
	public boolean isContinue(Tile[][] twoDim, Tile previousTile) {
		System.out.println("end tile check start");
		Repository.pipeList.add(this);
		addPath();
		int x = findTileInTwoDim(twoDim, this)[0];
		int y = findTileInTwoDim(twoDim, this)[1];
		switch (this.getPropertiesFromTile()) {

		case HORIZONTAL:
			if (Pipe.class.isAssignableFrom(twoDim[y][x - 1].getClass())) {

				if (previousTile.getPropertiesFromTile() == Properties.CURVED_ONE_ONE
						|| previousTile.getPropertiesFromTile() == Properties.CURVED_ZERO_ONE
						|| previousTile.getPropertiesFromTile() == Properties.HORIZONTAL) {
					return true;

				}
			}

			return false;
		case VERTICAL:

			if (Pipe.class.isAssignableFrom(twoDim[y + 1][x].getClass())) {
				// Pipe previousTile = (Pipe) twoDim[y + 1][x];
				if (previousTile.getPropertiesFromTile() == Properties.CURVED_ZERO_ONE
						|| previousTile.getPropertiesFromTile() == Properties.CURVED_ZERO_ZERO
						|| previousTile.getPropertiesFromTile() == Properties.VERTICAL) {
					return true;

				}
			}
			return false;
		default:
			return false;
		}
	}
}
