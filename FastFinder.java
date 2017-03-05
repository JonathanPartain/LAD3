import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class FastFinder {
	public static TramFinder.TravelLeg[] findRoute(TramNetwork nw, int currentTime, TramNetwork.Station from,
			TramNetwork.Station to) {
		boolean[] visited = new boolean[nw.stations.length];
		LinkedList<TramFinder.TravelLeg> bestPath = new LinkedList<>();
		ArrayList<TramFinder.TravelLeg> tmp = new ArrayList<>();
		Heap<TramFinder.TravelLeg> heap = new Heap<>();

		for (TramNetwork.TramConnection tc : from.tramsFrom) {
			int waitTime = tc.tram.waitingTime(currentTime, from);
			heap.add(new TramFinder.TravelLeg(tc.tram, from, tc.to, currentTime + waitTime, tc.timeTaken));
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
				heap = new Heap<>();
				for (TramNetwork.TramConnection conn : lastPath.next.tramsFrom) {
					int waitTime = conn.tram.waitingTime(lastPath.time, lastPath.next);
					heap.add(new TramFinder.TravelLeg(conn.tram, lastPath.next, conn.to, lastPath.time + waitTime,
							conn.timeTaken));
				}
				visited[lastPath.station.id] = true;
			}
			if (heap.isEmpty()) {
				// might be needed
				// prevPath.removeMin();
				// empty bestpath
				bestPath.removeLast();
				for (TramNetwork.TramConnection conn : lastPath.next.tramsFrom) {
					int waitTime = conn.tram.waitingTime(lastPath.time, lastPath.next);
					heap.add(new TramFinder.TravelLeg(conn.tram, lastPath.next, conn.to, lastPath.time + waitTime,
							conn.timeTaken));
				}

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