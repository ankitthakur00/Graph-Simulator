public class bfsObj{
	
	String buoc;
	int r,f;
	int [] queue;
	int nodeInReview;
	int nodeKeDangXet;
	
	String kqDuyet;
	
	int soDinhDaDuyet;
	
	int nEdge;
	
	int i,j;
	
	bfsObj next;
	bfsObj pre;
	
	public bfsObj(String buoc, int f, int r, int[] queue,int nodeInReview, int nodeKeDangXet, int nEdge,int i,int j, String kqDuyet, int soDinhDaDuyet){
		this.buoc=buoc;
		this.r=r;
		this.f=f;
		this.queue=queue;
		this.nodeInReview=nodeInReview;
		this.nodeKeDangXet=nodeKeDangXet;
		this.nEdge=nEdge;
		this.i=i;
		this.j=j;
		this.soDinhDaDuyet=soDinhDaDuyet;
		
		this.kqDuyet=kqDuyet;
		
		next = null;
	}
}