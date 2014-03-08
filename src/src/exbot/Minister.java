package exbot;

import javabot.model.Player;
import javabot.model.Unit;
import javabot.types.UnitType.UnitTypes;

public abstract class Minister implements IMinister, IContractListener {
	protected Exapi exapi;
	protected BasicOrders basicOrders;
	protected ContractQueue contractQueue;
	
	int homeX;
	int homeY;
	
	public Minister(Exapi exapi, BasicOrders basicOrders, ContractQueue contractQueue)
	{
		this.exapi = exapi;
		this.basicOrders = basicOrders;
		this.contractQueue = contractQueue;
	}
	
	public void gameStart() {
		homeX = exapi.getPlayerUnits(UnitTypes.Terran_Command_Center).get(0).getX();
		homeY = exapi.getPlayerUnits(UnitTypes.Terran_Command_Center).get(0).getY();
	}
	
	@Override
	public void queuePositionChanged(int position) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contractFulfilled(IContractInfo contractInfo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gameEnded() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(int keyCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void matchEnded(boolean winner) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void nukeDetect(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void nukeDetect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playerLeft(Player player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unitCreate(Unit unit) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unitDestroy(Unit unit) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unitDiscover(Unit unit) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unitEvade(Unit unit) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unitHide(Unit unit) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unitMorph(Unit unit) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unitShow(Unit unit) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void workerIsIdle(Unit worker) {
		// TODO Auto-generated method stub
		
	}

}
