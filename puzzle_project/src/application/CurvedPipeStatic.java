package application;

import application.Abstract.Pipe;
import application.Abstract.Tile;
import javafx.scene.shape.CubicCurveTo;

public class CurvedPipeStatic extends Pipe {

	public CurvedPipeStatic(Pipe pipe) {
		super(pipe);
		// TODO Auto-generated constructor stub
	}

	public CurvedPipeStatic(String id, String type, String property, String path) {

		super(id, type, property, path);
		// TODO Auto-generated constructor stub
	}

	public void addPath() {
		int thisX = Repository.findXYCoordinate(this)[0];
		int thisY = Repository.findXYCoordinate(this)[1];

		Tile tile;
		int previousX;
		int previousY;

		switch (this.getPropertiesFromTile()) {
		case CURVED_ZERO_ZERO:
			tile = Repository.pipeList.get(Repository.pipeList.indexOf(this) - 1);
			previousX = Repository.findXYCoordinate(tile)[0];
			previousY = Repository.findXYCoordinate(tile)[1];

			if (thisX > previousX) {// GO TO right up
				System.out.println("111111111111111111111111111111111111");

				CubicCurveTo curve = new CubicCurveTo(thisX - 50, thisY, thisX, thisY, thisX, thisY - 50);

				Repository.path.getElements().add(curve);
			} else {// GO TO LEFT
				System.out.println("222222222222222222222222222222222222222");

				CubicCurveTo curve = new CubicCurveTo(thisX, thisY - 50, thisX, thisY, thisX - 50, thisY);
				Repository.path.getElements().add(curve);

			}
			break;
		case CURVED_ZERO_ONE:
			tile = Repository.pipeList.get(Repository.pipeList.indexOf(this) - 1);
			previousX = Repository.findXYCoordinate(tile)[0];
			previousY = Repository.findXYCoordinate(tile)[1];

			if (thisY > previousY) {// GO TO down
				CubicCurveTo curve = new CubicCurveTo(thisX, thisY - 50, thisX, thisY, thisX + 50, thisY);
				Repository.path.getElements().add(curve);
			} else {// GO TO LEFT
				CubicCurveTo curve = new CubicCurveTo(thisX + 50, thisY, thisX, thisY, thisX, thisY - 50);
				Repository.path.getElements().add(curve);
			}
			break;
		case CURVED_ONE_ZERO:
			tile = Repository.pipeList.get(Repository.pipeList.indexOf(this) - 1);

			previousX = Repository.findXYCoordinate(tile)[0];
			previousY = Repository.findXYCoordinate(tile)[1];

			if (thisX > previousX) {

				CubicCurveTo curve = new CubicCurveTo(thisX - 50, thisY, thisX, thisY, thisX, thisY + 50);
				Repository.path.getElements().add(curve);
			} else {
				System.out.println("AAAAAA");
				CubicCurveTo curve = new CubicCurveTo(thisX, thisY + 50, thisX, thisY, thisX - 50, thisY);
				Repository.path.getElements().add(curve);

			}
			break;
		case CURVED_ONE_ONE:
			tile = Repository.pipeList.get(Repository.pipeList.indexOf(this) - 1);
			previousX = Repository.findXYCoordinate(tile)[0];
			previousY = Repository.findXYCoordinate(tile)[1];
			if (thisY > previousY) {
				System.out.println("111111111111111111111111111111111111");
				CubicCurveTo curve = new CubicCurveTo(thisX, thisY + 50, thisX, thisY, thisX + 50, thisY);
				Repository.path.getElements().add(curve);
			} else {
				System.out.println("222222222222222222222222222222222222222");

				CubicCurveTo curve = new CubicCurveTo(thisX + 50, thisY, thisX, thisY, thisX, thisY + 50);
				Repository.path.getElements().add(curve);

			}
			break;
		default:
			break;
		}
		// Repository.path.getElements().add(new LineTo(50, 250));
	}

