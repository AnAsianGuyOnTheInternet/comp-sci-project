/*
Michael Wenzel	
CMIS202
02/26/24
RandomDistribution.java
Provides pseudorandomly generated double values from varying potential distributions
*/
abstract class RandomDistribution {
	abstract double sample();
}
//Exponential Distribution
class ExponentialDistribution extends RandomDistribution {
	private double lambda;
	public ExponentialDistribution(double lambda) {
		this.lambda = lambda;
	}
	public double sample() {
		return -Math.log(1 - Math.random()) / lambda;
	}
}