package exbot;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import javabot.JNIBWAPI;
import javabot.model.Unit;
import javabot.types.UnitType;
import javabot.types.UnitType.UnitTypes;
import javabot.util.BWColor;

public class MinisterOfAggresion extends Minister {
	Contract currentContract;
	int debugX;
	int debugY;
	
	public MinisterOfAggresion(Exapi exapi, BasicOrders basicOrders,
			ContractQueue contractQueue) {
		super(exapi, basicOrders, contractQueue);

		currentContract = null;
	}

	@Override
	public void gameUpdate() {
		if (currentContract == null)
		{	
			if(exapi.getPlayerUnits(UnitTypes.Terran_Barracks).isEmpty() && !exapi.areWeBuilding(UnitTypes.Terran_Barracks))
			{
				UnitType barracksType = exapi.getUnitType(UnitTypes.Terran_Barracks);
				currentContract = new Contract(barracksType.getMineralPrice(), barracksType.getGasPrice(), this);
				contractQueue.addContract(currentContract);
			}
			else
			{
				exapi.getBaseApi().train(exapi.getPlayerUnits(UnitTypes.Terran_Barracks).get(0).getID(), UnitTypes.Terran_Marine.ordinal());
				
				Point pos = getBuildTile(exapi.getPlayerUnits(UnitTypes.Terran_SCV).get(0).getID(), UnitTypes.Terran_Supply_Depot.ordinal(), homeX, homeY);
				exapi.build(exapi.getPlayerUnits(UnitTypes.Terran_SCV).get(0), pos.x, pos.y, UnitTypes.Terran_Supply_Depot);
			}
		}
		
		exapi.getBaseApi().drawCircle(debugX * 32 + 16, debugY * 32 +16, 10, BWColor.CYAN, true, false);
	}
	
	@Override
	public void contractFulfilled(IContractInfo contractInfo) {
		ArrayList<Unit> workers = exapi.getPlayerUnits(UnitTypes.Terran_SCV);
		ArrayList<Unit> commandCenters = exapi.getPlayerUnits(UnitTypes.Terran_Command_Center);
		
		if (!workers.isEmpty())
		{
			Unit builder = workers.get(0);
			Point pos = new Point();
			
			if (commandCenters.isEmpty())
			{
				pos.x = builder.getX();
				pos.y = builder.getY();
			}
			else
			{
				pos.x = commandCenters.get(0).getX();
				pos.y = commandCenters.get(0).getY();
			}
			
			pos = getBuildTile(workers.get(0).getID(), UnitTypes.Terran_Barracks.ordinal(), pos.x, pos.y);
			
			System.out.println("Building barracks");
			System.out.println(builder.getTypeID() + " " + UnitTypes.Terran_SCV.ordinal());
			exapi.build(builder, pos.x, pos.y, UnitTypes.Terran_Barracks);
			debugX = pos.x;
			debugY = pos.y;
		}
		
		currentContract = null;
	}
	
	public Point getBuildTile(int builderID, int buildingTypeID, int x, int y) {
		Point ret = new Point(-1, -1);
		int maxDist = 3;
		int stopDist = 40;
		int tileX = x/32; int tileY = y/32;
		
		JNIBWAPI bwapi = exapi.getBaseApi();
		// Refinery, Assimilator, Extractor
		if (bwapi.getUnitType(buildingTypeID).isRefinery()) {
			for (Unit n : bwapi.getNeutralUnits()) {
				if ((n.getTypeID() == UnitTypes.Resource_Vespene_Geyser.ordinal()) && 
						( Math.abs(n.getTileX()-tileX) < stopDist ) &&
						( Math.abs(n.getTileY()-tileY) < stopDist )
						) return new Point(n.getTileX(),n.getTileY());
			}
		}
		
		while ((maxDist < stopDist) && (ret.x == -1)) {
			for (int i=tileX-maxDist; i<=tileX+maxDist; i++) {
				for (int j=tileY-maxDist; j<=tileY+maxDist; j++) {
					if (bwapi.canBuildHere(builderID, i, j, buildingTypeID, false)) {
						// units that are blocking the tile
						boolean unitsInWay = false;
						for (Unit u : bwapi.getAllUnits()) {
							if (u.getID() == builderID) continue;
							if ((Math.abs(u.getTileX()-i) < 4) && (Math.abs(u.getTileY()-j) < 4)) unitsInWay = true;
						}
						if (!unitsInWay) {
							ret.x = i; ret.y = j;
							return ret;
						}
					}
				}
			}
			maxDist += 2;
		}
		
		if (ret.x == -1) bwapi.printText("Unable to find suitable build position for "+bwapi.getUnitType(buildingTypeID).getName());
		return ret;
	}

}
