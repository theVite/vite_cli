# Purpose
The purpose of this document is to describe the coding standards that we are using for the Java coding in this project.
> # Note
> This document is a work in progress, and subject to discussion and change.

# Naming Conventions
Naming conventions are vital to promote a consistency in a codebase. Uniformity in naming reduces the mental drag and
 confusion of reading foreign code, making it easier to debug, code review, and repair.

## Interfaces Are Nouns
Interfaces are nouns and we want to code to the interface itself. Thus, we do not want to have a prefix such as
 `I` before every interface. Instead we want the interface to be the name of the thing we are going to talk to.

For instance, instead of calling something `IWidget` we would just call it `Widget`. In general, we want to be writing
 to interfaces anyway, and in the few cases where we need to know the difference the IDE should be able to notify
 us of it.

## Implementations are called out in name
Because implementations should be the uncommon reference (outside of factories and wiring systems) we want the specifics
 to be defined in the implementation itself. To do this, we will use an `Impl` postfix as a way to point out an
 implementation of a specific interface.

If there is a default implementation, for example a web service implementation, that one can be called
 `DefaultWidgetImpl`. Note that this can be used for other systems too - such as `EmptyWidgetImpl` for a unit test mock,
 or `LocalWidgetImpl` for a localhost version. This allows for obvious differentiation between the implementations.
 
## Unit Tests
Unit tests should generally be included under the `src/test/java` folder in the project.  The tests should mimic the
 name of the file they are testing. For example, if we are writing a test for `Widget.java` then our test would be named 
 `WidgetTest.java` and be in the same package as the original test file.  For instance, if `Widget.java` is in the
 package `com.vite.widget` then the test file would be in the package `com.vite.widget` as well, under the
 `src/test/java` folder.

The location of the unit tests is based on the Maven standard.  The standard includes locations for resources, as well
 as allowing for extension of other files that might be needed (such as files for a generation step).

