# 1 Application for Bus Traffic Simulation
Design and implement an application for viewing public transport lines and tracking their movement. Developed and tested Java version 1.8.0. Final project evaluation 80/80.

# 2 Assignment
- General requirements
    - the application displays the map base, to which you then transfer the link information
        - the basic map base is formed only by lines (lines / broken lines between crossing points)
        - each line represents one street (may have a name)
        - you can extend this basic concept in any way
        - the map base is calculated after running the file (the format is at your discretion)
        - it is possible to zoom in and out of the map base (enlarge)
    - public transport system
        - is divided into lines (eg bus line no. 41)
        - each line is defined by a list of stops (final - continuous - final)
        - the stop always lies on some of the streets
        - each line contains individual connections (the connection is one complete way from one final stop to the other final stop)
        - the line has a timetable that contains information about individual connections
        - lines and their timetables are loaded from a file (the format is at your discretion, you can use one of the available formats, eg GTFS)
- Movement of vehicles (connections)
    - the system contains its own clock, which can be set to the initial value and different speed
    - after loading the map and the line, the system will start displaying the individual connections that are currently on the way (the way of displaying is at                 your invention, a marker, a wheel, ... will suffice)
    - the connection symbol is shifted sequentially according to the current time and timetable (display updates can be, for example, every N seconds); the movement  of the connection on the route is therefore simulated
        - the current position on the map can be calculated according to the length of the route between stops, the timetable and the internal clock applications; in this mode, therefore, the connections are delayed
    - after moving / clicking on a connection symbol, the route on the map is highlighted and the connection itinerary is displayed (for example.
- Interactive interventions
    - possibility to define difficult traffic situations (levels of operation)
        - the level of traffic applies evenly across the street
        - the higher the degree, the slower the passage
        - due to higher levels of operation is needed to delay connections
    - the possibility of closing the street and defining the destination route
        - the detour route is made manually (eg by gradually clicking on the road)
        - we set a fixed value of the delay for the entire destination route
        - the detour route may omit one of the stops

# 3 Compilation and launch of the application
In root directory type ```ant compile```
this should compile the project, after that type ```ant run```
