<h1 style="text-align: center; 
background-image: linear-gradient(to right, #370505, #5b2829, #814c4c, #a97171, #d19999);">
    <img src="https://picsum.photos/320" alt="Header Image">
</h1>

<h1>
<span style="background-image: linear-gradient(to right, #14499b, #0065b7, #0081d1, #009ee9, #00bcff);
-webkit-background-clip: text; color: transparent;">N</span>US 
<span style="background-image: linear-gradient(to right, #e50000, #e84034, #e6615a, #de7e7b, #d19999);
-webkit-background-clip: text; color: transparent;">D</span>EGS
<span style="background-image: linear-gradient(to right, #959b14, #00a754, #00a9a9, #00a0f0, #0085ff);
-webkit-background-clip: text; color: transparent;">User Guide</span>
</h1>

## Introduction

NUSDegs streamlines computing degree planning by offering personalized module schedules, tracking progress, 
and ensuring on-time graduation. It eliminates guesswork, reduces stress, and saves time for students. 
It's a comprehensive tool for efficient and successful degree completion.

## Quick Start

1. Ensure that you have Java 11 or above installed.
2. Download the latest version of `NUSDegs` from [here](http://link.to/duke).
3. Copy the file to the folder you want to use as the home folder for NUSDegs.
4. Open a command terminal, cd into the folder you put the .jar file in, and run the command
   java -jar "duke.jar" to run the application.
5. Ensure that you remember the name you input into NUSDegs at the start of the application, as it would be used to
   access you saved data during subsequent uses. The name used is CASE_SENSITIVE.

## Features
- View help : `help`
- View modules required for major: `required`
- Recommend a schedule based on major: `recommend`
- Search for modules based on keywords: `search`
- View info about a module: `info`
- Check prerequisite for a module: `prereq`
- View schedule planner: `schedule`
- Add module to schedule planner: `add`
- Delete module from schedule planner: `delete`
- Shift module in schedule planner: `shift`
- Clear all schedule planner and completion data: `clear`
- Complete a module in your schedule planner: `complete`
- View modules left for graduation: `left`
- Check current pace to graduate: `pace`
- View weekly timetable: `timetable show`
- Modify weekly timetable: `timetable modify`
- Saves the user's schedule and exits the program: `Bye`

Note: Between arguments, spaces are required. Arguments need to be passed in the correct order.

### Viewing help: `help`

To view a list of all possible commands, a brief description of their functionality and syntax.

##### Format: `help`

User input:
`help`

- Expected outcome:
![ss_help.png](screenshots%2Fss_help.png)

### Getting a list of required modules:`required`
Get an overview of required modules for the user's major

##### Format: `required`

##### Example of usage 1: (user's major is CEG)

User input:
`required`

- Expected outcome:

<img src="screenshots/ss_required_ceg_1.png" alt="Image" width="300">
<img src="screenshots/ss_required_ceg_2.png" alt="Image" width="300">


##### Example of usage 2: (user's major is CS)

User input:
`required`

- Expected outcome:

<img src="screenshots/ss_required_cs_1.png" alt="Image" width="300">
<img src="screenshots/ss_required_cs_2.png" alt="Image" width="300">


### View module prerequisites:`prereq`
Based on the module selected, we will show what prerequisites the course has.

##### Note:
- Since NUS has the concept of preclusions, when prerequisites are shown, it is shown based on the degree of the current 
user.

- If the module is not a requisite of the students major, we will only show one preclusion as a prerequisite

##### Format: `prereq MODULE_CODE`

- The input is not case-sensitive. E.g. eg1311 or EG1311 is shown
out

##### Example of Usage:

User input:
`prereq eg1311` 

Assuming the user is from Computer Engineering

- Expected Outcome

<img src="screenshots/ss_prereq.jpeg" alt="Image" width="300">


### Searching for a module by title: `search`
Search for module title using a keyword.

##### Format: `search KEYWORD`

* The `KEYWORD` cannot be empty.

##### Example of usage:

User input:
`search Darwinian`

- Expected outcome:\
  _________________________________________\
  These are the modules that contain your keyword in the title:
  
  Title: Junior Seminar: The Darwinian Revolution
  Module Code: UTC1102B\
  _________________________________________\

### Get information about a module: `info`
Get information about a module using the info command, followed by the command 'description'.

##### Format: `info COMMAND MODULE_CODE`


* The `COMMAND` cannot be empty.
* The `MODULE_CODE` cannot be empty.

##### Examples of usage:

User input:
`info description CS2113`

- Expected outcome: This course introduces the necessary skills for systematic and rigorous development of software 
systems. It covers requirements, design, implementation, quality assurance, and project management aspects of 
small-to-medium size multi-person software projects. The course uses the Object Oriented Programming paradigm. 
Students of this course will receive hands-on practice of tools commonly used in the industry, such as test automation 
tools, build automation tools, and code revisioning tools will be covered.

### View schedule planner: `schedule`
Shows the user their current schedule planner

##### Format: `schedule`

- The input does not accept any arguments after the command word.

##### Example of usage: 

User input: `schedule`

- Expected outcome(if user has not inputted any modules):

![schedule.jpg](photos%2Fschedule.jpeg)

### View recommended schedule based on course: `recommend`
Based on the student's course, we will provide a recommended schedule that is sorted based on prerequisites.

##### Format: `recommend`

##### Example of usage:

User input:
`recommend`

- Expected outcome:

![recommendedSchedule.jpg](photos%2FrecommendSchedule.jpeg)

- If the user enters `Y`, the recommended schedule will be added to their schedule

![recommendedSchedule.jpg](screenshots%2Fadd_recommend.jpeg)


### Add module to schedule planner: `add`
Opens the user's personalized module schedule planner and adds the chosen module to the semester specified by the user.
Adding will not be allowed if the current schedule planner does not contain the required prerequisites.

##### Format: `add MODULE_CODE SEMESTER`

* `MODULE_CODE` cannot be empty and must be valid.
* `SEMESTER` cannot be empty and must be an integer between 1-8 inclusive.

##### Note:
- We do not check for preclusion's.
  E.g. If you have completed/added CS2040, and the prerequisite of a module you are trying to add is CS2040C,
  although they are preclusion's of each other, you are required to satisfy CS2040C! (It is something 
we hope to implement in the future!)


##### Example of usage:

User input:
`add CS1010 1`

- Expected outcome:
![ss_add_cs1010_1.png](screenshots%2Fss_add_cs1010_1.png)

### Delete module from schedule planner: `delete`
Opens the user's personalized module schedule planner and deletes the chosen module. Deleting will not be allowed if
the module to be deleted is a prerequisite of a module in later semesters on the schedule planner.

##### Format: `delete MODULE_CODE`

* `MODULE_CODE` cannot be empty and must be valid.
* `MODULE_CODE` must also be in the current schedule planner

##### Note:
- We do not check for preclusion's.
E.g. If you have completed/added CS2040, and the prerequisite of a module you are trying to delete is CS2040C, 
although they are preclusion's of each other, you are required to satisfy CS2040C! (It is something we hope to implement 
in the future!)

