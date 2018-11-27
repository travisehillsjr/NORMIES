package com.company;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    static ArrayList<String> code = new ArrayList<String>();
    static ArrayList<Double> lat = new ArrayList<Double>();
    static ArrayList<Double> lon = new ArrayList<Double>();
    static ArrayList<points> p = new ArrayList<points>();
    static ArrayList<Double> ANS = new ArrayList<Double>();

    static ArrayList<bestInPathSet> BEST = new ArrayList<>();
    static ArrayList<keepAllPaths> ALLPATHS = new ArrayList<>();
    static ArrayList<xvalues> infoin = new ArrayList<>();

    static Integer keepcount = 0;
    static Integer AllPathsKeepcount = 0;

    static String testStr1 = "Test1";
    static Double testdou2 = 5.655;
    static Double testdou3 = 48.644;

    private void setvalues(){
        points one = new points();
    }

    private static returnedLATLONG scan(String CODEIN){
        returnedLATLONG FAIL = new returnedLATLONG();
        //Get scanner instance
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File("SupportFiles/flights.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Set the delimiter used in file
        scanner.useDelimiter(",");

        //Get all tokens and store them in some data structure
        //I am just printing them
        scanner.reset();
        while (scanner.hasNextLine()){

            while (scanner.hasNext())
            {
                String current = scanner.next();
                String[] currentArray = current.split(",");

                if (Arrays.asList(currentArray).contains(CODEIN)){
                    returnedLATLONG vals = new returnedLATLONG();
                    vals.CODE = CODEIN;
                    vals.Lat = Double.parseDouble(currentArray[3]);
                    vals.Longi = Double.parseDouble(currentArray[4]);
                    return vals;
                }

//                    if (checkOnThis1 == CODEIN){
//                        returnedLATLONG vals = new returnedLATLONG();
//                        vals.CODE = CODEIN;
//                        vals.Lat = Double.parseDouble(currentArray[1]);
//                        vals.Longi = Double.parseDouble(currentArray[2]);
//                        System.out.printf(" | %s |", currentArray[i]);
//                        return vals;
//                    }
                //System.out.printf("hhhh");

                System.out.printf("\n");

            }
        }
        //Do not forget to close the scanner
        scanner.close();
        //System.out.printf("****************************\n\n\n\n");
        return FAIL;
    }

    private static void scanThatStuff(){
        int rowCounter = 0;
        int posCounter = 0;

        //Get scanner instance
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File("SupportFiles/flights.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Set the delimiter used in file
        scanner.useDelimiter(",");

        //Get all tokens and store them in some data structure
        //I am just printing them
        scanner.reset();
        while (scanner.hasNextLine()){

            while (scanner.hasNext())
            {
                xvalues x = new xvalues();
                String current = scanner.next();
                String[] currentArray = current.split(",");
                if (rowCounter != 0 ){
                    x.rowvalue = rowCounter;
                    for(int i = 0; i < currentArray.length-1; i++){
                        x.info.add(posCounter, currentArray[i + 1]);
                    }

                }
                //System.out.printf("\n");
                //System.out.print(current + "|");
                infoin.add(rowCounter, x);
                rowCounter++;
                posCounter = 0;
            }
        }
        //Do not forget to close the scanner
        scanner.close();
        //System.out.printf("****************************\n\n\n\n");
    }

    private static void getvalues(){

        Boolean conditional = true;
        String Str1;
        Double dou2;
        Double dou3;

        Scanner sc = new Scanner(System.in);
        for (;;) {
            returnedLATLONG codeNameRes = new returnedLATLONG();
            points one = new points();
            System.out.println("Please enter Airport Code: ");
            Str1 = sc.next();
            codeNameRes = scan(Str1);
            code.add(codeNameRes.CODE);
            lat.add(codeNameRes.Lat);
            lon.add(codeNameRes.Longi);

            one.code = codeNameRes.CODE;
            one.Longi = codeNameRes.Longi;
            one.Lat = codeNameRes.Lat;

//            System.out.println("Please enter Airport Code: ");
//            Str1 = sc.next();
//            code.add(Str1);
//
//            System.out.println("Please enter LAT: ");
//            dou2 = sc.nextDouble();
//            lat.add(dou2);
//
//            System.out.println("Please enter LONG: ");
//            dou3 = sc.nextDouble();
//            lon.add(dou3);

//            one.code = Str1;
//            one.Longi = dou3;
//            one.Lat = dou2;



            p.add(one);

            keepcount++;
            System.out.println("Enter Another (Y/N): ");
            String termValue = sc.next();
            if(!termValue.equals("Y")){
                break;
            }
        }
    }
    private static void viewvalues(){
        for(int i = 0; i < keepcount; i++){
            points getpoint = p.get(i);
            System.out.format("%32s %20f %20f \n", getpoint.code, getpoint.Lat, getpoint.Longi);
        }
    }
    private static void viewvaluesForOneD(){

        for(int i = 0; i < keepcount; i++){
            points getpoint = p.get(i);
            System.out.printf("Distances from %s \n", getpoint.code);
            for(int j = 0; j < keepcount; j++){
                System.out.format("To %s = %20f \n",p.get(j).code, getpoint.distPoints.get(j));
            }
        }
    }

    public static void printAllRecordedPaths(){
        int spacingCount = 0;
        for (int i = 0; i < ALLPATHS.size(); i++){
            System.out.printf("Start & End: %d   ", ALLPATHS.get(i).startEnd);
            for(int j = 0; j < ALLPATHS.get(1).pointOrder.size(); j++){
                System.out.printf("| %d ", ALLPATHS.get(i).pointOrder.get(j));
            }
            System.out.printf(" Distance: %f", ALLPATHS.get(i).distance);

            spacingCount++;
            if(spacingCount == p.size() - 1){
                System.out.printf("\n");
                spacingCount = 0;
            }

            System.out.printf("\n");
        }
    }

    public static void printDataSet(){
        for(int i = 0; i < infoin.size(); i++){
            System.out.printf("%d: ", infoin.get(i).rowvalue);
            for(int j = 0; j < infoin.get(i).info.size(); j++){
                System.out.printf(" | %10s", infoin.get(i).info.get(j));
            }
            System.out.printf("\n");
        }
    }
    /*::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::                                                                         :*/
    /*::  This routine calculates the distance between two points (given the     :*/
    /*::  latitude/longitude of those points). It is being used to calculate     :*/
    /*::  the distance between two locations using GeoDataSource (TM) prodducts  :*/
    /*::                                                                         :*/
    /*::  Definitions:                                                           :*/
    /*::    South latitudes are negative, east longitudes are positive           :*/
    /*::                                                                         :*/
    /*::  Passed to function:                                                    :*/
    /*::    lat1, lon1 = Latitude and Longitude of point 1 (in decimal degrees)  :*/
    /*::    lat2, lon2 = Latitude and Longitude of point 2 (in decimal degrees)  :*/
    /*::    unit = the unit you desire for results                               :*/
    /*::           where: 'M' is statute miles (default)                         :*/
    /*::                  'K' is kilometers                                      :*/
    /*::                  'N' is nautical miles                                  :*/
    /*::  Worldwide cities and other features databases with latitude longitude  :*/
    /*::  are available at https://www.geodatasource.com                         :*/
    /*::                                                                         :*/
    /*::  For enquiries, please contact sales@geodatasource.com                  :*/
    /*::                                                                         :*/
    /*::  Official Web site: https://www.geodatasource.com                       :*/
    /*::                                                                         :*/
    /*::           GeoDataSource.com (C) All Rights Reserved 2018                :*/
    /*::                                                                         :*/
    /*::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        }
        else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            if (unit == "K") {
                dist = dist * 1.609344;
            } else if (unit == "N") {
                dist = dist * 0.8684;
            }
            return (dist);
        }
    }


    public static void getdistances(){
        for(int i = 0; i < keepcount; i++){
            points one = new points();
            points to = new points();
            one = p.get(i);
            for (int j = 0; j < keepcount; j++){
                to = p.get(j);
                one.distPoints.add(distance(one.Lat,  one.Longi, to.Lat, to.Longi, "M"));
            }

        }
    }



    public static void SERIAL_ANSManager(){
        for (int i = 0; i < p.size(); i++){
            calculate(i);
        }
    }

    public static void calculate(int Iin){
        double addands = 0;
        int lastPoint = Iin;
        int printPathList = 0;
        int externalcounter = 0;
        ArrayList<Integer> trackints = new ArrayList<Integer>();
        for (int j = 0; j <= keepcount-1; j++){
            if(j != Iin){
                trackints.add(j);
            }
        }

        for (int y = 0; y < keepcount-1; y++){
            System.out.printf(" %d,  ", trackints.get(y));
        }
        System.out.printf("\n");

        for (int n = 0; n < keepcount - 1; n++){
            addands = 0;
            externalcounter++;
            lastPoint = Iin;
            if (n != keepcount - 2){
                System.out.printf("System Did Not Reach Last Iteration \n");
            }
            for (int i = 0; i < keepcount-1; i++){
                points one = new points();
                one = p.get(trackints.get(i));
                addands = addands + one.distPoints.get(lastPoint);
                System.out.printf("added number: %f \n", addands);
                lastPoint = trackints.get(i);
            }
            points one = new points();
            one = p.get(Iin);
            System.out.printf("last dist: %f \n", one.distPoints.get(lastPoint));
            addands = addands + one.distPoints.get(lastPoint);

            System.out.printf("Ans : %f \n\n\n", addands);
            ANS.add(n, addands);

            keepAllPaths ThisPath = new keepAllPaths();
            ThisPath.startEnd = Iin;
            for (int i = 0; i < trackints.size(); i++){
                ThisPath.pointOrder.add(i, trackints.get(i));
            }
            ThisPath.distance = addands;
            ALLPATHS.add(AllPathsKeepcount, ThisPath);
            AllPathsKeepcount++;

            //Not Working/////////////// Working on this
            for(int x = 0; x <= keepcount-2; x++){
                int cval = trackints.get(x);
                cval = cval + 1;
                for (int r = 0; r <= x; r++){
                    if (x != 0){
                        int oldvalcheck = trackints.get(r);
                        if (cval > keepcount-1){
                            cval = 0;
                        }
                        if (cval == oldvalcheck){
                            cval++;
                        }


                    }
                    if (cval == Iin){
                        cval++;
                    }
                }
                trackints.set(x, (cval));
            }


            if(externalcounter < keepcount-1){
                for (int y = 0; y < keepcount-1; y++){
                    System.out.printf(" %d,  ", trackints.get(y));
                }
                System.out.printf("\n");
            }

        }

        double shortest = returnShort();


    }

    public static double returnShort(){
        double shortest = 0;
        double current = ANS.get(0);
        for (int i = 0; i < ANS.size(); i++){
            if (ANS.get(i) < current){
                current = ANS.get(i);
                shortest = i;
            }
        }
        return shortest;
    }

    public static void setTestValues(){


        points zero = new points();
        zero.code = "A";
        zero.Longi = 82.467507;
        zero.Lat = 27.957801;

        points one = new points();
        one.code = "B";
        one.Longi = 82.448117;
        one.Lat = 27.958332;

        points two = new points();
        two.code = "C";
        two.Longi = 82.447688;
        two.Lat = 27.946201;

        points three = new points();
        three.code = "D";
        three.Longi = 82.461673;
        three.Lat = 27.946580;

        points four = new points();
        four.code = "E";
        four.Longi = 82.478660;
        four.Lat = 27.951964;



        p.add(zero);
        p.add(one);
        p.add(two);
        p.add(three);
        p.add(four);

        keepcount = 5;
    }


    public static void main(String[] args) {
        // write your code here

        System.out.println("Traveling Salesman Problem");
        //scanThatStuff();
        //printDataSet();
        getvalues();
        //setTestValues();
        viewvalues();
        getdistances();
        viewvaluesForOneD();
        //calculate(0);
        //double shortest = returnShort();
        //System.out.printf("\n\n\n This is the shortest path: %f \n", shortest);
        SERIAL_ANSManager();
        printAllRecordedPaths();
    }


    static class points{
        String code;
        double Longi;
        double Lat;
        ArrayList<Double> distPoints = new ArrayList<Double>();
    }

    static class bestInPathSet{
        String startEnd;
        ArrayList<String> Route = new ArrayList<>();
        double distance;
    }

    static class keepAllPaths{
        int startEnd;
        double distance;
        ArrayList<Integer> pointOrder = new ArrayList<Integer>();
    }

    static class xvalues{
        int rowvalue;
        ArrayList<String> info = new ArrayList<String>();

    }

    static class returnedLATLONG{
        String CODE;
        double Longi;
        double Lat;
    }
}
