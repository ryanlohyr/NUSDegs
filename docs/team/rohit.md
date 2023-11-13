# Rohit - Project Portfolio Page

## Overview

NUSDegs streamlines the process of degree planning for NUS computing students by offering personalized module
schedules, progress tracking, and ensuring on-time graduation. It eliminates guesswork, reduces stress, and saves time
for students. This is a comprehensive tool for efficient and successful degree completion.

### Code contributed: [Link to RepoSense Report](https://nus-cs2113-ay2324s1.github.io/tp-dashboard/?search=rohitcube&breakdown=false&sort=groupTitle%20dsc&sortWithin=title&since=2023-09-22&timeframe=commit&mergegroup=&groupSelect=groupByRepos&tabOpen=true&tabType=authorship&tabAuthor=rohitcube&tabRepo=AY2324S1-CS2113-T17-4%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

### Summary of Contributions

#### Features Implemented

#### 1. Search Feature Integration

**Feature:** Enables users to search the NUSModsAPI database by keywords in the module title.

**What it does:** This feature empowers users to efficiently search for modules based on specific keywords in the
module title.

**Justification:** The search functionality enhances the user experience by providing a convenient way for users to 
find modules that align with their interests or requirements.

#### 2. Info Description Feature Integration

**Feature:** Enables users to retrieve detailed descriptions of modules using their respective module codes.

**What it does:** This feature allows users to obtain comprehensive descriptions of modules by inputting 
the module code. It offers users more in-depth
information about specific modules they find interesting or relevant.

**Justification:** Users often seek more detailed information about modules, and this feature addresses
that need. This functionality enhances the user experience by providing valuable insights.

#### 3. Timetable Feature Integration

**Feature**: Displays the weekly timetable of the user.     

**What it does**: Using the lessons added by the user through Timetable Modify Feature, this feature displays the user's
lessons sorted by time and day.

**Justification**: This feature gives users a clear idea on how their current semester will look like on a weekly basis.
This will enable them to view their timetable easily.

**Credits**: I implemented the integration of the timetable feature into the main program by 
developing dedicated functions. @janelleenqi wrote the display and print functions for Timetable.

### Classes Implemented

#### 1. API Integration

- **Purpose:** The primary goal of this feature is to fetch and present information from the NUSModsAPI to the user.

- **Highlights:** Implementing this feature posed a significant challenge, requiring knowledge in API integration.
The process involved incorporating external dependencies to handle JSON files, necessitating the conversion of
data into a readable format for user display. Learnt how to establish connections and send requests to APIs.

#### 2. ModuleWeekly Class

- **Purpose:** The ModuleWeekly class serves the crucial purpose of storing information related to modules 
scheduled in the current semester, specifically those displayed in the weekly timetable.

- **Highlights:**  The ModuleWeekly 
class enhances the program's ability to manage and display
module-specific details within the context of the current semester.

#### 3. Timetable Parser and Associated Functions

- **Purpose:** The Timetable Parser, along with its associated functions (in Parser), is designed 
with the primary objective of parsing user arguments. 

- **Highlights:** This parsing facilitates
  the input of lessons for each module into the weekly timetable.

### Enhancements Implemented

#### 1. Refactored parser functions 
Purpose: Rewrote this function into happy path and made the exception class for it.
Credits: @ryanlohyr for providing the psuedocode which I implemented

###  Code Omitted 

#### 1. Info Feature

The Info feature was initially intended to incorporate additional user commands, such as the `getWorkload` function.
However, after careful consideration, it was deemed irrelevant for user interactions. Another function,
`listAllModules()`, initially excluded from user access, found its place within the Search function.

#### 2. Module Duration Retrieval

Originally, the `moduleWeekly` class included a `getDuration()` function meant to automatically update
the `ModuleWeekly` elements. This concept was abandoned in favor of a more user-friendly approach, requiring
users to input module durations as arguments when creating a class.

### Contributions beyond the project team:

- Reported 15 bugs for PE-D 
- Helped another team debug their jar before final submission

### Contribution to UG:

- Added descriptions for info, search, timetable show and timetable modify

### Contributions to DG:

- Added Target user profile and value proposition

![tt_modify_seq_diag.png](..%2Fdiagrams%2Ftt_modify_seq_diag.png)

![ss_logicDiagram.jpg](screenshots%2Fss_logicDiagram.jpg)
