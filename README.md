# Gallium
## The Java library for reflection-based debugging tools

## What it is

Ever been debugging a Java app that takes ages to compile/load? You have to stop the application, throw some values in the console, start it again. Then you see you're printing too much and the whole app is lagging, and you have to spend a few minutes trying to kill it. At least, that's my experience. Debugging like that gets annoying, because gradle also sometimes decides to just sit there doing nothing for two minutes.

<img src="https://preview.redd.it/0cm6yx27tez21.jpg?width=640&crop=smart&auto=webp&s=612c7aedbfe0ef17ba20120fb7a1defedaa1e7d3" width="320" alt="Coder optimization meme">

So then I spent 12 hours over 4 days working on a solution to this 5-minute annoyance, as coders do. I named it Gallium because it's soft and melts in your hand, but can do cool stuff if you use it right, just like this code (fun fact, it's actually both how and why you're seeing this right now, as gallium is used in the LEDs on your screen). But what does this library actually do?

It's a very simple "coding language" (that's a loose term for this abomination) that can only interface with Java reflection in the host process. You create some sort of terminal to run it, and you can print those values to the console without spending 2 minutes stopping the app, finding the class you need, typing the code, and restarting the app. It also works on code that can't be modified by you, as it uses reflection. It's similar to python, as it doesn't compile to a file, but is parsed at runtime (makes it faster to use and code due to the nature of gallium)

## The syntax

Here's an example script:

```
// reflect the classes and fields needed

vars A set (classes test.A) // get classes
vars B set (classes test.B)
vars A.d set (vars A get) fields d // get fields
vars B.e set (vars B get) fields e

// this code should
// 1. print value of test.A.d (variable type is test.C, but currently null)
// 2. set value of test.A.d to test.B.e.f (instance of test.C)
// 3. print value of test.A.d (value of test.B.e.f)

print (vars A.d get) get // print initial value of test.A.d
(vars A.d get) set ((vars B.e get) get fields f get) // set value of test.A.d to test.B.e.f
print (vars A.d get) get // print value of test.A.d

// 4. print value of test.A.d.i (value = 0)
// 5. set value of test.A.d.i to 42
// 6. print value of test.A.d.i

(vars A.d.i set) (vars A.d get) get fields i // reflect field test.A.d.i

print (vars A.d.i get) get // print initial value of test.A.d.i
(vars A.d.i get) set (primitive 42) // set value of test.A.d.i to 42
print (vars A.d.i get) get // print new value of test.A.d.i (should be 42)
```

As you can see, it's basically english in syntax (except for the comments). This makes it really easy to understand what's going on (but hard on the eyes) _and_ parse, as every token builds on the last. Let's look at a standalone line of code for getting a field:

```
print classes test.A fields d get
```

There are 6 "tokens" here, but it compiles to 2 instructions: print and get field. The print node just asks the get field instruction for the value when the script is run, then prints it. And the get field instruction has a reflection Field instance, and a supplier for the target. All instructions have references to their inputs, and other instructions take their outputs as inputs, so there is never anything at runtime like iterating through maps to find a variable.

## Using it