/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import static truthdiscovery.Main.GroundTruthDataItemList;
import static truthdiscovery.Main.Sv_count_List;
import static truthdiscovery.Main.claimList;
import static truthdiscovery.Main.col;
import static truthdiscovery.Main.listDataItems;
import static truthdiscovery.Main.row;
import static truthdiscovery.Main.scores;
import static truthdiscovery.Main.sourceList;

/**
 *
 * @author nawshad
 */
public class GeneralUtils {
    
    //Given an element in the data items, finds out which data item it belongs to
    public static int findDataItemIndex(int claimIndex){
        for(int i=0; i<listDataItems.size(); i++){
            for(int j=0; j<listDataItems.get(i).size(); j++){
                if(listDataItems.get(i).get(j)==claimIndex){
                    return i;
                }
            }
        }
        return -1;
    }
    
    //Given a data item index, finds out the total number of sources pointed by all of the sources of that particular data item
    public static int numberSourcesForDataItem(int dataItemIndex){
        int sum = 0;
        for(int i=0; i<listDataItems.get(dataItemIndex).size(); i++){
            sum += Sv_count_List.get(listDataItems.get(dataItemIndex).get(i));
        }
        return sum;   
    }
    
    //Given a claim id finds out all the sources that are claiming it and returns as an ArrayList
    public static ArrayList<Integer> sourceListforClaims(int claimIndex/*, double[][] scores*/){
        ArrayList<Integer> sourceList = new ArrayList<Integer>();
        for(int i=0; i < col; i++){
            for (int j=0; j< row; j++){
                if(i==claimIndex){
                    if(scores.get(j).get(i)>0){
                        //System.out.println("j value:"+j+" i value:"+i);
                        sourceList.add(j);
                    }
                }
            }
        }
        return sourceList;
    }
    
    //given a claimID finds out all the sourcesList for a data item where the claim ID exist and returns as the list
    public static ArrayList<Integer> sourceListforDataItem(int claimID){
        ArrayList<Integer> sourcesListforDataItem = new ArrayList<Integer>();  
        //find out all the data items  index where that claim exist
        int dataItemIndex = GeneralUtils.findDataItemIndex(claimID);
        for(int i=0; i <listDataItems.get(dataItemIndex).size(); i++){
             ArrayList<Integer> sources = new ArrayList<Integer>();
             sources = GeneralUtils.sourceListforClaims(listDataItems.get(dataItemIndex).get(i));
             for(int j=0; j<sources.size(); j++){
                 if(!sourcesListforDataItem.contains(sources.get(j))){
                     sourcesListforDataItem.add(sources.get(j));
                 }
             }
        }
        return sourcesListforDataItem;
    }
    
    public static ArrayList<Integer> claimsListforDataItemGivenSourceID(int SourceID){
        ArrayList<Integer> claimsListforDataItem = new ArrayList<Integer>(); 
       
        //find out all the data items  index where that claim exist
        for(int h=0; h<scores.get(SourceID).size(); h++){
            if(scores.get(SourceID).get(h) == 1){
                //System.out.println("h: "+h);
                for(int i=0; i<listDataItems.get(GeneralUtils.findDataItemIndex(h)).size(); i++){
                    claimsListforDataItem.add(listDataItems.get(GeneralUtils.findDataItemIndex(h)).get(i));   
                }   
            }
        }
        return claimsListforDataItem;
    }
    
    
    public static LinkedHashSet<String> showOrderedSources(ArrayList<Double> sourceScore){
        ArrayList<Double> toSortSourceScore = new ArrayList<Double>(sourceScore);
        Collections.sort(toSortSourceScore, Collections.reverseOrder());
        //System.out.println("Sorted Sources: "+toSortSourceScore);
        LinkedHashSet<String> trustedSources = new LinkedHashSet<String>();
        
        //System.out.println("Sources with most trust (in descending order): ");
        for(int i=0; i<toSortSourceScore.size(); i++){
                for(int j=0; j<sourceScore.size(); j++){
                    if(sourceScore.get(j)==toSortSourceScore.get(i)){
                        trustedSources.add(sourceList.get(j));
                }  
            }   
        }
        //System.out.println(trustedSources);
        return trustedSources;
    }
    
