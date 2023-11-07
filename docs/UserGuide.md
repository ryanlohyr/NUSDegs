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
- View help : `help`
- View modules left for graduation: `left`
- Check prerequisite for a module: `prereq`
- Search for modules based on keywords: `search`
- View info about a module: `info`
- View modules required for major: `required`
- View schedule planner: `schedule`
- Recommend a schedule based on major: `recommend`
- Add module to schedule planner: `add`
- Delete module from schedule planner: `delete`
- Complete a module in your schedule planner: `complete`
- Check current pace to graduate: `pace`

### Viewing help: `help`

To view a list of all possible commands, a brief description of their functionality and syntax.

Format: `help`

### Checking modules left: `left`
Displays the modules left, which are the modules required for the user's major that have not been completed.

Format: `left`

Example of usage 1: (scenario where user's selected major is CEG)

User input: 
`left`

- Expected outcome:
![ss_left_ceg.png](screenshots%2Fss_left_ceg.jpeg)

### View module prerequisites:`prereq`
Based on the module selected, we will show what prerequisites the course has.

##### Note:
- Since NUS has the concept of preclusions, when prerequisites are shown, it is shown based on the degree of the current 
user.

- If the module is not a requisite of the students major, we will only show one preclusion as a prerequisite 

Format: `prepreq MODULE_CODE`

The input is not case sensitive. E.g eg1311 or EG1311 is shown
out

User input
`prepreq eg1311` 

Assuming the user is from Computer Engineering

![ss_left_ceg.png](screenshots%2Fss_prereq.jpeg)


### Searching for a module by title: `search`
Search for module title using a keyword.

Format: `search n/KEYWORD`

* The `KEYWORD` cannot be empty.

Example of usage:

`search n/Darwinian`

- Expected outcome:
  These are the modules that contain your keyword in the title:

### Get information about a module: `info`
Get information about a module using the info command, followed by one of the commands 'description', 'workload'
and 'all'.

Format: `info n/command n/moduleCode`


* The `command` cannot be empty.
* The `moduleCode` cannot be empty.

Example of usage:

`info description CS2113`

- Expected outcome: This course introduces the necessary skills for systematic and rigorous development of software 
systems. It covers requirements, design, implementation, quality assurance, and project management aspects of small-to-medium size multi-person software projects. The course uses the Object Oriented Programming paradigm. Students of this course will receive hands-on practice of tools commonly used in the industry, such as test automation tools, build automation tools, and code revisioning tools will be covered.

`info workload CS2113`

- Expected outcome: "[2,1,0,3,4]"

`info all`

- Expected outcome: Displays module title and module code of all available modules

### Getting a list of required modules:`required`
Get an overview of required modules for the user's major

Format: `required`

Example of usage 1: (scenario where user's selected major is CEG)

User input:
`required`

- Expected outcome:
![ss_required_ceg.png](screenshots%2Fss_required_ceg.png)
![ss_required_ceg2.png](screenshots%2Fss_required_ceg2.png)


### View schedule planner: `schedule`
to be addedd

### View recommended schedule based on course: `recommend`
Based on the course, we will provide an recommended schedules that is sorted based on prerequisites.

Format: `recommend MAJOR_CODE`

The input is not case-sensitive. E.g. CEG or ceg is shown (Currently only works for CEG)

User input:
`recommend`

Expected outcome:
![recommendedSchedule.jpg](photos%2FrecommendSchedule.jpeg)

If the user enters `Y`, the recommended schedule will be added to their schedule
![recommendedSchedule.jpg](screenshots%2Fadd_recommend.jpeg)


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

### Complete a module: `complete`
Completes a module (Completes a module in your schedule planner).

Note: the module you complete has to be first added in your schedule planner! 

Format: `complete`

Example of usage 1: (scenario where user's selected major is CEG)

User input:
`complete ma1511`

Expected outcome:
`Mod completed: MA1511`

### Check current pace to graduate: `pace`

Based on the modules that have been completed, t
The user can see how many MCs are left and how much time is left to complete the required MCs.

Format: `pace`

if no argument is given, we will take the year that you have initially inputted.
User input

if an argument is given, we will take the academic year given and calculate the pace based on that.

User input:
`pace y1/s1`

Expected outcome: assuming 0MCs were done in semester one
![pace.jpg](photos%2Fpace.jpeg)

## FAQ

**Q**: How do I transfer my data to another computer? 

**A**: Currently, this feature is not included in NUSDegs.

## Command Summary
Note: if a argument is wrapped with `[]` it means that it is optional. 

| **Command**                                | **Format**                            |
|--------------------------------------------|---------------------------------------|
| View Help                                  | `help`                                |
| View modules left for graduation           | `left`                                | 
| Check prerequisite for a module            | `prereq <MODULE_CODE>`                | 
| Search for modules based on keywords       | `placeholder`                         | 
| View info about a module                   | `placeholder`                         | 
| View modules required for major            | `required`                            | 
| View schedule planner                      | `schedule`                            | 
| Recommend a schedule based on major        | `recommend`                           | 
| Add module to schedule planner             | `add <MODULE_CODE> <SEMESTER_NUMBER>` | 
| Delete module from schedule planner        | `delete <MODULE_CODE>`                |
| Complete a module in your schedule planner | `complete <MODULE_CODE>`              |
| Check current pace to graduate             | `pace [<MODULE_CODE>]`                | 

