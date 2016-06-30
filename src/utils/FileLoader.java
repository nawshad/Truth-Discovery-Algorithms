/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author nawshad
 */
public class FileLoader {
    //Contains all the data items / symbol names of stock and saved on DataItemListFromFile list
    public static ArrayList<String> GroundTruthDataItemList = new ArrayList<String>();
    
    
    public static void loadCleanFile(String groundTruthFilePath, String cleanDataFilePath) throws IOException{
       //data items of interest from ground truth file, read from file and put them in a list.
       for (String line : Files.readAllLines(Paths.get(groundTruthFilePath))) {
           //System.out.println(line);
           int part_no = 0;
           for (String part : line.split("\t")) {
              if(part_no == 0){
                  GroundTruthDataItemList.add(part);
              }
              part_no++;
           }
       }
       //placed in alphabetical order.
       //System.out.println("Parts:"+GroundTruthDataItemList);
       
       //Find out the claims and also put them in a list of lists.  
       //Loop through the list DataItemListFromFile, and find out the corresponding claimsIDs (from unique claim list) and save the ids 
       //in another list of list, where the outer list expresses the index for data list and inner list contains the claim ids
       //for them.
       
       Charset charset = Charset.forName("ISO-8859-1"); 
       //Calculating data items and claims
       
       String[] parts = new String [10];
       
       
       ArrayList<String> selectedSources = new ArrayList<String>();
       ArrayList<Double> claimScores = new ArrayList<Double>();
       
      
      
       
       for (String line : Files.readAllLines(Paths.get(cleanDataFilePath),charset)) {
            parts = line.split("\t");  
            if(GroundTruthDataItemList.contains(parts[1])&&!parts[5].isEmpty()){
                String claim = "";
                if(parts[5].contains("change:")){
                    claim = parts[5].substring(8);
                }else if(parts[5].contains("$")){
                    claim = parts[5].substring(1);
                }
                else{
                    claim = parts[5];
                }
                //System.out.println("Sources: "+parts[0]+"Data Item: "+parts[1]+" Claims: "+ Double.parseDouble(claim)); 
                
                //Save the sources in order of insertion
                selectedSources.add(parts[0]);
                
                //Save the claim scores in order of insertion
                claimScores.add(Double.parseDouble(claim));
            }
       }
       
       Collections.sort(claimScores);
       Collections.sort(selectedSources);
       
       
       String preSource = "";
       String postSource = "";
       
       ArrayList<String> uniqueSourceList = new ArrayList<String>();
       for(int i=0; i<selectedSources.size(); i++){
           preSource = selectedSources.get(i);
           if(preSource.equals(postSource)){
               continue;
           }else{
               System.out.println("Source: "+preSource);
               uniqueSourceList.add(preSource);
           }
           postSource = preSource;
       }
       
      
       
       double preClaimScore = 100000;
       double postClaimScore = 100000;
       
       ArrayList<Double> uniqueClaimList = new ArrayList<Double>();
       for(int j=0; j<claimScores.size(); j++){
           preClaimScore = claimScores.get(j);
           if(preClaimScore == postClaimScore){
               continue;
           }else{
               System.out.println("Claims: "+preClaimScore);
               uniqueClaimList.add(preClaimScore);
           }
           postClaimScore = preClaimScore;   
       }
       
       System.out.println("Size of uniqueSourceList: "+uniqueSourceList.size());
       System.out.println("Size of uniqueClaimsList: "+uniqueClaimList.size());
       
       /*now we should again go through the cleanDataFile, for each row, 
       we find the index of the source (from uniqueSourceList) and index of the claim 
       (from uniqueClaimsList ) and make the matrix location 1*/
       
       
       /*Initiate a Arraylist<ArrayList<Integer>>, For each data items (in the clean data file) initialize a new list and fill it with the claimIDs 
       using match of the claim values (converted to double) from clean Data file with uniqueClaimList,
       and add it to another list*/
       
       /*Now we have source claim relationship array and dataItems List*/
       
       /*We should create options in the program so that we can run it either by default book data
       or by luna dong example of stock data*/
       
    }
    
    
    public static void main(String[] args) throws IOException{
        FileLoader.loadCleanFile("Data/clean_stock/ground_truth_nasdaq.txt","Data/clean_stock/stock-2011-07-04.txt");       
    }
}
