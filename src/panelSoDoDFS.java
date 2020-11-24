import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;


public class panelSoDoDFS extends JPanel implements ActionListener{
	public int w = 455;
	public int h = 500;
	MainFrame mf;

	private SoDoDFS sodo;
	
	private int[][] a = new int[31][31];
	private int xuatphat,n;
	
	private int speed = 1000;


	boolean run = false;
	public MainDFS approach;
	dfsObj Step;

	Timer timer;
	long beforeTime = 0L;


	

	Graphics2D dbg2;
	Image dbImage;



	public void setKhoiTao(int xuatphat, int n) {
		this.xuatphat = xuatphat;
		this.n = n;
	}

	public void setSpeedForAuto(int speed) {
		this.speed = speed;
	}

	public panelSoDoDFS(MainFrame mf) {

		timer = new Timer(100, this);
		
		approach=new MainDFS();
		
		sodo=new SoDoDFS();
		
		this.mf=mf;
		
	}
	public void insertMatric(int matric[][]) {

		for (int i = 1; i < a.length; i++)
			for (int j = 1; j < a.length; j++)
				a[i][j] = matric[i - 1][j - 1];

	}
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		render();
		paintSr();
		if (run) {
			update();
		}
	}

	
	private void update() {
		if (approach.start != null) {
			if (System.currentTimeMillis() - beforeTime > speed) {
				beforeTime = System.currentTimeMillis();
				//System.out.println(Step.Step);
				if (Step.next != null)
					Step = Step.next;
				else
					Step = approach.start;
			}
		}
	}
	
	public void makeKichBanDFS(int xuatphat, int n) {
		
		int []Stack=new int[100];
		int topS=0;
		int []tham = new int[100];
		int[] back = new int[100];
		
		boolean jumpTo3 = true;
		
		Edge_List listEdge = new Edge_List();
		
		int i=-1;
		
		String ketQuaDuyet="";
		
		approach.insertKichBan("buff",Stack,0,0,0,ketQuaDuyet,i);
		approach.insertKichBan("begin",Stack,0,0,0,ketQuaDuyet,i);
		approach.insertKichBan("Khoi tao",Stack,0,0,0,ketQuaDuyet,i);
		Stack[topS++]=xuatphat;
		
		
		approach.insertKichBan("B1",Stack,topS,0,0,ketQuaDuyet,i);
		approach.insertKichBan("topHon0First",Stack,topS,0,0,ketQuaDuyet,i);
		
		
		
		while(topS>0){
			
			jumpTo3=true;
			
			
			
			int v=Stack[--topS];
			
			if(back[v]!=0) listEdge.InsertEdge(0, mf.wp.ds.get_Node(v-1), mf.wp.ds.get_Node(back[v]-1));
			
			
			
			approach.insertKichBan("B2",Stack,topS,v,0,ketQuaDuyet,i);
			
			
			approach.insertKichBan("dathamchua",Stack,topS,v,0,ketQuaDuyet,i);
			if(tham[v]!=1){
				i++;
				if(v==xuatphat)
					ketQuaDuyet+=String.valueOf(xuatphat);
					else ketQuaDuyet+=(">"+v);
				
				approach.insertKichBan("printV", Stack, topS, v, 0, ketQuaDuyet, i);
				
				
				
				tham[v] = 1;
				
				approach.insertKichBan("uHon1First",Stack,topS,v,0,ketQuaDuyet,i);
				
				for(int u=n;u>=1;u--){
					
					approach.insertKichBan("daTham",Stack,topS,v,u,ketQuaDuyet,i);
					
					if (a[v][u] != 0 && a[v][u] != 1111) {
						if (tham[u] == 0) {
							Stack[topS++] = u;
							
							//tham[u]=1;
							
							back[u]=v;
							
							approach.insertKichBan("B3",Stack,topS,v,u,ketQuaDuyet,i);
							approach.insertKichBan("plusJFirst",Stack,topS,v,0,ketQuaDuyet,i);
						}
					} else
						approach.insertKichBan("plusJSecond",Stack,topS,v,0,ketQuaDuyet,i);
					approach.insertKichBan("uHon1Second",Stack,topS,v,0,ketQuaDuyet,i);
				}
				jumpTo3 = false;
			}
			
			
			if(jumpTo3){
				approach.insertKichBan("topHon0Third", Stack, topS, 0, 0, ketQuaDuyet, i);
				jumpTo3=true;
			}else approach.insertKichBan("topHon0Second",Stack,topS,0,0,ketQuaDuyet,i);
			
		}
		approach.insertKichBan("end",Stack,0,0,0,ketQuaDuyet,i);
		approach.insertKichBan("buff2",Stack,0,0,0,ketQuaDuyet,i);
		
		Graphs d = new Graphs();
		Edge_List list = d.getEdgeDFS(mf.wp.ds);
		
		approach.insertEdgeL(list);
		
		
		
	}
	
	public void render() {
		dbImage = createImage(w, h);
		if (dbImage != null) {
			dbg2 = (Graphics2D) dbImage.getGraphics();
			if (dbg2 == null)
				return;
			else {
				dbg2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				sodo.showSoDo(dbg2, this.n, this.xuatphat);
				
				sodo.stack(Step,dbg2);
				
				if (Step != null) {
							
					setState();
				}
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
	
	public void actionPerformd2() {
		// TODO Auto-generated method stub

		render();
		paintSr();

		update3();
		
	}
	public void actionPerformd() {
		// TODO Auto-generated method stub

		render();
		paintSr();

		update2();
		
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
	public void setState() {
		
			Image dbImage = mf.wp.createImage(mf.width, mf.heigh);
		Graphics2D g2=null;
		if(dbImage!=null){
			g2 = (Graphics2D) dbImage.getGraphics();
			if(g2!=null){
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				mf.wp.bg.GridBackGr(g2);
				mf.wp.gp.drawHideGraph(g2, mf.wp.ds);
				
				mf.result.setResult(Step.ketQuaDuyet);
				
				if(Step.nEdge>0) {
					mf.wp.gp.drawEdgeList(g2, approach.edgeL, Step.nEdge, mf.wp.ds);
					
				}
				
				if (mf.wp.ds.get_xy(Step.nodeDangXet - 1) != null
						&& Step.nodeDangXet != 0&&g2!=null) {
					
					
					
					mf.wp.drawN.nodeStart(g2, mf.wp.ds
							.get_Node(Step.nodeDangXet - 1),true);
					
					if(Step.nodeKeDangXet!=0){
						
						if(Step.nodeDangXet==Step.nodeKeDangXet)
						mf.wp.drawN.nodeKeDangXet(g2, mf.wp.ds.get_Node(Step.nodeKeDangXet-1), false,true);
						else 
							mf.wp.drawN.nodeKeDangXet(g2, mf.wp.ds.get_Node(Step.nodeKeDangXet-1), false,false);
					}
					
				} 
				
				
				if(Step==null&&Step.buoc=="buff2") mf.wp.gp.drawResultDFS(g2, mf.wp.ds);
				
				mf.wp.DFSGuide.showCT(g2);
				
				Graphics g=null;
				try{
					g=mf.wp.getGraphics();
				}catch(Exception e){}
				if(g!=null) g.drawImage(dbImage,0,0,null);
			}
		}
		

	}
}
