mkdir -p urls
echo 'http://nutch.apache.org/' > urls/seed.txt
 bin/nutch inject urls
