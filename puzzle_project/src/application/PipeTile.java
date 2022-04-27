package application;

import application.Abstract.Movable;
import application.Abstract.Pipe;
import application.Abstract.Tile;
import javafx.scene.shape.LineTo;

public class PipeTile extends Pipe implements Movable {

	public PipeTile(String id, String type, String property, String path) {
		super(id, type, property, path);
		// TODO Auto-generated constructor stub
	}

	public void addPath() {
		int thisX = Repository.findXYCoordinate(this)[0];
		int thisY = Repository.findXYCoordinate(this)[1];
		Tile tile = Repository.pipeList.get(Repository.pipeList.indexOf(this) - 1);
		int previousX = Repository.findXYCoordinate(tile)[0];
		int previousY = Repository.findXYCoordinate(tile)[1];
		switch (this.getPropertiesFromTile()) {
		case HORIZONTAL:
			if (thisX > previousX) {// GO TO RİGHT
				Repository.path.getElements().add(new LineTo(thisX + 50, thisY));
			} else {// GO TO LEFT
				Repository.path.getElements().add(new LineTo(thisX - 50, thisY));
			}

			break;
		case VERTICAL:
			if (thisY > previousY) {
				Repository.path.getElements().add(new LineTo(thisX, thisY - 50));
			} else {
				Repository.path.getElements().add(new LineTo(thisX, thisY + 50));
			}
			break;
		default:
			break;
		}
		// Repository.path.getElements().add(new LineTo(50, 250));
	}

	@Override
	public boolean isContinue(Tile[][] twoDim, Tile previousTile) {
		System.out.println("pipe tile check start");
		Repository.pipeList.add(this);
		addPath();
		int x = findTileInTwoDim(twoDim, this)[0];
		int y = findTileInTwoDim(twoDim, this)[1];
		switch (this.getPropertiesFromTile()) {

		case HORIZONTAL:

			if (x == 0) {// sağı kontrol et
				if (Pipe.class.isAssignableFrom(twoDim[y][x + 1].getClass())) {
					Pipe secondTile = (Pipe) twoDim[y][x + 1];

					if (secondTile.getPropertiesFromTile() == Properties.CURVED_ZERO_ZERO
							|| secondTile.getPropertiesFromTile() == Properties.CURVED_ONE_ZERO
							|| secondTile.getPropertiesFromTile() == Properties.HORIZONTAL) {
						System.out.println("CONTİNUEEEEEE");
						return secondTile.isContinue(twoDim, this);

					}
				}
				return false;
			}
			if (x == 3) {// solu kontrol et
				if (Pipe.class.isAssignableFrom(twoDim[y][x - 1].getClass())) {
					Pipe secondTile = (Pipe) twoDim[y][x - 1];

					if (secondTile.getPropertiesFromTile() == Properties.CURVED_ZERO_ONE
							|| secondTile.getPropertiesFromTile() == Properties.CURVED_ONE_ONE
							|| secondTile.getPropertiesFromTile() == Properties.HORIZONTAL) {
						System.out.println("CONTİNUEEEEEE");
						return secondTile.isContinue(twoDim, this);

					}
				}
				return false;

			} else {// iki tarafıda kontrol et
				if (Pipe.class.isAssignableFrom(twoDim[y][x - 1].getClass())) {// sol
					Pipe secondTile = (Pipe) twoDim[y][x - 1];

					if (secondTile.getPropertiesFromTile() == Properties.CURVED_ZERO_ONE
							|| secondTile.getPropertiesFromTile() == Properties.CURVED_ONE_ONE
							|| secondTile.getPropertiesFromTile() == Properties.HORIZONTAL) {
						System.out.println("CONTİNUEEEEEE");

						if (!secondTile.getTileId().equals(previousTile.getTileId())) {
							return secondTile.isContinue(twoDim, this);

						}

					}
				}

				if (Pipe.class.isAssignableFrom(twoDim[y][x + 1].getClass())) {// sağ
					Pipe secondTile = (Pipe) twoDim[y][x + 1];

					if (secondTile.getPropertiesFromTile() == Properties.CURVED_ZERO_ZERO
							|| secondTile.getPropertiesFromTile() == Properties.CURVED_ONE_ZERO
							|| secondTile.getPropertiesFromTile() == Properties.HORIZONTAL) {
						System.out.println("CONTİNUEEEEEE");

						if (!secondTile.getTileId().equals(previousTile.getTileId())) {
							return secondTile.isContinue(twoDim, this);

						}
					}
				}
				return false;

			}
		case VERTICAL:
			if (y == 0) {// alt
				if (Pipe.class.isAssignableFrom(twoDim[y + 1][x].getClass())) {
					Pipe secondTile = (Pipe) twoDim[y + 1][x];

					if (secondTile.getPropertiesFromTile() == Properties.CURVED_ZERO_ZERO
							|| secondTile.getPropertiesFromTile() == Properties.CURVED_ZERO_ONE
							|| secondTile.getPropertiesFromTile() == Properties.VERTICAL) {
						System.out.println("CONTİNUEEEEEE");
						return secondTile.isContinue(twoDim, this);

					}
				}
				return false;

			}
			if (y == 3) {// üst
				if (Pipe.class.isAssignableFrom(twoDim[y - 1][x].getClass())) {
					Pipe secondTile = (Pipe) twoDim[y - 1][x];

					if (secondTile.getPropertiesFromTile() == Properties.CURVED_ONE_ZERO
							|| secondTile.getPropertiesFromTile() == Properties.CURVED_ONE_ONE
							|| secondTile.getPropertiesFromTile() == Properties.VERTICAL) {
						System.out.println("CONTİNUEEEEEE");
						return secondTile.isContinue(twoDim, this);

					}
				}
				return false;

			} else {// both

				if (Pipe.class.isAssignableFrom(twoDim[y + 1][x].getClass())) {

					Pipe secondTile = (Pipe) twoDim[y + 1][x];

					if (secondTile.getPropertiesFromTile() == Properties.CURVED_ZERO_ZERO
							|| secondTile.getPropertiesFromTile() == Properties.CURVED_ZERO_ONE
							|| secondTile.getPropertiesFromTile() == Properties.VERTICAL) {
						System.out.println(secondTile);

						if (!secondTile.getTileId().equals(previousTile.getTileId())) {
							return secondTile.isContinue(twoDim, this);

						}
					}
				}
				if (Pipe.class.isAssignableFrom(twoDim[y - 1][x].getClass())) {

					Pipe secondTile = (Pipe) twoDim[y - 1][x];

					if (secondTile.getPropertiesFromTile() == Properties.CURVED_ONE_ZERO
							|| secondTile.getPropertiesFromTile() == Properties.CURVED_ONE_ONE
							|| secondTile.getPropertiesFromTile() == Properties.VERTICAL) {
						System.out.println("CONTİNUEEEEEE");

						if (!secondTile.getTileId().equals(previousTile.getTileId())) {
							return secondTile.isContinue(twoDim, this);

						}
					}
				}
				return false;

			}

		default:
			return false;

		}
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

}
