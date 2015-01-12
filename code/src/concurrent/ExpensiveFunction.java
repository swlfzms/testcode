package concurrent;

import java.math.BigInteger;

public class ExpensiveFunction implements Computable<String,BigInteger>{

	@Override
	public BigInteger compute(String args) throws InterruptedException {
		// TODO Auto-generated method stub
		return new BigInteger(args);
	}
	
}
