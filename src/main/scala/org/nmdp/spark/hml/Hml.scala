package org.nmdp.spark.hml

import org.apache.spark.sql.{DataFrame, SQLContext}
import org.apache.spark.sql.functions.{ col, udf }
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

    val multipleGlStringParser= udf{
      glstrings: WrappedArray[String] => glstrings.flatMap(x => x.split(glStringRegexp))
    }
    val singleGlStringParser= udf{
      glstring: String => glstring.split(glStringRegexp)
    }
    //Use regex to find the data type of column typing.allele-assignment.glstring. 
    //If it's an array, use multipleGlStringParser. Otherwise, use singleGlStringParser
    val newDF = df.select("typing.allele-assignment.glstring").dtypes(0)._2 match {
      case typeRegex() => df.withColumn("alleles", multipleGlStringParser(col("typing.allele-assignment.glstring")))
      case _ => df.withColumn("alleles", singleGlStringParser(col("typing.allele-assignment.glstring")))

    }
    val cols = newDF.columns
    val renamedCols = cols.map(x => x.replace("_", ""))
    newDF.toDF(renamedCols: _*)
  }

}
