# Developer Guide

## Acknowledgements

{list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

## Design & implementation

{Describe the design and implementation of the product. Use UML diagrams and short code snippets where applicable.}
## Features
- ### Pace Feature
# Proposed Implementation - Pacing and MC Calculation

The proposed "Pacing and MC Calculation" mechanism is implemented to help users track their academic progress and remaining Modular Credits (MCs) required for graduation. This feature is facilitated by the PacingManager, which stores user data and provides functions for calculating the recommended pacing and remaining MCs. The following operations are available:

- PacingManager#calculateRemainingMCs() — Calculates the remaining MCs required for graduation.
- PacingManager#calculateRecommendedPace() — Recommends the pacing for upcoming semesters.

These operations are exposed in the Pacing interface as Pacing#calculateRemainingMCs() and Pacing#calculateRecommendedPace() respectively.

## Usage Examples

Here are a few examples of how the "Pacing and MC Calculation" feature behaves:

### Example 1: Calculate Remaining MCs

Command: `pace Y2/S1` (assuming that the user has completed 60 MCs from Y1S1 to Y2S1)

Response:
You currently have 100 MCs left until graduation.

### Example 2: Calculate Remaining MCs (No Semester Specified)

Command: `pace`

Response:
You currently have 100 MCs left until graduation.
- ### Feature 2

## Product scope
### Target user profile

{Describe the target user profile}

### Value proposition

{Describe the value proposition: what problem does it solve?}

## User Stories

|Version| As a ... | I want to ... | So that I can ...|
|--------|----------|---------------|------------------|
|v1.0|new user|see usage instructions|refer to them when I forget how to use the application|
|v2.0|user|find a to-do item by name|locate a to-do without having to go through the entire list|

## Non-Functional Requirements

{Give non-functional requirements}

## Glossary

* *glossary item* - Definition

## Instructions for manual testing

{Give instructions on how to do a manual product testing e.g., how to load sample data to be used for testing}
