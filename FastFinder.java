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


        ArrayList<TramFinder.TravelLeg> bestPath = new ArrayList<>();


        Heap<TramFinder.TravelLeg> heap = new Heap<>();
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
    }




}


