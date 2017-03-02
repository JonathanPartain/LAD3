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
        ArrayList<TramFinder.TravelLeg> currPath = new ArrayList<>();

        ArrayList<Node> stations = new ArrayList<>();

        TramFinder.TravelLeg tmpPath = null;

        Heap<TramFinder.TravelLeg> heap = new Heap<>();

        // First leg, from "from" to shortest.

        Heap<TramFinder.TravelLeg> fromStart = new Heap<>();
        for (TramNetwork.TramConnection tc : from.tramsFrom) {

            int waitTime = tc.tram.waitingTime(currentTime, from);
/**
            TramFinder.TravelLeg[] a = new TramFinder.TravelLeg[1];
            a[0] = new TramFinder.TravelLeg(tc.tram, from, tc.to, currentTime + waitTime, tc.timeTaken);
            System.out.println("VVVVV");
            TramFinder.printTravel(a);
            System.out.println("^^^^^");
*/
            fromStart.add(new TramFinder.TravelLeg(tc.tram, from, tc.to, currentTime + waitTime, tc.timeTaken));
        }




        visited[from.id] = true;
        TramFinder.TravelLeg lastPath = null;



        while (!fromStart.isEmpty()) {
            if (heap.isEmpty() && !fromStart.isEmpty()) {
                TramFinder.TravelLeg tmpAdd = fromStart.removeMin();

                heap.add(tmpAdd);
                currPath.add(tmpAdd);

            }



            tmpPath = heap.removeMin();
            lastPath = tmpPath;


            if (tmpPath.station.equals(to)) {
                System.out.println("Arrived");
                bestPath.add(new TramFinder.TravelLeg(null, to, to, 0, 0));
                break;
            }

            if (!visited[tmpPath.next.id]) {


                Heap<TramFinder.TravelLeg> tmpHeap = new Heap<>();
                for (TramNetwork.TramConnection conn : tmpPath.next.tramsFrom) {


                    int waitTime = conn.tram.waitingTime(tmpPath.time, tmpPath.next);
                    // check time calculation
                    tmpHeap.add(new TramFinder.TravelLeg(conn.tram, tmpPath.next, conn.to, tmpPath.time + waitTime, conn.timeTaken));

                }

                TramFinder.TravelLeg tmpLeg = tmpHeap.removeMin();
                heap.add(tmpLeg);
                currPath.add(tmpLeg);


                visited[tmpPath.station.id] = true;
            }



        }



        for (int i = 0; i < currPath.size(); i++) {
            bestPath.add(currPath.get(i));

        }



        TramFinder.TravelLeg[] print = new TramFinder.TravelLeg[bestPath.size()];
        int index = 0;
        for (TramFinder.TravelLeg t : bestPath) {
            print[index] = t;
            index++;
        }
        System.out.println("<----START---->");
        TramFinder.printTravel(print);
        System.out.println("<-----END----->");

        return null;
    }




}


