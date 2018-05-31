package com.hughes.basic.array;

import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Hughes on 2016/8/9.
 */
public class LoopPerformance {

    public static void main(String[] argv) {

        String sArray[] = createArray();

        // convert array to list
        List lList = Arrays.asList(sArray);
        String stemp;

        int iListSize = lList.size();

        //Forward Loop Testing
        System.out.println("\n--------- Forward Loop --------\n");
        long lForwardStartTime = new Date().getTime();
        System.out.println("Start: " + lForwardStartTime);

        //for loop
        for (int i = 0; i < iListSize; i++) {
            stemp = (String) lList.get(i);
        }

        long lForwardEndTime = new Date().getTime();
        System.out.println("End: " + lForwardEndTime);

        long lForwardDifference = lForwardEndTime - lForwardStartTime;
        System.out.println("Forward Looping - Elapsed time in milliseconds: " + lForwardDifference);

        //Reverse Loop Testing
        System.out.println("\n--------- Reverse Loop --------\n");
        long lReverseStartTime = new Date().getTime();
        System.out.println("Start: " + lReverseStartTime);

        //for loop
        for (int i = iListSize - 1; i > 0; i--) {
            stemp = (String) lList.get(i);
        }

        long lReverseEndTime = new Date().getTime();
        System.out.println("End: " + lReverseEndTime);

        long lReverseDifference = lReverseEndTime - lReverseStartTime;
        System.out.println("For - Elapsed time in milliseconds: " + lReverseDifference);

        ///////////////////////////////

        System.out.println("\n--------- Iterator Loop -------\n");
        long lIteratorStartTime = new Date().getTime();
        System.out.println("Start:\t" + lIteratorStartTime);

        // iterator loop
        Iterator<String> iterator = lList.iterator();
        while (iterator.hasNext()) {
            stemp = iterator.next();
        }
        long lIteratorEndTime = new Date().getTime();
        System.out.println("End:\t" + lIteratorEndTime);

        long lIteratorDifference = lIteratorEndTime - lIteratorStartTime;
        System.out.println("Iterator - Elapsed time in milliseconds: " + lIteratorDifference);

        System.out.println("\n--------- For Loop --------\n");
        long lForStartTime = new Date().getTime();
        System.out.println("Start:\t" + lForStartTime);

        // for loop
        for (int i = 0; i < lList.size(); i++) {
            stemp = (String) lList.get(i);
        }

        long lForEndTime = new Date().getTime();
        System.out.println("End:\t" + lForEndTime);

        long lForDifference = lForEndTime - lForStartTime;
        System.out.println("For - Elapsed time in milliseconds: " + lForDifference);

        System.out.println("\n--------- While Loop -------\n");
        long lWhileStartTime = new Date().getTime();
        System.out.println("Start:\t" + lWhileStartTime);

        // while loop
        int j = 0;
        while (j < lList.size()) {
            stemp = (String) lList.get(j);
            j++;
        }
        long lWhileEndTime = new Date().getTime();
        System.out.println("End:\t" + lWhileEndTime);

        long lWhileDifference = lWhileEndTime - lWhileStartTime;
        System.out.println("While - Elapsed time in milliseconds: " + lWhileDifference);
    }

    static String[] createArray() {
        int size = 200_000_000;
        String sArray[] = new String[size];
        for (int i = 0; i < size; i++) {
            sArray[i] = "H";
        }
        return sArray;
    }
}