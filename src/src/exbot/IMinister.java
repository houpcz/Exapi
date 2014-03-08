package exbot;

import javabot.model.Player;
import javabot.model.Unit;

public interface IMinister {
	public void gameEnded();
	public void keyPressed(int keyCode);
	public void matchEnded(boolean winner);
	public void nukeDetect(int x, int y);
	public void nukeDetect();
	public void playerLeft(Player player);
	public void unitCreate(Unit unit);
	public void unitDestroy(Unit unit);
	public void unitDiscover(Unit unit);
	public void unitEvade(Unit unit);
	public void unitHide(Unit unit);
	public void unitMorph(Unit unit);
	public void unitShow(Unit unit);
	public void workerIsIdle(Unit worker);
	public void gameStart();
	public void gameUpdate();
}
