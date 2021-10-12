#Makefile

install: 
	./gradlew clean install

run-dist: 
	./build/install/app//bin/app

check-updates: 
	./gradlew dependencyUpdates
list:
	@grep '^[^#[:space:]].*:' Makefile
lint:
	./gradlew checkstyleMain

 .PHONY: build

build:
	./gradlew clean build

