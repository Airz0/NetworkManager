# NetworkManager
For our university capstone, our group decides to create a simple network manager which broadcasts up to 10,000 OSC messages through UDP to a network of Raspberry Pi's and have the devices send those messages back to the broadcaster in order to confirm they've received the messages. 

This was created in order to prove its scalability with projects that may include thousands of connected devices which may be together displaying some form of media multiplicity.
By having the devices send the OSC messages back, you may be able to dynamically correct the media displayed by identifying which device is displaying something incorrect.

This code only works in conjunction with an Intellij plugin called HappyBrackets (developer kit & RPI image can be found on their website). Unfortunately, this only works on Intellij 2018.3.6  combined with jdk8.
