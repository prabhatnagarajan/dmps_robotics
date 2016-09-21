#!/bin/bash

#javac -Xlint -classpath ".:commons-math3-3.6.1/commons-math3-3.6.1.jar:.:/opt/ibm/ILOG/CPLEX_Studio1263/cplex/lib/cplex.jar" *.java

#java -classpath ".:commons-math3-3.6.1/commons-math3-3.6.1.jar:.:/opt/ibm/ILOG/CPLEX_Studio1263/cplex/lib/cplex.jar" -Djava.library.path=/opt/ibm/ILOG/CPLEX_Studio1263/cplex/bin/x86-64_linux -Xmx4g AutomatedMechanismDesign 

#javac *.java

#java DMPRunner 

javac -classpath ".:commons-math3-3.6.1/commons-math3-3.6.1.jar" *.java

java -classpath ".:commons-math3-3.6.1/commons-math3-3.6.1.jar" DMPRunner

Rscript graph.r