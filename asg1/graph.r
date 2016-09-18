#library(mosaic)
#library(nplr)

#DEMONSTRATION dt= 0.1
demo1 = read.csv("demo1.csv")
x <- demo1$x
y <- demo1$y
par(xpd=FALSE)
plot(y ~ x, data=demo1, xlab="X value", ylab="Y Value", main="Demonstration when dt = 0.1", col='darkcyan', ylim=c(-1.1, 1.1), xlim=c(0,23), lwd=2)
#lines(x, y, col='darkcyan', lwd=2)

#PLANNING FOR DEMONSTRATION dt = 0.1
demo1plan1 = read.csv("plan_demo1_dt_point1.csv")
x1plan1<- demo1plan1$x
y1plan1 <- demo1plan1$y
par(xpd=FALSE)
plot(y1plan1 ~ x1plan1, data=demo1plan1, xlab="X value", ylab="Y Value", main="Plan when dt = 0.1", col='blue', ylim=c(-1.1, 1.1), xlim=c(0,23))
#lines(x1plan1, y1plan1, col='blue', lwd=2)

#Plot of Both Demonstration (DarkCyan) and Planned Trajectory (Blue)
plot(y ~ x, data=demo1, xlab="X value", ylab="Y Value", main="Original Demonstration/Plan when dt = 0.1", col='darkcyan', ylim=c(-1.4, 1.1), xlim=c(0,23), lwd=2)
points(x1plan1, y1plan1, col='blue')
legend(x=-0.01, y=-1.15, inset=c(0, 0),
       legend=c("Original Demonstration", "Planned Trajectory"), 
       col=c("darkcyan","blue"), pch=1, merge=FALSE, cex=1)

#Plan 2, that uses dt= 0.12
demo1plan2 = read.csv("plan_demo1_dt_point12.csv")
x1plan2 <- demo1plan2$x
y1plan2 <- demo1plan2$y
par(xpd=FALSE)
plot(y1plan2 ~ x1plan2, data=demo1plan2, xlab="X value", ylab="Y Value", main="Plan when dt = 0.12", col='green', ylim=c(-1.1, 1.1), xlim=c(0,23))
#lines(x1plan2, y1plan2, col='green', lwd=2)

#Plan 2, that uses dt= 0.12 with demonstration as well
plot(y1plan2 ~ x1plan2, data=demo1plan2, xlab="X value", ylab="Y Value", main="Demonstration(dt=0.1) and Plan(dt = 0.12)", col='orange', ylim=c(-1.4, 1.1), xlim=c(0,23))
points(x, y, col='darkcyan')
legend(x=-0.01, y=-1.15, inset=c(0, 0),
       legend=c("Original Demonstration", "Planned Trajectory"), 
       col=c("darkcyan","orange"), pch=1, merge=FALSE, cex=1)

#----------------------------------------------------------------------------------------------------------------------------------------------------

#Demonstration 2, with a brand new start
#demo2 = read.csv("demo2.csv")
#x2 <- demo2$x
#y2 <- demo2$y
#par(xpd=FALSE)
#plot(y2 ~ x2, data=demo2, xlab="X value", ylab="Y Value", main="Demonstration when dt = 0.1, x0 = 0.75", col='darkcyan', ylim=c(-1.1, 1.1), xlim=c(0,23), lwd=2)
#lines(x2, y2, col='darkcyan', lwd=2)

#Planning for Demonstration 2, with new start
demo1plannew = read.csv("plan_newgoal_demo1_dt_point1.csv")
x1plannew<- demo1plannew$x
y1plannew <- demo1plannew$y
par(xpd=FALSE)
plot(y1plannew ~ x1plannew, data=demo1plannew, xlab="X value", ylab="Y Value", main="Plan when dt = 0.1, New Goal", col='blue', ylim=c(-1.1, 2), xlim=c(0,26))
#lines(x1plannew, y1plannew, col='blue', lwd=2)

#Plot of both Demonstration2 and Planned Trajectory
plot(y ~ x, data=demo1, xlab="X value", ylab="Y Value", main="Original Demonstration/Plan  (New Planned Start/Goal, dt = 0.1)", col='red', ylim=c(-1.1, 2), xlim=c(0,26), lwd=2)
points(x1plannew, y1plannew, col='blue')
legend(x=0, y=2, inset=c(0, 0),
       legend=c("Original Demonstration", "Planned Trajectory"), 
       col=c("red","blue"), pch=1, merge=FALSE, cex=1)


