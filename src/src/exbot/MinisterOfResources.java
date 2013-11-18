package exbot;

import javabot.model.Unit;
import javabot.types.UnitType.UnitTypes;

public class MinisterOfResources extends Minister {

	public MinisterOfResources(Exapi exapi, BasicOrders basicOrders, ContractQueue contractQueue) {
		super(exapi, basicOrders, contractQueue);
		
	}
	
	@Override
	public void workerIsIdle(Unit worker) {
		gatherResource(worker, UnitTypes.Resource_Mineral_Field.ordinal());
	}
	
	public boolean gatherResource(Unit worker, int resourceType) {
		Unit nearestUnit = exapi.findNearestNeutralUnit(worker, resourceType);
		
		if (nearestUnit != null)
		{
			exapi.getBaseApi().gather(worker.getID(), nearestUnit.getID());
			return true;
		}
		
		return false;
	}

	@Override
	public void gameUpdate() {
		// TODO Auto-generated method stub
		
	}

}
