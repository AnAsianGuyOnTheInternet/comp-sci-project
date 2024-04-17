import java.util.Random;
import java.lang.Math;

//abstract class that calculates the exponential distribution
abstract class RandomDistribution {
    abstract double sample();

}
class ExponentialDistribution extends RandomDistribution
{
    private double lambda;
    private Random random;
    public double sample() {
        return (-1.0/lambda)*Math.log(random.nextDouble());
    }

    public ExponentialDistribution(double lamb, Random rand)
    {
        lambda = lamb;
        random = rand;
    }
}

