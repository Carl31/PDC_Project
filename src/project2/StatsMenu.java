/*
 * User Statistics menu that is run by the PDC_Project main class
 */
package project2;

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
public class StatsMenu extends Menu { // line 103

    private ModelData data;

    public StatsMenu(ModelData data) {
        this.data = data;
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
                    printStats(data.getUsername());
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
        System.out.println("\t\tTotal rounds played: " + data.getUser().getGamesPlayed());
        TimeUnit.SECONDS.sleep(1);
        System.out.println("\t\tCorrect Percentage: " + data.getUser().getCorrectPercent() + "%");
        TimeUnit.SECONDS.sleep(1);
        System.out.println("\t\tIncorrect words: ");
        int count = 1;
        if (data.getUser().getIncorrectWords().isEmpty()) {
            System.out.println("\t\tRevision pile empty!!");
        } else {
            for (Word temp : data.getUser().getIncorrectWords()) {
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
//        for (Map.Entry<String, UserData> user : data.entrySet()) { // DB -- need to replace data.entrySet with a HashMap<CorrectPercentage, Username> of ALL users
//            scores.put(user.getValue().getCorrectPercent(), user.getKey());
//        }

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
