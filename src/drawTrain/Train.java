package drawTrain;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Arc2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;

class Train extends JPanel implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	private int color1, color2, color3;
	private Color randomColour;// màu của hàng rót xuống
	private int N ;              // số lượng tối đa hàng 1 toa.
	private int  numberOfWagons;    // số lượng toa tàu
	private int  originalNumberGoods, numberGoods ;     // số lượng hàng trong kho
	private boolean pouringGoods = false, stop = false;// biến tạm dừng chương trình
	private int x =  100000, y = 0; // x : toạ độ tàu, y : toạ độ hàng rót
    private int count; // lượng hàng đã rót vào một toa
    private double angle = 0.0, angle1 = 0.0; // biến xoay
	private Timer timer;
    private final int DELAY = 10 ;
    private int [] numberOfGoodsPerWagon ; // mảng lưu số lượng hàng đang có trên mỗi toa
    private int wagonNumber = 0; // số của toa tới chỗ rót
    private boolean skip = false ;
   
    
    
 // phương thức khởi tạo
    public Train(int number, int N, int numberGoods) {
    	
		this.numberOfWagons = number;
		this.N = N;
		this.originalNumberGoods = numberGoods;
		this.numberGoods = numberGoods;
		numberOfGoodsPerWagon = new int [numberOfWagons + 1];
		randomGoods();
        initTimer();
        setBackground(new Color(176, 226, 255));
        
    }
    	

    // khởi tạo giao diện
    private void initTimer() {
        timer = new Timer(DELAY, this);
        timer.start();
    }
    
    // trạng thái cảu chương trình (tạm dừng/ không tạm dừng)
    public void state() {
    	if(stop) timer.start();
    	else timer.stop();	 
    	stop = !stop;
    	repaint();	   	
    }
    
    // restart (đặt lại về trạng thái ban đầu)
    public void Reset() {
    	numberGoods = originalNumberGoods;
    	repaint();
     }
    
    // bỏ qua toa
    public void Skip() {	
    		skip = true;
    }
    
	// random hàng ban đầu trên các toa
	private void randomGoods() {
		int random;
		
		for(int i = 0 ; i < numberOfWagons; i ++ ) {
			random = new Random().nextInt(N + 1);
			numberOfGoodsPerWagon[i] = random;
		}
	}
		
	
 
    // vẽ tàu
	private void drawTrain(Graphics g)  {
		int h = getHeight() - 100;
		drawHeadTrain( g, x - 320,h);
		drawBigWheel(g, x - 320, h);
		for(int i = 0; i< numberOfWagons ;i ++) {
			int index = x - (320 + 240 + i*240); // vị trí của toa đang vẽ
        	drawWagon (g,  index , h);
        	drawSmallWheel(g,index,h);       	
        	g.setFont(new Font("TimesRoman", Font.BOLD, 40));
		    g.setColor(new Color(100, 149, 237));
		    String text = String.valueOf(numberOfGoodsPerWagon[i]) + "/" + N;
		    FontMetrics fontmetrics = g.getFontMetrics();
		    g.drawString(text,index  + 100 - fontmetrics.stringWidth(text) / 2, 140 + h/2 );
		    if( index == 400) {
		    	pouringGoods = true;
		    	wagonNumber = i;
		    	count = numberOfGoodsPerWagon[i];
		    }
        }
		
    }
	
	private void drawHeadTrain(Graphics g, int w, int  h) {
	       
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.translate(w , h / 2 - 50);
       
        g2d.setColor(Color.black);
        BasicStroke bs1 = new BasicStroke(5, BasicStroke.CAP_ROUND,
                BasicStroke.CAP_ROUND);
        g2d.setStroke(bs1);
        Shape s;
        
// vẽ ống khoi nhỏ
        g2d.drawRect(190, 105, 20, 20);
        g2d.setColor(Color.gray);
        g2d.fillRect(190, 105, 20, 20);
        g2d.setColor(Color.black);
  // vẽ miệng ống khói nhỏ      
        g2d.drawOval(190, 104, 20, 2);
        g2d.setColor(Color.white);
        g2d.fillOval(190, 104, 20, 2);
        g2d.setColor(Color.black);
        
// vẽ ống khói to
        g2d.drawRect(230, 80, 30, 45);
        g2d.setColor(Color.gray);
        g2d.fillRect(230, 80, 30, 45);
        g2d.setColor(Color.black);
//       vẽ miệng ống khói to 
        g2d.drawOval(230, 78, 30, 4);
        g2d.setColor(Color.white);
        g2d.fillOval(230, 78, 30, 4);
        g2d.setColor(Color.black);
   
//      vẽ thân trên
        g2d.setStroke(bs1);
        s = new Arc2D.Float(250, 125, 50, 50, 90, -180, Arc2D.OPEN);
        g2d.draw(s);
        g2d.setColor(new Color(40 , 120, 40));
        g2d.fill(s);
        g2d.setColor(Color.black);
     
 //       
        g2d.drawLine(135,125,280,125);
        g2d.setColor(new Color(40 ,120, 40));
        g2d.fillRect(135, 125, 145, 50);
        g2d.setColor(Color.black);      

        
 //      vẽ khoang lái 
        s = new Rectangle2D.Float(15,15,120,160);
        g2d.draw(s);
        g2d.setColor(new Color(58, 95, 205));
        g2d.fill(s);
        g2d.setColor(Color.black);
        
//        vẽ nóc khoang lái
        s = new RoundRectangle2D.Float(-15, 0, 180, 15, 10, 10);
        g2d.draw(s);
        g2d.setColor(new Color(205, 38, 38));
        g2d.fill(s);
        g2d.setColor(Color.black);
        
//        vẽ thân dưới
        s = new RoundRectangle2D.Float(0, 175, 320, 70, 20, 20);
        g2d.draw(s);
        g2d.setColor(Color.orange);
        g2d.fill(s);
        g2d.setColor(Color.black);
        
        // vẽ sửa xổ
        g2d.drawOval(45,35,60,60);
        g2d.setColor(Color.white);
        g2d.fillOval(45,35,60,60);
        g2d.setColor(Color.black);

    }
	
	private void drawWagon (Graphics g, int w, int h) {
    	Graphics2D g2d = (Graphics2D)g.create();
    	g2d.translate(w , h / 2 + 45);
        BasicStroke bs1 = new BasicStroke(10, BasicStroke.CAP_BUTT,
                BasicStroke.CAP_BUTT);
        g2d.setStroke(bs1);
        g2d.setColor(Color.black);
        Shape s;
              
//  vẽ điểm nối giữa các toa
        g2d.drawLine(200,125,240,125);
        g2d.drawOval(210, 115, 20, 20);
        g2d.setColor(Color.white);
        g2d.fillOval(210, 115, 20, 20);
        g2d.setColor(Color.black);
        
//    vẽ khối thân toa
        bs1 = new BasicStroke(5, BasicStroke.CAP_ROUND,
                BasicStroke.CAP_ROUND);
        g2d.setStroke(bs1);
        s = new RoundRectangle2D.Float(0, 0, 200, 160, 20, 20);
        g2d.draw(s);
        g2d.setColor(Color.ORANGE);
        g2d.fill(s);
        g2d.setColor(Color.black);
        
//     vẽ trang trí
        bs1 = new BasicStroke(2, BasicStroke.CAP_ROUND,
                BasicStroke.CAP_ROUND);
        g2d.setStroke(bs1);
        g2d.drawLine(0, 25, 200, 25);
        g2d.drawLine(0, 40, 200, 40); 
        
//
        g2d.setColor(Color.gray);
        g2d.fillRect(0, 25, 200, 15);

    	
    }
	
	
	
	// vẽ bánh cho đầu tàu
	private void drawBigWheel( Graphics g,int  w,int  h) {
        BasicStroke bs1;
//      vẽ bánh trước
        bs1 = new BasicStroke(10, BasicStroke.CAP_ROUND,
                BasicStroke.CAP_ROUND);
        Graphics2D g1 = (Graphics2D) g.create();
        g1.setColor(Color.black);        
        g1.translate(w + 80,h/2 + 185);
        g1.rotate(angle1);
        g1.setStroke(bs1);
        g1.drawOval(-50,-50,100,100);
        g1.setColor(new Color(178, 34, 34));
        g1.fillOval(-50,-50,100,100);
        bs1 = new BasicStroke(5, BasicStroke.CAP_ROUND,
                BasicStroke.CAP_ROUND);
        g1.setStroke(bs1);
        g1.setColor(Color.black);
        g1.drawLine(-50, 0, 50, 0);
        g1.fillOval(-15, -15, 30, 30);
              
        
//vẽ báng sau
        bs1 = new BasicStroke(10, BasicStroke.CAP_ROUND,
                BasicStroke.CAP_ROUND);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(Color.black);
        
        g2.translate(w + 80 + 160, h/2 + 185);
        g2.rotate(angle1);
        g2.setStroke(bs1);
        g2.drawOval(-50,-50,100,100);
        g2.setColor(new Color(178, 34, 34));
        g2.fillOval(-50,-50,100,100);
        bs1 = new BasicStroke(5, BasicStroke.CAP_ROUND,
                BasicStroke.CAP_ROUND);
        g2.setStroke(bs1);
        g2.setColor(Color.black);
        g2.drawLine(-50, 0, 50, 0);
        g2.fillOval(-15, -15, 30, 30);
        
    }
	
