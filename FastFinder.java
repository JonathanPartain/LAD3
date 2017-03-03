import java.util.LinkedList;

public class FastFinder {

	public static TramFinder.TravelLeg[] findRoute(TramNetwork nw, int currentTime, TramNetwork.Station from, TramNetwork.Station to) {

		boolean[] visited = new boolean[nw.stations.length]; // from recursive
		LinkedList<TramFinder.TravelLeg> currentPath = new LinkedList<TramFinder.TravelLeg>(); // from recursive
		int bestTime = -1; // from recursive
		TramFinder.TravelLeg[] bestPath = null; // from recursive
		Heap<TramFinder.TravelLeg> heap = new Heap<>();
		TramNetwork.Station tmpStation = null;

		for (TramNetwork.TramConnection conn : from.tramsFrom) { // from recursive
			int waitTime = conn.tram.waitingTime(currentTime, from); // from recursive
			heap.add(new TramFinder.TravelLeg(conn.tram, conn.from, conn.to, currentTime + waitTime, conn.timeTaken));
			currentPath.add(new TramFinder.TravelLeg(conn.tram, from, to, currentTime + waitTime, conn.timeTaken));
		}

		visited[from.id] = true;
		tmpStation = from;
		
//		System.err.println(tmpStation);

//		 for(int i = 0; i < heap.size();i++)
//			 System.err.println(heap.removeMin());
		
		while (!heap.isEmpty()) { // all codes should be in a while loop because of recursive
									
			for (TramNetwork.TramConnection conn : tmpStation.tramsFrom) { // from recursive
				// RECURSIVE SHOULD START HERE
				if (visited[tmpStation.id]) {
					continue; // from recursive
				}
				if (tmpStation.equals(to)) { // from recursive
					if (bestTime == -1 || currentTime < bestTime) { // from recursive
						bestTime = currentTime; // from recursive
						bestPath = currentPath.toArray(new TramFinder.TravelLeg[0]); // from recursive
					}
//					System.err.println("here: " + tmpStation);
					break; // from recursive
				}
				currentPath.removeLast();
				int waitTime = conn.tram.waitingTime(currentTime, tmpStation); // from recursive

				TramFinder.TravelLeg dep = new TramFinder.TravelLeg(conn.tram, conn.from, to, currentTime + waitTime, conn.timeTaken); // from recursive
				currentPath.addLast(dep);
//				System.err.println(dep.station);
				heap.add(dep);
			}
			visited[tmpStation.id] = true; // from recursive
//			System.err.println(tmpStation + " " + visited[tmpStation.id]);
			tmpStation = heap.removeMin().next;
			
		}
		return bestPath;
	}

}
