package application.Abstract;

import application.Properties;
import application.Types;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

abstract public class Tile extends ImageView {
	private String id;
	public String typeNormal;
	public String propertyNormal;
	private Types type;
	private Properties property;
	public String path;

	public Tile(String id, String type, String property, String path) {
		super();
		this.path = path;
		this.propertyNormal = property;
		this.typeNormal = type;
		setTileId(id);
		setTypes(type);
		setProperties(property);

		setImage(path);

		super.setImage(getImageFromVisual());
		if (this.getTypes() == Types.STARTER && this.getPropertiesFromTile() == Properties.HORIZONTAL) {
			super.setRotate(180);

		}

		super.setFitHeight(100);
		super.setFitWidth(100);
		if (getPropertiesFromTile() == Properties.FREE) {
			super.setOpacity(0);
			System.out.println(id);
		}
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (this.getTileId() == ((Tile) obj).getTileId()) {
			return true;
		}
		return false;
	}

	public Tile(Tile tile) {

		this(tile.getTileId(), tile.typeNormal, tile.propertyNormal, tile.path);

		this.propertyNormal = tile.propertyNormal;
		this.typeNormal = tile.typeNormal;
		setTileId(id);
		setTypes(tile.typeNormal);
		setProperties(tile.propertyNormal);

		setImage(path);

		super.setImage(getImageFromVisual());

		super.setFitHeight(100);
		super.setFitWidth(100);
		if (getPropertiesFromTile() == Properties.FREE) {
			super.setOpacity(0);
			System.out.println(id);
		}

	}

	private Image image;

	public Image getImageFromVisual() {
		return image;
	}

	public void setImage(String path) {

		Image img2 = new Image(getClass().getResource(path).toExternalForm());
		image = img2;

	}

	public String getTileId() {
		return id;
	}

	public void setTileId(String id) {
		this.id = id;
	}

	public Types getTypes() {
		return type;
	}

	public void setTypes(String type) {

		if (type.toLowerCase().equals("starter")) {

			this.type = Types.STARTER;
		} else if (type.toLowerCase().equals("end")) {
			this.type = Types.END;

		} else if (type.toLowerCase().equals("empty")) {
			this.type = Types.EMPTY;

		} else if (type.toLowerCase().equals("pipe")) {
			this.type = Types.PIPE;

		} else if (type.toLowerCase().equals("pipestatic")) {
			this.type = Types.PIPESTATIC;

		} else {
			this.type = Types.EMPTY;

		}
	}

	public void goToRight() {
		System.out.println("RRRRRRRRRRRRRRRRRRRRRRRRRRRRR");
		System.out.println(this);
		TranslateTransition translate = new TranslateTransition();
		translate.setNode(this);
		translate.setByX(100);
		translate.play();
		System.out.println("moving right");

	}

	public void goToLeft() {
		TranslateTransition translate = new TranslateTransition();
		translate.setNode(this);
		translate.setByX(-100);
		translate.play();
		System.out.println("moving left");

	}

	public void goToUp() {
		TranslateTransition translate = new TranslateTransition();
		translate.setNode(this);
		translate.setByY(-100);
		translate.play();
		System.out.println("moving up");

	}

	public void goToDown() {
		TranslateTransition translate = new TranslateTransition();
		translate.setNode(this);
		translate.setByY(+100);
		translate.play();
		System.out.println("moving down");

	}

	public Properties getPropertiesFromTile() {
		return property;
	}

	public void setProperties(String property) {
		if (property.toLowerCase().equals("vertical")) {
			this.property = Properties.VERTICAL;
		} else if (property.toLowerCase().equals("horizontal")) {
			this.property = Properties.HORIZONTAL;
		} else if (property.toLowerCase().equals("none")) {
			this.property = Properties.NONE;
		} else if (property.toLowerCase().equals("free")) {
			this.property = Properties.FREE;
		} else if (property.toLowerCase().equals("00")) {
			this.property = Properties.CURVED_ZERO_ZERO;
		} else if (property.toLowerCase().equals("01")) {
			this.property = Properties.CURVED_ZERO_ONE;
		} else if (property.toLowerCase().equals("10")) {
			this.property = Properties.CURVED_ONE_ZERO;
		} else if (property.toLowerCase().equals("11")) {
			this.property = Properties.CURVED_ONE_ONE;
		} else {
			this.property = Properties.NONE;
		}

	}

//	@Override
//	public String toString() {
//		return "Tile [id=" + id + ", typeNormal=" + typeNormal + ", propertyNormal=" + propertyNormal + ", type=" + type
//				+ ", property=" + property + ", path=" + path + ", image=" + image + "]";
//	}

}