##### Examples of usage:

User input:
`delete CS1010` (Assume schedule is currently in the state from the example in `add`)

- Expected outcome:

![](photos/delete_outcome.png)

### Shift module in schedule planner: `shift`
Opens the user's personalized module schedule planner and shifts the chosen module to the semester specified by the 
user. Shifting will not be allowed if it causes conflicts with other modules in the schedule planner.

##### Format: `shift MODULE_CODE SEMESTER`

* `MODULE_CODE` cannot be empty and must be valid.
* `MODULE_CODE` must also be in the current schedule planner
* `SEMESTER` cannot be empty and must be an integer between 1-8 inclusive.

##### Note:
- We do not check for preclusion's.
  E.g. If you have completed/added CS2040, and the prerequisite of a module you are trying to shift is CS2040C,
  although they are preclusion's of each other, you are required to satisfy CS2040C! (It is something we hope to implement
  in the future!)

##### Example of usage:

User input:
`shift CS1010 2`

### Clear all schedule planner and completion data: `clear`
Deletes every module in the module schedule planner and their completion data. The user will be prompted to confirm this
action as this command cannot be undone.

##### Format: `clear`

##### Example of usage:

User input:
`clear`

* Expected outcome:

![img.png](photos/clear_outcome.png)

### Complete a module: `complete`
Completes a module (Completes a module in your schedule planner).

