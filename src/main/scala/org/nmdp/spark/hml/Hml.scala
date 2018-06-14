package org.nmdp.spark.hml

import org.apache.spark.sql.{DataFrame, SQLContext}
import org.apache.spark.sql.functions.{ split, col, udf }
import scala.collection.mutable.WrappedArray


object Hml {

  def toDataFrame(sqlContext: SQLContext, hmlFile: String): DataFrame = {

    val df = sqlContext.read
      .format("com.databricks.spark.xml")
      .option("rootTag", "hml")
      .option("rowTag", "sample")
      .load(hmlFile)


    val glStringRegexp = "\\^|\\||\\+|\\~|\\/"

    val glaStringParser= udf{
      glstrings: WrappedArray[String] => glstrings.flatMap(x => x.split(glStringRegexp))
    }

    val dfWithAlleles = df
      .withColumn("alleles", glaParser($"typing.allele-assignment.glstring"))

    dfWithAlleles
  }

}
