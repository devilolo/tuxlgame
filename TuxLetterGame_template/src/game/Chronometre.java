package game;

public class Chronometre {

    private long begin;
    private long end;
    private long current;
    private int limite;

    public Chronometre(int limite) {
        begin = 0;
        end = 0;
        current = 0;
        this.limite = limite;
    }

    public void start() {
        begin = System.currentTimeMillis();
    }

    public void stop() {
        end = System.currentTimeMillis();
    }

    public long getTime() {
        return end - begin;
    }

    public long getMilliseconds() {
        return end - begin;
    }

    public int getSeconds() {
        return (int) ((end - begin) / 1000.0);
    }

    public double getMinutes() {
        return (end - begin) / 60000;
    }

    public double getHours() {
        return (end - begin) / Math.pow(3.6, 6);
    }

    public int remainsTime() {
        current = System.currentTimeMillis();
        int timeSpent;
        timeSpent = (int) ((current - begin) / 1000.0);
        return (this.limite - timeSpent);
    }
}
