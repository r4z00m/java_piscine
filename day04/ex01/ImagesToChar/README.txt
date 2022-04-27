Compilation:

find . -name "*.java" > sources.txt
mkdir target
javac --release 8 -d target @sources.txt
rm -rf sources.txt

Copy files:

cp -R src/resources target/

Jar creating:

jar cmf src/manifest.txt target/images-to-chars-printer.jar -C target edu -C src resources

Run:

java -jar target/images-to-chars-printer.jar . o