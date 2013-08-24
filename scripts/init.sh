mkdir -p urls
echo 'http://narenji.ir/' > urls/seed.txt
bin/nutch inject urls
