package exbot;

import javabot.model.Player;

public class Contract implements Comparable<Contract>{
	int gas;
	int minerals;
	int priority;
	
	IContractListener listener;
	IContractInfo contractInfo;
	
	/// when 0, contract was fullfiled
	int queuePosition;
	
	public Contract(int minerals, int gas, IContractListener listener)
	{
		this.minerals = minerals;
		this.gas = gas;
		this.listener = listener;
		priority = 1;
	}
	
	public void setQueuePosition(int position)
	{
		if (queuePosition != position)
		{
			listener.queuePositionChanged(position);
			queuePosition = position;
		}
	}

	public int getPriority()
	{
		return priority;
	}
	
	@Override
	public int compareTo(Contract contractB) {
		if (priority == contractB.priority)
		{
			return 0;
		}
		
		return (priority < contractB.priority) ? 1 : -1;
	}

	public boolean isFulfilled(Player player) {
		if (player.getGas() >= gas && player.getMinerals() >= minerals)
		{
			listener.contractFulfilled(contractInfo);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	
}
