messing-with-java
=================

I took an alorithms class on coursera.org (https://www.coursera.org/course/algs4partI) for an excuse to play around with Java as class assignments were to be done using Java.

I added some code that I wrote for the course into this project.  The code is not 'production' ready code, it lacks comments, but it was done purely as a personal exercise to try different things out in Java.

There are 3 applications:

1. percolation - the classic percolation problem - determine number of spaces that must be 'opened' on an N by N grid so that the top row of the grid is connected to the bottom grid.  As another self determined fun exercise, I tried working this out as an AngularJS application just to mess around with the UI aspect of it.  The code lives in https://github.com/mjb14/percolation/tree/master/app and a demo of that can be viewed at: http://www.risingthree.com/percolation/app/index.html#/wquf

2. points - the goal was to read a file of points and determine how many unique lines could be drawn that connected 4 or more points.  The lines were to be drawn.  Two different techniques had to be used, a brute force approach (which couldn't handle a massive file of points) and a more optimized approach that could handle a very large number of points.

3. puzzle - given a N by N grid with all spaces occupied by the numbers (N*N -1, with a single empty space), determine if it was possible to arrange the numbers in order when only 1 number could be moved at a time to the empty space (freeing up a new empty space for other numbers to roate into).  The application had to determine the fewest number of moves possible to achieve the goal and print out the solution.

In addition to the 3 I did to mess around with Java, I did the zappos challenge application (download images from rest api and save them locally.  This was the first Java code that I had written in essentially 10 years.  I put the code for that with instructions on running it at: https://bitbucket.org/brennan3/zappos-challenge-2/src/f7a2cb6994e8?at=master

I used a few different techniques with that application: 
- Stored api key in a properties file.  Encrypted the value.
- Used Maven to help with dependencies
- Used Cobertura to generate coverage reports
- Used SLF4J with log4j for logging