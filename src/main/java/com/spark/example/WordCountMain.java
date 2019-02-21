package com.spark.example;

import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import scala.Tuple2;

import java.util.Arrays;

public final class WordCountMain {
    static Logger logger = Logger.getLogger(WordCountMain.class);
    public static void main(String[] args) {
        final SparkConf conf = new SparkConf()
                .setMaster("local[*]")
                .setAppName("Word Count");
        final SparkContext sc = new SparkContext(conf);
        final String file = "textfile.txt";

        final JavaRDD<String> lines = sc.textFile(file, 1).toJavaRDD();
//        lines.foreach(s -> System.out.println(s));
        final JavaRDD<String> words = lines.flatMap(s -> Arrays.asList(s.split("\\s+")).iterator());
        final JavaPairRDD<String, Integer> ones = words.mapToPair(s -> new Tuple2<>(s, 1));
        final JavaPairRDD<String, Integer> counts = ones.reduceByKey((value1, value2) -> value1 + value2);
//        counts.foreach(s -> System.out.println(s));
        logger.warn("\n\nlogging test by ankur  >>>>>> <<<<<<< hello  \n\n");
//        logger.warn(counts.collectAsMap());
        counts.saveAsTextFile("results");
        sc.stop();
    }
}