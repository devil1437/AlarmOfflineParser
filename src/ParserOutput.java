import java.util.ArrayList;

public class ParserOutput {
    ArrayList<Alarm> mAlarms = new ArrayList<Alarm>();
    long mEndTime;
    long mStartTime;
    
    public void addAlarm(Alarm a) {
        mAlarms.add(a);
    }
}
