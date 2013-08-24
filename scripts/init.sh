mkdir -p urls
echo 'http://www.estekhtam.com/' > urls/seed.txt
bin/nutch inject urls
