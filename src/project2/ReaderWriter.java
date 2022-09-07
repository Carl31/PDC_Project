/*
 * Interface for a class that can read data from an external source and write data to an external source.
 * Project 1 - used for reading/writing from text files (userdata and words database).
 * Project 2 - used for reading/writing from database.
 */
package project2;

import java.util.Map;

public interface ReaderWriter {
    
    /*
    *  Read data from source - return is as a Map
    */
    public Map readFrom();
    
    /*
    *  Write the inputted Map to the external source file
    */
    public int writeTo(Map map);
}
