import java.util.ArrayList;
import java.util.Arrays;

// Implement this!
public class FastFinder {


    // This is the method you should implement!
    /**
     * Fast route finder using Dijkstras algorithm (see TramFinder.findRoute)
     **/
    public static TramFinder.TravelLeg[] findRoute(TramNetwork nw, int currentTime, TramNetwork.Station from, TramNetwork.Station to) {

        // Hint: You need to use TramFinder.TravelLeg because this is outside
        // the TramFinder class. Example:
        // TramFinder.TravelLeg[] fastest = new
        // TramFinder.TravelLeg[nw.stations.length];

        boolean[] visited = new boolean[nw.stations.length];
        Arrays.fill(visited, Boolean.FALSE);

        // i use this for testing so far
        ArrayList<TramFinder.TravelLeg> bestPath = new ArrayList<>();
        Heap<TramFinder.TravelLeg> allPaths = new Heap<>();

        ArrayList<Node> stations = new ArrayList<>();

        TramFinder.TravelLeg tmpPath = null;

        Heap<TramFinder.TravelLeg> heap = new Heap<>();

        // according to TramFinder class that should be correct right?

        Heap<TramFinder.TravelLeg> tmpHeap1 = new Heap<>();
        for (TramNetwork.TramConnection tc : from.tramsFrom) {

            int waitTime = tc.tram.waitingTime(currentTime, from);

            tmpHeap1.add(new TramFinder.TravelLeg(tc.tram, from, tc.to, currentTime + waitTime, tc.timeTaken));
        }

        heap.add(tmpHeap1.removeMin());

        visited[from.id] = true;
        TramFinder.TravelLeg lastPath = null;

        while (!heap.isEmpty()) {

            tmpPath = heap.removeMin();
            lastPath = tmpPath;


            if (tmpPath.station.equals(to)) {
                bestPath.add(new TramFinder.TravelLeg(null,to, to, 0,0));
                break;
            }

            if (!visited[tmpPath.next.id]) {

                Heap<TramFinder.TravelLeg> tmpHeap = new Heap<>();
                for (TramNetwork.TramConnection conn : tmpPath.next.tramsFrom) {

                    int waitTime = conn.tram.waitingTime(tmpPath.time, tmpPath.next);
                    // check time calculation
                    tmpHeap.add(new TramFinder.TravelLeg(conn.tram, tmpPath.next, conn.to, tmpPath.time + waitTime, conn.timeTaken));

                }

                heap.add(tmpHeap.removeMin());


                visited[tmpPath.station.id] = true;
            }


        }
        heap.add(lastPath);

        while (!heap.isEmpty()) {

            TramFinder.TravelLeg tmpLeg = heap.removeMin();

            bestPath.add(tmpLeg);
        }
        for (TramFinder.TravelLeg t : bestPath) {
            System.out.println("Tram " + t.tram  + ", time: " + (t.time+t.travelTime));
        }

        return null;
    }




}


