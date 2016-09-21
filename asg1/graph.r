#library(mosaic)
#library(nplr)

#DEMONSTRATION dt= 0.1
demo1 = read.csv("demo1.csv")
x <- demo1$x
y <- demo1$y
par(xpd=FALSE)
#jpeg('demo1.jpg')
plot(y ~ x, data=demo1, xlab="X value", ylab="Y Value", main="Demonstration, dt = 0.1", col='darkcyan', ylim=c(-1.1, 1.1), xlim=c(0,23), lwd=2)
#dev.off()

#PLANNING FOR DEMONSTRATION dt = 0.1
demo1plan1 = read.csv("plan_demo1_dt_point1.csv")
x1plan1<- demo1plan1$x
y1plan1 <- demo1plan1$y
par(xpd=FALSE)
#jpeg('plan_demo1_dt_point1.jpg')
plot(y1plan1 ~ x1plan1, data=demo1plan1, xlab="X value", ylab="Y Value", main="Plan for Original Demonstration, dt = 0.1", col='blue', ylim=c(-4, 4), xlim=c(0,23))
#dev.off()

#jpeg('plan_demo1_dt_point1both.jpg')
#Plot of Both Demonstration (DarkCyan) and Planned Trajectory (Blue)
plot(y ~ x, data=demo1, xlab="X value", ylab="Y Value", main="Original Demonstration/Plan, dt = 0.1", col='darkcyan', ylim=c(-1.4, 1.1), xlim=c(0,23), lwd=2)
points(x1plan1, y1plan1, col='blue')
legend(x=-0.01, y=-1.15, inset=c(0, 0),
       legend=c("Original Demonstration", "Planned Trajectory"), 
       col=c("darkcyan","blue"), pch=1, merge=FALSE, cex=1)
#dev.off()

#Plan 2, that uses dt= 0.12
demo1plan2 = read.csv("plan_demo1_dt_point12.csv")
x1plan2 <- demo1plan2$x
y1plan2 <- demo1plan2$y
par(xpd=FALSE)
plot(y1plan2 ~ x1plan2, data=demo1plan2, xlab="X value", ylab="Y Value", main="Plan, dt = 0.12", col='green', ylim=c(-1.1, 1.1), xlim=c(0,23))

#jpeg('plan_demo1_dt_point12both.jpg')
#Plan 2, that uses dt= 0.12 with demonstration as well
plot(y1plan2 ~ x1plan2, data=demo1plan2, xlab="X value", ylab="Y Value", main="Demonstration(dt=0.1) and Plan(dt = 0.12)", col='orange', ylim=c(-1.4, 1.1), xlim=c(0,23))
points(x, y, col='darkcyan')
legend(x=-0.01, y=-1.15, inset=c(0, 0),
       legend=c("Original Demonstration", "Planned Trajectory"), 
       col=c("darkcyan","orange"), pch=1, merge=FALSE, cex=1)
#dev.off()
#----------------------------------------------------------------------------------------------------------------------------------------------------

#Planning for Demonstration 2, with new start
demo1plannew = read.csv("plan_newgoal_demo1_dt_point1.csv")
x1plannew<- demo1plannew$x
y1plannew <- demo1plannew$y
par(xpd=FALSE)
#jpeg('plan_newgoal_demo1_dt_point1.jpg')
plot(y1plannew ~ x1plannew, data=demo1plannew, xlab="X value", ylab="Y Value", main="Plan when dt = 0.1, New Start/Goal", col='blue', ylim=c(-1.1, 2), xlim=c(0,26))
#dev.off()

#jpeg('plan_newgoal_demo1_dt_point1both.jpg')
#Plot of both Demonstration2 and Planned Trajectory
plot(y ~ x, data=demo1, xlab="X value", ylab="Y Value", main="Original Demonstration/Plan(New Start/Goal), dt = 0.1)", col='red', ylim=c(-1.1, 2), xlim=c(0,26), lwd=2)
points(x1plannew, y1plannew, col='blue')
legend(x=0, y=2, inset=c(0, 0),
       legend=c("Original Demonstration", "Planned Trajectory"), 
       col=c("red","blue"), pch=1, merge=FALSE, cex=1)
#dev.off()


