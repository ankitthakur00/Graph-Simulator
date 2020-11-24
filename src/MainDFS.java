

public class MainDFS {
	dfsObj start;

	dfsObj end;

	Edge_List edgeL;

	public MainDFS() {
		start = end = null;

	}

	public void insertEdgeL(Edge_List edgeL) {
		this.edgeL = edgeL;
	}

	public void insertKichBan(String buoc,int []Stack,int l,int nodeDangXet,int nodeKeDangXet,String ketQuaDuyet, int nEdge) {
		dfsObj a = new dfsObj(buoc,Stack,l,nodeDangXet,nodeKeDangXet,ketQuaDuyet,nEdge);
		if (start == null) {
			start = end = a;
		} else {
			end.next = a;
			a.pre = end;
			end = a;
		}
	}
}
