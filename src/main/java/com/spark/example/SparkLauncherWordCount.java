package com.spark.example;

import com.google.common.collect.Maps;
import org.apache.spark.launcher.SparkAppHandle;
import org.apache.spark.launcher.SparkLauncher;

import java.util.Map;

public class SparkLauncherWordCount {

    public static void main(String[] args) {
        Map<String, String> env = Maps.newHashMap();
        env.put("SPARK_PRINT_LAUNCH_COMMAND", "1");
        SparkAppHandle handle = null;
        try
        {
            handle   = new SparkLauncher(env
                    )
                    .setAppResource("/Users/ansaran/dev/spark_examples/target/spark-examples-1.0-SNAPSHOT.jar")
                    .setMainClass("com.spark.example.WordCountMain")
                    .setMaster("spark://ansaran-a01.vmware.com:7077")
                    .setDeployMode("client")
                    .setSparkHome("/usr/local/spark")
                    .addSparkArg("--verbose")
                    .setConf("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
                    .startApplication();
            System.out.println(handle.getState());

            while(handle.getState().toString().trim().equals("UNKNOWN")) {
                Thread.sleep(1);
            }
            System.out.println(handle.getState());
            System.out.println(handle.getState());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }



    }
}
