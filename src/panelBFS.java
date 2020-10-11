import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class panelBFS extends JPanel implements ActionListener {

	private int w = 455;
	private int h = 500;
	MainFrame mf;

	private int xuatphat = 0, n = 0;

	private int speed = 1000;

	private int[][] a = new int[31][31];
	public kichBanBFS kBan;

	Timer timer;
	long beforeTime = 0L;
	play steps;

	private SoDo sodo;

	Graphics2D dbg2;
	Image dbImage;

	boolean run = false;

	public void setKhoiTao(int xuatphat, int n) {
		this.xuatphat = xuatphat;
		this.n = n;
	}

	public void setSpeedForAuto(int speed) {
		this.speed = speed;
	}

	public panelBFS(MainFrame mf) {

		timer = new Timer(100, this);
		sodo = new SoDo();
		kBan = new kichBanBFS();
		this.mf = mf;
		
		
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
		if (kBan.start != null) {

			if (steps.next != null)
				steps =steps.next;
			else
				steps = kBan.start;

		}
	}
	private void update3() {
		if (kBan.start != null) {
			if (steps.pre != null)
				steps = steps.pre;
			else
				steps = kBan.start;

		}
	}
	private void update() {
		if (kBan.start != null) {
			if (System.currentTimeMillis() - beforeTime > speed) {
				beforeTime = System.currentTimeMillis();
			
				if (steps.next != null)
					steps = steps.next;
				else
					steps = kBan.start;
			}
		}
	}

	public void makeKichBanBFS(int xuatphat, int n) {

		String kqDuyet="";
		
		int soDinhDaDuyet=0;
		
		Edge_List edgeL = new Edge_List();
		
		int nEdge=0;
		
		int nodeDangXet = 0;

		int[] queue = new int[100];
		int[] tham = new int[100];
		
		kBan.insertKichBan("buff", 0, 0, queue, nodeDangXet, 0, nEdge,0,0,"",soDinhDaDuyet);
		int f = 0, r = 0;
		kBan.insertKichBan("begin", f, r, queue, nodeDangXet,0, nEdge,0,0,"",soDinhDaDuyet);
		kBan.insertKichBan("Khoi tao", f, r, queue, nodeDangXet,0, nEdge,0,0,"", soDinhDaDuyet);
		queue[r++] = xuatphat;
		tham[xuatphat] = 1;

		kBan.insertKichBan("B1", f, r, queue, nodeDangXet, 0, nEdge,0,0,"", soDinhDaDuyet);
		kBan.insertKichBan("fKhacrFirst", f, r, queue, nodeDangXet, 0,nEdge,0,0,"", soDinhDaDuyet);
		
		while (f != r) {
			
			soDinhDaDuyet++;
			
			int i = queue[f++];
			nodeDangXet = i;
			if(kqDuyet!="")kqDuyet+="->"+i;
			else kqDuyet+=""+i;
			System.out.print(" " + i);
			kBan.insertKichBan("B2", f, r, queue, nodeDangXet, 0, nEdge,i,1,kqDuyet,soDinhDaDuyet);
			System.out.println("Nut dang xet: " + nodeDangXet);
			kBan.insertKichBan("jNhoHonNFirst", f, r, queue, nodeDangXet, 0,nEdge,i,1,kqDuyet,soDinhDaDuyet);
			for (int j = 1; j <= n; j++) {
				
				kBan.insertKichBan("daTham", f, r, queue, nodeDangXet, j,nEdge,i,j,kqDuyet, soDinhDaDuyet);

				if (tham[j] == 0 && a[i][j] != 0 && a[i][j] != 1111) {
					tham[j] = 1;
					queue[r++] = j;
					//******************Rac roi vo cung***************
					
					
					edgeL.InsertEdge(0, mf.wp.ds.get_Node(nodeDangXet-1), mf.wp.ds.get_Node(j-1));
					nEdge++;
					//******************** =))**********************
					kBan.insertKichBan("B3", f, r, queue, nodeDangXet, j, nEdge,i,j,kqDuyet, soDinhDaDuyet);
					kBan.insertKichBan("plusJFirst", f, r, queue, nodeDangXet, 0, nEdge,i,j+1,kqDuyet, soDinhDaDuyet);
				} else
					kBan.insertKichBan("plusJSecond", f, r, queue, nodeDangXet, 0, nEdge,i,j+1,kqDuyet, soDinhDaDuyet);
				kBan.insertKichBan("jNhoHonNSecond", f, r, queue, nodeDangXet, 0, nEdge,i,j+1,kqDuyet, soDinhDaDuyet);
			}
			kBan.insertKichBan("fKhacrSecond", f, r, queue, 0, 0, nEdge,i,0,kqDuyet, soDinhDaDuyet);
		}
		kBan.insertKichBan("end", f, r, queue, 0, 0, nEdge,0,0,kqDuyet, soDinhDaDuyet);
		kBan.insertKichBan("buff2", f, r, queue, nodeDangXet, 0, 0,0,0,kqDuyet,soDinhDaDuyet);
		
		kBan.insertEdgeL(edgeL);
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
				sodo.showSoDo(dbg2, n, xuatphat);
				
				if (steps != null) {
					
						if(steps!=null) mf.result.prosessingTime.setTienDo(steps);
						
						if(!mf.result.prosessingTime.press&&mf.result.prosessingTime.release){
							mf.result.prosessingTime.release=false;
							mf.result.prosessingTime.press = false;
						}
							
					sodo.showState(dbg2, steps.f, steps.r,steps.i,steps.j);	
					
					if (steps.buoc == "begin")
						sodo.showStart(dbg2, Color.red);
					if (steps.buoc == "Khoi tao")
						sodo.khoitao(dbg2, Color.red, 165, 60, n, xuatphat);
					if (steps.buoc == "B1")
						sodo.B1(dbg2, Color.red, 155, 130);
					if (steps.buoc == "fKhacrFirst")
						sodo.fKhacRFirst(dbg2, Color.red, 170, 180);
					if (steps.buoc == "fKhacrSecond")
						sodo.fKhacRSecond(dbg2, Color.red, 170, 180);

					if (steps.buoc == "jNhoHonNFirst")
						sodo.jBeHonNFirst(dbg2, Color.red, 170, 320);
					if (steps.buoc == "jNhoHonNSecond")
						sodo.jBeHonNSecond(dbg2, Color.red, 170, 320);

					if (steps.buoc == "plusJFirst")
						sodo.plusJFirst(dbg2, Color.red, 170, 320);
					if (steps.buoc == "plusJSecond")
						sodo.plusJSecond(dbg2, Color.red, 170, 320);

					if (steps.buoc == "B3")
						sodo.B3(dbg2, Color.red, 300, 405);
					if (steps.buoc == "daTham")
						sodo.daTham(dbg2, Color.red, 165, 390);
					if (steps.buoc == "B2")
						sodo.B2(dbg2, Color.red, 170, 250);
					if (steps.buoc == "end")
						sodo.theEnd(dbg2, Color.red);
					if (steps.buoc == "buff2") {
						steps = kBan.start;
						mf.start.setIcon(mf.startIcon);
						run = false;
					}
					
					setState();
				}else
					sodo.showState(dbg2, 0, 0,0,0);
			}
		}
	}

	public void setState() {
			
			mf.queue.setText(steps.buoc);

			

			int[] queue = steps.queue;
			String textOutQueue = "";
			for (int i = 0; i <steps.r; i++)
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
					
					mf.wp.gp.drawEdgeListSapDuyet(g2, kBan.edgeL,steps.nEdge,mf.wp.ds);
					
					
					
					for(int i=0;i<steps.r;i++){
						mf.wp.drawN.nodeSapDuyet(g2, mf.wp.ds.get_Node(queue[i]-1).x,
								mf.wp.ds.get_Node(queue[i]-1).y, queue[i]-1);
					}
					mf.wp.gp.drawEdgeList(g2, kBan.edgeL,steps.soDinhDaDuyet-1, mf.wp.ds);
					
					mf.result.setResult(steps.kqDuyet);
					
					if (mf.wp.ds.get_xy(steps.nodeDangXet - 1) != null
							&& steps.nodeDangXet != 0&&g2!=null) {
						
						mf.wp.drawN.nodeStart(g2, mf.wp.ds
								.get_Node(steps.nodeDangXet - 1),true);
						
						if(steps.nodeKeDangXet!=0){
							
							if(steps.nodeDangXet==steps.nodeKeDangXet)
							mf.wp.drawN.nodeKeDangXet(g2, mf.wp.ds.get_Node(steps.nodeKeDangXet-1), false,true);
							else 
								mf.wp.drawN.nodeKeDangXet(g2, mf.wp.ds.get_Node(steps.nodeKeDangXet-1), false,false);
						}
						
					}
					if(!steps.kqDuyet.equals("")&&steps.nodeDangXet-1!=mf.wp.ds.FirstNode.cost
							&&steps.nodeKeDangXet-1!=mf.wp.ds.FirstNode.cost)
						mf.wp.drawN.nodeRoot(g2, mf.wp.ds.FirstNode, false);
					
		
					if(run==false&& steps==null) mf.wp.gp.drawResultBFS(g2, mf.wp.ds);
					
					mf.wp.BFSChuThich.showCT(g2);
					
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
