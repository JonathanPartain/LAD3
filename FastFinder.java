import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

// Implement this!
public class FastFinder {


    // This is the method you should implement!
    /**
     * Fast route finder using Dijkstras algorithm (see TramFinder.findRoute)
     **/
    public static TramFinder.TravelLeg[] findRoute(TramNetwork nw, int currentTime, TramNetwork.Station from, TramNetwork.Station to) {

        TramFinder.TravelLeg[] travelLegs = new TramFinder.TravelLeg[nw.stations.length];
        Heap<TramFinder.TravelLeg> heap = new Heap<>();

        // Add all connections leaving from Station from.
        for (TramNetwork.TramConnection tc : from.tramsFrom) {
        	
        	int waitTime = tc.tram.waitingTime(currentTime, from);
            heap.add(new TramFinder.TravelLeg(tc.tram, from, tc.to, currentTime + waitTime , tc.timeTaken));
 
        }
        

        // make sure removeMin is possible
        while (!heap.isEmpty()) {
        	// take the "smallest" travelLeg
        	TramFinder.TravelLeg leg = heap.removeMin();


        	// if next station has been visited, restart the while loop.
            if (travelLegs[leg.next.id] != null) {
                continue;
            }
            // "visit" the next station, add it using stations id as index.
            // potential issues if stations have weird IDs, that are larger than network size
        	travelLegs[leg.next.id] = leg;

            // find the destination station
        	if (leg.next.equals(to)) {
        		break;
        	}

        	// get all connections from the next station
            // add to heap.
            for (TramNetwork.TramConnection conn : leg.next.tramsFrom) {
                int waitTime = conn.tram.waitingTime(leg.time, leg.next);
                heap.add(new TramFinder.TravelLeg(conn.tram, conn.from, conn.to, leg.time + waitTime, conn.timeTaken));

            }
            // start over...
        }

        // Arraylist to make it easier to reverse
        List<TramFinder.TravelLeg> reversePath = new ArrayList<>();
        TramFinder.TravelLeg tmp;

        tmp = travelLegs[to.id];
        // add non-null values to reversePath
        while (tmp != null) {
            reversePath.add(tmp);
            // found destination, break!
            if (tmp.station .equals(from)) {
                break;
            }
            // take .station, since travellegs were added using .next
            // it will be possible to backtrace this way
            tmp = travelLegs[tmp.station.id];
        }

        // reverse array
        Collections.reverse(reversePath);
        // array to be returned
        TramFinder.TravelLeg[] ret = new TramFinder.TravelLeg[reversePath.size()];
        // to normal array
        reversePath.toArray(ret);
        return ret;
        
    
    }




}


