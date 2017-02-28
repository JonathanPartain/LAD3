/**
 * Created by jd on 2017-02-28.
 */
public class Node {
    TramNetwork.Station from;
    int weight = Integer.MAX_VALUE;

    public Node(TramNetwork.Station from) {
        this.weight = weight;
        this.from = from;
    }
    public Node(TramNetwork.Station from, int weight) {
        this.weight = weight;
        this.from = from;
    }

}
