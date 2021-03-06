# ICS4U0 Final Project - Don't Get Hurt, Stay at Work

This is our submission for our final project in the ICS4U0 course, where we had to create a game with the topic of adversity in Java. Our game covers safety in the workplace, presented as a turn-based RPG inspired by Undertale. It is coded with JavaFX, so the project should be easy to setup within any IDE running Java 8.

## Setting up source code

##### DrJava

1. Transfer the `resources` folder's contents into `src` (ex. `Logo.png` would be directly in `src`)
2. Open `DrJavaProject.drjava` (found in the root directory)
3. Ensure you have your compiler set to JDK 8. We developed our project under `JDK 8.0_161` specifically. You can download it from [Oracle's site](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
4. Open `Main.java`, `Compile` the project and `Run` it.

##### IntelliJ

1. First, clone this repository (`File > New > Project from Version Control > Git` and paste `https://github.com/0ffz/ICS4U0_Final.git`).
2. We strongly recommend using IntelliJ, as the project should be almost ready to work with, using it. Simply open the project's root directory in IntelliJ and the project should load.
3. Next, go to `File > Project Sructure` (shortcut `Ctrl+Alt+Shift+S`), go to the `Project` tab and set your JDK to `Java 8`. You may need to create the JDK by pressing the `New` button and linking to where it is installed. On Windows, this is `C:\Program Files\Java\jdk1.8.0_<version>` by default.
4. Run the `Main.java` file under `src` by opening it, and pressing `Ctrl+Shift+F10`

## Version control

This project is [hosted on GitHub](https://github.com/0ffz/ICS4U0_Final) but also contains version numbers in the top comments of each class.

##### Version numbers

They are split into three sections `x.y.z`

* `x` describes the overall state of the class. `1` signifies it is viable for the final product, `0` signifies it is unfinished.
* `y` describes the date of the update.
    * `0`: May 21, 2019
    * `1`: May 27, 2019
    * `2`: June 3, 2019
    * `3`: June 10, 2019 - The final due date of
* `z` describes the number of version the file has gone through, starting from 1.

##### Authors

If there is no author mentioned next to an edit, it has been done by the first listed author in the `@author` tag.

## References

The basis of the GameRunner class originated from a [Money Bag Collecting demo](https://github.com/tutsplus/Introduction-to-JavaFX-for-Game-Development). The main aspects of code remaining from it are the input listener's ArrayList of inputs, the AnimationTimer, and the basis for the Entity class (ex. position, image and boundaries).

The system for resizing the game window was taken from [here](https://gist.github.com/jewelsea/5603471) and modified to work properly, as it was originally broken.

The delay and repeating tasks used in battles were learned from [here](http://mrbool.com/how-to-schedule-recurring-tasks-in-java-applications/28909)

Fixing concurrency issues with ArrayLists was learned from [here](https://stackoverflow.com/questions/6916385/is-there-a-concurrent-list-in-javas-jdk)

All resources and any other code were created by Daniel Voznyy or Enfei Zhang