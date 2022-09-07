/*
 * A class to download and upload data from a text file. Used for getting/setting eng/esp words.
 */
package project2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class WordsFileReaderWriter implements ReaderWriter {

    private final File file;

    public WordsFileReaderWriter(File file) {
        this.file = file;
    }

    /*
    * Converts text file contents to HashMap
    */
    @Override
    public Map<String, String> readFrom() {
        Map<String, String> data = new HashMap<String, String>();

        try {
            FileReader fr = new FileReader(file);
            BufferedReader inputStream = new BufferedReader(fr);
            String line = "";
            String tempArray[];

            String tempData[];
            // store file contents
            while ((line = inputStream.readLine()) != null) {
                if (line.contains(":")) {
                    tempArray = line.split(":");
                    // System.out.println(line); // for testing
                    data.put(tempArray[0], tempArray[1]);
                }
            }

            // close stream
            inputStream.close();

        } catch (FileNotFoundException e) {
            System.err.println("File not found.");
            return null;
        } catch (IOException e) {
            System.err.println("Error reading from file.");
            return null;
        }
        return data;
    }

    /*
    * Converts HashMap contents to text file
    */
    @Override
    public int writeTo(Map map) {
        try {

            FileOutputStream fos = new FileOutputStream(file, false);
            PrintWriter pw = new PrintWriter(fos);

            for (Iterator it = map.entrySet().iterator(); it.hasNext();) {
                Map.Entry<String, String> entry = (Map.Entry<String, String>) it.next();
                pw.println(entry.getKey() + ":" + entry.getValue());
            }

            // close streams
            pw.close();

        } catch (FileNotFoundException e) {
            System.err.println("File not found.");
            return -1;
        }

        return 0;
    }
}
