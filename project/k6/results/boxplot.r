#!/usr/bin/R

library(tidyverse)
library(hrbrthemes)
library(viridis)

prepare <- function(path) {
  data <- read.csv(path)
  data <- data[data$metric_name == 'time_lapse',]
  data <- data[c('metric_value','extra_tags', 'metric_name')]
  data$iteration <- str_replace(data$extra_tags,'iteration=' ,'')
  return(data)
}

setwd("/home/fmcruz/Documents/work/iot-project/project/k6/results")

data50_10 <- prepare('scenario1/50devices-10interval/data.csv')

data100_10 <- prepare('scenario1/100devices-10interval/data.csv')
                      
data200_10 <- prepare('scenario1/200devices-10interval/data.csv')
                      
data500_10 <- prepare('scenario1/500devices-10interval/data.csv')
                      
data1000_10 <- prepare('scenario1/1000devices-10interval/data.csv')
                      
data <- data.frame(
  name=c( 
    rep("A",length(data50_10$metric_value)),
    rep("B",length(data100_10$metric_value)),
    rep("C",length(data200_10$metric_value)),
    rep("D",length(data500_10$metric_value)),
    rep("E",length(data1000_10$metric_value))
  ),
  value=c( 
    data50_10$metric_value,
    data100_10$metric_value,
    data200_10$metric_value,
    data500_10$metric_value,
    data1000_10$metric_value
  )
)
data %>%
  ggplot( aes(x=name, y=value, fill=name)) +
  geom_boxplot() +
  scale_fill_viridis(discrete = TRUE, alpha=0.6) +
  geom_jitter(color="black", size=0.1, alpha=0.9) +
  theme_ipsum() +
  theme(
    legend.position="none",
    plot.title = element_text(size=11)
  ) +
  ggtitle("Process Time for Smart Irrigation Scenarios") +
  xlab("experience")
  ylab("process time (ms)")
