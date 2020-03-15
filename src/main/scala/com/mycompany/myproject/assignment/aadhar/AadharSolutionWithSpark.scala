package com.mycompany.myproject.assignment.aadhar

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object AadharSolutionWithSpark {


  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder().appName("").config("spark.driver.host","localhost").master("local[1]").getOrCreate()

    val sc = spark.sparkContext

    sc.setLogLevel("ERROR")

    val aadharDF = spark.read.option("header","true").option("inferschema","true").csv("/Users/gcl/Documents/Github/spark_tutorial/src/main/resources/aadhar/auth.csv")

    aadharDF.printSchema()

    println(s"Aadhar Dataset Count: ${aadharDF.count()}")

    val pattern = "[0-9]*".r

    def isOnlyIntegers(str:String):Boolean={
      pattern.pattern.matcher(str).matches()
    }

//    def isOnlyIntegers(str:String):Boolean= {
//      str.matches("[0-9]*")
//    }

    val integersOnlyUDF = udf(isOnlyIntegers:(String)=>(Boolean))

    import spark.implicits._

    val outDF = aadharDF.filter("aua>650000")
                        .filter("res_state_name!='Delhi'")
                        .filter(integersOnlyUDF($"sa"))//.select("sa").distinct()

    //.filter( data => pattern.pattern.matcher(data.getAs[String]("sa")).matches())

    // res_state_name  Total => 4916
    // res_state_name == Delhi => 188
    // res_state_name == null  => 556
    // res_state_name != Delhi && !=null  => 4172


    println(s"OutDF Count : ${outDF.count()}")

    outDF.show(5,false)


//    val s2c = new  Schema2CaseClass()
//    import s2c.implicits.defaultTypeConverter
//    println(s2c.schemaToCaseClass(outDF.schema, "Aadhar"))

    spark.stop()
    spark.close()

  }


}


