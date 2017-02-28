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

        for (TramNetwork.TramConnection tc : from.tramsFrom) {

            int waitTime = tc.tram.waitingTime(currentTime, from);

            heap.add(new TramFinder.TravelLeg(tc.tram, from, tc.to, currentTime + waitTime, tc.timeTaken));
        }

        visited[from.id] = true;

        while (!heap.isEmpty()) {

            tmpPath = heap.removeMin();


            if (tmpPath.station.equals(to)) {
                heap = new Heap<>();
                continue;
            }

            if (!visited[tmpPath.next.id]) {

                for (TramNetwork.TramConnection conn : tmpPath.next.tramsFrom) {

                    int waitTime = conn.tram.waitingTime(tmpPath.time, tmpPath.next);

                    stations.add(new Node(conn.to, waitTime));



                    heap.add(new TramFinder.TravelLeg(conn.tram, tmpPath.next, conn.to, tmpPath.time + waitTime, conn.timeTaken));

                }

                visited[tmpPath.station.id] = true;
            }

            allPaths.add(tmpPath);

        }

        for (Node n : stations) {
            System.out.println("From: " + n.from.toString() + ", w: " + n.weight);
        }


        return null;
    }




}


