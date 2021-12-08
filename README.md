<div align="center">
    <h1>DeltaEngine</h1>
</div>

## Description

DeltaEngine is a minimalist game engine, developed in Java, 
allowing to easily create a new game, without all constraints that could occur.
Using JavaFX to work, this Engine provides graphics, physics, sound, network and sound features.
This Engine is born because it is the result of a university project, for Aix-Marseille University, France.

DeltaEngine is subdivided into multiple engines to work properly :

### The Kernel Engine
Core of the engine, like a manager, it coordinates, cadences and uses the potential of all the other engines, 
in order to produce the game that should be rendered.
This engine is the only way of communication between the game and the sub-engines.
Other than the Kernel Engine, it is impossible for the game to directly talk the other sub-engines: the Kernel is like an access door,
which is role is to distribute the workload where it is necessary,
and to coordinate on its own other engines, under the orders given to it.
This engine is also the only one to which it is possible to pass game maps, 
in order to subsequently load them. 
Note: the cards must have unique names, otherwise the engine will refuse to save them.

### The Input Engine
It acts as a link between the user and the game. 
It captures all the presses and releases of the user's keys, 
but only allows actions to be activated if they have been assigned to them. 
Containing a dictionary, the engine makes it possible to make the link between a key and an event.
For example, the developer can assign the upward movement to the W key on the keyboard.
It is also possible to assign the same action for two keys at the same time.
Please note, however, that it is impossible to assign two simultaneous actions to a single key: 
it will be necessary to review the code created, 
so that it takes all the necessary actions when pressing the desired key.

### The Event Engine
This engine can contain a series of events, which will all be checked one by one in turn. 
We write the event code, the conditions, the actions, ..., then we pass our created event directly to a predefined map, 
or directly to the kernel engine, which will take care of giving it to the event engine. 
It is also possible to delete an event, by passing the latter to it.

### AI Engine
Engine governing the game's AI, this engine is similar to the event engine: 
the desired behavior must be coded in an appropriate method, 
which will be passed to the engine. 
The difference between the event engine is that the written AI code must first be linked to an Entity in order to be taken into account. 
In addition, the class used to contain the AI code makes it possible to give the Entity to which it is attached, 
allowing modifications to the latter.

### Physics Engine
This engine is used to manage the movements of entities in the game, 
as well as events resulting from collisions between entities. 
Regarding the movements, the engine will calculate the next position of each of the entities, 
which is defined by floating coordinates as well as a type of movement (up, down, right, left). 
Then, it assigns this new position to the entity in the case where the position is valid, 
that is to say that the entity is not going to position itself in a cell on which it cannot go. 
And, in regard to collision detection, the engine will take each pair of entities in order to know 
how to calculate whether they are collisions. 
Then, it will execute the event corresponding to the collision of these 2 entities.

### Sound Engine
Sound Engine allowing to play sounds, it is also possible to change these properties for each song :
- Volume
- Balance
- Speed <br>
Warning: Sounds required need to be unique!

### Network Engine
This makes it possible to use the power of the network (local or Internet), 
in order to create games with several users, or a use requiring a connection. 
Working with the TCP / IP protocol, this very rudimentary engine can send and receive any Java Objects. 
It also contains a buffer in its operation, which is used when the engine receives information, 
if the game has not yet retrieved the received content.
However, it is not possible to initialize the engine as a Server and a Client at the same time.

### Graphics Engine
This engine manages the visual rendering of the game in two dimensions. 
It updates the items displayed on the screen according to the properties of the items,
which can be modified by the game, or the graphics engine. 
This engine can display shapes, text, or images (jpg, png, gif…).
It has the particularity of adapting to the display: 
in fact, the map containing the elements takes up the entire screen and these elements are resized accordingly.

In addition, this engine benefits from some optimization such as the use of a “BufferedHashMap” 
which keeps in memory the recent addition and deletion on the map to avoid checking 
for each element of the map that it is still present 
(we save this way a lot of unnecessary computing time)

#### To properly understand the full potential and how to use this project, a Javadoc is available in the project

## Authors
- [DAVID--BROGLIO Tom](https://github.com/Stocy)
- [Valorator](https://github.com/VidalGuillaume)
- [Grosflan](https://github.com/Grosflan)
- [Airels](https://github.com/Airels)

## Supervisor
- HAMRI Maamar El Amine

## Dependencies
- JavaFX
- Gradle
- JUnit (for tests)

## Examples of implementation of this engine
- [Puck-Man Reloaded](https://github.com/Airels/puck-man-reloaded) : A revisited PacMan game, created for the university project.