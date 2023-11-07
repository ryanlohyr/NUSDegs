# Ryan Loh - Project Portfolio Page

## Overview

NUSDegs streamlines computing degree planning by offering personalized module schedules,
tracking progress, and ensuring on-time graduation. It eliminates guesswork, reduces stress,
and saves time for students. It’s a comprehensive tool for efficient and successful degree completion.

## Summary of Contributions

### Features Implemented

#### 1. Module Prerequisite Feature

- `What it does:` Allows the user to see the prerequisites the module has.
- `Justification:` This feature allows the user to plan their schedule more efficiently, eliminating guesswork. 
This Function is also used as a helper function for feature 2.
- `Highlights:` This feature was challenging as format of the prerequisites returned from NUSMODS API was unintuitive 
and it required an in depth understanding of recursive functions due to its nested prerequisites. 
Furthermore, good understanding of the REST API convention in order to retrieve prerequisites based on module 
from the NUSMODS API. 

#### 2. Schedule Recommending Feature

- `What it does:` Allows the user to generate a course based recommended schedule that is sorted based on prerequisites.
- Justification: This allows the user to have a schedule based on their degree, which saves time for the user having to 
look through all the prerequisites of each module.
- `Highlights:` This feature was challenging as well as it required in depth data structure and algorithms knowledge. The 
feature utilised the topological sort algorithm, hashmaps, adjacency lists and queues. 


### Enhancements Implemented

#### 1. Implemented overall architecture to adopt MVC design pattern

- `Justification`: Improved abstraction, separation of concerns and made it easier for my teammates to work on features 
independently
-  `Highlights`: Required an in-depth understanding of design patterns. The implementation was challenging as well as I 
had to constantly communicate with my teammates where respective functions had to be placed.

#### 2. Optimised runtime on certain functions

- `Problem at hand:` Adding a recommended schedule to a schedule and deleting a module from a planner both took
significantly long (150 seconds and 20 seconds respectively) due to the checking of prerequisites which required API calls to NUS MODS.
- `Justification:` 
  - Initially, the GitHub actions consumed a total of 
  [18 minutes](https://github.com/AY2324S1-CS2113-T17-4/tp/actions/runs/6770254540). However, they were subsequently 
  optimized, resulting in a reduction to just
  [3 minutes](https://github.com/AY2324S1-CS2113-T17-4/tp/actions/runs/6773558838). 
  - Moreover, each execution of the functions previously required nearly 2 minutes to complete. 
  - This optimization not only enhanced the productivity of my colleagues by eliminating the need to wait for 20 minutes 
  for their GitHub actions to run but also significantly improved the overall user experience.
-  `Highlights:` In depth understanding of time complexities as well as the responses NUSMODs provided 
was required in order to make the optimisations. 
[Detailed explanantion regarding the optimisations](https://github.com/AY2324S1-CS2113-T17-4/tp/pull/148) 

### Code Contributions: 
[RepoSense link](https://nus-cs2113-ay2324s1.github.io/tp-dashboard/?search=ryanlohyr&sort=groupTitle&sortWithin=title)

### Contribution to UG:

### Contributions to DG:

### Contributions to team-based tasks

### Reviews/mentoring contributions: 

- PRs reviewed ([#152](https://github.com/AY2324S1-CS2113-T17-4/tp/pull/152), [#154](https://github.com/AY2324S1-CS2113-T17-4/tp/pull/154#pullrequestreview-1718105780))

### Contributions beyond the project team: 


|                    Ui class diagram:                     |                   Logic object diagram:                    |
|:--------------------------------------------------------:|:----------------------------------------------------------:|
|  <img src="../uml/diagrams/UiClass.png" height="300"/>   |  <img src="../uml/diagrams/LogicClass.png" height="200"/>  |
|                **Model object diagram:**                 |                **Storage object diagram:**                 |
| <img src="../uml/diagrams/ModelClass.png" height="150"/> | <img src="../uml/diagrams/StorageClass.png" height="100"/> |

**Add module sequence diagram:**

<img src="" height="300"/>

**Remove module sequence diagram:**

<img src="../uml/diagrams/RemoveModSequence.png" height="300"/>

**Mark module sequence diagram:**

<img src="../uml/diagrams/MarkModSequence.png" height="300"/>

**List sequence diagram:**

<img src="../uml/diagrams/ListSequence.png" height="300"/>

**Grade class diagram:**

<img src="../uml/diagrams/GradeClass.png" height="150"/>

**Save feature class diagram:**

<img src="../uml/diagrams/SaveFeatureClass.png" height="200"/>

**Architecture diagram:***
