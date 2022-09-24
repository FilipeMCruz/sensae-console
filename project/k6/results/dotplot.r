# Libraries
library(tidyverse)
library(hrbrthemes)
library(viridis)

prepare <- function(path) {
  data <- read.csv(path)
  data <- data[data$metric_name == 'time_lapse',]
  data <- data[c('timestamp', 'metric_value', 'extra_tags')]
  data$start <- data$timestamp - min(data$timestamp)
  data$metric_value <- data$metric_value / 1000
  data$iteration <- str_replace(data$extra_tags,'iteration=' ,'')
  return(data)
}

output <- function(dataframe, path) {
  pdot <- ggplot(data=dataframe, mapping=aes(x=start, y=metric_value, col=iteration, label="")) + 
    geom_point() +
    geom_text(nudge_x=0.2) +
    xlab("time data unit was sent (seconds)") +
    ylab("time taken to process data unit (seconds)") +
    scale_color_manual(values = c("#b30000", "#7c1158", "#4421af", "#1a53ff", "#0d88e6", "#00b7c7", "#5ad45a", "#8be04e", "#ebdc78", "#ffb55a"))
  #scale_color_brewer(palette = "Spectral")
  
  pdot
  ggsave(plot = pdot, file = path)
}

process <- function(path) {
  data <- prepare(paste(path,'data.csv', sep = "/"))
  output(data, paste(path, 'data.pdf', sep="/"))
}

processAll <- function() {
  folder <- list.dirs('scenario1')
  folder <- folder[-1]
  for (i in folder) {
    process(i)
  }
}

setwd("/home/fmcruz/Documents/work/iot-project/project/k6/results")

processAll()