#----------------------------------------------------------------------------------------------------------------------------------------------------------------------
#Plot graph that is half speed with dt = 0.1
hspeed1plan1 = read.csv("plan_demo1_dt_point1_halfx.csv")
xhspeed1plan1<- hspeed1plan1$x
yhspeed1plan1 <- hspeed1plan1$y
par(xpd=FALSE)
#jpeg('plan_demo1_dt_point1_halfx.jpg')
plot(yhspeed1plan1 ~ xhspeed1plan1, data=hspeed1plan1, xlab="X value", ylab="Y Value", main="Half-Speed-Plan, dt = 0.1", col='blue', ylim=c(-1.1, 1.1), xlim=c(0,23))
#dev.off()

#jpeg('plan_demo1_dt_point1_halfxboth.jpg')
#Plot of Both Demonstration (Red) and Planned Trajectory (Blue) fpr Half Speed
plot(y ~ x, data=demo1, xlab="X value", ylab="Y Value", main="Demonstration/Half-Speed-Plan, dt = 0.1", col='red', ylim=c(-1.35, 1.05), xlim=c(0,23), lwd=2)
points(xhspeed1plan1, yhspeed1plan1, col='blue')
legend(x=0, y=-1.06, inset=c(0, 0),
       legend=c("Original Demonstration", "Planned Trajectory"), 
       col=c("red","blue"), pch=1, merge=FALSE, cex=1)
#dev.off()

#Plot graph that is double speed with dt = 0.1
dspeed1plan1 = read.csv("plan_demo1_dt_point1_2x.csv")
xdspeed1plan1<- dspeed1plan1$x
ydspeed1plan1 <- dspeed1plan1$y
par(xpd=FALSE)
#jpeg('plan_demo1_dt_point1_2x.jpg')
plot(ydspeed1plan1 ~ xdspeed1plan1, data=dspeed1plan1, xlab="X value", ylab="Y Value", main="Double-Speed-Plan, dt = 0.1", col='blue', ylim=c(-1.1, 1.1), xlim=c(0,23))
#lines(xhspeed1plan1, yhspeed1plan1, col='blue', lwd=2)
#dev.off()

#jpeg('plan_demo1_dt_point1_2xboth.jpg')
#Plot of Both Demonstration (Red) and Planned Trajectory (Blue) fpr Half Speed
plot(y ~ x, data=demo1, xlab="X value", ylab="Y Value", main="Demonstration/Double-Speed-Plan, dt = 0.1", col='red', ylim=c(-1.35, 1.05), xlim=c(0,23), lwd=2)
points(xdspeed1plan1, ydspeed1plan1, col='blue')
legend(x=0, y=-1.1, inset=c(0, 0),
       legend=c("Original Demonstration", "Planned Trajectory"), 
       col=c("red","blue"), pch=1, merge=FALSE, cex=1)
#dev.off()

#Plot Noisy demo
noisydemo = read.csv("noisydemo.csv")
noisyx = noisydemo$x
noisyy = noisydemo$y
par(xpd=FALSE)
jpeg('noisydemo.jpg')
plot(noisyy ~ noisyx, data=noisydemo, xlab="X value", ylab="Y Value", main="Noisy Demonstration, dt = 0.1, Noise Variance = 0.1", col='blue', ylim=c(-1.75, 1.75), xlim=c(-1,26), lwd=2)
dev.off()

#jpeg('noisydemoboth.jpg')
#Plot alongside original demonstration
plot(y ~ x, data=demo1, xlab="X value", ylab="Y Value", main="Original Demonstration/Noisy Demonstration, dt = 0.1", col='red', ylim=c(-1.75, 1.75), xlim=c(-1,26), lwd=2)
points(noisyx, noisyy, col='blue')
legend(x=20, y=-1.4, inset=c(0, 0),
       legend=c("Original Demo", "Noisy Demo"), 
       col=c("red","blue"), pch=1, merge=FALSE, cex=0.9)
#dev.off()

#Plot plan from joint demonstrations
#Plot Noisy demo
#jpeg('noisyplan.jpg')
plan2demos = read.csv("plan_2demos_dt_point1.csv")
plan2demosx = plan2demos$x
plan2demosy = plan2demos$y
par(xpd=FALSE)
plot(plan2demosy ~ plan2demosx, data=plan2demos, xlab="X value", ylab="Y Value", main="Plan from Original & Noisy Demo, dt = 0.1", col='blue', ylim=c(-1.75, 1.75), xlim=c(0,25), lwd=2)
#dev.off()

