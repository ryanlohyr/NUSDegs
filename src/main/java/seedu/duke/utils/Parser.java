package seedu.duke.utils;

public class Parser {

    //we need to consider if
    public boolean isValidAcademicYear(String userInput ) {
        try {
            String[] parts = userInput.split("/");
            if(parts.length != 2){
                throw new IllegalArgumentException("Needs to be in format of Y2/S1");
            }
            String year = parts[0].toUpperCase();
            String semester = parts[1].toUpperCase();

            //last year
            if(year.equals("Y4") && semester.equals("S2")){
                throw new IllegalArgumentException("Its your last sem!! A bit too late ya....");
            }
            //validate semester
            if(!semester.equals("S1") && !semester.equals("S2")){
                throw new IllegalArgumentException("Invalid Semester");
            }

            //validate year
            if (!(year.equals("Y1") || year.equals("Y2") || year.equals("Y3") || year.equals("Y4"))) {
                // The input is not "Y1," "Y2," "Y3," or "Y4"
                throw new IllegalArgumentException("Invalid Year");
            }
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

    }



}