#----------------------------------------------------------------------------------------------------------------------------------------------------------------------
#Plot graph that is half speed with dt = 0.1
hspeed1plan1 = read.csv("plan_demo1_dt_point1_halfx.csv")
xhspeed1plan1<- hspeed1plan1$x
yhspeed1plan1 <- hspeed1plan1$y
par(xpd=FALSE)
plot(yhspeed1plan1 ~ xhspeed1plan1, data=hspeed1plan1, xlab="X value", ylab="Y Value", main="Half-Speed-Plan, dt = 0.1", col='blue', ylim=c(-1.1, 1.1), xlim=c(0,23))
#lines(xhspeed1plan1, yhspeed1plan1, col='blue', lwd=2)

#Plot of Both Demonstration (Red) and Planned Trajectory (Blue) fpr Half Speed
plot(y ~ x, data=demo1, xlab="X value", ylab="Y Value", main="Demonstration/Half-Speed-Plan, dt = 0.1", col='red', ylim=c(-1.35, 1.05), xlim=c(0,23), lwd=2)
points(xhspeed1plan1, yhspeed1plan1, col='blue')
legend(x=0, y=-1.06, inset=c(0, 0),
       legend=c("Original Demonstration", "Planned Trajectory"), 
       col=c("red","blue"), pch=1, merge=FALSE, cex=1)

#BELOW COMMENTED CODE NOT SUPER NECESSARY, POINT CLEAR FROM dt = 0.1
#Plot graph that is half speed with dt=0.12
#hspeed1plan2 = read.csv("plan_demo1_dt_point12_halfx.csv")
#xhspeed1plan2<- hspeed1plan2$x
#yhspeed1plan2 <- hspeed1plan2$y
#par(xpd=FALSE)
#plot(yhspeed1plan2 ~ xhspeed1plan2, data=hspeed1plan2, xlab="X value", ylab="Y Value", main="Half-Speed-Plan, dt = 0.12", col='blue', ylim=c(-1.1, 1.1), xlim=c(0,23))
#lines(xhspeed1plan2, yhspeed1plan2, col='blue', lwd=2)

#Plot of Both Demonstration (DarkCyan) and Planned Trajectory (Blue)
#plot(y ~ x, data=demo1, xlab="X value", ylab="Y Value", main="Demonstration(dt = 0.1)/Half-Speed-Plan(dt=0.12)", col='red', ylim=c(-1.35, 1.1), xlim=c(0,23), lwd=2)
#points(xhspeed1plan2, yhspeed1plan2, col='blue')
#legend(x=0, y=-1.06, inset=c(0, 0),
 #      legend=c("Original Demonstration", "Planned Trajectory"), 
  #     col=c("red","blue"), pch=1, merge=FALSE, cex=1)
#ADD LEGENDS

#Plot graph that is double speed with dt = 0.1
dspeed1plan1 = read.csv("plan_demo1_dt_point1_2x.csv")
xdspeed1plan1<- dspeed1plan1$x
ydspeed1plan1 <- dspeed1plan1$y
par(xpd=FALSE)
plot(ydspeed1plan1 ~ xdspeed1plan1, data=dspeed1plan1, xlab="X value", ylab="Y Value", main="Double-Speed-Plan, dt = 0.1", col='blue', ylim=c(-1.1, 1.1), xlim=c(0,23))
#lines(xhspeed1plan1, yhspeed1plan1, col='blue', lwd=2)

#Plot of Both Demonstration (Red) and Planned Trajectory (Blue) fpr Half Speed
plot(y ~ x, data=demo1, xlab="X value", ylab="Y Value", main="Demonstration/Double-Speed-Plan, dt = 0.1", col='red', ylim=c(-1.35, 1.05), xlim=c(0,23), lwd=2)
points(xdspeed1plan1, ydspeed1plan1, col='blue')
legend(x=0, y=-1.1, inset=c(0, 0),
       legend=c("Original Demonstration", "Planned Trajectory"), 
       col=c("red","blue"), pch=1, merge=FALSE, cex=1)
