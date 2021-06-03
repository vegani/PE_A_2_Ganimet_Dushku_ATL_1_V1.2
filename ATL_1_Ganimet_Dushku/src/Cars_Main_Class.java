import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class Cars_Main_Class 

{
	List<Car_Class> carsGlobal = new ArrayList<>();
	List<String> printCarsGlobal = new ArrayList<String>();
	String UserSelectionGlobal = "";
	long countCarsGlobal = 0;
	
	
	// Liest die Daten aus der CSV Datei in die Liste "carsGlobal".
	// p[] liest die Eingaben in die Strings des Objektes ein. 
	// Integer.parseInt konvertiert den Text zu einem int. und speichert diese in die Variable des Objektes.
	public void readData() throws IOException 
	{ 
	   
	
		

		// Pfad zur CSV Datei
	    String file = "./cars.csv";
	    
	    InputStream is = new FileInputStream(new File(file));
	    BufferedReader br = new BufferedReader(new InputStreamReader(is));
	    
	    	    
	    // https://ivarconr.wordpress.com/2013/11/21/streaming-csv-files-with-the-java-8-stream-api/
	    Function<String, Car_Class> mapToCars = (line) -> 
		 {
			 String[] p = line.split(";");
			 
			 return new Car_Class(p[0], p[1], p[2], Integer.parseInt(p[3]), Integer.parseInt(p[4]));
		 };
		 		
		 // Skippt den Header, (überspringt die erste Zeile).
		 // Limit auf 20 Einträge von der CSV setzen (es werden nur die ersten 20 Einträge gelesen)
		 		carsGlobal = br.lines()
                 .skip(1)
                 .map(mapToCars)
                 .limit(20)
                 .collect(toList());	  		
		 		
		 		// Zeig das Menü für den Anwender an indem er/sie eine Auswahl treffen kann.
		 		this.showMenu();
	}	
	
	
	
	// Zeigt das Menü an, damit der User auswählen kann.
	// Speichert die Auswahl vom User in die Variable "userSelection".
	public void showMenu()
	{
		   Scanner userAnswer = new Scanner(System.in);  

		    System.out.println("Wähle 1 um das stärkste Auto zu zeigen");
		    System.out.println("Wähle 2 um das schwächste Auto zu zeigen");
		    System.out.println("Wähle 3 um von stärksten zum schwächsten Auto zu zeigen");
		    System.out.println("Wähle 4 um Autos welche weniger als 500Nm haben zu zeigen.");
		    System.out.println("Gebe die Marke ein um deine Suche zu verkleinern");
		    
		    
		    String userSelection = userAnswer.nextLine();  
		    
		    UserSelectionGlobal = userSelection;
		    
		    this.getUserSelection();
		   
	}
	
	
	
	/// Füllt die Liste anhand Stream Befehln so, wie der User gewählt hat.
	public void getUserSelection()
	{
		
		// Wähle 1 um das stärkste Auto zu zeigen.
		 if(UserSelectionGlobal.equals("1"))
			{	
			 
			 	List<String> fastestCar = carsGlobal.stream()	   
			 		    .filter(Car_Class -> Car_Class.getPS() > 600 )
			 		    .sorted(Comparator.comparingInt(Car_Class::getPS).reversed())
			 		    .limit(1)
			 		    .map(c -> c.getMarke() + " " + c.getModell() + " PS " + c.getPS() + " NM " + c.getNm())
			 		    .collect(Collectors.toList());
			 	
			 	printCarsGlobal = fastestCar;
			
			 	this.writeSelection();
			 		
			}
		 	// Wähle 2 um das schwächste Auto zu zeigen.
			else if(UserSelectionGlobal.equals("2"))
			{
				
				 Optional<Object> slowestCar = carsGlobal.stream()	
				 .min(Comparator.comparingInt(Car_Class::getPS))
				 .map(c -> c.getMarke() + " " + c.getModell() + " PS " + c.getPS() + " NM " + c.getNm());
				 
				
				 // Einziger welcher nicht über die Ausgabemethode ausgegeben wird.
				 System.out.println(slowestCar.get());
				
			}
		 	// Wähle 3 um von stärksten zum schwächsten Auto zu zeigen.
			else if (UserSelectionGlobal.equals("3"))
			{
			
				List<String> sortedCars = carsGlobal.stream()	   				
						 .sorted(Comparator.comparingInt(Car_Class::getPS).reversed())
						 .map(c -> c.getMarke() + " " + c.getModell() + " PS " + c.getPS() + " NM " + c.getNm())
						 .collect(toList());
				
				printCarsGlobal = sortedCars;
				
				this.writeSelection();
				
			}
		 	// Wähle 4 um Autos welche weniger als 500Nm haben zu zeigen.
			else if(UserSelectionGlobal.equals("4"))
			{
				
				// Zählt wie viele Autos unter 500Nm haben
				long countCars = carsGlobal.stream()  
			 		    .filter(Car_Class -> Car_Class.getNm() < 500 )
			 		    .count();
				
				// Stellt die Autos zusammen welcheunter 500Nm haben
				List<String> under500Cars = carsGlobal.stream()	   
						 .filter(Car_Class -> Car_Class.getNm() < 500 )
			 		    .sorted(Comparator.comparingInt(Car_Class::getPS).reversed())
			 		    .map(c -> c.getMarke() + " " + c.getModell() + " PS " + c.getPS() + " NM " + c.getNm())
			 		    .collect(Collectors.toList());
						
				countCarsGlobal = countCars;
				printCarsGlobal = under500Cars;
				
			 		    		 		    
				
				this.writeSelection();
				
			}
		 	// Wenn keine Eingabe getätigt wurde, erscheint diese Meldung.
			else if(UserSelectionGlobal.equals(""))
			{
				System.out.println("Bitte eine Auswahl treffen!");
			}
		 	// Gebe die Marke ein um deine Suche zu verkleinern
			else
			{
				
				List<String> userChoosenMarke = carsGlobal.stream()	   
			 		    .filter(Car_Class -> Car_Class.getMarke().equals(UserSelectionGlobal) )
			 		    .sorted(Comparator.comparingInt(Car_Class::getPS).reversed())
			 		    .map(c -> c.getMarke() + " " + c.getModell() + " PS " + c.getPS() + " NM " + c.getNm())
			 		    .collect(Collectors.toList());
				
				printCarsGlobal = userChoosenMarke;
				
				this.writeSelection();
				
			}
		 
	}
	
	
	
	// Gibt die Liste welche gefüllt wurde, anhand der Selektion vom User aus.
	public void writeSelection()
	{
		
		// Wenn es Autos findet, gibt es folgende Infos aus.
		if(countCarsGlobal > 0)
		{
			System.out.println("Es wurden " + countCarsGlobal + " Autos gefunden.");
			System.out.println(" ");
			printCarsGlobal.stream().forEach(System.out::println);	
		}
		// Wenn die Marke des Autos in der Liste nicht gefunden wird, kommt folgende Meldung.
		else if(printCarsGlobal.isEmpty())
		{
			System.out.println("Die Marke konnte nicht gefunden werden.");
		}
		// Wenn nichts von oben zustimmt (sollte bei Wahl 1-3 der Fall sein) erfolgt die normale Ausgabe der Liste.
		else
		{
			printCarsGlobal.stream().forEach(System.out::println);
		}
										
	}

}
