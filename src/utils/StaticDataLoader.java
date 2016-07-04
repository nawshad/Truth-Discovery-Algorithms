/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.ArrayList;
import java.util.Arrays;
import static truthdiscovery.Main.France;
import static truthdiscovery.Main.Russia;
import static truthdiscovery.Main.Sv_count_List;
import static truthdiscovery.Main.USA;
import static truthdiscovery.Main.Vs_count_List;
import static truthdiscovery.Main.claimList;
import static truthdiscovery.Main.col;
import static truthdiscovery.Main.listDataItems;
import static truthdiscovery.Main.GroundTruthDataItemList;
import static truthdiscovery.Main.row;
import static truthdiscovery.Main.scores;
import static truthdiscovery.Main.sourceList;

/**
 *
 * @author nawshad
 */
public class StaticDataLoader {
    public static void loadStaticData(){
        /*for(int i=0; i<scores.length; i++){
            for(int j=0; j<scores[i].length; j++){
                scores[i][j] = 0;
            }
        }*/
        row = 7;
        col = 7;
        
        for(int i=0; i<row; i++){
            ArrayList<Double> innerList = new ArrayList<Double>();
            for(int j=0; j<col; j++){
                innerList.add(0.0);
            }
            scores.add(innerList);
        }
        
        /*scores[0][0] = 1;
        scores[1][0] = 1;
        scores[1][3] = 1;
        scores[2][1] = 1;
        scores[3][2] = 1;
        scores[3][4] = 1;
        scores[4][4] = 1;
        scores[4][5] = 1;
        scores[5][4] = 1;
        scores[6][6] = 1;*/
        
        scores.get(0).set(0, 1.0);
        scores.get(1).set(0, 1.0);
        scores.get(1).set(3, 1.0);
        scores.get(2).set(1, 1.0);
        scores.get(3).set(2, 1.0);
        scores.get(3).set(4, 1.0);
        scores.get(4).set(4, 1.0);
        scores.get(4).set(5, 1.0);
        scores.get(5).set(4, 1.0);
        scores.get(6).set(6, 1.0);
        
 
        Russia.add(0);// Each Data Items store a reference to the claim index.
        Russia.add(1);
        Russia.add(2);

        listDataItems.add(Russia);

        USA.add(3);
        USA.add(4);

        listDataItems.add(USA);

        France.add(5);
        France.add(6);

        listDataItems.add(France);
        GroundTruthDataItemList.add("Russia");
        GroundTruthDataItemList.add("USA");
        GroundTruthDataItemList.add("France");

        System.out.println("List of DataItems Reference to claims: " + listDataItems);

        sourceList.addAll(Arrays.asList("S1", "S2", "S3", "S4", "S5", "S6", "S7"));
        claimList.addAll(Arrays.asList("Medvedev", "Putin", "Yeltsin", "Clinton", "Obama", "Hollande", "Sarcozy"));

        System.out.println("Source List: "+ sourceList);
        System.out.println("Claim List: "+ claimList);
        
        //Calculating Vs score for each sources and saving that in a list
        for (int i = 0; i < scores.size(); i++) {
            int Vs = 0;
            for (int j = 0; j < scores.get(i).size(); j++) {
                //System.out.print(scores[i][j]+" ");
                if (scores.get(i).get(j) == 1) {
                    Vs++;
                }
            }
            Vs_count_List.add(Vs);
            //System.out.println();
        }
       
        for (int i = 0; i < scores.size(); i++) {
            int sourceCount = 0;
            for (int j = 0; j < scores.get(i).size(); j++) {
                if (scores.get(j).get(i) == 1) {
                    sourceCount++;
                }
            }
            Sv_count_List.add(sourceCount);
        }
        
        System.out.println("Source-Claim realationship Matrix:");
        GeneralUtils.showMatrix(scores);
        
        System.out.println("Claim counts for sources (Vs_Count) :" +Vs_count_List);
        System.out.println("Source counts for claims (Sv_Count) :" +Sv_count_List);
        
    }
    
}
