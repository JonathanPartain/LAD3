import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;


public class TramFinder { 
    
    
    // This works but is not fast enough. 
    /** Finds a route in a tram network. This is like a simple travel planner,
     * taking a starting time, a departure station and a destination station.
     * 
     * Unfortunately, the algorithm used for this method is quite slow -
     * but we have a team of software engineers working on an improved version.
     * 
     * @param nw The network to search in (contains all the trams, stops etc).
     * @param starttime The intended time of departure of the travel. 
     * @param from The departure station. 
     * @param to The destination station.
     * @return An array of TravelLegs, each representing a step from one station to the next on the travel.
     * Null if no route is found.
     */
    public static TravelLeg[] findRoute(TramNetwork nw, int starttime, TramNetwork.Station from, TramNetwork.Station to){
       Finder f = new Finder(nw); 
       f.findRoute(starttime, from, to);
       return f.bestPath;
    }
    
    private static class Finder {
        private boolean[] visited;
        private LinkedList<TravelLeg> currentPath = new LinkedList<TravelLeg>();
        
        private int bestTime = -1;
        TravelLeg[] bestPath = null;
        
        public Finder(TramNetwork nw) {
            visited = new boolean[nw.stations.length];
        }

        
        public void findRoute(int currentTime, TramNetwork.Station from, TramNetwork.Station to){
            
            if (visited[from.id])
                return;
            if (from.equals(to)){
                if (bestTime == -1 || currentTime < bestTime){
                    bestTime = currentTime;
                    bestPath = currentPath.toArray(new TravelLeg[0]);
                }
                return;
            }
            
            
            visited[from.id]=true;
            
            List<TramNetwork.TramConnection> fromHere = from.tramsFrom;
            for(TramNetwork.TramConnection conn : fromHere ){
                
                int waitTime = conn.tram.waitingTime(currentTime,from);
                
                TravelLeg dep = new TravelLeg(conn.tram, from, conn.to, currentTime+waitTime, conn.timeTaken);
                
                currentPath.addLast(dep);
                findRoute(dep.time+conn.timeTaken, conn.to, to);
                currentPath.removeLast();
            }
            
            visited[from.id]=false;
            
        }
    
    }
    
    /** Represents a single travel step from one station to the next using a tram */
    public static class TravelLeg implements Comparable<TravelLeg>{
        public final TramNetwork.Tram tram;
        
        /** Departure station **/
        public final TramNetwork.Station station;
        
        /** Arrival station **/
        public final TramNetwork.Station next;
        
        
        /** Absolute time of departure, in minutes from 00:00, possibly exceeds 24*60 */
        public final int time;
        
        /** Time in minutes for this step **/
        public int travelTime;
        
        public TravelLeg(TramNetwork.Tram tram, TramNetwork.Station station, TramNetwork.Station to, int time, int timeTaken) {
            this.tram = tram;
            this.station = station;
            this.time = time;
            this.next=to;
            this.travelTime=timeTaken;
        }
        
        public String toString(){
            return formatTime(time)+", Tram "+tram+" departs from " + station;    
        }
        
        public int getArrivalTime() {
            return time+travelTime;
        }

        public int compareTo(TravelLeg t) {
            return getArrivalTime() - (t.time+t.travelTime);
        }
    }
    
    /**
     * Prints a found route in a nice user-friendly text format.
     * @param travel The route as constructed by the tram finder (null if no route found). 
     */
    public static void printTravel(TravelLeg[] travel){
        PrintStream o = System.out;
        if (travel==null) {
          o.println("No route was found");
          return;
        }
        
        if (travel.length==0){
          o.print("Seriously, you are already where you are going. Use your legs.");
          return;
        }
        
        TramNetwork.Tram currentTram = null;
        System.out.println("Route length: " + travel.length);
        for(TramFinder.TravelLeg tc:travel) 
            if (tc.tram==currentTram)
                System.out.println("  " + formatTime(tc.getArrivalTime()) + ", " + tc.next);
            else {
                System.out.println(tc);
                System.out.println("  " + formatTime(tc.getArrivalTime()) + ", " + tc.next);
                currentTram = tc.tram;
            }
        
        TravelLeg dest = travel[travel.length-1];
        System.out.println(formatTime(dest.getArrivalTime()) + ", Tram " + dest.tram + " arrives at "+ dest.next);
        
    }
    
    private static String formatTime(int time){
        int timeofday = time % (60*24),
            hour = timeofday/60,
            minute = timeofday%60,
            day = time / (60*24);
        
        return (hour < 10 ? "0"+hour: hour)+":"+(minute < 10 ? "0"+minute: minute)+(day == 0 ? "" : "+");
        
    }
    
}





