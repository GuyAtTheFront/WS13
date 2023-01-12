
# My solution for Workshop 13

Things to note:


## `Ws13Application.java` file

* `args` has been overwritten in this file. Your command line arguments will not work. To use your own args, comment out the relevant lines in the file

* `args` passed to Spring Application has been transformed to the format: `--dataDir=/opt/tmp/data`. Spring automatically sets `args` passed this way into `properties`, which you can access in other parts of the application. Workshop Task 1 passes in the Command Line Arguments with a white-space instead, and cannot directly make use of Spring's magik. 


## `application.properties` file

* A single entry `myapp.rootDir=src` is added to this file

* This property controls the root of the path passed in `args` i.e. `--dataDir /opt/tmp/data` would save created files in `../src/opt/tmp/data`

* I prefer this behavior over creating a directory at system root, easier to see from project window. Also allows for both windows/linux to start the app with the same CLI command (Model answer provided has two ways to start the application)

* To change where files are saved, update this property.


## `test.java` file

* This file is not used by the application, and consequently does not affect the application.

* I used it to test methods in `Contacts.java` during development, and left it there to show how you can test java codes without starting Spring App. 


## Validations on D.O.B form field

* Firstly, I've declared `dateOfBirth` as `String` instead of `DateLocal`

* Because of above, I had to manaually validate that the user-input can be cast to `DateLocal`. Model answer handles this by using a date-picker on the form. 

* I've also decided to validate `dateOfBirth` using custom validation method shown in slides (day13, slides 13-14). Model answer uses a smart "hack" that I believe is a better solution for this workshop. 

* Lastly, my implementation refactors the code on slide 14, by adding custom-errors to `bindingObject` **before** checking `bindingObject` for errors. This means that hibernate-errors and custom-errors are checked and displayed at the same time. If you follow Slide 14's implementation, then D.O.B validation will only kick-in **if** the other hibernate-validation passes. 


## Final Words

Workshop 13 is a challenging workshop, a huge leap in difficulty from Workshop 12. Completing this workshop from scratch would give you a strong repository to copy-paste-edit codes for your final  assessment. 

IMO, this workshop is too complex for "read-and-understand". You may find it difficult to determine where to find or how to edit codes snippets if you do not attempt this for yourself. 

Alas, here's my solution for your enjoyment

\- Guy at the front
