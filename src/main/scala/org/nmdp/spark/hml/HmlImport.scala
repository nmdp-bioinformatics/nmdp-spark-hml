package org.nmdp.spark.hml

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{DataFrame, SQLContext}


object HmlImport extends App {
  val (hmlInputFile, hmlOutputParquet) = (args(0), args(1))

  private val conf = new SparkConf().setAppName("HML Import")
  private val sqlContext = new SQLContext(new SparkContext(conf))

  private val dfWithAlleles: DataFrame = Hml.toDataFrame(sqlContext, hmlInputFile)

  // Show the alleles
  dfWithAlleles.select("alleles").show(false)

  dfWithAlleles.write.parquet(hmlOutputParquet)

}
