import java.io.File;
import java.util.ArrayList;

public class Main {

    final static boolean DEBUG = false;

    public static void main(String[] args) {
        parseFile("13.alarm");
        parseFile("14.alarm");
        parseFile("15.alarm");
    }

    private static void parseFile(String string) {
        File file = new File(string);
        Parser parser = new Parser();
        ParserOutput output = parser.parse(file);

        System.out.println("====" + string + "====");
        printHardwareUsageDistribution(output, string);
        printAverageNumEvent(output, string);
        printAlignmentResult(output, string);
    }

    private static void printAlignmentResult(ParserOutput output, String string) {
        ArrayList<Alarm> alarms = output.mAlarms;
        int[] hardwareUsageCount = new int[HardwareUsage.NUM_HARDWARE];
        int[][] hardwareUsageSta = new int[HardwareUsage.NUM_HARDWARE][2];
        long time = -1;

        for (int i = 0; i < alarms.size(); i++) {
            Alarm a = alarms.get(i);
            if (time == a.time) {
                for (int j = 0; j < HardwareUsage.NUM_HARDWARE; j++) {
                    if (a.hardwareUsage[j] != 0) {
                        if (hardwareUsageCount[j] == 0) {
                        	hardwareUsageSta[j][0]++;
                        }
                        hardwareUsageCount[j]++;
                        hardwareUsageSta[j][1]++;
                    }
                }
                continue;
            } else {
                time = a.time;
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
        for (int i = 0; i < hardwareUsageCount.length; i++) {
            System.out.println(HardwareUsage.HARDWARE_STRING[i] + ": "
                    + hardwareUsageSta[i][0] + ", " + hardwareUsageSta[i][1] + ", "
                    + ((float) hardwareUsageSta[i][0] / hardwareUsageSta[i][1]));
        }
    }

    private static void printAverageNumEvent(ParserOutput output, String string) {
        ArrayList<Alarm> alarms = output.mAlarms;
        ArrayList<Integer> numEvent = new ArrayList<Integer>();
        long time = -1;
        int count = 0;

        for (int i = 0; i < alarms.size(); i++) {
            Alarm a = alarms.get(i);
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
    }

    private static void printHardwareUsageDistribution(ParserOutput output, String string) {
        ArrayList<Alarm> alarms = output.mAlarms;
        int[] hardwareUsageCount = new int[HardwareUsage.NUM_HARDWARE];
        int timeTickCount = 0, wakeupCount = 0;

        for (int i = 0; i < alarms.size(); i++) {
            Alarm a = alarms.get(i);
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
