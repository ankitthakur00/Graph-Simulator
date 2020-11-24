import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;
@SuppressWarnings("serial")
public class ProsessingTime extends JPanel implements MouseListener, MouseMotionListener{
	
	private int w = 350;
	private int h = 30;
	private MainBFS a;
	private MainDFS b;
	//private MainDj c;
	
	private double doan;
	
	private int nStep;
	
	private double x=0;
	
	public boolean press = false;
	public boolean release = false;
	
	int n;
	MainFrame mf;
	
	public ProsessingTime(MainFrame mf){
		this.setPreferredSize(new Dimension(w,h));
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.mf=mf;
		
	}
	public void setNumStep(MainBFS a){
		int step = 0;
		this.a = a;
		bfsObj Step = a.start;
		while(Step!=null){
			step++;
			Step=Step.next;
		}
		if(step==0) nStep=1;
		else nStep = step;
		doan = (double) w/nStep;
		this.n = 1;
	}
	public void setNumStep(MainDFS b){
		int step = 0;
		this.b = b;
		dfsObj Step = b.start;
		while(Step!=null){
			step++;
			Step=Step.next;
		}
		if(step==0) nStep=1;
		else nStep = step;
		doan = (double) w/nStep;
		
		this.n=2;
	}
	public void paint(Graphics g){
		g.setColor(Color.gray);
		g.fillRect(0, 14, w, 4);
		g.setColor(Color.red);
		g.fillRect(0, 11, (int)x, 8);
	}
	public int getStep(){
		int step = 0;
		double s = 0;
		while(x>s){
			s+=doan;
			step++;
		}
		return step-1;
	}

	public void paintScr(){
		Graphics g;
		try{
			
			g = this.getGraphics();
			g.setColor(Color.decode("#eeeeee"));
			g.fillRect(0, 0, w, h);
			g.setColor(Color.gray);
			g.fillRect(0, 14, w, 4);
			g.setColor(Color.red);
			g.fillRect(0, 11, (int)x, 8);
		}catch(Exception e){}
		
	}
	public void setStepForKB(int step){
		if(n==1){
			int i=0;
			mf.panelSoDoBFS.Step=a.start;
			if(step == 0) mf.panelSoDoBFS.Step=a.start;
			else if(step == nStep) mf.panelSoDoBFS.Step= mf.panelSoDoBFS.approach.end;
			else
			while(i<step) {
				mf.panelSoDoBFS.Step=mf.panelSoDoBFS.Step.next;
				i++;
			}
		}else if(n==2){
			int i=0;
			mf.panelSoDoDFS.Step=b.start;
			if(step == 0) mf.panelSoDoDFS.Step=b.start;
			else if(step == nStep) mf.panelSoDoDFS.Step= mf.panelSoDoDFS.approach.end;
			else
			while(i<step) {
				mf.panelSoDoDFS.Step=mf.panelSoDoDFS.Step.next;
				i++;
			}
		}
	}
		
	public void setTienDo(bfsObj Step){
		if(Step==mf.panelSoDoBFS.approach.start) x = 0;
		else if (Step == mf.panelSoDoBFS.approach.end) x = w;
		else {
			x=0;
			bfsObj p = mf.panelSoDoBFS.approach.start;
			while(p!=Step&&p!=null) {
				x+=doan;
				p=p.next;
			}
		}
		paintScr();
	}
	public void setTienDo(dfsObj Step){
		if(Step==mf.panelSoDoDFS.approach.start) x = 0;
		else if (Step == mf.panelSoDoDFS.approach.end) x = w;
		else {
			x=0;
			dfsObj p = mf.panelSoDoDFS.approach.start;
			while(p!=Step&&p!=null) {
				x+=doan;
				p=p.next;
			}
		}
		paintScr();
	}

	
	public void mouseDragged(MouseEvent arg0) {
		if(arg0.getX()>=0 && arg0.getX()<=w) x = arg0.getX();
		else if (arg0.getX()<0) x =0;
		else if(arg0.getY()>w) x=w;
		paintScr();
		
		if(mf.wp.ds.FirstNode!=null) {
			System.out.println(getStep());
			setStepForKB(getStep());
		}
	}
	public void mouseMoved(MouseEvent arg0) {}
	public void mouseClicked(MouseEvent arg0) {}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {
		
		press = true;
		
		if(arg0.getX()>=0 && arg0.getX()<=w) x = arg0.getX();
		else if (arg0.getX()<0) x =0;
		else if(arg0.getY()>w) x=w;
		if(mf.wp.ds.FirstNode!=null) {
			System.out.println(getStep());
			setStepForKB(getStep());
		}
		
		
		paintScr();
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {release=true;}
}
