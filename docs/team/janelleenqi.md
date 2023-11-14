# Liow Enqi Janelle's Project Portfolio Page

## Project Overview

NUSDegs streamlines the process of degree planning for NUS computing students by offering personalized module 
schedules, progress tracking, and ensuring on-time graduation. It eliminates guesswork, reduces stress, and saves time 
for students. This is a comprehensive tool for efficient and successful degree completion.

## Summary of Contributions

### Code contributed: [Link to RepoSense Report](https://nus-cs2113-ay2324s1.github.io/tp-dashboard/?search=janelleenqi&breakdown=false&sort=groupTitle%20dsc&sortWithin=title&since=2023-09-22&timeframe=commit&mergegroup=&groupSelect=groupByRepos)

### Features Implemented

#### 1. Timetable Show Feature
- Displays the weekly timetable of the user. Using the lessons added by the user through Timetable Modify Feature, this feature displays the user's 
lessons sorted by time and day.

- Justification: This feature gives users a clear idea on how their current semester will look like on a weekly basis. 
This will enable them to view their timetable easily.

- Highlights: Lessons are displayed by time and day and does not show days that do not have lessons.


#### 2. Save Timetable Feature
- Saves the weekly timetable of the user throughout the program. Updates the save file for the weekly timetable of the user when the user uses any features that changes 
the timetable (timetable modify, recommend, shift, delete, clear).

- Justification: This feature allows the user to store their timetable data, so that they can built upon the same 
timetable across multiple sessions. 
- Highlights: The timetable needed to be updated when modules were deleted, to remove those modules from the timetable.

#### 3. Modules Left Feature
- Added the ability to view modules left. Allows the user to retrieve a list of modules that required for their major but have not been marked as
completed.

- Justification: This feature gives clarity to the user as they are able to easily view what modules they have yet to 
complete for graduation.

#### 4. Modules Required Feature
- Added the ability to view modules required. Provides students with easy access to the requirements of their major, and be aware of Common Curriculum 
Requirements.

- Justification: Gives students an easy access to a list of modules (and their names) they are required to take to 
graduate.

### Classes Implemented

#### 1. Event (and its child classes Lecture, Tutorial, Lab)
- Purpose: Allow information on a lesson to be store concisely for convenience, as well as methods for comparison 
between different events (for printing in order), printing and saving. 

#### 2. ModuleList
- Purpose: Provides multiple methods to use an ArrayList of Module, for easy retrieval of data (eg. ArrayList of module 
codes, ArrayList of modules completed, module index)

#### 3. UserCommand & TimetableUserCommand
- Purpose: Validate user commands, process commands

#### 4. TimetableView
- Purpose: Provide a dynamic printing method for Timetable that adjusts according to number of  information of lessons 
provided by user, ensuring optimal readability and good user experience. 

### Contributions to Team-based Tasks

#### 1. Refactor Code

- Justification: Improved abstraction, separation of concerns and made it easier for testing
- Highlights: Required an in-depth understanding of code to ensure that no features were affected.

#### 2. Project Management

- My contribution: Created the Labels and Milestones, and a majority of Story/Task-base Issues within the Github Tracker.
- Justification: Allowed my team to be clearer on what we need to accomplish, to ensure productivity.

#### 3. Documentation
##### User Guide:
- Added documentation for the features left and required
- Updated documentation for the features timetable show, timetable modify, help, bye.

##### Developer Guide:
- Added implementation details of the features left, required, timetable show.
- Sections: Non-Functional Requirements, Glossary, Instructions for manual testing (Launch & Modify lessons in the Weekly Timetable Feature)
- Diagrams (refer to extracts)

#### 4. Fixed Bugs

- `Bye` command was case sensitive, needed to input twice to exit [#154](https://github.com/AY2324S1-CS2113-T17-4/tp/pull/154)
- `timetable modify`: fix error messages (had cases with double errors, or no errors)
- infinite loop of error messages [#139](https://github.com/AY2324S1-CS2113-T17-4/tp/pull/139)
- `complete`: complete a completed module [#115](https://github.com/AY2324S1-CS2113-T17-4/tp/issues/115)

#### 5. Testing
- SUT tests for the features Left, Required, Timetable Modify & Timetable Show, the class ModuleList


#### 6. Community
- PRs reviewed: [#38](https://github.com/AY2324S1-CS2113-T17-4/tp/pull/38#pullrequestreview-1721555155), [#152](https://github.com/AY2324S1-CS2113-T17-4/tp/pull/152), [#155](https://github.com/AY2324S1-CS2113-T17-4/tp/pull/155#pullrequestreview-1721581031), [#157](https://github.com/AY2324S1-CS2113-T17-4/tp/pull/157#pullrequestreview-1719360399) 
- Made `timetable modify` less bug prone

### Diagram Contributions to the Developer Guide (Extracts)

Model Component: Component Diagram
![ModelComponent.png](..%2Fdiagrams%2FModelComponent.png)

List Required Modules Left Feature: Sequence Diagram
![LeftFeature_Seq.png](..%2Fdiagrams%2FLeftFeature_Seq.png)

Required Feature: Sequence Diagram
![RequiredFeature_Seq.png](..%2Fdiagrams%2FRequiredFeature_Seq.png)

Show Weekly Timetable Feature: Sequence Diagram
![TimetableShowFeature_Seq.png](..%2Fdiagrams%2FTimetableShowFeature_Seq.png)

Show Weekly Timetable Feature: Sequence Diagram for printTimetable operation
![PrintTimetable_Seq.png](..%2Fdiagrams%2FPrintTimetable_Seq.png)



