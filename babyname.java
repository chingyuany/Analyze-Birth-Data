
/**
 * Write a description of babyname here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import org.apache.commons.csv.*;
import edu.duke.*;
import java.io.*;
public class babyname {
    public void printNames () {
        FileResource fr = new FileResource();
        for (CSVRecord rec : fr.getCSVParser(false)) {
            int numBorn = Integer.parseInt(rec.get(2));
            if (numBorn <= 100) {
                System.out.println("Name " + rec.get(0) +
                           " Gender " + rec.get(1) +
                           " Num Born " + rec.get(2));
            }
        }
    }

    public void totalBirths (FileResource fr) {
        int totalBirths = 0;
        int totalBoys = 0;
        int totalGirls = 0;
        int boycount =0;
        int girlcount =0;
        for (CSVRecord rec : fr.getCSVParser(false)) {
            int numBorn = Integer.parseInt(rec.get(2));
            totalBirths += numBorn;
            if (rec.get(1).equals("M")) {
                totalBoys += numBorn;
                boycount++;
            }
            else {
                totalGirls += numBorn;
                girlcount++;
            }
        }
        System.out.println("total births = " + totalBirths);
        System.out.println("female girls = " + totalGirls);
        System.out.println("male boys = " + totalBoys);
        System.out.println("number of male boys = " + boycount);
        System.out.println("number of female girls = " + girlcount);
    }
    public int getRank(int year, String name, String gender)
    {
         String fname = "data/yob"+year+".csv";
        FileResource fr = new FileResource(fname);
        int rank = 0;
        int flag = 0;
        //int lastnumBorn =0;
            for (CSVRecord rec : fr.getCSVParser(false))
        {
            rank++;
            //int thisnumBorn = Integer.parseInt(rec.get(2));
            //if (rec.get(1).equals("M") && thisnumBorn >= lastnumBorn)
            if (rec.get(1).equals("M") && flag == 0)
            {
                rank = 1;
                flag = 1;
             }
            if (rec.get(1).equals(gender) && rec.get(0).equals(name))
            {
                return rank;
             }
            //lastnumBorn = Integer.parseInt(rec.get(2));     
        }
          return -1;
       }
    public String getName(int year, int rank, String gender)
    {
        String fname = "data/yob"+year+".csv";
        FileResource fr = new FileResource(fname);
        int thisrank = 0;
        int flag = 0;
        for (CSVRecord rec : fr.getCSVParser(false))
        {
            thisrank++;
            
            if (rec.get(1).equals("M") && flag == 0)
            {
                thisrank = 1;
                flag = 1;
             }
            if (rec.get(1).equals(gender) && thisrank == rank )
            {
                return rec.get(0);
             }
              
        }
        return "NO name";
    }
    public void whatIsNameInYear(String name, int year, int newYear, String gender)
    {
        int rank = getRank(year, name, gender);
        String newname = getName(newYear, rank, gender);
        String Pronoun ="";
        if (gender.contains("M"))
        {
            Pronoun = "He";
        }
        else
        {
            Pronoun = "She";
        }
        System.out.println(name+" born in "+year +" would be " +newname+" if "+Pronoun+" was born in " +newYear+".");
    }
    public int yearOfHighestRank(String name, String gender)
    {
        DirectoryResource dr = new DirectoryResource();
        int maxrank = 0;
        int maxyear = 0;
        for (File f : dr.selectedFiles())
        {
                 String fname = f.getName();
                 int year = Integer.parseInt(fname.substring(3,7));
                 //System.out.println(year);
                 int thisrank = getRank(year, name, gender);
                 //System.out.println(thisrank);
                 if (thisrank == -1) continue;
                 if (maxrank ==0)
                 {maxrank = thisrank; 
                    maxyear = year;}
                 else{
                 if (thisrank < maxrank)
                 {
                     maxrank = thisrank;
                     maxyear = year;
                 }
                }
        }
        //System.out.println(maxrank+"  " + maxyear);
        return maxyear;
    }
    public double getAverageRank(String name, String gender)
    {
        DirectoryResource dr = new DirectoryResource();
        int totalrank=0;
        int count = 0;
        for (File f : dr.selectedFiles())
        {
            String fname = f.getName();
                 int year = Integer.parseInt(fname.substring(3,7));
                 //System.out.println(year);
                 int thisrank = getRank(year, name, gender);
                 //System.out.println(thisrank);
                 if (thisrank !=-1)
                 {totalrank += thisrank;
                 count++;}
        }
        if (totalrank == 0)
        {return -1;}
        return (double) totalrank / count;
    }
    public void testTotalBirths () {
       FileResource fr = new FileResource();
       // FileResource fr = new FileResource("data/example-small.csv");
        totalBirths(fr);
        
    }
    public int getTotalBirthsRankedHigher(int year, String name, String gender)
    {
        String fname = "data/yob"+year+".csv";
        FileResource fr = new FileResource(fname);
        //int rank = getRank(year, name, gender);
        int totalpopulation = 0;
        
            for (CSVRecord rec : fr.getCSVParser(false))
            {
                if (rec.get(0).contains(name) && rec.get(1).contains(gender))
                {break;}
                if (rec.get(1).contains(gender))
                {
                totalpopulation += Integer.parseInt(rec.get(2));
                
                /*
                int thisrank = getRank(year, rec.get(0), rec.get(1));
                if (thisrank < rank)
                {
                    totalpopulation += Integer.parseInt(rec.get(2));
                }
                */
            }
            }
            return totalpopulation;
    }
    public void test () {
        //FileResource fr = new FileResource();
        //test rank
        //int rank = getRank(1971,"Frank","M");
        //System.out.println(rank);
        
        // test whatIsNameInYear
        //whatIsNameInYear("Owen", 1974, 2014, "M");
        
        //test rank's name
        //String name = getName(1982, 450, "M");
        //System.out.println(name);
        
        
        // test higherst rank
        //int year = yearOfHighestRank("Mich", "M");
        //System.out.println("His highest ranking was in "+year+".");
        
        //test avgrank
        double avgrank = getAverageRank("Robert", "M");
        System.out.println("AVG rank is " + avgrank);
        
        //test getTotalBirthsRankedHigher
        //int totalpopulation = getTotalBirthsRankedHigher(1990,"Emily","F");
        //System.out.println("totalpopulation = " +totalpopulation);
    }
    
}