## Camel Case
All classes and methods are [Camel Cased](https://en.wikipedia.org/wiki/CamelCase), i.e. `TheWidget`.  Classes use Upper
 Camel Case, while methods use Lower Camel Case.

## Static Constants
Static constants should use Uppercase [Snake Case](https://en.wikipedia.org/wiki/Snake_case). 

## Class and Interface Names
Class and Interface names should be capitalized, as in `Widget.java` not `widget.java`.

## Package Names
Packages should be single-word names wherever possible and should be all lower case. All work done for internal
 Vite projects should be done under subpackages of the `com.vite` package.  

# Documentation
As quoted by Damian Conway, "Documentation is a love letter that you write to your future self."[1](#damianConway) In order to keep
 Java projects maintainable, maintaining certain standards are required to ensure that code bases don't become
 unmanageable. 

## Javadocs
All functions and classes that will be run or deployed into a production system must be documented with Javadocs. This
 means that any class that will be deployed should be documented with a Javadoc.
 
For instance, test classes don't necessarily *need* documentation, even though it is highly recommended that they have
 some documentation.  However, any classes in the `src/main/java` folders should be fully documented to the best of
 our ability.

### Interfaces
Interfaces should have Javadoc documentation describing what they are intended for.  The methods on an interface should
 describe what is expected for input, as well as output, and what the interface is used for. Links to related classes
 should exist, although this can be determined on a case-by-case basis.

    /**
     * This interface is for loading and storing {@link com.vite.widget.TheWidget}s and is exposed primarily via
     * web calls.
     */
    public interface WidgetProcessor {
        // Code goes here
    }


### Classes
Classes should have Javadoc documentation describing what they are intended to do.  If they have are an implementation
 that is specific to an interface, describe what it is that the implementation is meant for. Links to other classes and
 interfaces in the documentation that matter are recommended on a case-by-case basis.

    /**
     * This is the default implementation of the WidgetProcessor interface, used primarily for the web communication
     * layer to load and store {@link com.vite.widget.TheWidget}s.
     */
    public class DefaultWidgetProcessorImpl implements WidgetProcessor {
        // Implementation details go here
    }

Note that in the above example, we do not link to the WidgetProcessor in the text.  This is because we are
 implementing it as part of the class definition, and when it is output to a javadoc website there will be a link
 to all implemented interfaces on the class' definition page.

### Methods
Methods should be documented. They should cover what the method does, what the inputs mean and what the return value
 (if there is one) represents.  If a method overrides a parent class or interface method, then the parent javadoc will
 be inherited unless it is overridden.  If the override function needs additional documentation, it is often best to
 include a `{@inheritDoc}` after the new documentation so that the original documentation gets included.

    /**
     * This method gets {@code TheWidget} that is related to the {@code Fluxion} passed in as the request.  If none is
     * found, then {@code null} will be returned.
     * @param fluxion The {@code Fluxion} that we want to lookup associated widgets from.
     * @return The found {@code TheWidget}, or {@code null} if no {@code TheWidget} is associated with the passed in
     *          {@code Fluxion}.
     */
    public TheWidget lookupWidget(Fluxion fluxion) {
        // lookup code here
    }

### Variables
Class-level variables, whether they are public or not, should have a short javadoc explaining what they are there for.

    /** Should be {@code True} when the system looks for failures, otherwise {@code False}.  Defaults to {@code False}.*/
    private bool isFailChecked = False;

### Test Cases
Test cases do not necessarily need Javadocs, but it is recommended to include them in any case where it is not obvious
 what the test is testing.

## Comments
Comments should be used sparingly, but where they are needed they should be above the line they are referencing, or
 in very rare cases inline with what they are referencing.  Comments are generally considered a code smell, and as such
 should be avoided if at all possible.

An example of a good place to use a comment - if something is particularly complex, or specific to our environment, or
 a workaround to a bug in a library or even the JVM, having a comment about that, and a link to any articles describing
 it (such as Stack Overflow) is valuable.

    // We found this bug in the JVM after hours of searching - see http://stackoverflow.com/something for more info
    // Also see Jira ticket XS-12345
    com.sun.never.touch.this.foo = False;

Alternatively, having a comment noting that you are setting a value into a variable when it is a simple assignment is
 generally not a valuable comment.

    // This sets isAlive to False.
    isAlive = False;

# Code Style
What follows are some general rules for the code style so that there are less diffs between people during commits.

## Tabs vs. Spaces
We will be using spaces for indentation, primarily because GitHub has decided, in its infinite wisdom, to make tabs
 8 characters instead of 4.

## Curly Braces
Opening curly braces are always on the same line, and closing curly braces should be matched to the same indentation as
 the opening line.

_ALL_ loops, conditionals and switch statements _MUST_ have curly braces, even if they are only a single line of
 execution. The reason for this is because it is VERY easy to inject bugs when you add lines later and don't notice that
 a conditional or an if doesn't have a curly brace. This one will hold up releases, as it can lead to some very
 frightening bugs.

    if (someTest == someValue)
        doImportantStuff(variables);

The above example should NEVER be done.  It is really REALLY easy to accidentally do the following

    if (someTest == someValue)
        doImportantStuff(variables);
        doSomethingElseThatShouldOnlyHappenIfTrue(variables);

Which could lead to some nasty bugs, including cases where people could get access to data they shouldn't have access to.

Alternatively, it should be written as follows

    if (someTest == someValue) {
        doImportantStuff(variables);
    }

This way it is obvious to anyone following after you (including yourself) where the if statement starts and stops, and
 what is included or not as code gets added.

## Column Length
We will be using a 100 character column length, as that is the size that seems to fit into GitHub best.  If a line is
 too long and needs to be wrapped, you can drop to the next line and indent two steps to show that it is a continuation.

# Warning Free Code
Our code should be warning free.  Warnings should be treated as errors.  In some cases it is not possible to remove a
 warning, such as around certain operations with generics. In those cases a `@SuppressWarnings` annotation can be
 used to handle that, but should be used as sparingly as possible.

# Annotations
Annotations are a way to decorate things in Java.  These include methods, classes and interfaces. Some annotations are
 used at runtime to allow systems to know things about objects that would not be obvious otherwise. Spring uses these
 annotations for their automated bean wiring.

Other annotations are stripped at compile time, and are there as a helper to the developer and the IDE and compiler. 
 These can help by pointing out potential issues, helping with autocomplete, or hiding warnings that just won't go
 away (because the language forces the situation to occur).  What follows are some of the most common annotations that
 are used for those purposes and a discussion about how and when to use them.

## @Override
Any function that overrides a parent function (or implements an interface function) should have the `@Override` built
 in annotation attached to it.  This makes it easy to find places where functions were overridden should they be
 deleted, and also helps developers know if a function is overriding or implementing an interface.

    @Override
    public void overriddenFunction() {
        // code goes here
    }

## @SuppressWarnings
Sometimes we will have warnings that cannot be fixed. The most common case of this is around operations with generics.
 In these cases, we can suppress the warnings so our code will be warning free, but they should be used as sparingly
 as possible.

    @SuppressWarnings("deprecation")
    public void hasToUseADeprecatedFunction() {
        // code goes here
    }

# Recommendations
Following are some recommendations to help make your development life easier, as well as to make it easier to comply
 with the style guide.

## Use an IDE
Being a statically typed language, Java is ideal for using in an IDE. This allows for coloring of the code, ease of
 refactoring, and automatic styling with the style guide.
 
We recommend using IntelliJ, since we have an [export](code-style-sttings.jar) of the code style settings you can import.

# References
<a name="damianConway">1</a>: Perl Best Practices, page 132
