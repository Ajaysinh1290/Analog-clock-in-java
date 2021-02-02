package analogclock;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.time.LocalDateTime;

import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.UIManager;

@SuppressWarnings("serial")
public class AnalogClock extends JFrame implements ActionListener{
	
		final int SIZE=500;
		final int RADIUS=230;
		final int MARGIN=10;
		final int X=0;
		final int Y=60;
		final int HOUR_HAND_HEIGHT=6;
		final int MINUTE_HAND_HEIGHT=3;
		final int SECOND_HAND_HEIGHT=1;
		final int startAngle=90;
		AnalogClock() {
			
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setSize(this.SIZE-this.MARGIN,this.SIZE+this.Y-this.MARGIN);
			this.setTitle("Analog Clock");
			
			Timer timer=new Timer(1000,this);
			timer.start();
		
		}
		public void paint(Graphics g) {
			
			g.setColor(Color.DARK_GRAY);
			g.fillRect(0, 0, getWidth(),getHeight());
			g.setFont(new Font("Segoe UI",Font.BOLD,20));
			g.setColor(new Color(50,50,50));
			g.fillOval(X+5, Y-5, this.RADIUS*2+20, this.RADIUS*2+20);
			g.setColor(Color.white);
			for(int i=1; i<13; i++) {
				
				int x=(int) (Math.sin(Math.toRadians(this.startAngle*2-(i*30)))*(this.RADIUS-10))+this.getCenterX();
				int y=(int) (Math.cos(Math.toRadians(this.startAngle*2-(i*30)))*(this.RADIUS-10))+this.getCenterY();
				g.drawString(UIManager.get("PasswordField.echoChar")+"",x ,y );
			}
		
//			Hour hand
			{
				int degree=this.startAngle-getHour()*30-getMinute()/12*6;
				drawHand(this.RADIUS-90,HOUR_HAND_HEIGHT,degree,g);
				
			}
//			Minute hand
			{
				int degree=this.startAngle-(getMinute()/5)*30-getMinute()%5*6;
				drawHand(this.RADIUS-70,MINUTE_HAND_HEIGHT,degree,g);
				
			}
			
//			Second hand
			{
				int degree=this.startAngle-(getSecond()/5)*30-getSecond()%5*6;
				drawHand(this.RADIUS-50,SECOND_HAND_HEIGHT,degree,g);
			}
			
			int centerCircleRad=20;
			g.setColor(Color.yellow);
			g.fillOval(getCenterX()-centerCircleRad/2, getCenterY()-centerCircleRad/2, centerCircleRad, centerCircleRad);
			
			
		}
		void drawHand(int x,int y,int handWidth,int handHeight,int degree,Graphics g) {
			
			Graphics2D g2d = (Graphics2D)g;
			Rectangle2D myRect = new Rectangle2D.Double(x-handHeight/2,y-handHeight/2,handWidth, handHeight);
			AffineTransform at = AffineTransform.getRotateInstance(Math.toRadians(-degree),x,y);
			Shape rotatedRect = at.createTransformedShape(myRect);
			g2d.translate(0, 0);
			g2d.draw(rotatedRect);
			g2d.fill(rotatedRect);
		}
		void drawHand(int handWidth,int handHeight,int degree,Graphics g) {
			
			this.drawHand(getCenterX(), getCenterY(), handWidth, handHeight, degree, g);
		}
		int getCenterX() {
			
			return this.RADIUS+this.X+this.MARGIN;
		}
		int getCenterY() {
			
			return this.RADIUS+this.Y+this.MARGIN;
		}
		int getHour() {
			
			return LocalDateTime.now().getHour()%12;
		}
		int getMinute() {
			
			return LocalDateTime.now().getMinute();
		}
		int getSecond() {
			
			return LocalDateTime.now().getSecond();
		}
	
		public static void main(String[] args) {
			
			AnalogClock clock=new AnalogClock();
			clock.setLocation(200,100);
			clock.setResizable(false);
			clock.setVisible(true);
			
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			this.repaint();
		}

}
