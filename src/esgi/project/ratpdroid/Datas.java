package esgi.project.ratpdroid;

import java.util.List;

import esgi.project.ratpdroid.model.Line;
import esgi.project.ratpdroid.model.Stop;

public class Datas {

	private static Datas _instance;
	private Line currentLine;
	private Stop currentStop;
	
	private List<Stop> stops;
		
	public static Datas GetInstance()
	{
		if (_instance == null)
			_instance = new Datas();
		
		return _instance;
	}
	
	public void SetStops(List<Stop> stops)
	{
		this.stops = stops;
	}
	
	public List<Stop> GetStops()
	{
		return stops;
	}
	
	public void SetCurrentLine(Line line)
	{
		this.currentLine = line;
	}
	
	public Line GetCurrentLine()
	{
		return currentLine;
	}
	
	public void SetCurrentStop(Stop stop)
	{
		this.currentStop = stop;
	}
	
	public Stop GetCurrentStop()
	{
		return currentStop;
	}
}
