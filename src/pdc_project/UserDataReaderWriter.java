/*
 * A class to download and upload data from a text file. Used for getting/setting user data.
 */
package pdc_project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

public class UserDataReaderWriter implements ReaderWriter { //TODO: enable correct parsing of user data (make a format in txt doc)

    private final File file;

    public UserDataReaderWriter(File file) {
        this.file = file;
    }

    /*
    * Converts text file contents to HashMap
    */
    @Override
    public Map<String, UserData> readFrom() {
        Map<String, UserData> data = new HashMap<String, UserData>();

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
                    
                    HashSet<Word> tempWords = new HashSet<Word>();
                    for (String tempWord : tempArray[3].split(", ")) {
                       tempWords.add(new Word(tempWord.split("/")[0], tempWord.split("/")[1]));
                    }
                    data.put(tempArray[0], new UserData(Integer.parseInt(tempArray[1]), Float.valueOf(tempArray[2]), tempWords));
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

            FileOutputStream fos = new FileOutputStream(file, false); // ensure is appending - not deleting it all lol
            PrintWriter pw = new PrintWriter(fos);

            for (Iterator it = map.entrySet().iterator(); it.hasNext();) {
                Map.Entry<String, UserData> entry = (Map.Entry<String, UserData>) it.next();
                pw.println(entry.getKey().toString() + ":" + entry.getValue().toString());
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

