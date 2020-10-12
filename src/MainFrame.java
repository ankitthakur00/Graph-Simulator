import java.awt.CardLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class MainFrame extends JFrame implements ActionListener {

	private static final int WIDTH = 1000;
	private static final int HEIGH = 720;

	public static final int width = 501;
	public static final int heigh = 411;

	public resultSearch result;	
	int xBFS;
	int xDFS;
	boolean press=false;

	public static int xStartBt = 70;
	public static int yStartBt = 20;
	public static int wStartBt = 50;
	public static int hStartBt = 20;
	
	public static int xStopBt = 130;
	public static int yStopBt = 20;
	public static int wStopBt = 50;
	public static int hStopBt = 20;
	
	public static int xNextBt = 80;
	public static int yNextBt = 20;
	public static int wNextBt = 50;
	public static int hNextBt = 20;
	
	public static int xPreBt = 20;
	public static int yPreBt = 20;
	public static int wPreBt = 50;
	public static int hPreBt = 20;
	
	MainScreen qc = new MainScreen(15, 20);
	workPanel wp = new workPanel(this);
	JPanel ctrTable = new JPanel();
	JPanel MatricTable;
	JTable table = new JTable();
	TableValues tableValues = new TableValues();

	JButton edit = new JButton("Sửa đô thị");
	
	
	private int loccard = 1;
	CardLayout card = new CardLayout();
	JPanel desc = new JPanel();
	//style Panels
	ImageIcon startIcon = new ImageIcon(MainFrame.class.getResource("startIcon.png"));
	Icon pauseIcon = new ImageIcon(MainFrame.class.getResource("pauseIcon.png"));
	Icon stopIcon = new ImageIcon(MainFrame.class.getResource("stopIcon.png"));
	Icon nextIcon = new ImageIcon(MainFrame.class.getResource("nextIcon.png"));
	Icon previousIcon = new ImageIcon(MainFrame.class.getResource("previousIcon.png"));
	

	private JPanel credits = new Credits();
	private JPanel panelDuyetRong = new JPanel();
	public panelBFS panelSoDoBFS = new panelBFS(this);
	public panelDFS panelSoDoDFS = new panelDFS(this);
	public panelDijstra panelDijstra = new panelDijstra(this);

	public JPanel panelDuyetSau = new JPanel();
	public JPanel panelDij = new JPanel();

	JTextField queue = new JTextField();
	public JButton start;
	
	// //////// panel DFS/////////////
	JLabel browseResult = new JLabel();
	public JButton start2;

	public JButton start3;
	JButton New, Edit, BFS, DFS, DIJStra;
	JCheckBox autoNumber, hideNumber;
	
	public MainFrame() {
		super("Graph Algorithms simulator");
		setLayout(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		URL iconURL = getClass().getResource("endd.png");
		ImageIcon icon = new ImageIcon(iconURL);
		setIconImage(icon.getImage());
		JMenuBar mb = new JMenuBar();
		this.setJMenuBar(mb);
		New = new JButton("Create new");
		New.addActionListener(this);
		Edit = new JButton("Edit");
		Edit.addActionListener(this);
		BFS = new JButton("Breadth first Search");
		BFS.addActionListener(this);
		DFS = new JButton("Depth First Search");
		DFS.addActionListener(this);
		DIJStra = new JButton("Dijkstra");
		DIJStra.addActionListener(this);
		mb.add(New);
		mb.add(Edit);
		mb.add(BFS);
		mb.add(DFS);
		mb.add(DIJStra);
		wp.setBounds(5, 5, width, heigh);
		add(wp);
		makeCtrlTable();
		makeMatricTable();
		makeDescr();
		JLabel lbMakeNode = new JLabel("Click left mouse to make a point");
		lbMakeNode.setBounds(25,60,200,20);
		credits.add(lbMakeNode);
	}

	public void come_card(int a, int maxcard) {
		if (a > loccard) {
			for (int i = 0; i < a - loccard; i++)
				card.next(desc);
			loccard = a;
		} else if (a < loccard) {
			for (int i = 0; i < maxcard - loccard + a; i++)
				card.next(desc);
			loccard = a;
		}
	}

	private void makeDescr() {
		int WIDTH = 465, HEIGHT = 647;
		Container c = getContentPane();
		desc.setBounds(510, 3, WIDTH, HEIGHT);
		desc.setLayout(card);
		desc.add(credits, "edit");
		credits.setBorder(BorderFactory.createTitledBorder("Edit graph"));
		credits.setLayout(null);
		credits.add(qc);
		panelDuyetRong.setBorder(BorderFactory.createTitledBorder("BFS"));
		JPanel automatic = new JPanel();
		automatic.setBounds(5, 520, 300, 80);
		automatic.setLayout(null);
		automatic.setBorder(BorderFactory.createTitledBorder("Auto"));
		panelDuyetRong.add(automatic);
		start = new JButton(startIcon);
		start.setBounds(xStartBt, yStartBt, wStartBt, hStartBt);

	//start bfs
		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(!panelSoDoBFS.run){
					if(wp.ds.FirstNode!=null){
						if(panelSoDoBFS.steps==null) {	
							panelSoDoBFS.steps=panelSoDoBFS.kBan.start;
						}else if(panelSoDoBFS.steps.buoc=="buff2")
							panelSoDoBFS.steps=panelSoDoBFS.kBan.start;
						panelSoDoBFS.run = true;
						start.setIcon(pauseIcon);
					}
					
				}else {
					panelSoDoBFS.run=false;
					start.setIcon(startIcon);
				}
				
			}

		});
		automatic.add(start);
		JButton tamDung = new JButton(stopIcon);
		tamDung.setBounds(xStopBt, yStopBt, wStopBt, hStopBt);
		tamDung.addActionListener(new ActionListener() {
			// PAUSEBFS
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				panelSoDoBFS.steps=null;
				panelSoDoBFS.run = false;
				start.setIcon(startIcon);
				panelSoDoBFS.steps=panelSoDoBFS.kBan.start;
				panelSoDoBFS.setState();
				panelSoDoBFS.render();
				
			}

		});
		automatic.add(tamDung);
		JLabel lbSpeed = new JLabel("Speed");
		lbSpeed.setBounds(20, 50, 100, 20);
		automatic.add(lbSpeed);

		JPanel bangTay = new JPanel();
		bangTay.setBounds(305, 520, 155, 50);
		bangTay.setBorder(BorderFactory.createTitledBorder("Step by step"));

		JButton tiepTheo = new JButton(nextIcon);
		bangTay.setLayout(null);
		tiepTheo.setBounds(xNextBt, yNextBt, wNextBt, hNextBt);
		tiepTheo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (panelSoDoBFS.steps == null && wp.ds.FirstNode != null) {
					panelSoDoBFS.makeKichBanBFS(wp.ds.FirstNode.cost + 1,
							wp.data);
					panelSoDoBFS.steps = panelSoDoBFS.kBan.start;
				}
				panelSoDoBFS.actionPerformd();
			}

		});
		bangTay.add(tiepTheo);
		JButton lui = new JButton(previousIcon);
		lui.setBounds(xPreBt,yPreBt,wPreBt,hPreBt);
		lui.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				panelSoDoBFS.actionPerformd2();
			}

		});
		bangTay.add(lui);
		panelDuyetRong.add(bangTay);

		JLabel lbQueue = new JLabel("Queue");
		lbQueue.setBounds(10, 610, 90, 20);
		panelDuyetRong.add(lbQueue);
		queue.setBounds(80, 610, 200, 20);
		panelDuyetRong.add(queue);

		panelDuyetRong.setLayout(null);

		panelSoDoBFS.setBounds(5, 20, 455, 500);

		panelDuyetRong.add(panelSoDoBFS);
		desc.add(panelDuyetRong, "bfs");

		
		volumeSpeed speed1 = new volumeSpeed(panelSoDoBFS, panelSoDoDFS,
				panelDijstra, 1);
		speed1.setBounds(70, 50, 220, 20);
		automatic.add(speed1);

		// ********************DUYET CHIEU SAU**************************
		desc.add(panelDuyetSau, "dfs");
		panelDuyetSau.setBorder(BorderFactory
				.createTitledBorder("DFS"));

		panelDuyetSau.setLayout(null);

		panelSoDoDFS.setBounds(5, 20, panelSoDoDFS.w, panelSoDoDFS.h);
		panelDuyetSau.add(panelSoDoDFS);

		JPanel tuDong2 = new JPanel();
		tuDong2.setBounds(5, 520, 300, 80);
		tuDong2.setLayout(null);
		tuDong2.setBorder(BorderFactory.createTitledBorder("Auto"));
		panelDuyetSau.add(tuDong2);

		start2 = new JButton(startIcon);
		start2.setBounds(xStartBt, yStartBt, wStartBt, hStartBt);

		start2.addActionListener(new ActionListener() {
			// STARTBFS
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				if (wp.ds.FirstNode != null) {
					if(!panelSoDoDFS.run){
						if(panelSoDoDFS.buoc==null){
							
							panelSoDoDFS.buoc = panelSoDoDFS.kBan.start;
						}
						if(panelSoDoDFS.buoc.buoc=="buff2"){
							panelSoDoDFS.buoc=panelSoDoDFS.kBan.start;
						}
						panelSoDoDFS.run=true;
						start2.setIcon(pauseIcon);
					}else{
						panelSoDoDFS.run = false;
						start2.setIcon(startIcon);
					}
					
					
				}
				
			}

		});
		tuDong2.add(start2);
		JButton tamDung2 = new JButton(stopIcon);
		tamDung2.setBounds(xStopBt, yStopBt, wStopBt, hStopBt);
		tamDung2.addActionListener(new ActionListener() {
			// PAUSEBFS
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				panelSoDoDFS.buoc=null;
				panelSoDoDFS.run = false;
				start2.setIcon(startIcon);
				panelSoDoDFS.buoc=panelSoDoDFS.kBan.start;
				panelSoDoDFS.render();
			}

		});
		tuDong2.add(tamDung2);
	
		JLabel lbSpeed2 = new JLabel("Speed");
		lbSpeed2.setBounds(20, 50, 100, 20);
		tuDong2.add(lbSpeed2);

		JPanel bangTay2 = new JPanel();
		bangTay2.setBounds(305, 520, 155, 50);
		bangTay2.setBorder(BorderFactory.createTitledBorder("Step by step"));

		JButton tiepTheo2 = new JButton(nextIcon);//để hình cho các nút
		bangTay2.setLayout(null);
		tiepTheo2.setBounds(xNextBt, yNextBt, wNextBt, hNextBt);
		tiepTheo2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (panelSoDoDFS.buoc == null && wp.ds.FirstNode != null) {
					panelSoDoDFS.makeKichBanDFS(wp.ds.FirstNode.cost + 1,
							wp.data);
					panelSoDoDFS.buoc = panelSoDoDFS.kBan.start;
				}
				panelSoDoDFS.actionPerformd();
			}

		});
		bangTay2.add(tiepTheo2);
		JButton lui2 = new JButton(previousIcon);
		lui2.setBounds(xPreBt, yPreBt, wPreBt, hPreBt);
		lui2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				panelSoDoDFS.actionPerformd2();
			}

		});
		bangTay2.add(lui2);
		panelDuyetSau.add(bangTay2);

		volumeSpeed speed2 = new volumeSpeed(panelSoDoBFS, panelSoDoDFS,
				panelDijstra, 2);
		speed2.setBounds(70, 50, 220, 20);
		tuDong2.add(speed2);

		JLabel lbOutput = new JLabel("Result: ");
		lbOutput.setBounds(10, 605, 100, 20);
		panelDuyetSau.add(lbOutput);

		browseResult.setBounds(60, 605, 300, 20);
		panelDuyetSau.add(browseResult);

		// ************************DIJksTRA****************************

		desc.add(panelDij, "dj");
		panelDij.setBorder(BorderFactory
				.createTitledBorder("Dijkstra"));

		c.add(desc);
		panelDij.setLayout(null);
		panelDijstra.setBounds(5, 20, panelDijstra.w, panelDijstra.h);
		panelDij.add(panelDijstra);

		JPanel tuDong3 = new JPanel();
		tuDong3.setBounds(5, 520, 300, 80);
		tuDong3.setLayout(null);
		tuDong3.setBorder(BorderFactory.createTitledBorder("Auto"));
		panelDij.add(tuDong3);

		start3 = new JButton(startIcon);
		start3.setBounds(xStartBt, yStartBt, wStartBt, hStartBt);

		start3.addActionListener(new ActionListener() {
			// STARTBFS
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				
				if (wp.ds.FirstNode != null) {
					if(!panelDijstra.run){
						if(panelDijstra.buoc==null){
							panelDijstra.buoc = panelDijstra.kBan.start;
							
						}
						if(panelDijstra.buoc.text=="buff2"){
							panelDijstra.buoc=panelDijstra.kBan.start;
							panelDijstra.tb.format();
						}
						
						
						panelDijstra.run=true;
						start3.setIcon(pauseIcon);
					}else{
						panelDijstra.run=false;
						start3.setIcon(startIcon);
					}
					
				}
			}

		});
		tuDong3.add(start3);
		JButton tamDung3 = new JButton(stopIcon);
		tamDung3.setBounds(xStopBt, yStopBt, wStopBt, hStopBt);
		tamDung3.addActionListener(new ActionListener() {
			// PAUSEBFS
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				panelDijstra.run = false;
				panelDijstra.buoc=null;
				panelDijstra.tb.format();
				panelDijstra.render();
				start3.setIcon(startIcon);
			}

		});
		tuDong3.add(tamDung3);
		/*JButton tiepTuc3 = new JButton("Continue");
		tiepTuc3.setBounds(200, 20, 90, 20);
		tiepTuc3.addActionListener(new ActionListener() {
			// tiep tuc
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				panelDijstra.run = true;
				if (panelDijstra.buoc == null)
					panelDijstra.buoc = panelDijstra.kBan.start;
			}

		});
		tuDong3.add(tiepTuc3);*/

		JLabel lbSpeed3 = new JLabel("Speed");
		lbSpeed3.setBounds(20, 50, 100, 20);
		tuDong3.add(lbSpeed3);

		JPanel bangTay3 = new JPanel();
		bangTay3.setBounds(305, 520, 155, 50);
		bangTay3.setBorder(BorderFactory.createTitledBorder("Step by step"));

		JButton tiepTheo3 = new JButton(nextIcon);
		bangTay3.setLayout(null);
		tiepTheo3.setBounds(xNextBt, yNextBt, wNextBt, hNextBt);
		tiepTheo3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (panelDijstra.buoc == null && wp.ds.FirstNode != null) {
					panelDijstra.makeKichBan(wp.data);
					panelDijstra.buoc = panelDijstra.kBan.start;
				}
				panelDijstra.actionPerformd();
			}

		});
		bangTay3.add(tiepTheo3);
		JButton lui3 = new JButton(previousIcon);
		lui3.setBounds(xPreBt, yPreBt, wPreBt, hPreBt);
		lui3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				panelDijstra.actionPerformd2();
			}

		});
		bangTay3.add(lui3);
		panelDij.add(bangTay3);

		volumeSpeed speed3 = new volumeSpeed(panelSoDoBFS, panelSoDoDFS,
				panelDijstra, 3);
		speed3.setBounds(70, 50, 220, 20);
		tuDong3.add(speed3);

	}

	private void makeMatricTable() {
		MatricTable = new JPanel();
		MatricTable.setLayout(null);
		MatricTable.setBounds(5, 470, 501, 180);
		MatricTable.setBorder(BorderFactory
				.createTitledBorder("Matrix"));
		add(MatricTable);
		table.setModel(tableValues);

		JScrollPane scr = new JScrollPane();
		scr.setBounds(10, 20, 480, 155);

		scr.setViewportView(table);
		for (int i = 0; i < table.getColumnCount(); i++)
			table.getColumnModel().getColumn(i).setPreferredWidth(30);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		MatricTable.add(scr);
	}

	private void makeCtrlTable() {
		ctrTable.setBounds(5, 420, 501, 50);
		// ctrTable.setBackground(Color.decode("#819FF7"));
		ctrTable.setLayout(null);
		ctrTable.setBorder(BorderFactory.createTitledBorder("Mode"));
		add(ctrTable);

		

		autoNumber = new JCheckBox("Auto generate weight number");
		autoNumber.setBounds(15,20,200,20);
		autoNumber.addActionListener(this);
		ctrTable.add(autoNumber);
		
		
		hideNumber = new JCheckBox("Hide weight number");
		hideNumber.setBounds(220,20,150,20);
		hideNumber.addActionListener(this);
		ctrTable.add(hideNumber);
		
		setSize(WIDTH, HEIGH);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == New) {
			
			start.setIcon(startIcon);
			start2.setIcon(startIcon);
			start3.setIcon(startIcon);
			
			if(result!=null) {
				result.setVisible(false);
				result = null;
			}
			
			
			wp.ds = new Node_Link();
			wp.repaint();
			wp.data = 0;
			wp.matNum.updateSize(wp.ds);
			wp.updateTable();
			
			wp.edit = true;
			wp.BFS = false;
			wp.DFS = false;
			wp.DJ = false;
			wp.listEdge.format();
			wp.repaint();
			qc.setRun(true);
			panelDijstra.timer.stop();
			panelDijstra.setRun(false);
			panelSoDoBFS.timer.stop();
			panelSoDoDFS.timer.stop();
			panelSoDoBFS.steps = null;
			panelSoDoDFS.buoc = null;
			panelDijstra.buoc = null;
			panelDijstra.kBan = new playDJ();
			card.show(desc, "edit");
			
			
		}

		if (e.getSource() == this.Edit) {
			
			start.setIcon(startIcon);
			start2.setIcon(startIcon);
			start3.setIcon(startIcon);
			
			
			if(result!=null) {
				result.setVisible(false);
				result = null;
			}
			
			
			wp.edit = true;
			wp.BFS = false;
			wp.DFS = false;
			wp.DJ = false;
			wp.repaint();
			panelDijstra.timer.stop();
			panelDijstra.setRun(false);
			panelSoDoBFS.timer.stop();
			panelSoDoDFS.timer.stop();
			card.show(desc, "edit");
			panelSoDoBFS.steps = null;
			panelSoDoDFS.buoc = null;
			panelDijstra.buoc = null;
			panelDijstra.kBan = new playDJ();
			qc.setRun(true);
		}
		if (e.getSource() == this.BFS) {
			
			panelSoDoBFS.steps = null;
			panelSoDoDFS.buoc = null;
			panelDijstra.buoc = null;
			
			start.setIcon(startIcon);
			
			
			
			if(result!=null) {
				
				result.setVisible(false);
				result = null;
			}
			result = new resultSearch(this, 1);
			result.setVisible(true);
			
			
				wp.BFS = true;
				wp.DFS = false;
				wp.DJ = false;
				// come_card(2,4);

				card.show(desc, "bfs");
				panelSoDoBFS.insertMatric(wp.matNum.getA());
				panelSoDoBFS.kBan.start = null;
				if (wp.ds.FirstNode != null){
					panelSoDoBFS.setKhoiTao(wp.ds.FirstNode.cost + 1,
							wp.data);
					panelSoDoBFS.makeKichBanBFS(wp.ds.FirstNode.cost + 1,
							wp.data);
					result.prosessingTime.setNumStep(panelSoDoBFS.kBan);
				}
					

				
				panelSoDoBFS.timer.start();
				panelSoDoDFS.run=false;
				panelSoDoDFS.timer.stop();
				panelDijstra.timer.stop();
				
				panelDijstra.setRun(false);
				wp.edit = false;
				wp.repaint();
				qc.setRun(false);
		} 
		if (e.getSource() == this.DFS) {
			
			panelSoDoBFS.steps = null;
			panelSoDoDFS.buoc = null;
			panelDijstra.buoc = null;
			
			start2.setIcon(startIcon);

			
			if(result!=null) {
				result.setVisible(false);
				result = null;
			}
			result = new resultSearch(this, 2);
			result.setVisible(true);
			
			
				wp.BFS = false;
				wp.DFS = true;
				wp.DJ = false;
				// come_card(3,4);
				panelSoDoBFS.run=false;
				panelSoDoBFS.timer.stop();
				panelDijstra.timer.stop();
				panelDijstra.setRun(false);
				card.show(desc, "dfs");
				
				panelSoDoDFS.kBan.start = null;
				if (wp.ds.FirstNode != null){
					panelSoDoDFS.insertMatric(wp.matNum.getA());
					
					panelSoDoDFS.setKhoiTao(wp.ds.FirstNode.cost + 1,
							wp.data);
					panelSoDoDFS.makeKichBanDFS(wp.ds.FirstNode.cost + 1,
							wp.data);
					result.prosessingTime.setNumStep(panelSoDoDFS.kBan);
				}
					
				panelSoDoDFS.timer.start();
				wp.edit = false;
				wp.repaint();
				qc.setRun(false);
		} 
		if(e.getSource() == this.DIJStra){
			
		
			start3.setIcon(startIcon);
			
			if(result!=null) {
				result.setVisible(false);
				result = null;
			}
			
			
				wp.BFS = false;
				wp.DFS = false;
				wp.DJ = true;
				// come_card(4,4);

				panelDijstra.insertMatric(wp.matNum.getA());
				panelDijstra.tb.setNumOfNode(wp.data);
				panelDijstra.makeKichBan(wp.data);
				panelDijstra.tb.format();

				if (wp.ds.FirstNode != null){
				}
				
				panelSoDoBFS.timer.stop();
				panelSoDoDFS.timer.stop();
				
				card.show(desc, "dj");
				panelDijstra.timer.start();
				wp.edit = false;
				wp.repaint();
				qc.setRun(false);
		}
			
		

		if(this.autoNumber.isSelected()){
			wp.ranDome = true;
		}else wp.ranDome = false;
		
		if(this.hideNumber.isSelected()){
			wp.showNumb = false;
			wp.repaint();
		}else {
			wp.showNumb = true;
			wp.repaint();
		}
	}

	

}
