

public class playDFS {
	play2 start;

	play2 end;

	Edge_List edgeL;

	public playDFS() {
		start = end = null;

	}

	public void insertEdgeL(Edge_List edgeL) {
		this.edgeL = edgeL;
	}

	public void insertKichBan(String buoc,int []Stack,int l,int nodeDangXet,int nodeKeDangXet,String ketQuaDuyet, int nEdge) {
		play2 a = new play2(buoc,Stack,l,nodeDangXet,nodeKeDangXet,ketQuaDuyet,nEdge);
		if (start == null) {
			start = end = a;
		} else {
			end.next = a;
			a.pre = end;
			end = a;
		}
	}
}
