import java.util.ArrayList;
import java.util.Arrays;

// Implement this!
public class FastFinder {


    // This is the method you should implement!
    /**
     * Fast route finder using Dijkstras algorithm (see TramFinder.findRoute)
     **/
    public static TramFinder.TravelLeg[] findRoute(TramNetwork nw, int currentTime, TramNetwork.Station from, TramNetwork.Station to) {
    	
        // the TramFinder class. Example:
        // TramFinder.TravelLeg[] fastest = new
        // TramFinder.TravelLeg[nw.stations.length];

        boolean[] visited = new boolean[nw.stations.length];
        TramFinder.TravelLeg[] travelLegs = new TramFinder.TravelLeg[nw.stations.length];
        Heap<TramFinder.TravelLeg> heap = new Heap<>();
        
        for (TramNetwork.TramConnection tc : from.tramsFrom) {
        	
        	int waitTime = tc.tram.waitingTime(currentTime, from);
            heap.add(new TramFinder.TravelLeg(tc.tram, from, tc.to, tc.timeTaken + waitTime , tc.timeTaken));
 
        }
        
        
        while (!heap.isEmpty()) {
        	
        	TramFinder.TravelLeg leg = heap.removeMin();
        	
        	travelLegs[leg.station.id] = leg;
        	
        	
        	if (leg.next.equals(to)) {
        		break;
        	}
        	
        	if (travelLegs[leg.next.id] == null) {
        		
        		for (TramNetwork.TramConnection conn : leg.next.tramsFrom) {
        			
                	int waitTime = conn.tram.waitingTime(leg.time, leg.next);
                	heap.add(new TramFinder.TravelLeg(conn.tram, conn.from, conn.to, conn.timeTaken + waitTime , conn.timeTaken));
                	
            	}
        	}
        	
     	
        }
        
        
        TramFinder.TravelLeg[] path = new TramFinder.TravelLeg[nw.stations.length];
        int c = 0; /*count*/
        
        for (int i = travelLegs.length -1; i >= 0; i--) {
        	if (travelLegs[i] != null && travelLegs[i].station != to) {
        		path[c] = travelLegs[i];
        		c++;
        	}
        	else {
        		path[c] = travelLegs[i];
        		c++;
        		break;
        	}
        }
        
        
        return path;        
        /**
        ArrayList<TramFinder.TravelLeg> bestPath = new ArrayList<>();
        Heap<TramFinder.TravelLeg> prevPath = new Heap<>();

        // First leg, from "from" to shortest.
        for (TramNetwork.TramConnection tc : from.tramsFrom) {

            int waitTime = tc.tram.waitingTime(currentTime, from);

            heap.add(new TramFinder.TravelLeg(tc.tram, from, tc.to, tc.timeTaken + waitTime , tc.timeTaken));
            prevPath.add(new TramFinder.TravelLeg(tc.tram, tc.from, tc.to, tc.timeTaken + waitTime, tc.timeTaken));


        }

        visited[from.id] = true;
        TramFinder.TravelLeg lastPath;


        while (!heap.isEmpty()) {

            lastPath = heap.removeMin();






            if (lastPath.next.equals(to)) {

                bestPath.add(lastPath);
                break;
            }




            if (!visited[lastPath.next.id]) {

                bestPath.add(lastPath);
                // UPDATE prevPATH SOMEWHERE
                // MOVE -> UPDATE -> MOVE -> UPDATE
                heap = new Heap<>();

                for (TramNetwork.TramConnection conn : lastPath.next.tramsFrom) {
                    int waitTime = conn.tram.waitingTime(lastPath.time, lastPath.next);
                    heap.add(new TramFinder.TravelLeg(conn.tram, conn.from, conn.to, lastPath.time + waitTime, conn.timeTaken));

                }


                visited[lastPath.station.id] = true;
            }

            if (heap.isEmpty()) {
                // might be needed
                // prevPath.removeMin();
                // empty bestpath
                bestPath = new ArrayList<>();
                heap = prevPath;
                Arrays.fill(visited, Boolean.FALSE);
            }


        }


        TramFinder.TravelLeg[] print = new TramFinder.TravelLeg[bestPath.size()];
        int index = 0;
        for (TramFinder.TravelLeg t : bestPath) {
            print[index] = t;
            index++;
        }

        return print;
        */
        
    
    }




}


