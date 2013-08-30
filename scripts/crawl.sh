mkdir -p urls
echo 'http://www.estekhtam.com/' > urls/seed.txt
./bin/crawl ./urls $1 http://127.0.0.1:8983/solr/ $2