/*

============ OUTOUT ============

root
 |-- auth_code: string (nullable = true)
 |-- subreq_id: integer (nullable = true)
 |-- aua: string (nullable = true)
 |-- sa: string (nullable = true)
 |-- asa: integer (nullable = true)
 |-- ver: double (nullable = true)
 |-- tid: string (nullable = true)
 |-- license_id: string (nullable = true)
 |-- req_datetime: timestamp (nullable = true)
 |-- tkn_used_flag: integer (nullable = true)
 |-- tkn_type: string (nullable = true)
 |-- pid_ts: timestamp (nullable = true)
 |-- pid_ver: integer (nullable = true)
 |-- pi_uses_flag: integer (nullable = true)
 |-- pa_uses_flag: integer (nullable = true)
 |-- pfa_uses_flag: integer (nullable = true)
 |-- bio_uses_flag: integer (nullable = true)
 |-- bt_fmr_uses_flag: integer (nullable = true)
 |-- bt_fir_uses_flag: integer (nullable = true)
 |-- bt_iir_uses_flag: integer (nullable = true)
 |-- pin_uses_flag: integer (nullable = true)
 |-- otp_uses_flag: integer (nullable = true)
 |-- pi_used_flag: integer (nullable = true)
 |-- pa_used_flag: integer (nullable = true)
 |-- pfa_used_flag: integer (nullable = true)
 |-- bio_used_flag: integer (nullable = true)
 |-- bt_fmr_used_flag: integer (nullable = true)
 |-- bt_fir_used_flag: integer (nullable = true)
 |-- bt_iir_used_flag: integer (nullable = true)
 |-- pin_used_flag: integer (nullable = true)
 |-- otp_used_flag: integer (nullable = true)
 |-- otp_identifier: string (nullable = true)
 |-- lang: string (nullable = true)
 |-- pi_name_used_flag: integer (nullable = true)
 |-- pi_ms: string (nullable = true)
 |-- pi_mv: integer (nullable = true)
 |-- pi_match_score: integer (nullable = true)
 |-- pi_lname_used_flag: integer (nullable = true)
 |-- pi_lname_ms: string (nullable = true)
 |-- pi_lname_mv: string (nullable = true)
 |-- pi_lname_match_score: string (nullable = true)
 |-- pi_phone_used_flag: integer (nullable = true)
 |-- pi_email_used_flag: integer (nullable = true)
 |-- pi_gender_used_flag: integer (nullable = true)
 |-- pi_gender: string (nullable = true)
 |-- pi_dob_used_flag: integer (nullable = true)
 |-- pi_dob: string (nullable = true)
 |-- pi_dobt_used_flag: integer (nullable = true)
 |-- pi_dobt: string (nullable = true)
 |-- pi_age_used_flag: integer (nullable = true)
 |-- pi_age: string (nullable = true)
 |-- pfa_addr_used_flag: integer (nullable = true)
 |-- pfa_addr_ms: string (nullable = true)
 |-- pfa_addr_mv: integer (nullable = true)
 |-- pfa_match_score: integer (nullable = true)
 |-- pfa_laddr_used_flag: integer (nullable = true)
 |-- pfa_laddr_ms: string (nullable = true)
 |-- pfa_laddr_mv: string (nullable = true)
 |-- pfa_laddr_match_score: string (nullable = true)
 |-- pa_ms: string (nullable = true)
 |-- pa_co_used_flag: integer (nullable = true)
 |-- pa_house_used_flag: integer (nullable = true)
 |-- pa_street_used_flag: integer (nullable = true)
 |-- pa_lm_used_flag: integer (nullable = true)
 |-- pa_loc_used_flag: integer (nullable = true)
 |-- pa_vtc_used_flag: integer (nullable = true)
 |-- pa_vtc: string (nullable = true)
 |-- pa_po_used_flag: integer (nullable = true)
 |-- pa_po: string (nullable = true)
 |-- pa_subdist_used_flag: integer (nullable = true)
 |-- pa_subdist: string (nullable = true)
 |-- pa_dist_used_flag: integer (nullable = true)
 |-- pa_dist: string (nullable = true)
 |-- pa_state_used_flag: integer (nullable = true)
 |-- pa_state: string (nullable = true)
 |-- pa_pc_used_flag: integer (nullable = true)
 |-- pa_pc: string (nullable = true)
 |-- fmr_count: integer (nullable = true)
 |-- fir_count: string (nullable = true)
 |-- iir_count: integer (nullable = true)
 |-- finger_match_score: integer (nullable = true)
 |-- uidai_tfmr: string (nullable = true)
 |-- aua_tfmr: string (nullable = true)
 |-- finger_match_threshold: integer (nullable = true)
 |-- fmr_gall_type: string (nullable = true)
 |-- fmr_gall_vendor: string (nullable = true)
 |-- fmr_sdk_vendor: string (nullable = true)
 |-- fmr_sdk_version: string (nullable = true)
 |-- fir_gall_type: string (nullable = true)
 |-- fir_gall_vendor: string (nullable = true)
 |-- fir_sdk_vendor: string (nullable = true)
 |-- fir_sdk_version: string (nullable = true)
 |-- iir_gall_type: string (nullable = true)
 |-- iir_gall_vendor: string (nullable = true)
 |-- iir_sdk_vendor: string (nullable = true)
 |-- iir_sdk_version: string (nullable = true)
 |-- auth_result: string (nullable = true)
 |-- error_code: integer (nullable = true)
 |-- error_classfn: string (nullable = true)
 |-- resp_datetime: timestamp (nullable = true)
 |-- auth_duration: integer (nullable = true)
 |-- fdc: string (nullable = true)
 |-- idc: string (nullable = true)
 |-- udc: string (nullable = true)
 |-- locn_lat: string (nullable = true)
 |-- locn_lng: string (nullable = true)
 |-- locn_vtc_code: string (nullable = true)
 |-- locn_subdist_code: string (nullable = true)
 |-- locn_dist_code: string (nullable = true)
 |--  locn_state_code: string (nullable = true)
 |-- locn_pc: integer (nullable = true)
 |--  enr_ref_id: string (nullable = true)
 |-- res_gender: string (nullable = true)
 |-- res_birth_day: integer (nullable = true)
 |-- res_birth_month: integer (nullable = true)
 |-- res_birth_year: integer (nullable = true)
 |-- res_dob: string (nullable = true)
 |-- res_dobt: string (nullable = true)
 |-- res_age: integer (nullable = true)
 |-- res_pincode: integer (nullable = true)
 |-- res_vtc_code: string (nullable = true)
 |-- res_vtc_name: string (nullable = true)
 |-- res_po_name: string (nullable = true)
 |-- res_subdist_code: string (nullable = true)
 |-- res_subdist_name: string (nullable = true)
 |-- res_dist_code: string (nullable = true)
 |-- res_dist_name: string (nullable = true)
 |-- res_state_code: integer (nullable = true)
 |-- res_state_name: string (nullable = true)
 |-- uid_gen_date: timestamp (nullable = true)
 |-- txn: string (nullable = true)
 |-- auth_type: string (nullable = true)
 |-- hash_uid: string (nullable = true)
 |-- locn_alt: string (nullable = true)
 |-- lot: string (nullable = true)
 |-- bfd_done_flag: integer (nullable = true)
 |-- finger_matching_type: string (nullable = true)
 |-- finger_fusion_perfomed: integer (nullable = true)
 |-- iris_match_score: double (nullable = true)
 |-- iris_threshold: integer (nullable = true)
 |-- iris_matching_type: string (nullable = true)
 |-- iris_fusion_perfomed: integer (nullable = true)
 |-- auth_xml_size: integer (nullable = true)
 |-- pid_size: integer (nullable = true)
 |-- data_type: string (nullable = true)
 |-- skey_scheme: string (nullable = true)
 |-- ssk_type: string (nullable = true)
 |-- ki: string (nullable = true)
 |-- kyc_flag: integer (nullable = true)

Aadhar Dataset Count: 4916
OutDF Count : 1691
+--------------------------------+---------+------+------+----+---+------+------------+-------------------+-------------+--------+-------------------+-------+------------+------------+-------------+-------------+----------------+----------------+----------------+-------------+-------------+------------+------------+-------------+-------------+----------------+----------------+----------------+-------------+-------------+--------------+----+-----------------+-----+-----+--------------+------------------+-----------+-----------+--------------------+------------------+------------------+-------------------+---------+----------------+------+-----------------+-------+----------------+------+------------------+-----------+-----------+---------------+-------------------+------------+------------+---------------------+-----+---------------+------------------+-------------------+---------------+----------------+----------------+------+---------------+-----+--------------------+----------+-----------------+-------+------------------+--------+---------------+-----+---------+---------+---------+------------------+----------+--------+----------------------+-------------+---------------+--------------+---------------+-------------+---------------+--------------+---------------+-------------+---------------+--------------+---------------+-----------+----------+-------------+-------------------+-------------+---+---+------------------+--------+--------+-------------+-----------------+--------------+----------------+-------+------------------------------------+----------+-------------+---------------+--------------+----------+--------+-------+-----------+----------------------------------------+------------------+-----------------+----------------+----------------+-------------+-------------+--------------+--------------+-------------------+--------------------------+---------+--------+--------+---+-------------+--------------------+----------------------+----------------+--------------+------------------+--------------------+-------------+--------+---------+-----------+--------+----+--------+
|auth_code                       |subreq_id|aua   |sa    |asa |ver|tid   |license_id  |req_datetime       |tkn_used_flag|tkn_type|pid_ts             |pid_ver|pi_uses_flag|pa_uses_flag|pfa_uses_flag|bio_uses_flag|bt_fmr_uses_flag|bt_fir_uses_flag|bt_iir_uses_flag|pin_uses_flag|otp_uses_flag|pi_used_flag|pa_used_flag|pfa_used_flag|bio_used_flag|bt_fmr_used_flag|bt_fir_used_flag|bt_iir_used_flag|pin_used_flag|otp_used_flag|otp_identifier|lang|pi_name_used_flag|pi_ms|pi_mv|pi_match_score|pi_lname_used_flag|pi_lname_ms|pi_lname_mv|pi_lname_match_score|pi_phone_used_flag|pi_email_used_flag|pi_gender_used_flag|pi_gender|pi_dob_used_flag|pi_dob|pi_dobt_used_flag|pi_dobt|pi_age_used_flag|pi_age|pfa_addr_used_flag|pfa_addr_ms|pfa_addr_mv|pfa_match_score|pfa_laddr_used_flag|pfa_laddr_ms|pfa_laddr_mv|pfa_laddr_match_score|pa_ms|pa_co_used_flag|pa_house_used_flag|pa_street_used_flag|pa_lm_used_flag|pa_loc_used_flag|pa_vtc_used_flag|pa_vtc|pa_po_used_flag|pa_po|pa_subdist_used_flag|pa_subdist|pa_dist_used_flag|pa_dist|pa_state_used_flag|pa_state|pa_pc_used_flag|pa_pc|fmr_count|fir_count|iir_count|finger_match_score|uidai_tfmr|aua_tfmr|finger_match_threshold|fmr_gall_type|fmr_gall_vendor|fmr_sdk_vendor|fmr_sdk_version|fir_gall_type|fir_gall_vendor|fir_sdk_vendor|fir_sdk_version|iir_gall_type|iir_gall_vendor|iir_sdk_vendor|iir_sdk_version|auth_result|error_code|error_classfn|resp_datetime      |auth_duration|fdc|idc|udc               |locn_lat|locn_lng|locn_vtc_code|locn_subdist_code|locn_dist_code| locn_state_code|locn_pc| enr_ref_id                         |res_gender|res_birth_day|res_birth_month|res_birth_year|res_dob   |res_dobt|res_age|res_pincode|res_vtc_code                            |res_vtc_name      |res_po_name      |res_subdist_code|res_subdist_name|res_dist_code|res_dist_name|res_state_code|res_state_name|uid_gen_date       |txn                       |auth_type|hash_uid|locn_alt|lot|bfd_done_flag|finger_matching_type|finger_fusion_perfomed|iris_match_score|iris_threshold|iris_matching_type|iris_fusion_perfomed|auth_xml_size|pid_size|data_type|skey_scheme|ssk_type|ki  |kyc_flag|
+--------------------------------+---------+------+------+----+---+------+------------+-------------------+-------------+--------+-------------------+-------+------------+------------+-------------+-------------+----------------+----------------+----------------+-------------+-------------+------------+------------+-------------+-------------+----------------+----------------+----------------+-------------+-------------+--------------+----+-----------------+-----+-----+--------------+------------------+-----------+-----------+--------------------+------------------+------------------+-------------------+---------+----------------+------+-----------------+-------+----------------+------+------------------+-----------+-----------+---------------+-------------------+------------+------------+---------------------+-----+---------------+------------------+-------------------+---------------+----------------+----------------+------+---------------+-----+--------------------+----------+-----------------+-------+------------------+--------+---------------+-----+---------+---------+---------+------------------+----------+--------+----------------------+-------------+---------------+--------------+---------------+-------------+---------------+--------------+---------------+-------------+---------------+--------------+---------------+-----------+----------+-------------+-------------------+-------------+---+---+------------------+--------+--------+-------------+-----------------+--------------+----------------+-------+------------------------------------+----------+-------------+---------------+--------------+----------+--------+-------+-----------+----------------------------------------+------------------+-----------------+----------------+----------------+-------------+-------------+--------------+--------------+-------------------+--------------------------+---------+--------+--------+---+-------------+--------------------+----------------------+----------------+--------------+------------------+--------------------+-------------+--------+---------+-----------+--------+----+--------+
|c31a04666ee249d8b9f29ec5806a9d8c|1        |740000|740000|1100|1.6|public|0000740000:0|2015-06-03 00:00:11|0            |null    |2015-06-02 23:59:14|1      |0           |0           |1            |0            |0               |0               |0               |0            |0            |0           |0           |1            |0            |0               |0               |0               |0            |0            |null          |23  |0                |null |null |null          |0                 |null       |null       |null                |0                 |0                 |0                  |null     |0               |null  |0                |null   |0               |null  |1                 |P          |60         |0              |0                  |null        |null        |null                 |null |0              |0                 |0                  |0              |0               |0               |null  |0              |null |0                   |null      |0                |null   |0                 |null    |0              |null |null     |null     |null     |null              |null      |null    |null                  |null         |null           |null          |null           |null         |null           |null          |null           |null         |null           |null          |null           |n          |200       |null         |2015-06-03 00:00:11|102          |NC |NA |UIDAI:SampleClient|null    |null    |null         |null             |null          |null            |560103 |40b495a3-3fc8-44fc-861a-ebf3c3fcbd05|M         |17           |4              |1985          |4/17/1985 |D       |30     |143001     |3.02027E+12                             |Amritsar -I       |Amritsar         |null            |null            |302          |Amritsar     |3             |Punjab        |2011-11-14 04:33:48|TU:PFA:2015-06-02T23:59:14|D        |NA      |null    |P  |0            |null                |0                     |null            |null          |null              |0                   |4350         |203     |X        |N          |null    |null|0       |
|a5aa1037e51545b996451abf96f2207a|1        |740000|740000|1100|1.6|public|0000740000:0|2015-06-03 00:00:14|0            |null    |2015-06-02 23:59:17|1      |0           |0           |1            |0            |0               |0               |0               |0            |0            |0           |0           |1            |0            |0               |0               |0               |0            |0            |null          |23  |0                |null |null |null          |0                 |null       |null       |null                |0                 |0                 |0                  |null     |0               |null  |0                |null   |0               |null  |1                 |P          |55         |0              |0                  |null        |null        |null                 |null |0              |0                 |0                  |0              |0               |0               |null  |0              |null |0                   |null      |0                |null   |0                 |null    |0              |null |null     |null     |null     |null              |null      |null    |null                  |null         |null           |null          |null           |null         |null           |null          |null           |null         |null           |null          |null           |n          |200       |null         |2015-06-03 00:00:14|166          |NC |NA |UIDAI:SampleClient|null    |null    |null         |null             |null          |null            |560103 |0453a809-3248-4985-a284-005d814beada|M         |20           |11             |1966          |11/20/1966|D       |48     |506101     |597615                                  |Mahabubabad       |Mahabubabad      |5119            |Mahabubabad     |535          |Warangal     |28            |Andhra Pradesh|2013-08-25 19:28:23|TU:PFA:2015-06-02T23:59:17|D        |NA      |null    |P  |0            |null                |0                     |null            |null          |null              |0                   |4414         |242     |X        |N          |null    |null|0       |
|7f9d928a49384c82a5ab7f0f4721d209|1        |740000|740000|1100|1.6|public|0000740000:0|2015-06-03 00:00:20|0            |null    |2015-06-02 23:59:20|1      |1           |0           |0            |0            |0               |0               |0               |0            |0            |1           |0           |0            |0            |0               |0               |0               |0            |0            |null          |23  |1                |P    |60   |100           |0                 |null       |null       |null                |0                 |0                 |1                  |M        |1               |1978  |0                |null   |0               |null  |0                 |null       |null       |null           |0                  |null        |null        |null                 |null |0              |0                 |0                  |0              |0               |0               |null  |0              |null |0                   |null      |0                |null   |0                 |null    |0              |null |null     |null     |null     |null              |null      |null    |null                  |null         |null           |null          |null           |null         |null           |null          |null           |null         |null           |null          |null           |y          |null      |null         |2015-06-03 00:00:20|230          |NC |NA |UIDAI:SampleClient|null    |null    |null         |null             |null          |null            |560103 |fe3d5738-0c54-424c-9b85-274983ac2b75|M         |12           |12             |1978          |12/12/1978|D       |36     |400094     |2.72302E+13                             |Anu Shakti Nagar  |null             |null            |null            |2723         |Mumbai       |27            |Maharashtra   |2011-09-27 05:25:42|TU:POI:2015-06-02T23:59:20|D        |NA      |null    |P  |0            |null                |0                     |null            |null          |null              |0                   |4326         |181     |X        |N          |null    |null|0       |
|245ec6a9824d443a9039cd7a1ef8c425|1        |740000|740000|1100|1.6|public|0000740000:0|2015-06-03 00:00:22|0            |null    |2015-06-02 23:59:26|1      |1           |0           |0            |0            |0               |0               |0               |0            |0            |1           |0           |0            |0            |0               |0               |0               |0            |0            |null          |23  |1                |P    |60   |0             |0                 |null       |null       |null                |0                 |0                 |1                  |M        |1               |1985  |0                |null   |0               |null  |0                 |null       |null       |null           |0                  |null        |null        |null                 |null |0              |0                 |0                  |0              |0               |0               |null  |0              |null |0                   |null      |0                |null   |0                 |null    |0              |null |null     |null     |null     |null              |null      |null    |null                  |null         |null           |null          |null           |null         |null           |null          |null           |null         |null           |null          |null           |n          |100       |null         |2015-06-03 00:00:23|268          |NC |NA |UIDAI:SampleClient|null    |null    |null         |null             |null          |null            |560103 |f558423d-6365-4c34-925e-b283d8bf9540|M         |5            |4              |1982          |4/5/1982  |V       |33     |520010     |2.81622E+13                             |Vijayawada (Urban)|Venkateswarapuram|null            |null            |2816         |Krishna      |28            |Andhra Pradesh|2012-03-21 06:54:56|TU:POI:2015-06-02T23:59:26|D        |NA      |null    |P  |0            |null                |0                     |null            |null          |null              |0                   |4326         |184     |X        |N          |null    |null|0       |
|a11a803cd5fb4f19aeff1d5ecb1c7aa0|1        |740000|740000|1100|1.6|public|0000740000:0|2015-06-03 00:00:23|0            |null    |2015-06-02 23:59:26|1      |0           |0           |1            |0            |0               |0               |0               |0            |0            |0           |0           |1            |0            |0               |0               |0               |0            |0            |null          |23  |0                |null |null |null          |0                 |null       |null       |null                |0                 |0                 |0                  |null     |0               |null  |0                |null   |0               |null  |1                 |P          |55         |0              |0                  |null        |null        |null                 |null |0              |0                 |0                  |0              |0               |0               |null  |0              |null |0                   |null      |0                |null   |0                 |null    |0              |null |null     |null     |null     |null              |null      |null    |null                  |null         |null           |null          |null           |null         |null           |null          |null           |null         |null           |null          |null           |n          |200       |null         |2015-06-03 00:00:24|150          |NC |NA |UIDAI:SampleClient|null    |null    |null         |null             |null          |null            |560103 |a2305f71-b0af-4c19-be66-d3bcf7103315|M         |1            |1              |1969          |1/1/1969  |A       |46     |500074     |28-2806-2A57CB5EE625403C1A6E6AE6EF8FADF8|L B nagar         |null             |null            |null            |2806         |Rangareddi   |28            |Andhra Pradesh|2012-05-02 04:41:11|TU:PFA:2015-06-02T23:59:26|D        |NA      |null    |P  |0            |null                |0                     |null            |null          |null              |0                   |4434         |259     |X        |N          |null    |null|0       |
+--------------------------------+---------+------+------+----+---+------+------------+-------------------+-------------+--------+-------------------+-------+------------+------------+-------------+-------------+----------------+----------------+----------------+-------------+-------------+------------+------------+-------------+-------------+----------------+----------------+----------------+-------------+-------------+--------------+----+-----------------+-----+-----+--------------+------------------+-----------+-----------+--------------------+------------------+------------------+-------------------+---------+----------------+------+-----------------+-------+----------------+------+------------------+-----------+-----------+---------------+-------------------+------------+------------+---------------------+-----+---------------+------------------+-------------------+---------------+----------------+----------------+------+---------------+-----+--------------------+----------+-----------------+-------+------------------+--------+---------------+-----+---------+---------+---------+------------------+----------+--------+----------------------+-------------+---------------+--------------+---------------+-------------+---------------+--------------+---------------+-------------+---------------+--------------+---------------+-----------+----------+-------------+-------------------+-------------+---+---+------------------+--------+--------+-------------+-----------------+--------------+----------------+-------+------------------------------------+----------+-------------+---------------+--------------+----------+--------+-------+-----------+----------------------------------------+------------------+-----------------+----------------+----------------+-------------+-------------+--------------+--------------+-------------------+--------------------------+---------+--------+--------+---+-------------+--------------------+----------------------+----------------+--------------+------------------+--------------------+-------------+--------+---------+-----------+--------+----+--------+
only showing top 5 rows

 */
