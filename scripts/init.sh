mkdir -p urls
echo 'http://www.e-estekhdam.com/' > urls/seed.txt
bin/nutch inject urls
