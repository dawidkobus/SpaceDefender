package spacedefender;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class StatsEngine{
    private int statsCapacity;
    private File statsFile;
    private FileWriter statsFileWriter;
    private Scanner scanner;
    private String[] readStats;
    
    public void init(){
        statsCapacity = 300;
        try{
            statsFile = new File("res/stats.txt");
            
            scanner = new Scanner(statsFile);
            scanner.useDelimiter(";");
            
            readStats = new String[statsCapacity];
        }
        catch(FileNotFoundException ex){
            System.out.println("FileNotFoundException");
        }
    }
    
    public String[] read(){
        if(statsFile.exists()){
            for(int i = 0; scanner.hasNext() && i < statsCapacity; i++){
                readStats[i] = scanner.next();
            }
        }
        else{
            try{
                statsFile.createNewFile();
            }
            catch(IOException ex){
                System.out.println("IOException");
            }
        }
        return readStats;
    }
    
    public void write(String[] newStats){
        try{
            statsFileWriter = new FileWriter(statsFile, true);
            for(int i = 0; i < newStats.length; i++){
                statsFileWriter.write(newStats[i] + ";");
            }
            statsFileWriter.flush();
            statsFileWriter.close();
        }
        catch(IOException ex){
            System.out.println("IOException");
        }
    }
    
    public int getStatsCapacity(){
        return statsCapacity;
    }
}
