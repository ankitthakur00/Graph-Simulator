import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class panelSoDoBFS extends JPanel implements ActionListener {

	private int w = 455;
	private int h = 500;
	MainFrame mf;
	private int start = 0, n = 0;

	private int speed = 1000;

	private int[][] a = new int[31][31];
	public MainBFS approach;

	Timer timer;
	long beforeTime = 0L;
	bfsObj Step;

	private SoDo sodo;

	Graphics2D dbg2;
	Image dbImage;

	boolean run = false;

	public void setInitial(int start, int n) {
		this.start = start;
		this.n = n;
	}

	public void setSpeedForAuto(int speed) {
		this.speed = speed;
	}

	public panelSoDoBFS(MainFrame mf) {

		timer = new Timer(100, this);
		sodo = new SoDo();
		approach = new MainBFS();
		this.mf = mf;	
	}
	public void actionPerformd2() {
		render();
		paintSr();
		update3();
		
	}
	public void actionPerformd() {
		render();
		paintSr();
		update2();		
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

		render();
		paintSr();
		if (run) {
			update();
		}
	}
	private void update2() {
		if (approach.start != null) {

			if (Step.next != null)
				Step = Step.next;
			else
				Step = approach.start;

		}
	}
	private void update3() {
		if (approach.start != null) {
			if (Step.pre != null)
				Step = Step.pre;
			else
				Step = approach.start;

		}
	}
	private void update() {
		if (approach.start != null) {
			if (System.currentTimeMillis() - beforeTime > speed) {
				beforeTime = System.currentTimeMillis();
				if (Step.next != null)
					Step = Step.next;
				else
					Step = approach.start;
			}
		}
	}

	public void makeScriptBFS(int start, int n) {

		String resultBFS="";
		
		int soStart=0;
		
		Edge_List edgeL = new Edge_List();
		
		int nEdge=0;
		
		int nodeInReview = 0;

		int[] queue = new int[100];
		int[] tham = new int[100];
		
		approach.insertBFS("buff", 0, 0, queue, nodeInReview, 0, nEdge,0,0,"",soStart);
		int f = 0, r = 0;
		approach.insertBFS("begin", f, r, queue, nodeInReview,0, nEdge,0,0,"",soStart);
		approach.insertBFS("Initialization", f, r, queue, nodeInReview,0, nEdge,0,0,"", soStart);
		queue[r++] = start;
		tham[start] = 1;

		approach.insertBFS("B1", f, r, queue, nodeInReview, 0, nEdge,0,0,"", soStart);
		approach.insertBFS("FaceFirst", f, r, queue, nodeInReview, 0,nEdge,0,0,"", soStart);
		
		while (f != r) {
			
			soStart++;
	
			int i = queue[f++];
			nodeInReview = i;
			if(resultBFS!="")resultBFS+="->"+i;
			else resultBFS+=""+i;
			approach.insertBFS("B2", f, r, queue, nodeInReview, 0, nEdge,i,1,resultBFS,soStart);
			
			approach.insertBFS("jSmallerFirst", f, r, queue, nodeInReview, 0,nEdge,i,1,resultBFS,soStart);
			for (int j = 1; j <= n; j++) {
				
				approach.insertBFS("do Then", f, r, queue, nodeInReview, j,nEdge,i,j,resultBFS, soStart);

				if (tham[j] == 0 && a[i][j] != 0 && a[i][j] != 1111) {
					tham[j] = 1;
					queue[r++] = j;
					
					edgeL.InsertEdge(0, mf.wp.ds.get_Node(nodeInReview-1), mf.wp.ds.get_Node(j-1));
					nEdge++;
					
					approach.insertBFS("B3", f, r, queue, nodeInReview, j, nEdge,i,j,resultBFS, soStart);
					approach.insertBFS("plusJFirst", f, r, queue, nodeInReview, 0, nEdge,i,j+1,resultBFS, soStart);
				} else
					approach.insertBFS("plusJSecond", f, r, queue, nodeInReview, 0, nEdge,i,j+1,resultBFS, soStart);
				approach.insertBFS("jNhoHonNSecond", f, r, queue, nodeInReview, 0, nEdge,i,j+1,resultBFS, soStart);
			}
			approach.insertBFS("fKhacrSecond", f, r, queue, 0, 0, nEdge,i,0,resultBFS, soStart);
		}
		approach.insertBFS("end", f, r, queue, 0, 0, nEdge,0,0,resultBFS, soStart);
		approach.insertBFS("buff2", f, r, queue, nodeInReview, 0, 0,0,0,resultBFS,soStart);
		
		approach.insertEdgeL(edgeL);
	}

	public void insertMatric(int matric[][]) {

		for (int i = 1; i < a.length; i++)
			for (int j = 1; j < a.length; j++)
				a[i][j] = matric[i - 1][j - 1];
	}

	public void render() {
		dbImage = createImage(w, h);
		if (dbImage != null) {
			dbg2 = (Graphics2D) dbImage.getGraphics();
			if (dbg2 == null)
				return;
			else {
				dbg2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				sodo.showSoDo(dbg2, n, start);
				
				if (Step != null) {
					
						if(Step!=null) mf.result.prosessingTime.setTienDo(Step);
						
						if(!mf.result.prosessingTime.press&&mf.result.prosessingTime.release){
							mf.result.prosessingTime.release=false;
							mf.result.prosessingTime.press = false;
						}
									
					sodo.showState(dbg2, Step.f, Step.r,Step.i,Step.j);
							
					setState();
				}
				else
					sodo.showState(dbg2, 0, 0,0,0);
			}
		}
	}

	public void setState() {
			mf.queue.setText(Step.buoc);
			int[] queue = Step.queue;
			String textOutQueue = "";
			for (int i = 0; i < Step.r; i++)
				textOutQueue += " " + queue[i];
			mf.queue.setText(textOutQueue);
			
			Image dbImage = mf.wp.createImage(mf.width, mf.heigh);
			Graphics2D g2=null;
			if(dbImage!=null){
				g2 = (Graphics2D) dbImage.getGraphics();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				
				
				if(g2!=null){
					
					mf.wp.bg.GridBackGr(g2);
					mf.wp.gp.drawHideGraph(g2, mf.wp.ds);
					//Node******
					
					mf.wp.gp.drawEdgeListSapDuyet(g2, approach.edgeL,Step.nEdge,mf.wp.ds);
					
					
					
					for(int i=0;i<Step.r;i++){
						mf.wp.drawN.nodeSapDuyet(g2, mf.wp.ds.get_Node(queue[i]-1).x,
								mf.wp.ds.get_Node(queue[i]-1).y, queue[i]-1);
					}
					mf.wp.gp.drawEdgeList(g2, approach.edgeL, Step.soDinhDaDuyet-1, mf.wp.ds);
					
					mf.result.setResult(Step.kqDuyet);
					
					if (mf.wp.ds.get_xy(Step.nodeInReview - 1) != null
							&& Step.nodeInReview != 0&&g2!=null) {
						
						
						
						mf.wp.drawN.nodeStart(g2, mf.wp.ds
								.get_Node(Step.nodeInReview - 1),true);
						
						
						if(Step.nodeKeDangXet!=0){
							
							if(Step.nodeInReview==Step.nodeKeDangXet)
							mf.wp.drawN.nodeKeDangXet(g2, mf.wp.ds.get_Node(Step.nodeKeDangXet-1), false,true);
							else 
								mf.wp.drawN.nodeKeDangXet(g2, mf.wp.ds.get_Node(Step.nodeKeDangXet-1), false,false);
						}
						
					
					}
					if(!Step.kqDuyet.equals("")&&Step.nodeInReview-1!=mf.wp.ds.FirstNode.cost
							&&Step.nodeKeDangXet-1!=mf.wp.ds.FirstNode.cost)
						mf.wp.drawN.nodeRoot(g2, mf.wp.ds.FirstNode, false);
					
					if(run==false&&Step==null) mf.wp.gp.drawResultBFS(g2, mf.wp.ds);
					
					mf.wp.BFSGuide.showCT(g2);
					
					
					Graphics g=null;
					try{
						g=mf.wp.getGraphics();
					}catch(Exception e){}
					if(g!=null) g.drawImage(dbImage,0,0,null);
				}
		}
		
	}

	private void paintSr() {
		Graphics g;
		try {
			g = this.getGraphics();
			if (g != null)
				g.drawImage(dbImage, 0, 0, null);
		} catch (Exception e) {
		}
	}

}