	@Override
	public boolean isContinue(Tile[][] twoDim, Tile previousTile) {
		System.out.println("curved static check start");
		Repository.pipeList.add(this);
		addPath();
		int x = findTileInTwoDim(twoDim, this)[0];
		int y = findTileInTwoDim(twoDim, this)[1];

		switch (this.getPropertiesFromTile()) {

		case CURVED_ZERO_ZERO:
			if (x > 0) {
				if (Pipe.class.isAssignableFrom(twoDim[y][x - 1].getClass())) {
					Pipe secondTile = (Pipe) twoDim[y][x - 1];

					if (secondTile.getPropertiesFromTile() == Properties.HORIZONTAL
							|| secondTile.getPropertiesFromTile() == Properties.CURVED_ZERO_ONE
							|| secondTile.getPropertiesFromTile() == Properties.CURVED_ONE_ONE) {
						System.out.println("CONTİNUEEEEEE");
						if (!secondTile.getTileId().equals(previousTile.getTileId())) {
							return secondTile.isContinue(twoDim, this);

						}
					}

				}

			}
			if (y > 0) {
				if (Pipe.class.isAssignableFrom(twoDim[y - 1][x].getClass())) {
					Pipe secondTile = (Pipe) twoDim[y - 1][x];

					if (secondTile.getPropertiesFromTile() == Properties.VERTICAL
							|| secondTile.getPropertiesFromTile() == Properties.CURVED_ONE_ZERO
							|| secondTile.getPropertiesFromTile() == Properties.CURVED_ONE_ONE) {
						System.out.println("CONTİNUEEEEEE");
						if (!secondTile.getTileId().equals(previousTile.getTileId())) {
							return secondTile.isContinue(twoDim, this);

						}

					}
				}

			}
			return false;
		case CURVED_ZERO_ONE:
			if (x < 3) {
				if (Pipe.class.isAssignableFrom(twoDim[y][x + 1].getClass())) {
					System.out.println("newwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");

					Pipe secondTile = (Pipe) twoDim[y][x + 1];

					if (secondTile.getPropertiesFromTile() == Properties.HORIZONTAL
							|| secondTile.getPropertiesFromTile() == Properties.CURVED_ZERO_ZERO
							|| secondTile.getPropertiesFromTile() == Properties.CURVED_ONE_ZERO) {

						if (!secondTile.getTileId().equals(previousTile.getTileId())) {
							return secondTile.isContinue(twoDim, this);

						}

					}
				}

			}
			if (y > 1) {

				if (Pipe.class.isAssignableFrom(twoDim[y - 1][x].getClass())) {
					Pipe secondTile = (Pipe) twoDim[y - 1][x];

					if (secondTile.getPropertiesFromTile() == Properties.VERTICAL
							|| secondTile.getPropertiesFromTile() == Properties.CURVED_ONE_ZERO
							|| secondTile.getPropertiesFromTile() == Properties.CURVED_ONE_ONE) {
						System.out.println("CONTİNUEEEEEE");
						if (!secondTile.getTileId().equals(previousTile.getTileId())) {
							return secondTile.isContinue(twoDim, this);

						}

					}
				}

			}
			return false;

		case CURVED_ONE_ZERO:

			if (x > 0) {
				if (Pipe.class.isAssignableFrom(twoDim[y][x - 1].getClass())) {
					Pipe secondTile = (Pipe) twoDim[y][x - 1];

					if (secondTile.getPropertiesFromTile() == Properties.HORIZONTAL
							|| secondTile.getPropertiesFromTile() == Properties.CURVED_ZERO_ONE
							|| secondTile.getPropertiesFromTile() == Properties.CURVED_ONE_ONE) {
						System.out.println("CONTİNUEEEEEE");
						if (!secondTile.getTileId().equals(previousTile.getTileId())) {
							return secondTile.isContinue(twoDim, this);

						}
					}
				}

			}
			if (y < 3) {
				if (Pipe.class.isAssignableFrom(twoDim[y + 1][x].getClass())) {
					Pipe secondTile = (Pipe) twoDim[y + 1][x];

					if (secondTile.getPropertiesFromTile() == Properties.VERTICAL
							|| secondTile.getPropertiesFromTile() == Properties.CURVED_ZERO_ZERO
							|| secondTile.getPropertiesFromTile() == Properties.CURVED_ZERO_ONE) {
						System.out.println("CONTİNUEEEEEE");
						if (!secondTile.getTileId().equals(previousTile.getTileId())) {
							return secondTile.isContinue(twoDim, this);

						}
					}
				}

			}
			return false;

		case CURVED_ONE_ONE:

			if (x < 3) {
				if (Pipe.class.isAssignableFrom(twoDim[y][x + 1].getClass())) {
					Pipe secondTile = (Pipe) twoDim[y][x + 1];

					if (secondTile.getPropertiesFromTile() == Properties.HORIZONTAL
							|| secondTile.getPropertiesFromTile() == Properties.CURVED_ZERO_ZERO
							|| secondTile.getPropertiesFromTile() == Properties.CURVED_ONE_ZERO) {
						System.out.println("CONTİNUEEEEEE");
						if (!secondTile.getTileId().equals(previousTile.getTileId())) {
							return secondTile.isContinue(twoDim, this);

						}
					}
				}

			}
			if (y < 3) {
				if (Pipe.class.isAssignableFrom(twoDim[y + 1][x].getClass())) {
					Pipe secondTile = (Pipe) twoDim[y + 1][x];

					if (secondTile.getPropertiesFromTile() == Properties.VERTICAL
							|| secondTile.getPropertiesFromTile() == Properties.CURVED_ZERO_ZERO
							|| secondTile.getPropertiesFromTile() == Properties.CURVED_ZERO_ONE) {
						System.out.println("CONTİNUEEEEEE");
						if (!secondTile.getTileId().equals(previousTile.getTileId())) {
							return secondTile.isContinue(twoDim, this);

						}
					}
				}

			}
			return false;

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
