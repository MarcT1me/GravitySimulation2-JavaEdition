> # GravitySimulation2 - Java edition

###### powered by LibGDX

![GS2ico128](https://github.com/user-attachments/assets/9c741864-5d29-44ac-8315-78cfb12ecb0e)

### Description

This is a small game written in java using the popular LibGDX library.
The game is a simulation of the movement of various objects in space. All calculations take place in a separate stream
and according to the real formulas of classical mechanics.
The game has an interface with bitmap texts, which adds atmosphere. There is a background of stars (screenshot from the
game Elite Dangerous, made by my friend)

### How the code works

The lwjgl3 project initializes and launches the game. This project was edited by me to load and
apply the window configurations correctly.
GravitySimulation2 is a game class (there is a sin - the use of static methods and fields)
the main task of the class is to initialize the interface and load configurations

Packages:

- config - configuration factory and interface for defining them
- gameinterface - implementation of basic menu classes and objects for rendering on the interface and menu
  implementations
- objects - the scene and the objects of the scene. The objects of the scene have ObjectType implemented to separate the
  logic (zombie code), themselves
  ObjectType should implement different types of objects (stars, planets), but so far it has not been implemented. In
  the same
  The module implements physics, there is a simulation running in a separate thread.
- save - downloads saves from files, first the "header" save is read.json and when you click the upload button , the
  rest of the files are uploaded
- screen - game screens, they are separate for the stage and the main menu (in the future I plan to add the main menu to
  the background
  short simulations)

Sequence:

1. Configuration initializations
2. Initialize the menu (save configuration data is loaded inside)
3. Initialization screen
4. When you click on the menu buttons, some menu objects are hidden and the second ones are shown
5. When pressing the load button (in the main menu - load) or main manu (in pause), the screens change
6. When you press load, saves are read and created:
    1. Scene
    2. Simulation and simulation space
    3. Scene objects from the file (loaded into the simulation space and into the scene at the same time)
    4. Camera
    5. Camera controller
    6. After the download is finished, the game switches to the scene screen and the simulation starts.
7. When the main manu button is pressed, the scene is cleaned and removed from the screen.

### Settings

The game has settings by category

- Game - settings of various game components that are not included in other categories

1. interfacesize - changes the sizes of different interface elements
2. Buttons for enabling and disabling the FPS display of the TPS simulation window and the game version
3. Various sliders for camera and mouse sensitivity settings

- Window - window settings

1. Switch the fullscreen
2. Select the monitor. It changes dynamically, if you have more than one monitor, then when the full screen is turned
   on, you can capture the game behind a specific monitor.
3. Window dimensions. It only works if fullscreen mode is turned off.
4. Settings related to the number of frames,

- VSync - includes the technology of the same name
    - target FPS - allows you to select a specific FPS. It is automatically limited to the frequency of the monitor when
      VSync is enabled, in other cases it has a range from 1 to 300 FPS. FPS can be delimited by putting the slider in
      the leftmost position.
- Graphic - advanced graphics settings, works only in simulation

1. Setting up the display of the speed of objects

- show V vectors - V vector is a red line that visualizes the speed of an object and the direction in which it will move
  with the current data.
    - V vector scale - V vector can be scaled by changing its scale from 0.5 to 10 of its original length
    - show V modules - switches the display of the value of the velocity vector of the object

2. Switching the display of the object's position values
3. The direction line is a line pointing to an object, it starts a little further from the center of the screen and goes
   to the object. This line gives a 3D effect because when it is turned on, it seems that the object is standing on the
   counter.
    - display switching setting

- line transparency

4. trajectory - the trajectory of the object. This is a line that displays the object's past positions. The lighter part
   is responsible for the new positions, the more transparent for the old ones.
    - switching visibility
    - len - the number of points to be remembered. Attention! The higher the value, the more RAM will be used. It is
      better to reduce the values in advance if too many objects are involved in your simulation.
    - interval - is responsible for the time intervals between which points will be created. The higher the value, the
      more noticeable the interval will be.

### Saves

The game implements the saving of scenes for simulation in halyards.

- save - the file is the header for saving. The save name and time data are duplicated here (they are not implemented in
  the saves provided right after downloading)
- camera - a file for storing data about the camera, (zoom PosX posY)
- simulation - data about the simulation, its speed, maximum allowed speed (in powers of two) and TPS of the simulation
- scene is a file with enumerations of data about scene objects
  The game has already implemented several scenarios:
- Centauri - Alpha Centauri and Proxima Centauri system
- duet - two massive stars close to each other
- magic - two objects with the same difference, one with a positive mass and the other with a negative mass
- simple - a small system consisting of a star and 2 objects with a mass of 10 kilograms
- sol - the solar system (the earth has a moon)
- test - a small unstable save of 3 large-mass objects

### Node

The game still has not implemented error detection and information output (logging)
When loading a scene, the game forcibly extends the loading time by 1 second. For a release, it doesn't carry any
semantic loads, just decoration (one second is not that long)
The game still does not have a save editor implemented.
There is no sound implemented in the game

### Futures

- error detection and logging
- save editor
- textured objects
- adding lighting and improving graphics
- realization of tidal forces
- more types of objects (asteroids, comets)
- It is possible to add rockets and simulate flight between planets. At the same time, improved physics for working with
  small objects (asteroids, rockets, comets)

### Possible errors

- eternal download - check the save files, they should not contain numbers without a fractional part (example of an
  incorrect number 0, corrected version 0.0)
- the window does not respond - check the trajectory settings, or better yet, reset Graphics and configure again

### Fixed errors that may return

- canceling the download pause and enabling it after a second (due to a one-second delay when loading the save)
- after the game is closed, the program continues to work - there is a problem with stopping the simulation flow. Use
  the interface and don't make any "sudden movements"

# Lib GDX lift-off generated

This project was generated with a template including simple application launchers and a main class extending `Game` that
sets the first screen.

## Platforms

- `core`: Main module with the application logic shared by all platforms.
- `lwjgl3`: Primary desktop platform using LWJGL3; was called 'desktop' in older docs.

## Gradle

This project uses [Gradle](https://gradle.org/) to manage dependencies.
The Gradle wrapper was included, so you can run Gradle tasks using `gradlew.bat` or `./gradlew` commands.
Useful Gradle tasks and flags:

- `--continue`: when using this flag, errors will not stop the tasks from running.
- `--daemon`: thanks to this flag, Gradle daemon will be used to run chosen tasks.
- `--offline`: when using this flag, cached dependency archives will be used.
- `--refresh-dependencies`: this flag forces validation of all dependencies. Useful for snapshot versions.
- `build`: builds sources and archives of every project.
- `cleanEclipse`: removes Eclipse project data.
- `cleanIdea`: removes IntelliJ project data.
- `clean`: removes `build` folders, which store compiled classes and built archives.
- `eclipse`: generates Eclipse project data.
- `idea`: generates IntelliJ project data.
- `lwjgl3:jar`: builds application's runnable jar, which can be found at `lwjgl3/build/libs`.
- `lwjgl3:run`: starts the application.
- `test`: runs unit tests (if any).

Note that most tasks that are not specific to a single project can be run with `name:` prefix, where the `name` should
be replaced with the ID of a specific project.
For example, `core:clean` removes `build` folder only from the `core` project.
