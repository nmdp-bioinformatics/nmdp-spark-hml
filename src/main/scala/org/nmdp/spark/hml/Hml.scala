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

    val typeRegex = "ArrayType.*".r

    val hlaGlaStringParser= udf{
      glstrings: WrappedArray[String] => glstrings.flatMap(x => x.split(glStringRegexp))
    }

    val kirGlaStringParser= udf{
      glstring: String => glstring.split(glStringRegexp)
    }

    df.select("typing.allele-assignment.glstring").dtypes(0)._2 match {
      case typeRegex() => df.withColumn("alleles", hlaGlaStringParser(col("typing.allele-assignment.glstring")))
      case _ => df.withColumn("alleles", kirGlaStringParser(col("typing.allele-assignment.glstring")))

    }
  }

}
