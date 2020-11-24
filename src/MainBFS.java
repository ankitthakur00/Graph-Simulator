public class MainBFS {
	bfsObj start,end;
	
	Edge_List edgeL;

	public MainBFS(){
		start=end=null;
		
	}
	public void insertEdgeL(Edge_List edgeL){
		this.edgeL=edgeL;
	}
	public void insertBFS(String buoc, int f, int r, int [] queue,int nodeDangXet,int nodeKeDangXet, int nEdge,int i,int j,String kqDuyet, int soDinhDaDuyet){
		bfsObj a = new bfsObj(buoc,f,r,queue,nodeDangXet, nodeKeDangXet, nEdge,i,j, kqDuyet, soDinhDaDuyet);
		if(start==null){
			start=end=a;
		}else{
			end.next=a;
			a.pre=end;
			end=a;
		}
	}
}
