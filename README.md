# README #

### What is this repository for? ###

* Quick summary:
This repository contains the experiment code for different truth discovery algorithms referred from [Veracity of Data: From Truth Discovery Computation Algorithms to Models of Misinformation Dynamics](http://www.morganclaypool.com/doi/abs/10.2200/S00676ED1V01Y201509DTM042). 

* Version:
This is version 1 of such code implementation by the author. This version contains exact implementation of agreement based truth discovery formulae stated in the above mentioned book. 

### Data and Implementation Overview ###


* **Book Example Data:** The data provided in the book, has three data items, such as, presidents of US, France and Russia. US has two values or claims, such as Obama and Clinton, likewise, France has Hollande and Sarcozy and Russia has Putin, Yeltsin and Medvedev. There are total 7 sources which are claiming these 7 values. The relation between the sources and claims are represented as a 2D matrix, where rows are sources and columns are claims. Different helper functions have been written for truth value calculation (have a look at utils package in the source code). Based on the majority voting algorithms (Sum and Avg Log), the most confident claims are: US->Obama, Russia->Yeltsin and France-> Hollande. The most trusted sources are: S4, S5, S6, S2, S1, S3, S7. See the source code scores matrix to see the relationship between sources and claims.

* **Real World Data (Stock Data, from [Luna Dong](http://lunadong.com/fusionDataSets.htm)):** Stock market data 
for date 2011-07-01 has been utilized. The change of stock price has been used as claim, whereas the first and the second column of tab seperated text file indicates the source and data item list respectively. Stock data from the source NASDAQ (for same date) has been used as the ground truth. 

### Notes on Algorithms for Synthetic DataSet ###

The following observation can be made from this version of implementation (this may subject to change later based on further code refactoring and fixing bugs). 

Except the last three algorithms, all the other ones converges in a few interations, (i.e. Source and Claim values become fixed). At this version, convergence checking and breaking out the loop is not important because more emphasis was provided on implementing algorithms and testing manually with different number of iterations.

*  ***Avg Log***: This algorithm has some issues when a source has exactly one value/claim. The 10 based logarithm returns 0 result for such a scinario. An extra parameter can be used and a default value can be set in that for calculation to avoid this. Avg Log( took 2 iterations) converges earlier than Sum (took 4 iterations). 

* ***Cosine***: Converged after too many (2207) iteration.

* ***Two Estimates***: Doesn't converge. Source and Claims both oscilates in two sets of results. 

* ***Three Estimates***: Calculation breaks if Ts_0 = 1 is used (as suggested in the book), Ts_0 = 0.8 is used instead. Claims converges, though Sources don't converge rather oscilates between two sets of results.

Pooled investment reach convergence much earlier than investment.

For Cosine, Two Estimates and Three Estimates, the source and claim scores could be normalized because in many cases the values grows much bigger and in negative range. 

### Notes on Algorithms for Stock DataSet ###

* Accuracy in the following points indicate how many data items provided the same value as the ground truth data, out of total data items.

* Majority voting based algorithms are more accurate. Algorithms may provide unexpected result based on input data characteristic, e.g., if multiple data items have same claim values, then that claim
value gets more emphasis than other less popular value in data item. The overall accuracy is close to ~0.94.

* Cosine's accuracy is better after the accuracy of summation based methods. Overall accuracy is ~0.88

* Investment, Pooled Investment puts emphasis on rare claim which is repeated in most data item, 
Like, the value 0.0 is repeated in all the the data items at least once, overall accuracy (for both of them) is very poor. 

* Cosine and Truth Finding algorithms puts emphasis on rare claims in a data item which is repeated
number of times in other data items, across all the data items. Truth finder is less accurate than Cosine, with each iteration the accuracy of Cosine decreases. Cosine accuracy starts with ~0.88 then gradually decreases if iteration is more.

* 2 and 3 estimates has same results like Cosine, except that their result does not degrade with further
iterations, accuracy is ~0.85.

Overall, the sum based algoithms perform much better with respect to few iterations and accuracy than the other algorithms which goes hand in hand with the observation stated in the paper where the datasets were used.

### How do I get set up? ###

Clone the repo in your local space or download it. Go inside the folder, run the following command: java -classpath "dist/TruthDiscovery.jar:dist/lib/*:" truthdiscovery.Main <arg1> <arg2> 

arg1 can take two values/switches. Using -sd switch enables the user to run the algorithms with the example data given in the book, where -d can be used for running the algorithms with real data, i.e. stock market data.

arg2 can take value 1 to 9. Following are the argument values corresponding to different truth discovery algorithms :

1. Sum
2. Average Log
3. Investment
4. Pooled Investment
5. Truth Discovery
6. Cosine
7. Two Estimates
8. Three Estimates
9. Run all algorithms at once


### Contribution guidelines ###

* Suggestions on removing code smells:
You are more than welcome to suggest me on how to remove code smells from the code.
* Code review: 
Any suggestion on better coding practice and optimization of exisiting functions is much appreciated.


### Who do I talk to? ###

* Please email Repo owner or admin, Nawshad Farruque: me.naws@gmail.com or nawshad@ualberta.ca