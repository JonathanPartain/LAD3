import java.util.LinkedList;
import java.util.List;

public class FastFinder {

	public static TramFinder.TravelLeg[] findRoute(TramNetwork nw, int currentTime, TramNetwork.Station from, TramNetwork.Station to) {

		boolean[] visited = new boolean[nw.stations.length]; // from recursive
		LinkedList<TramFinder.TravelLeg> currentPath = new LinkedList<TramFinder.TravelLeg>(); // from recursive
		int bestTime = -1; // from recursive
		TramFinder.TravelLeg[] bestPath = null; // from recursive
		Heap<TramFinder.TravelLeg> heap = new Heap<>();
		TramFinder.TravelLeg tmpTravelLeg = null;

		if (from.equals(to)) { // from recursive
			if (bestTime == -1 || currentTime < bestTime) { // from recursive
				bestTime = currentTime; // from recursive
				bestPath = currentPath.toArray(new TramFinder.TravelLeg[0]); // from recursive
			}
		} else {
			for (TramNetwork.TramConnection conn : from.tramsFrom) { // from recursive
				int waitTime = conn.tram.waitingTime(currentTime, from); // from recursive
				heap.add(new TramFinder.TravelLeg(conn.tram, from, conn.to, currentTime + waitTime, conn.timeTaken)); // adding to heap if from is not equals to
			}
			visited[from.id] = true; // from recursive

		}

		// for(int i = 0; i < heap.size()+1;i++)
		// System.err.println(heap.removeMin().station);
		//
		while (!heap.isEmpty()) { // all codes should be in a while loop because of recursive
			tmpTravelLeg = heap.removeMin();
			// System.err.println(tmpTravelLeg.next);
			List<TramNetwork.TramConnection> fromHere = tmpTravelLeg.next.tramsFrom; // from recursive
			for (TramNetwork.TramConnection conn : fromHere) { // from recursive

				int waitTime = conn.tram.waitingTime(currentTime, tmpTravelLeg.next); // from recursive

				TramFinder.TravelLeg dep = new TramFinder.TravelLeg(conn.tram, from, conn.to, currentTime + waitTime,
						conn.timeTaken); // from recursive
				currentPath.addLast(dep);
				System.err.println(currentPath.size());
				// RECURSIVE SHOULD START HERE
				if (visited[tmpTravelLeg.next.id]) {
					currentPath.removeLast();// from recursive
					continue; // from recursive
				}

				if (tmpTravelLeg.next.equals(to)) { // from recursive
					if (bestTime == -1 || currentTime < bestTime) { // from recursive
						bestTime = currentTime; // from recursive
						bestPath = currentPath.toArray(new TramFinder.TravelLeg[0]); // from recursive
																						

					}
					System.err.println("here: " + tmpTravelLeg.next);
					break; // from recursive
				} else {
					heap.add(dep);
					visited[tmpTravelLeg.next.id] = true; // from recursive
				}
//				System.err.println(currentPath.size());
			}

		}
		return bestPath;
	}

}
