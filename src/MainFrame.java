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

@SuppressWarnings("serial")
public class MainFrame extends JFrame implements ActionListener {

	private static final int WIDTH = 1000, HEIGH = 720;
	public static final int width = 501, heigh = 411;

	public resultSearch result;

	int xBFS, xDFS;
	boolean press = false;

	public static int xStartBt = 70, yStartBt = 20, wStartBt = 50, hStartBt = 20;

	public static int xStopBt = 130, yStopBt = 20, wStopBt = 50, hStopBt = 20;

	public static int xNextBt = 80, yNextBt = 20, wNextBt = 50, hNextBt = 20;

	public static int xPreBt = 20, yPreBt = 20, wPreBt = 50, hPreBt = 20;

	panelRight pr = new panelRight(15, 20);
	workPanel wp = new workPanel(this);
	JPanel ctrTable = new JPanel();
	JPanel MatricTable;
	JTable table = new JTable();
	TableValues tableValues = new TableValues();

	private int loccard = 1;
	CardLayout card = new CardLayout();
	JPanel desc = new JPanel();
	// Style Panels
	ImageIcon startIcon = new ImageIcon(MainFrame.class.getResource("startIcon.png"));
	Icon pauseIcon = new ImageIcon(MainFrame.class.getResource("pauseIcon.png"));
	Icon stopIcon = new ImageIcon(MainFrame.class.getResource("stopIcon.png"));
	Icon nextIcon = new ImageIcon(MainFrame.class.getResource("nextIcon.png"));
	Icon previousIcon = new ImageIcon(MainFrame.class.getResource("previousIcon.png"));

	private JPanel panelCredit = new PanelCredit();

	private JPanel panelWideBrowse = new JPanel();
	public panelSoDoBFS panelSoDoBFS = new panelSoDoBFS(this);
	public panelSoDoDFS panelSoDoDFS = new panelSoDoDFS(this);
	
	
	public JPanel panelRightCorner = new JPanel();
	
	JTextField queue = new JTextField();
	public JButton play;
	public JButton play2;
	public JButton play3;
	JButton New, BFS, DFS, Dijkstra;
	JCheckBox autoNumber, hideNumber;