#### Note: 
- The module you complete has to be first added in your schedule planner!

- We do not check for preclusion's.
  E.g. If you have completed/added CS2040, and the prerequisite of a module you are trying to complete is CS2040C,
  although they are preclusion's of each other, you are required to satisfy CS2040C! (It is something
  we hope to implement in the future!)

##### Format: `complete MODULE_CODE`

##### Example of usage 1: (scenario where user's selected major is CEG)

User input:
`complete ma1511`

Expected outcome:
`Module Successfully Completed`

### Checking modules left: `left`
Displays the modules left, which are the modules required for the user's major that have not been completed.

##### Format: `left`

##### Example of usage 1: (major is CEG, no modules completed)

User input:
`left`

- Expected outcome:

![ss_left_ceg.png](screenshots%2Fss_left_ceg.jpeg)

##### Example of usage 2: (major is CEG, CS1010 & GEC1000 are added and completed)

User input:
`left`

- Expected outcome:

![ss_left_ceg_completed.png](screenshots%2Fss_left_ceg_completed.png)

### Check current pace to graduate: `pace`

Based on the modules that have been completed, t
The user can see how many MCs are left and how much time is left to complete the required MCs.

##### Format: `pace`

##### Note:
- If no argument is given, we will take the year that you have initially inputted.

- If an argument is given, we will take the academic year given and calculate the pace based on that.

- The current number of modular credits to complete is set to 160. However, this is something we do want to modify in 
future to cater to our double degree friends!

##### Example of Usage:

User input:
`pace y1/s1`

- Expected outcome: assuming 0 modular credits were done in semester one

![pace.jpg](photos%2Fpace.jpeg)

### Add module to schedule planner: `add`
Opens the user's personalized module schedule planner and adds the chosen module to the semester specified by the user.
Adding will not be allowed if the current schedule planner does not contain the required prerequisites.

##### Format: `add MODULE_CODE SEMESTER`

* `MODULE_CODE` cannot be empty and must be valid.
* `SEMESTER` cannot be empty and must be an integer between 1-8 inclusive.

##### Note:
- We do not check for preclusion's.
  E.g. If you have completed/added CS2040, and the prerequisite of a module you are trying to add is CS2040C,
  although they are preclusion's of each other, you are required to satisfy CS2040C! (It is something
  we hope to implement in the future!)


##### Example of usage:

User input:
`add CS1010 1`

- Expected outcome:
  ![ss_add_cs1010_1.png](screenshots%2Fss_add_cs1010_1.png)

### View Weekly Timetable: `timetable show`

Timetable view displays lectures, tutorials and classes (collectively referred to as lessons) 
for each module in the student's current semester. 

Format: `timetable show`

##### Example of usage:

Scenario 1: No lectures, tutorials or labs exist

User input:
`timetable show`

Expected outcome:
![ss_timetable_lessonNoExist.png](screenshots%2Fss_timetable_lessonNoExist.png)

Scenario 2: The lessons have been specified in Timetable Modify Mode
- CS2101 has a lecture at 12 for 2 hours on Monday
- GESS1000 has a lecture at 12 for 2 hours 
on Tuesday
- GESS1000 has a tutorial at 8 for 2 hours on Wednesday. 

User input:
`timetable show`

Expected outcome:
![ss_timetable_show_lessonExist.png](screenshots%2Fss_timetable_show_lessonExist.png)

### Modify Weekly Timetable: `timetable modify`

Add lectures, tutorials and classes (collectively referred to as lessons) for a specific module 
in the student's current semester. 
The current semester's modules (if any) will be displayed. Users can perform actions such as add and clear 
lessons in a module:

#### Format for subcommands:

