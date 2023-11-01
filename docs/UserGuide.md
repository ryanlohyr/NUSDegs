# User Guide

## Introduction

{Give a product intro}

## Quick Start

{Give steps to get started quickly}

1. Ensure that you have Java 11 or above installed.
1. Down the latest version of `Duke` from [here](http://link.to/duke).

## Features 
- Left
- Required

{Give detailed description of each feature}

### Checking modules left: `left`
Displays the modules left, which are the modules required for the user's major that have not been completed.

Format: `left n/MAJOR`

* The `MAJOR` is an optional argument and can be CS or CEG.

Example of usage 1: (scenario where user's selected major is CS)

User input: 
`left`

Expected outcome:
`Modules Left: CS2030S CS2040S CS2100 CS2101 CS2106 CS2109S CS3230"`

Example of usage 2: 

User input:
`left CEG`

Expected outcome:
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

Expected outcome:
Module requirements for CS

Example of usage 2:

User input:
`required CEG`

Expected outcome:
Module requirements for CEG

### Adding a todo: `todo`
Adds a new item to the list of todo items.

Format: `todo n/TODO_NAME d/DEADLINE`

* The `DEADLINE` can be in a natural language format.
* The `TODO_NAME` cannot contain punctuation.  

Example of usage: 

`todo n/Write the rest of the User Guide d/next week`

`todo n/Refactor the User Guide to remove passive voice d/13/04/2020`

## FAQ

**Q**: How do I transfer my data to another computer? 

**A**: {your answer here}

## Command Summary

{Give a 'cheat sheet' of commands here}

* Add todo `todo n/TODO_NAME d/DEADLINE`
