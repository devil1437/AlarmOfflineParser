public class Alarm {
    public long time;
    public long duration;
    public long delay;
    public long window;
    public long interval;
    public long register2Trigger;
    public int uid;
    public String id;
    public int type;
    public int networkRcv;
    public int networkSnd;
    public int[] hardwareUsage = new int[HardwareUsage.NUM_HARDWARE];
    public long lastFocus;

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer(200);

        sb.append("Time: ");
        sb.append(time);
        sb.append(", Duration: ");
        sb.append(duration);
        sb.append(", Delay: ");
        sb.append(delay);
        sb.append(", Window: ");
        sb.append(window);
        sb.append(", Interval: ");
        sb.append(interval);
        sb.append(", Register2Trigger: ");
        sb.append(register2Trigger);
        sb.append(", Uid: ");
        sb.append(uid);
        sb.append(", Id: ");
        sb.append(id);
        sb.append(", Type: ");
        sb.append(type);
        sb.append(", NetworkRec: ");
        sb.append(networkRcv);
        sb.append(", NetworkSnd: ");
        sb.append(networkSnd);
        for (int i = 0; i < hardwareUsage.length; i++) {
            sb.append(", ");
            sb.append(HardwareUsage.HARDWARE_STRING[i]);
            sb.append(": ");
            sb.append(hardwareUsage[i]);
        }
        sb.append(", LastFocus: ");
        sb.append(lastFocus);

        return sb.toString();
    }
}
