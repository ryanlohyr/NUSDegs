//        if (userInput.equals("Y")) {
//            displayMessage("Do you want to keep your completion statuses?");
//            displayMessage("Please input 'Y' or 'N'");
//
//            String userInputForCompletion = in.nextLine();
//
//            while (!userInputForCompletion.equals("N") && !userInputForCompletion.equals(("Y"))) {
//                displayMessage("Invalid input, please choose Y/N");
//                userInputForCompletion = in.nextLine();
//            }
//
//            displayMessage("Hold on, this may take a while......");
//
//            Boolean keep;
//            if (userInputForCompletion.equals("Y")) {
//                keep = true;
//            } else {
//                keep = false;
//            }
//            student.getSchedule().addRecommendedScheduleListToSchedule(scheduleToAdd, keep);
//            displayMessage("Here is your schedule planner!");
//            student.getSchedule().printMainModuleList();
//            displayMessage("Happy degree planning!");
//
//
//        } else {
//            displayHelp();
//        }
      /*String logo = "  _____       _                 __  __        _____      _              _       _      \n"
                + " |  __ \\     | |               |  \\/  |      / ____|    | |            | |     | |     \n"
                + " | |  | | ___| |__  _   _  __ _| \\  / |_   _| (___   ___| |__   ___  __| |_   _| | ___ \n"
                + " | |  | |/ _ \\ '_ \\| | | |/ _` | |\\/| | | | |\\___ \\ / __| '_ \\ / _ \\/ _` | | | | |/ _ \\\n"
                + " | |__| |  __/ |_) | |_| | (_| | |  | | |_| |____) | (__| | | |  __/ (_| | |_| | |  __/\n"
                + " |_____/ \\___|_.__/ \\__,_|\\__, |_|  |_|\\__, |_____/ \\___|_| |_|\\___|\\__,_|\\__,_|_|\\___|\n"
                + "                           __/ |        __/ |                                          \n"
            //    private void processCommand(String command, String[] arguments, String userInput) {
    //        switch (command) {
    //        case UserCommandWord.LEFT_COMMAND: {
    //            showModulesLeft(student.getModuleCodesLeft());
    //            break;
    //        }
    //        case UserCommandWord.PACE_COMMAND: {
    //            computePace(arguments, student.getCurrentModuleCredits(), student.getYear());
    //            break;
    //        }
    //        case UserCommandWord.PREREQUISITE_COMMAND: {
    //            String module = arguments[0];
    //            determinePrereq(module.toUpperCase(), student.getMajor()); //to convert "CEG" to dynamic course
    //            break;
    //        }
    //        case UserCommandWord.RECOMMEND_COMMAND: {
    //            recommendScheduleToStudent(student);
    //            break;
    //        }
    //        case UserCommandWord.ADD_MODULE_COMMAND: {
    //            String module = arguments[0].toUpperCase();
    //            int targetSem = Integer.parseInt(arguments[1]);
    //
    //            addModule(module, targetSem, student);
    //            break;
    //        }
    //        case UserCommandWord.DELETE_MODULE_COMMAND: {
    //            String module = arguments[0].toUpperCase();
    //
    //            deleteModule(module,student);
    //            break;
    //        }
    //        case UserCommandWord.SHIFT_MODULE_COMMAND: {
    //            String module = arguments[0].toUpperCase();
    //            int targetSem = Integer.parseInt(arguments[1]);
    //
    //            shiftModule(module, targetSem, student);
    //            break;
    //        }
    //        case UserCommandWord.VIEW_SCHEDULE_COMMAND: {
    ////            getStudentSchedule();
    //            student.printSchedule();
    //            break;
    //        }
    //        case UserCommandWord.COMPLETE_MODULE_COMMAND: {
    //            String module = arguments[0].toUpperCase();
    //            //to add to user completed module
    //            completeModule(student, module);
    //
    //            break;
    //        }
    //        case UserCommandWord.REQUIRED_MODULES_COMMAND: {
    //            getRequiredModulesForStudent(student.getMajor());
    //            break;
    //        }
    //        case UserCommandWord.INFO_COMMAND: {
    //            Api.infoCommands(arguments[0], userInput);
    //            break;
    //        }
    //        case UserCommandWord.SEARCH_MODULE_COMMAND: {
    //            Api.searchCommand(userInput);
    //            break;
    //        }
    //        case UserCommandWord.HELP_COMMAND: {
    //            printListOfCommands(commandManager);
    //            break;
    //        }
    //        case UserCommandWord.TIMETABLE_COMMAND: {
    //            student.timetableShowOrModify(student, userInput);
    //            break;
    //        }
    //        default: {
    //            break;
    //        }
    //        }
    //
    //    }    + "                          |___/        |___/                                           ";*/

