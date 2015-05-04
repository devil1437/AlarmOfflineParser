import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Parser {
    enum Mode {
        DEFAULT, ALARM_STATS, WAKEUP_RECORD
    }

    final static boolean DEBUG = false;
    final static int RECORD_LENGTH = 19;

    ParserOutput mOutput;

    public ParserOutput parse(File file) {
        String line;
        Mode parseMode = Mode.DEFAULT;
        mOutput = new ParserOutput();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ((line = br.readLine()) != null) {
                Mode mode = getMode(line);
                parseMode = mode != Mode.DEFAULT ? mode : parseMode;

                parseLine(line, parseMode);
            }

            if (DEBUG) {
                for (int i = 0; i < mOutput.mAlarms.size(); i++) {
                    System.out.println(mOutput.mAlarms.get(i).toString());
                }
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
            case ALARM_STATS:
                break;
            case WAKEUP_RECORD:
                Alarm a = parseAlarm(line);
                if (a != null)
                    mOutput.addAlarm(a);
                break;
        }
    }

    private Alarm parseAlarm(final String line) {
        final String[] splits = line.split(",");
        final int length = splits.length;

        if (length == 0)
            return null;

        if (length != RECORD_LENGTH) {
            if (DEBUG)
                System.err.println("Parse error: " + line);
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

        if (splits[0].equals("Time:")) {
            a.time = Long.parseLong(splits[1]);
        } else if (splits[0].equals("Duration:")) {
            a.duration = Long.parseLong(splits[1]);
        } else if (splits[0].equals("Delay:")) {
            a.delay = Long.parseLong(splits[1]);
        } else if (splits[0].equals("Window:")) {
            a.window = Long.parseLong(splits[1]);
        } else if (splits[0].equals("Interval:")) {
            a.interval = Long.parseLong(splits[1]);
        } else if (splits[0].equals("Register2Trigger:")) {
            a.register2Trigger = Long.parseLong(splits[1]);
        } else if (splits[0].equals("Uid:")) {
            a.uid = Integer.parseInt(splits[1]);
        } else if (splits[0].equals("Id:")) {
            a.id = splits[1];
        } else if (splits[0].equals("Type:")) {
            a.type = Integer.parseInt(splits[1]);
        } else if (splits[0].equals("NetworkRec:")) {
            a.networkRcv = Integer.parseInt(splits[1]);
        } else if (splits[0].equals("NetworkSnd:")) {
            a.networkSnd = Integer.parseInt(splits[1]);
        } else if (splits[0].equals("NETWORK:")) {
            a.hardwareUsage[0] = Integer.parseInt(splits[1]);
        } else if (splits[0].equals("VIBRATION:")) {
            a.hardwareUsage[1] = Integer.parseInt(splits[1]);
        } else if (splits[0].equals("SOUND:")) {
            a.hardwareUsage[2] = Integer.parseInt(splits[1]);
        } else if (splits[0].equals("SCREEN:")) {
            a.hardwareUsage[3] = Integer.parseInt(splits[1]);
        } else if (splits[0].equals("AGPS:")) {
            a.hardwareUsage[4] = Integer.parseInt(splits[1]);
        } else if (splits[0].equals("GPS:")) {
            a.hardwareUsage[5] = Integer.parseInt(splits[1]);
        } else if (splits[0].equals("SENSOR_ACC:")) {
            a.hardwareUsage[6] = Integer.parseInt(splits[1]);
        } else if (splits[0].equals("LastFocus:")) {
            a.lastFocus = Long.parseLong(splits[1]);
        }
    }

    private Mode getMode(final String line) {
        if (line.contains("Alarm Stats:"))
            return Mode.ALARM_STATS;
        if (line.contains("Recent Wakeup History:"))
            return Mode.WAKEUP_RECORD;
        return Mode.DEFAULT;
    }
}
