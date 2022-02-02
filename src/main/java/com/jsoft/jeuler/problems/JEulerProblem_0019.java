package com.jsoft.jeuler.problems;

import com.jsoft.jeuler.solver.EulerSolver;

import java.util.List;

public class JEulerProblem_0019 extends EulerSolver {

    public JEulerProblem_0019(int problemNumber) {
        super(problemNumber);
    }

    @Override
    public String solve() {

        int[] months = {31, 28, 31, 30, 31, 30,
                        31, 31, 30, 31, 30, 31};
        int feb_in_leap_year = 29;

        //7th Jan 1900 is sunday
        int current_sunday_date = 7;

        int no_of_sundays = 0;

        for(int year=1900; year<=2000; year++) {
            for(int month=0; month<12; month++) {

                if(year > 1900 && current_sunday_date == 1) {
                    no_of_sundays++;
                }

                int noOfDays = months[month];
                if(month == 1 && isLeapYear(year)) {
                    noOfDays = feb_in_leap_year;
                }

                int rem = noOfDays % 7;
                if (current_sunday_date > rem) {
                    current_sunday_date -= rem;
                } else {
                    current_sunday_date += 7;
                    current_sunday_date -= rem;
                }

                //System.out.printf("Sunday in month %d is %d\n", month+1, current_sunday_date);
            }
        }

        if(current_sunday_date == 1) {
            no_of_sundays++;
        }

        return Integer.toString(no_of_sundays);
    }

    private boolean isLeapYear(int year) {
        if(year % 400 == 0) {
            return true;
        } else if(year % 100 == 0) {
            return false;
        } else if(year % 4 == 0) {
            return true;
        }

        return false;
    }

    @Override
    public String getProblemStatement() {
        return "How many Sundays fell on the first of the month during the twentieth century (1 Jan 1901 to 31 Dec 2000)?";
    }

    @Override
    public List<String> getTags() {
        return null;
    }
}
