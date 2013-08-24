#!/bin/bash
# Example script only
# Guaranteed to be fit for no purpose

#Must be in directory with bin/nutch in it or NUTCH_HOME must be set appropriately in the line below.
export NUTCH_HOME=`pwd`

# number of passes to make
n=1
# number of selected urls for fetching
maxUrls=10
# solr server
solrUrl=http://localhost:8983/solr/

echo "starting mycrawl" >> $NUTCH_HOME/logs/hadoop.log                                                                                                                                                           

# Inject
cd $NUTCH_HOME
$NUTCH_HOME/bin/nutch inject -D db.update.additions.allowed=true urls

for (( i = 1 ; i <= $n ; i++ ))
do

batchId=`date +%s`-$RANDOM
echo "Generating batchId: "$batchId
log=$NUTCH_HOME/logs/log$batchId     

echo "log: "$log

echo "Generate "$maxUrls" urls."
# Generate
$NUTCH_HOME/bin/nutch generate -D db.update.additions.allowed=true -topN $maxUrls -batchId $batchId >> $log

echo "Fetching."
# Fetch
$NUTCH_HOME/bin/nutch fetch -D db.update.additions.allowed=true $batchId >> $log

echo "Parsing."
# Parse
$NUTCH_HOME/bin/nutch parse -D db.update.additions.allowed=true $batchId >> $log

echo "Updating."
# Update
$NUTCH_HOME/bin/nutch updatedb -D db.update.additions.allowed=true >> $log

echo "Indexing"
# Index
# $NUTCH_HOME/bin/nutch solrindex $solrUrl $batchId >> $log

done