    public static LinkedHashSet<String> showOrderedClaims(ArrayList<Double> claimScore){
        ArrayList<Double> toSortClaimScore = new ArrayList<Double>(claimScore);      
        Collections.sort(toSortClaimScore, Collections.reverseOrder());
        //System.out.println("Sorted Claims: "+toSortClaimScore);
        LinkedHashSet<String> confidentClaims = new LinkedHashSet<String>();
          
        //System.out.println("Claims with most confidence (in descending order): ");
        for(int i=0; i<toSortClaimScore.size(); i++){
            for(int j=0; j<claimScore.size(); j++){
                if(claimScore.get(j)==toSortClaimScore.get(i)){
                    //System.out.println(j);
                    confidentClaims.add(claimList.get(j));
                }
            } 
        }
        //System.out.println(confidentClaims);
        return confidentClaims;

    }
    
    public static void showMatrix(double[][] matrix){
        for(int i=0; i<matrix.length; i++){
            for(int j=0; j<matrix[i].length; j++){
                System.out.print(matrix[i][j]+"\t");
            }
            System.out.println();
        }  
    }
    
    public static void showMatrix(ArrayList<ArrayList<Double>> matrix){
        for(int i=0; i<matrix.size(); i++){
            for(int j=0; j<matrix.get(i).size(); j++){
                System.out.print(matrix.get(i).get(j)+"\t");
            }
            System.out.println();
        }  
    }
    
    public static void showClaimsPerDataItems(ArrayList<Double> claimScore){
        ArrayList<Double> toSortClaimScore = new ArrayList<Double>(claimScore);      
        Collections.sort(toSortClaimScore, Collections.reverseOrder());
        //System.out.println("Sorted Claims: "+toSortClaimScore);
        
        ArrayList<Integer> sortedClaimIDs = new ArrayList<Integer>();
        
        System.out.println("Claims with most confidence for each data item(in descending order): ");
        for(int i=0; i<toSortClaimScore.size(); i++){
            for(int j=0; j<claimScore.size(); j++){
                if(claimScore.get(j)==toSortClaimScore.get(i)){
                    sortedClaimIDs.add(j);
                }
            } 
        }
        
        //System.out.println("Sorted Claim IDs: "+sortedClaimIDs);
        ArrayList<Integer> DataItemID = new ArrayList<Integer>();
        
        ArrayList<Integer> coveredDataItemIndex = new ArrayList<Integer>();
        
        for (int i=0; i<sortedClaimIDs.size(); i++){
            for (int j=0; j<listDataItems.size(); j++){
                if(coveredDataItemIndex.contains(j)){
                    continue;
                }
                for(int k=0; k<listDataItems.get(j).size(); k++){
                    if(sortedClaimIDs.get(i)==listDataItems.get(j).get(k)){
                        if(sortedClaimIDs.get(i)==listDataItems.get(j).get(k)){  
                            System.out.println(GroundTruthDataItemList.get(j)+" >> ["+claimList.get(listDataItems.get(j).get(k))+"] ");
                            coveredDataItemIndex.add(j);
                            break;
                        }
                    }
                }
            }
        }
        //System.out.println();   
    }
    
    //Compares two linked hash sets based on the elements it has in a certain order.
    public static boolean isSameSet(LinkedHashSet<String> lsh1, LinkedHashSet<String> lsh2){
        boolean is_same = true;
        
        Iterator it1 = lsh1.iterator();
        Iterator it2 = lsh2.iterator();
        
        String value1, value2;
        
        if(lsh1.isEmpty()){
            is_same = false;
        }
        
        while(it1.hasNext() && it2.hasNext()){
            value1 = it1.next().toString();
            value2 = it2.next().toString();
            if(value1!=value2){
                is_same = false;
            }
        }
        
        return is_same;
    }

}
