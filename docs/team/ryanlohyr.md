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
- `Highlights:` This feature was challenging as format of the prerequisites returned from NUSMODS API was unintuitive, 
and it required an in depth understanding of recursive functions due to its nested prerequisites. 
Furthermore, good understanding of the REST API convention in order to retrieve prerequisites based on module 
from the NUSMODS API. 

#### 2. Schedule Recommending Feature

- `What it does:` Allows the user to generate a course based recommended schedule that is sorted based on prerequisites.
- `Justification:` This allows the user to have a schedule based on their degree, which saves time for the user having to 
look through all the prerequisites of each module.
- `Highlights:` This feature was challenging as it required in depth data structure and algorithms knowledge. The 
feature utilised the topological sort algorithm, hashmaps, adjacency lists and queues. 

#### 3. Pace Feature

- `What it does:` Allows the user to see the average pace required for graduation
- `Justification:` Provides crucial information for users to plan their academic journey effectively. 
Enables students to track and manage their progress towards graduation.
  Assists in avoiding potential delays in completing the degree by offering insights into the necessary pace.
- `Highlights:` Challenging as it required careful validation of the inputs as well as taking into account various other
components such as the schedule and student class.

### Enhancements Implemented

#### 1. Optimised runtime on certain functions

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
[Detailed explanation regarding the optimisations I did](https://github.com/AY2324S1-CS2113-T17-4/tp/pull/148) 

#### 2. Implemented overall architecture to adopt MVC design pattern
- `Justification`: Improved abstraction, separation of concerns and made it easier for my teammates to work on features
  independently
-  `Highlights`: Required an in-depth understanding of design patterns. The implementation was challenging as well as I
   had to constantly communicate with my teammates where respective functions had to be placed.

### Code Contributions: 
[RepoSense link](https://nus-cs2113-ay2324s1.github.io/tp-dashboard/?search=ryanlohyr&sort=groupTitle&sortWithin=title)

### Contribution to UG:

- Added table of contents
- Added header links to Table of contents 
- Restructure commands to have a natural progressive flow
- GitHub Theme to enhance look


### Contributions to team-based tasks

- Maintain issue tracker and milestones
- Initiated team to do feature based integration tests
- Add JavaDoc to most methods
- Suggested and maintained external
[notion board](https://ryanloh.notion.site/2113-Task-Board-b13948bab54046c3b49b24d5d978379a?pvs=4)

### Reviews/mentoring contributions: 

- PRs reviewed ([#152](https://github.com/AY2324S1-CS2113-T17-4/tp/pull/152), [#154](https://github.com/AY2324S1-CS2113-T17-4/tp/pull/154#pullrequestreview-1718105780), Provided pseudocode to help teammate understand my suggestion in this PR
[#166](https://github.com/AY2324S1-CS2113-T17-4/tp/pull/166) , 
[#185](https://github.com/AY2324S1-CS2113-T17-4/tp/pull/185))

### Contributions beyond the project team: 

- Reported 8 bugs for PED, helped other teams debug their jar and spot potential issues


### Contributions to DG:

**Architecture diagram:**      

<img src="../diagrams/architectureDiagram.jpeg" width= "320">

**Ui class diagram:**

<img src="../diagrams/UI.jpeg" width= "220">

**Pace feature sequence diagram:**                                                                               

<img src="../diagrams/pace_sequenceDiagram.jpeg" width= "500">

**Recommend feature sequence diagrams:**

Recommended a schedule based on the user's major:

<img src="../diagrams/recommended_one.jpeg" alt="Image" width="500">

Add a Recommended schedule to the user's schedule:


<img src="../diagrams/recommended_two.jpeg" alt="Image" width="500">
