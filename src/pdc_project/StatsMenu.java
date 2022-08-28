/*
 * User Statistics menu that is run by the PDC_Project main class
 */
package pdc_project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author carls
 */
public class StatsMenu extends Menu {

    private ReaderWriter udrw;
    private String username;
    private HashMap<String, UserData> data;

    public StatsMenu(ReaderWriter udrw, String username) {
        this.udrw = udrw;
        this.username = username;
        data = (HashMap<String, UserData>) udrw.readFrom();
    }

    @Override
    public void run() {
        // initialising variables
        int input = 0;

        System.out.println("\nWelcome to our hall of statistics!");

        while (input != 3) { // keeps loopin until user goes back (presses 3)
            System.out.println("\n\t\tSTATS MENU:");
            System.out.println("\nWhat would you like to do?");
            System.out.println("\t 1 - My Stats: View your own statistics!");
            System.out.println("\t 2 - World Stats: View our local stat leaderboard!");
            System.out.println("\t 3 - Go Back: Back to main menu.");

            while (input < 1 || input > 4) {
                input = InputGetter.getPosInt("Enter \'1\', \'2\' or \'3\': ", 3);
            }

            switch (input) {
                case (1):
                    try {
                    printStats(username);
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException ex) {
                }
                break;
                case (2):
                    try {
                    printLeaderboard();
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException ex) {
                }
                break;
                case (3):
                    break;
                default:
                    break;
            }
            if (input != 3) {
                input = 0;
            }
        }
    }

    /*
    * Prints user stats to screen, neatly
     */
    private void printStats(String username) throws InterruptedException {
        System.out.println("\n\tNAME: " + username);
        TimeUnit.SECONDS.sleep(1);
        System.out.println("\t\tTotal rounds played: " + (data.get(username) != null ? data.get(username).getGamesPlayed() : "0"));
        TimeUnit.SECONDS.sleep(1);
        System.out.println("\t\tCorrect Percentage: " + (data.get(username) != null ? data.get(username).getCorrectPercent() : "0") + "%");
        TimeUnit.SECONDS.sleep(1);
        System.out.println("\t\tIncorrect words: ");
        int count = 1;
        if (data.get(username) == null || data.get(username).getIncorrectWords().isEmpty()) {
            System.out.println("\t\tRevision pile empty!!");
        } else {
            for (Word temp : data.get(username).getIncorrectWords()) {
                System.out.println("\t\t" + count + ") " + temp.getSpanish() + " : " + temp.getEnglish());
                count++;
            }
        }
    }

    /*
    * Prints all users - leader board order based off of their 'correct' percentage 
     */
    private void printLeaderboard() throws InterruptedException {
        int place = 1;

        // put user name and correctPercentage into a HashMap scores
        HashMap<Float, String> scores = new HashMap<Float, String>();
        for (Map.Entry<String, UserData> user : data.entrySet()) {
            scores.put(user.getValue().getCorrectPercent(), user.getKey());
        }

        // add all map entries from scores to a list (for sorting)
        List<Map.Entry<Float, String>> list = new ArrayList<>();
        for (Map.Entry<Float, String> score : scores.entrySet()) {
            list.add(score);
        }

        // sort list
        Collections.sort(list, new Comparator<Map.Entry<Float, String>>() {
            @Override
            public int compare(Map.Entry<Float, String> o1, Map.Entry<Float, String> o2) {
                return o2.getKey().compareTo(o1.getKey());
            }
        });

        // print list, neatly
        for (Map.Entry<Float, String> score : list) {
            System.out.format("\t" + place + ") " + score.getValue() + " - %.2f%% correctness\n", score.getKey());
            TimeUnit.SECONDS.sleep(1);
            place++;
        }
    }
}