//    /**
//     * Processes a course file, extracts relevant information, and returns a list of course codes.
//     *
//     * @param f The file to be processed.
//     * @return An ArrayList of course codes extracted from the file.
//     * @throws FileNotFoundException If the specified file is not found.
//     */
//    private static ArrayList<String> processCourseFile(File f) throws FileNotFoundException {
//        ArrayList<String> currentArray = new ArrayList<>();
//        Scanner s = new Scanner(f);
//        while (s.hasNext()) {
//            String currentLine = s.nextLine();
//
//            String[] words = currentLine.split(" ");
//
//            if (!currentLine.isEmpty() && !currentLine.startsWith("*")) { // not empty line, not title
//                currentArray.add(words[0]);
//            }
//        }
//        return currentArray;
//    }

//
//    /**
//     * Add all mods that require prerequisites to a map storing the mod and a set of preqs
//     *
//     * @param list HashMap of ModsWithPreqs
//     * @return HashMap of Mods with their corresponding preqs
//     */
//
//    private HashMap<String, List<String>> addModsWithPreqs(HashMap<String, List<String>> list) {
//        //Only two mods don't have preqs MA1511 and CS1231S.
//        // In the future this will be dealt
//        addValue(list, "CS3230", "CS2030S");
//        addValue(list, "CS3230", "CS1231S");
//
//        addValue(list, "CS2030S", "CS1231S");
//
//        addValue(list, "CS2040S", "CS1231S");
//
//        addValue(list, "CS2106", "CS1231S");
//
//        addValue(list, "CS2109S", "CS1231S");
//
//        return list;
//    }
//
//
//    /**
//     * Adds a value to a HashMap with a list of values associated with a key.
//     * If the key does not exist in the map, it creates a new key-value pair with an empty list.
//     * The value is added to the list associated with the key.
//     *
//     * @param map   The HashMap in which the value will be added.
//     * @param key   The key to which the value will be associated.
//     * @param value The value to add to the list.
//     */
//    public static void addValue(HashMap<String, List<String>> map, String key, String value) {
//        // If the map does not contain the key, put an empty list for that key
//        if (!map.containsKey(key)) {
//            map.put(key, new ArrayList<>());
//        }
//        // Add the value to the list associated with the key
//        map.get(key).add(value);
//    }

//        completedModules.deleteModulebyCode(module);
//        int nextSemStartingIndex = moduleCount;
//
//        int lastModuleIndex = modulesPlanned.getMainModuleList().size() - 1;
//        List<String> completedModulesArray = modulesPlanned.getModuleCodes().subList(0, nextSemStartingIndex);
//        ModuleList completedModules = new ModuleList(String.join(" ", completedModulesArray));
//        completedModules.deleteModulebyCode(module);
//
//        List<String> modulesAheadArray;
//        try {
//       modulesAheadArray = modulesPlanned.getModuleCodes().subList(nextSemStartingIndex, lastModuleIndex + 1);
//        } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
//            modulesAheadArray = new ArrayList<>();
//        }
//
//        try {
//            for (String moduleAhead : modulesAheadArray){
//                if (!satisfiesAllPrereq(moduleAhead, completedModules)) {
//                   throw new FailPrereqException("Unable to delete module. This module is a prerequisite for "
//                            + moduleAhead);
//                }
//            }
//        } catch (IllegalArgumentException e) {
//            // This catch should never occur as it should not be possible to add an invalid module
//            assert false;
//            throw new IllegalArgumentException("Invalid Module in Schedule");
//        }

//        modulesPerSem[targetSem - 1] -= 1;
//
    /*Sebestians version
    private static boolean isInternetReachable() {
        try {
            // Try connecting to a well-known server (Google's DNS server)
            InetAddress address = InetAddress.getByName("8.8.8.8");
            return address.isReachable(3000); // 3 seconds timeout
        } catch (java.io.IOException e) {
            return false; // Unable to connect
        }
    }
    */