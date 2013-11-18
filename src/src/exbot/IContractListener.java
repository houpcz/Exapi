package exbot;

public interface IContractListener {

	void queuePositionChanged(int position);

	void contractFulfilled(IContractInfo contractInfo);

}
