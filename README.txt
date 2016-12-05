Context extraction and search using Apache Tika

1. Phone number and fax number should be of 10 digits
2. Dates should follow their format
3. PostData <= firstSeenDate
4. firstSeenDate <= lastSeenDate
5. lattitude and longitude are floatig point numbers

Final repo
https://github.com/Monil200/csci572HW1


Command to convert xhtml to json in TIKA
java -jar tika-app-1.6.jar -j computrabajo-ar-20121106.tsv_0.xhtml > tempXHTMLtoJSON.json
