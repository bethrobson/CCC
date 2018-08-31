setwd("/Users/beth/Projects/RProgramming")
tempData <- read.csv("phoenix.csv", header=TRUE, sep=",")
years <- tempData$Year
par(
  mfrow = c(3, 4), # rows, columns
  mar = c(1.2, 1.2, 0.5, 0.5), # margins on each plot
  tcl = -0.3, # length of tick marks
  mgp = c(1.7, 0.4, 0), # margin line sizes for axis
  oma = c(2.5, 2.5, 0, 0) # outer margin sizes
)
colCount <- ncol(tempData) - 1
for (col in 2:colCount) {
  month <- tempData[[col]]
  pTitle <- colnames(tempData)[col]
  plot(years, month, main=pTitle, xlab="Year", ylab="Temp", ylim=c(40, 100))
  tempModel = lm(month ~ years)
  abline(tempModel)
}
