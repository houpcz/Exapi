package javabot;

/**
 * Example of a Java AI Client that does nothing.
 */
import java.awt.Point;
import java.util.ArrayList;

import exbot.BasicOrders;
import exbot.ContractQueue;
import exbot.Exapi;
import exbot.IMinister;
import exbot.MinisterOfAggresion;
import exbot.MinisterOfResources;
import javabot.model.Unit;
import javabot.types.UnitType;
import javabot.types.UnitType.UnitTypes;
import javabot.util.BWColor;
public class MinimalAIClient implements BWAPIEventListener {

	JNIBWAPI bwapi;
	Exapi exapi;
	BasicOrders basicOrders;
	ContractQueue contractQueue;
	ArrayList<IMinister> ministers;
	
	public static void main(String[] args) {
		new MinimalAIClient();
	}
	
	public MinimalAIClient() {
		bwapi = new JNIBWAPI(this);
		exapi = new Exapi(bwapi);
		basicOrders = new BasicOrders(exapi);
		contractQueue = new ContractQueue(exapi);
		ministers = new ArrayList<IMinister>();
		ministers.add(new MinisterOfResources(exapi, basicOrders, contractQueue));
		ministers.add(new MinisterOfAggresion(exapi, basicOrders, contractQueue));
		
		bwapi.start();
	} 

	public void connected() {
		bwapi.loadTypeData();
	}	
	
	public void gameStarted() 
	{
		// allow me to manually control units during the game
		bwapi.enableUserInput();
		
		// set game speed to 30 (0 is the fastest. Tournament speed is 20)
		// You can also change the game speed from within the game by "/speed X" command.
		bwapi.setGameSpeed(100);
		
		// analyze the map
		bwapi.loadMapData(true);
				
		bwapi.printText("This map is called "+bwapi.getMap().getName());
		bwapi.printText("My race ID: "+String.valueOf(bwapi.getSelf().getRaceID()));				// Z=0,T=1,P=2
		bwapi.printText("Enemy race ID: "+String.valueOf(bwapi.getEnemies().get(0).getRaceID()));	// Z=0,T=1,P=2
		
		bwapi.printText("Enemy unit count "+ String.valueOf(bwapi.getEnemyUnits().size()));
		
		for (IMinister minister : ministers)
		{
			minister.gameStart();
		}
	}
	
	private void customEvents()
	{
		for(Unit unit : bwapi.getMyUnits())
		{
			if(unit.isWorker() && unit.isIdle())
			{
				bwapi.printText("Idle worker " + unit.getID());
				workerIsIdle(unit);
			}
		}
	}
	
	int frame = 0;
	public void gameUpdate() 
	{
		exapi.gameUpdate();
		customEvents();
		
		contractQueue.update(bwapi.getSelf());
		for (IMinister minister : ministers)
		{
			minister.gameUpdate();
		}
		
		for(Unit unit : bwapi.getAllUnits())
		{
			int color = BWColor.BLUE;
			if (unit.isIdle())
			{
				color = BWColor.YELLOW;
			}
			
			bwapi.drawCircle(unit.getX(), unit.getY(), 5, color, true, false);
			
			UnitType type = exapi.getUnitType(unit.getTypeID());
			bwapi.drawBox(unit.getTileX() * 32, unit.getTileY() * 32, (unit.getTileX() + type.getTileWidth()) * 32, (unit.getTileY() + type.getTileHeight()) * 32, BWColor.BROWN, false, false);
		}
		
		//bwapi.printText("Enemy unit count "+ String.valueOf(bwapi.getEnemyUnits().size()));
		for(Unit u : bwapi.getEnemyUnits())
		{
			bwapi.drawCircle(u.getX(), u.getY(), 5, BWColor.RED, true, false);
			bwapi.drawText(u.getTileX(), u.getTileY(), String.valueOf(u.getHitPoints()), false);
			bwapi.drawText(new Point(u.getTileX(), u.getTileY()), "aaabbb", false);
			bwapi.drawCircle(u.getX(), u.getY(), 5, BWColor.GREEN, true, false);
		}
	}
	
	private void workerIsIdle(Unit worker)
	{
		for (IMinister minister : ministers)
		{
			minister.workerIsIdle(worker);
		}
	}
	
	public void gameEnded() {
		for (IMinister minister : ministers)
		{
			minister.gameEnded();
		}
	}
	public void keyPressed(int keyCode) {
		for (IMinister minister : ministers)
		{
			minister.keyPressed(keyCode);
		}
	}
	public void matchEnded(boolean winner) { 
		for (IMinister minister : ministers)
		{
			minister.matchEnded(winner);
		}
	}
	public void nukeDetect(int x, int y) { 
		for (IMinister minister : ministers)
		{
			minister.nukeDetect(x, y);
		}
	}
	public void nukeDetect() { 
		for (IMinister minister : ministers)
		{
			minister.nukeDetect();
		}
	}
	public void playerLeft(int id) { 
		for (IMinister minister : ministers)
		{
			minister.playerLeft(bwapi.getPlayer(id));
		}
	}
	public void unitCreate(int unitID) 
	{
		for (IMinister minister : ministers)
		{
			minister.unitCreate(bwapi.getUnit(unitID));
		}
	}
	public void unitDestroy(int unitID) {
		for (IMinister minister : ministers)
		{
			minister.unitDestroy(bwapi.getUnit(unitID));
		}
	}
	public void unitDiscover(int unitID) { 
		for (IMinister minister : ministers)
		{
			minister.unitDiscover(bwapi.getUnit(unitID));
		}
	}
	public void unitEvade(int unitID) { 
		for (IMinister minister : ministers)
		{
			minister.unitEvade(bwapi.getUnit(unitID));
		}
	}
	public void unitHide(int unitID) { 
		for (IMinister minister : ministers)
		{
			minister.unitHide(bwapi.getUnit(unitID));
		}
	}
	public void unitMorph(int unitID) {
		for (IMinister minister : ministers)
		{
			minister.unitMorph(bwapi.getUnit(unitID));
		}
	}
	public void unitShow(int unitID) { 
		for (IMinister minister : ministers)
		{
			minister.unitShow(bwapi.getUnit(unitID));
		}
	}
}
