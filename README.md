# nmdp-spark-hml
Apache Spark support for HML

# Build the package
```
sbt clean package
```

# Import HML as a DataFrame
Convert an HML File to a ParquetFile
```
spark-submit \
	--packages com.databricks:spark-xml_2.11:0.4.1 \
	--class org.nmdp.spark.hml.HmlImport \
	--master local[*] \
	target/scala-2.11/nmdp-spark-hml_2.11-0.0.1.jar \
	KIR_1.hml \
	KIR_1.parquet
```



# DataFrame schema

```
root
 |-- _center-code: long (nullable = true)
 |-- _id: long (nullable = true)
 |-- collection-method: string (nullable = true)
 |-- typing: struct (nullable = true)
 |    |-- _date: string (nullable = true)
 |    |-- _gene-family: string (nullable = true)
 |    |-- allele-assignment: struct (nullable = true)
 |    |    |-- _allele-db: string (nullable = true)
 |    |    |-- _allele-version: string (nullable = true)
 |    |    |-- _date: string (nullable = true)
 |    |    |-- glstring: array (nullable = true)
 |    |    |    |-- element: struct (containsNull = true)
 |    |    |    |    |-- _VALUE: string (nullable = true)
 |    |    |    |    |-- _uri: string (nullable = true)
 |    |-- consensus-sequence: struct (nullable = true)
 |    |    |-- _date: string (nullable = true)
 |    |    |-- consensus-sequence-block: array (nullable = true)
 |    |    |    |-- element: struct (containsNull = true)
 |    |    |    |    |-- _continuity: boolean (nullable = true)
 |    |    |    |    |-- _description: string (nullable = true)
 |    |    |    |    |-- _end: long (nullable = true)
 |    |    |    |    |-- _expected-copy-number: long (nullable = true)
 |    |    |    |    |-- _phasing-group: long (nullable = true)
 |    |    |    |    |-- _reference-sequence-id: string (nullable = true)
 |    |    |    |    |-- _start: long (nullable = true)
 |    |    |    |    |-- _strand: long (nullable = true)
 |    |    |    |    |-- sequence: string (nullable = true)
 |    |    |    |    |-- sequence-quality: array (nullable = true)
 |    |    |    |    |    |-- element: struct (containsNull = true)
 |    |    |    |    |    |    |-- _VALUE: string (nullable = true)
 |    |    |    |    |    |    |-- _quality-score: double (nullable = true)
 |    |    |    |    |    |    |-- _sequence-end: long (nullable = true)
 |    |    |    |    |    |    |-- _sequence-start: long (nullable = true)
 |    |    |    |    |-- variant: array (nullable = true)
 |    |    |    |    |    |-- element: struct (containsNull = true)
 |    |    |    |    |    |    |-- _alternate-bases: string (nullable = true)
 |    |    |    |    |    |    |-- _end: long (nullable = true)
 |    |    |    |    |    |    |-- _reference-bases: string (nullable = true)
 |    |    |    |    |    |    |-- _start: long (nullable = true)
 |    |    |    |    |    |    |-- variant-effect: struct (nullable = true)
 |    |    |    |    |    |    |    |-- _VALUE: string (nullable = true)
 |    |    |    |    |    |    |    |-- _term: string (nullable = true)
 |    |    |-- reference-database: struct (nullable = true)
 |    |    |    |-- _availability: string (nullable = true)
 |    |    |    |-- _curated: boolean (nullable = true)
 |    |    |    |-- _description: string (nullable = true)
 |    |    |    |-- _name: string (nullable = true)
 |    |    |    |-- _uri: string (nullable = true)
 |    |    |    |-- _version: string (nullable = true)
 |    |    |    |-- reference-sequence: struct (nullable = true)
 |    |    |    |    |-- _VALUE: string (nullable = true)
 |    |    |    |    |-- _accession: string (nullable = true)
 |    |    |    |    |-- _end: long (nullable = true)
 |    |    |    |    |-- _id: string (nullable = true)
 
 |    |    |    |    |-- _name: string (nullable = true)
 |    |    |    |    |-- _start: long (nullable = true)
 |    |    |    |    |-- _uri: string (nullable = true)
 |    |-- typing-method: struct (nullable = true)
 |    |    |-- sbt-ngs: struct (nullable = true)
 |    |    |    |-- _VALUE: string (nullable = true)
 |    |    |    |-- _locus: string (nullable = true)
 |    |    |    |-- _test-id: string (nullable = true)
 |    |    |    |-- _test-id-source: string (nullable = true)
 |-- alleles: array (nullable = true)
 |    |-- element: string (containsNull = true)
```
