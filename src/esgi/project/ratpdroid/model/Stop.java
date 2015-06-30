package esgi.project.ratpdroid.model;

public class Stop implements Comparable<Stop>{

	private int id;
	private String name;
	private double lat;
	private double lon;
	private int position;
	private String idLine;

	public Stop(){
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}
	
	public String getIdLine() {
		return idLine;
	}

	public void setIdLine(String idLine) {
		this.idLine = idLine;
	}

	public String toString(){
		return name;
	}

	@Override
	public int compareTo(Stop another) {
		if(this.position < another.position)
			return -1;
		if(this.position > another.position)
			return 1;
		return 0;
	}
	
}
