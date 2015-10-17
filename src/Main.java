import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    final static boolean DEBUG = true;
    final private static boolean ENABLE_TIME_BOUNDARY = true;
    final private static boolean ENABLE_ENERGY_CONSUMPTION = true;
    
    final private static long WAKEUP_THRESHOLD = 5*1000;
    
    final private static HashMap<String, Long> performanceAlarm = new HashMap<String, Long>();
    
    public static String toPercentage(float n){
        return String.format("%.2f",n*100)+"%";
    }
    
    public static void main(String[] args) {
    	init();
//    	parseFile("1_1.alarm", 30 * 60 * 1000, 21 * 1000);
//        parseFile("2_2.alarm", 30 * 60 * 1000,  56 * 1000);
//        parseFile("3.alarm", 30 * 60 * 1000,  22 * 1000);
//        parseFile("4.alarm", 30 * 60 * 1000,  11 * 1000);
//        parseFile("101.alarm", 30 * 60 * 1000,  12 * 1000);
//    	parseFile("102_2.alarm", 30 * 60 * 1000, 918 * 1000);
//        parseFile("103.alarm", 30 * 60 * 1000,  13 * 1000);
//        parseFile("104.alarm", 30 * 60 * 1000,  11 * 1000);
//        parseFile("201.alarm", 3600 * 1000,  20 * 3600 * 1000 + 1296 * 1000 + 18 * 1000);
//    	parseFile("301_2_merge.alarm", 3 * 3600 * 1000,  14 * 1000);
//    	parseFile("302_merge.alarm", 3 * 3600 * 1000,  15 * 1000);
//    	parseFile("303_2_merge.alarm", 3 * 3600 * 1000,  16 * 1000);
//    	parseFile("304_merge.alarm", 3 * 3600 * 1000,  43 * 1000);
//    	parseFile("305_merge.alarm", 3 * 3600 * 1000,  18 * 1000);
//    	parseFile("306_merge.alarm", 3 * 3600 * 1000,  31 * 1000);
//    	parseFile("402_merge.alarm", 3*3600 * 1000, 4*3600*1000+31 * 1000);
//    	parseFile("502_merge.alarm", 6*1800 * 1000, 11 * 1000);
//    	parseFile("501_merge.alarm", 7*1800 * 1000, 59 * 1000);
//    	parseFile("503_merge.alarm", 6*1800 * 1000, 59 * 1000);
//    	parseFile("604_merge.alarm", 6*1800 * 1000, 16 * 1000);
//    	parseFile("605_merge.alarm", 6*1800 * 1000, 6*1800*1000+600*1000+34 * 1000);
//    	parseFile("601_merge.alarm", 6*1800 * 1000, 1800*1000+35 * 1000);
//    	parseFile("602_merge.alarm", 7*1800 * 1000, 100*1000+29 * 1000);
    	parseFile("602_2_merge.alarm", 6 * 1800 * 1000, 26 * 1800 * 1000+1300*1000+29 * 1000);
    }

    private static void init() {
    	performanceAlarm.put("nullComponentInfo{com.kakao.talk/com.kakao.talk.service.MessengerService}", 10*60*1000l);
    	performanceAlarm.put("ALARM_ACTION(", 15*60*1000l);
    	performanceAlarm.put("jp.naver.line.android.legy.SpdyHeartbeatChecker.sendPingnull", 200*1000l);
    	performanceAlarm.put("com.facebook.rti.mqtt.keepalive.KeepaliveManager.ACTION_INEXACT_ALARM.MqttLite.com.facebook.katananull", 60*1000l);
    	performanceAlarm.put("io.wecloud.message.action.METHODnull", 506*1000l);
    	performanceAlarm.put("122ComponentInfo{com.rakesh.alarmmanagerexample/com.rakesh.alarmmanagerexample.AlarmManagerBroadcastReceiver}", 5*60*1000l);
    	performanceAlarm.put("123ComponentInfo{com.rakesh.alarmmanagerexample/com.rakesh.alarmmanagerexample.AlarmManagerBroadcastReceiver}", 3*60*1000l);
    	performanceAlarm.put("124ComponentInfo{com.rakesh.alarmmanagerexample/com.rakesh.alarmmanagerexample.AlarmManagerBroadcastReceiver}", 5*60*1000l);
    	performanceAlarm.put("125ComponentInfo{com.rakesh.alarmmanagerexample/com.rakesh.alarmmanagerexample.AlarmManagerBroadcastReceiver}", 30*60*1000l);
    	performanceAlarm.put("129ComponentInfo{com.rakesh.alarmmanagerexample/com.rakesh.alarmmanagerexample.AlarmManagerBroadcastReceiver}", 60*1000l);
    	performanceAlarm.put("229ComponentInfo{com.rakesh.alarmmanagerexample/com.rakesh.alarmmanagerexample.AlarmManagerBroadcastReceiver}", 90*1000l);
    	performanceAlarm.put("nullComponentInfo{com.aplicativoslegais.beberagua/com.aplicativoslegais.beberagua.broadcastReceivers.Notificacao}", 15*60*1000l);
    	performanceAlarm.put("com.viber.voip.action.KEEP_ALIVE_RECEIVEnull", 10*60*1000l);
    	performanceAlarm.put("com.sina.heartbeat.action.1004null", 5*60*1000l);
    	performanceAlarm.put("com.facebook.rti.mqtt.c.b.ACTION_INEXACT_ALARM.MqttLite.com.facebook.orcanull", 60*1000l);
    	performanceAlarm.put("com.facebook.push.mqtt.e.b.ACTION_INEXACT_ALARM.com.facebook.orca", 900*1000l);
    	performanceAlarm.put("com.xiaomi.push.PING_TIMERnull", 300*1000l);
    	performanceAlarm.put("zayhu.actions.ACTION_KEEP_ALIVE", 270*1000l);
    	performanceAlarm.put("com.imo.android.imoim.KEEPALIVEComponentInfo{com.imo.android.imoim/com.imo.android.imoim.Alarms}", 3*60*1000l);
    	performanceAlarm.put("com.nhn.nni.intent.REGISTERnull", 202*1000l);
	}

	private static void parseFile(String string, long duration, long offset) {
        File file = new File(string);
        Parser parser = new Parser();
        ParserOutput output = parser.parse(file, duration, offset);

        System.out.println("====" + string + "====");
        printHardwareUsageDistribution(output, string);
        printAverageNumEvent(output, string, false);
        printAlignmentResult(output, string, false);
        printAverageNumEvent(output, string, true);
        printAlignmentResult(output, string, true);
        printPerformance(output, string);
//        printAverageInterval(output, string);
        if(ENABLE_ENERGY_CONSUMPTION){
        	
        }
    }

