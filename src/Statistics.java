import java.util.ArrayList;

public class Statistics {
    public static double getVariance(ArrayList<Integer> numEvent)
    {
        double mean = getAverage(numEvent);
        double temp = 0;
        for (int a : numEvent)
            temp += (mean - a) * (mean - a);
        return temp / numEvent.size();
    }

    public static double getStd(ArrayList<Integer> numEvent) {
        return Math.sqrt(getVariance(numEvent));
    }

    public static double getAverage(ArrayList<Integer> numEvent) {
        double sum = 0.0;
        for (int a : numEvent)
            sum += a;
        return sum / numEvent.size();
    }
    
    public static float getSum(float[] value) {
    	float sum = 0.0f;
        for (float a : value)
            sum += a;
        return sum;
    }
}
