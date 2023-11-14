# Sebastian Fok Shin Hung - Project Portfolio Page

## Overview

NUSDegs streamlines computing degree planning by offering personalized module schedules,
tracking progress, and ensuring on-time graduation. It eliminates guesswork, reduces stress,
and saves time for students. It’s a comprehensive tool for efficient and successful degree completion.

## Summary of Contributions

### Features Implemented

#### 1. Add Schedule Feature

- `What it does:` Allows the user view schedule planner.
- `Highlights:` This feature is implemented through a `schedule` class which is instantiated within a `student` class.
Makes use of OOP concepts to model real life objects.

#### 2. Add Module Feature

- `What it does:` Allows the user to add a module to their schedule planner, and verifies that prerequisites are met
before adding.
- `Justification:` This forms the core of the program and would be used in several other area of our application.
- `Highlights:` This feature was challenging as it requires integration with the NUSMODS API, which I was unfamiliar
with. The format of the prerequisites was hard to understand and required good understanding of recursive functions to
traverse through the prerequisite tree to verify the prerequisites.

#### 3. Delete Module Feature

- `What it does:` Allows the user to delete a module from their schedule planner, and checks that it is not a
prerequisite of a future module before deleting it.
- `Highlights:` This feature had multiple ways of implementation. When I first implemented the feature, it checked if
every module in the semesters after the target module would still have their prerequisites satisfied even after deletion
of the target module. However, this implementation was extremely slow as it had to parse through large amounts of
modules before the check is complete. One benefit of this implementation is that it is guaranteed to always be correct,
however it severely reduces the usability of the application due to processing time. Hence, my group mate Ryan modified
the implementation to trade small amounts of accuracy for a huge improvement in performance.

#### 4. Shift Feature

- `What it does:` Allows the user to shift a module from one sem to another in their schedule planner
- `Justification:` Initially, we only had add and delete features, but we realised quickly that it is extremely 
time-consuming an inconvenient to move modules around the schedule. For example, if CS1010 is taken in semester 1, and
CS2040C is taken in semester 3, for the user the shift CS1010 to semester 2, they have to first delete CS2040C, then 
delete CS1010, re-add CS1010 to semester 2 and then re-add CS2040C to semester 3. The shift command takes care of 
prerequisite verification logic and allows for quick shifting between semesters.
- `Highlights:` This feature required combining the logic from both add and delete, and separating the shifting action
to two cases, shifting earlier and shifting later. Each case requires a different logic to implement, and
requires clear understanding on what are the checks that each case needs to be executed.
#### 5. Clear Feature

- `What it does:` Allows the user to clear their entire schedule
- `Justification:` We quickly realised after implementing the recommend schedule feature that we needed a way to quickly
delete the whole schedule, as it would not make sense for the user to delete each module individually 30+ times.
- `Highlights:` This feature was quite simple to implement, as it just replaces the `schedule` object in `student` with
a new schedule, allowing the user to start from scratch.

#### 6. Storage implementation
- `What it does:` Allows the user to save their name, major, year, schedule and timetable when using NUSDegs
- `Justification:` Since NUSDegs is a planner app, it must be able to have some memory storage capabilities. It is also
important to store some vital information about majors that the NUSMODS API is unable to provide.
- `Highlights:` The memory of each feature is stored in separate text files. At first, we were not sure if schedule and 
timetable data should be stored after every edit or when the user exits the application. We decided to implement the
former as firstly, it is much safer for the user's data should the application crash due to some unforeseen 
circumstance. Due to our app requiring internet connection, a loss in connection might result in the NUSDegs closing,
which is fine as the students progress would be saved before connection was lost. The trade-off of requiring more
processing time and power is also negligible in this case.

### Enhancements Implemented

#### 1. Reformatted the help message

- `Problem at hand:` The help message was rather messy with insufficient instruction to use our features
properly
- `Justification:` The help feature is important for the user to quickly understand the features of NUSDegs. By
standardising indentation, and having clear gaps between input and description, it allows for a clean view for the user
to quickly glance through and look for what they need.


### Code Contributions:
[RepoSense link](https://nus-cs2113-ay2324s1.github.io/tp-dashboard/?search=sebasfok&breakdown=false&sort=groupTitle%20dsc&sortWithin=title&since=2023-09-22&timeframe=commit&mergegroup=&groupSelect=groupByRepos)

### Contribution to UG:

- Added documentation for schedule, add, delete, shift and clear features
- Reformatted font size and effects to standardise across all the features
- Added some info in quick start
- Replaced some of the screenshots with code for a cleaner guide
- 
### Contributions to DG:

- Added implementation details for add and clear features
- Added implementation for storage component
- Diagrams: (refer to extract)

### Contributions to team-based tasks

- Set up GitHub team org and repo
- Created the student and schedule objects to introduce OOP concepts to our application
- Refactored some code to make it more readable and easier to isolate issues
- Add JavaDoc to some methods

### Contributions beyond the project team:

- Reported 5 bugs for PED, helped other teams debug their jar and spot potential issues


### Diagrams in DG(extract):

**Storage object diagram:**

![StorageDiagram.png](..%2Fdiagrams%2FStorageDiagram.png)

**Add module sequence diagram**

![updatedAddModule.png](..%2Fdiagrams%2FupdatedAddModule.png)

**Clear schedule sequence diagram**

![ClearDiagram.png](..%2Fdiagrams%2FClearDiagram.png)