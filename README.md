# Rock Paper Scissors game
## Some general implementation notes
- The project is on SBT, ZIO library is used.
- I conciously left no comments, because the code looks quite clear and self-documenting. ZIO really helped me with that. 
- The unit-tests are implemented with `zio-test` library. It's a purely functional Scala testing framework. It allows you
to mock the effects easily, so you always test your code only.
Tests could be run as usual with `sbt test` command.
- It should be very easy to extend the game, just have a look at `HandSign` implementation and add new signs if needed.
- The GUI is apparently CLI.
## How to run the game
The program could be executed only in interactive mode. By this I mean that it has a menu, but you can't pass any
program arguments to it. In order to start just execute `sbt run`.