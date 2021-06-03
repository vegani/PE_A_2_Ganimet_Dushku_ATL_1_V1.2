
public class Car_Class 
{
	
	
	
	public String Marke;
	public String Modell;
	public String Hubraum;
	public int PS ;
	public int Nm ;
	
	public Car_Class(String marke, String modell, String hubraum, int pS, int nm) 
	{
		super();
		Marke = marke;
		Modell = modell;
		Hubraum = hubraum;
		PS = pS;
		Nm = nm;
	}
	
	public String getMarke() 
	{
		return Marke;
	}
	
	public void setMarke(String marke) 
	{
		Marke = marke;
	}
	
	public String getModell() 
	{
		return Modell;
	}
	
	public void setModell(String modell) 
	{
		Modell = modell;
	}
	
	public String getHubraum() 
	{
		return Hubraum;
	}
	
	public void setHubraum(String hubraum) 
	{
		Hubraum = hubraum;
	}
	
	public int getPS() 
	{
		return PS;
	}
	
	public void setPS(int pS) 
	{
		PS = pS;
	}
	
	public int getNm() 
	{
		return Nm;
	}
	
	public void setNm(int nm) 
	{
		Nm = nm;
	}
	
	

}
