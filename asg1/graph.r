#library(mosaic)
#library(nplr)

#DEMONSTRATION dt= 0.1
demo1 = read.csv("demo1.csv")
x <- demo1$x
y <- demo1$y
par(xpd=FALSE)
plot(y ~ x, data=demo1, xlab="X value", ylab="Y Value", main="Demonstration when t = 0.1", col='darkcyan', ylim=c(-1.1, 1.1), xlim=c(0,23), lwd=2)
lines(x, y, col='darkcyan', lwd=2)
#PLANNING FOR DEMONSTRATION dt = 0.1
demo1plan1 = read.csv("dataplan.csv")
xplan <- dataplan$x
yplan <- dataplan$y
par(xpd=FALSE)
plot(yplan ~ xplan, data=dataplan, xlab="X value", ylab="Y Value", main="Plan when t = 0.1", col='blue', ylim=c(-1.1, 1.1), xlim=c(0,23))
lines(xplan, yplan, col='blue', lwd=2)
#Plot of Both Demonstration (DarkCyan) and Planned Trajectory (Blue)
plot(y ~ x, data=data, xlab="X value", ylab="Y Value", main="Demonstration when t = 0.1", col='darkcyan', ylim=c(-1.1, 1.1), xlim=c(0,23), lwd=2)
points(xplan, yplan, col='blue')

#Plan 2, that uses dt= 0.12
dataplan2 = read.csv("dataplan2.csv")
xplan2 <- dataplan2$x
yplan2 <- dataplan2$y
par(xpd=FALSE)
plot(yplan2 ~ xplan2, data=dataplan2, xlab="X value", ylab="Y Value", main="Plan when t = 0.12", col='green', ylim=c(-1.1, 1.1), xlim=c(0,23))
lines(xplan2, yplan2, col='green', lwd=2)

#Plan 2, that uses dt= 0.12 with demonstration as well
plot(yplan2 ~ xplan2, data=dataplan2, xlab="X value", ylab="Y Value", main="Plan when t = 0.12", col='orange', ylim=c(-1.1, 1.1), xlim=c(0,23))
points(x, y, col='darkcyan')

#Demonstration 2, with a brand new start
