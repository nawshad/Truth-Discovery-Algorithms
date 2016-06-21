# README #

This README would normally document whatever steps are necessary to get your application up and running.

### What is this repository for? ###

* Quick summary
This repository contains the experiment code for difference truth discovery algorithms referred from [Veracity of Data: From Truth Discovery Computation Algorithms to Models of Misinformation Dynamics](http://www.morganclaypool.com/doi/abs/10.2200/S00676ED1V01Y201509DTM042). 

* Version
This is version 1 of such code implemenatation. This version contains exact implementation of agreement based truth discovery formulae stated in the above mentioned book. The following observation can be made from this version implementation (this may subject to change later based on further code refactoring and fixing bugs in future).

**Most of the algorithms converges in few iterations with fixed source and claim values. The data provided in the book, has three data items, such as, presidents of US, France and Russia. US has two values or claims, such as Obama and Clinton, likewise, France has Hollande and Sarcozy and Russia has Putin, Yeltsin and Medvedev. There are total 7 sources which are claiming these 7 values. The relation between the sources and claims are represented as a 2D matrix, where rows are sources and columns are claims. Different helper functions have been created for truth value calculation, see utils package. 

### How do I get set up? ###

Clone the repo in your local space or download it. Go inside the folder, run the following command: java -classpath "dist/TruthDiscovery.jar:dist/lib/*:" truthdiscovery.Main <arg1>. 

arg1 can take value 1 to 9. Following are the argument values corresponding to different truth discovery algorithms :

1: Sum
2: Average Log
3: Investment
4: Pooled Investment
5: Truth Discovery
6: Cosine
7: Two Estimates
8: Three Estimates


### Contribution guidelines ###

* Suggestions on removing code smells:
You are more than welcome to suggest me on how to remove code smells from the code.
* Code review: 
Any suggestions on better coding is much appreciated.


### Who do I talk to? ###

* Please email Repo owner or admin: me.naws@gmail.com or nawshad@ualberta.ca