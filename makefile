all: Main.java
	javac Main.java
	java Main
	display image.ppm
run: all
clean:
	rm *.class
	rm image.ppm
convert:
	convert image.ppm image.png