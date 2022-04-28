package application;

import java.util.ArrayList; // import the ArrayList class

import application.Abstract.Movable;
import application.Abstract.Tile;
import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {// ADDPATH İ PİPE IN İÇNE KOY
	Tile objectForMove = null;
	PathTransition pathTransition = new PathTransition();
	final int duration = 1250;
	int numberMovement = 0;

	@Override
	public void start(Stage primaryStage) {
		// We can generate two dimensional arraylist or we can hold places inside object
		ArrayList<Tile> tileList = Repository.tileList;
		ArrayList<Tile> tileListCopy = new ArrayList<Tile>();
		Tile[][] twoDim = Repository.twoDim;

		Helper.readFromFile(tileList);

		tileList.forEach((e) -> {
			tileListCopy.add(Helper.generateTilesFromTile(e));
		});
		int count = 0;
		try {
			GridPane gridPane = new GridPane();
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
					twoDim[i][j] = (tileList.get(count));
					gridPane.add((tileList.get(count)), j, i);
					count++;
				}
			}

			for (int i = 0; i < twoDim.length; i++) {
				for (int j = 0; j < twoDim[i].length; j++) {
					Tile tile = twoDim[i][j];
					tile.setOnMouseReleased((event) -> {
						System.out.println("releaseed");

						objectForMove = tile;
					});

					tile.setOnMouseEntered((event) -> {
						System.out.println("entered");
						moveTiles(tileList, twoDim, tile);

					});
				}
			}

			BorderPane root = new BorderPane();
			root.getChildren().add(gridPane);
			root.getChildren().add(createCircle());

			setBackgroundImage(root);

			setScene(primaryStage, root);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void moveTiles(ArrayList<Tile> tileList, Tile[][] twoDim, Tile tile) {
		int xOfTarget = Repository.findTileInTwoDim(twoDim, tile)[0];
		int yOfTarget = Repository.findTileInTwoDim(twoDim, tile)[1];

		if (objectForMove != null) {

			int xOfOrigin = Repository.findTileInTwoDim(twoDim, objectForMove)[0];
			int yOfOrigin = Repository.findTileInTwoDim(twoDim, objectForMove)[1];

			if (((Movable.class.isAssignableFrom(tile.getClass()))
					&& (Movable.class.isAssignableFrom(objectForMove.getClass())))
					&& EmptyFreeTile.class.isAssignableFrom(tile.getClass())
					&& (!(Math.abs(xOfTarget - xOfOrigin) == 1 && Math.abs(yOfTarget - yOfOrigin) == 1))
					&& (!((Math.abs(xOfTarget - xOfOrigin) > 1) || (Math.abs(yOfTarget - yOfOrigin) > 1)))) {

				System.out.println(++numberMovement);

				if (xOfTarget > xOfOrigin) {
					objectForMove.goToRight();
					tile.goToLeft();

					changePositionOfTwoObjectInList(twoDim, objectForMove, tile);

				} else if (xOfTarget < xOfOrigin) {

					tile.goToRight();
					objectForMove.goToLeft();
					changePositionOfTwoObjectInList(twoDim, objectForMove, tile);

				}
				if (yOfTarget < yOfOrigin) {
					objectForMove.goToUp();
					tile.goToDown();
					changePositionOfTwoObjectInList(twoDim, objectForMove, tile);

				} else if (yOfTarget > yOfOrigin) {

					tile.goToUp();
					objectForMove.goToDown();
					changePositionOfTwoObjectInList(twoDim, objectForMove, tile);

				}

				objectForMove = null;

				checkIsGameDone(tileList, twoDim);

			} else {
				objectForMove = null;

			}

		}
	}

	private void checkIsGameDone(ArrayList<Tile> tileList, Tile[][] twoDim) {
		tileList.forEach((e) -> {
			if (e.getTypes() == application.Types.STARTER) {

				boolean isCompleted = ((StartTile) e).isContinue(twoDim, null);
				if (isCompleted) {
					pathTransition.setPath(Repository.path);
					pathTransition.play();
					Repository.pipeList.clear();
					Repository.path.getElements().clear();

				} else {

					Repository.pipeList.clear();
					Repository.path.getElements().clear();
				}
//										 
			}
		});
	}

	public Circle createCircle() {
		Circle circle = new Circle();
		circle.setCenterX(50);
		circle.setCenterY(57);
		Repository.tileList.forEach((e) -> {
			if (e.getTypes() == application.Types.STARTER) {
				int x = Repository.findXYCoordinate(e)[0];
				int y = Repository.findXYCoordinate(e)[1];
				circle.setCenterX(x);
				circle.setCenterY(y);

			}
		});

		circle.setRadius(13);

		pathTransition.setNode(circle);
		pathTransition.setDuration(Duration.millis(duration));

		return circle;
	}

	private void printTwoDimArray(Tile[][] twoDim) {
		int count = 1;
		for (Tile[] tiles : twoDim) {
			for (Tile tile : tiles) {
				System.out.println(count++);
				System.out.println(tile);
			}
		}
	}

	public void changePositionOfTwoObjectInList(Tile[][] list, Tile to, Tile from) {
		int tempToX = Repository.findTileInTwoDim(list, to)[0];
		int tempToY = Repository.findTileInTwoDim(list, to)[1];
		int tempFromX = Repository.findTileInTwoDim(list, from)[0];
		int tempFromY = Repository.findTileInTwoDim(list, from)[1];

		Tile toCopy = Helper.generateTilesFromTile(to);
		Tile fromCopy = Helper.generateTilesFromTile(from);

		list[tempToY][tempToX] = fromCopy;
		list[tempFromY][tempFromX] = toCopy;

	}

	public void oneDimensionToTwo(ArrayList<Tile> list) {
		ArrayList<ArrayList<Tile>> graph = new ArrayList<>(2);

	}

	public void assignObjectFormove(Tile tile) {
		objectForMove = tile;

	}

	private void setScene(Stage primaryStage, BorderPane root) {
		Scene scene = new Scene(root, 400, 400);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void setBackgroundImage(BorderPane root) {
		Image img2 = new Image(getClass().getResource("tiles/bg.png").toExternalForm());
		ImageView imageView = new ImageView(img2);
		imageView.setFitHeight(400);
		imageView.setFitWidth(400);
		Background background2 = new Background(new BackgroundImage(img2, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT));
		root.setBackground(background2);
	}

	public static void main(String[] args) {
		launch(args);
	}
}

//
//for (Object dest : list) {
////	
//	Tile tile = ((Tile) dest);
//
//	tile.setOnMouseReleased((event) -> {
//		System.out.println("-------------------------");
//		System.out.println(tile);
//		// assignObjectFormove(destination);
//		objectForMove = tile; // bunu değiştir
//
//		System.out.println("-------------------------");
//
//	});
//	
//
////i,j				
////0-> 0,0
////1-> 0,1
////2-  0,2		
////3- 0,3
////4-1,0
////5-1,1
////6-1,2		
////7-1,3		
////8-1,4		
////	
////	
//
////	((Tile) object).setOnMouseExited((event) -> {
////		System.out.println("exit");
////		System.out.println(object);
////		System.out.println("exit");
////
////	});
//	tile.setOnMouseEntered((event) -> {
//
//		System.out.println("xxxxxx");
//		System.out.println(tile);
//		System.out.print("X-" + tile.positionX);
//		System.out.println("Y-" + tile.positionY);
//		System.out.println("destination");
//
//		if (objectForMove != null) {
//
//			if (((Movable.class.isAssignableFrom(tile.getClass()))
//					&& (Movable.class.isAssignableFrom(objectForMove.getClass())))
//					&& EmptyFreeTile.class.isAssignableFrom(tile.getClass())
//					&& (!(Math.abs(tile.positionX - objectForMove.positionX) == 1
//							&& Math.abs(tile.positionY - objectForMove.positionY) == 1))
//					&& (!((Math.abs(tile.positionX - objectForMove.positionX) > 1)
//							|| (Math.abs(tile.positionY - objectForMove.positionY) > 1)))) {
//				System.out.println(
//						"----------------------------------------ALLOOOOOOOOOOOOOOO------------------------------------------");
//				System.out.println("shouldMove");
//				System.out.println(objectForMove);
//				System.out.print("X-" + objectForMove.positionX);
//				System.out.println("Y-" + objectForMove.positionY);
//				System.out.println("shouldMove");
//
//				if ((tile.positionX > objectForMove.positionX)
//						&& tile.positionX - objectForMove.positionX == 1) {// mutlak değer
//					System.out.println("go right");
//					objectForMove.goToRight();
//					tile.goToLeft();
//					updatePosition(tile, objectForMove);
//
//				} else if (tile.positionX < objectForMove.positionX) {
//					System.out.println("go left");
//
//					tile.goToRight();
//					objectForMove.goToLeft();
//					updatePosition(tile, objectForMove);
//
//				}
//				if (tile.positionY < objectForMove.positionY) {
//					System.out.println("go UP");
//					objectForMove.goToUp();
//					tile.goToDown();
//					updatePosition(tile, objectForMove);
//
//				} else if (tile.positionY > objectForMove.positionY) {
//					System.out.println("go down");
//					tile.goToUp();
//					objectForMove.goToDown();
//					updatePosition(tile, objectForMove);
//
//				}
//				// objectForMove.goToRight();
//				// ((Tile) object).goToLeft();
//
//				assignObjectFormove(null);
//
//				System.out.println("**********************");
//				System.out.println();
//				System.out.println();
//				tileList.forEach((e) -> {
//					if (e.getTypes() == application.Types.STARTER) {
//						System.out.println(e.getTileId());
//						System.out.println(e.getPropertiesFromTile());
//					}
//				});
//				list.forEach((e) -> {
//
//					System.out.println();
//				});
//
//				checkGameCompleted();
//
//			} else {
//				System.out.println("movaaabblleeeeeeeeeee------------------------------------------");
//				assignObjectFormove(null);
//
//			}
//
//		}
//
//	});
//	// -----------------
//
//	System.out.println(list.indexOf(tile));
//	System.out.println(tile);
//}
