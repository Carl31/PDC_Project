/*
 * A countdown timer for time attack mode.
 */
package project2;

import java.util.TimerTask;

/**
 *
 * @author carls
 */
public class CountDown extends TimerTask {

    public int seconds = 8;
    public boolean outOfTime = false;

    public boolean getOutOfTime() {
        return outOfTime;
    }

    @Override
    public void run() {
        if (seconds > 0) {
            System.out.println("Time remaining: " + seconds);
        } else {
            System.out.println("Out of time! (press enter to continue)");
            outOfTime = true;
            try {
                this.cancel();
            } catch (IllegalStateException e) {
            }

        }
        seconds--;
    }

}
