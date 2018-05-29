package org.nmdp.spark.hml

import org.apache.spark.sql.{DataFrame, SQLContext}
import org.apache.spark.sql.functions.split
import org.apache.spark.sql.functions.col



object Hml {

  def toDataFrame(sqlContext: SQLContext, hmlFile: String): DataFrame = {

    val df = sqlContext.read
      .format("com.databricks.spark.xml")
      .option("rootTag", "hml")
      .option("rowTag", "sample")
      .load(hmlFile)


    val glStringRegexp = "\\^|\\||\\+|\\~|\\/"
    val dfWithAlleles = df
      .withColumn("alleles",
        split(
          col("typing.allele-assignment.glstring"),
          glStringRegexp))

    dfWithAlleles
  }

}
