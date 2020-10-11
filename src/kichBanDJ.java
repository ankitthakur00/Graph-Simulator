
public class kichBanDJ {
	play3 start;
	play3 end;

	Edge_List edgeL;
	
	
	public void insertEdgeL(Edge_List edgeL){
		this.edgeL=edgeL;
	}

	public kichBanDJ() {
		start = end = null;

	}

	

	public void insertKichBan(String text, int r, int c, int edgeN,kqDuyet kq, int nkq) {
		play3 a = new play3(text,r,c, edgeN,kq,nkq);
		if (start == null) {
			start = end = a;
		} else {
			end.next = a;
			a.prev = end;
			end = a;
		}
	}
}