	public MainFrame() {

		super("Graph simulator");
		setLayout(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JMenuBar mb = new JMenuBar();
		this.setJMenuBar(mb);
		New = new JButton("Create new");
		New.addActionListener(this);
		BFS = new JButton("Breadth First Search");
		BFS.addActionListener(this);
		DFS = new JButton("Depth First Search");
		DFS.addActionListener(this);
		Dijkstra = new JButton("Dijkstra Algorithm");
		
		mb.add(New);
		mb.add(BFS);
		mb.add(DFS);
		mb.add(Dijkstra);

		wp.setBounds(5, 5, width, heigh);
		add(wp);
		makeCtrlTable();
		makeMatricTable();
		makeDescr();
		JLabel helpNote = new JLabel("Right click to make a point");
		helpNote.setBounds(25, 60, 200, 20);
		panelCredit.add(helpNote);

	}

	// to be done
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
		desc.add(panelCredit, "edit");
		panelCredit.setBorder(BorderFactory.createTitledBorder("Edit graph"));
		panelCredit.setLayout(null);
		panelCredit.add(pr);

		panelWideBrowse.setBorder(BorderFactory.createTitledBorder("BFS"));

		JPanel autoBt = new JPanel();
		autoBt.setBounds(5, 520, 300, 80);
		autoBt.setLayout(null);
		autoBt.setBorder(BorderFactory.createTitledBorder("Auto"));
		panelWideBrowse.add(autoBt);

		play = new JButton(startIcon);

		play.setBounds(xStartBt, yStartBt, wStartBt, hStartBt);

		play.addActionListener(new ActionListener() {
			// STARTBFS
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!panelSoDoBFS.run) {
					if (wp.ds.FirstNode != null) {
						if (panelSoDoBFS.Step == null) {
							panelSoDoBFS.Step = panelSoDoBFS.approach.start;
						} else if (panelSoDoBFS.Step.buoc== "buff2")
							panelSoDoBFS.Step = panelSoDoBFS.approach.start;
						panelSoDoBFS.run = true;
						play.setIcon(pauseIcon);
					}

				} else {
					panelSoDoBFS.run = false;
					play.setIcon(startIcon);
				}

			}
		});
		autoBt.add(play);
		JButton pause = new JButton(stopIcon);
		pause.setBounds(xStopBt, yStopBt, wStopBt, hStopBt);
		pause.addActionListener(new ActionListener() {
			// PAUSEBFS
			@Override
			public void actionPerformed(ActionEvent arg0) {
				panelSoDoBFS.Step = null;
				panelSoDoBFS.run = false;
				play.setIcon(startIcon);
				panelSoDoBFS.Step = panelSoDoBFS.approach.start;
				panelSoDoBFS.setState();
				panelSoDoBFS.render();

			}

		});

		//JLabel lbSpeed = new JLabel("Speed");
		//lbSpeed.setBounds(20, 50, 100, 20);
		//autoBt.add(lbSpeed);

		JPanel manual = new JPanel();
		manual.setBounds(305, 520, 155, 50);
		manual.setBorder(BorderFactory.createTitledBorder("Step by step"));

		JButton mnext = new JButton(nextIcon);
		manual.setLayout(null);
		mnext.setBounds(xNextBt, yNextBt, wNextBt, hNextBt);
		mnext.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (panelSoDoBFS.Step == null && wp.ds.FirstNode != null) {
					panelSoDoBFS.makeScriptBFS(wp.ds.FirstNode.cost + 1, wp.data);
					panelSoDoBFS.Step = panelSoDoBFS.approach.start;
				}
				panelSoDoBFS.actionPerformd();
			}

		});
		manual.add(mnext);
		JButton manualPrev = new JButton(previousIcon);
		manualPrev.setBounds(xPreBt, yPreBt, wPreBt, hPreBt);
		manualPrev.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panelSoDoBFS.actionPerformd2();
			}

		});
		manual.add(manualPrev);
		panelWideBrowse.add(manual);

		JLabel lbQueue = new JLabel("Queue");
		lbQueue.setBounds(10, 610, 90, 20);
		panelWideBrowse.add(lbQueue);
		queue.setBounds(80, 610, 200, 20);
		panelWideBrowse.add(queue);

		panelWideBrowse.setLayout(null);

		panelSoDoBFS.setBounds(5, 20, 455, 500);

		panelWideBrowse.add(panelSoDoBFS);
		desc.add(panelWideBrowse, "bfs");


		desc.add(panelRightCorner, "dfs");
		panelRightCorner.setBorder(BorderFactory.createTitledBorder("DFS"));

		panelRightCorner.setLayout(null);

		panelSoDoDFS.setBounds(5, 20, panelSoDoDFS.w, panelSoDoDFS.h);
		panelRightCorner.add(panelSoDoDFS);

		JPanel autoBt2 = new JPanel();
		autoBt2.setBounds(5, 520, 300, 80);
		autoBt2.setLayout(null);
		autoBt2.setBorder(BorderFactory.createTitledBorder("Auto"));
		panelRightCorner.add(autoBt2);

		play2 = new JButton(startIcon);
		play2.setBounds(xStartBt, yStartBt, wStartBt, hStartBt);

		play2.addActionListener(new ActionListener() {
			// STARTBFS
			@Override
			public void actionPerformed(ActionEvent arg0) {

				if (wp.ds.FirstNode != null) {
					if (!panelSoDoDFS.run) {
						if (panelSoDoDFS.Step == null) {
							panelSoDoDFS.Step = panelSoDoDFS.approach.start;
						}
						if (panelSoDoDFS.Step.buoc == "buff2") {
							panelSoDoDFS.Step = panelSoDoDFS.approach.start;
						}
						panelSoDoDFS.run = true;
						play2.setIcon(pauseIcon);
					} else {
						panelSoDoDFS.run = false;
						play2.setIcon(startIcon);
					}

				}

			}

		});
		autoBt2.add(play2);
		JButton pause2 = new JButton(stopIcon);
		pause2.setBounds(xStopBt, yStopBt, wStopBt, hStopBt);
		pause2.addActionListener(new ActionListener() {
			// PAUSEBFS
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				panelSoDoDFS.Step = null;
				panelSoDoDFS.run = false;
				play2.setIcon(startIcon);
				panelSoDoDFS.Step = panelSoDoDFS.approach.start;
				panelSoDoDFS.render();
			}

		});
		autoBt2.add(pause2);

		//JLabel lbSpeed2 = new JLabel("Speed");
		//lbSpeed2.setBounds(20, 50, 100, 20);
		//autoBt2.add(lbSpeed2);

		JPanel manual2 = new JPanel();
		manual2.setBounds(305, 520, 155, 50);
		manual2.setBorder(BorderFactory.createTitledBorder("Step by step"));

		JButton manualNext = new JButton(nextIcon);
		manual2.setLayout(null);
		manualNext.setBounds(xNextBt, yNextBt, wNextBt, hNextBt);
		manualNext.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (panelSoDoDFS.Step == null && wp.ds.FirstNode != null) {
					panelSoDoDFS.makeKichBanDFS(wp.ds.FirstNode.cost + 1, wp.data);
					panelSoDoDFS.Step = panelSoDoDFS.approach.start;
				}
				panelSoDoDFS.actionPerformd();
			}

		});
		manual2.add(manualNext);
		JButton manualPrev2 = new JButton(previousIcon);
		manualPrev2.setBounds(xPreBt, yPreBt, wPreBt, hPreBt);
		manualPrev2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				panelSoDoDFS.actionPerformd2();
			}

		});
		manual2.add(manualPrev2);
		panelRightCorner.add(manual2);

		JLabel lbOutput = new JLabel("Result: ");
		lbOutput.setBounds(10, 605, 100, 20);
		panelRightCorner.add(lbOutput);

		//ketQuaDuyet.setBounds(60, 605, 300, 20);
		//panelRightCorner.add(ketQuaDuyet);

		c.add(desc);

	}

	private void makeMatricTable() {
		MatricTable = new JPanel();
		MatricTable.setLayout(null);
		MatricTable.setBounds(5, 470, 501, 180);
		MatricTable.setBorder(BorderFactory.createTitledBorder("Matrix"));
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
		ctrTable.setLayout(null);
		ctrTable.setBorder(BorderFactory.createTitledBorder("Mode"));
		add(ctrTable);

		autoNumber = new JCheckBox("Auto generate weight number");
		autoNumber.setBounds(15, 20, 200, 20);
		autoNumber.addActionListener(this);
		ctrTable.add(autoNumber);

		hideNumber = new JCheckBox("Hide weight number");
		hideNumber.setBounds(220, 20, 150, 20);
		hideNumber.addActionListener(this);
		ctrTable.add(hideNumber);

		setSize(WIDTH, HEIGH);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == New) {

			play.setIcon(startIcon);
			play2.setIcon(startIcon);
			play3.setIcon(startIcon);

			if (result != null) {
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
			//wp.DJ = false;
			wp.listEdge.format();
			wp.repaint();
			pr.setRun(true);
	
			panelSoDoBFS.timer.stop();
			panelSoDoDFS.timer.stop();
			panelSoDoBFS.Step = null;
			panelSoDoDFS.Step = null;
			//panelDijstra.Step = null;
			//panelDijstra.approach = new MainDj();
			card.show(desc, "edit");

		}

		if (e.getSource() == this.BFS) {

			panelSoDoBFS.Step = null;
			panelSoDoDFS.Step = null;
			//panelDijstra.Step = null;

			play.setIcon(startIcon);

			if (result != null) {

				result.setVisible(false);
				result = null;
			}
			result = new resultSearch(this, 1);
			result.setVisible(true);

			wp.BFS = true;
			wp.DFS = false;
			
			card.show(desc, "bfs");
			panelSoDoBFS.insertMatric(wp.matNum.getA());
			panelSoDoBFS.approach.start = null;
			if (wp.ds.FirstNode != null) {
				panelSoDoBFS.setInitial(wp.ds.FirstNode.cost + 1, wp.data);
				panelSoDoBFS.makeScriptBFS(wp.ds.FirstNode.cost + 1, wp.data);
				result.prosessingTime.setNumStep(panelSoDoBFS.approach);
			}

			panelSoDoBFS.timer.start();
			panelSoDoDFS.run = false;
			panelSoDoDFS.timer.stop();

			wp.edit = false;
			wp.repaint();
			pr.setRun(false);
		}
		if (e.getSource() == this.DFS) {

			panelSoDoBFS.Step = null;
			panelSoDoDFS.Step = null;
			play2.setIcon(startIcon);

			if (result != null) {
				result.setVisible(false);
				result = null;
			}
			result = new resultSearch(this, 2);
			result.setVisible(true);

			wp.BFS = false;
			wp.DFS = true;
			//wp.DJ = false;
			panelSoDoBFS.run = false;
			panelSoDoBFS.timer.stop();
			card.show(desc, "dfs");

			panelSoDoDFS.approach.start = null;
			if (wp.ds.FirstNode != null) {
				panelSoDoDFS.insertMatric(wp.matNum.getA());

				panelSoDoDFS.setKhoiTao(wp.ds.FirstNode.cost + 1, wp.data);
				panelSoDoDFS.makeKichBanDFS(wp.ds.FirstNode.cost + 1, wp.data);
				result.prosessingTime.setNumStep(panelSoDoDFS.approach);
			}

			panelSoDoDFS.timer.start();
			wp.edit = false;
			wp.repaint();
			pr.setRun(false);
		}
		if (e.getSource() == this.Dijkstra) {

			play3.setIcon(startIcon);

			if (result != null) {
				result.setVisible(false);
				result = null;
			}

			wp.BFS = false;
			wp.DFS = false;
			//wp.DJ = true;

			if (wp.ds.FirstNode != null) {
			}

			panelSoDoBFS.timer.stop();
			panelSoDoDFS.timer.stop();

			card.show(desc, "dj");
			wp.edit = false;
			wp.repaint();
			pr.setRun(false);
		}

		if (this.autoNumber.isSelected()) {
			wp.ranDome = true;
		} else
			wp.ranDome = false;

		if (this.hideNumber.isSelected()) {
			wp.showNumb = false;
			wp.repaint();
		} else {
			wp.showNumb = true;
			wp.repaint();
		}
	}
	
	public static void main(String[] as) {		
		new MainFrame();
	}

}
