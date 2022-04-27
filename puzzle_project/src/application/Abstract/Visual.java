package application.Abstract;

abstract public class Visual extends Tile {
	public Visual(String id, String type, String property, String path) {
		super(id, type, property, path);
//		setImage(path);
//
//		super.setImage(getImageFromVisual());

		// TODO Auto-generated constructor stub
	}

	public Visual(Visual visual) {
		super(visual);
//		setImage(path);
//
//		super.setImage(getImageFromVisual());

		// TODO Auto-generated constructor stub
	}

//	private Image image;
//
//	public Image getImageFromVisual() {
//		return image;
//	}
//
//	public void setImage(String path) {
//
//		Image img2 = new Image(getClass().getResource(path).toExternalForm());
//		image = img2;
//		ImageView imageView = new ImageView(img2);
//		imageView.setFitHeight(100);
//		imageView.setFitWidth(100);
//		// this.image = imageView;
//	}

}
