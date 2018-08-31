library("ggplot2", lib.loc="/Library/Frameworks/R.framework/Versions/3.5/Resources/library")
setwd("/Users/beth/Projects/RProgramming")
tempData <- read.csv("phoenix.csv", header=TRUE, sep=",")
years <- tempData$Year
month <- tempData$Jan
mmvals <- c(min(month), max(month))
mmyears <- c(years[which.min(month)], years[which.max(month)])
mmdata <- data.frame(mmyears, mmvals)
sp <- ggplot(tempData, aes(x = years, y = month))
sp + geom_point(colour="grey60") + 
  labs(x="Year", y="Temperature", title="January average temp in Phoenix") +
  geom_smooth(method=lm, se=FALSE) +
  geom_point(data=mmdata, aes(x=mmyears, y=mmvals), colour="red")




