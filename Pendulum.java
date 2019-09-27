import java.awt.*;
import javax.swing.*;

public class Pendulum extends JPanel implements Runnable {

	private double angle = Math.PI / 2;
	private final int length;

	public Pendulum(final int length) {
		this.length = length;
		setDoubleBuffered(true);
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.BLACK);
		int anchorX = getWidth() / 2, anchorY = getHeight() / 4;
		int ballX = anchorX + (int) (Math.sin(angle) * length);
		int ballY = anchorY + (int) (Math.cos(angle) * length);
		g.drawLine(anchorX, anchorY, ballX, ballY);
		g.fillOval(anchorX - 3, anchorY - 4, 7, 7);
		g.fillOval(ballX - 7, ballY - 7, 14, 14);
	}

	@Override
	public void run() {
		double angleAccel, angleVelocity = 0, dt = 0.1;
		while (true) {
			angleAccel = -9.81 / length * Math.sin(angle);
			angleVelocity += angleAccel * dt;
			angle += angleVelocity * dt;
			repaint();
			try {
				Thread.sleep(15);
			} catch (InterruptedException ex) {}
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(2 * length + 50, length / 2 * 3);
	}

	public static void main(final String... args) {
		JFrame frame = new JFrame("Pendulum");
		Pendulum pendulum = new Pendulum(200);
		frame.add(pendulum);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		new Thread(pendulum).start();
	}
}
