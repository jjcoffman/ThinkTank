package gameAssets;

public class Cichlid
{
	private float speed;
	private String sex;
	private float size;
	private Strategy strategy;
	//have to determine which we want to be our own types or existing
	public Cichlid()
	{
		
	}
	
	public Cichlid(float siz, float spee, String se){
		speed = spee;
		sex = se; 
		size = siz;
	}
	public String getSex(){
		return sex;
	}
	public float getSpeed(){
		return speed;
	}
	public Strategy getStrategy(){
		return strategy;
	}
	public float getSize(){
		return size;
	}
	public void setSpeed(float spee){
		speed = spee;
	}
	public void setSex(String se){
		sex = se;
	}
	public void setStrategy(Strategy strat){
		strategy = strat;
	}
	public void setSize(float siz){
		size = siz;
	}
}
