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
import static truthdiscovery.Main.GroundTruthDataItemList;
import static truthdiscovery.Main.Sv_count_List;
import static truthdiscovery.Main.Vs_count_List;
import static truthdiscovery.Main.col;
import static truthdiscovery.Main.scores;
import static truthdiscovery.Main.listDataItems;
import static truthdiscovery.Main.row;
import static truthdiscovery.Main.scores;
import static truthdiscovery.Main.sourceList;
import static truthdiscovery.Main.claimList;


/**
 *
 * @author nawshad
 */
public class FileLoader { 
    public static void loadCleanFile(String groundTruthFilePath, String cleanDataFilePath) throws IOException{
        GroundTruthDataItemList = new ArrayList<String>();
       //data items of interest from ground truth file, read from file and put them in a list.
       for (String line : Files.readAllLines(Paths.get(groundTruthFilePath))) {
            String[] parts = line.split("\t");
            GroundTruthDataItemList.add(parts[0]);
       }
       //placed in alphabetical order.
       //Collections.sort(GroundTruthDataItemList);
       System.out.println("Ground Truth Items: "+GroundTruthDataItemList);
     
       //Find out the claims and also put them in a list of lists.  
       //Loop through the list DataItemListFromFile, and find out the corresponding claimsIDs (from unique claim list) and save the ids 
       //in another list of list, where the outer list expresses the index for data list and inner list contains the claim ids
       //for them.
       
       Charset charset = Charset.forName("ISO-8859-1"); 
       //Calculating data items and claims
       
       
       ArrayList<String> selectedSources = new ArrayList<String>();
       ArrayList<Double> claimScores = new ArrayList<Double>();
       
       for (String line : Files.readAllLines(Paths.get(cleanDataFilePath),charset)) {
            String[] parts = line.split("\t");  
            if(GroundTruthDataItemList.contains(parts[1])&&!parts[5].isEmpty()&&!parts[5].equals("-0.00")){
                String claim = "";
                if(parts[5].contains("change:")){
                    claim = parts[5].substring(8);
                }else if(parts[5].contains("$")){
                    claim = parts[5].substring(1);
                    //System.out.println("Claim: "+claim);
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
       
       sourceList = new ArrayList<String>();
       for(int i=0; i<selectedSources.size(); i++){
           preSource = selectedSources.get(i);
           if(preSource.equals(postSource)){
               continue;
           }else{
               //System.out.println("Source: "+preSource);
               sourceList.add(preSource);
           }
           postSource = preSource;
       }
       
       double preClaimScore = 100000;
       double postClaimScore = 100000;
       
       ArrayList<Double> uniqueClaimList = new ArrayList<Double>();
       
       claimList = new ArrayList<String>();
       
       for(int j=0; j<claimScores.size(); j++){
           preClaimScore = claimScores.get(j);
           if(preClaimScore == postClaimScore){
               continue;
           }else{
               //System.out.println("Claims: "+preClaimScore);
               uniqueClaimList.add(preClaimScore);
               claimList.add(Double.toString(preClaimScore));
           }
           postClaimScore = preClaimScore;   
       }
       
       System.out.println("Unique Claim List: "+uniqueClaimList);
       
       System.out.println("Size of uniqueSourceList: "+sourceList.size());
       System.out.println("Size of uniqueClaimsList: "+uniqueClaimList.size());
       
       row = sourceList.size();
       col = uniqueClaimList.size();
       
       //double[][] scores = new double[row][col];
       
       scores = new ArrayList<ArrayList<Double>>();
      
       for(int i=0; i<row; i++){
            ArrayList<Double> innerList = new ArrayList<Double>();
            for(int j=0; j<col; j++){
                innerList.add(0.0);
            }
            scores.add(innerList);
        }
       
       /*now we should again go through the cleanDataFile, for each row, 
       we find the index of the source (from uniqueSourceList) and index of the claim 
       (from uniqueClaimsList ) and make the matrix location 1*/
       
       for(int i=0; i<row; i++){
            ArrayList<Integer> innerList = new ArrayList<Integer>();
            for(int j=0; j<col; j++){
                innerList.add(0);
            }
       }
         
        
       for (String line : Files.readAllLines(Paths.get(cleanDataFilePath),charset)) {
            String[] parts = line.split("\t"); 
            if(GroundTruthDataItemList.contains(parts[1])&&!parts[5].isEmpty()&&!parts[5].equals("-0.00")){
                String claim = "";
                if(parts[5].contains("change:")){
                    claim = parts[5].substring(8);
                }else if(parts[5].contains("$")){
                    claim = parts[5].substring(1);
                }
                else{
                    claim = parts[5];
                }
                int i = sourceList.indexOf(parts[0]);
                int j = uniqueClaimList.indexOf(Double.parseDouble(claim));
                scores.get(i).set(j, 1.0);
            } 
       }
       
       GeneralUtils.showMatrix(scores);
          
       listDataItems = new ArrayList<ArrayList<Integer>>();
       int preClaimIndex = -1;
       int postClaimIndex = -1;
       
        for(int i=0; i<GroundTruthDataItemList.size(); i++){
            ArrayList<Integer> claimIDList = new ArrayList<Integer>();
            ArrayList<Integer> uniqueClaimIDList = new ArrayList<Integer>();
            for (String line : Files.readAllLines(Paths.get(cleanDataFilePath),charset)){
                String[] parts = line.split("\t");
                if(GroundTruthDataItemList.get(i).equals(parts[1])&&!parts[5].isEmpty()&&!parts[5].equals("-0.00")){
                    String claim = "";
                    if(parts[5].contains("change:")){
                        claim = parts[5].substring(8);
                    }
                    else if(parts[5].contains("$")){
                        claim = parts[5].substring(1);
                    }
                    else{
                        claim = parts[5];
                    }
                    int claimID = uniqueClaimList.indexOf(Double.parseDouble(claim));
                    claimIDList.add(claimID);
                }
            }
            Collections.sort(claimIDList);
            for(int claimIndex : claimIDList){
                preClaimIndex = claimIndex;
                if(preClaimIndex != postClaimIndex){
                    uniqueClaimIDList.add(preClaimIndex);
                }
                postClaimIndex = preClaimIndex;
            }
            
            listDataItems.add(uniqueClaimIDList);
        }
   
       System.out.println("List Item Size: "+listDataItems.size()+ " Ground Truth Data Item Size: "+GroundTruthDataItemList.size());
       System.out.println("List Items: "+listDataItems);
      
        //Calculating Vs score for each sources and saving that in a list
        for (int i = 0; i < row; i++) {
            int Vs = 0;
            for (int j = 0; j < col; j++) {
                //System.out.print(scores[i][j]+" ");
                if (scores.get(i).get(j) == 1) {
                    Vs++;
                }
            }
            Vs_count_List.add(Vs);
            //System.out.println();
        }
       
        for (int i = 0; i < col; i++) {
            int sourceCount = 0;
            for (int j = 0; j < row; j++) {
                if (scores.get(j).get(i) == 1) {
                    sourceCount++;
                }
            }
            Sv_count_List.add(sourceCount);
        }
        
        System.out.println("Claim counts for sources (Vs_Count) :" +Vs_count_List);
        System.out.println("Source counts for claims (Sv_Count) :" +Sv_count_List);
       
       
       
    }
    
    
}
