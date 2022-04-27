package application;

import application.Abstract.Pipe;
import application.Abstract.Tile;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;

public class StartTile extends Pipe {

	public StartTile(String id, String type, String property, String path) {
		super(id, type, property, path);
		// TODO Auto-generated constructor stub
	}

	public StartTile(StartTile tile) {
		super(tile);
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

	@Override
	public String toString() {
		return "StartTile [getTileId()=" + getTileId() + ", getTypes()=" + getTypes() + ", getPropertiesFromTile()="
				+ getPropertiesFromTile() + "]";
	}

	public void addPath() {
		int thisX = Repository.findXYCoordinate(this)[0];
		int thisY = Repository.findXYCoordinate(this)[1];
		Repository.path.getElements().add(new MoveTo(thisX, thisY));

		switch (this.getPropertiesFromTile()) {
		case HORIZONTAL:
			Repository.path.getElements().add(new LineTo(thisX + 50, thisY));
			break;
		case VERTICAL:
			Repository.path.getElements().add(new LineTo(thisX, thisY + 50));
			break;
		default:
		}
		// Repository.path.getElements().add(new LineTo(50, 250));
	}

	@Override
	public boolean isContinue(Tile[][] twoDim, Tile previousTile) {
		System.out.println("start tile check start");

		Repository.pipeList.add(this);
		addPath();
		int x = findTileInTwoDim(twoDim, this)[0];
		int y = findTileInTwoDim(twoDim, this)[1];
		switch (this.getPropertiesFromTile()) {

		case HORIZONTAL:

			if (x == 1 && y == 0) {
				if (Pipe.class.isAssignableFrom(twoDim[y][x + 1].getClass())) {
					Pipe secondTile = (Pipe) twoDim[y][x + 1];

					if (secondTile.getPropertiesFromTile() == Properties.CURVED_ZERO_ZERO
							|| secondTile.getPropertiesFromTile() == Properties.CURVED_ONE_ZERO
							|| secondTile.getPropertiesFromTile() == Properties.HORIZONTAL) {
						System.out.println("CONTİNUEEEEEE");
						return secondTile.isContinue(twoDim, this);

					} else {
						return false;
					}
				}

			}
			if (x == 1 && y == 1) {
				if (Pipe.class.isAssignableFrom(twoDim[y][x + 1].getClass())) {
					Pipe secondTile = (Pipe) twoDim[y][x + 1];

					if (secondTile.getPropertiesFromTile() == Properties.CURVED_ZERO_ZERO
							|| secondTile.getPropertiesFromTile() == Properties.CURVED_ONE_ZERO
							|| secondTile.getPropertiesFromTile() == Properties.HORIZONTAL) {
						System.out.println("CONTİNUEEEEEE");
						return secondTile.isContinue(twoDim, this);

					} else {
						return false;
					}
				}

			}
			if (Pipe.class.isAssignableFrom(twoDim[y][x + 1].getClass())) {
				Pipe secondTile = (Pipe) twoDim[y][x + 1];

				if (secondTile.getPropertiesFromTile() == Properties.CURVED_ZERO_ZERO
						|| secondTile.getPropertiesFromTile() == Properties.CURVED_ONE_ZERO
						|| secondTile.getPropertiesFromTile() == Properties.HORIZONTAL) {
					System.out.println("CONTİNUEEEEEE");
					return secondTile.isContinue(twoDim, this);

				} else {
					return false;
				}
			}
			return false;
		case VERTICAL:
			if (Pipe.class.isAssignableFrom(twoDim[y + 1][x].getClass())) {
				Pipe secondTile = (Pipe) twoDim[y + 1][x];

				if (secondTile.getPropertiesFromTile() == Properties.CURVED_ZERO_ONE
						|| secondTile.getPropertiesFromTile() == Properties.CURVED_ZERO_ZERO
						|| secondTile.getPropertiesFromTile() == Properties.VERTICAL) {
					System.out.println("CONTİNUEEEEEE");
					return secondTile.isContinue(twoDim, this);

				} else {
					return false;
				}
			}
			return false;
		default:
			return false;
		}
	}

}
