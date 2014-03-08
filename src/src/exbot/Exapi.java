package exbot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javabot.JNIBWAPI;
import javabot.model.Unit;
import javabot.types.UnitType;
import javabot.types.UnitType.UnitTypes;

public class Exapi {
	JNIBWAPI bwapi;
	private HashMap<Integer, ArrayList<Unit>> playerUnitsMap;
	private HashMap<Integer, ArrayList<Unit>> alliedUnitsMap;
	private HashMap<Integer, ArrayList<Unit>> enemyUnitsMap;
	private HashMap<Integer, ArrayList<Unit>> neutralUnitsMap;
	final ArrayList<Unit> emptyUnitsArrayList;
	
	public Exapi(JNIBWAPI bwapi)
	{
		this.bwapi = bwapi;
		playerUnitsMap = new HashMap<Integer, ArrayList<Unit>>();
		alliedUnitsMap = new HashMap<Integer, ArrayList<Unit>>();
		enemyUnitsMap = new HashMap<Integer, ArrayList<Unit>>();
		neutralUnitsMap = new HashMap<Integer, ArrayList<Unit>>();
		emptyUnitsArrayList = new ArrayList<Unit>();
	}

	public JNIBWAPI getBaseApi()
	{
		return bwapi;
	}
	
	public void build(Unit worker, int posX, int posY, UnitTypes buildingType)
	{
		bwapi.build(worker.getID(), posX, posY, buildingType.ordinal());
		worker.setConstructingTypeID(buildingType.ordinal());
		System.out.println("build " + worker.getID() + " " + posX + " " + posY + " " + buildingType.ordinal());
	}
	
	public boolean canBuildHere(Unit worker, int posX, int posY, UnitTypes buildingType)
	{
		return bwapi.canBuildHere(worker.getID(), posX, posY, buildingType.ordinal(), false);
	}
	
	public boolean areWeBuilding(UnitTypes buildingType) {
		for (Unit unit : bwapi.getMyUnits()) {
			if ((unit.getTypeID() == buildingType.ordinal()) && (!unit.isCompleted())) 
			{
				return true;
			}
			if (bwapi.getUnitType(unit.getTypeID()).isWorker() && unit.getConstructingTypeID() == buildingType.ordinal()) 
			{
				System.out.println("building it");
				return true;
			}
		}
		return false;
	}
	
	public UnitType getUnitType(int type)
	{
		return bwapi.getUnitType(type);
	}
	
	public UnitType getUnitType(UnitTypes type)
	{
		return bwapi.getUnitType(type.ordinal());
	}
	
	final ArrayList<Unit> getPlayerUnits(UnitTypes type)
	{
		return getUnits(type, playerUnitsMap);
	}
	
	final ArrayList<Unit> getEnemyUnits(UnitTypes type)
	{
		return getUnits(type, enemyUnitsMap);
	}
	
	final ArrayList<Unit> getAlliedUnits(UnitTypes type)
	{
		return getUnits(type, alliedUnitsMap);
	}
	
	final ArrayList<Unit> getNeutralUnits(UnitTypes type)
	{
		return getUnits(type, neutralUnitsMap);
	}
	
	private final ArrayList<Unit> getUnits(UnitTypes type, HashMap<Integer, ArrayList<Unit>> units)
	{
		if (units.containsKey(type.ordinal()))
		{
			return units.get(type.ordinal());
		}
		
		return emptyUnitsArrayList; 
	}
	
	private void clearHashMap(HashMap<Integer, ArrayList<Unit>> hashMap)
	{
		for (Entry<Integer, ArrayList<Unit>> entry : hashMap.entrySet())
		{
			entry.getValue().clear();
		}
	}
	
	public void gameUpdate()
	{
		clearHashMap(playerUnitsMap);
		clearHashMap(alliedUnitsMap);
		clearHashMap(enemyUnitsMap);
		clearHashMap(neutralUnitsMap);

		fillHashMap(playerUnitsMap, bwapi.getMyUnits());
		fillHashMap(alliedUnitsMap, bwapi.getAlliedUnits());
		fillHashMap(enemyUnitsMap, bwapi.getEnemyUnits());
		fillHashMap(neutralUnitsMap, bwapi.getNeutralUnits());
	}
	
	private void fillHashMap(HashMap<Integer, ArrayList<Unit>> unitsMap, ArrayList<Unit> units) {
		for (Unit unit : units)
		{
			if (!unitsMap.containsKey(unit.getTypeID()))
			{
				if (unit.getTypeID() == UnitTypes.Terran_SCV.ordinal())
				{
					System.out.println("jj");
				}
				unitsMap.put(unit.getTypeID(), new ArrayList<Unit>());
			}
			unitsMap.get(unit.getTypeID()).add(unit);
		}
	}

	public Unit findNearestNeutralUnit(Unit unit, int wantedUnitType) {
		return findNearestUnit(unit, wantedUnitType, bwapi.getNeutralUnits());
	}
	
	public Unit findNearestUnit(Unit unit, int wantedUnitType, ArrayList<Unit> wantedCandidates)
	{
		if (wantedCandidates.isEmpty())
		{
			return null;
		}
		
		double smallestDistance = squareDistance(wantedCandidates.get(0), unit);
		Unit bestUnit = wantedCandidates.get(0);
		
		for (Unit wantedCandidate : wantedCandidates)
		{
			double tempDistance = squareDistance(wantedCandidate, unit);
			if (wantedCandidate.getTypeID() == wantedUnitType && tempDistance < smallestDistance)
			{
				smallestDistance = tempDistance;
				bestUnit = wantedCandidate;
			}
		}
		
		return bestUnit;
	}
	
	public double squareDistance(Unit unitA, Unit unitB)
	{
		double dX = unitA.getX() - unitB.getX();
		double dY = unitA.getY() - unitB.getY();
		return dX * dX + dY * dY;
	}
}