// vẽ bánh cho than tàu	
	private void drawSmallWheel( Graphics g,int  w,int  h) {
    	
        BasicStroke bs1;
// ve bánh trước
        bs1 = new BasicStroke(10, BasicStroke.CAP_ROUND,
                BasicStroke.CAP_ROUND);
        Graphics2D g1 = (Graphics2D) g.create();
        g1.setColor(Color.black);
        
        g1.translate(w + 50 ,h/2 + 200);
        g1.rotate(angle);
        g1.setStroke(bs1);
        g1.drawOval(-35,-35,70,70);
        g1.setColor(new Color(178, 34, 34));
        g1.fillOval(-35,-35,70,70);
        bs1 = new BasicStroke(5, BasicStroke.CAP_ROUND,
                BasicStroke.CAP_ROUND);
        g1.setStroke(bs1);
        g1.setColor(Color.black);
        g1.drawLine(-35, 0, 35, 0);
        g1.fillOval(-10, -10, 20, 20);       
        
        
//      vẽ bánh sau
        bs1 = new BasicStroke(10, BasicStroke.CAP_ROUND,
                BasicStroke.CAP_ROUND);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(Color.black);
        g2.translate(w + 50 + 100,h/2 + 200);
        g2.rotate(angle);
        g2.setStroke(bs1);
        g2.drawOval(-35,-35,70,70);
        g2.setColor(new Color(178, 34, 34));
        g2.fillOval(-35,-35,70,70);
        bs1 = new BasicStroke(5, BasicStroke.CAP_ROUND,
                BasicStroke.CAP_ROUND);
        g2.setStroke(bs1);
        g2.setColor(Color.black);
        g2.drawLine(-35, 0, 35, 0);
        g2.fillOval(-10, -10, 20, 20);
        
    }
	
		
	// vẽ đường ray
    private void drawLineTrain(Graphics g) {
    	Graphics2D g2d = (Graphics2D) g.create();
    	BasicStroke bs1 = new BasicStroke(14, BasicStroke.CAP_ROUND,
                BasicStroke.CAP_ROUND);
        g2d.setStroke(bs1);
        g2d.setColor(new Color(110, 123, 139));
        g2d.drawLine(0,428,1000,428);
        g2d.setColor(new Color(139, 90, 43));
        for(int i = 20; i < 1000 ; i += 90 ) {
        	g2d.fillRect(i, 435, 40, 10);
        }
        
        bs1 = new BasicStroke(30, BasicStroke.CAP_ROUND,
                BasicStroke.CAP_ROUND);
        g2d.setStroke(bs1);
        g2d.setColor(new Color(192, 192, 192));
        g2d.drawLine(0,460,1000,460);
        
    	        
    }
    
	
    //  vẽ hàng rót	
	private void doDrawingPouringGoods(Graphics g2d) {
        g2d.setColor(randomColour);       
        g2d.fillOval(472,y,56,56);
    }
	
	// random màu của hàng	
	private void ramdomColor() {
		Random randomGenerator = new Random();
        color1 = randomGenerator.nextInt(256);
        color2 = randomGenerator.nextInt(256);
        color3 = randomGenerator.nextInt(256);
        randomColour = new Color(color1 , color2, color3); 
	}
	
	// vẽ ống rót
    private void drawFillingTube(Graphics g){
    	Graphics2D g2d = (Graphics2D) g.create();
    	g2d.translate(375, 0);
    	GeneralPath drawFillingTube = new GeneralPath(); 
        int points[][] = {{0,0},{85,55},{85,100},{75,100},{75,120},
        		{175,120},{175,100},{165,100},{165,55},{250,0}};
        drawFillingTube.moveTo(points[points.length - 1][0], points[points.length - 1][1]);
        
        for (int k = 0; k < points.length; k++) {
             
        	drawFillingTube.lineTo(points[k][0], points[k][1]);
        }
        BasicStroke bs1 = new BasicStroke(5, BasicStroke.CAP_ROUND,
                BasicStroke.CAP_ROUND);
        g2d.setStroke(bs1);
        g2d.setColor(Color.black);
        g2d.draw(drawFillingTube);
        g2d.setColor(new Color(205, 38, 38));
        g2d.fill(drawFillingTube);
        g2d.setColor(Color.black);
        g2d.drawLine(85, 97, 165, 97);
        g2d.drawLine(85, 57, 165, 57);
        
        
        
    };
	
	// Hiển thị số hàng trong kho	
	void drawNumberGoods(Graphics g) {
		    Graphics2D g2d = (Graphics2D) g.create();
		    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
 
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
	     	
		    g2d.setFont(new Font("TimesRoman", Font.BOLD, 50));
		    g2d.setColor(new Color(0,0,205));	
		    FontMetrics fontmetrics = g2d.getFontMetrics();
		    String text = String.valueOf(numberGoods); 
		    g2d.drawString(text, 970 - fontmetrics.stringWidth(text), 50);
	}
	
	
	// vẽ skip
	private void drawSkip(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.translate(150, 16);
	    
        BasicStroke bs1 = new BasicStroke(10, BasicStroke.CAP_ROUND,
                BasicStroke.CAP_ROUND);
        g2d.setStroke(bs1);
        g2d.setColor(new Color(205, 38, 38));
        
        g2d.drawLine(0, 4, 15, 19);
        g2d.drawLine(0, 34, 15, 19);
        
        g2d.drawLine(23, 4, 38, 19);
        g2d.drawLine(23, 34, 38, 19);
        
	}
	
	
	
	
	// vẽ pause
	private void drawPause (boolean state, Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		BasicStroke bs1 = new BasicStroke(12, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_BEVEL);
		g2d.setColor(new Color(205, 38, 38));
        g2d.setStroke(bs1);
        if(state) {
        	GeneralPath triangle = new GeneralPath(); 
            int points[][] = {{13,13},{13,51},{48,32}};
            triangle.moveTo(points[0][0], points[0][1]);
            
            for (int k = 1; k < 3; k++) {
                 
                triangle.lineTo(points[k][0], points[k][1]);
            }
            g2d.fill(triangle);
        } else {
        	g2d.drawLine(20,20,20,50);
            g2d.drawLine(45,20,45,50);
        }
	}
	
	// draw restart
	private void drawReset(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		BasicStroke bs1 = new BasicStroke(8, BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_BEVEL);
		g2d.setColor(new Color(205, 38, 38));
        g2d.setStroke(bs1);
        Shape s = new Arc2D.Float(80, 20, 35, 35, 90, 260, Arc2D.OPEN);
        g2d.draw(s);
        g2d.drawLine(95, 20, 105, 20);
        g2d.drawLine(95,10, 105, 20);
        g2d.drawLine(95, 30, 105, 20);
        
 
		//g2d.drawImage(restart,80,5,null);	
	}
	
		
	
    //  thay đổi toạ độ
	private void step() {
		// nếu trạng thái là rót hàng và còn hàng
		if(pouringGoods && numberGoods > 0 ) {
			//  nếu skip trước khi toa tới vị trí rót
			if(skip && y == 0) {
        		pouringGoods = false;// ngưng rót hàng
        		x += 2;// tàu chạy tiếp
        		skip = false; // kết thúc skip
        	}else if (count < N) { // rót 1 hàng vào toa
				if(y < 250) y +=4 ; // nếu hàng chưa rơt đến toa thì tăng toạ độ của hàng
		        else { // hàng rơi đến toa
		        	//nếu hàng đang rót thì  hàng rót hết mới skip
		        	if(skip) { // nếu trạng thái skip
		        		pouringGoods = false;
		        		x += 2;
		        		skip = false;
		        	}
		        	count ++ ; // tăng số hàng trong toa
		        	numberOfGoodsPerWagon[wagonNumber] = count; // lưu lại số hàng trong toa
		        	numberGoods --; // giảm số hàng trong kho
		        	y = 0;
		        	ramdomColor();  // tạo màu hàng mới	   
		        }		
			}else { // nếu toa đầy hàng
				x += 2; // tàu chạy tiếp
	    		pouringGoods = false; // ngưng rót hàng
			}
				
		}else { // nếu không ở trạng thái rót hàng thì tàu di chuyển
			if(x < 1000 + numberOfWagons * 240 + 320) {// nếu tàu vẫn còn trong màn hình
				x += 2; // tăng toạ độ tàu
				angle += 2.0 / 35; // tăng góc quay bánh trước
				angle1 += 2.0 / 50; //tăng góc quay bánh sau
			}else { // nếu tàu đi khỏi màn hình
				// đặt lại toạ độ về 0
    			x = 0; 
    			// random hàng trên toa
				randomGoods();
				wagonNumber = 0;
				// chờ ngẫu nghiên từ 0 - 15 giây
	        	int random = new Random().nextInt(16);
	        	try {
					Thread.sleep(random * 1000); // cho luồng sleep
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	        }
			
		}                    
    }

	
	
    // vẽ	
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);              
        doDrawingPouringGoods(g);
        drawFillingTube(g);
        drawNumberGoods(g);
        drawLineTrain(g);
        drawTrain(g); 
        drawPause(stop,g);
        drawReset(g);
        drawSkip(g);
    }

	
	@Override
	public void actionPerformed(ActionEvent e) {	
		step();		
		repaint();		
	}

}
