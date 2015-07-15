package esgi.project.ratpdroid.model;

public class Line {

	private int id;
	private String shortName;
	private String longName;
	private int type;

	public Line() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getLongName() {
		return longName;
	}

	public void setLongName(String longName) {
		this.longName = longName;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String toString() {

		String typeTransport = "";

		switch (type) {
		case 0:
			typeTransport = "TRAMWAY";
			break;

		case 1:
			typeTransport = "METRO";
			break;

		case 2:
			typeTransport = "RER";
			break;

		case 3:
			typeTransport = "BUS";
			break;
		}

		return typeTransport + " " + shortName;
	}
}
