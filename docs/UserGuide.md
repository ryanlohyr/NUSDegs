# NUSDegs User Guide

## Introduction

NUSDegs streamlines computing degree planning by offering personalized module schedules, tracking progress, 
and ensuring on-time graduation. It eliminates guesswork, reduces stress, and saves time for students. 
It's a comprehensive tool for efficient and successful degree completion.

## Quick Start

{Give steps to get started quickly}

1. Ensure that you have Java 11 or above installed.
1. Down the latest version of `Duke` from [here](http://link.to/duke).

## Features
- Left
- Required
- Info
- Search
- Major
- Add
- Delete

{Give detailed description of each feature}

### Checking modules left: `left`
Displays the modules left, which are the modules required for the user's major that have not been completed.

Format: `left n/MAJOR`

* The `MAJOR` is an optional argument and can be CS or CEG.

Example of usage 1: (scenario where user's selected major is CS)

User input: 
`left`

- Expected outcome:
`Modules Left: CS2030S CS2040S CS2100 CS2101 CS2106 CS2109S CS3230"`

Example of usage 2: 

User input:
`left CEG`

- Expected outcome:
`Modules Left: 
CG4002
MA1508E
EG2401A
CP3880
CG2111A
CS1231
CG2023
CG2027
CG2028
CG2271
ST2334
CS2040C
CS2113
EE2026
EE4204"`

### Getting a list of required modules: `required`
Get an overview of required modules for the user's major

Format: `required n/MAJOR`

* The `MAJOR` is an optional argument and can be CS or CEG.

Example of usage 1: (scenario where user's selected major is CS)

User input:
`required`

- Expected outcome:
Module requirements for CS

Example of usage 2:

User input:
`required CEG`

- Expected outcome:
Module requirements for CEG

### Get information about a module: `info`
Get information about a module using the info command, followed by one of the commands 'description', 'workload' 
and 'all'.

Format: `info n/command n/moduleCode`


* The `command` cannot be empty.
* The `moduleCode` cannot be empty.

Example of usage: 

`info description CS2113`

- Expected outcome: This course introduces the necessary skills for systematic and rigorous development of software systems. It covers requirements, design, implementation, quality assurance, and project management aspects of small-to-medium size multi-person software projects. The course uses the Object Oriented Programming paradigm. Students of this course will receive hands-on practice of tools commonly used in the industry, such as test automation tools, build automation tools, and code revisioning tools will be covered.

`info workload CS2113`

- Expected outcome: "[2,1,0,3,4]"

`info all`

- Expected outcome: "returns module title and module code of all available modules"


### Searching for a module by title: `search`
Search for module title using a keyword.

Format: `search n/KEYWORD`

* The `KEYWORD` cannot be empty.

Example of usage:

`search n/Darwinian`

- Expected outcome:
These are the modules that contain your keyword in the title:

### Selecting your current major: `major`
Saves the selected major into the programme if a major is specified.
Returns the current saved major if no major is specified.

Format: `major n/MAJOR`

* The `MAJOR` is an optional argument and can be CS or CEG.

Examples of usage:

`major` (Assume no major has been selected)

- Expected outcome: "No major selected!"

`major CEG`

- Expected outcome: "Major CEG selected!"

`major` (Assume CEG has been selected by the previous command)

- Expected outcome: "Current major is CEG."


### Add module to schedule: `add`
Opens the user's personalized module schedule and adds the chosen module to the semester specified by the user.

Format: `add n/MODULE n/SEMESTER`

* The `MODULE` cannot be empty and must be valid.
* The `SEMESTER` cannot be empty and must be an integer between 1-8 inclusive.

Examples of usage:

`add CS1010 1`

- Expected outcome:

![](photos/add_outcome.png)

### Delete module from schedule: `delete`
Opens the user's personalized module schedule and deletes the chosen module.

Format: `delete n/MODULE`

* The `MODULE` cannot be empty and must be valid.

Examples of usage:

`delete CS1010` (Assume schedule is currently in the state from the example in `add`)

- Expected outcome:

![](photos/delete_outcome.png)

## FAQ

**Q**: How do I transfer my data to another computer? 

**A**: {your answer here}

## Command Summary

{Give a 'cheat sheet' of commands here}

* Add todo `todo n/TODO_NAME d/DEADLINE`
