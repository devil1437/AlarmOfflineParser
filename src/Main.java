import java.io.File;
import java.util.ArrayList;

public class Main {

    final static boolean DEBUG = false;
    final private static boolean ENABLE_TIME_BOUNDARY = true;
    
    public static void main(String[] args) {
//        parseFile("33.alarm", 60 * 60 * 1000);
//        parseFile("34.alarm", 60 * 60 * 1000);
//        parseFile("41.alarm", 60 * 60 * 1000);
//        parseFile("42.alarm", 60 * 60 * 1000);
//    	parseFile("3_4.alarm", 120 * 1000);
//        parseFile("52.alarm", 30 * 60 * 1000);
//        parseFile("3_1_Mine.alarm", 125 * 1000);
//        parseFile("tmp.alarm", 760 * 1000);
        parseFile("62.alarm", 30 * 60 * 1000);
    }

    private static void parseFile(String string, long duration) {
        File file = new File(string);
        Parser parser = new Parser();
        ParserOutput output = parser.parse(file, duration);

        System.out.println("====" + string + "====");
        printHardwareUsageDistribution(output, string);
        printAverageNumEvent(output, string);
        printAlignmentResult(output, string);
        printPerformance(output, string);
    }

    private static void printPerformance(ParserOutput output, String string) {
    	ArrayList<Alarm> alarms = output.mAlarms;
        ArrayList<Integer> delay = new ArrayList<Integer>();

        for (int i = 0; i < alarms.size(); i++) {
            Alarm a = alarms.get(i);
            if (ENABLE_TIME_BOUNDARY && !inBoundary(a, output.mStartTime, output.mEndTime)) {
            	continue;
            }
            if (a.isPerceivable()) {
            	if(DEBUG)	System.out.println("perceivable:" + a);
            	delay.add((int) a.delay);
            }
        }

        System.out.println("====" + string + "====Performance====");
        System.out.println("Average: " + Statistics.getAverage(delay) + ", Std: "
                + Statistics.getStd(delay));
        System.out.println("number: " + delay.size());
		
	}

	private static void printAlignmentResult(ParserOutput output, String string) {
        ArrayList<Alarm> alarms = output.mAlarms;
        int[] hardwareUsageCount = new int[HardwareUsage.NUM_HARDWARE];
        int[][] hardwareUsageSta = new int[HardwareUsage.NUM_HARDWARE][2];
        long time = -1;
        float energySaving[] = new float[HardwareUsage.NUM_HARDWARE+1];
        boolean isWakeupFlag = false;
        
        for (int i = 0; i < alarms.size(); i++) {
            Alarm a = alarms.get(i);
            if (ENABLE_TIME_BOUNDARY && !inBoundary(a, output.mStartTime, output.mEndTime)) {
            	continue;
            }
            if (time == a.time) {
            	if(a.isWakeup()){
            		if(isWakeupFlag)	energySaving[HardwareUsage.NUM_HARDWARE] += HardwareUsage.CPU_SAVING;
            		isWakeupFlag = true;
            	}
                for (int j = 0; j < HardwareUsage.NUM_HARDWARE; j++) {
                    if (a.hardwareUsage[j] != 0) {
                        if (hardwareUsageCount[j] == 0) {
                        	hardwareUsageSta[j][0]++;
                        } else {
                        	energySaving[j] += HardwareUsage.HARDWARE_SAVING[j] - HardwareUsage.CPU_SAVING;
                        }
                        hardwareUsageCount[j]++;
                        hardwareUsageSta[j][1]++;
                    }
                }
                continue;
            } else {
                time = a.time;
                isWakeupFlag = a.isWakeup();
                hardwareUsageCount = new int[HardwareUsage.NUM_HARDWARE];
                for (int j = 0; j < HardwareUsage.NUM_HARDWARE; j++) {
                    if (a.hardwareUsage[j] != 0) {
                        hardwareUsageCount[j]++;
                        hardwareUsageSta[j][0]++;
                        hardwareUsageSta[j][1]++;
                    }
                }
            }
        }

        System.out.println("====" + string + "====Alignment result====");
        System.out.println("Energy Saving: " + Statistics.getSum(energySaving) + ". CPU: " + energySaving[HardwareUsage.NUM_HARDWARE]);
        for (int i = 0; i < hardwareUsageCount.length; i++) {
            System.out.println(HardwareUsage.HARDWARE_STRING[i] + ": "
                    + hardwareUsageSta[i][0] + ", " + hardwareUsageSta[i][1] + ", "
                    + ((float) hardwareUsageSta[i][0] / hardwareUsageSta[i][1]) + ", " + energySaving[i]);
        }
    }

    private static boolean inBoundary(Alarm a, long mStartTime, long mEndTime) {
		if (a.time >= mStartTime && a.time <= mEndTime)	return true;
		return false;
	}

	private static void printAverageNumEvent(ParserOutput output, String string) {
        ArrayList<Alarm> alarms = output.mAlarms;
        ArrayList<Integer> numEvent = new ArrayList<Integer>();
        long time = -1;
        int count = 0;

        for (int i = 0; i < alarms.size(); i++) {
            Alarm a = alarms.get(i);
            if (ENABLE_TIME_BOUNDARY && !inBoundary(a, output.mStartTime, output.mEndTime)) {
            	continue;
            }
            if (time == a.time) {
                count++;
                continue;
            } else {
                time = a.time;
                if (count == 0) {
                    // First time
                    count = 1;
                    continue;
                }
                numEvent.add(count);
                count = 1;
            }
        }

        System.out.println("====" + string + "====Average number of events====");
        System.out.println("Average: " + Statistics.getAverage(numEvent) + ", Std: "
                + Statistics.getStd(numEvent));
        System.out.println("Wake up number: " + numEvent.size());
    }

    private static void printHardwareUsageDistribution(ParserOutput output, String string) {
        ArrayList<Alarm> alarms = output.mAlarms;
        int[] hardwareUsageCount = new int[HardwareUsage.NUM_HARDWARE];
        int timeTickCount = 0, wakeupCount = 0;

        for (int i = 0; i < alarms.size(); i++) {
            Alarm a = alarms.get(i);
            if (ENABLE_TIME_BOUNDARY && !inBoundary(a, output.mStartTime, output.mEndTime)) {
            	continue;
            }
            for (int j = 0; j < HardwareUsage.NUM_HARDWARE; j++) {
                if (a.hardwareUsage[j] != 0)
                    hardwareUsageCount[j]++;
            }
            if (a.id.equals("1000android.intent.action.TIME_TICKnull"))
                timeTickCount++;
            if (a.type == 0 || a.type == 2)
                wakeupCount++;
        }

        if (DEBUG) {
            System.out.println("Number of events: " + alarms.size() + ", time tick events: "
                    + timeTickCount + ", wake up events: "
                    + wakeupCount);
        }

        System.out.println("====" + string + "====Percent of each hardware====");
        for (int i = 0; i < hardwareUsageCount.length; i++) {
            System.out.println(HardwareUsage.HARDWARE_STRING[i] + ": "
                    + ((float) hardwareUsageCount[i] / (wakeupCount)));
        }
    }

}
