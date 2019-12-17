package com.mycompany.myproject.Interview_Questions;

import org.json.JSONObject;

public class JsonParser {

    public static void main(String[] args) {

        String str = "{\"hive.metastore.uris\":\"thrift://apvrp21890.uhc.com:11384\",\"spark.kubernetes.namespace\":\"hatscale-nonprod\"}";

        JSONObject json = new JSONObject(str);

        System.out.println(json.toString());

        System.out.println(json.has("hive.metastore.uris"));

        json.remove("hive.metastore.uris");

        System.out.println(json.toString());

        System.out.println(json.has("hive.metastore.uris"));

        System.out.println(json.put("hive.metastore.uris","new.value").toString());

    }
}