//    private static void printAverageInterval(ParserOutput output, String string) {
//    	HashMap<String, ArrayList<Alarm>> intervalMap = new HashMap<String, ArrayList<Alarm>>();
//    	ArrayList<Alarm> alarms = output.mAlarms;
//    	
//    	for (int i = 0; i < alarms.size(); i++) {
//            Alarm a = alarms.get(i);
//            if (ENABLE_TIME_BOUNDARY && !inBoundary(a, output.mStartTime, output.mEndTime)) {
//            	continue;
//            }
//            if (a.isPerceivable()) {
//            	continue;
//            }
//            
//            if(!intervalMap.containsKey(a.id)){
//            	intervalMap.put(a.id, new ArrayList<Alarm>());
//            } 
//            intervalMap.get(a.id).add(a);
//        }
//    	
//    	System.out.println("====" + string + "====Performance2====");
//    	int n = performanceAlarm.length;
//    	for(int i = 0; i < n; i++){
//    		String alarmName = performanceAlarm[i];
//    		String id = null;
//    		for(String str : intervalMap.keySet()){
//    			if(str.contains(alarmName)){
//    				id = str;
//    				break;
//    			}
//    		}
//    			
//    		if(id == null){
//    			System.out.println(alarmName+",x,x,x");
//    			continue;
//    		}
//    		
//    		ArrayList<Alarm> orderAlarms = intervalMap.get(id);
//    		if(orderAlarms.get(0).interval <= 0){
//    			System.out.println(alarmName+",x,x,x");
//    			continue;
//    		}
//    		ArrayList<Integer> intervals = new ArrayList<Integer>();
//    		long lastWakeupTime = 0;
//    		for(Alarm a : orderAlarms){
//    			if(lastWakeupTime != 0 && a.time - lastWakeupTime < 3*performanceAlarmInterval[i]){
//    				intervals.add((int) (a.time - lastWakeupTime));
//    			}
//    			lastWakeupTime = a.time;
//    		}
//    		
//    		System.out.println(alarmName + "," + Statistics.getAverage(intervals) + "," + Statistics.getStd(intervals)+ "," + Statistics.getMax(intervals)+ "," + orderAlarms.size());
//    		
////    		System.out.println("Id: " + orderAlarms.get(0).id + ", Interval: " + orderAlarms.get(0).interval
////                    + ", Mean of interval: " + Statistics.getAverage(intervals) + ", Std of interval: " + Statistics.getStd(intervals)
////                    + ", number of events: " + orderAlarms.size());
//    	}
//	}

	private static void printPerformance(ParserOutput output, String string) {
    	ArrayList<Alarm> alarms = output.mAlarms;
        ArrayList<Float> perceivableDelay = new ArrayList<Float>();
        ArrayList<Float> imperceivableDelay = new ArrayList<Float>();

        System.out.println("====" + string + "====Performance====");
        
        for (String alarmId : performanceAlarm.keySet()) {
        	boolean isPerceivable = false;
        	long repeatingInterval = performanceAlarm.get(alarmId);
        	int n = 0;
        	float averageDelay = 0;
        	for (int i = 0; i < alarms.size(); i++) {
                Alarm a = alarms.get(i);
                if (ENABLE_TIME_BOUNDARY && !inBoundary(a, output.mStartTime, output.mEndTime)) {
                	continue;
                }
                if (!a.id.contains(alarmId)) {
                	continue;
                }
                
//                if (DEBUG && a.isPerceivable()) {
//            		System.out.println("perceivable:" + a);
//                }
                isPerceivable |= a.isPerceivable();
                
                double delay = a.window == -1 ? Math.max(a.delay-0.75*repeatingInterval, 0) : Math.max(a.delay-a.window*repeatingInterval, 0);
                averageDelay = (float) ((averageDelay*n + delay)/(n+1));
                n+=1;
//                if (DEBUG) {
//            		System.out.println("delay: " + a.delay + ", interval: " + a.interval + ", repeatingInterval: " + repeatingInterval + ", window: " + a.window);
//            		System.out.println("number: " + n + ", averageDelay: " + averageDelay);
//                }
            }
        	if(n != 0){
        		if(isPerceivable)	perceivableDelay.add((float)averageDelay/repeatingInterval);
        		else imperceivableDelay.add((float)averageDelay/repeatingInterval);
        		System.out.println("App: " + alarmId + ", Number: " + n);
        	} else {
        		System.out.println("Not found: " + alarmId);
        	}
        }
        

        
        System.out.println("====Perceivable====");
        System.out.println("Average: " + toPercentage((float) Statistics.getAverageFloat(perceivableDelay)) + ", Std: "
                + toPercentage((float) Statistics.getStdFloat(perceivableDelay)));
        System.out.println("number: " + perceivableDelay.size());
        System.out.println("====Imperceivable====");
        System.out.println("Average: " + toPercentage((float) Statistics.getAverageFloat(imperceivableDelay)) + ", Std: "
                + toPercentage((float) Statistics.getStdFloat(imperceivableDelay)));
        System.out.println("number: " + imperceivableDelay.size());
		
	}

	private static void printAlignmentResult(ParserOutput output, String string, boolean excludeSystemAlarms) {
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
            if (excludeSystemAlarms && !isExpApp(a)) {
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

        System.out.println("====" + string + "====Alignment result====" + "excludeSystemApp: " + excludeSystemAlarms);
        System.out.println("Energy Saving: " + Statistics.getSum(energySaving) + ". CPU: " + energySaving[HardwareUsage.NUM_HARDWARE]);
        for (int i = 0; i < hardwareUsageCount.length; i++) {
            System.out.println(HardwareUsage.HARDWARE_STRING[i] + ": "
                    + hardwareUsageSta[i][0] + ", " + hardwareUsageSta[i][1] + ", "
                    + ((float) hardwareUsageSta[i][0] / hardwareUsageSta[i][1]) + ", " + energySaving[i]);
        }
    }

    private static boolean isExpApp(Alarm a) {
		for(String id : performanceAlarm.keySet()){
			if ( a.id.contains(id) )	return true;
		}
		return false;
	}

	private static boolean isClose(long time, long time2) {
		return Math.abs(time - time2) < WAKEUP_THRESHOLD;
	}

	private static boolean inBoundary(Alarm a, long mStartTime, long mEndTime) {
		if (a.time >= mStartTime && a.time <= mEndTime)	return true;
		return false;
	}

	private static void printAverageNumEvent(ParserOutput output, String string, boolean excludeSystemAlarms) {
        ArrayList<Alarm> alarms = output.mAlarms;
        ArrayList<Integer> numEvent = new ArrayList<Integer>();
        long time = -1;
        int count = 0;

        for (int i = 0; i < alarms.size(); i++) {
            Alarm a = alarms.get(i);
            if (ENABLE_TIME_BOUNDARY && !inBoundary(a, output.mStartTime, output.mEndTime)) {
            	continue;
            }
            if (excludeSystemAlarms && !isExpApp(a)) {
            	continue;
            }
            if (a.id.equals("1000android.intent.action.TIME_TICKnull")){
            	continue;
            }
            if(DEBUG){
            	if(excludeSystemAlarms && a.isCPU())	System.out.println(a.toString());
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
        numEvent.add(count);

        System.out.println("====" + string + "====Average number of events===="  + "excludeSystemApp: " + excludeSystemAlarms);
        System.out.println("Average: " + Statistics.getAverage(numEvent) + ", Std: "
                + Statistics.getStd(numEvent));
        System.out.println("Wake up number: " + numEvent.size() + ", number of events: " + Statistics.getSum(numEvent));
    }

    private static void printHardwareUsageDistribution(ParserOutput output, String string) {
        ArrayList<Alarm> alarms = output.mAlarms;
        int[] hardwareUsageCount = new int[HardwareUsage.NUM_HARDWARE];
        int timeTickCount = 0, wakeupCount = 0, eventCount = 0;

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
            
            eventCount++;
        }
        
        System.out.println("Number of events: " + eventCount + ", time tick events: " + timeTickCount + ", wake up events: " + wakeupCount);

        System.out.println("====" + string + "====Percent of each hardware====");
        for (int i = 0; i < hardwareUsageCount.length; i++) {
            System.out.println(HardwareUsage.HARDWARE_STRING[i] + ": "
                    + ((float) hardwareUsageCount[i] / (wakeupCount)));
        }
    }

}
