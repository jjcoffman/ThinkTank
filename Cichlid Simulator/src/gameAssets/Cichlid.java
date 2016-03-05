package gameAssets;

public class Cichlid extends thinktank.simulator.entity.Fish
{
	private float speed;
	private String sex;
	private Strategy strategy;
	//have to determine which we want to be our own types or existing
	public Cichlid()
	{
		//default
	}
	
	public Cichlid(float size, float speed, String sex){
		super.setSize(size);
		this.speed = speed;
		this.sex = sex;
	}
	public String getSex(){
		return this.sex;
	}
	public float getSpeed(){
		return this.speed;
	}
	public Strategy getStrategy(){
		return this.strategy;
	}
	public void setSpeed(float speed){
		this.speed = speed;
	}
	public void setSex(String sex){
		this.sex = sex;
	}
	public void setStrategy(Strategy strat){
		this.strategy = strat;
	}
}
