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
    
    public static double getSum(ArrayList<Integer> numEvent) {
        double sum = 0.0;
        for (int a : numEvent)
            sum += a;
        return sum;
    }
    
    public static double getVarianceFloat(ArrayList<Float> numEvent)
    {
        double mean = getAverageFloat(numEvent);
        double temp = 0;
        for (float a : numEvent)
            temp += (mean - a) * (mean - a);
        return temp / numEvent.size();
    }

    public static double getStdFloat(ArrayList<Float> numEvent) {
        return Math.sqrt(getVarianceFloat(numEvent));
    }

    public static double getAverageFloat(ArrayList<Float> numEvent) {
        double sum = 0.0;
        for (float a : numEvent)
            sum += a;
        return sum / numEvent.size();
    }
    
    public static double getSumFloat(ArrayList<Float> numEvent) {
        double sum = 0.0;
        for (float a : numEvent)
            sum += a;
        return sum;
    }
    
    public static float getSum(float[] value) {
    	float sum = 0.0f;
        for (float a : value)
            sum += a;
        return sum;
    }
    
    public static int getMax(ArrayList<Integer> numEvent) {
    	int sum = 0;
        for (int a : numEvent){
            sum = a > sum ? a : sum;
        }
        return sum;
    }
}
