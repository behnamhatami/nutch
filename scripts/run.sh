bin/nutch generate -topN 5
bin/nutch fetch -all
bin/nutch parse -all
bin/nutch updatedb
bin/nutch solrindex http://localhost:8983/solr/ -all