`timetable modify` - To enter Timetable Modify Mode

`[MODULE CODE] [LECTURE / TUTORIAL / LAB] [TIME] [DURATION] [DAY]` -  Add a lesson
and specify its details (time, duration, day)
- lessonType: lecture, tutorial, lab
- startTime: integer from 8 to 20 (representing 8am to 8pm)
- duration: time in hours
- day: monday, tuesday, wednesday, thursday, friday, saturday, sunday

`[MODULE CODE] clear` - Clears all lessons of the selected module in current semester

`exit` - Exits Timetable Modify Mode and return to the main command loop

##### Note: 

- Each argument has to be separated by whitespace.
- Input for TIME must be an integer from 8 to 19 (representing 8am to 8pm)
- Input for DURATION must be at least 1
- Inputs for TIME and DURATION have to be int values and the sum must be 20 or lower 



##### Example of Usage: 

User input:
- `CS2101 LECTURE 12 2 MONDAY` 
- `GESS1000 LECTURE 12 2 TUESDAY`
- `GESS1000 TUTORIAL 8 2 WEDNESDAY`

![ss_timetable_show_lessonExist.png](screenshots%2Fss_timetable_show_lessonExist.png)

- `GESS1000 clear` 

![ss_timetablemodify_clear.png](screenshots%2Fss_timetablemodify_clear.png)

- `exit`

![ss_timetablemodify_exit.png](screenshots%2Fss_timetablemodify_exit.png)

### Save schedule and exit the program: `Bye`

Exit NUSDegs and save your current schedule into a data folder that will be in the same folder as
where you placed `duke.jar`. The data folder will be named using your name inputted at the start of the application.

##### Format: `Bye` (with a capital letter 'B')

##### Example of usage:

User input: `Bye`

- Expected outcome

![img.png](photos/Bye_outcome.png)



## FAQ

**Q**: How do I transfer my data to another computer? 

**A**: Currently, this feature is not included in NUSDegs.

**Q**: What do I need to run this application? 

**A**: Your computer requires **Internet Access and Java 11** to run the application. 
The operating system (Windows, macOS or Linux), doesn't matter.

**Q**: Would my data be saved after I close NUSDegs?

**A**: Yes. Currently, only your schedule planner will be saved inside a data folder that will be in the same folder as 
where you placed `duke.jar`. The data folder will be named using your name inputted at the start of the application.
To access the save folder next time you use NUSDegs, please use the same name which you used when you first saved the 
data folder.

**Q** Can I save multiple schedules in one computer?

**A** Yes. By using a different name at the start of NUSDegs, you can create another save folder to store another 
schedule.

**Q**: How is the `pace` function calculated?

**A**: Currently, we set it at a default 160Modular credits to graduate, 
however it is a feature we plan to include as to cater to double degree students!

## Command Summary
Note: if an argument is wrapped with `[]` it means that it is optional. 

| **Command**                                    | **Format**                        |
|------------------------------------------------|-----------------------------------|
| View Help                                      | `help`                            |
| View modules left for graduation               | `left`                            | 
| Check prerequisite for a module                | `prereq MODULE_CODE`              | 
| Search for modules based on keywords           | `search KEYWORD`                  | 
| View info about a module                       | `info description MODULE_CODE`    | 
| View modules required for major                | `required`                        | 
| View schedule planner                          | `schedule`                        | 
| Recommend a schedule based on major            | `recommend`                       | 
| Add module to schedule planner                 | `add MODULE_CODE SEMESTER_NUMBER` | 
| Delete module from schedule planner            | `delete MODULE_CODE`              |
| Shift module in schedule planner               | `shift MODULE_CODE SEMESTER`      |
| Clear all schedule planner and completion data | `clear`                           |
| Complete a module in your schedule planner     | `complete MODULE_CODE`            |
| Check current pace to graduate                 | `pace [CURRENT_SEMESTER]`         | 
| Modify weekly timetable                        | `timetable modify`                |
| Show weekly timetable                          | `timetable show`                  |
| Save schedule and exit the program             | `Bye`                             |

