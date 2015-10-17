import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Parser {
    enum Mode {
        DEFAULT, ALARM_MANAGER_STATS, ALARM_STATS, WAKEUP_RECORD
    }

    final static boolean DEBUG = true;
    final static int RECORD_LENGTH = 19;

    ParserOutput mOutput;
    private long mDuration;
    private long mOffset;
    
    public ParserOutput parse(File file, long duration, long offset) {
        String line;
        Mode parseMode = Mode.DEFAULT;
        mOutput = new ParserOutput();
        mDuration = duration;
        mOffset = offset;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ((line = br.readLine()) != null) {
                Mode mode = getMode(line);
                parseMode = mode != Mode.DEFAULT ? mode : parseMode;

                parseLine(line, parseMode);
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return mOutput;
    }

    private void parseLine(String line, Mode mode) {
        switch (mode) {
            case DEFAULT:
                break;
            case ALARM_MANAGER_STATS:
            	long time = parseEndTime(line);
            	if (time != -1) {
            		mOutput.mStartTime = time - mDuration-mOffset;
            		mOutput.mEndTime = time-mOffset;
            	}
                break;
            case ALARM_STATS:
                break;
            case WAKEUP_RECORD:
                Alarm a = parseAlarm(line);
                if (a != null)
                    mOutput.addAlarm(a);
                if (DEBUG && a != null /* && ((a.time - mOutput.mStartTime)/1000) < 40000*/) {
//                	if(a.id.contains("10098com.facebook.rti.mqtt.keepalive.KeepaliveManager.ACTION_INEXACT_ALARM.MqttLite.com.facebook.katananull"))
                		System.out.println("Request, " + ((a.time - mOutput.mStartTime)/1000) + ", " + a.toString());
                }
                break;
        }
    }

    private long parseEndTime(final String line) {
    	// nowRTC=1431268849160=2015-05-10 22:40:49 nowELAPSED=3870036
    	if(line.contains("nowRTC=") && line.contains("nowELAPSED=")){
    		final String[] splits = line.split("=");
    		long endTime = Long.parseLong(splits[1]);
    		return endTime;
    	}
		return -1;
	}

	private Alarm parseAlarm(final String line) {
		// Time: 1431266297780, Duration: 44, Delay: 17780, Window: 0, Interval: 0, Register2Trigger: 48002, Uid: 1000, Id: 1000android.intent.action.TIME_TICKnull, Type: 3, NetworkRec: 100, NetworkSnd: 184, NETWORK: 0, VIBRATION: 0, SOUND: 0, SCREEN: 0, AGPS: 0, GPS: 0, SENSOR_ACC: 0, LastFocus: -1

        final String[] splits = line.split(",");
        final int length = splits.length;

        if (length == 0)
            return null;

        if (length != RECORD_LENGTH) {
            return null;
        }

        Alarm a = new Alarm();

        for (int i = 0; i < length; i++) {
            parseAlarmAttribute(a, splits[i]);
        }

        return a;
    }

    private void parseAlarmAttribute(Alarm a, final String string) {
        String[] splits = string.trim().split(" ");

        for(int i = 0; i < splits.length-1; i++){
	        if (splits[i].equals("Time:")) {
	            a.time = Long.parseLong(splits[i+1]);
	        } else if (splits[i].equals("Duration:")) {
	            a.duration = Long.parseLong(splits[i+1]);
	        } else if (splits[i].equals("Delay:")) {
	            a.delay = Long.parseLong(splits[i+1]);
	        } else if (splits[i].equals("Window:")) {
	            a.window = Long.parseLong(splits[i+1]);
	        } else if (splits[i].equals("Interval:")) {
	            a.interval = Long.parseLong(splits[i+1]);
	        } else if (splits[i].equals("Register2Trigger:")) {
	            a.register2Trigger = Long.parseLong(splits[i+1]);
	        } else if (splits[i].equals("Uid:")) {
	            a.uid = Integer.parseInt(splits[i+1]);
	        } else if (splits[i].equals("Id:")) {
	            a.id = splits[i+1];
	        } else if (splits[i].equals("Type:")) {
	            a.type = Integer.parseInt(splits[i+1]);
	        } else if (splits[i].equals("NetworkRec:")) {
	            a.networkRcv = Integer.parseInt(splits[i+1]);
	        } else if (splits[i].equals("NetworkSnd:")) {
	            a.networkSnd = Integer.parseInt(splits[i+1]);
	        } else if (splits[i].equals("NETWORK:")) {
	            a.hardwareUsage[0] = Integer.parseInt(splits[i+1]);
	        } else if (splits[i].equals("VIBRATION:")) {
	            a.hardwareUsage[1] = Integer.parseInt(splits[i+1]);
	        } else if (splits[i].equals("SOUND:")) {
	            a.hardwareUsage[2] = Integer.parseInt(splits[i+1]);
	        } else if (splits[i].equals("SCREEN:")) {
	            a.hardwareUsage[3] = Integer.parseInt(splits[i+1]);
	        } else if (splits[i].equals("AGPS:")) {
	            a.hardwareUsage[4] = Integer.parseInt(splits[i+1]);
	        } else if (splits[i].equals("GPS:")) {
	            a.hardwareUsage[5] = Integer.parseInt(splits[i+1]);
	        } else if (splits[i].equals("SENSOR_ACC:")) {
	            a.hardwareUsage[6] = Integer.parseInt(splits[i+1]);
	        } else if (splits[i].equals("LastFocus:")) {
	            a.lastFocus = Long.parseLong(splits[i+1]);
	        }
        }
    }

    private Mode getMode(final String line) {
    	if (line.contains("Current Alarm Manager state:"))
    		return Mode.ALARM_MANAGER_STATS;
        if (line.contains("Alarm Stats:"))
            return Mode.ALARM_STATS;
        if (line.contains("Recent Wakeup History:"))
            return Mode.WAKEUP_RECORD;
        return Mode.DEFAULT;
    }
}
