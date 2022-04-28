package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import application.Abstract.Tile;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Helper {
	public static void readFromFile(ArrayList<Tile> tileList) {
		try {
			File directoryPath = new File("levels");
			// List of all files and directories
			String contents[] = directoryPath.list();
			System.out.println("List of files and directories in the specified directory:");
			for (String string : contents) {
				System.out.println(string);

			}
			for (int i = 4; i < 5; i++) {

				System.out.println("levels/" + contents[i]);
				File myObj = new File("levels/" + contents[i]);

				Scanner myReader = new Scanner(myObj);
				while (myReader.hasNextLine()) {

					String data = myReader.nextLine();
					data = data.replaceAll("\\s", "");
					if (data.equals("")) {
						data = "";
					} else {
						String[] list = data.trim().split(",");
						tileList.add(Helper.generateTiles(list));

					}
				}
				myReader.close();
			}

		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

	public static Tile generateTiles(String[] list) {
		String id = list[0];
		String type = list[1].toLowerCase();
		String property = list[2].toLowerCase();
		String imagePath;
		switch (type) {
		case "starter":

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

	public static Tile generateTilesFromTile(Tile tile) {
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
}
