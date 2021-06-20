package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ApplicationClient {


    public boolean readFile(String filename)
    {
        String records = new String();
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null)
            {
                records.concat(line);
            }
            reader.close();
            TransportClient transportClient = new TransportClient();
            transportClient.EnTeteLisible(filename);
            return true;
        }
        catch (Exception e)
        {
            System.err.format("Exception occurred trying to read '%s'.", filename);
            e.printStackTrace();
            return false;
        }
    }

    public String nomFile(File filename){
        return filename.getName();
    }
            return records;
}
