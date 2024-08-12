package com.dungeonadventure.database;
import model.Enemy;
import model.Position;
import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class to query data about game objects.
 * @author Nazarii Revitskyi
 * @version Aug. 11, 2024.
 */
public final class SQLiteConnections {
    /**
     * make connection to db file in system.
     */
    private static SQLiteDataSource myDS;
    public static void main(String[] args){
        establishConnection();
    }
    public static void establishConnection() {
        myDS = null;

        try {
            myDS = new SQLiteDataSource();
            myDS.setUrl("jdbc:sqlite:enemy.db");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }

        //System.out.println("Opened database successfully");
        createTable();
        addDataRows();
    }
    private static void createTable(){
        String query = "CREATE TABLE IF NOT EXISTS enemy ( " +
            "MONSTERTYPE TEXT NOT NULL, " +
            "HEALTH INTEGER," +
            "MINDMG INTEGER," +
            "MAXDMG INTEGER," +
            "HEALCHANCE INTEGER," +
            "HITCHANCE INTEGER," +
            "SPEED INTEGER," +
            "MINHEAL INTEGER," +
            "MAXHEAL INTEGER)";
        try ( Connection conn = myDS.getConnection();
              Statement stmt = conn.createStatement(); ) {
            int rv = stmt.executeUpdate( query );
            //System.out.println( "executeUpdate() returned " + rv );
        } catch ( SQLException e ) {
            e.printStackTrace();
            System.exit( 0 );
        }
        //System.out.println( "Created enemy table successfully" );
    }
    private static void deleteData(){
        String query = "DELETE FROM enemy";
        try ( Connection conn = myDS.getConnection();
              Statement stmt = conn.createStatement(); ) {
            int rv = stmt.executeUpdate( query );
            //System.out.println( "executeUpdate() returned " + rv );
        } catch ( SQLException e ) {
            e.printStackTrace();
            System.exit( 0 );
        }
        //System.out.println( "Deleted data from enemy table successfully" );
    }
    public static void addDataRows(){
        deleteData();
        //System.out.println( "Attempting to insert three rows into enemy table" );

        String query1 = "INSERT INTO enemy ( MONSTERTYPE, HEALTH, MINDMG, MAXDMG, HEALCHANCE, HITCHANCE, SPEED, MINHEAL, MAXHEAL ) VALUES ( 'Ogre', 200, 30, 60, 10, 60, 2, 30, 60 )";
        String query2 = "INSERT INTO enemy ( MONSTERTYPE, HEALTH, MINDMG, MAXDMG, HEALCHANCE, HITCHANCE, SPEED, MINHEAL, MAXHEAL ) VALUES ( 'Skeleton', 100, 30, 50, 30, 80, 3, 30, 50 )";
        String query3 = "INSERT INTO enemy ( MONSTERTYPE, HEALTH, MINDMG, MAXDMG, HEALCHANCE, HITCHANCE, SPEED, MINHEAL, MAXHEAL ) VALUES ( 'Gremlin', 70, 15, 30, 40, 80, 5, 20, 40 )";
        try ( Connection conn = myDS.getConnection();
              Statement stmt = conn.createStatement(); ) {
            int rv = stmt.executeUpdate( query1 );
            //System.out.println( "1st executeUpdate() returned " + rv );

            rv = stmt.executeUpdate( query2 );
            //System.out.println( "2nd executeUpdate() returned " + rv );

            rv = stmt.executeUpdate( query3 );
            //System.out.println( "3rd executeUpdate() returned " + rv);
        } catch ( SQLException e ) {
            e.printStackTrace();
            System.exit( 0 );
        }
    }

    /**
     * Returns a monster based on type requested.
     * @param theMonsterType String type of the monster 'Ogre', 'Skeleton', 'Gremlin'.
     *
     */
    public static Enemy readTable(final String theMonsterType, final String theName, final int theX, final int theY){
        String query = null;
        switch(theMonsterType){
            case "Ogre":
                query = "SELECT * FROM enemy WHERE MONSTERTYPE = 'Ogre';";
                break;
            case "Skeleton":
                query = "SELECT * FROM enemy WHERE MONSTERTYPE = 'Skeleton';";
                break;
            case "Gremlin":
                query = "SELECT * FROM enemy WHERE MONSTERTYPE = 'Gremlin';";
                break;
            default:
                throw new IllegalArgumentException("Cannot proceed with type undefined in sqlite table. Type: " + theMonsterType);
        }
        //System.out.println( "Selecting " + query + " from enemy table" );
        Enemy enemy = null;
        try ( Connection conn = myDS.getConnection();
              Statement stmt = conn.createStatement(); ) {

            ResultSet rs = stmt.executeQuery(query);
            //walk through each 'row' of results, grab data by column/field name
            // and print it
            while(rs.next()){
                String monsterType = rs.getString("MONSTERTYPE");
                int hp = rs.getInt("HEALTH");
                int minDmg = rs.getInt("MINDMG");
                int maxDmg = rs.getInt("MAXDMG");
                int healChance = rs.getInt("HEALCHANCE");
                int hitChance = rs.getInt("HITCHANCE");
                int speed = rs.getInt("SPEED");
                int minHeal = rs.getInt("MINHEAL");
                int maxHeal = rs.getInt("MAXHEAL");
               // System.out.println("Selected query: " + monsterType + " " + hp + " " + minDmg + " " + maxDmg + " " + healChance + " " + hitChance + " " + speed + " " + minHeal + " " + maxHeal);
                enemy = new Enemy(monsterType, theName, hp, minDmg, maxDmg, healChance, hitChance, speed, minHeal, maxHeal, theX, theY);
            }
        } catch ( SQLException e ) {
            e.printStackTrace();
            System.exit( 0 );
        }
        if(enemy == null){
            throw new IllegalStateException("Failed to create a requested enemy from db.");
        }
        return enemy;
    }
}
