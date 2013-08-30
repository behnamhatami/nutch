cd $1
ant clean
ant runtime
$1/scripts/cp.sh $1
