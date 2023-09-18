# Clean build files
./gradlew clean
rm -rf ./dist

# Run application build
./gradlew build

#
WORKDIR=${PWD}
cd app/build/distributions
unzip app-shadow.zip
mkdir -p ${WORKDIR}/dist
mv app-shadow/* ${WORKDIR}/dist
