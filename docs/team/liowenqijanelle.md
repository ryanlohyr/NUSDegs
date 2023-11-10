# Liow Enqi Janelle's Project Portfolio Page

## Project: NUSDegs

NUSDegs streamlines the process of degree planning for NUS computing students by offering personalized module 
schedules, progress tracking, and ensuring on-time graduation. It eliminates guesswork, reduces stress, and saves time 
for students. This is a comprehensive tool for efficient and successful degree completion.

## Summary of Contributions

### Code contributed

### Features Implemented

#### 1. Timetable Show Feature

Feature: Displays the weekly timetable of the user.

What it does: Using the lessons added by the user through Timetable Modify Feature, this feature displays the user's 
lessons sorted by time and day.

Justification: This feature gives users a clear idea on how their current semester will look like on a weekly basis. 
This will enable them to view their timetable easily.

Highlights: Lessons are displayed by time and day and does not show days that do not have lessons.

Credits: nil

#### 2. Modules Left Feature

Feature: Added the ability to view modules left.

What it does: Allows the user to retrieve a list of modules that is in their schedule but have not been marked as
completed.

Justification: This feature gives clarity to the user as they are able to easily view what modules they have yet to complete.

Highlights: nil

Credits: nil

#### 3. Modules Required Feature

Feature: Added the ability to view modules required.

What it does: Provides students with easy access to the requirements of their major, and be aware of Common Curriculum 
Requirements.

Justification: 

Highlights: nil

Credits: nil

### Classes Implemented

#### 1. Event (and its child classes Lecture, Tutorial, Lab)

#### 2. ModuleList
Purpose: Provides multiple methods to use an ArrayList of Module, for easy retrieval of data (eg. ArrayList of module 
codes, ArrayList of modules completed, module index)

#### 3. UserCommand & TimetableUserCommand
Purpose: Validate user commands, process commands

#### 4. TimetableView
Purpose: Provide a dynamic printing method for Timetable that adjusts according to number of  information of lessons 
provided by user, ensuring optimal readability and good user experience. 

### Contributions to Team-based Tasks

#### 1. Refactor Code

Justification: Improved abstraction, separation of concerns and made it easier for testing
Highlights: Required an in-depth understanding of code to ensure that I am not breaking features implemented by my 
teammates. 

