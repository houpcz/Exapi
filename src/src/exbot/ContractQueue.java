package exbot;

import java.util.ArrayList;

import javabot.model.Player;

public class ContractQueue {
	// array list because I want to know specific space of each constract in a queue
	ArrayList<Contract> contractQueue;
	Exapi exapi;
	
	public ContractQueue(Exapi exapi)
	{
		this.exapi = exapi;
		contractQueue = new ArrayList<Contract>();
	}
	
	public void addContract(Contract contract)
	{
		contractQueue.add(contract);
		exapi.getBaseApi().printText("Contract added.");
		System.out.println("Contract added");
		
		for (int i = contractQueue.size() - 2; i >= 0; ++i)
		{
			if (contract.getPriority() > contractQueue.get(i).priority)
			{
				contractQueue.get(i).setQueuePosition(i + 1);
				contractQueue.set(i + 1, contractQueue.get(i));
				contractQueue.set(i, contract);
			}
		}
	}
	
	public void update(Player player)
	{
		while (!contractQueue.isEmpty())
		{
			Contract contract = contractQueue.get(0);
			if (contract.isFulfilled(player))
			{
				exapi.getBaseApi().printText("Contract fulfilled.");
				System.out.println("Contract fulfilled");
				contractQueue.remove(0);
			}
			else
			{
				break;
			}
		}
	}
}
