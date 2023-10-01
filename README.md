# Mars-Rover-App

Case Study 2: Mars Rover App

This app simulates the initialization of rovers with (x,y) coordinates and N,S,E,W direction and allows user to move them based on the 4 available commands:  
f – Move forward 1 coordinate in the current direction  
b – Move backward 1 coordinate in the current direction  
r – Rotate 90 degree clock-wise from current direction  
l – Rotate 90 degree anti clock-wise from current direction  

The rovers will move accordingly, and will check if there are any collisions or if it is going out of bounds.  

**Assumptions/Settings (Can be changed accordingly)**:
- Due to how my terminal/console GUI is set-up (a 10x10 grid), I will limit the roverId to single digit (0-9) only
- x and y will be limited from (0,0) to (10,10), rovers cannot exceed the set 10x10 grid boundary
- Rovers cannot collide with each other. It will stop at the position before collision and all further commands will be cancelled
- I started off with RestController APIs with initializing and movement of a single rover, before exploring multiple rovers. So I left the REST api in the document, but it is not required. Of course, once the program has started (with localhost:8080), it is possible to output the result of a single rover e.g. typing http://localhost:8080/mars-rover/1,0,0,N/rff in the browser or GET request using Postman/Insomnia/etc. You should be able to see the final coordinates of (2,0) and Direction of East.
- **Note**: I have explored many options but I am unable to get my app to work properly in a .JAR file as of now. The while loop that keeps the commands open only execute once no matter what. As such, please run this app in IDE or terminal accordingly without .JAR file.
  - In IDE, just run and MarsRoverApp with the main method should initialize
  - In terminal, go to the root directory of where you cloned/downloaded this app, and type mvn spring-boot:run

When the app has started, you should see an empty 10x10 grid along with *Enter your command (or type 'exit' to quit the loop):*  
Note: entering exit does exit the while loop, but the spring boot app will continue running. So to exit the program, please Ctrl+C or stop/close the program in your IDE.

Here are the list of commands:
- Initialize a single rover: "init <roverId> <x> <y> <direction>" *e.g. init 1 1 1 N --> [roverId 1, coordinates (1,1), direction N] initialized*
- Initialize multiple rovers: "batch-init <rover1_params> <rover2_params>" *e.g. batch-init 1,1,1,N 2,2,2,S --> [roverId 1, coordinates (1,1), direction N] and [roverId 2, coordinates (2,2), direction S] initialized*
- Move a single rover: "move <roverId> <commands>" *e.g. move 1 rfff --> move roverId 1 with the following commands: rotate 90 degree clock-wise from current direction, move forward 1 coordinate three times*
- Show status of a single rover: "status <roverId>" *e.g. status 1 --> shows the coordinates and direction of roverId 1*

Please refer to the image example above if required to have a better understanding.

This concludes my explanation, do let me know if anything is unclear. Thank you and feel free to leave any suggestion!  
-Yeong Zhun
