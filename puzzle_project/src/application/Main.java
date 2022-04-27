package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList; // import the ArrayList class
import java.util.Scanner;

import application.Abstract.Movable;
import application.Abstract.Tile;
import javafx.animation.PathTransition;
import javafx.animation.PathTransition.OrientationType;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Node;
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

	@Override
	public void start(Stage primaryStage) {
		// We can generate two dimensional arraylist or we can hold places inside object
		ArrayList<Tile> tileList = Repository.tileList;
		ArrayList<Tile> tileListCopy = new ArrayList<Tile>();
		Tile[][] twoDim = Repository.twoDim;
		PathTransition transition = new PathTransition();

		readFromFile(tileList);

		tileList.forEach((e) -> {
			tileListCopy.add(generateTilesFromTile(e));
		});
		int count = 0;
		try {
			GridPane gridPane = new GridPane();
			for (int i = 0; i < 4; i++) {
				System.out.println(i);
				for (int j = 0; j < 4; j++) {
					ArrayList<Tile> listTemp = new ArrayList<Tile>();
					twoDim[i][j] = (tileList.get(count));

					gridPane.add((tileList.get(count)), j, i);
					count++;
				}
			}
			System.out.println("111111111111111");

			System.out.println(tileListCopy.get(0).equals(tileList.get(0)));

			ObservableList<Node> list = gridPane.getChildren();
			for (int i = 0; i < twoDim.length; i++) {
				for (int j = 0; j < twoDim[i].length; j++) {
					Tile tile = twoDim[i][j];
					tile.setOnMouseReleased((event) -> {
						System.out.println("-------------------------");
						System.out.println(tile);
						// assignObjectFormove(destination);
						objectForMove = tile; // bunu değiştir

						System.out.println("-------------------------");

					});

					tile.setOnMouseEntered((event) -> {

						int xOfTarget = findTileInTwoDim(twoDim, tile)[0];
						int yOfTarget = findTileInTwoDim(twoDim, tile)[1];
						System.out.println("xxxxxx");
						System.out.println(tile);
						System.out.println(((xOfTarget * 100) + 50) + "-" + ((yOfTarget * 100) + 50));

						System.out.print("X-" + tile.positionX);
						System.out.println("Y-" + tile.positionY);
						System.out.println(tile.getPropertiesFromTile());

						System.out.print("X-" + xOfTarget);
						System.out.println("Y-" + yOfTarget);
						System.out.println("destination");

						if (objectForMove != null) {

							int xOfOrigin = findTileInTwoDim(twoDim, objectForMove)[0];
							int yOfOrigin = findTileInTwoDim(twoDim, objectForMove)[1];

							if (((Movable.class.isAssignableFrom(tile.getClass()))
									&& (Movable.class.isAssignableFrom(objectForMove.getClass())))
									&& EmptyFreeTile.class.isAssignableFrom(tile.getClass())
									&& (!(Math.abs(xOfTarget - xOfOrigin) == 1 && Math.abs(yOfTarget - yOfOrigin) == 1))
									&& (!((Math.abs(xOfTarget - xOfOrigin) > 1)
											|| (Math.abs(yOfTarget - yOfOrigin) > 1)))) {

								if (xOfTarget > xOfOrigin) {
									System.out.println("go right");
									objectForMove.goToRight();
									tile.goToLeft();
									changePositionOfTwoObjectInList(twoDim, objectForMove, tile);

									updatePosition(tile, objectForMove);

								} else if (xOfTarget < xOfOrigin) {
									System.out.println("go left");

									tile.goToRight();
									objectForMove.goToLeft();
									changePositionOfTwoObjectInList(twoDim, objectForMove, tile);
									updatePosition(tile, objectForMove);

								}
								if (yOfTarget < yOfOrigin) {
									System.out.println("go UP");
									objectForMove.goToUp();
									tile.goToDown();
									changePositionOfTwoObjectInList(twoDim, objectForMove, tile);
									updatePosition(tile, objectForMove);

								} else if (yOfTarget > yOfOrigin) {
									System.out.println("go down");
									tile.goToUp();
									objectForMove.goToDown();
									changePositionOfTwoObjectInList(twoDim, objectForMove, tile);
									updatePosition(tile, objectForMove);

								}

								assignObjectFormove(null);

								System.out.println("**********************");
								System.out.println();
								System.out.println();
								tileList.forEach((e) -> {
									if (e.getTypes() == application.Types.STARTER) {
										System.out.println(e.getTileId());
										System.out.println(e.getPropertiesFromTile());

										boolean isContinue = ((StartTile) e).isContinue(twoDim, null);
										if (isContinue) {
											System.out.println("con");
											int[] pos = Repository.findXYCoordinate(e);

											pathTransition.setPath(Repository.path);
											pathTransition.play();
											Repository.pipeList.clear();
											Repository.path.getElements().clear();

										} else {

											Repository.pipeList.clear();
											Repository.path.getElements().clear();
											System.out.println("not con");
										}
//										 
									}
								});
								list.forEach((e) -> {

									System.out.println();
								});

								checkGameCompleted();

							} else {
								assignObjectFormove(null);

							}

						}

					});
				}
			}

			System.out.println("*******");
			for (Tile object2 : tileList) {

				System.out.println(tileList.indexOf(object2));
				System.out.println(object2);
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
//		Path path = new Path();
//		CubicCurveTo quadcurve = new CubicCurveTo(50, 300, 50, 350, 100, 350);
//		path.getElements().add(new MoveTo(50, 57));
//		pathTransition.setPath(path);
		// path.getElements().add(new LineTo(50, 250));

		// path.getElements().add(quadcurve);

		// path.getElements().add(new LineTo(350, 350));
		pathTransition.setNode(circle);
		pathTransition.setDuration(Duration.millis(1250));

		circle.setOnMouseClicked((e) -> {

			pathTransition.setOrientation(OrientationType.ORTHOGONAL_TO_TANGENT);

		});
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

	private void checkGameCompleted() {
		System.out.println("game completed");
		// TODO Auto-generated method stub

	}

	public void changePositionOfTwoObjectInList(Tile[][] list, Tile to, Tile from) {
		int tempToX = findTileInTwoDim(list, to)[0];
		int tempToY = findTileInTwoDim(list, to)[1];
		int tempFromX = findTileInTwoDim(list, from)[0];
		int tempFromY = findTileInTwoDim(list, from)[1];
		System.out.println("-------------");
		System.out.println(tempToX + "-" + tempToY);
		System.out.println(tempFromX + "-" + tempFromY);
		System.out.println("-------------");

		Tile toCopy = generateTilesFromTile(to);
		Tile fromCopy = generateTilesFromTile(from);
		System.out.println(toCopy);
		System.out.println(fromCopy);

		list[tempToY][tempToX] = fromCopy;
		list[tempFromY][tempFromX] = toCopy;
		System.out.println(list[tempToX][tempToY]);
		System.out.println(list[tempFromX][tempFromY]);

	}

	public void updatePosition(Tile to, Tile from) {
		int tempToX = to.positionX;
		int tempToY = to.positionY;
		to.positionX = from.positionX;
		to.positionY = from.positionY;
		from.positionX = tempToX;
		from.positionY = tempToY;

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

	public void oneDimensionToTwo(ArrayList<Tile> list) {
		ArrayList<ArrayList<Tile>> graph = new ArrayList<>(2);

	}

	public void assignObjectFormove(Tile tile) {
		objectForMove = tile;

	}

	private void assignOneGridToOtheraa(GridPane gridPane, Tile tile1, Tile tile2, ArrayList<Tile> tileList) {

		for (Tile object2 : tileList) {

			System.out.println(tileList.indexOf(object2));
			System.out.println(object2);
		}
//		int index1 = gridPane.getChildren().indexOf(tile1);
//		int index2 = gridPane.getChildren().indexOf(tile2);
//		Tile tile1copy = generateTilesFromTile(tile1);
//		Tile tile2copy = generateTilesFromTile(tile2);
//
//		gridPane.getChildren().set(index1, tile2copy);
//		gridPane.getChildren().set(index2, tile1copy);
//		for (Object object2 : list) {
//
//			System.out.println(list.indexOf(object2));
//			System.out.println(object2);
//		}

	}

	private void assignOneGridToOther(GridPane gridPane) {
		ObservableList<Node> list = gridPane.getChildren();
		for (Object object : list) {
			Tile tile = (Tile) object;
			tile.setOnMouseClicked((e) -> {
				System.out.println("**********");
				System.out.println(gridPane.getColumnIndex(tile));

				Tile tile1 = (Tile) gridPane.getChildren().get(0);
				Tile tile2 = (Tile) gridPane.getChildren().get(1);
				System.out.println(tile1);
				System.out.println(tile2);
				System.out.println("----");
				int index1 = gridPane.getChildren().indexOf(tile1);
				int index2 = gridPane.getChildren().indexOf(tile2);

				System.out.println(index1);
				System.out.println(index2);
				System.out.println("----");

				// gridPane.getChildren().remove(tile1);
				// gridPane.getChildren().remove(tile2);
				System.out.println(tile2.path);
				System.out.println(tile1.path);

				Tile emptyTile = generateTilesFromTile(tile2);
				Tile startTile = generateTilesFromTile(tile1);
				gridPane.getChildren().set(index1, emptyTile);
				gridPane.getChildren().set(index2, tile1);
				// gridPane.getChildren().remove(tile2);
				// gridPane.getChildren().add(0, tile2);
				System.out.println("**********-------");

				tile1 = (Tile) gridPane.getChildren().get(index1);
				tile2 = (Tile) gridPane.getChildren().get(index2);
				System.out.println(tile1);
				System.out.println(tile2);
				System.out.println("----");
				System.out.println(gridPane.getChildren().indexOf(tile1));
				System.out.println(gridPane.getChildren().indexOf(tile2));
				System.out.println("----");
				//
				// System.out.println(tile.getTileId());
				ObservableList<Node> list2 = gridPane.getChildren();
				for (Object object2 : list2) {

					System.out.println(list2.indexOf(object2));
					System.out.println(object2);
				}
			});
			// System.out.println(tile.getTileId());
			System.out.println(list.indexOf(object));
			System.out.println(object);
		}
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

	private void readFromFile(ArrayList<Tile> tileList) {
		try {
			File directoryPath = new File("levels");
			// List of all files and directories
			String contents[] = directoryPath.list();
			System.out.println("List of files and directories in the specified directory:");
			for (String string : contents) {
				System.out.println(string);

			}
			for (int i = 9; i < 10; i++) {

				System.out.println("levels/" + contents[i]);
				File myObj = new File("levels/" + contents[i]);

				Scanner myReader = new Scanner(myObj);
				while (myReader.hasNextLine()) {

					String data = myReader.nextLine();
					data = data.replaceAll("\\s", "");
					if (data.equals("")) {
						data = "";
						// System.out.println("********************************************");
					} else {
						String[] list = data.trim().split(",");
						tileList.add(generateTiles(list));

					}
				}
				System.out.println("----------");
				myReader.close();
			}

		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

	private Tile generateTiles(String[] list) {
		String id = list[0];
		String type = list[1].toLowerCase();
		String property = list[2].toLowerCase();
		String imagePath;
		switch (type) {
		case "starter":
			Image image;
			ImageView imageView;
			switch (property) {

			case "vertical":
				imagePath = "tiles/Starter-Vertical.png";

				break;
			case "horizontal":
				imagePath = "tiles/Starter-Horizontal.png";

				break;

			default:
				imagePath = "tiles/Starter-Vertical.png";

				break;
			}
			return new StartTile(id, type, property, imagePath);

		case "end":
			switch (property) {

			case "vertical":
				imagePath = "tiles/End-Vertical.png";

				break;
			case "horizontal":
				imagePath = "tiles/End-Horizontal.png";

				break;

			default:
				imagePath = "tiles/End-Vertical.png";

				break;
			}
			return new EndTile(id, type, property, imagePath);

		case "empty":

			switch (property) {
			case "none":
				imagePath = "tiles/Empty-None.png";
				return new EmptyTile(id, type, property, imagePath);
			case "free":
				imagePath = "tiles/Empty-Free.png";
				return new EmptyFreeTile(id, type, property, "tiles/Empty-Free.png");
			default:
				imagePath = "tiles/Empty-None.png";
				break;
			}
			return new EmptyFreeTile(id, type, property, "tiles/Empty-Free.png");

		case "pipe":
			switch (property) {
			case "vertical":
				imagePath = "tiles/Pipe-Vertical.png";
				break;
			case "horizontal":
				imagePath = "tiles/Pipe-Horizontal.png";
				break;
			case "00":
				imagePath = "tiles/Pipe-00.png";
				return new CurvedPipe(id, type, property, imagePath);
			case "01":
				imagePath = "tiles/Pipe-01.png";
				return new CurvedPipe(id, type, property, imagePath);
			case "10":
				imagePath = "tiles/Pipe-10.png";
				return new CurvedPipe(id, type, property, imagePath);
			case "11":
				imagePath = "tiles/Pipe-11.png";
				return new CurvedPipe(id, type, property, imagePath);
			default:
				imagePath = "tiles/Pipe-Vertical.png";
				break;
			}
			return new PipeTile(id, type, property, imagePath);
		case "pipestatic":
			switch (property) {

			case "vertical":
				imagePath = "tiles/PipeStatic-Vertical.png";

				break;
			case "horizontal":
				imagePath = "tiles/PipeStatic-Horizontal.png";

				break;
			case "00":
				imagePath = "tiles/PipeStatic-00.png";
				return new CurvedPipeStatic(id, type, property, imagePath);
			case "01":
				imagePath = "tiles/PipeStatic-01.png";
				return new CurvedPipeStatic(id, type, property, imagePath);

			case "10":
				imagePath = "tiles/PipeStatic-10.png";

				return new CurvedPipeStatic(id, type, property, imagePath);
			case "11":
				imagePath = "tiles/PipeStatic-11.png";

				return new CurvedPipeStatic(id, type, property, imagePath);

			default:
				imagePath = "tiles/PipeStatic-Vertical.png";

				break;
			}
			return new PipeStatic(id, type, property, imagePath);

		default:
			return new EmptyFreeTile(id, type, property, "tiles/Empty-Free.png");

		}
		// TODO Auto-generated method stub

	}

	private Tile generateTilesFromTile(Tile tile) {
		String id = tile.getTileId();
		String type = tile.typeNormal;
		String property = tile.propertyNormal;
		String imagePath;
		switch (type) {
		case "starter":
			Image image;
			ImageView imageView;
			switch (property) {

			case "vertical":
				imagePath = "tiles/Starter-Vertical.png";

				break;
			case "horizontal":
				imagePath = "tiles/Starter-Horizontal.png";

				break;

			default:
				imagePath = "tiles/Starter-Vertical.png";

				break;
			}
			return new StartTile(id, type, property, imagePath);

		case "end":
			switch (property) {

			case "vertical":
				imagePath = "tiles/End-Vertical.png";

				break;
			case "horizontal":
				imagePath = "tiles/End-Horizontal.png";

				break;

			default:
				imagePath = "tiles/End-Vertical.png";

				break;
			}
			return new EndTile(id, type, property, imagePath);

		case "empty":

			switch (property) {
			case "none":
				imagePath = "tiles/Empty-None.png";
				return new EmptyTile(id, type, property, imagePath);
			case "free":
				imagePath = "tiles/Empty-Free.png";
				return new EmptyFreeTile(id, type, property, "tiles/Empty-Free.png");
			default:
				imagePath = "tiles/Empty-None.png";
				break;
			}
			return new EmptyFreeTile(id, type, property, "tiles/Empty-Free.png");

		case "pipe":
			switch (property) {
			case "vertical":
				imagePath = "tiles/Pipe-Vertical.png";
				break;
			case "horizontal":
				imagePath = "tiles/Pipe-Horizontal.png";
				break;
			case "00":
				imagePath = "tiles/Pipe-00.png";
				return new CurvedPipe(id, type, property, imagePath);
			case "01":
				imagePath = "tiles/Pipe-01.png";
				return new CurvedPipe(id, type, property, imagePath);
			case "10":
				imagePath = "tiles/Pipe-10.png";
				return new CurvedPipe(id, type, property, imagePath);
			case "11":
				imagePath = "tiles/Pipe-11.png";
				return new CurvedPipe(id, type, property, imagePath);
			default:
				imagePath = "tiles/Pipe-Vertical.png";
				break;
			}
			return new PipeTile(id, type, property, imagePath);
		case "pipestatic":
			switch (property) {

			case "vertical":
				imagePath = "tiles/PipeStatic-Vertical.png";

				break;
			case "horizontal":
				imagePath = "tiles/PipeStatic-Horizontal.png";

				break;
			case "00":
				imagePath = "tiles/PipeStatic-00.png";
				return new CurvedPipeStatic(id, type, property, imagePath);
			case "01":
				imagePath = "tiles/PipeStatic-01.png";
				return new CurvedPipeStatic(id, type, property, imagePath);

			case "10":
				imagePath = "tiles/PipeStatic-10.png";

				return new CurvedPipeStatic(id, type, property, imagePath);
			case "11":
				imagePath = "tiles/PipeStatic-11.png";

				return new CurvedPipeStatic(id, type, property, imagePath);

			default:
				imagePath = "tiles/PipeStatic-Vertical.png";

				break;
			}
			return new PipeStatic(id, type, property, imagePath);

		default:
			return new EmptyFreeTile(id, type, property, "tiles/Empty-Free.png");

		}
		// TODO Auto-generated method stub

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
