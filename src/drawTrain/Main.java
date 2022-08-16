package drawTrain;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.KeyEvent;

public class Main extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static int number , N, numberGoods ;
	
	public Main() {
		Train train = new Train(number, N, numberGoods);
		
		add(train);
        setTitle("Train");
        setSize(1000, 500);
        setLocationRelativeTo(null);   
        this.setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // thêm sự kiện click chuột vào  nút reset, pause, skip
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            	int _x = e.getX();
                int _y = e.getY();
                if(_x >= 13 && _x <= 65 && _y >= 40 && _y <= 90) {
                	train.state();
                }else if(_x >= 80 && _x <= 130 && _y >= 40 && _y <= 90) {
                	train.Reset();       
                }else if (_x >= 150 && _x <= 190 && _y >= 40 && _y <= 90) {
                	train.Skip();
                }
            }
        });
        
	}
	
	
   
	
	
	public static void main(String args[]) {
	
		
		JFrame frame = new JFrame("DrawTrain");  
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width/2 - 250 , dim.height/2 - 150);
		
		
		JLabel label1 = new JLabel("Nhập số lượng toa: ");
		label1.setFont(new Font("Serif", Font.BOLD, 18));
		label1.setBounds(40, 20, 250, 50);
		
		
		JLabel label2 = new JLabel("Nhập số lượng hàng/toa: ");
		label2.setBounds(40, 80, 250, 50);
		label2.setFont(new Font("Serif", Font.BOLD, 18));
		
		JLabel label3 = new JLabel("Nhập số hàng trong kho: ");
		label3.setBounds(40, 140, 250, 50);
		label3.setFont(new Font("Serif", Font.BOLD, 18));
		
	    JTextField textfield1 = new JTextField();  
	    textfield1.setBounds(350,30, 50,30);
	    textfield1.setFont(new Font("Serif", Font.BOLD, 16));
	    
	    JTextField textfield2=new JTextField(); 
	    textfield2.setBounds(350,90, 50,30);
	    textfield2.setFont(new Font("Serif", Font.BOLD, 16));
	    
	    JTextField textfield3=new JTextField();
	    textfield3.setBounds(350,150, 50,30);
	    textfield3.setFont(new Font("Serif", Font.BOLD, 16));
	    
	    JButton botton=new JButton("OK");
	    botton.setFont(new Font("Time New Roman", Font.BOLD, 18));
	    botton.setBounds(200,220,95,30);  
	    botton.addActionListener(new ActionListener(){ // thêm sự kiện nhấn button
	    	 @Override
	    	 public void actionPerformed(ActionEvent e){  
	    		number = Integer.parseInt(textfield1.getText());
				N = Integer.parseInt(textfield2.getText());
				numberGoods = Integer.parseInt(textfield3.getText());	
	    			frame.setVisible(false);
	    			EventQueue.invokeLater(new Runnable() {
	    	            @Override
	    	            public void run() {
	    	 
	    	                Main ex = new Main();
	    	                ex.setVisible(true);
	    	    
	    	            } 
           
                    });
		
	        }  
	    }); 
	    
	    frame.add(botton);
	    frame.add(textfield1);  
	    frame.add(textfield2);
	    frame.add(textfield3);
	    frame.add(label1);
	    frame.add(label2);
	    frame.add(label3);
	    frame.setSize(500,300);  
	    frame.setLayout(null);  
	    frame.setVisible(true); 
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
    }

}
