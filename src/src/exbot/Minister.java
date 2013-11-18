package exbot;

import javabot.model.Player;
import javabot.model.Unit;

public abstract class Minister implements IMinister, IContractListener {
	protected Exapi exapi;
	protected BasicOrders basicOrders;
	protected ContractQueue contractQueue;
	
	public Minister(Exapi exapi, BasicOrders basicOrders, ContractQueue contractQueue)
	{
		this.exapi = exapi;
		this.basicOrders = basicOrders;
		this.contractQueue = contractQueue;
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