#jpeg('noisyplanwithdemo.jpg')
plot(plan2demosy ~ plan2demosx, data=plan2demos, xlab="X value", ylab="Y Value", main="Noisy Plan vs. Original Demonstration, dt = 0.1", col='blue', ylim=c(-1.75, 1.75), xlim=c(0,25), lwd=2)
points(x, y, col='red')
legend(x=18, y=-1.4, inset=c(0, 0),
       legend=c("Original Demo", "Planned Trajectory"), 
       col=c("red","blue"), pch=1, merge=FALSE, cex=0.9)
#dev.off()

#jpeg('noisyplannewgoal.jpg')
plan2demosng = read.csv("plan_2demos_newgoal_dt_point1.csv")
plan2demosxng = plan2demosng$x
plan2demosyng = plan2demosng$y
par(xpd=FALSE)
plot(plan2demosyng ~ plan2demosxng, data=plan2demosng, xlab="X value", ylab="Y Value", main="Plan from Original & Noisy Demo: New Start/Goal, dt = 0.1", col='blue', ylim=c(-1.75, 2.5), xlim=c(0,26), lwd=2)
#dev.off()

ftargetx = read.csv("ftargetx.csv")
ftargetxs = ftargetx$s
ftargetxf = ftargetx$ftarget
par(xpd=FALSE)
plot(ftargetxf ~ ftargetxs, data=ftargetx, xlab="S value", ylab="F Value", main="F Target X", col='blue', ylim=c(-20, 20), xlim=c(-0.01,1.01), lwd=2)

ftargety = read.csv("ftargety.csv")
ftargetys = ftargety$s
ftargeyf = ftargety$ftarget
par(xpd=FALSE)
plot(ftargeyf ~ ftargetys, data=ftargety, xlab="S value", ylab="F Value", main="F Target Y", col='blue', ylim=c(-20, 20), xlim=c(-0.01,1.01), lwd=2)

fx = read.csv("fx.csv")
fxs = fx$s
fxf = fx$f
par(xpd=FALSE)
plot(fxf ~ fxs, data=fx, xlab="S value", ylab="F Value", main="F  X", col='blue', ylim=c(-20, 20), xlim=c(-0.01,1.01), lwd=2)

fy = read.csv("fy.csv")
fys = fy$s
fyf = fy$f
par(xpd=FALSE)
plot(fyf ~ fys, data=fy, xlab="S value", ylab="F Value", main="F  Y", col='blue', ylim=c(-20, 20), xlim=c(-0.01,1.01), lwd=2)

planobstacle = read.csv("plan_obstacle_dt_point1.csv")
planobstaclex = planobstacle$x
planobstacley = planobstacle$y
obstacle = read.csv("obstacle.csv")
obstaclex = obstacle$x
obstacley = obstacle$y

par(xpd=FALSE)
#jpeg('obstacle1.jpg')
plot(planobstacley ~ planobstaclex, data=planobstacle, xlab="X value", ylab="Y Value", main="Plan from Original Demo with Obstacle", col='red',  ylim=c(-1.75, 1.75), xlim=c(-1,23), lwd=2)
points(obstaclex, obstacley, col='black')
legend(x=15, y=-1.4, inset=c(0, 0),
       legend=c("Trajectory", "Obstacle"), 
       col=c("red","black"), pch=1, merge=FALSE, cex=0.9)
#dev.off()

planobstacle2 = read.csv("plan_obstacle2_dt_point1.csv")
planobstaclex2 = planobstacle2$x
planobstacley2 = planobstacle2$y
obstacle2 = read.csv("obstacle2.csv")
obstaclex2 = obstacle2$x
obstacley2 = obstacle2$y

par(xpd=FALSE)
#jpeg('obstacle2.jpg')
plot(planobstacley2 ~ planobstaclex2, data=planobstacle2, xlab="X value", ylab="Y Value", main="Plan from Original Demo with Obstacle", col='red',  ylim=c(-3, 1.75), xlim=c(-1,23), lwd=2)
points(obstaclex2, obstacley2, col='black')
legend(x=18, y=-2, inset=c(0, 0),
       legend=c("Trajectory", "Obstacle"), 
       col=c("red","black"), pch=1, merge=FALSE, cex=0.9)
#dev.off()